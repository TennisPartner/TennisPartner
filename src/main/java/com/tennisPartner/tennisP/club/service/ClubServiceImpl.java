package com.tennisPartner.tennisP.club.service;

import com.tennisPartner.tennisP.club.dto.ClubJoinResponseDTO;
import com.tennisPartner.tennisP.club.dto.ClubRequestDTO;
import com.tennisPartner.tennisP.club.dto.ClubResponseDTO;
import com.tennisPartner.tennisP.club.entity.Club;
import com.tennisPartner.tennisP.club.entity.ClubJoin;
import com.tennisPartner.tennisP.club.repository.ClubJoinRepository;
import com.tennisPartner.tennisP.club.repository.ClubRepository;
import com.tennisPartner.tennisP.user.entity.User;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClubServiceImpl implements ClubService{

    ClubRepository clubRepository;

    ClubJoinRepository clubJoinRepository;

    @Autowired
    public ClubServiceImpl(ClubRepository clubRepository, ClubJoinRepository clubJoinRepository){
        this.clubRepository = clubRepository;
        this.clubJoinRepository = clubJoinRepository;
    }
    @Override
    @Transactional
    public ClubResponseDTO createClub(ClubRequestDTO req) {
        Club entity = req.toEntity();
        Club duplClub = clubRepository.findByClubName(entity.getClubName());

        if(duplClub != null){
            // 예외 처리
            // 동일한 이름의 클럽이 존재하는 경우
            return null;
        }

        Club saveClub = clubRepository.save(entity);
        User user = User.builder()
            .userIdx(1L)
            .userName("예시")
            .userNickname("예시")
            .build();
        ClubJoin join = new ClubJoin(saveClub, user, "Master");

        ClubJoin clubJoin = clubJoinRepository.save(join);
        if(clubJoin == null){
            // 예외 처리
            // club_join_tb에 데이터가 생성되지 않았을 경우
            return null;
        }

        ClubResponseDTO res = new ClubResponseDTO(saveClub);

        return res;
    }

    @Override
    @Transactional
    public ClubResponseDTO updateClub(Long clubIdx, ClubRequestDTO req) {
        Club findClub = clubRepository.findById(clubIdx).get();
        if(findClub == null || findClub.getUseYn() == 'N'){
            // 예외 처리
            // 삭제됐거나 존재하지 않는 클럽일 경우
           return null;
        }
        Club Entity = req.toEntity();
        findClub.updateClub(Entity);

        ClubResponseDTO res = new ClubResponseDTO(findClub);

        return res;
    }

    @Override
    @Transactional
    public Page<ClubResponseDTO> getClubList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createDt"));
        Page<Club> findList = clubRepository.findByUseYn('Y',pageable);
        if(findList.isEmpty()){
            // 해당하는 게시물이 없을 경우
            return null;
        }
        Page<ClubResponseDTO> resList = findList.map(p -> new ClubResponseDTO(p));

        return resList;
    }

    @Override
    @Transactional
    public ClubResponseDTO getClub(Long clubIdx) {
        Club findClub = clubRepository.findById(clubIdx).get();

        if(findClub == null || findClub.getUseYn() == 'N'){
            // 삭제됐거나 존재하지 않는 클럽일 경우
            return null;
        }

        ClubResponseDTO res = new ClubResponseDTO(findClub);
        return res;
    }

    @Override
    public ClubJoinResponseDTO joinClub(Long clubIdx) {
        User user = User.builder()
            .userIdx(1L)
            .userName("예시")
            .userNickname("예시")
            .build();

        Club findClub = clubRepository.findById(clubIdx).orElseThrow(EntityNotFoundException::new);

        if(findClub.getUseYn() == 'N'){
            //클럽 없음
            System.out.println("해당 클럽이 없습니다.");
            return null;
        }
        ClubJoin findJoin = clubJoinRepository.findByUserUserIdxAndClubClubIdx(user.getUserIdx(),clubIdx);

        if(findJoin != null){
            //이미 가입된 상태
            System.out.println("이미 가입된 상태입니다.");
            return null;
        }

        ClubJoin clubJoin = new ClubJoin(findClub, user, "Common");
        ClubJoin saveJoin = clubJoinRepository.save(clubJoin);

        ClubJoinResponseDTO res = new ClubJoinResponseDTO(saveJoin);

        return res;
    }


}
