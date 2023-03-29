import React from "react";
import styled from "styled-components";
import { useLocation } from "react-router-dom";

const Footer = () => {
  const location = useLocation();

  const isAuthRelatedPage: boolean =
    location.pathname === "/login" || location.pathname === "/signup";

  return (
    isAuthRelatedPage && (
      <FooterContainer>
        <IconContainer>홈</IconContainer>
        <IconContainer>게시판</IconContainer>
        <IconContainer>클럽</IconContainer>
        <IconContainer>내 정보</IconContainer>
      </FooterContainer>
    )
  );
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
  color: ${({ theme }) => theme.colors.primary};
  width: 60px;
`;

export default Footer;
