import React, { useEffect, useState } from "react";
import styled from "styled-components";
import BoardPreview from "../../components/board/BoardPreview";
import ClubPreview from "../../components/club/ClubPreview";

import { Link } from "react-router-dom";
import useUserDataStore from "../../zustand/store";
import axios from "axios";

const ClubPage = () => {
  const hasClub = useUserDataStore((state: any) => state.hasClub);
  const setHasClub = useUserDataStore((state: any) => state.setHasClub);
  const [data, setData] = useState({});

  useEffect(() => {
    const fetchData = async () => {
      const result = await axios(
        "https://port-0-tennispartner-du3j2blg4j5r2e.sel3.cloudtype.app/login/api/clubs/3"
      )
        .then((res) => {
          return res;
        })
        .catch((err) => {
          console.log("err", err);
        });
      setData(result?.data);
    };

    fetchData();
  }, []);

  console.log("fetchData", data);

  return hasClub ? (
    <ClubPageContainer>
      <ClubPreview hasClub={hasClub} setHasClub={setHasClub}></ClubPreview>
      <BoardPreview />
      <BoardPreview />
      <BoardPreview />
      <BoardPreview />
      <BoardPreview />
      <BoardPreview />
      <BoardPreview />
      <BoardPreview />
    </ClubPageContainer>
  ) : (
    <ClubPageContainer>
      <GoToCreateClub>
        <CustomLink to="/club/clubCreate">직접 클럽 만들기</CustomLink>
      </GoToCreateClub>
      <ClubPreview hasClub={hasClub} setHasClub={setHasClub} />
      <ClubPreview hasClub={hasClub} setHasClub={setHasClub} />
      <ClubPreview hasClub={hasClub} setHasClub={setHasClub} />
      <ClubPreview hasClub={hasClub} setHasClub={setHasClub} />
      <ClubPreview hasClub={hasClub} setHasClub={setHasClub} />
      <ClubPreview hasClub={hasClub} setHasClub={setHasClub} />
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
  min-height: 100vh;
  height: 100%;
  background-color: ${({ theme }) => theme.colors.tennis};
`;

const GoToCreateClub = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 300px;
  height: 50px;
  background: #ffffff;
  border-radius: 12px;
`;

const CustomLink = styled(Link)`
  text-decoration: none;
  font-family: "Noto Sans KR", sans-serif;
  font-size: 20px;
  font-weight: 700;
`;

export default ClubPage;