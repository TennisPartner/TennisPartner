import styled from "styled-components";
import FinishButton from "../../components/FinishButton";
import GuideInput from "../../components/GuideInput";
import { useEffect, useState } from "react";
import MatchBox from "../../components/Matching/MatchBox";
import CourtNumber from "../../components/Matching/CourtNumber";
import axios from "axios";
import useInput from "../../hooks/useInput";
import { useNavigate } from "react-router-dom";

// inputStyle type
interface InputStyle {
  peopleNumber: string;
  gameNumber: string;
  courtNumber: string;
}

const MainPage = () => {
  const routes = useNavigate();
  const [errorMessage, setErrorMessage] = useState(
    `최대: 인원 20명, 경기 30경기, 코트 5개`
  );
  const [inputStyle, setInputStyle] = useState<InputStyle>({
    peopleNumber: "",
    gameNumber: "",
    courtNumber: "",
  });

  const [peopleNumber, setPeopleNumber, resetPeopleNumber] = useInput(0);
  const [gameNumber, setGameNumber, resetGameNumber] = useInput(0);
  const [courtNumber, setCourtNumber, resetCourtNumber] = useInput(0);

  const match = () => {
    // VITE_APP_BACK_END_URL : 브랜치 main 서버 url
    // VITE_APP_BACK_END_URL_dev : 브랜치 dev 서버 url
    axios
      .post(`${import.meta.env.VITE_APP_BACK_END_AWS}/api/matchs`, {
        courtCnt: courtNumber,
        gameCnt: gameNumber,
        playerCnt: peopleNumber,
      })
      .then((res) => {
        routes("/matching", { state: res.data });
      })
      .catch((err) => {
        setErrorMessage(err.response.data);
        checkErrorPoint(err.response.data);
      });
  };
  const checkMaxValue = () => {
    if (peopleNumber > 20 || gameNumber > 30 || courtNumber > 5) {
      setErrorMessage(`최대 값을 확인해주세요.`);
      return false;
    }
    return true;
  };

  const checkNullValue = () => {
    if (peopleNumber === 0 || gameNumber === 0 || courtNumber === 0) {
      setErrorMessage(`값을 입력해주세요.`);
      return false;
    }
    return true;
  };

  // response에 있는 에러 메시지 기반으로 에러 체크
  const checkErrorPoint = (msg: string) => {
    const errorStyle = {
      peopleNumber: "",
      gameNumber: "",
      courtNumber: "",
    };
    // msg에 인원 이라는 단어가 있으면 인원수 에러
    if (msg.includes("인원")) {
      errorStyle.peopleNumber = "red";
      // resetPeopleNumber();
    }
    // msg에 게임 라는 단어가 있으면 게임수 에러
    if (msg.includes("게임")) {
      errorStyle.gameNumber = "red";
      // resetGameNumber();
    }
    // msg에 코트 라는 단어가 있으면 코트수 에러
    if (msg.includes("코트")) {
      errorStyle.courtNumber = "red";
      // resetCourtNumber();
    }
    setInputStyle(errorStyle);
    return;
  };

  const handleSubmit = (e: React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
    e.preventDefault();
    if (!checkMaxValue()) return;
    if (!checkNullValue()) return;
    match();
  };

  return (
    <MainPageContainer style={{ justifyContent: "center" }}>
      <Logo src="/logo.png" alt="logo" />
      <label htmlFor="peopleNumber">매칭을 진행할 인원수를 작성해주세요.</label>
      <GuideInput
        guideMessage="복식 경기를 위해 4명 이상이 필요합니다."
        onChangeHandler={setPeopleNumber}
        value={peopleNumber}
        typeProps="number"
        id="peopleNumber"
        errorStyle={inputStyle.peopleNumber}
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
        errorStyle={inputStyle.gameNumber}
      />
      <label htmlFor="courtNumber">매칭을 진행할 코트수를 작성해주세요.</label>
      <GuideInput
        guideMessage="인원수/4 이하로 작성해주세요."
        onChangeHandler={setCourtNumber}
        value={courtNumber}
        typeProps="number"
        id="courtNumber"
        errorStyle={inputStyle.courtNumber}
      />
      <ErrorMessage>{errorMessage}</ErrorMessage>
      <FinishButtonContainer>
        <FinishButton onClickHandler={(e) => handleSubmit(e)} />
      </FinishButtonContainer>
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

const ErrorMessage = styled.div`
  font-size: 16px;
  font-weight: 700;

  width: 90%;
  text-align: center;

  color: black;
`;

const FinishButtonContainer = styled.div`
  margin-top: 8px;
`;

const Logo = styled.img`
  width: 108px;
  height: 85px;
`;

export default MainPage;
