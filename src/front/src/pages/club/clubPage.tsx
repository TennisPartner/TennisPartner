import React, { useEffect, useState } from "react";
import styled from "styled-components";
import BoardPreview from "../../components/board/BoardPreview";
import ClubPreview from "../../components/club/ClubPreview";

import { Link } from "react-router-dom";
import useUserDataStore from "../../zustand/store";
import axios from "axios";

import useIntersect from "../../hooks/useIntersect";

const ClubPage = () => {
  const hasClub = useUserDataStore((state: any) => state.hasClub);
  const setHasClub = useUserDataStore((state: any) => state.setHasClub);
  const [data, setData] = useState([]);
  const [targetState, setTargetState] = useState(false);
  const [page, setPage] = useState(0);
  const fetchData = async () => {
    const result = await axios(
      "https://port-0-tennispartner-du3j2blg4j5r2e.sel3.cloudtype.app:443/login/api/clubs?page=" +
        page
    )
      .then((res) => {
        return res;
      })
      .catch((err) => {
        console.log("err", err);
      });
    if (result?.data.content.length === 0) {
      setTargetState(false);
      return;
    }
    setTargetState(true);
    setPage(page + 1);

    setData(data.concat(result?.data.content));
  };
  useEffect(() => {
    fetchData();
  }, []);
  console.log("data", data);

  const ref = useIntersect(async (entry, observer) => {
    observer.unobserve(entry.target);
    console.log("무한스크롤");
    fetchData();
  });

  return hasClub ? (
    <ClubPageContainer>
      <ClubPreview setHasClub={setHasClub}></ClubPreview>
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
      {data?.map((club: any) => {
        return (
          <ClubPreview club={club} setHasClub={setHasClub} key={club.clubIdx} />
        );
      })}
      {targetState && <Target ref={ref} />}
    </ClubPageContainer>
  );
};

const Target = styled.div`
  height: 1px;
  background-color: aqua;
`;

const ClubPageContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 24px;
  padding-top: 40px;
  padding-bottom: 80px;

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