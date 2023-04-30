package com.tennisPartner.tennisP.clubBoardReply.service;

import com.tennisPartner.tennisP.club.domain.Club;
import com.tennisPartner.tennisP.club.repository.ClubRepository;
import com.tennisPartner.tennisP.clubBoard.domain.ClubBoard;
import com.tennisPartner.tennisP.clubBoard.repository.ClubBoardRepository;
import com.tennisPartner.tennisP.clubBoard.repository.dto.ClubBoardRequestDTO;
import com.tennisPartner.tennisP.clubBoardReply.domain.ClubBoardReply;
import com.tennisPartner.tennisP.clubBoardReply.repository.ClubBoardReplyRepository;
import com.tennisPartner.tennisP.clubBoardReply.repository.dto.ClubBoardReplyResponseDTO;
import com.tennisPartner.tennisP.user.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

@SpringBootTest
public class ClubBoardReplyServiceImplTest {

    @Autowired
    private ClubBoardReplyService clubBoardReplyService;

    @Autowired
    private ClubBoardRepository clubBoardRepository;

    @Autowired
    private ClubRepository clubRepository;
    @Autowired
    private ClubBoardReplyRepository clubBoardReplyRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;
    private TransactionTemplate transactionTemplate;

    private Club club;
    private Long clubIdx;

    private User user;

    private ClubBoard clubBoard;

    private Long clubBoardIdx;

    private ClubBoardRequestDTO requestDTO;



    @BeforeEach
    void init() {
        transactionTemplate = new TransactionTemplate(transactionManager);

        club = Club.builder()
            .clubName("테스트 클럽")
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

        clubBoard = ClubBoard.builder()
            .clubBoardTitle("클럽보드 타이틀 테스트")
            .clubBoardContents("클럽보드 내용 테스트")
            .clubBoardType("T")
            .writer(user)
            .club(club)
            .useYn("Y")
            .build();

        clubIdx = saveClub.getClubIdx();

        ClubBoard saveClubBoard = clubBoardRepository.save(clubBoard);

        clubBoardIdx = saveClubBoard.getClubBoardIdx();

    }

    @AfterEach
    public void delete(){
        clubRepository.deleteById(clubIdx);
        clubBoardRepository.deleteById(clubBoardIdx);


    }
    @Test
    @Transactional
    public void 댓글작성테스트() {

        String replyContents = "댓글 추가 테스트";
        ClubBoardReplyResponseDTO res = clubBoardReplyService.createClubBoardReply(clubIdx, clubBoardIdx, replyContents);

        Assertions.assertThat(res.getReplyContents()).isEqualTo(replyContents);

        clubBoardReplyRepository.deleteById(res.getClubBoardReplyIdx());

    }
    @Test
    public void 댓글수정테스트() {
        //public ClubBoardReplyResponseDTO updateClubBoardReply(Long clubIdx, Long boardIdx, Long clubBoardReplyIdx, String replyContents) {
        String replyContents = "매일 우린 처음이니까";
        ClubBoardReplyResponseDTO res = transactionTemplate.execute(status ->{
            ClubBoardReply createReply = ClubBoardReply.builder()
                .writer(user)
                .replyContents("댓글 추가")
                .clubBoard(clubBoard)
                .build();

            Long findReplyId = clubBoardReplyRepository.save(createReply).getClubBoardReplyIdx();

            ClubBoardReplyResponseDTO updateReply = clubBoardReplyService.updateClubBoardReply(clubIdx, clubBoardIdx, findReplyId, replyContents);

            return updateReply;
            });


        Assertions.assertThat(replyContents).isEqualTo(res.getReplyContents());

        clubBoardReplyRepository.deleteById(res.getClubBoardReplyIdx());
    }

    @Test
    public void 댓글삭제테스트(){

        transactionTemplate.execute(status -> {
            ClubBoardReply createReply = ClubBoardReply.builder()
                .writer(user)
                .replyContents("댓글 추가")
                .clubBoard(clubBoard)
                .build();

            Long findReplyId = clubBoardReplyRepository.save(createReply).getClubBoardReplyIdx();
            System.out.println(findReplyId);

            clubBoardReplyService.deleteClubBoardReply(clubIdx, clubBoardIdx, findReplyId);

            Assertions.assertThat(clubBoardReplyRepository.findById(findReplyId)).isEmpty();

            return null;
        });

    }

    @Test
    public void 댓글조회테스트(){
        ClubBoardReply reply1 = ClubBoardReply.builder()
            .writer(user)
            .replyContents("댓글1")
            .clubBoard(clubBoard).build();
        ClubBoardReply reply2 = ClubBoardReply.builder()
            .writer(user)
            .replyContents("댓글2")
            .clubBoard(clubBoard).build();
        ClubBoardReply reply3 = ClubBoardReply.builder()
            .writer(user)
            .replyContents("댓글3")
            .clubBoard(clubBoard).build();
        ClubBoardReply reply4 = ClubBoardReply.builder()
            .writer(user)
            .replyContents("댓글4")
            .clubBoard(clubBoard).build();
        ClubBoardReply reply5 = ClubBoardReply.builder()
            .writer(user)
            .replyContents("댓글5")
            .clubBoard(clubBoard).build();

        Page<ClubBoardReplyResponseDTO> res =  transactionTemplate.execute(status -> {
            clubBoardReplyRepository.save(reply1);
            clubBoardReplyRepository.save(reply2);
            clubBoardReplyRepository.save(reply3);
            clubBoardReplyRepository.save(reply4);
            clubBoardReplyRepository.save(reply5);
            Page<ClubBoardReplyResponseDTO> getList = clubBoardReplyService.getClubBoardReplyList(clubIdx, clubBoardIdx,0,5);
            return getList;
        });
        Assertions.assertThat(5).isEqualTo(res.getTotalElements());

    }
}
