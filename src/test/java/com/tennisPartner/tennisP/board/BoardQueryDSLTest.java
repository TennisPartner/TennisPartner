package com.tennisPartner.tennisP.board;

import static org.assertj.core.api.Assertions.assertThat;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tennisPartner.tennisP.board.domain.Board;
import com.tennisPartner.tennisP.board.domain.QBoard;
import com.tennisPartner.tennisP.board.repository.dto.CreateBoardRequestDto;
import com.tennisPartner.tennisP.board.service.BoardService;
import com.tennisPartner.tennisP.user.domain.User;
import com.tennisPartner.tennisP.user.repository.dto.JoinRequestDto;
import com.tennisPartner.tennisP.user.service.UserService;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class BoardQueryDSLTest {

    @PersistenceContext
    private EntityManager em;
    @Autowired
    private BoardService boardService;
    @Autowired
    private UserService userService;

    private Long userIdx;

    @BeforeEach
    void createUser() {
        JoinRequestDto join1 = new JoinRequestDto("test", "123123", "test", "", "", 0, "");
        User joinUser = userService.join(join1);
        userIdx = joinUser.getUserIdx();
    }

    @Test
    void JPQLTest() {
        //given
        CreateBoardRequestDto requestDto = new CreateBoardRequestDto("JPQL Test Title",
                "JPQL Test Contents");
        Long createBoardIdx = boardService.createBoard(requestDto, userIdx);
        String searchTitle = requestDto.getBoardTitle();
        String query = "select b from board_tb b where b.boardTitle = :boardTitle";

        //when
        List<Board> boards = em.createQuery(query, Board.class)
                .setParameter("boardTitle", searchTitle)
                .getResultList();

        //then
        assertThat(boards.size()).isEqualTo(1);
        assertThat(boards.get(0).getBoardIdx()).isEqualTo(createBoardIdx);
    }

    @Test
    void QueryDSLTest() {
        //given
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
//        QBoard b = new QBoard("b");
        QBoard b = QBoard.board;

        CreateBoardRequestDto requestDto = new CreateBoardRequestDto("QueryDSL Test Title",
                "QueryDSL Test Contents");
        Long createBoardIdx = boardService.createBoard(requestDto, userIdx);
        String searchTitle = requestDto.getBoardTitle();

        //when
        List<Board> boards = queryFactory.select(b)
                .from(b)
                .where(b.boardTitle.eq(searchTitle))
                .fetch();

        //then
        assertThat(boards.size()).isEqualTo(1);
        assertThat(boards.get(0).getBoardIdx()).isEqualTo(createBoardIdx);

    }
}
