import React from "react";
import styled from "styled-components";

interface CourtNumberProps {
  match: any;
  setCurrentCourt: any;
  currentCourt: number;
  courtNumber: number;
}

const CourtNumber = ({
  setCurrentCourt,
  currentCourt,
  courtNumber,
}: CourtNumberProps) => {
  const emptyArray = new Array(courtNumber).fill(0);

  const courtChange = (e: any) => {
    const index = e.target.innerText.split("경기")[0] - 1;
    setCurrentCourt(index);
  };

  return (
    <CourtNumberContainer>
      {emptyArray.map((court: any, index: number) => {
        return (
          <CourtNumberBox
            currentCourt={currentCourt}
            index={index}
            onClick={(e) => courtChange(e)}
            key={index}
          >
            {index + 1}경기
          </CourtNumberBox>
        );
      })}
    </CourtNumberContainer>
  );
};

const CourtNumberContainer = styled.div`
  display: flex;
  align-items: center;
  height: 52px;
  width: 300px;

  font-family: "Noto Sans KR";
  font-style: normal;
  font-weight: 700;
  font-size: 16px;
  line-height: 42px;

  gap: 16px;

  overflow: auto;
  white-space: nowrap;

  padding: 8px;

  &::-webkit-scrollbar {
    display: none;
  }
  -ms-overflow-style: none;
  scrollbar-width: none;
`;

const CourtNumberBox = styled.div<{ index: number; currentCourt: number }>`
  padding: 0 20px 0 20px;
  border: 1px solid black;
  border-radius: 12px;

  background-color: ${({ index, currentCourt }) =>
    index === currentCourt ? "aqua" : "white"};
`;

export default CourtNumber;
