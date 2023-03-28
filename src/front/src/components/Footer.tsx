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
  background-color: #97e201;
  color: #fff;
  padding: 1rem;
  text-align: center;

  display: flex;
  justify-content: space-around;

  height: 48px;
  width: 100%;

  position: fixed;
  bottom: 0;
`;

const IconContainer = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 1rem;
`;

export default Footer;
