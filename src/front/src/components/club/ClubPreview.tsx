import React, { useEffect, useState } from "react";
import styled from "styled-components";

import axios from "axios";
interface ClubPreviewProps {
  club?: any;
  onClick?: any;
  joinClub?: any;
  clubIdx?: any;
  member?: any;
  userId?: any;
  accessToken?: any;
}

const ClubPreview = ({
  club,
  clubIdx,
  member,
  userId,
  accessToken,
}: ClubPreviewProps) => {
  const [isJoin, setIsJoin] = useState(false);
  const baseUrl = import.meta.env.VITE_APP_BACK_END_AWS;
  const owner = club.joinList[0].userDTO.userId;
  // club 가입
  const joinClub = async () => {
    const result = await axios
      .post(
        `${baseUrl}/login/api/clubs/${clubIdx}/join`,
        {},
        {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        }
      )
      .then((res) => {
        return res;
      })
      .catch((err) => {
        console.log("err", err);
      });
    if (result) {
      setIsJoin(true);
    }
  };

  // club 탈퇴
  const leaveClub = async () => {
    const result = await axios
      .patch(
        `${baseUrl}/login/api/clubs/${clubIdx}/join`,
        {},
        {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        }
      )
      .then((res) => {
        return res;
      })
      .catch((err) => {
        console.log("err", err);
      });
    if (result) {
      setIsJoin(false);
    }
  };

  const goToClubDetail = (clubIdx: number) => {
    // idx를 가지고 클럽 상세 페이지로 이동, idx를 같이 넘겨줌
    window.location.href = `/club/:${clubIdx}`;
  };

  // 클럽 가입 확인
  useEffect(() => {
    const isJoin = () => {
      const result = member?.find((item: any) => {
        return item.userDTO.userId === userId;
      });
      if (result) {
        setIsJoin(true);
      } else {
        setIsJoin(false);
      }
    };
    isJoin();
  }, []);

  return (
    <ClubPreviewContainer>
      <div>
        <ClubTitle>
          {club.clubName}
          {!isJoin ? (
            <button onClick={() => joinClub()}>가입하기</button>
          ) : (
            <div style={{ display: "flex", gap: "10px" }}>
              {owner !== userId && (
                <button onClick={() => leaveClub()}>탈퇴하기</button>
              )}
              <button onClick={() => goToClubDetail(clubIdx)}>클럽 상세</button>
            </div>
          )}
        </ClubTitle>
        <ClubDescription>{club.clubInfo}</ClubDescription>
      </div>
    </ClubPreviewContainer>
  );
};

const ClubPreviewContainer = styled.div`
  display: flex;
  align-items: center;
  width: 300px;
  height: 100px;
  background: #ffffff;
  border-radius: 12px;

  padding: 12px;
  box-sizing: border-box;
`;

const ClubPhoto = styled.div`
  width: 80px;
  height: 80px;
  background: gray;
  border-radius: 12px;
  margin-left: 8px;

  overflow: hidden;
`;

const ClubTitle = styled.h1`
  font-family: "Noto Sans KR";
  font-style: normal;
  font-weight: 700;
  font-size: 16px;

  width: 280px;
  height: 40px;

  display: flex;
  align-items: center;
  justify-content: space-between;

  padding-right: 12px;

  margin-bottom: 12px;

  button {
    width: 80px;
    height: 30px;
    border-radius: 12px;

    font-family: "Noto Sans KR";
    font-style: normal;
    font-weight: 700;

    z-index: 100;
  }
`;

const ClubDescription = styled.div``;
export default ClubPreview;
