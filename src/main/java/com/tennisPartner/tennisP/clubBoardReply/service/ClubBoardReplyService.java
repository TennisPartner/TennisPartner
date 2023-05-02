package com.tennisPartner.tennisP.clubBoardReply.service;

import com.tennisPartner.tennisP.clubBoardReply.repository.dto.ClubBoardReplyResponseDTO;
import org.springframework.data.domain.Page;

public interface ClubBoardReplyService {

    public ClubBoardReplyResponseDTO createClubBoardReply(Long clubIdx, Long clubBoardIdx, String replyContents, Long userIdx);
    public ClubBoardReplyResponseDTO updateClubBoardReply(Long clubIdx, Long clubBoardIdx, Long clubBoardReplyIdx, String replyContents, Long userIdx);
    public void deleteClubBoardReply(Long clubIdx, Long clubBoardIdx, Long clubBoardReplyIdx, Long userIdx);
    public Page<ClubBoardReplyResponseDTO> getClubBoardReplyList(Long clubIdx, Long clubBoardIdx, int page, int size, Long userIdx);
}
