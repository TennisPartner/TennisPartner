package com.tennisPartner.tennisP.board.service;

import com.tennisPartner.tennisP.board.domain.Board;
import com.tennisPartner.tennisP.board.repository.JpaBoardRepository;
import com.tennisPartner.tennisP.board.repository.dto.CreateBoardRequestDto;
import com.tennisPartner.tennisP.board.repository.dto.GetBoardResponseDto;
import com.tennisPartner.tennisP.common.Exception.CustomException;
import com.tennisPartner.tennisP.user.domain.User;
import com.tennisPartner.tennisP.user.repository.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final JpaUserRepository userRepository;
    private final JpaBoardRepository boardRepository;

    @Override
    public Long createBoard(CreateBoardRequestDto createBoardRequestDto, Long userIdx) {
        Optional<User> findUser = userRepository.findById(userIdx);

        if (!findUser.isEmpty()) {
            User writer = findUser.get();
            Board createBoard = createBoardRequestDto.DtoToBoardEntity(writer);
            Board saveBoard = boardRepository.save(createBoard);
            return saveBoard.getBoardIdx();
        }

        return null;
    }

    @Override
    public Page<GetBoardResponseDto> getBoardList(Integer page, Integer size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createDt"));
        Page<Board> findBoardList = boardRepository.findByUseYn("Y", pageable).get();
        System.out.println("findBoardList.getTotalElements() = " + findBoardList.getTotalElements());
        if (findBoardList.getTotalElements() == 0) {
            throw new CustomException("게시글이 존재하지 않습니다.", 300);
        }

        Page<GetBoardResponseDto> boardList = findBoardList.map(b -> new GetBoardResponseDto(b));
        System.out.println("boardList = " + boardList.getTotalElements());
        return boardList;
    }
}
