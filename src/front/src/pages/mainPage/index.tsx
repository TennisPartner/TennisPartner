import styled from "styled-components";
import FinishButton from "../../components/FinishButton";
import GuideInput from "../../components/GuideInput";
import { useState } from "react";
import MatchBox from "../../components/Matching/MatchBox";
import CourtNumber from "../../components/Matching/CourtNumber";
import axios from "axios";

const MainPage = () => {
  const [isMatching, setIsMatching] = useState(false);

  const handleSubmit = (e: React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
    e.preventDefault();
    console.log("first");
    setIsMatching(true);
    // axios
    //   .post("http://localhost:3000", {
    //     data: {
    //       name: "test",
    //       age: 20,
    //     },
    //   })
    //   .then((res) => {
    //     console.log(res);
    //   })
    //   .catch((err) => {
    //     console.log(err);
    //   });
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
      <GuideInput guideMessage="매칭을 진행할 인원수를 작성해주세요." />
      <GuideInput guideMessage="매칭을 진행할 게임수를 작성해주세요." />
      <GuideInput guideMessage="매칭을 진행할 코트수를 작성해주세요." />
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

  min-height: 600px;
  height: 100%;
  padding-top: 40px;
`;

const FinishButtonContainer = styled.div`
  margin-top: 76px;
`;

export default MainPage;
