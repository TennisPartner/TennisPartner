package com.tennisPartner.tennisP.clubBoardReply.service;

import com.tennisPartner.tennisP.clubBoardReply.repository.dto.ClubBoardReplyResponseDTO;
import org.springframework.data.domain.Page;

public interface ClubBoardReplyService {

    public ClubBoardReplyResponseDTO createClubBoardReply(Long clubIdx, Long clubBoardIdx, String replyContents);
    public ClubBoardReplyResponseDTO updateClubBoardReply(Long clubIdx, Long clubBoardIdx, Long clubBoardReplyIdx, String replyContents);
    public void deleteClubBoardReply(Long clubIdx, Long clubBoardIdx, Long clubBoardReplyIdx);
    public Page<ClubBoardReplyResponseDTO> getClubBoardReplyList(Long clubIdx, Long clubBoardIdx, int page, int size);
}
