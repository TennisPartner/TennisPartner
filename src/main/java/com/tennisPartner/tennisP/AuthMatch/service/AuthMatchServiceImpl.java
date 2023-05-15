package com.tennisPartner.tennisP.AuthMatch.service;

import com.tennisPartner.tennisP.AuthMatch.domain.AuthGame;
import com.tennisPartner.tennisP.AuthMatch.domain.AuthMatch;
import com.tennisPartner.tennisP.AuthMatch.repository.AuthGameRepository;
import com.tennisPartner.tennisP.AuthMatch.repository.AuthMatchRepository;
import com.tennisPartner.tennisP.AuthMatch.repository.dto.PlayCountResponseDTO;
import com.tennisPartner.tennisP.AuthMatch.repository.dto.UpdateAuthGameRequestDTO;
import com.tennisPartner.tennisP.AuthMatch.repository.dto.AuthGameResponseDTO;
import com.tennisPartner.tennisP.AuthMatch.repository.dto.AuthMatchRequestDTO;
import com.tennisPartner.tennisP.AuthMatch.repository.dto.AuthMatchResponseDTO;
import com.tennisPartner.tennisP.AuthMatch.repository.dto.MatchResultResponseDTO;
import com.tennisPartner.tennisP.AuthMatch.repository.selectColumn.GuestScore;
import com.tennisPartner.tennisP.AuthMatch.repository.selectColumn.HostScore;
import com.tennisPartner.tennisP.club.domain.Club;
import com.tennisPartner.tennisP.club.repository.ClubRepository;
import com.tennisPartner.tennisP.clubBoard.domain.ClubBoard;
import com.tennisPartner.tennisP.clubBoard.domain.ClubBoardJoin;
import com.tennisPartner.tennisP.clubBoard.repository.ClubBoardJoinRepository;
import com.tennisPartner.tennisP.clubBoard.repository.ClubBoardRepository;
import com.tennisPartner.tennisP.common.Exception.CustomException;
import com.tennisPartner.tennisP.user.domain.User;
import com.tennisPartner.tennisP.user.repository.JpaUserRepository;
import com.tennisPartner.tennisP.user.repository.dto.GetUserResponseDto;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthMatchServiceImpl implements AuthMatchService {

    AuthGameRepository authGameRepository;
    AuthMatchRepository authMatchRepository;
    ClubBoardRepository clubBoardRepository;
    ClubBoardJoinRepository clubBoardJoinRepository;
    JpaUserRepository userRepository;
    ClubRepository clubRepository;

    @Autowired
    public AuthMatchServiceImpl(AuthGameRepository authGameRepository,
        AuthMatchRepository authMatchRepository,
        ClubBoardRepository clubBoardRepository, ClubBoardJoinRepository clubBoardJoinRepository,
        JpaUserRepository userRepository, ClubRepository clubRepository) {
        this.authGameRepository = authGameRepository;
        this.authMatchRepository = authMatchRepository;
        this.clubBoardRepository = clubBoardRepository;
        this.clubBoardJoinRepository = clubBoardJoinRepository;
        this.userRepository = userRepository;
        this.clubRepository = clubRepository;
    }

    @Override
    @Transactional
    public AuthMatchResponseDTO createMatch(Long userIdx, Long clubIdx, Long clubBoardIdx,
        AuthMatchRequestDTO req) {
        User user = userRepository.findById(userIdx).get();
        Optional<ClubBoard> findClubBoard = clubBoardRepository.findById(clubBoardIdx);
        ClubBoard clubBoard = findClubBoard.get();
        Optional<List<ClubBoardJoin>> findClubBoardJoinList = clubBoardJoinRepository.findByClubBoard(
            clubBoard);

        List<User> joinList = findClubBoardJoinList.get().stream().map(join -> join.getUser())
            .collect(
                Collectors.toList());

        int gameCnt = req.getGameCnt();
        int courtCnt = req.getCourtCnt();
        int playerCnt = joinList.size();

        if (playerCnt < 4) {
            throw new CustomException("4명 이상 모여야 합니다.", 601);
        }
        if (gameCnt * 4 < playerCnt) {
            throw new CustomException("1게임도 못하는 사람이 있습니다. 게임수를 증가시키거나, 인원수를 줄여주세요.", 602);
        }


        if (courtCnt * 4 > playerCnt) {
            courtCnt = courtCnt - (courtCnt - playerCnt / 4);
            req.setCourtCnt(courtCnt);
            System.out.println("코트 수를 " + courtCnt + "로 감소함");
        }

        AuthMatch authMatch = AuthMatch.builder()
            .owner(user)
            .clubBoard(clubBoard)
            .matchType(req.getMatchType())
            .courtCnt(req.getCourtCnt())
            .gameCnt(req.getGameCnt())
            .userCnt(joinList.size())
            .build();

        AuthMatch saveAuthMatch = authMatchRepository.save(authMatch);

        AuthMatchResponseDTO res = new AuthMatchResponseDTO(saveAuthMatch);

        List<List<User>> game = matchingGame(req, joinList, saveAuthMatch);

        return res;
    }

    private List<List<User>> matchingGame(AuthMatchRequestDTO req, List<User> joinList,
        AuthMatch saveAuthMatch) {

        int gameCnt = req.getGameCnt();
        int courtCnt = req.getCourtCnt();
        int playerCnt = joinList.size();

        // 매칭된 게임 전체 리스트
        List<List<User>> totalList = new ArrayList<>();
        // 모든 게임에 대한 임시 Integer 리스트
        List<Integer> gamerNumList = new ArrayList<>();
        // 한 번에 진행되는 게임당 참가하는 인원 리스트( ex)1코트 : 4명, 2코트 : 8명) -> 2코트일 경우 1번 코트와 2번 코트에서 경기하는 사람이 겹치면 안되기 때문
        List<Integer> gameList;
        //4명씩 경기함에 따라 gameCnt*4번의 인원 할당이 필요함
        int totalGamer = gameCnt * 4;
        //전체 인원이 공정하게 게임 기회를 갖을 수 있도록 전체 할당되는 수를 플레이어 수로 나눈 나머지를 모든 게임에 대한 인원을 넣어줌(임시 Integer)
        for (int i = 0; i < totalGamer; i++) {
            gamerNumList.add(i % playerCnt);
        }

        // 한 번에 여러 코트에서 게임이 진행되기 때문에 gameCnt/courtCnt를 하였으며, 나머지가 있는 경우를 대비해 +1을 추가함
        for (int i = 0; i < gameCnt / courtCnt + 1; i++) {
            // 실제 유저 정보를 담는 리스트
            List<User> gamerList = new ArrayList<>();
            gameList = new ArrayList<>();
            //각 게임 리스트에 코트수*4 만큼 인원을 넣어줌
            for (int j = 0; j < 4 * courtCnt; j++) {
                try {
                    gameList.add(gamerNumList.get(4 * courtCnt * i + j));

                    // 나머지가 있는 경우 아래의 Exception이 발생하며 이럴 경우 break하게 됨 -> 인원수에 맞게 매칭이 진행됨
                } catch (IndexOutOfBoundsException e) {
                    break;
                }

            }
            //임시 Integer로 넣은값에 따라 index를 이용해 실제 유저 정보를 추가함
            for (int j = 0; j < gameList.size(); j++) {
                gamerList.add(joinList.get(gameList.get(j)));
            }
            //유저 정보가 담긴 매칭 데이터를 전체 리스트에 하나씩 추가함.
            if (!gameList.isEmpty()) {
                totalList.add(gamerList);
            }
        }
        // 각 매칭 게임 데이터를 shuffle함으로써 임의로 변동되도록 함. 1,2,3,4,5,6,7,8 -> (예)3,4,1,5,2,6,7,8
        for (int i = 0; i < totalList.size(); i++) {
            Collections.shuffle(totalList.get(i));
        }
        // 매칭에 대한 내용을 DB에 저장함.
        for (List<User> set : totalList) {
            int court = 1;
            for (int i = 0; i < set.size() / 4; i++) {
                User[] setUser = new User[4];
                for (int j = 0; j < 4; j++) {
                    setUser[j] = set.get(4 * i + j);
                }
                AuthGame authGame = AuthGame.builder()
                    .authMatch(saveAuthMatch)
                    .host1(setUser[0])
                    .host2(setUser[1])
                    .guest1(setUser[2])
                    .guest2(setUser[3])
                    .courtSequence(court)
                    .guestScore(0)
                    .hostScore(0)
                    .build();

                AuthGame saveAuthGame = authGameRepository.save(authGame);
                court++;

            }

        }

        return totalList;

    }

    @Override
    @Transactional
    public List<PlayCountResponseDTO> getGameCnt(Long userIdx, Long clubIdx, Long clubBoardIdx, Long authMatchIdx) {
        Optional<User> findUser = userRepository.findById(userIdx);
        Optional<Club> findClub = clubRepository.findById(clubIdx);
        Optional<ClubBoard> findClubBoard = clubBoardRepository.findById(clubBoardIdx);
        ClubBoard clubBoard = findClubBoard.get();
        Optional<List<ClubBoardJoin>> findClubBoardJoin = clubBoardJoinRepository.findByClubBoard(
            clubBoard);
        Optional<AuthMatch> findAuthMatch = authMatchRepository.findById(authMatchIdx);
        AuthMatch authMatch = findAuthMatch.get();
        if(findCheck(findUser, findClub, findClubBoard, findClubBoardJoin, findAuthMatch)){

        }

        List<ClubBoardJoin> clubBoardJoinList = findClubBoardJoin.get();
        List<User> joinUser = clubBoardJoinList.stream().map(join -> join.getUser()).collect(
            Collectors.toList());
        List<PlayCountResponseDTO> resList = new ArrayList<>();
        for (User u : joinUser) {
            int cnt = authGameRepository.findUserGameCount(authMatch,u);
            resList.add(new PlayCountResponseDTO(new GetUserResponseDto(u), cnt));
        }

        return resList;
    }

    @Override
    @Transactional
    public List<AuthGameResponseDTO> getGame(Long userIdx, Long clubIdx, Long clubBoardIdx,
        Long authMatchIdx) {
        Optional<AuthMatch> findAuthMatch = authMatchRepository.findById(authMatchIdx);
        if (findAuthMatch.isEmpty()) {
            throw new CustomException("해당 요청에 대한 게임이 존재하지 않음", 600);
        }
        AuthMatch authMatch = findAuthMatch.get();
        List<AuthGame> gameList = authGameRepository.findByAuthMatch(authMatch);
        List<AuthGameResponseDTO> res = gameList.stream().map(game -> new AuthGameResponseDTO(game))
            .collect(
                Collectors.toList());

        return res;

    }

    @Override
    @Transactional
    public AuthGameResponseDTO updateGame(Long userIdx, Long clubIdx, Long clubBoardIdx, Long authMatchIdx,
        Long authGameIdx,
        UpdateAuthGameRequestDTO req) {
        Optional<AuthGame> findAuthGame = authGameRepository.findById(authGameIdx);
        if (findAuthGame.isEmpty()) {
            throw new CustomException("해당 게임이 없음", 600);
        }
        Optional<User> findHost1 = userRepository.findById(req.getHost1_idx());
        Optional<User> findHost2 = userRepository.findById(req.getHost2_idx());
        Optional<User> findGuest1 = userRepository.findById(req.getGuest1_idx());
        Optional<User> findGuest2 = userRepository.findById(req.getGuest2_idx());

        if (findHost1.isEmpty() || findHost2.isEmpty() || findGuest1.isEmpty()
            || findGuest2.isEmpty()) {
            throw new CustomException("해당 유저가 없습니다.", 100);
        }
        User host1 = findHost1.get();
        User host2 = findHost2.get();
        User guest1 = findGuest1.get();
        User guest2 = findGuest2.get();

        AuthGame updateReq = AuthGame.builder()
            .host1(host1)
            .host2(host2)
            .guest1(guest1)
            .guest2(guest2)
            .hostScore(req.getHost_score())
            .guestScore(req.getGuest_score())
            .build();

        AuthGame authGame = findAuthGame.get();
        authGame.updateAuthGame(updateReq);
        AuthGameResponseDTO res = new AuthGameResponseDTO(authGame);
        return res;
    }


    @Override
    @Transactional
    public List<MatchResultResponseDTO> getResult(Long userIdx, Long clubIdx, Long clubBoardIdx,
        Long authMatchIdx) {
        Optional<ClubBoard> findClubBoard = clubBoardRepository.findById(clubBoardIdx);
        if (findClubBoard.isEmpty() || findClubBoard.get().getUseYn().equals("N")
            || findClubBoard.get().getWantedCnt() == 0) {
            throw new CustomException("해당 게시글이 존재하지 않거나 모임 게시판이 아닙니다.", 300);
        }
        ClubBoard clubBoard = findClubBoard.get();
        Optional<List<ClubBoardJoin>> findClubBoardJoin = clubBoardJoinRepository.findByClubBoard(
            clubBoard);
        if (findClubBoardJoin.isEmpty()) {
            throw new CustomException("모임에 참가한 사람이 없습니다.", 600);
        }
        List<ClubBoardJoin> clubBoardJoinList = findClubBoardJoin.get();
        List<User> joinUser = clubBoardJoinList.stream().map(join -> join.getUser()).collect(
            Collectors.toList());
        Optional<AuthMatch> findAuthMatch = authMatchRepository.findById(authMatchIdx);
        AuthMatch authMatch = findAuthMatch.get();
        List<MatchResultResponseDTO> res = new ArrayList<>();
        for (User u : joinUser) {
            List<HostScore> hostScores = authGameRepository.findHostScore(authMatch, u);
            List<GuestScore> guestScores = authGameRepository.findGuestScore(authMatch, u);
            int hostSum = 0;
            int guestSum = 0;
            int winCnt = 0;
            int loseCnt = 0;

            for (HostScore score : hostScores) {
                //System.out.println(score.getHost1() + " " + score.getHost2() + " " +  score.getHostScore() + " " + score.getGuestScore());
                hostSum += score.getHostScore();
                if (score.getHostScore() > score.getGuestScore()) {
                    winCnt++;
                } else {
                    loseCnt++;
                }
            }
            for (GuestScore score : guestScores) {
                guestSum += score.getGuestScore();
                if (score.getGuestScore() > score.getHostScore()) {
                    winCnt++;
                } else {
                    loseCnt++;
                }
            }

            res.add(MatchResultResponseDTO.builder().user(new GetUserResponseDto(u))
                .score(hostSum + guestSum).winCnt(winCnt).loseCnt(loseCnt).build());
        }

        Collections.sort(res);
        for(MatchResultResponseDTO s : res){
            System.out.println(s.getUser().getUserId() + " " + s.getWinCnt() + " " + s.getLoseCnt() + " " + s.getScore());
        }
        return res;
    }

    public boolean findCheck(Optional<User> findUser, Optional<Club> findClub, Optional<ClubBoard> findClubBoard,
        Optional<List<ClubBoardJoin>> findClubBoardJoin, Optional<AuthMatch> findAuthMatch){
        if(findClub.isEmpty() || findClub.get().getUseYn().equals("N")){
            throw new CustomException("삭제됐거나 존재하지 않는 클럽입니다.", 200);
        }
        if(findUser.isEmpty() || findUser.get().getUseYn().equals("N")){
            throw new CustomException("해당 유저가 없습니다.", 100);
        }
        if(findClubBoard.isEmpty() || findClubBoard.get().getUseYn().equals("N")){
            throw new CustomException("해당 클럽 게시판이 존재하지 않습니다.", 300);
        }
        if (findClubBoardJoin.isEmpty()) {
            throw new CustomException("모임에 참가한 사람이 없습니다.", 600);
        }
        if(findAuthMatch.isEmpty()){
            throw new CustomException("매칭이 존재하지 않습니다.", 700);
        }


        return true;
    }
}
