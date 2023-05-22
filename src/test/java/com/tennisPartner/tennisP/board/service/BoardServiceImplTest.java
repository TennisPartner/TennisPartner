package com.tennisPartner.tennisP.board.service;

import com.tennisPartner.tennisP.board.repository.dto.BoardSearchCondition;
import com.tennisPartner.tennisP.board.repository.dto.CreateBoardRequestDto;
import com.tennisPartner.tennisP.board.repository.dto.GetBoardResponseDto;
import com.tennisPartner.tennisP.board.repository.dto.UpdateBoardRequestDto;
import com.tennisPartner.tennisP.user.domain.User;
import com.tennisPartner.tennisP.user.repository.JpaUserRepository;
import com.tennisPartner.tennisP.user.repository.dto.JoinRequestDto;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
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
        BoardSearchCondition noCond = new BoardSearchCondition("");
        BoardSearchCondition cond = new BoardSearchCondition("테스트1 입니다.");
        for (int i = 0; i < 8; i++) {
            boardService.createBoard(new CreateBoardRequestDto("테스트 입니다.",
                    "테스트"+ String.valueOf(i) +" 입니다."), userIdx);
        }

        CreateBoardRequestDto createBoardRequestDtoLast = new CreateBoardRequestDto("마지막 입니다.",
                "마지막 입니다.");
        boardService.createBoard(createBoardRequestDtoLast, userIdx);
        Page<GetBoardResponseDto> noSearchBoards = boardService.getBoardList(noCond, 1, 5);
        Page<GetBoardResponseDto> SearchBoards = boardService.getBoardList(cond, 0, 5);

        GetBoardResponseDto getBoardResponseDto = noSearchBoards.stream()
                .skip(3)
                .findFirst()
                .orElse(null);

        Optional<GetBoardResponseDto> searchBoard = SearchBoards.stream()
                .findFirst();
        assertThat(noSearchBoards.getTotalElements()).isEqualTo(9);
        assertThat(SearchBoards.getTotalElements()).isEqualTo(1);
        assertThat(searchBoard.get().getBoardContents()).isEqualTo(cond.getSearchText());
        assertThat(getBoardResponseDto.getBoardTitle()).isEqualTo("마지막 입니다.");
    }
    
    @Test
    void updateBoard() {
        CreateBoardRequestDto createBoardRequestDto = new CreateBoardRequestDto("테스트 입니다.",
                "테스트 입니다.");
        Long boardIdx = boardService.createBoard(createBoardRequestDto, userIdx);
        UpdateBoardRequestDto updateBoardRequestDto = new UpdateBoardRequestDto("수정 테스트", "수정 테스트", "");

        boolean tf = boardService.updateBoard(boardIdx, userIdx, updateBoardRequestDto);
        GetBoardResponseDto board = boardService.getBoard(boardIdx);
        System.out.println("boardIdx = " + boardIdx);

        assertThat(tf).isTrue();
        assertThat(board.getBoardTitle()).isEqualTo("수정 테스트");
    }

    @Test
    void deleteBoard() {
        //given
        CreateBoardRequestDto createBoardRequestDto = new CreateBoardRequestDto("테스트 입니다.",
                "테스트 입니다.");
        Long boardIdx = boardService.createBoard(createBoardRequestDto, userIdx);
        UpdateBoardRequestDto updateBoardRequestDto = new UpdateBoardRequestDto(null, null, "N");

        //when
        boolean b = boardService.updateBoard(boardIdx, userIdx, updateBoardRequestDto);
        GetBoardResponseDto findBoard = boardService.getBoard(boardIdx);

        //then
        assertThat(b).isTrue();
        assertThat(findBoard).isNull();
    }

}