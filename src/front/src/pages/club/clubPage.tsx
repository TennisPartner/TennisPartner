import React from "react";
import styled from "styled-components";
import BoardPreview from "../../components/board/BoardPreview";
import ClubPreview from "../../components/club/ClubPreview";

import { useState } from "react";
import { Link } from "react-router-dom";
import axios from "axios";

import useIntersect from "../../hooks/useIntersect";

const ClubPage = () => {
  const [hasClub, setHasClub] = useState(false);
  const [clubName, setClubName] = useState("");
  const [data, setData] = useState<any>([]);
  const [targetState, setTargetState] = useState(false);
  const [page, setPage] = useState(0);

  const baseUrl = import.meta.env.VITE_APP_BACK_END_AWS;

  const fetchData = async () => {
    const accessToken = localStorage.getItem("accessToken");
    const result = await axios
      .get(`${baseUrl}/login/api/clubs?page=` + page, {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      })
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

    setData([...data, ...result?.data.content]);
  };

  useEffect(() => {
    fetchData();
  }, []);

  const ref = useIntersect(async (entry, observer) => {
    observer.unobserve(entry.target);
    fetchData();
  });


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
  min-height: 600px;
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
