import styled from "styled-components";

interface AuthButtonProps {
  children: string;
  onClick?: () => void;
}

const AuthButton = ({ children, onClick }: AuthButtonProps) => {
  return <CustomButton onClick={onClick}>{children}</CustomButton>;
};

const CustomButton = styled.button`
  width: 230px;
  height: 54px;
  border: none;
  border-radius: 24px;
  background-color: ${({ theme }) => theme.colors.tennis};
  color: black;
  font-style: normal;
  font-weight: 400;
  font-size: 36px;
  line-height: 46px;
  text-align: center;

  display: flex;
  align-items: center;
  justify-content: center;

  cursor: pointer;
`;

export default AuthButton;
