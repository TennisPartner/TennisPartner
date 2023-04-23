package com.tennisPartner.tennisP.clubBoard.service;

import com.tennisPartner.tennisP.club.domain.Club;
import com.tennisPartner.tennisP.club.repository.ClubRepository;

import com.tennisPartner.tennisP.clubBoard.domain.ClubBoard;
import com.tennisPartner.tennisP.clubBoard.domain.ClubBoardJoin;
import com.tennisPartner.tennisP.clubBoard.repository.ClubBoardJoinRepository;
import com.tennisPartner.tennisP.clubBoard.repository.ClubBoardRepository;
import com.tennisPartner.tennisP.clubBoard.repository.dto.ClubBoardJoinResponseDTO;
import com.tennisPartner.tennisP.clubBoard.repository.dto.ClubBoardRequestDTO;
import com.tennisPartner.tennisP.clubBoard.repository.dto.ClubBoardResponseDTO;
import com.tennisPartner.tennisP.user.domain.User;
import com.tennisPartner.tennisP.user.repository.JpaUserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service
public class ClubBoardServiceImpl implements ClubBoardService{

    ClubRepository clubRepository;
    ClubBoardRepository clubBoardRepository;
    JpaUserRepository userRepository;
    ClubBoardJoinRepository clubBoardJoinRepository;

    @Autowired
    public ClubBoardServiceImpl(ClubRepository clubRepository, ClubBoardRepository clubBoardRepository,
        JpaUserRepository userRepository, ClubBoardJoinRepository clubBoardJoinRepository){
        this.clubRepository = clubRepository;
        this.clubBoardRepository = clubBoardRepository;
        this.userRepository = userRepository;
        this.clubBoardJoinRepository = clubBoardJoinRepository;
    }

    @Override
    @Transactional
    public ClubBoardResponseDTO createClubBoard(Long clubIdx, ClubBoardRequestDTO req) {

        User writer = userRepository.findById(1L).get();

        Optional<Club> findClub = clubRepository.findById(clubIdx);

        if(findClub.isEmpty() || findClub.get().getUseYn() == 'N'){
            System.out.println("해당 클럽은 존재하지 않습니다.");
            return null;
        }

        ClubBoard clubBoard = req.dtoToClubBoardEntity(findClub.get(),writer);

        ClubBoard saveBoard = clubBoardRepository.save(clubBoard);

        ClubBoardResponseDTO res = new ClubBoardResponseDTO(saveBoard);
        return res;
    }

    @Override
    @Transactional
    public ClubBoardResponseDTO updateClubBoard(Long clubIdx, Long clubBoardIdx, ClubBoardRequestDTO req) {
        User writer = userRepository.findById(1L).get();

        Optional<Club> findClub = clubRepository.findById(clubIdx);

        if(findClub.isEmpty()){
            System.out.println("클럽이 존재하지 않습니다.");
            return null;
        }

        Optional<ClubBoard> findClubBoard = clubBoardRepository.findById(clubBoardIdx);
        if(findClubBoard.isEmpty()){
            System.out.println("해당 게시판이 존재하지 않습니다.");
            return null;
        }
        ClubBoard updateBoard = req.dtoToClubBoardEntity(findClub.get(), writer);
        findClubBoard.get().updateClubBoard(updateBoard);

        ClubBoardResponseDTO res = new ClubBoardResponseDTO(findClubBoard.get());

        return res;
    }

    @Override
    @Transactional
    public Page<ClubBoardResponseDTO> getClubBoardList(Long clubIdx, int page, int size) {

        Optional<Club> findClub = clubRepository.findById(clubIdx);
        if(findClub.isEmpty()){
            System.out.println("해당 클럽이 존재하지 않습니다.");
            return null;
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by("createDt"));

        Page<ClubBoard> findList = clubBoardRepository.findByUseYnAndClub('Y',findClub.get(),pageable).get();


        if(findList.isEmpty()){
            System.out.println("해당 요청에 대한 게시글이 존재하지 않습니다.");
            return null;
        }

        Page<ClubBoardResponseDTO> resList = findList.map(p -> new ClubBoardResponseDTO(p));

        return resList;
    }

    @Override
    @Transactional
    public ClubBoardResponseDTO getClubBoard(Long clubIdx, Long clubBoardIdx) {
        Optional<Club> findClub = clubRepository.findById(clubIdx);
        if(findClub.isEmpty()){
            System.out.println("해당 클럽이 존재하지 않습니다.");
            return null;
        }
        Optional<ClubBoard> findClubBoard = clubBoardRepository.findById(clubBoardIdx);

        if(findClubBoard.isEmpty() || findClubBoard.get().getUseYn() == 'N'){
            System.out.println("해당 게시물이 존재하지 않습니다.");
            return null;
        }
        ClubBoardResponseDTO res = new ClubBoardResponseDTO(findClubBoard.get());

        return res;

    }

    @Override
    @Transactional
    public ClubBoardJoinResponseDTO joinMatch(Long clubIdx, Long clubBoardIdx) {
        Optional<Club> findClub = clubRepository.findById(clubIdx);
        if(findClub.isEmpty() || findClub.get().getUseYn() == 'N'){
            System.out.println("해당 클럽이 존재하지 않습니다.");
            return null;
        }
        User findUser = userRepository.findById(1L).get();
        Optional<ClubBoard> findClubBoard = clubBoardRepository.findById(clubBoardIdx);
        if(findClubBoard.isEmpty() || findClubBoard.get().getUseYn() == 'N'){
            System.out.println("해당 모임은 삭제되었거나 존재하지 않는 모임입니다.");
            return null;
        }

        Long joinCount = clubBoardJoinRepository.countByClubBoard(findClubBoard.get());
        if(findClubBoard.get().getWantedCnt() < joinCount){
            System.out.println("이미 정원이 찬 모임입니다.");
            return null;
        }

        if(findClubBoard.get().getWantedCnt() == 0){
            System.out.println("해당 게시판은 일정이 없는 게시판 입니다.");
            return null;
        }

        Optional<ClubBoardJoin> findClubBoardJoin = clubBoardJoinRepository.findByUserAndClubBoard(findUser, findClubBoard.get());

        if(findClubBoardJoin.isPresent()){
            System.out.println("이미 참여한 모임입니다.");
            return null;
        }

        ClubBoardJoin clubBoardJoin = new ClubBoardJoin(findClubBoard.get(), findUser);

        ClubBoardJoinResponseDTO res = new ClubBoardJoinResponseDTO(clubBoardJoinRepository.save(clubBoardJoin));

        return res;
    }

    @Override
    @Transactional
    public void leaveMatch(Long clubIdx, Long clubBoardIdx) {
        Optional<Club> findClub = clubRepository.findById(clubIdx);
        if(findClub.isEmpty() || findClub.get().getUseYn() == 'N'){
            System.out.println("해당 클럽이 존재하지 않습니다.");
            return;
        }
        User findUser = userRepository.findById(1L).get();
        Optional<ClubBoard> findClubBoard = clubBoardRepository.findById(clubBoardIdx);
        if(findClubBoard.isEmpty() || findClubBoard.get().getUseYn() == 'N'){
            System.out.println("해당 모임은 삭제되었거나 존재하지 않는 모임입니다.");
            return;
        }

        if(findClubBoard.get().getWantedCnt() == 0){
            System.out.println("해당 게시판은 일정이 없는 게시판 입니다.");
            return;
        }

        Optional<ClubBoardJoin> findClubBoardJoin = clubBoardJoinRepository.findByUserAndClubBoard(findUser, findClubBoard.get());

        if(findClubBoardJoin.isEmpty()){
            System.out.println("내가 참여하지 않은 모임입니다.");
            return;
        }

        clubBoardJoinRepository.delete(findClubBoardJoin.get());

    }
}
