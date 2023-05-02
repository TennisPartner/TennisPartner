import React, { useEffect, useState } from "react";
import styled from "styled-components";
import axios from "axios";

import { useNavigate } from "react-router-dom";
interface Props {
  board?: any;
}

const BoardPreview = ({ board }: Props) => {
  const baseUrl = import.meta.env.VITE_APP_BACK_END_AWS;
  const navigate = useNavigate();

  const [isView, setIsView] = useState(true);
  const [userId, setUserId] = useState("");

  const transformDate = (date: string) => {
    // date => null 일 때
    if (!date) {
      return { dateStr: "", time: "" };
    }

    const dateArr = date.split("T");
    const time = dateArr[1].slice(0, 5);
    const dateStr = dateArr[0];
    return { dateStr, time };
  };
  const { dateStr, time } = transformDate(board?.meetDt);

  // 위의 코드를 ...payload로 대체 가능
  const payload = {
    ...board,
  };

  const deleteBoard = () => {
    // 클럽 게시판 글 삭제
    const accessToken = localStorage.getItem("accessToken");
    const clubIdx = board?.clubIdx;
    const clubBoardIdx = board?.clubBoardIdx;

    const result = axios
      .patch(
        `${baseUrl}/login/api/clubs/${clubIdx}/boards/${clubBoardIdx}`,
        { ...payload, useYn: "n" },
        {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        }
      )
      .then((res) => {
        console.log("delete-res", res);
        setIsView(false);
      })
      .catch((err) => {
        console.log("delete-err", err);
      });

    return result;
  };

  // userId === writerId 일 때 삭제 버튼 보이기
  useEffect(() => {
    const accessToken = localStorage.getItem("accessToken");

    const getUserInfo = async () => {
      const result = await axios
        .get(`${baseUrl}/login/api/users`, {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        })
        .then((res) => {
          console.log("res", res);
          setUserId(res.data.userId);
          return res;
        })
        .catch((err) => {
          console.log("err", err);
        });
    };
    getUserInfo();
  }, []);

  const goToBoardDetail = (
    clubIdx: number,
    clubBoardIdx: number,
    userId: string
  ) => {
    // idx를 가지고 클럽 상세 페이지로 이동, idx를 같이 넘겨줌
    navigate(`/club/board/${clubBoardIdx}`, { state: { clubIdx, userId } });
  };

  if (!isView) {
    return null;
  }

  return (
    <Container>
      <ButtonContainer>
        {userId === board.writerDTO.userId && (
          <Button onClick={() => deleteBoard()}>삭제</Button>
        )}
        <Button
          onClick={() =>
            goToBoardDetail(board.clubIdx, board.clubBoardIdx, userId)
          }
        >
          상세보기
        </Button>
      </ButtonContainer>
      <TopTag>
        <UserName>{board.writerDTO.userNickname}</UserName>

        {/* <CreateTiem>n분전</CreateTiem> */}
        {/* <CommentCount>댓글 개수</CommentCount> */}
        {/* 날짜 및 시간 component */}
      </TopTag>
      <Title>{board.clubBoardTitle}</Title>
      {dateStr && (
        <TimeContainer>
          <CreateTime>{dateStr}</CreateTime>
          <CreateTime>{time}</CreateTime>
        </TimeContainer>
      )}
    </Container>
  );
};

const Button = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;

  height: 32px;

  padding: 0 8px;

  margin-right: 8px;

  border-radius: 12px;
  background: lightgray;
  border: 1px solid #bdbdbd;
`;

const Container = styled.div`
  display: flex;
  flex-direction: column;

  width: 240px;

  background: #ffffff;
  border-radius: 12px;

  padding: 16px;
  box-sizing: border-box;
`;

const TimeContainer = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;

  width: 100%;
  height: 40px;

  margin-top: 8px;
`;

const ButtonContainer = styled.div`
  display: flex;
  align-items: center;

  width: 100%;
  height: 32px;
`;

const TopTag = styled.div`
  display: flex;
  align-items: center;
  width: 100%;
  height: 40px;

  margin-top: 8px;
`;

const Title = styled.div`
  display: flex;

  font-size: 20px;
  margin-top: auto;

  width: 200px;
  height: 40px;

  overflow: hidden;
`;

const ProfilePicture = styled.div`
  img {
    width: 40px;
    height: 40px;
    border-radius: 50%;
    margin-left: 16px;
  }
`;

const UserName = styled.div`
  display: flex;
  align-items: center;

  width: 100%;
  height: 32px;

  overflow: hidden;
`;

const CreateTime = styled.div`
  display: flex;
  align-items: center;

  height: 40px;
`;

const CommentCount = styled.div`
  margin-left: auto;

  display: flex;
  align-items: center;

  width: 72px;
  height: 40px;
`;

export default BoardPreview;
