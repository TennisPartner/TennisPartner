package com.tennisPartner.tennisP;

import com.tennisPartner.tennisP.club.dto.ClubRequestDTO;
import com.tennisPartner.tennisP.club.dto.ClubResponseDTO;
import com.tennisPartner.tennisP.club.entity.Club;
import com.tennisPartner.tennisP.club.repository.ClubRepository;
import com.tennisPartner.tennisP.club.service.ClubService;
import com.tennisPartner.tennisP.common.CustomException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class ClubTest {

    @Autowired
    private ClubService clubService;

    @Autowired
    private ClubRepository clubRepository;

    @Test
    @Transactional
    void 클럽생성테스트(){
        //when
        ClubRequestDTO req1 = ClubRequestDTO.builder()
            .clubName("클럽1")
            .clubInfo("테니스 클럽~")
            .clubCity("경기도")
            .clubCounty("성남시")
            .build();

        ClubRequestDTO req2 = ClubRequestDTO.builder()
            .clubName("클럽1")
            .clubInfo("테니스 클럽11~")
            .clubCity("경기도")
            .clubCounty("성남시")
            .build();
        //then
        ClubResponseDTO res1 = clubService.createClub(req1);
        ClubResponseDTO findClub1 = clubService.getClub(res1.getClubIdx());

        Assertions.assertThat(findClub1.getClubIdx()).isEqualTo(res1.getClubIdx());

    }

    @Test
    @Transactional
    public void 클럽수정테스트(){
        ClubRequestDTO saveReq = ClubRequestDTO.builder()
            .clubName("클럽명")
            .clubInfo("테니스 클럽 ")
            .clubCity("경기도")
            .clubCounty("성남띠")
            .build();

        ClubRequestDTO updateReq = ClubRequestDTO.builder()
            .clubName("클럽명 수정")
            .clubInfo("테니스 클럽 수정")
            .clubCity("경기도")
            .clubCounty("성남띠")
            .build();

        Club saveEntity = saveReq.toEntity();
        Club saveClub = clubRepository.save(saveEntity);

        Long findIdx = saveClub.getClubIdx();

        ClubResponseDTO updateClub = clubService.updateClub(findIdx, updateReq);

        Assertions.assertThat(updateReq.getClubName()).isEqualTo(updateClub.getClubName());
        Assertions.assertThat(updateReq.getClubInfo()).isEqualTo(updateClub.getClubInfo());
        Assertions.assertThat(updateReq.getClubCity()).isEqualTo(updateClub.getClubCity());
        Assertions.assertThat(updateReq.getClubCounty()).isEqualTo(updateClub.getClubCounty());
    }

    @Test
    @Transactional
    public void 없는클럽수정(){
        Pageable pageable = PageRequest.of(0, 100, Sort.by("createDt"));
        Page<Club> findClubs = clubRepository.findByUseYn('N',pageable);
        Long findIdx = findClubs.stream().findFirst().get().getClubIdx();

        ClubRequestDTO updateReq = ClubRequestDTO.builder()
            .clubName("클럽명 수정")
            .clubInfo("테니스 클럽 수정")
            .clubCity("경기도")
            .clubCounty("성남띠")
            .build();

        Assertions.assertThatThrownBy(() -> {
            ClubResponseDTO updateClub= clubService.updateClub(findIdx, updateReq);
        }).isInstanceOf(CustomException.class);

    }
    @Test
    @Transactional
    public void 클럽조회(){
        Club saveReq = Club.builder()
            .clubName("클럽명")
            .clubInfo("테니스 클럽 ")
            .clubCity("경기도")
            .clubCounty("성남띠")
            .build();

        Club saveClub = clubRepository.save(saveReq);
        ClubResponseDTO res = clubService.getClub(saveClub.getClubIdx());
        Assertions.assertThat(res.getClubName()).isEqualTo(saveReq.getClubName());
        Assertions.assertThat(res.getClubInfo()).isEqualTo(saveReq.getClubInfo());
        Assertions.assertThat(res.getClubCity()).isEqualTo(saveReq.getClubCity());
        Assertions.assertThat(res.getClubCounty()).isEqualTo(saveReq.getClubCounty());

    }
    @Test
    @Transactional
    public void 클럽리스트조회() {
        int size = 5;
        int page = 0;
        Page<ClubResponseDTO> clubList = clubService.getClubList(page,size);

        clubList.stream().forEach(club -> Assertions.assertThat(club.getUseYn()).isEqualTo('Y'));
        Assertions.assertThat(size).isEqualTo(clubList.getSize());


    }
}
