package com.tennisPartner.tennisP.authMatch.service;

import com.tennisPartner.tennisP.AuthMatch.domain.AuthGame;
import com.tennisPartner.tennisP.AuthMatch.domain.AuthMatch;
import com.tennisPartner.tennisP.AuthMatch.repository.AuthGameRepository;
import com.tennisPartner.tennisP.AuthMatch.repository.AuthMatchRepository;
import com.tennisPartner.tennisP.AuthMatch.repository.dto.AuthMatchRequestDTO;
import com.tennisPartner.tennisP.AuthMatch.repository.dto.AuthMatchResponseDTO;
import com.tennisPartner.tennisP.AuthMatch.service.AuthMatchService;
import com.tennisPartner.tennisP.club.domain.Club;
import com.tennisPartner.tennisP.club.domain.ClubJoin;
import com.tennisPartner.tennisP.club.repository.ClubJoinRepository;
import com.tennisPartner.tennisP.club.repository.ClubRepository;
import com.tennisPartner.tennisP.clubBoard.domain.ClubBoard;
import com.tennisPartner.tennisP.clubBoard.domain.ClubBoardJoin;
import com.tennisPartner.tennisP.clubBoard.repository.ClubBoardJoinRepository;
import com.tennisPartner.tennisP.clubBoard.repository.ClubBoardRepository;
import com.tennisPartner.tennisP.user.UserGrade;
import com.tennisPartner.tennisP.user.domain.User;
import com.tennisPartner.tennisP.user.repository.JpaUserRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

@SpringBootTest
public class AuthMatchServiceImplTest {

    @Autowired
    AuthGameRepository authGameRepository;
    @Autowired
    AuthMatchRepository authMatchRepository;
    @Autowired
    ClubBoardRepository clubBoardRepository;
    @Autowired
    ClubBoardJoinRepository clubBoardJoinRepository;
    @Autowired
    JpaUserRepository userRepository;
    @Autowired
    ClubRepository clubRepository;
    @Autowired
    AuthMatchService authMatchService;

    @Autowired
    ClubJoinRepository clubJoinRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;
    private TransactionTemplate transactionTemplate;

    List<User> userList = new ArrayList<>();
    Long userIdx;

    Club club;

    User user;

    Long clubIdx;
    Long clubBoardIdx;

    Long authMatchIdx;
    List<Long> clubJoinIdxs = new ArrayList<>();
    AuthMatch authMatch;
    List<AuthGame> authGames;

    @BeforeEach
    void init() {

            User loginUser = User.builder()
                .userId("로그인")
                .userPassword("1234")
                .userName("로그인")
                .userGender("M")
                .userGrade(UserGrade.COMMON)
                .useYn("Y")
                .build();

            User saveLoginUser = userRepository.save(loginUser);
            userIdx = saveLoginUser.getUserIdx();

            Club clubEntity = Club.builder()
                .clubName("테스트 클럽")
                .clubInfo("테스트")
                .clubCity("경기도")
                .clubCounty("성남시")
                .build();

            Club club = clubRepository.save(clubEntity);
            for(int i=0; i<4; i++){
                User user = User.builder()
                    .userId("Test"+i)
                    .userName("TestName" + i)
                    .userGender("M")
                    .userNtrp(2.5)
                    .userPassword("1234")
                    .userGrade(UserGrade.COMMON)
                    .useYn("Y")
                    .build();
                userRepository.save(user);
                userList.add(user);
            }

            ClubBoard clubBoardEntity = ClubBoard.builder()
                .club(club)
                .clubBoardTitle("클럽 보드 제목")
                .writer(loginUser)
                .clubBoardContents("내용")
                .clubBoardType("M")
                .useYn("Y")
                .build();

            ClubBoard clubBoard = clubBoardRepository.save(clubBoardEntity);
            clubIdx = club.getClubIdx();

            clubBoardIdx = clubBoard.getClubBoardIdx();

            for(User u : userList){
                ClubJoin clubJoin = ClubJoin.builder()
                    .club(club)
                    .user(u)
                    .clubGrade("Common")
                    .build();

                ClubJoin saveClubJoin = clubJoinRepository.save(clubJoin);

                clubJoinIdxs.add(saveClubJoin.getClubJoinIdx());

                ClubBoardJoin clubBoardJoin = ClubBoardJoin.builder()
                    .clubBoard(clubBoard)
                    .user(u)
                    .build();

                clubBoardJoinRepository.save(clubBoardJoin);
            }


    }

    @AfterEach
    public void delete(){

            if(!(userIdx == null))
                userRepository.deleteById(userIdx);
            if(!userList.isEmpty())
                userRepository.deleteAll(userList);
            if(!(clubIdx == null))
                clubRepository.deleteById(clubIdx);
        if(!(clubBoardIdx == null))
            clubBoardRepository.deleteById(clubBoardIdx);
            if(!(authMatchIdx == null)){
               //authMatchRepository.deleteById(authMatchIdx);
                authGameRepository.deleteAll(authGames);
            }
        }




    @Test
    void 매칭테스트(){
//        public AuthMatchResponseDTO createMatch(Long userIdx, Long clubIdx, Long clubBoardIdx,
//            AuthMatchRequestDTO req)

        transactionTemplate = new TransactionTemplate(transactionManager);
        AuthMatchRequestDTO req = AuthMatchRequestDTO.builder()
            .courtCnt(1)
            .gameCnt(4)
            .matchType("D")
            .build();

        transactionTemplate.executeWithoutResult(status -> {
            AuthMatchResponseDTO res = authMatchService.createMatch(userIdx, clubIdx, clubBoardIdx, req);
            authMatchIdx = res.getAuthMatchIdx();
            AuthMatch authMatch = authMatchRepository.findById(authMatchIdx).get();
            authGames = authGameRepository.findByAuthMatch(authMatch);
            });

        System.out.println(authMatchIdx);



    }
}
