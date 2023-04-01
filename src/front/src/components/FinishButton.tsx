import styled from "styled-components";

interface FinishButtonProps {
  setStateProps: (state: boolean) => void;
}

const FinishButton = ({ setStateProps }: FinishButtonProps) => {
  return (
    <FinishButtonContainer onClick={() => setStateProps(true)}>
      완료
    </FinishButtonContainer>
  );
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
