import React from "react";
import styled from "styled-components";

interface ClubPreviewProps {
  setHasClub: React.Dispatch<React.SetStateAction<boolean>>;
  club?: any;
  onClick?: any;
}

const ClubPreview = ({ setHasClub, club, onClick }: ClubPreviewProps) => {
  return (
    <ClubPreviewContainer onClick={onClick}>
      {/* <ClubPhoto>
        <img
          src="data:image/jpeg;base64,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"
          alt="클럽 사진"
        />
      </ClubPhoto> */}
      <div>
        <ClubTitle>
          {club.clubName}
          <button onClick={() => setHasClub(true)}>가입하기</button>
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
  }
`;

const ClubDescription = styled.div``;
export default ClubPreview;
