import React from "react";
import styled from "styled-components";

interface TeamBoxProps {
  player1: number;
  player2: number;
}

const TeamBox = ({ player1, player2 }: TeamBoxProps) => {
  return (
    <TeamBoxContainer>
      <TeamPlayerBox>
        <PlayerName> Player {player1 + 1}</PlayerName>
      </TeamPlayerBox>
      <TeamPlayerBox>
        <PlayerName> Player {player2 + 1}</PlayerName>
      </TeamPlayerBox>
    </TeamBoxContainer>
  );
};

const TeamBoxContainer = styled.div`
  width: 100px;
  height: 64px;
  background-color: ${({ theme }) => theme.colors.white};
  border-radius: 12px;

  display: flex;
  flex-direction: column;
`;

const TeamPlayerBox = styled.div`
  width: 100%;
  height: 32px;
  display: flex;
  align-items: center;
  justify-content: center;
`;

const PlayerProfile = styled.div`
  width: 28px;
  height: 28px;
  background: #d9d9d9;
  border-radius: 50%;
  overflow: hidden; /* added to clip the image to the border-radius */

  margin-left: 8px;
`;

const PlayerName = styled.div`
  width: 100%;
  height: 32px;

  display: flex;
  align-items: center;
  justify-content: center;
`;

export default TeamBox;
