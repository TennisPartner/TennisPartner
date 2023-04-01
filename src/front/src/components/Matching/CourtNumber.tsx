import React from "react";
import styled from "styled-components";

const CourtNumber = () => {
  return (
    <CourtNumberContainer>
      <CourtNumberBox style={{ backgroundColor: "aqua" }}>
        코트 1
      </CourtNumberBox>
      <CourtNumberBox>코트 2</CourtNumberBox>
      <CourtNumberBox>코트 2</CourtNumberBox>
    </CourtNumberContainer>
  );
};

const CourtNumberContainer = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;

  height: 40px;
  width: 300px;

  font-family: "Noto Sans KR";
  font-style: normal;
  font-weight: 700;
  font-size: 16px;
  line-height: 42px;

  gap: 16px;
`;

const CourtNumberBox = styled.div`
  padding: 0 20px 0 20px;
  border: 1px solid black;
  border-radius: 12px;
`;

export default CourtNumber;
