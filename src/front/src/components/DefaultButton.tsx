import React from "react";
import styled from "styled-components";

interface ButtonProps {
  onClick: () => void;
  children: React.ReactNode;
  style?: React.CSSProperties;
}

const DefaultButton = ({ onClick, style, children }: ButtonProps) => {
  return <CustomButton>{children}</CustomButton>;
};

const CustomButton = styled.button`
  width: 80px;
  height: 32px;
  border: none;
  border-radius: 10px;
  background-color: ${({ theme }) => theme.colors.gray};
  color: ${({ theme }) => theme.colors.white};
  font-size: 1.125rem;
  font-weight: bold;
  cursor: pointer;
`;

export default DefaultButton;
