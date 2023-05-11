import React, { useState } from "react";
import styled from "styled-components";
import CourtNumber from "../../components/Matching/CourtNumber";
import MatchBox from "../../components/Matching/MatchBox";
import { useLocation } from "react-router-dom";

const Matching = () => {
  const [currentCourt, setCurrentCourt] = useState(0);

  const location = useLocation();

  console.log("location", location);

  const matchingData = location.state;

  return (
    <MainPageContainer>
      <CourtNumber
        currentCourt={currentCourt}
        setCurrentCourt={setCurrentCourt}
        courtNumber={matchingData.gameList.length}
      />
      <MatchBox
        match={matchingData}
        currentCourt={currentCourt}
        setCurrentCourt={setCurrentCourt}
      />
    </MainPageContainer>
  );
};
const MainPageContainer = styled.div`
  background-color: ${({ theme }) => theme.colors.tennis};

  display: flex;
  flex-direction: column;
  align-items: center;

  gap: 12px;

  width: 100%;
  height: calc(100vh - 48px);
`;
export default Matching;
