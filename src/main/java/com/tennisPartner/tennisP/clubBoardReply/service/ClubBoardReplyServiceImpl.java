package com.tennisPartner.tennisP.clubBoardReply.service;

import com.tennisPartner.tennisP.club.domain.Club;
import com.tennisPartner.tennisP.club.domain.ClubJoin;
import com.tennisPartner.tennisP.club.repository.ClubRepository;

import com.tennisPartner.tennisP.clubBoard.domain.ClubBoard;
import com.tennisPartner.tennisP.clubBoard.repository.ClubBoardRepository;


import com.tennisPartner.tennisP.clubBoardReply.domain.ClubBoardReply;
import com.tennisPartner.tennisP.clubBoardReply.repository.ClubBoardReplyRepository;
import com.tennisPartner.tennisP.clubBoardReply.repository.dto.ClubBoardReplyResponseDTO;
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
public class ClubBoardReplyServiceImpl implements ClubBoardReplyService {

    JpaUserRepository userRepository;
    ClubBoardRepository clubBoardRepository;
    ClubRepository clubRepository;
    ClubBoardReplyRepository clubBoardReplyRepository;


    @Autowired
    public ClubBoardReplyServiceImpl(JpaUserRepository userRepository, ClubBoardRepository clubBoardRepository,
        ClubRepository clubRepository, ClubBoardReplyRepository clubBoardReplyRepository){
        this.userRepository = userRepository;
        this.clubBoardRepository = clubBoardRepository;
        this.clubRepository = clubRepository;
        this.clubBoardReplyRepository = clubBoardReplyRepository;
    }
    @Override
    @Transactional
    public ClubBoardReplyResponseDTO createClubBoardReply(Long clubIdx, Long clubBoardIdx, String replyContents, Long userIdx) {
        Optional<User> findWriter = userRepository.findById(userIdx);
        Optional<Club> findClub = clubRepository.findById(clubIdx);
        Optional<ClubBoard> findClubBoard = clubBoardRepository.findById(clubBoardIdx);

        if(findCheck(findClub, findWriter, findClubBoard)){
            User writer = findWriter.get();
            ClubBoardReply reply =  ClubBoardReply.builder()
                .clubBoard(findClubBoard.get())
                .writer(writer)
                .replyContents(replyContents)
                .build();

            ClubBoardReply saveReply = clubBoardReplyRepository.save(reply);

            ClubBoardReplyResponseDTO res = new ClubBoardReplyResponseDTO(saveReply);

            return res;
        }else{
            return null;
        }

    }

    @Override
    @Transactional
    public ClubBoardReplyResponseDTO updateClubBoardReply(Long clubIdx, Long clubBoardIdx, Long clubBoardReplyIdx, String replyContents, Long userIdx) {
        Optional<User> findWriter = userRepository.findById(userIdx);
        Optional<Club> findClub = clubRepository.findById(clubIdx);
        Optional<ClubBoard> findClubBoard = clubBoardRepository.findById(clubBoardIdx);
        if(findCheck(findClub,findWriter,findClubBoard)){
            Optional<ClubBoardReply> findClubBoardReply = clubBoardReplyRepository.findById(clubBoardReplyIdx);
            if(findClubBoardReply.isEmpty()){
                throw new CustomException("해당 댓글은 존재하지 않습니다.", 400);
            }
            User writer = findWriter.get();
            if(!writer.getUserIdx().equals(findClubBoardReply.get().getWriter().getUserIdx())){
                throw new CustomException("작성자만 수정할 수 있습니다.", 403);
            }

            findClubBoardReply.get().updateClubBoardReply(replyContents);

            ClubBoardReplyResponseDTO res = new ClubBoardReplyResponseDTO(findClubBoardReply.get());

            return res;
        }else {
            return null;
        }

    }

    @Override
    @Transactional
    public void deleteClubBoardReply(Long clubIdx, Long clubBoardIdx, Long clubBoardReplyIdx, Long userIdx) {
        Optional<User> findWriter = userRepository.findById(userIdx);
        Optional<Club> findClub = clubRepository.findById(clubIdx);
        Optional<ClubBoard> findClubBoard = clubBoardRepository.findById(clubBoardIdx);

        if(findCheck(findClub,findWriter,findClubBoard)) {
            User writer = findWriter.get();
            Optional<ClubBoardReply> findClubBoardReply = clubBoardReplyRepository.findById(clubBoardReplyIdx);
            if(findClubBoardReply.isEmpty()){
                throw new CustomException("해당 댓글은 존재하지 않습니다.", 400);
            }
            if(!writer.getUserIdx().equals(findClubBoardReply.get().getWriter().getUserIdx())){
                throw new CustomException("작성자만 수정할 수 있습니다.", 403);
            }

            clubBoardReplyRepository.deleteById(clubBoardReplyIdx);
        }
    }

    @Override
    @Transactional
    public Page<ClubBoardReplyResponseDTO> getClubBoardReplyList(Long clubIdx, Long clubBoardIdx, int page,
        int size, Long userIdx) {

        Optional<Club> findClub = clubRepository.findById(clubIdx);
        Optional<User> findUser = userRepository.findById(userIdx);
        Optional<ClubBoard> findClubBoard = clubBoardRepository.findById(clubBoardIdx);

        if(findCheck(findClub, findUser, findClubBoard)){
            Pageable pageable = PageRequest.of(page, size, Sort.by("createDt"));
            Page<ClubBoardReply> findList = clubBoardReplyRepository.findByClubBoard(findClubBoard.get(), pageable);

            if(findList.isEmpty()){
                throw new CustomException("해당 게시글이 존재하지 않습니다.", 400);
            }
            Page<ClubBoardReplyResponseDTO> resList = findList.map(p -> new ClubBoardReplyResponseDTO(p));

            return resList;
        } else {
            return null;
        }
    }

    public boolean findCheck(Optional<Club> findClub, Optional<User> findUser, Optional<ClubBoard> findClubBoard ){
        if(findClub.isEmpty() || findClub.get().getUseYn().equals("N")){
            throw new CustomException("삭제됐거나 존재하지 않는 클럽입니다.", 200);
        }
        if(findUser.isEmpty() || findUser.get().getUseYn().equals("N")){
            throw new CustomException("해당 유저가 없습니다.", 100);
        }
        if(findClubBoard.isEmpty() || findClubBoard.get().getUseYn().equals("N")){
            throw new CustomException("해당 클럽 게시판이 존재하지 않습니다.", 300);
        }

        return true;
    }
}
