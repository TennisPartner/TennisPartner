package com.tennisPartner.tennisP.board.repository;


import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tennisPartner.tennisP.board.domain.Board;
import com.tennisPartner.tennisP.board.domain.QBoard;
import com.tennisPartner.tennisP.board.repository.dto.BoardSearchCondition;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;

public class BoardDSLRepositoryImpl implements BoardDSLRepository {

    private final JPAQueryFactory queryFactory;

    public BoardDSLRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public Page<Board> getBoardList(BoardSearchCondition cond, Pageable pageable) {
        QBoard b = QBoard.board;

        List<Board> boards = queryFactory
                .select(b)
                .from(b)
                .where(
                        b.useYn.eq("Y"),
                        boardTitleOrContentsContain(cond.getSearchText())
                )
                .orderBy(b.updateDt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return PageableExecutionUtils.getPage(boards, pageable, () -> boards.size());
    }

    private BooleanExpression boardTitleOrContentsContain(String searchText) {
        return StringUtils.hasText(searchText) ? QBoard.board.boardTitle.contains(searchText)
                .or(QBoard.board.boardContents.contains(searchText))
                : null;
    }


}
