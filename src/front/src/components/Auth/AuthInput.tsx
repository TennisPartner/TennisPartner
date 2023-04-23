import React from "react";
import styled from "styled-components";

interface AuthInputProps {
  value: string;
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
  placeholder: string;
  type?: string;
}

const AuthInput = ({ value, onChange, placeholder, type }: AuthInputProps) => {
  return (
    <InputContainer
      value={value}
      onChange={onChange}
      placeholder={placeholder}
      type={type}
    />
  );
};

const InputContainer = styled.input`
  width: 100%;

  padding-bottom: 8px;
  border: none;
  border-bottom: 1px solid black;

  font-size: 20px;

  ::placeholder {
    color: black;
    font-weight: 700;
  }
`;

export default AuthInput;
