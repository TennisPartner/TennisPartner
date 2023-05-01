package com.tennisPartner.tennisP.board.service;

import com.tennisPartner.tennisP.board.repository.dto.CreateBoardRequestDto;
import com.tennisPartner.tennisP.board.repository.dto.GetBoardResponseDto;
import com.tennisPartner.tennisP.user.domain.User;
import com.tennisPartner.tennisP.user.repository.JpaUserRepository;
import com.tennisPartner.tennisP.user.repository.dto.JoinRequestDto;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BoardServiceImplTest {

    @Autowired
    private BoardService boardService;
    @Autowired
    private JpaUserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private Long userIdx;
    @BeforeEach
    void joinUser() {
        JoinRequestDto join1 = new JoinRequestDto("test", "123123", "test", "test", "F", 3.5, "test");
        User user = join1.dtoToUserEntity(passwordEncoder.encode(join1.getUserPassword()));
        User save = userRepository.save(user);
        userIdx = save.getUserIdx();
    }

    @Test
    void createBoard() {

        CreateBoardRequestDto createBoardRequestDto = new CreateBoardRequestDto("테스트 입니다.", "테스트 입니다.");

        Long boardIdx = boardService.createBoard(createBoardRequestDto, userIdx);

        assertThat(boardIdx).isEqualTo(1);
    }

    @Test
    void getBoardList() {
        CreateBoardRequestDto createBoardRequestDto = new CreateBoardRequestDto("테스트 입니다.",
                "테스트 입니다.");
        for (int i = 0; i < 6; i++) {
            boardService.createBoard(createBoardRequestDto, userIdx);
        }
        CreateBoardRequestDto createBoardRequestDtoLast = new CreateBoardRequestDto("마지막 입니다.",
                "마지막 입니다.");
        boardService.createBoard(createBoardRequestDtoLast, userIdx);
        Page<GetBoardResponseDto> boardList = boardService.getBoardList(1, 5);

        assertThat(boardList.getTotalElements()).isEqualTo(7);
        assertThat(boardList.getSize()).isEqualTo(5);
        assertThat(boardList.getTotalPages()).isEqualTo(2);
    }

}