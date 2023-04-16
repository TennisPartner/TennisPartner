import styled from "styled-components";

interface GuideInputProps {
  guideMessage: string;
  onChangeHandler?: (e: React.ChangeEvent<HTMLInputElement>) => void;
  typeProps: string;
  value?: number;
}

const GuideInput = ({
  guideMessage,
  onChangeHandler,
  typeProps,
  value,
}: GuideInputProps) => {
  return (
    <GuideInputContainer
      type={typeProps}
      placeholder={guideMessage}
      onChange={onChangeHandler}
      value={value}
    />
  );
};

const GuideInputContainer = styled.input`
  display: flex;
  justify-content: center;
  align-items: center;

  width: 312px;
  height: 48px;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);

  box-sizing: border-box;
  text-align: center; // Add this property to center the text

  background: #ffffff;
  border-radius: 24px;
`;

export default GuideInput;
