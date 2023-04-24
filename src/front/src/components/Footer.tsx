import React, { useEffect, useState } from "react";
import styled from "styled-components";
import { useLocation } from "react-router-dom";
import { Link } from "react-router-dom";
import { checkLoginState } from "../util/checkLoginState";

const Footer = () => {
  const location = useLocation();

  const [loginState, setLoginState] = useState(false);

  const isAuthRelatedPage: boolean =
    location.pathname === "/auth/login" || location.pathname === "/auth/signup";

  useEffect(() => {
    if (checkLoginState()) setLoginState(true);
  }, []);

  return !isAuthRelatedPage ? (
    <FooterContainer>
      <IconContainer>
        <CustumLink to="/">홈</CustumLink>
      </IconContainer>
      <IconContainer>
        <CustumLink to="board/boardPage">게시판</CustumLink>
      </IconContainer>
      <IconContainer>
        <CustumLink to="club/clubPage">클럽</CustumLink>
      </IconContainer>
      {loginState ? (
        <IconContainer>
          <CustumLink to="myPage">내 정보</CustumLink>
        </IconContainer>
      ) : (
        <IconContainer>
          <CustumLink to="auth/login">로그인</CustumLink>
        </IconContainer>
      )}
    </FooterContainer>
  ) : null;
};

const FooterContainer = styled.div`
  background-color: ${({ theme }) => theme.colors.tennis};
  color: #fff;
  text-align: center;

  display: flex;
  justify-content: space-around;

  height: 48px;
  width: 100%;

  font-size: 16px;

  position: fixed;
  bottom: 0;

  border-top: 2px solid ${({ theme }) => theme.colors.primary};
`;

const IconContainer = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;

  font-weight: bold;
  color: ${({ theme }) => theme.colors.white};
  width: 60px;
`;

const CustumLink = styled(Link)`
  display: flex;
  align-items: center;
  justify-content: center;

  text-decoration: none;
  cursor: pointer;

  width: 100%;
  height: 100%;
  border-radius: 50%;

  :hover {
    color: ${({ theme }) => theme.colors.primary};
    background-color: ${({ theme }) => theme.colors.white};
  }
`;

export default Footer;
