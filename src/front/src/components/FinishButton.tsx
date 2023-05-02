import styled from "styled-components";

interface FinishButtonProps {
  onClickHandler: (e: React.MouseEvent<HTMLButtonElement>) => void;
}

const FinishButton = ({ onClickHandler }: FinishButtonProps) => {
  return (
    <FinishButtonContainer onClick={(e: any) => onClickHandler(e)}>
      완료
    </FinishButtonContainer>
  );
};

const FinishButtonContainer = styled.button`
  width: 100px;
  height: 48px;

  background: #ffffff;
  border: 2px solid #000000;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 24px;

  font-size: 20px;
  font-weight: 700;

  cursor: pointer;
`;

export default FinishButton;
