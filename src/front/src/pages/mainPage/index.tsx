import styled from "styled-components";
import FinishButton from "../../components/FinishButton";
import GuideInput from "../../components/GuideInput";

const MainPage = () => {
  return (
    <MainPageContainer>
      <GuideInput guideMessage="매칭을 진행할 인원수를 작성해주세요." />
      <GuideInput guideMessage="매칭을 진행할 게임수를 작성해주세요." />
      <GuideInput guideMessage="매칭을 진행할 코트수를 작성해주세요." />
      <FinishButtonContainer>
        <FinishButton />
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
  gap: 20px;

  height: 100vh;
`;

const FinishButtonContainer = styled.div`
  margin-top: 76px;
`;

export default MainPage;
