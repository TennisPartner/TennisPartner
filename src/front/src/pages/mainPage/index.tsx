import styled from "styled-components";
import FinishButton from "../../components/FinishButton";
import GuideInput from "../../components/GuideInput";
import { useState } from "react";
import MatchBox from "../../components/Matching/MatchBox";
import CourtNumber from "../../components/Matching/CourtNumber";
import axios from "axios";
import useInput from "../../hooks/useInput";

const MainPage = () => {
  const [isMatching, setIsMatching] = useState(false);
  const [matchingData, setMatchingData] = useState({ gameList: [[]] });
  const [errorMessage, setErrorMessage] = useState(
    `최대 값 : 인원수 50명, 경기수 20경기, 코트수 5개`
  );

  const [peopleNumber, setPeopleNumber, resetPeopleNumber] = useInput(0);
  const [gameNumber, setGameNumber, resetGameNumber] = useInput(0);
  const [courtNumber, setCourtNumber, resetCourtNumber] = useInput(0);
  const [currentCourt, setCurrentCourt] = useState(0);

  const match = () =>
    axios
      .post(
        "https://port-0-tennispartner-du3j2blg4j5r2e.sel3.cloudtype.app/api/matchs",
        {
          courtCnt: courtNumber,
          gameCnt: gameNumber,
          playerCnt: peopleNumber,
        },
        {
          headers: {
            "Content-Type": "application/json",
            "Access-Control-Allow-Origin": "*",
            "Access-Control-Allow-Methods": "GET,PUT,POST,DELETE,PATCH,OPTIONS",
          },
        }
      )
      .then((res) => {
        setIsMatching(true);
        setMatchingData(res.data);
      })
      .catch((err) => {
        setErrorMessage(err.response.data);
        resetCourtNumber();
        resetGameNumber();
        resetPeopleNumber();
      });

  const checkMaxValue = () => {
    if (peopleNumber > 50 || gameNumber > 20 || courtNumber > 5) {
      setErrorMessage(`최대 값을 확인해주세요.`);
      return false;
    }
    return true;
  };

  const handleSubmit = (e: React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
    e.preventDefault();
    if (!checkMaxValue()) return;
    match();
  };

  return isMatching ? (
    <MainPageContainer>
      <CourtNumber
        match={matchingData}
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
  ) : (
    <MainPageContainer style={{ justifyContent: "center" }}>
      <label htmlFor="peopleNumber">매칭을 진행할 인원수를 작성해주세요.</label>
      <GuideInput
        guideMessage="복식 경기를 위해 4명 이상이 필요합니다."
        onChangeHandler={setPeopleNumber}
        value={peopleNumber}
        typeProps="number"
        id="peopleNumber"
      />
      <label htmlFor="gameNumber">
        매칭을 진행할 전체 게임수를 작성해주세요.
      </label>
      <GuideInput
        guideMessage="인원수/4 이상으로 작성해주세요."
        onChangeHandler={setGameNumber}
        value={gameNumber}
        typeProps="number"
        id="gameNumber"
      />
      <label htmlFor="courtNumber">매칭을 진행할 코트수를 작성해주세요.</label>
      <GuideInput
        guideMessage="인원수/4 이하로 작성해주세요."
        onChangeHandler={setCourtNumber}
        value={courtNumber}
        typeProps="number"
        id="courtNumber"
      />
      <ErrorMessage>{errorMessage}</ErrorMessage>
      <FinishButtonContainer>
        <FinishButton
          setStateProps={setIsMatching}
          onClickHandler={(e) => handleSubmit(e)}
        />
      </FinishButtonContainer>
    </MainPageContainer>
  );
};

const MainPageContainer = styled.div`
  background-color: ${({ theme }) => theme.colors.tennis};

  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 24px;

  min-height: calc(100vh - 48px);
  padding-top: 48px;
  height: 100%;
`;

const ErrorMessage = styled.div`
  font-size: 16px;
  font-weight: 700;

  width: 90%;
  text-align: center;

  color: red;
`;

const FinishButtonContainer = styled.div`
  margin-top: 76px;
`;

export default MainPage;
