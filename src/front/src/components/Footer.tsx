import React from "react";
import styled from "styled-components";

const Footer = () => {
  return (
    <FooterContainer>
      <IconContainer>홈</IconContainer>
      <IconContainer>게시판</IconContainer>
      <IconContainer>클럽</IconContainer>
      <IconContainer>내 정보</IconContainer>
    </FooterContainer>
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

  width: 60px;
`;

export default Footer;
