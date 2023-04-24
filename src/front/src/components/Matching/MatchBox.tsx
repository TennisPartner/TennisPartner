import styled from "styled-components";

import TeamBox from "../../components/Matching/TeamBox";
import ScoreBox from "../../components/Matching/ScoreBox";

const MatchBox = () => {
  return (
    <MatchBoxContainer>
      <TeamBox></TeamBox>
      <ScoreBox></ScoreBox>
      <TeamBox></TeamBox>
    </MatchBoxContainer>
  );
};

const MatchBoxContainer = styled.div`
  width: 300px;
  height: calc(100vh - 48px);
`;


  display: flex;
  align-items: center;
  justify-content: space-between;
`;

export default MatchBox;
