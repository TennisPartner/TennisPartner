import React, { FC } from "react";
import styled from "styled-components";

interface CourtNumberProps {
  setCurrentCourt: (index: number) => void;
  currentCourt: number;
  courtNumber: number;
}

const CourtNumber: FC<CourtNumberProps> = ({
  setCurrentCourt,
  currentCourt,
  courtNumber,
}) => {
  const emptyArray = new Array<number>(courtNumber).fill(0);

  const courtChange = (e: React.MouseEvent<HTMLDivElement>) => {
    const index = parseInt(e.currentTarget.innerText.split("경기")[0], 10) - 1;
    setCurrentCourt(index);
  };

  return (
    <CourtNumberContainer>
      {emptyArray.map((court, index) => {
        return (
          <CourtNumberBox
            currentCourt={currentCourt}
            index={index}
            onClick={courtChange}
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
