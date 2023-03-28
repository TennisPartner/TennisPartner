import styled from "styled-components";

const FinishButton = () => {
  return <FinishButtonContainer>완료</FinishButtonContainer>;
};

const FinishButtonContainer = styled.button`
  width: 80px;
  height: 32px;

  background: #ffffff;
  border: 2px solid #000000;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 24px;

  cursor: pointer;
`;

export default FinishButton;
