import React, { useEffect, useState } from "react";
import styled from "styled-components";
import BoardPreview from "../../components/board/BoardPreview";
import ClubPreview from "../../components/club/ClubPreview";

import { Link } from "react-router-dom";
import axios from "axios";
import { useNavigate } from "react-router-dom";

import useIntersect from "../../hooks/useIntersect";

const ClubPage = () => {
  const navigate = useNavigate();

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

    if (result?.data.content === undefined) {
      setTargetState(false);
      return;
    }
    setTargetState(true);
    setPage(page + 1);

    setData([...data, ...result?.data.content]);
  };

  const goToClubDetail = (clubIdx: number) => {
    // idx를 가지고 클럽 상세 페이지로 이동, idx를 같이 넘겨줌
    navigate(`/club/:${clubIdx}`);
  };

  useEffect(() => {
    fetchData();
  }, []);

  const ref = useIntersect(async (entry, observer) => {
    observer.unobserve(entry.target);
    fetchData();
  });

  return (
    <ClubPageContainer>
      <GoToCreateClub>
        <CustomLink to="/club/clubCreate">직접 클럽 만들기</CustomLink>
      </GoToCreateClub>
      {data?.map((club: any) => {
        return (
          <ClubPreview
            onClick={() => goToClubDetail(club.clubIdx)}
            club={club}
            setHasClub={setHasClub}
            key={club.clubIdx}
          />
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
