package com.tennisPartner.tennisP.clubBoard.service;

import com.tennisPartner.tennisP.club.domain.Club;
import com.tennisPartner.tennisP.club.repository.ClubJoinRepository;
import com.tennisPartner.tennisP.club.repository.ClubRepository;
import com.tennisPartner.tennisP.club.service.ClubService;
import com.tennisPartner.tennisP.clubBoard.domain.ClubBoard;
import com.tennisPartner.tennisP.clubBoard.repository.ClubBoardRepository;
import com.tennisPartner.tennisP.clubBoard.repository.dto.ClubBoardJoinResponseDTO;
import com.tennisPartner.tennisP.clubBoard.repository.dto.ClubBoardRequestDTO;
import com.tennisPartner.tennisP.clubBoard.repository.dto.ClubBoardResponseDTO;
import com.tennisPartner.tennisP.user.domain.User;
import java.awt.print.Pageable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class ClubBoardServiceImplTest {

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private ClubJoinRepository clubJoinRepository;

    @Autowired
    private ClubBoardService clubBoardService;

    @Autowired
    private ClubBoardRepository clubBoardRepository;

    private  Club club;
    private Long clubIdx;

    private User user;

    private ClubBoardRequestDTO requestDTO;
    @BeforeEach
    void init(){

        club = Club.builder()
            .clubName("테스트클럽")
            .clubInfo("테스트")
            .clubCity("경기도")
            .clubCounty("성남시")
            .build();
        user = User.builder()
            .userIdx(1L)
            .userName("테스트")
            .userNickname("테스트닉네임")
            .userGender("M")
            .build();
        Club saveClub = clubRepository.save(club);

        clubIdx = saveClub.getClubIdx();
        requestDTO = ClubBoardRequestDTO.builder()
            .clubBoardTitle("당신과는 천천히")
            .clubBoardContents("밤이라도 당신과 함께 순간만은 천천히")
            .clubBoardType('Y')
            .wantedCnt(10)
            .build();
    }
    @Test
    @Transactional
    public void 클럽게시판생성(){


        ClubBoardResponseDTO res = clubBoardService.createClubBoard(clubIdx, requestDTO);

        Assertions.assertThat(clubIdx).isEqualTo(res.getClubDTO().getClubIdx());
        Assertions.assertThat(requestDTO.getClubBoardTitle()).isEqualTo(res.getClubBoardTitle());
        Assertions.assertThat(requestDTO.getClubBoardContents()).isEqualTo(res.getClubBoardContents());
        Assertions.assertThat(requestDTO.getClubBoardType()).isEqualTo(requestDTO.getClubBoardType());
    }

    @Test
    @Transactional
    public void 클럽게시판수정(){
        ClubBoardRequestDTO updateDTO = ClubBoardRequestDTO.builder()
            .clubBoardTitle("수정 테스트 타이틀")
            .clubBoardContents("수정 테스트 내용")
            .clubBoardType('Y')
            .wantedCnt(5)
            .build();
        ClubBoard entity = requestDTO.dtoToClubBoardEntity(club, user);
        ClubBoard saveClubBoard = clubBoardRepository.save(entity);

        ClubBoardResponseDTO res = clubBoardService.updateClubBoard(clubIdx,
            saveClubBoard.getClubBoardIdx(), updateDTO);

        Assertions.assertThat(clubIdx).isEqualTo(res.getClubDTO().getClubIdx());
        Assertions.assertThat(updateDTO.getClubBoardTitle()).isEqualTo(res.getClubBoardTitle());
        Assertions.assertThat(updateDTO.getClubBoardContents()).isEqualTo(res.getClubBoardContents());
        Assertions.assertThat(updateDTO.getClubBoardType()).isEqualTo(requestDTO.getClubBoardType());

    }

    @Test
    @Transactional
    public void 클럽조회(){
        ClubBoardResponseDTO saveClubBoard = clubBoardService.createClubBoard(clubIdx, requestDTO);
        ClubBoardResponseDTO res = clubBoardService.getClubBoard(clubIdx,
            saveClubBoard.getClubBoardIdx());

        Assertions.assertThat(res.getClubBoardTitle()).isEqualTo(requestDTO.getClubBoardTitle());
        Assertions.assertThat(res.getClubBoardTitle()).isEqualTo(res.getClubBoardTitle());
        Assertions.assertThat(res.getClubBoardContents()).isEqualTo(requestDTO.getClubBoardContents());
        Assertions.assertThat(res.getClubBoardType()).isEqualTo(requestDTO.getClubBoardType());
    }

    @Test
    @Transactional
    public void 클럽리스트조회(){
        ClubBoard req1 = ClubBoard.builder()
            .club(club)
            .clubBoardTitle("제목1")
            .clubBoardContents("내용1")
            .writer(user)
            .useYn('Y').build();
        ClubBoard req2 = ClubBoard.builder()
            .club(club)
            .clubBoardTitle("제목2")
            .clubBoardContents("내용2")
            .writer(user)
            .useYn('Y').build();
        ClubBoard req3 = ClubBoard.builder()
            .club(club)
            .clubBoardTitle("제목3")
            .clubBoardContents("내용3")
            .writer(user)
            .useYn('Y').build();
        ClubBoard req4 = ClubBoard.builder()
            .club(club)
            .clubBoardTitle("제목4")
            .clubBoardContents("내용4")
            .writer(user)
            .useYn('Y').build();
        ClubBoard req5 = ClubBoard.builder()
            .club(club)
            .clubBoardTitle("제목5")
            .clubBoardContents("내용5")
            .writer(user)
            .useYn('Y').build();

        clubBoardRepository.save(req1);
        clubBoardRepository.save(req2);
        clubBoardRepository.save(req3);
        clubBoardRepository.save(req4);
        clubBoardRepository.save(req5);
        Page<ClubBoardResponseDTO> res = clubBoardService.getClubBoardList(clubIdx,0,5);

        Assertions.assertThat(5).isEqualTo(res.getTotalElements());

    }

    @Test
    @Transactional
    public void 모임참여(){
        ClubBoard req = ClubBoard.builder()
            .club(club)
            .clubBoardTitle("제목1")
            .clubBoardContents("내용1")
            .writer(user)
            .wantedCnt(10)
            .useYn('Y').build();

        ClubBoard saveBoard = clubBoardRepository.save(req);

        ClubBoardJoinResponseDTO res = clubBoardService.joinMatch(clubIdx,saveBoard.getClubBoardIdx());

        Assertions.assertThat(req.getClubBoardTitle()).isEqualTo(res.getClubBoardDTO().getClubBoardTitle());
        Assertions.assertThat(req.getClubBoardContents()).isEqualTo(res.getClubBoardDTO().getClubBoardContents());
    }

    @Test
    @Transactional
    public void 모임탈퇴(){
        ClubBoard req = ClubBoard.builder()
            .club(club)
            .clubBoardTitle("제목1")
            .clubBoardContents("내용1")
            .writer(user)
            .wantedCnt(10)
            .useYn('Y').build();

        ClubBoard saveBoard = clubBoardRepository.save(req);

        ClubBoardJoinResponseDTO joinMatch = clubBoardService.joinMatch(clubIdx,saveBoard.getClubBoardIdx());

        clubBoardService.leaveMatch(clubIdx,saveBoard.getClubBoardIdx());
        ClubBoard res = clubBoardRepository.findById(joinMatch.getClubBoardJoinIdx()).get();

        Assertions.assertThat(res).isNull();
    }
}
