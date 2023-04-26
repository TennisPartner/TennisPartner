package com.tennisPartner.tennisP.clubBoard.service;

import com.tennisPartner.tennisP.club.domain.Club;
import com.tennisPartner.tennisP.club.domain.ClubJoin;
import com.tennisPartner.tennisP.club.repository.ClubJoinRepository;
import com.tennisPartner.tennisP.club.repository.ClubRepository;

import com.tennisPartner.tennisP.clubBoard.domain.ClubBoard;
import com.tennisPartner.tennisP.clubBoard.domain.ClubBoardJoin;
import com.tennisPartner.tennisP.clubBoard.repository.ClubBoardJoinRepository;
import com.tennisPartner.tennisP.clubBoard.repository.ClubBoardRepository;
import com.tennisPartner.tennisP.clubBoard.repository.dto.ClubBoardJoinResponseDTO;
import com.tennisPartner.tennisP.clubBoard.repository.dto.ClubBoardRequestDTO;
import com.tennisPartner.tennisP.clubBoard.repository.dto.ClubBoardResponseDTO;
import com.tennisPartner.tennisP.common.Exception.CustomException;
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
    ClubJoinRepository clubJoinRepository;
    ClubBoardJoinRepository clubBoardJoinRepository;

    @Autowired
    public ClubBoardServiceImpl(ClubRepository clubRepository, ClubBoardRepository clubBoardRepository,
        JpaUserRepository userRepository, ClubJoinRepository clubJoinRepository, ClubBoardJoinRepository clubBoardJoinRepository){
        this.clubRepository = clubRepository;
        this.clubBoardRepository = clubBoardRepository;
        this.userRepository = userRepository;
        this.clubJoinRepository = clubJoinRepository;
        this.clubBoardJoinRepository = clubBoardJoinRepository;
    }

    @Override
    @Transactional
    public ClubBoardResponseDTO createClubBoard(Long clubIdx, ClubBoardRequestDTO req, Long userIdx) {

        Optional<User> findWriter = userRepository.findById(userIdx);
        Optional<Club> findClub = clubRepository.findById(clubIdx);

        if(findCheck(findClub, findWriter)){
            User writer = findWriter.get();
            Club club = findClub.get();

            ClubBoard clubBoard = req.dtoToClubBoardEntity(club,writer);

            ClubBoard saveBoard = clubBoardRepository.save(clubBoard);

            ClubBoardResponseDTO res = new ClubBoardResponseDTO(saveBoard);
            return res;

        } else {
            return null;
        }

    }

    @Override
    @Transactional
    public ClubBoardResponseDTO updateClubBoard(Long clubIdx, Long clubBoardIdx, ClubBoardRequestDTO req, Long userIdx) {
        Optional<User> findWriter = userRepository.findById(userIdx);
        Optional<Club> findClub = clubRepository.findById(clubIdx);

        if(findCheck(findClub, findWriter)){
            User writer = findWriter.get();
            Club club = findClub.get();
            Optional<ClubBoard> findClubBoard = clubBoardRepository.findById(clubBoardIdx);
            if(findClubBoard.isEmpty() || findClubBoard.get().getUseYn().equals("N")){
                throw new CustomException("해당 게시글이 존재하지 않습니다.", 300);
            }
            ClubBoard clubBoard = findClubBoard.get();
            if(req.getUseYn().equals("N")){
                clubBoard.deleteJoin();
            }
            ClubBoard updateBoard = req.dtoToClubBoardEntity(club, writer);
            clubBoard.updateClubBoard(updateBoard);


            ClubBoardResponseDTO res = new ClubBoardResponseDTO(clubBoard);

            return res;
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public Page<ClubBoardResponseDTO> getClubBoardList(Long clubIdx, int page, int size, Long userIdx) {

        Optional<Club> findClub = clubRepository.findById(clubIdx);
        Optional<User> findUser = userRepository.findById(userIdx);

        if(findCheck(findClub,findUser)){
            Club club = findClub.get();
            Pageable pageable = PageRequest.of(page, size, Sort.by("createDt"));

            Page<ClubBoard> findList = clubBoardRepository.findByUseYnAndClub("Y",club,pageable).get();

            if(findList.isEmpty()){
                throw new CustomException("해당 게시글이 존재하지 않습니다.", 300);
            }

            Page<ClubBoardResponseDTO> resList = findList.map(p -> new ClubBoardResponseDTO(p));

            return resList;
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public ClubBoardResponseDTO getClubBoard(Long clubIdx, Long clubBoardIdx, Long userIdx) {
        Optional<Club> findClub = clubRepository.findById(clubIdx);
        Optional<User> findUser = userRepository.findById(userIdx);
        if(findCheck(findClub, findUser)){
            Optional<ClubBoard> findClubBoard = clubBoardRepository.findById(clubBoardIdx);

            if(findClubBoard.isEmpty() || findClubBoard.get().getUseYn().equals("N")){
                throw new CustomException("해당 게시글이 존재하지 않습니다.", 300);
            }

            ClubBoard clubBoard = findClubBoard.get();
            ClubBoardResponseDTO res = new ClubBoardResponseDTO(clubBoard);

            return res;
        }else {
            return null;
        }
    }

    @Override
    @Transactional
    public ClubBoardJoinResponseDTO joinMatch(Long clubIdx, Long clubBoardIdx, Long userIdx) {
        Optional<Club> findClub = clubRepository.findById(clubIdx);
        Optional<User> findUser = userRepository.findById(userIdx);

        if(findCheck(findClub, findUser)){
            User user = findUser.get();
            Optional<ClubBoard> findClubBoard = clubBoardRepository.findById(clubBoardIdx);
            if(findClubBoard.isEmpty() || findClubBoard.get().getUseYn().equals("N")){
                throw new CustomException("해당 게시글이 존재하지 않습니다.", 300);

            }

            ClubBoard clubBoard = findClubBoard.get();

            Long joinCount = clubBoardJoinRepository.countByClubBoard(clubBoard);
            if(clubBoard.getWantedCnt() < joinCount){
                throw new CustomException("이미 정원이 찬 모임입니다.", 305);

            }

            if(clubBoard.getWantedCnt() == 0){
                throw new CustomException("해당 게시판은 일정이 없는 게시판 입니다.", 306);

            }

            Optional<ClubBoardJoin> findClubBoardJoin = clubBoardJoinRepository.findByUserAndClubBoard(user,clubBoard);

            if(findClubBoardJoin.isPresent()){
                throw new CustomException("이미 참여한 모임입니다.", 304);
            }

            ClubBoardJoin clubBoardJoin = new ClubBoardJoin(clubBoard, user);

            clubBoardJoin = clubBoardJoinRepository.save(clubBoardJoin);

            ClubBoardJoinResponseDTO res = new ClubBoardJoinResponseDTO(clubBoardJoin);

            return res;
        }
        else {
            return null;
        }

    }

    @Override
    @Transactional
    public void leaveMatch(Long clubIdx, Long clubBoardIdx, Long userIdx) {
        Optional<Club> findClub = clubRepository.findById(clubIdx);
        Optional<User> findUser = userRepository.findById(userIdx);
        if(findCheck(findClub, findUser)){
            User user = findUser.get();

            Optional<ClubBoard> findClubBoard = clubBoardRepository.findById(clubBoardIdx);
            if(findClubBoard.isEmpty() || findClubBoard.get().getUseYn().equals("N")){
                throw new CustomException("해당 게시글이 존재하지 않습니다.", 300);
            }
            ClubBoard clubBoard = findClubBoard.get();

            Optional<ClubBoardJoin> findClubBoardJoin = clubBoardJoinRepository.findByUserAndClubBoard(user, clubBoard);

            if(findClubBoardJoin.isEmpty()){
                throw new CustomException("해당 게시글이 존재하지 않습니다.", 305);
            }

            clubBoardJoinRepository.delete(findClubBoardJoin.get());
        }


    }
    public boolean findCheck(Optional<Club> findClub, Optional<User> findUser){
        if(findClub.isEmpty() || findClub.get().getUseYn().equals("N")){
            throw new CustomException("삭제됐거나 존재하지 않는 클럽입니다.", 200);
        }
        if(findUser.isEmpty() || findUser.get().getUseYn().equals("N")){
            throw new CustomException("해당 유저가 없습니다.", 100);
        }
        User user = findUser.get();
        Club club = findClub.get();
        Optional<ClubJoin> findJoin = clubJoinRepository.findByUserAndClub(user, club);
        if(findJoin.isEmpty() || findJoin.get().getUseYn().equals("N")){
            throw new CustomException("해당 클럽에 가입하지 않았습니다.", 301);

        }
        return true;
    }
}
