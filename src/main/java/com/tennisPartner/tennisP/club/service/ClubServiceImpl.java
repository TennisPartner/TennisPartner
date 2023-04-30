package com.tennisPartner.tennisP.club.service;

import com.tennisPartner.tennisP.club.repository.dto.ClubJoinResponseDTO;
import com.tennisPartner.tennisP.club.repository.dto.ClubRequestDTO;
import com.tennisPartner.tennisP.club.repository.dto.ClubResponseDTO;
import com.tennisPartner.tennisP.club.domain.Club;
import com.tennisPartner.tennisP.club.domain.ClubJoin;
import com.tennisPartner.tennisP.club.repository.ClubJoinRepository;
import com.tennisPartner.tennisP.club.repository.ClubRepository;
import com.tennisPartner.tennisP.common.Exception.CustomException;
import com.tennisPartner.tennisP.user.domain.User;
import com.tennisPartner.tennisP.user.repository.JpaUserRepository;
import java.util.Optional;
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

    JpaUserRepository userRepository;

    @Autowired
    public ClubServiceImpl(ClubRepository clubRepository, ClubJoinRepository clubJoinRepository, JpaUserRepository userRepository){
        this.clubRepository = clubRepository;
        this.clubJoinRepository = clubJoinRepository;
        this.userRepository = userRepository;
    }
    @Override
    @Transactional
    public ClubResponseDTO createClub(ClubRequestDTO req, Long userIdx) {
        Club entity = req.dtoToClubEntity();
        Optional<User> findUser = userRepository.findById(userIdx);
        if(findUser.isEmpty() || findUser.get().getUseYn().equals("N")){
            throw new CustomException("해당 유저가 없습니다.", 100);
        }
        Optional<Club> duplClub = clubRepository.findByClubName(entity.getClubName());

        if(duplClub.isPresent() && duplClub.get().getUseYn().equals("Y")){
            // 예외 처리
            throw new CustomException("동일한 이름의 클럽이 존재합니다.", 201);
        }
        User user = findUser.get();
        ClubJoin join = new ClubJoin(entity, user, "Master");
        entity.addJoin(join);
        Club saveClub = clubRepository.save(entity);

        ClubResponseDTO res = new ClubResponseDTO(saveClub);

        return res;
    }

    @Override
    @Transactional
    public ClubResponseDTO updateClub(Long clubIdx, ClubRequestDTO req , Long userIdx) {
        Optional<Club> findClub = clubRepository.findById(clubIdx);

        if(findClub.isEmpty() || findClub.get().getUseYn().equals("N")){
            throw new CustomException("삭제됐거나 존재하지 않는 클럽입니다.", 200);
        }

        Optional<User> findUser = userRepository.findById(userIdx);


        if(findUser.isEmpty() || findUser.get().getUseYn().equals("N")){
            throw new CustomException("해당 유저가 없습니다.", 100);
        }

        Optional<Club> duplClub = clubRepository.findByClubName(req.getClubName());

        if(duplClub.isPresent() && duplClub.get().getUseYn().equals("Y") && req.getUseYn().equals("Y")){
            // 예외 처리
            throw new CustomException("동일한 이름의 클럽이 존재합니다.", 201);
        }

        Club club= findClub.get();

        Long masterIdx = club.getJoinList().stream()
            .filter(j -> j.getClubGrade().equals("Master"))
            .findFirst().get().getUser().getUserIdx();

        if(!masterIdx.equals(userIdx)){
            throw new CustomException("클럽 마스터만 수정할 수 있습니다.", 203);
        }



        Club updateClub = req.dtoToClubEntity();
        club.updateClub(updateClub);
        if(req.getUseYn().equals("N")){
            club.deleteJoin();
        }

        ClubResponseDTO res = new ClubResponseDTO(club);

        return res;
    }

    @Override
    @Transactional
    public Page<ClubResponseDTO> getClubList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createDt"));
        Page<Club> findList = clubRepository.findByUseYn("Y",pageable).get();

        if(findList.isEmpty()){
            throw new CustomException("해당 요청에 대한 클럽 리스트가 존재하지 않습니다.", 200);
        }

        Page<ClubResponseDTO> resList = findList.map(p -> new ClubResponseDTO(p));

        return resList;
    }

    @Override
    @Transactional
    public ClubResponseDTO getClub(Long clubIdx) {
        Optional<Club> findClub = clubRepository.findById(clubIdx);

        if(findClub.isEmpty() || findClub.get().getUseYn().equals("N")){
            throw new CustomException("삭제됐거나 존재하지 않는 클럽입니다.", 200);
        }

        ClubResponseDTO res = new ClubResponseDTO(findClub.get());
        return res;
    }

    @Override
    @Transactional
    public ClubJoinResponseDTO joinClub(Long clubIdx, Long userIdx) {
        Optional<User> findUser = userRepository.findById(userIdx);

        if(findUser.isEmpty() || findUser.get().getUseYn().equals("N")){
            throw new CustomException("해당 유저가 없습니다.", 100);
        }

        User user = findUser.get();

        Club findClub = clubRepository.findById(clubIdx).orElseThrow(EntityNotFoundException::new);

        if(findClub.getUseYn().equals("N")){
            throw new CustomException("삭제됐거나 존재하지 않는 클럽입니다.", 200);
        }
        Optional<ClubJoin> findJoin = clubJoinRepository
            .findByUserAndClub(user, findClub);

        if(findJoin.isPresent()){
            if(findJoin.get().getUseYn().equals("Y")){
                throw new CustomException("이미 가입한 클럽입니다.", 204);
            }else {
                //탈퇴 후 재 가입
                findJoin.get().reJoinClub();

                ClubJoinResponseDTO reJoin = new ClubJoinResponseDTO(findJoin.get());
                return reJoin;
            }

        }else {
            ClubJoin clubJoin = new ClubJoin(findClub, user, "Common");
            ClubJoin saveJoin = clubJoinRepository.save(clubJoin);
            ClubJoinResponseDTO joinDTO = new ClubJoinResponseDTO(saveJoin);
            return joinDTO;
        }

    }

    @Transactional
    @Override
    public void leaveClub(Long clubIdx, Long userIdx) {
        Optional<User> findUser = userRepository.findById(userIdx);

        if(findUser.isEmpty() || findUser.get().getUseYn().equals("N")){
            throw new CustomException("해당 유저가 없습니다.", 100);
        }

        User user = findUser.get();

        Club findClub = clubRepository.findById(clubIdx).orElseThrow(EntityNotFoundException::new);

        if(findClub.getUseYn().equals("N")){
            throw new CustomException("삭제됐거나 존재하지 않는 클럽입니다.", 200);
        }
        ClubJoin findJoin = clubJoinRepository.
            findByUserAndClub(user, findClub).get();

        if(findJoin == null || findJoin.getUseYn().equals("N")){
            throw new CustomException("가입하지 않았거나, 이미 탈퇴한 클럽입니다.", 205);
        }

        findJoin.leaveClub();

    }


}
