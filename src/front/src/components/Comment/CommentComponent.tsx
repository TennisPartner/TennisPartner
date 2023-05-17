import { any } from "cypress/types/bluebird";
import { useState } from "react";
import styled from "styled-components";

interface Props {
  comment: any;
  deleteComment: (clubBoardReplyIdx: number) => void;
  updateComment: (clubBoardReplyIdx: number) => void;
  editComment: string;
  setEditComment: React.Dispatch<React.SetStateAction<string>>;
  userId: string;
}

const CommentComponent = ({
  comment,
  deleteComment,
  updateComment,
  editComment,
  setEditComment,
  userId,
}: Props) => {
  const [isEditComment, setIsEditComment] = useState(false);

  const updateCommentHandler = (commentId: number) => {
    updateComment(commentId);
    setIsEditComment(false);
  };

  const isEditHandler = () => {
    setIsEditComment(true);
    setEditComment(comment.replyContents);
  };

  return (
    <Comment key={comment.clubBoardReplyIdx}>
      <CommentWriter>
        닉네임 : {comment.writer.userNickname || comment.writer.userId}
      </CommentWriter>
      <FlexBox>
        {!isEditComment && (
          <>
            <CommentContents>{comment.replyContents}</CommentContents>
            {userId === comment.writer.userId && (
              <ButtonContainer>
                <CommentButton
                  onClick={() => deleteComment(comment.clubBoardReplyIdx)}
                >
                  삭제
                </CommentButton>
                <CommentButton onClick={() => isEditHandler()}>
                  수정
                </CommentButton>
              </ButtonContainer>
            )}
          </>
        )}
        {isEditComment && (
          <>
            <CommentInput
              type="text"
              value={editComment}
              onChange={(e) => setEditComment(e.target.value)}
            />
            <ButtonContainer>
              <CommentButton
                onClick={() => updateCommentHandler(comment.clubBoardReplyIdx)}
              >
                완료
              </CommentButton>
              <CommentButton onClick={() => setIsEditComment(false)}>
                취소
              </CommentButton>
            </ButtonContainer>
          </>
        )}
      </FlexBox>
    </Comment>
  );
};

const Comment = styled.div`
  display: flex;
  flex-direction: column;
  gap: 5px;

  border: 1px solid ${({ theme }) => theme.colors.black};
  border-radius: 5px;
  padding: 10px;

  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  box-sizing: border-box;
`;

const CommentWriter = styled.div`
  font-weight: bold;
`;

const CommentContents = styled.div`
  font-size: 14px;
`;

const FlexBox = styled.div`
  display: flex;
  justify-content: space-between;
`;

const ButtonContainer = styled.div`
  display: flex;
  justify-content: flex-end;

  gap: 10px;
`;

const CommentInput = styled.input`
  width: 80%;
  height: 32px;
  border: 1px solid ${({ theme }) => theme.colors.black};
  border-radius: 5px;

  box-sizing: border-box;
`;

const CommentButton = styled.button`
  width: 40px;
  height: 32px;
  background-color: ${({ theme }) => theme.colors.gray};
  border: 1px solid ${({ theme }) => theme.colors.black};
  border-radius: 5px;
  font-weight: bold;
`;
export default CommentComponent;
