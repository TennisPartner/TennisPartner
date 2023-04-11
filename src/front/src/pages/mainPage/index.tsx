import styled from "styled-components";
import FinishButton from "../../components/FinishButton";
import GuideInput from "../../components/GuideInput";
import { useState } from "react";
import MatchBox from "../../components/Matching/MatchBox";
import CourtNumber from "../../components/Matching/CourtNumber";
import axios from "axios";

const MainPage = () => {
  const [isMatching, setIsMatching] = useState(false);
  const [peopleNumber, setPeopleNumber] = useState(0);
  const [gameNumber, setGameNumber] = useState(0);
  const [courtNumber, setCourtNumber] = useState(0);

  const handleSubmit = (e: React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
    e.preventDefault();
    console.log("first");
    // setIsMatching(true);

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
        console.log("res", res);
        setIsMatching(true);
      })
      .catch((err) => {
        console.log("err", err);
      });
  };

  const changePeopleNumber = (e: React.ChangeEvent<HTMLInputElement>) => {
    setPeopleNumber(Number(e.target.value));
  };

  const changeGameNumber = (e: React.ChangeEvent<HTMLInputElement>) => {
    setGameNumber(Number(e.target.value));
  };

  const changeCourtNumber = (e: React.ChangeEvent<HTMLInputElement>) => {
    setCourtNumber(Number(e.target.value));
  };

  const matchingData = {
    peopleNumber,
    gameNumber,
    courtNumber,
  };

  return isMatching ? (
    <MainPageContainer>
      <CourtNumber />
      <MatchBox />
      <MatchBox />
      <MatchBox />
      <MatchBox />
      <MatchBox />
      <MatchBox />
      <MatchBox />
      <MatchBox />
      <MatchBox />
      <MatchBox />
    </MainPageContainer>
  ) : (
    <MainPageContainer>
      <GuideInput
        guideMessage="매칭을 진행할 인원수를 작성해주세요."
        onChangeHandler={changePeopleNumber}
        typeProps="number"
      />
      <GuideInput
        guideMessage="매칭을 진행할 게임수를 작성해주세요."
        onChangeHandler={changeGameNumber}
        typeProps="number"
      />
      <GuideInput
        guideMessage="매칭을 진행할 코트수를 작성해주세요."
        onChangeHandler={changeCourtNumber}
        typeProps="number"
      />
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
  justify-content: center;
  gap: 24px;

  min-height: 100vh;
  height: 100%;
  padding-top: 40px;
`;

const FinishButtonContainer = styled.div`
  margin-top: 76px;
`;

export default MainPage;
