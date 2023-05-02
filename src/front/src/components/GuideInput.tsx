import styled from "styled-components";

interface GuideInputProps {
  guideMessage: string;
  onChangeHandler?: (e: React.ChangeEvent<HTMLInputElement>) => void;
  typeProps: string;
  value?: number;
  id?: string;
  errorStyle: string;
}

const GuideInput = ({
  guideMessage,
  onChangeHandler,
  typeProps,
  value,
  id,
  errorStyle,
}: GuideInputProps) => {
  return (
    <GuideInputContainer
      type={typeProps}
      placeholder={guideMessage}
      onChange={onChangeHandler}
      value={value ? value : ""}
      id={id}
      errorStyle={errorStyle}
    />
  );
};

const GuideInputContainer = styled.input<{ errorStyle: string }>`
  display: flex;
  justify-content: center;
  align-items: center;

  width: 312px;
  height: 48px;

  box-sizing: border-box;
  text-align: center; // Add this property to center the text

  background: #ffffff;
  border-radius: 24px;

  font-size: 16px;

  //props로 받은 style을 적용

  border: 2px solid ${(props) => props.errorStyle || "#000000"};

  ::-webkit-inner-spin-button {
    -webkit-appearance: none;
    margin: 0;
  }
`;

export default GuideInput;
