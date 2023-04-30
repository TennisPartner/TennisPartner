import React from "react";
import styled from "styled-components";
import BoardPreview from "../../components/board/BoardPreview";

const BoardPage = () => {
  return <ClubPageContainer>22 </ClubPageContainer>;
};

const ClubPageContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 24px;

  padding-top: 40px;

  width: 100%;
  height: calc(100vh - 48px);
  background-color: ${({ theme }) => theme.colors.tennis};

  overflow: auto;
`;

export default BoardPage;
