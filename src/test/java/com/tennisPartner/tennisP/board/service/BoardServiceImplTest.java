package com.tennisPartner.tennisP.board.service;

import com.tennisPartner.tennisP.board.domain.Board;
import com.tennisPartner.tennisP.board.repository.JpaBoardRepository;
import com.tennisPartner.tennisP.board.repository.dto.CreateBoardRequestDto;
import com.tennisPartner.tennisP.user.domain.User;
import com.tennisPartner.tennisP.user.repository.JpaUserRepository;
import com.tennisPartner.tennisP.user.repository.dto.JoinRequestDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
    @DisplayName("게시판 생성 성공")
    void createBoard() {

        CreateBoardRequestDto createBoardRequestDto = new CreateBoardRequestDto("테스트 입니다.", "테스트 입니다.");

        Long boardIdx = boardService.createBoard(createBoardRequestDto, userIdx);

        assertThat(boardIdx).isEqualTo(1);
    }
}