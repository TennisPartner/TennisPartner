package com.tennisPartner.tennisP.clubBoard.service;

import com.tennisPartner.tennisP.club.domain.Club;
import com.tennisPartner.tennisP.club.repository.ClubJoinRepository;
import com.tennisPartner.tennisP.club.repository.ClubRepository;
import com.tennisPartner.tennisP.club.repository.dto.ClubRequestDTO;
import com.tennisPartner.tennisP.club.repository.dto.ClubResponseDTO;
import com.tennisPartner.tennisP.club.service.ClubService;
import com.tennisPartner.tennisP.clubBoard.domain.ClubBoard;
import com.tennisPartner.tennisP.clubBoard.domain.ClubBoardJoin;
import com.tennisPartner.tennisP.clubBoard.repository.ClubBoardJoinRepository;
import com.tennisPartner.tennisP.clubBoard.repository.ClubBoardRepository;
import com.tennisPartner.tennisP.clubBoard.repository.dto.ClubBoardJoinResponseDTO;
import com.tennisPartner.tennisP.clubBoard.repository.dto.ClubBoardRequestDTO;
import com.tennisPartner.tennisP.clubBoard.repository.dto.ClubBoardResponseDTO;
import com.tennisPartner.tennisP.user.domain.User;
import com.tennisPartner.tennisP.user.repository.JpaUserRepository;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

@SpringBootTest
public class ClubBoardServiceImplTest {

    @Autowired
    private ClubRepository clubRepository;
    @Autowired
    private ClubJoinRepository clubJoinRepository;
    @Autowired
    private ClubService clubService;
    @Autowired
    private ClubBoardService clubBoardService;
    @Autowired
    private ClubBoardRepository clubBoardRepository;
    @Autowired
    private ClubBoardJoinRepository clubBoardJoinRepository;
    @Autowired
    private PlatformTransactionManager transactionManager;
    @Autowired
    private JpaUserRepository userRepository;

    TransactionTemplate transactionTemplate;

    Club club;
    User user;

    ClubBoard clubBoard;
    Long clubIdx = 0L;
    Long clubBoardIdx = 0L;
    Long clubBoardJoinIdx = 0L;
    Long userIdx = 0L;



    @BeforeEach
    void init(){
        transactionTemplate = new TransactionTemplate(transactionManager);
        ClubRequestDTO clubDTO = ClubRequestDTO.builder()
            .clubName("테스트클럽1")
            .clubInfo("테스트")
            .clubCity("경기도")
            .clubCounty("성남시")
            .build();
        user = userRepository.findAll().stream().findFirst().get();

        userIdx = user.getUserIdx();

        ClubResponseDTO saveClub = clubService.createClub(clubDTO, userIdx);
        club = Club.builder()
            .clubIdx(saveClub.getClubIdx())
            .clubName(saveClub.getClubName())
            .clubInfo(saveClub.getClubInfo())
            .clubCity(saveClub.getClubCity())
            .clubCounty(saveClub.getClubCounty())
            .useYn(saveClub.getUseYn())
            .build();

        clubIdx = saveClub.getClubIdx();
        clubBoard = ClubBoard.builder()
            .clubBoardTitle("테스트 타이틀")
            .clubBoardContents("테스트 내용")
            .clubBoardType("T")
            .meetDt(LocalDateTime.now())
            .wantedCnt(10)
            .club(club)
            .writer(user)
            .useYn("Y")
            .build();
    }

    @AfterEach
    void delete(){
        clubRepository.deleteById(clubIdx);
        if(!clubBoardIdx.equals(0L)){
            clubBoardRepository.deleteById(clubBoardIdx);
        }
        if(!clubBoardJoinIdx.equals(0L)){
            clubBoardJoinRepository.deleteById(clubBoardJoinIdx);
        }

    }
    @Test
    public void 클럽게시판생성(){
        ClubBoardRequestDTO requestDTO = ClubBoardRequestDTO.builder()
            .clubBoardTitle("당신과는 천천히")
            .clubBoardContents("밤이라도 당신과 함께 순간만은 천천히")
            .clubBoardType("Y")
            .wantedCnt(10)
            .build();
        ClubBoardResponseDTO res = transactionTemplate.execute(status ->{
            ClubBoardResponseDTO clubBoard = clubBoardService.createClubBoard(clubIdx, requestDTO,1L);
            return clubBoard;
        });

        clubBoardIdx = res.getClubBoardIdx();
        Assertions.assertThat(clubIdx).isEqualTo(res.getClubIdx());
        Assertions.assertThat(requestDTO.getClubBoardTitle()).isEqualTo(res.getClubBoardTitle());
        Assertions.assertThat(requestDTO.getClubBoardContents()).isEqualTo(res.getClubBoardContents());
        Assertions.assertThat(requestDTO.getClubBoardType()).isEqualTo(requestDTO.getClubBoardType());
    }

    @Test
    public void 클럽게시판수정(){
        ClubBoardRequestDTO updateDTO = ClubBoardRequestDTO.builder()
            .clubBoardTitle("수정 테스트 타이틀")
            .clubBoardContents("수정 테스트 내용")
            .clubBoardType("Y")
            .wantedCnt(5)
            .useYn("Y")
            .build();

        ClubBoardResponseDTO res = transactionTemplate.execute(status ->{
            ClubBoard saveClubBoard = clubBoardRepository.save(clubBoard);
            ClubBoardResponseDTO responseDTO = clubBoardService.updateClubBoard(clubIdx,
                saveClubBoard.getClubBoardIdx(), updateDTO,userIdx);
            clubBoardIdx = responseDTO.getClubBoardIdx();
            return responseDTO;
        });

        Assertions.assertThat(clubIdx).isEqualTo(res.getClubIdx());
        Assertions.assertThat(updateDTO.getClubBoardTitle()).isEqualTo(res.getClubBoardTitle());
        Assertions.assertThat(updateDTO.getClubBoardContents()).isEqualTo(res.getClubBoardContents());
        Assertions.assertThat(updateDTO.getClubBoardType()).isEqualTo(res.getClubBoardType());

    }

    @Test
    public void 클럽조회(){

        ClubBoardResponseDTO res = transactionTemplate.execute(status -> {
            ClubBoard saveClubBoard = clubBoardRepository.save(clubBoard);
            ClubBoardResponseDTO responseDTO = clubBoardService.getClubBoard(clubIdx,
                saveClubBoard.getClubBoardIdx(),1L);
            clubBoardIdx = responseDTO.getClubBoardIdx();
            return responseDTO;
        });

        Assertions.assertThat(res.getClubBoardTitle()).isEqualTo(clubBoard.getClubBoardTitle());
        Assertions.assertThat(res.getClubBoardTitle()).isEqualTo(clubBoard.getClubBoardTitle());
        Assertions.assertThat(res.getClubBoardContents()).isEqualTo(clubBoard.getClubBoardContents());
        Assertions.assertThat(res.getClubBoardType()).isEqualTo(clubBoard.getClubBoardType());
    }

    @Test
    public void 클럽리스트조회(){
        ClubBoard req1 = ClubBoard.builder()
            .club(club)
            .clubBoardTitle("제목1")
            .clubBoardContents("내용1")
            .clubBoardType("M")
            .writer(user)
            .useYn("Y").build();
        ClubBoard req2 = ClubBoard.builder()
            .club(club)
            .clubBoardTitle("제목2")
            .clubBoardContents("내용2")
            .writer(user)
            .clubBoardType("M")
            .useYn("Y").build();
        ClubBoard req3 = ClubBoard.builder()
            .club(club)
            .clubBoardTitle("제목3")
            .clubBoardContents("내용3")
            .writer(user)
            .clubBoardType("M")
            .useYn("Y").build();
        ClubBoard req4 = ClubBoard.builder()
            .club(club)
            .clubBoardTitle("제목4")
            .clubBoardContents("내용4")
            .writer(user)
            .clubBoardType("M")
            .useYn("Y").build();
        ClubBoard req5 = ClubBoard.builder()
            .club(club)
            .clubBoardTitle("제목5")
            .clubBoardContents("내용5")
            .writer(user)
            .clubBoardType("M")
            .useYn("Y").build();

        List<ClubBoard> clubBoardList = Arrays.asList(req1, req2, req3, req4, req5);
        Page<ClubBoardResponseDTO> res =transactionTemplate.execute(status -> {
           List<ClubBoard> saveClubBoardList = clubBoardRepository.saveAll(clubBoardList);
           Page<ClubBoardResponseDTO> responseDTO = clubBoardService.getClubBoardList(clubIdx,0,5,1L);
           List<Long> clubBoardIdxs = saveClubBoardList.stream().map(ClubBoard :: getClubBoardIdx).collect(
                Collectors.toList());

            clubBoardRepository.deleteAllById(clubBoardIdxs);
           return responseDTO;
        });


        Assertions.assertThat(5).isEqualTo(res.getTotalElements());

    }

    @Test
    public void 모임참여(){
        ClubBoardJoinResponseDTO res =transactionTemplate.execute(status ->{
            ClubBoard saveBoard = clubBoardRepository.save(clubBoard);
            ClubBoardJoinResponseDTO responseDTO = clubBoardService.joinMatch(clubIdx,saveBoard.getClubBoardIdx(),userIdx);
            clubBoardJoinIdx = responseDTO.getClubBoardJoinIdx();
            return responseDTO;
        });
        ClubBoardJoin findJoin = clubBoardJoinRepository.findByUserAndClubBoard(user,clubBoard).get();
        Assertions.assertThat(clubBoardJoinIdx).isEqualTo(findJoin.getClubBoardJoinIdx());

    }

    @Test
    public void 모임탈퇴(){

        Long joinIdx = transactionTemplate.execute(status -> {
            ClubBoard saveBoard = clubBoardRepository.save(clubBoard);
            clubBoardIdx = saveBoard.getClubBoardIdx();
            ClubBoardJoinResponseDTO joinMatch = clubBoardService.joinMatch(clubIdx,clubBoardIdx,userIdx);
            clubBoardService.leaveMatch(clubIdx,clubBoardIdx,userIdx);

            return joinMatch.getClubBoardJoinIdx();
        });

        Assertions.assertThat(clubBoardJoinRepository.findById(joinIdx).isEmpty()).isEqualTo(true);
    }
}
