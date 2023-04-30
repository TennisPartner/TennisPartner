package com.tennisPartner.tennisP.clubBoardReply.service;

import com.tennisPartner.tennisP.club.domain.Club;
import com.tennisPartner.tennisP.club.repository.ClubRepository;

import com.tennisPartner.tennisP.clubBoard.domain.ClubBoard;
import com.tennisPartner.tennisP.clubBoard.repository.ClubBoardRepository;


import com.tennisPartner.tennisP.clubBoardReply.domain.ClubBoardReply;
import com.tennisPartner.tennisP.clubBoardReply.repository.ClubBoardReplyRepository;
import com.tennisPartner.tennisP.clubBoardReply.repository.dto.ClubBoardReplyResponseDTO;
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
    public ClubBoardReplyResponseDTO createClubBoardReply(Long clubIdx, Long clubBoardIdx, String replyContents) {
        User writer = userRepository.findById(1L).get();

        Optional<Club> findClub = clubRepository.findById(clubIdx);

        if(findClub.isEmpty() || findClub.get().getUseYn().equals("N")){
            System.out.println("해당 클럽은 존재하지 않습니다.");
            return null;
        }
        Optional<ClubBoard> findClubBoard = clubBoardRepository.findById(clubBoardIdx);

        if(findClubBoard.isEmpty() || findClubBoard.get().getUseYn().equals("N")){
            System.out.println("해당 클럽 게시판은 존재하지 않습니다.");
            return null;
        }

        ClubBoardReply reply =  ClubBoardReply.builder()
            .clubBoard(findClubBoard.get())
            .writer(writer)
            .replyContents(replyContents)
            .build();

        ClubBoardReply saveReply = clubBoardReplyRepository.save(reply);

        ClubBoardReplyResponseDTO res = new ClubBoardReplyResponseDTO(saveReply);

        return res;
    }

    @Override
    @Transactional
    public ClubBoardReplyResponseDTO updateClubBoardReply(Long clubIdx, Long boardIdx, Long clubBoardReplyIdx, String replyContents) {
        User writer = userRepository.findById(1L).get();

        Optional<Club> findClub = clubRepository.findById(clubIdx);

        if(findClub.isEmpty() || findClub.get().getUseYn().equals("N")){
            System.out.println("해당 클럽은 존재하지 않습니다.");
            return null;
        }
        Optional<ClubBoard> findClubBoard = clubBoardRepository.findById(boardIdx);

        if(findClubBoard.isEmpty() || findClubBoard.get().getUseYn().equals("N")){
            System.out.println("해당 클럽 게시판은 존재하지 않습니다.");
            return null;
        }

        Optional<ClubBoardReply> findClubBoardReply = clubBoardReplyRepository.findById(clubBoardReplyIdx);
        if(findClubBoardReply.isEmpty()){
            System.out.println("해당 댓글은 존재하지 않습니다.");
            return null;
        }
        findClubBoardReply.get().updateClubBoardReply(replyContents);

        ClubBoardReplyResponseDTO res = new ClubBoardReplyResponseDTO(findClubBoardReply.get());

        return res;
    }

    @Override
    @Transactional
    public void deleteClubBoardReply(Long clubIdx, Long clubBoardIdx, Long clubBoardReplyIdx) {
        User writer = userRepository.findById(1L).get();

        Optional<Club> findClub = clubRepository.findById(clubIdx);

        if(findClub.isEmpty() || findClub.get().getUseYn().equals("N")){
            System.out.println("해당 클럽은 존재하지 않습니다.");
            return;
        }
        Optional<ClubBoard> findClubBoard = clubBoardRepository.findById(clubBoardIdx);

        if(findClubBoard.isEmpty() || findClubBoard.get().getUseYn().equals("N")){
            System.out.println("해당 클럽 게시판은 존재하지 않습니다.");
            return;
        }

        Optional<ClubBoardReply> findClubBoardReply = clubBoardReplyRepository.findById(clubBoardReplyIdx);
        if(findClubBoardReply.isEmpty()){
            System.out.println("해당 댓글은 존재하지 않습니다.");
            return;
        }
        if(writer.getUserIdx().equals(findClubBoardReply.get().getWriter().getUserIdx())){
            System.out.println("작성자만 삭제할 수 있습니다.");
            return;
        }

        clubBoardReplyRepository.deleteById(clubBoardReplyIdx);

    }

    @Override
    @Transactional
    public Page<ClubBoardReplyResponseDTO> getClubBoardReplyList(Long clubIdx, Long clubBoardIdx, int page,
        int size) {

        Optional<Club> findClub = clubRepository.findById(clubIdx);

        if(findClub.isEmpty() || findClub.get().getUseYn().equals("N")){
            System.out.println("해당 클럽은 존재하지 않습니다.");
            return null;
        }
        Optional<ClubBoard> findClubBoard = clubBoardRepository.findById(clubBoardIdx);

        if(findClubBoard.isEmpty() || findClubBoard.get().getUseYn().equals("N")){
            System.out.println("해당 클럽 게시판은 존재하지 않습니다.");
            return null;
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by("createDt"));
        Page<ClubBoardReply> findList = clubBoardReplyRepository.findByClubBoard(findClubBoard.get(), pageable);

        if(findList.isEmpty()){
            System.out.println ("해당 요청에 대한 클럽 리스트가 존재하지 않습니다.");
            return null;
        }
        Page<ClubBoardReplyResponseDTO> resList = findList.map(p -> new ClubBoardReplyResponseDTO(p));

        return resList;
    }
}
