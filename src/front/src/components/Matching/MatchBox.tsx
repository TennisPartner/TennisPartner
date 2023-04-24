import styled from "styled-components";

import TeamBox from "../../components/Matching/TeamBox";
import ScoreBox from "../../components/Matching/ScoreBox";

interface MatchBoxProps {
  match: any;
  currentCourt: number;
  setCurrentCourt: any;
}

const MatchBox = ({ match, currentCourt, setCurrentCourt }: MatchBoxProps) => {
  const arr = match.gameList[currentCourt];

  const splitArray = (arr: any) => {
    const result = [];
    for (let i = 0; i < arr.length; i += 4) {
      const chunk = arr.slice(i, i + 4);
      result.push(chunk);
    }
    return result;
  };

  const result = splitArray(arr);

  return (
    <MatchBoxContainer>
      {result.map((member: any, index: number) => {
        return (
          <GameBox key={index}>
            <TeamBox player1={member[0]} player2={member[1]} />
            <ScoreBox />
            <TeamBox player1={member[2]} player2={member[3]} />
          </GameBox>
        );
      })}
    </MatchBoxContainer>
  );
};

const MatchBoxContainer = styled.div`
  width: 300px;
  height: calc(100vh - 48px);
`;

const GameBox = styled.div`
  width: 100%;
  height: 100px;
  display: flex;
  justify-content: space-between;
  align-items: center;
`;

export default MatchBox;
