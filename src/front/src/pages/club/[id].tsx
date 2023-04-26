import { useEffect, useState } from "react";
import axios from "axios";
import styled from "styled-components";

import { useNavigate, useParams } from "react-router-dom";
import BoardPreview from "../../components/board/BoardPreview";
import uuid from "react-uuid";

const ClubDetail = () => {
  const baseUrl = import.meta.env.VITE_APP_BACK_END_AWS;
  const navigate = useNavigate();
  const { id } = useParams();
  const clubIdx = id?.slice(1);
  const accessToken = localStorage.getItem("accessToken");

  const [clubBoardList, setClubBoardList] = useState([]);

  const [clubInfo, setClubInfo] = useState({
    clubIdx: 0,
    clubName: "",
    clubInfo: "",
    clubCity: "",
    clubCounty: "",
  });

  // clubIdx로 클럽 정보 가져오기
  useEffect(() => {
    const clubIdx = id?.slice(1);

    const getData = async () => {
      // add clubIdx body data
      if (!clubIdx) {
        return;
      }
      const result = await axios
        .get(`${baseUrl}/login/api/clubs/${clubIdx}`, {
          headers: {
            Authorization: `Bearer ${accessToken}`,
            clubIdx,
          },
        })
        .then((res) => {
          console.log("club", res);
          return res;
        })
        .catch((err) => {
          console.log("club-err", err);
        });
      setClubInfo(result?.data);
    };

    getData();
  }, []);

  const goToClubBoardCreate = () => {
    // 클럽 게시판 글쓰기 페이지로 이동
    navigate("/club/clubBoardCreate", { state: { clubIdx } });
  };

  useEffect(() => {
    const clubIdx = id?.slice(1);

    const getClubBoard = () => {
      console.log("clubIdx", clubIdx);
      const result = axios
        .get(`${baseUrl}/login/api/clubs/${clubIdx}/boards?page=0`, {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        })
        .then((res) => {
          console.log("clubBoard", res);
          setClubBoardList(res?.data.content);
          return res;
        })
        .catch((err) => {
          console.log("clubBoard-err", err);
        });
    };

    getClubBoard();
  }, []);

  console.log("clubBoardList", clubBoardList);
  return (
    <ClubDetailContainer>
      <WriteButton onClick={goToClubBoardCreate}>글쓰기</WriteButton>
      <ClubDetailWrapper>
        <ClubProfileContainer>
          <ClubDetailTitle>{clubInfo?.clubName}</ClubDetailTitle>
          <ClubDetailContent>
            클럽 지역 : {clubInfo?.clubCity}
          </ClubDetailContent>
          <ClubDetailContent>
            상세 지역 : {clubInfo?.clubCounty}
          </ClubDetailContent>
          <ClubBoardContent> 소개 글 : {clubInfo?.clubInfo}</ClubBoardContent>
        </ClubProfileContainer>
        <ClubBoardContainer>
          <ClubBoardTitle>게시판</ClubBoardTitle>
          {/* 게시글 data map  */}
          <BoardContainer>
            {clubBoardList &&
              clubBoardList?.map((board) => (
                <BoardPreview key={uuid()} board={board} />
              ))}
          </BoardContainer>
        </ClubBoardContainer>
      </ClubDetailWrapper>
    </ClubDetailContainer>
  );
};

const WriteButton = styled.button`
  position: absolute;
  top: 30px;
  right: 20px;
  width: 80px;
  height: 40px;
  border-radius: 10px;
  border: none;
  background-color: ${({ theme }) => theme.colors.primary};
  font-size: 20px;
  font-weight: 700;
  color: ${({ theme }) => theme.colors.white};
  cursor: pointer;
`;

const ClubProfileContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;

  width: 250px;
  height: 300px;

  padding: 20px 20px;
  box-sizing: border-box;
  border-radius: 10px;
  background-color: ${({ theme }) => theme.colors.white};

  overflow-y: scroll;

  @media screen and (min-width: 768px) {
    align-self: flex-start;
    width: 400px;
  }
`;

const ClubDetailContainer = styled.div`
  display: flex;
  flex-direction: column;

  width: 100%;
  height: calc(100vh - 48px);

  overflow: auto;

  background-color: ${({ theme }) => theme.colors.tennis};
`;

const ClubDetailWrapper = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  padding: 40px 40px;

  font-family: "Noto Sans KR", sans-serif;

  @media screen and (min-width: 768px) {
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;

    gap: 40px;
  }
`;

const BoardContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  gap: 20px;
`;

const ClubDetailTitle = styled.div`
  font-size: 30px;
  font-weight: 700;
  color: ${({ theme }) => theme.colors.black};

  margin-bottom: 20px;
`;

const ClubDetailContent = styled.div`
  font-size: 20px;
  font-weight: 400;
  color: ${({ theme }) => theme.colors.black};

  margin-bottom: 20px;
`;

const ClubBoardContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  margin-top: 20px;
`;

const ClubBoardTitle = styled.div`
  font-size: 20px;
  font-weight: 700;
  color: ${({ theme }) => theme.colors.black};

  margin-bottom: 20px;
`;

const ClubBoardContent = styled.div`
  font-size: 20px;
  font-weight: 400;
  color: ${({ theme }) => theme.colors.black};

  margin-bottom: 20px;
`;

export default ClubDetail;
