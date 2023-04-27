import axios from "axios";
import { useEffect, useState } from "react";
import { useParams, useLocation } from "react-router-dom";
import styled from "styled-components";
import Tag from "../../../components/Tag";

const ClubBoardDetail = () => {
  const baseUrl = import.meta.env.VITE_APP_BACK_END_AWS;
  const accessToken = localStorage.getItem("accessToken");

  const { id } = useParams<{ id: string }>();
  const { state } = useLocation();

  const [isWanted, setIsWanted] = useState(false);
  const [isEdit, setIsEdit] = useState(false);
  const [isFullMember, setIsFullMember] = useState(false);
  const [isWriter, setIsWriter] = useState(false);

  const [board, setBoard] = useState({
    clubBoardIdx: 0,
    clubBoardTitle: "",
    clubBoardContents: "",
    clubBoardType: "",
    clubIdx: 0,
    meetDt: "",
    wantedCnt: 0,
    useYn: "",
    writerDTO: {},
    joinList: [],
  } as any);

  const clubIdx = state?.clubIdx;
  const clubBoardIdx = id;
  const userId = state?.userId;

  const cancelBoard = () => {
    const response = axios.delete(
      `${baseUrl}/login/api/clubs/${clubIdx}/boards/${clubBoardIdx}/join`,
      {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      }
    );
    response
      .then((res) => {
        console.log("userId", userId);
        console.log(board.joinList.filter((join: any) => join.userDTO.userId));
        setBoard({
          ...board,
          joinList: board.joinList.filter(
            (join: any) => join.userDTO.userId !== userId
          ),
        });
        setIsWanted(false);
      })
      .catch((err) => {
        console.log(err);
      });

    console.log(response);
  };

  const joinBoard = () => {
    const response = axios.post(
      `${baseUrl}/login/api/clubs/${clubIdx}/boards/${clubBoardIdx}/join`,
      {
        userId,
      },
      {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      }
    );
    response
      .then((res) => {
        console.log(res);
        setIsWanted(true);
        setBoard({ ...board, joinList: [...board.joinList, res.data] });
      })
      .catch((err) => {
        console.log(err);
      });

    console.log(response);
  };

  const editBoard = () => {
    const response = axios.patch(
      `${baseUrl}/login/api/clubs/${clubIdx}/boards/${clubBoardIdx}`,
      {
        ...board,
      },
      {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      }
    );
    response
      .then((res) => {
        console.log(res);
        setIsEdit(false);
      })
      .catch((err) => {
        console.log(err);
      });

    console.log(response);
  };

  useEffect(() => {
    const response = axios.get(
      `${baseUrl}/login/api/clubs/${clubIdx}/boards/${clubBoardIdx}`,
      {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      }
    );
    response
      .then((res) => {
        // 2023-04-30T17:22:00 -> 2023-04-30 17:22:00 으로 변환
        const meetDt = res.data.meetDt.replace("T", ",  시간 :");
        setBoard({ ...res.data, meetDt });

        // res.data.joinList 에 userId가 있으면 true
        if (
          res.data.joinList.some((join: any) => join.userDTO.userId === userId)
        ) {
          setIsWanted(true);
        }
        // joinList의 길이가 wantedCnt와 같으면 true
        if (res.data.joinList.length === res.data.wantedCnt) {
          setIsFullMember(true);
        }
        // writerDTO의 userId가 userId와 같으면 true
        if (res.data.writerDTO.userId === userId) {
          setIsWriter(true);
        }
      })
      .catch((err) => {
        console.log(err);
      });
  }, []);

  return (
    <BoardContainer>
      <BoardCard>
        <ButtonContainer>
          {isWriter &&
            (isEdit ? (
              <UpdateButton onClick={() => setIsEdit(false)}>
                수정 취소
              </UpdateButton>
            ) : (
              <UpdateButton onClick={() => setIsEdit(true)}>
                수정하기
              </UpdateButton>
            ))}
          {isEdit && (
            <UpdateButton onClick={() => editBoard()}>수정 완료</UpdateButton>
          )}

          {!isEdit &&
            board?.clubBoardType === "M" &&
            !isFullMember &&
            (isWanted ? (
              <UpdateButton onClick={() => cancelBoard()}>
                참여 취소
              </UpdateButton>
            ) : (
              <UpdateButton onClick={() => joinBoard()}>참여하기</UpdateButton>
            ))}
        </ButtonContainer>
        {isEdit ? (
          <EditBox>
            <label>제목 : </label>
            <Input
              type="text"
              value={board?.clubBoardTitle}
              onChange={(e) =>
                setBoard({ ...board, clubBoardTitle: e.target.value })
              }
              style={{ width: "200px" }}
            />
          </EditBox>
        ) : (
          <StyledTitle>{board?.clubBoardTitle}</StyledTitle>
        )}
        <StyledWriter>작성자 : {board?.writerDTO?.userName}</StyledWriter>
        {isEdit ? (
          <EditBox>
            <label>내용 : </label>
            <Input
              type="text"
              value={board?.clubBoardContents}
              onChange={(e) =>
                setBoard({ ...board, clubBoardContents: e.target.value })
              }
            />
          </EditBox>
        ) : (
          <div> 내용 : {board?.clubBoardContents}</div>
        )}

        {board?.clubBoardType === "M" && (
          <>
            <StyledDate> 만나는 날 : {board?.meetDt}</StyledDate>
            <StyledJoinList>
              <TagName>참여자 :</TagName>
              <TagBox>
                {board?.joinList?.length === 0
                  ? "참여자가 없습니다."
                  : board?.joinList?.map((join: any) => (
                      <Tag type="user" key={join.userDTO.userId}>
                        {join?.userDTO.userName || join?.userDTO.userId}
                      </Tag>
                    ))}
              </TagBox>
            </StyledJoinList>
          </>
        )}
      </BoardCard>
    </BoardContainer>
  );
};
const BoardContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: ${({ theme }) => theme.colors.tennis};
`;

const BoardCard = styled.div`
  width: 90%;
  height: 80%;
  background-color: ${({ theme }) => theme.colors.white};
  border-radius: 5px;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  margin-bottom: 20px;
  padding: 20px;
  font-size: 16px;
  line-height: 1.5;
  text-align: justify;
`;

const EditBox = styled.div`
  display: flex;
  margin-bottom: 20px;
  align-items: center;
  label {
    min-width: 50px;
  }
`;

const Input = styled.input`
  width: 100%;
  height: 32px;
  border: 1px solid ${({ theme }) => theme.colors.black};
  border-radius: 5px;
`;

const ButtonContainer = styled.div`
  display: flex;
  justify-content: flex-end;
  margin-bottom: 20px;

  position: absolute;
  bottom: 40px;
  right: 5%;

  gap: 10px;
`;

const UpdateButton = styled.button`
  width: 80px;
  height: 32px;
  background-color: ${({ theme }) => theme.colors.gray};
  border: 1px solid ${({ theme }) => theme.colors.black};
  border-radius: 5px;
  margin-bottom: 20px;
  font-weight: bold;
`;

const StyledTitle = styled.h1`
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 10px;
`;

const StyledWriter = styled.div`
  font-weight: bold;
  margin-bottom: 10px;
`;

const StyledDate = styled.div`
  margin-top: 10px;
`;

const TagBox = styled.div`
  display: flex;
  gap: 5px;

  margin-left: 10px;

  overflow-x: scroll;

  &::-webkit-scrollbar {
    display: none;
  }
`;

const StyledJoinList = styled.div`
  display: flex;
  align-items: center;

  margin-top: 20px;
  font-weight: bold;
`;

const TagName = styled.div`
  font-weight: bold;
  min-width: 60px;
`;

export default ClubBoardDetail;
