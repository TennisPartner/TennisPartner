import React from "react";
import styled from "styled-components";
import BoardPreview from "../../components/board/BoardPreview";

const ClubPage = () => {
  return (
    <ClubPageContainer>
      <BoardPreview></BoardPreview>
    </ClubPageContainer>
  );
};

const ClubPageContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 24px;

  padding-top: 40px;

  width: 100%;
  height: 100vh;
  background-color: ${({ theme }) => theme.colors.tennis};
`;

export default ClubPage;
