package com.tennisPartner.tennisP.authMatch.service;


import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.either;
import static org.junit.Assert.assertThat;
import com.tennisPartner.tennisP.AuthMatch.domain.AuthGame;
import com.tennisPartner.tennisP.AuthMatch.domain.AuthMatch;
import com.tennisPartner.tennisP.AuthMatch.repository.AuthGameRepository;
import com.tennisPartner.tennisP.AuthMatch.repository.AuthMatchRepository;
import com.tennisPartner.tennisP.AuthMatch.repository.dto.AuthGameResponseDTO;
import com.tennisPartner.tennisP.AuthMatch.repository.dto.AuthMatchRequestDTO;
import com.tennisPartner.tennisP.AuthMatch.repository.dto.AuthMatchResponseDTO;
import com.tennisPartner.tennisP.AuthMatch.repository.dto.MatchResultResponseDTO;
import com.tennisPartner.tennisP.AuthMatch.repository.dto.UpdateAuthGameRequestDTO;
import com.tennisPartner.tennisP.AuthMatch.service.AuthMatchService;
import com.tennisPartner.tennisP.AuthMatch.service.AuthMatchServiceImpl;
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
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import org.assertj.core.api.Assertions;

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
    AuthMatchServiceImpl authMatchServiceImpl;
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
    ClubBoard clubBoard;
    List<AuthGame> authGames = new ArrayList<>();
    List<Long> authGameIdxs = new ArrayList<>();
    User loginUser;
    @BeforeEach
    void init() {
            transactionTemplate = new TransactionTemplate(transactionManager);
            loginUser = User.builder()
                .userId("로그인")
                .userPassword("1234")
                .userName("로그인")
                .userGender("M")
                .userGrade(UserGrade.COMMON)
                .useYn("Y")
                .userPhotoPath("string")
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
                    .userPhotoPath("string")
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
                .wantedCnt(4)
                .build();

            clubBoard = clubBoardRepository.save(clubBoardEntity);
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
        if(!(authMatchIdx == null)){
            authGames = authGameRepository.findByAuthMatch(authMatch).get();
            authGameRepository.deleteAll(authGames);
        }
       if(!(userIdx == null))
           userRepository.deleteById(userIdx);
       if(!userList.isEmpty())
           userRepository.deleteAll(userList);
       if(!(clubIdx == null))
           clubRepository.deleteById(clubIdx);
        if(!(clubBoardIdx == null))
            clubBoardRepository.deleteById(clubBoardIdx);

    }


    @Test
    void 매칭테스트(){

        AuthMatchRequestDTO req = AuthMatchRequestDTO.builder()
            .courtCnt(1)
            .gameCnt(4)
            .matchType("D")
            .build();


        AuthMatchResponseDTO res = transactionTemplate.execute(status -> {
            AuthMatchResponseDTO authMatchRes = authMatchService.createMatch(userIdx, clubIdx, clubBoardIdx, req);
            authMatchIdx = authMatchRes.getAuthMatchIdx();
            authMatch = authMatchRepository.findById(authMatchIdx).get();

            return authMatchRes;
            });

        List<ClubBoardJoin> joinList = clubBoardJoinRepository.findByClubBoard(clubBoard).get();

        Assertions.assertThat(res.getGameCnt()).isEqualTo(req.getGameCnt());
        Assertions.assertThat(res.getCourtCnt()).isEqualTo(req.getCourtCnt());
        Assertions.assertThat(res.getMatchType()).isEqualTo(req.getMatchType());
        Assertions.assertThat(res.getUserCnt()).isEqualTo(joinList.size());

    }
    @Test
    void 게임생성테스트(){

//        private List<List<User>> matchingGame(AuthMatchRequestDTO req, List<User> joinList,
//            AuthMatch saveAuthMatch) {
        AuthMatchRequestDTO req = AuthMatchRequestDTO.builder()
            .courtCnt(1)
            .gameCnt(4)
            .matchType("D")
            .build();

        AuthMatch authMatchBuild = AuthMatch.builder()
            .owner(loginUser)
            .clubBoard(clubBoard)
            .courtCnt(req.getCourtCnt())
            .gameCnt(req.getGameCnt())
            .matchType(req.getMatchType())
            .userCnt(userList.size())
            .build();

        authMatch = authMatchRepository.save(authMatchBuild);
        authMatchIdx = authMatch.getAuthMatchIdx();
        List<List<User>> res = authMatchServiceImpl.matchingGame(req, userList, authMatch);

        Map<Long, Integer> userMap = new HashMap<>();
        for(List<User> list : res){
            for(User u : list){
                for(User join : userList){
                    if(u.getUserIdx().equals(join.getUserIdx())){
                        userMap.put(join.getUserIdx(), userMap.getOrDefault(join.getUserIdx(),0)+1);
                    }
                }
            }
        }
        int gameCnt = req.getGameCnt() * 4 / userList.size();
        for(Long key : userMap.keySet()){
            assertThat(userMap.get(key), either(comparesEqualTo(gameCnt)).or(comparesEqualTo(gameCnt+1)));
        }
    }

    @Test
    void 게임수정테스트(){
//        public AuthGameResponseDTO updateGame(Long userIdx, Long clubIdx, Long clubBoardIdx, Long authMatchIdx,
//            Long authGameIdx, UpdateAuthGameRequestDTO req)

        AuthMatchRequestDTO AuthMatchReq = AuthMatchRequestDTO.builder()
            .courtCnt(1)
            .gameCnt(4)
            .matchType("D")
            .build();
        UpdateAuthGameRequestDTO req = UpdateAuthGameRequestDTO.builder()
                .host1Idx(userList.get(0).getUserIdx())
                .host2Idx(userList.get(1).getUserIdx())
                .guest1Idx(userList.get(2).getUserIdx())
                .guest2Idx(userList.get(3).getUserIdx())
                .hostScore(6)
                .guestScore(3)
                .build();



        AuthGameResponseDTO res = transactionTemplate.execute(status -> {
            AuthMatchResponseDTO authMatchResult = authMatchService.createMatch(userIdx, clubIdx, clubBoardIdx, AuthMatchReq);
            authMatch = authMatchRepository.findById(authMatchResult.getAuthMatchIdx()).get();
            Optional<List<AuthGame>> findGameList = authGameRepository.findByAuthMatch(authMatch);
            List<AuthGame> gameList = findGameList.get();
            AuthGame firstGame = gameList.get(0);
            authMatchIdx = authMatch.getAuthMatchIdx();
            System.out.println("authMatchIdx------------- : " + authMatch.getAuthMatchIdx());
            AuthGameResponseDTO responseDTO = authMatchService.updateGame(userIdx, clubIdx, clubBoardIdx, authMatchIdx,
                firstGame.getAuthGameIdx(),req);
            return responseDTO;
        });

        Assertions.assertThat(res.getHost1().getUserIdx()).isEqualTo(req.getHost1Idx());
        Assertions.assertThat(res.getHost2().getUserIdx()).isEqualTo(req.getHost2Idx());
        Assertions.assertThat(res.getGuest1().getUserIdx()).isEqualTo(req.getGuest1Idx());
        Assertions.assertThat(res.getGuest2().getUserIdx()).isEqualTo(req.getGuest2Idx());
        Assertions.assertThat(res.getHostScore()).isEqualTo(req.getHostScore());
        Assertions.assertThat(res.getGuestScore()).isEqualTo(req.getGuestScore());
    }
    @Test
    void 게임조회(){
//        public List<AuthGameResponseDTO> getGame(Long userIdx, Long clubIdx, Long clubBoardIdx,
//            Long authMatchIdx)
        AuthMatchRequestDTO requestDTO = AuthMatchRequestDTO
            .builder()
            .courtCnt(1)
            .gameCnt(4)
            .matchType("D")
            .build();

        List<AuthGameResponseDTO> resList = transactionTemplate.execute(status ->{
            AuthMatchResponseDTO createAuthMatch = authMatchService.createMatch(userIdx,clubIdx,clubBoardIdx,requestDTO);
            authMatchIdx = createAuthMatch.getAuthMatchIdx();

            List<AuthGameResponseDTO> res = authMatchService.getGame(userIdx, clubIdx, clubBoardIdx, authMatchIdx);
            return res;
        });
        for(AuthGameResponseDTO res : resList){
                assertThat(res.getHost1().getUserIdx(), either(comparesEqualTo(userList.get(0).getUserIdx()))
                    .or(comparesEqualTo(userList.get(1).getUserIdx())).or(comparesEqualTo(userList.get(2).getUserIdx()))
                    .or(comparesEqualTo(userList.get(3).getUserIdx())));
        }

    }

    @Test
    void 게임결과조회(){
//        public List<MatchResultResponseDTO> getResult(Long userIdx, Long clubIdx, Long clubBoardIdx,
//            Long authMatchIdx)

        AuthMatchRequestDTO requestDTO = AuthMatchRequestDTO
            .builder()
            .courtCnt(1)
            .gameCnt(4)
            .matchType("D")
            .build();

        int [] hostScoreArr = {6, 3, 1, 3};
        int [] guestScoreArr = {3, 6, 6, 6};
        List<MatchResultResponseDTO> res = transactionTemplate.execute(status -> {
            AuthMatchResponseDTO authMatchRes = authMatchService.createMatch(userIdx, clubIdx, clubBoardIdx, requestDTO);
            authMatchIdx = authMatchRes.getAuthMatchIdx();
            authMatch = authMatchRepository.findById(authMatchIdx).get();
            Optional<List<AuthGame>> findAuthGame = authGameRepository.findByAuthMatch(authMatch);
            List<AuthGame> authGameList = findAuthGame.get();
            List<AuthGameResponseDTO> updateGame = new ArrayList<>();

            for(AuthGame game : authGameList){
                int i =0;
                UpdateAuthGameRequestDTO update = UpdateAuthGameRequestDTO.builder()
                    .host1Idx(game.getHost1().getUserIdx())
                    .host2Idx(game.getHost2().getUserIdx())
                    .guest1Idx(game.getGuest1().getUserIdx())
                    .guest2Idx(game.getGuest2().getUserIdx())
                    .hostScore(hostScoreArr[i])
                    .guestScore(guestScoreArr[i])
                    .build();
               authMatchService.updateGame(userIdx,clubIdx,clubBoardIdx,authMatchIdx,game.getAuthGameIdx(),update);
            }
            List<MatchResultResponseDTO> result = authMatchService.getResult(userIdx, clubIdx, clubBoardIdx, authMatchIdx);
            return result;
        });
        MatchResultResponseDTO first =  res.stream().max(Comparator.comparing(
            MatchResultResponseDTO::getWinCnt)).orElseThrow(NoSuchElementException::new);

        Assertions.assertThat(res.get(0).getUser().getUserName()).isEqualTo(first.getUser().getUserName());
    }

    @Test
    void 매칭삭제(){
        AuthMatchRequestDTO requestDTO = AuthMatchRequestDTO
            .builder()
            .courtCnt(1)
            .gameCnt(4)
            .matchType("D")
            .build();

        transactionTemplate.executeWithoutResult(status -> {
            AuthMatchResponseDTO authMatchRes = authMatchService.createMatch(userIdx, clubIdx, clubBoardIdx, requestDTO);
            authMatchIdx = authMatchRes.getAuthMatchIdx();
            authMatch = authMatchRepository.findById(authMatchIdx).get();
            List<AuthGame> authGameList = authGameRepository.findByAuthMatch(authMatch).get();
            for(AuthGame authGame : authGameList){
                authGameIdxs.add(authGame.getAuthGameIdx());
            }
            authMatchService.deleteMatch(userIdx, clubIdx, clubBoardIdx, authMatchIdx);

        });
        Optional<AuthMatch> findMatch = authMatchRepository.findById(authMatchIdx);
        Assertions.assertThat(findMatch).isEmpty();
        for(Long idx : authGameIdxs){
            Optional<AuthGame> findGame = authGameRepository.findById(idx);
            Assertions.assertThat(findGame).isEmpty();
        }



    }
}
