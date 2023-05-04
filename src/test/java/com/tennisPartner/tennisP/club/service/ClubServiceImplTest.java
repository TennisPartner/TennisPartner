package com.tennisPartner.tennisP.club.service;

import com.tennisPartner.tennisP.club.domain.ClubJoin;
import com.tennisPartner.tennisP.club.repository.ClubJoinRepository;
import com.tennisPartner.tennisP.club.repository.dto.ClubJoinResponseDTO;
import com.tennisPartner.tennisP.club.repository.dto.ClubRequestDTO;
import com.tennisPartner.tennisP.club.repository.dto.ClubResponseDTO;
import com.tennisPartner.tennisP.club.domain.Club;
import com.tennisPartner.tennisP.club.repository.ClubRepository;
import com.tennisPartner.tennisP.user.domain.User;
import com.tennisPartner.tennisP.user.repository.JpaUserRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
public class ClubServiceImplTest {

    @Autowired
    private ClubService clubService;

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private ClubJoinRepository clubJoinRepository;

    @Autowired
    private JpaUserRepository userRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;
    private TransactionTemplate transactionTemplate;

    Long clubIdx = 0L;
    Long clubJoinIdx = 0L;
    Long userIdx = 0L;
    User user;

    @BeforeEach
    void init(){
        transactionTemplate = new TransactionTemplate(transactionManager);
        user = userRepository.findAll().stream().filter(u -> u.getUseYn().equals("Y")).findFirst().get();
        userIdx = user.getUserIdx();
    }

    @AfterEach
    public void delete(){
        if(!clubIdx.equals(0L))
            clubRepository.deleteById(clubIdx);
        if(!clubJoinIdx.equals(0L)){
            //CasCade로 인해 club이 삭제되면 같이 삭제됨
            //clubJoinRepository.deleteById(clubJoinIdx);
        }

    }


    @Test
    void 클럽생성테스트() {
        ClubResponseDTO responseDTO = transactionTemplate.execute(status -> {
            //given
            ClubRequestDTO req = ClubRequestDTO.builder()
                .clubName("클럽1")
                .clubInfo("테니스 클럽~")
                .clubCity("경기도")
                .clubCounty("성남시")
                .build();
            //when
            ClubResponseDTO res = clubService.createClub(req,1L); //테스트하는 메소드

            return res;
        });
        ClubResponseDTO findClub = clubService.getClub(responseDTO.getClubIdx());
        clubIdx = findClub.getClubIdx();

        //then
        Assertions.assertThat(findClub.getClubName()).isEqualTo(responseDTO.getClubName());
        Assertions.assertThat(findClub.getClubInfo()).isEqualTo(responseDTO.getClubInfo());
        Assertions.assertThat(findClub.getClubCity()).isEqualTo(responseDTO.getClubCity());
        Assertions.assertThat(findClub.getClubCounty()).isEqualTo(responseDTO.getClubCounty());


    }

    @Test
    public void 클럽수정테스트() {
        ClubRequestDTO saveReq = ClubRequestDTO.builder()
            .clubName("클럽명")
            .clubInfo("테니스 클럽 ")
            .clubCity("경기도")
            .clubCounty("성남시")
            .build();

        ClubRequestDTO updateReq = ClubRequestDTO.builder()
            .clubName("클럽명 수정")
            .clubInfo("테니스 클럽 수정")
            .clubCity("경기도 수정")
            .clubCounty("성남시 수정")
            .useYn("Y")
            .build();

        ClubResponseDTO updateClub = transactionTemplate.execute(status -> {
            ClubResponseDTO saveClub = clubService.createClub(saveReq,2L);

            clubIdx = saveClub.getClubIdx();

            ClubResponseDTO res = clubService.updateClub(clubIdx, updateReq,2L);

            return res;
        });

        Assertions.assertThat(updateReq.getClubName()).isEqualTo(updateClub.getClubName());
        Assertions.assertThat(updateReq.getClubInfo()).isEqualTo(updateClub.getClubInfo());
        Assertions.assertThat(updateReq.getClubCity()).isEqualTo(updateClub.getClubCity());
        Assertions.assertThat(updateReq.getClubCounty()).isEqualTo(updateClub.getClubCounty());
    }


    @Test
    public void 클럽조회() {
        Club saveReq = Club.builder()
            .clubName("클럽명")
            .clubInfo("테니스 클럽")
            .clubCity("경기도")
            .clubCounty("성남시")
            .build();

        ClubResponseDTO res = transactionTemplate.execute(status ->{
            Club saveClub = clubRepository.save(saveReq);
            clubIdx = saveClub.getClubIdx();
            ClubResponseDTO responseDTO = clubService.getClub(clubIdx);

            return responseDTO;
        });

        Assertions.assertThat(res.getClubName()).isEqualTo(saveReq.getClubName());
        Assertions.assertThat(res.getClubInfo()).isEqualTo(saveReq.getClubInfo());
        Assertions.assertThat(res.getClubCity()).isEqualTo(saveReq.getClubCity());
        Assertions.assertThat(res.getClubCounty()).isEqualTo(saveReq.getClubCounty());

    }

    @Test
    public void 클럽리스트조회() {
        int size = 5;
        int page = 0;
        Club club1 = Club.builder()
            .clubName("클럽1")
            .clubInfo("테스트클럽")
            .clubCity("도시")
            .clubCounty("군")
            .build();
        Club club2 = Club.builder()
            .clubName("클럽2")
            .clubInfo("테스트클럽")
            .clubCity("도시")
            .clubCounty("군")
            .build();
        Club club3 = Club.builder()
            .clubName("클럽3")
            .clubInfo("테스트클럽")
            .clubCity("도시")
            .clubCounty("군")
            .build();
        Club club4 = Club.builder()
            .clubName("클럽4")
            .clubInfo("테스트클럽")
            .clubCity("도시")
            .clubCounty("군")
            .build();
        Club club5 = Club.builder()
            .clubName("클럽5")
            .clubInfo("테스트클럽")
            .clubCity("도시")
            .clubCounty("군")
            .build();
        List<Club> clubs = Arrays.asList(club1, club2, club3, club4, club5);
        Page<ClubResponseDTO> clubList = transactionTemplate.execute(status ->{
            List<Club> saveClubs = clubRepository.saveAll(clubs);
            Page<ClubResponseDTO> res = clubService.getClubList(page, size);
            List<Long> saveIdx = saveClubs.stream().map(Club::getClubIdx).collect(Collectors.toList());
            clubRepository.deleteAllById(saveIdx);
            return res;
        });


        clubList.stream().forEach(club -> Assertions.assertThat(club.getUseYn()).isEqualTo("Y"));
        Assertions.assertThat(size).isEqualTo(clubList.getSize());

    }

    @Test
    public void 클럽가입() {
        ClubRequestDTO saveReq = ClubRequestDTO.builder()
            .clubName("클럽명")
            .clubInfo("테니스 클럽")
            .clubCity("경기도")
            .clubCounty("성남시")
            .build();


        ClubJoinResponseDTO res = transactionTemplate.execute(status -> {
            Long masterIdx = userRepository.findAll().stream().filter(u -> u.getUseYn().equals("Y")).skip(1).findAny().get().getUserIdx();
            ClubResponseDTO club = clubService.createClub(saveReq, masterIdx);
            clubIdx = club.getClubIdx();
            ClubJoinResponseDTO joinClubDTO = clubService.joinClub(clubIdx,user.getUserIdx());

            return joinClubDTO;
        });
        Club findClub = clubRepository.findById(clubIdx).get();
        clubJoinIdx = clubJoinRepository.findByUserAndClub(user,findClub).get().getClubJoinIdx();

        Assertions.assertThat(res.getClubJoinIdx()).isEqualTo(clubJoinIdx);
    }

    @Test
    public void 클럽탈퇴() {
        Club saveReq = Club.builder()
            .clubName("클럽명")
            .clubInfo("테니스 클럽")
            .clubCity("경기도")
            .clubCounty("성남시")
            .build();

        Club saveClub = transactionTemplate.execute(status -> {
            Club club = clubRepository.save(saveReq);
            ClubJoin clubJoin = ClubJoin.builder()
                .club(club)
                .user(user)
                .clubGrade("Common")
                .build();
            clubIdx = club.getClubIdx();
            clubJoinRepository.save(clubJoin);
            clubService.leaveClub(club.getClubIdx(),1L);
            return club;
        });

        Optional<ClubJoin> findClubJoin = clubJoinRepository.findByUserAndClub(user,saveClub);
        clubJoinIdx = findClubJoin.get().getClubJoinIdx();
        Assertions.assertThat(findClubJoin.get().getUseYn()).isEqualTo("N");
    }
}


















