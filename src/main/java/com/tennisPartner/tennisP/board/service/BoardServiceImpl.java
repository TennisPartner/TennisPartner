package com.tennisPartner.tennisP.board.service;

import com.tennisPartner.tennisP.board.domain.Board;
import com.tennisPartner.tennisP.board.repository.JpaBoardRepository;
import com.tennisPartner.tennisP.board.repository.dto.CreateBoardRequestDto;
import com.tennisPartner.tennisP.user.domain.User;
import com.tennisPartner.tennisP.user.repository.JpaUserRepository;
import lombok.RequiredArgsConstructor;
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
}
