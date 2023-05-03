import React, { useState } from "react";
import styled from "styled-components";

const ScoreBox = (): JSX.Element => {
  const [firstTeamScore, setFirstTeamScore] = useState<number>(0);
  const [secondTeamScore, setSecondTeamScore] = useState<number>(0);

  return (
    <ScoreBoxContainer>
      <div>Score</div>
      <CurrentScore>
        <input
          type="number"
          value={firstTeamScore}
          onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
            setFirstTeamScore(Number(e.target.value))
          }
        />
        <div>:</div>
        <input
          type="number"
          value={secondTeamScore}
          onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
            setSecondTeamScore(Number(e.target.value))
          }
        />
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

const CurrentScore = styled.div`
  display: flex;
  align-items: center;

  div {
    height: 20px;

    font-size: 20px;
    line-height: 22px;
  }

  input {
    width: 40px;
    height: 20px;
    border: none;

    background-color: ${({ theme }) => theme.colors.tennis};

    display: flex;
    justify-content: center;
    align-items: center;
    text-align: center;

    font-size: 20px;
    line-height: 22px;
    font-weight: 400;

    ::-webkit-inner-spin-button {
      -webkit-appearance: none;
      margin: 0;
    }
  }
`;

export default ScoreBox;
