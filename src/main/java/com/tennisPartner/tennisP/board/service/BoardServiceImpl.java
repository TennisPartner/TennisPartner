package com.tennisPartner.tennisP.board.service;

import com.tennisPartner.tennisP.board.domain.Board;
import com.tennisPartner.tennisP.board.domain.UplBoard;
import com.tennisPartner.tennisP.board.repository.BoardRepository;
import com.tennisPartner.tennisP.board.repository.JpaBoardUplRepository;
import com.tennisPartner.tennisP.board.repository.dto.BoardSearchCondition;
import com.tennisPartner.tennisP.board.repository.dto.CreateBoardRequestDto;
import com.tennisPartner.tennisP.board.repository.dto.GetBoardResponseDto;
import com.tennisPartner.tennisP.board.repository.dto.UpdateBoardRequestDto;
import com.tennisPartner.tennisP.common.Exception.CustomException;
import com.tennisPartner.tennisP.common.util.ImageUtil;
import com.tennisPartner.tennisP.user.domain.User;
import com.tennisPartner.tennisP.user.repository.JpaUserRepository;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final JpaUserRepository userRepository;
    private final BoardRepository boardRepository;
    private final JpaBoardUplRepository boardUplRepository;

    @Value("${upload.path.board}")
    private String boardPath;

    @Transactional
    @Override
    public Long createBoard(CreateBoardRequestDto createBoardRequestDto, List<MultipartFile> boardPhotos, Long userIdx) throws IOException {
        Optional<User> findUser = userRepository.findById(userIdx);

        if (!findUser.isEmpty()) {
            User writer = findUser.get();
            Board createBoard = createBoardRequestDto.DtoToBoardEntity(writer);
            Board saveBoard = boardRepository.save(createBoard);

            if (boardPhotos != null) {
                for (MultipartFile boardPhoto : boardPhotos) {
                    String uplPath = ImageUtil.imageSave(boardPath, saveBoard.getBoardIdx(), boardPhoto);

                    UplBoard boardUpl = UplBoard.builder()
                            .board(saveBoard)
                            .uplPath(uplPath)
                            .build();

                    boardUplRepository.save(boardUpl);
                }
            }

            return saveBoard.getBoardIdx();
        } else {
            throw new CustomException("존재하지 않는 유저 입니다.", HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Page<GetBoardResponseDto> getBoardList(BoardSearchCondition cond, Integer page, Integer size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Board> boards = boardRepository.getBoardList(cond, pageable);

        if (boards.getTotalElements() == 0) {
            throw new CustomException("게시글이 존재하지 않습니다.", HttpServletResponse.SC_BAD_REQUEST);
        }

        Page<GetBoardResponseDto> boardList = boards.map(b -> new GetBoardResponseDto(b));

        return boardList;
    }

    @Transactional(readOnly = true)
    @Override
    public GetBoardResponseDto getBoard(Long boardIdx) {

        Optional<Board> findBoard = boardRepository.findByUseYnAndBoardIdx("Y", boardIdx);

        if (!findBoard.isEmpty()) {
            Optional<GetBoardResponseDto> getBoardResponseDto = findBoard.map(b -> new GetBoardResponseDto(b));
            return getBoardResponseDto.get();
        }

        return null;
    }

    @Transactional
    @Override
    public boolean updateBoard(Long boardIdx, Long userIdx, UpdateBoardRequestDto updateBoardRequestDto, List<MultipartFile> boardPhotos) {

        Optional<Board> findBoard = boardRepository.findByUseYnAndBoardIdx("Y", boardIdx);

        if (!findBoard.isEmpty()) {
            Board board = findBoard.get();
            User writer = board.getWriter();
            if (writer.getUserIdx() == userIdx && writer.getUseYn().equals("Y")) {
                if (!StringUtils.hasText(updateBoardRequestDto.getUseYn())) {
                    board.updateBoard(updateBoardRequestDto);
                } else {
                    board.deleteBoard();
                }
                return true;
            } else {
                throw new CustomException("잘못된 유저의 접근입니다.", HttpServletResponse.SC_BAD_REQUEST);
            }
        }

        return false;
    }

    @Override
    public boolean blobTest(List<String> blobs) throws IOException {

        for (String blob : blobs) {
            ImageUtil.blobSave(blob, boardPath);
        }

        return false;
    }
}
