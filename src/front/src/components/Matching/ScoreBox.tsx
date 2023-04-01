import React from "react";
import styled from "styled-components";

const ScoreBox = () => {
  const FirstTeamScore = 12;
  const SecondTeamScore = 12;

  return (
    <ScoreBoxContainer>
      <div>Score</div>
      <CurrentScore>
        {FirstTeamScore} : {SecondTeamScore}
      </CurrentScore>
    </ScoreBoxContainer>
  );
};

const ScoreBoxContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  font-family: "Noto Sans KR";
  font-style: normal;
  font-weight: 700;
  font-size: 16px;
  line-height: 42px;

  div {
    height: 32px;
  }
`;

const CurrentScore = styled.div``;

export default ScoreBox;
