import React from "react";
import styled from "styled-components";

interface AuthInputProps {
  titleMessage: string;
  inputMessage: string;
}

const AuthInput = ({ titleMessage, inputMessage }: AuthInputProps) => {
  return (
    <InputContainer>
      <div>{titleMessage}</div>
      <input type="text" placeholder={inputMessage} />
    </InputContainer>
  );
};

const InputContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 10px;
  width: 100%;
  padding: 12px 30px 12px 30px;

  box-sizing: border-box;

  div {
    font-family: "Inter";
    font-style: normal;
    font-weight: 400;
    font-size: 26px;
    line-height: 32px;

    color: #6b6b6b;
  }

  input {
    padding-bottom: 8px;
    border: none;
    border-bottom: 1px solid black;

    font-size: 20px;

    ::placeholder {
      color: black;
      font-weight: 700;
    }
  }
`;

export default AuthInput;
