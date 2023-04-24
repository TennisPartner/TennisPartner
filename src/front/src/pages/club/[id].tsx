import { useEffect, useState } from "react";
import axios from "axios";
import styled from "styled-components";

import { useNavigate, useParams } from "react-router-dom";
import BoardPreview from "../../components/board/BoardPreview";

const ClubDetail = () => {
  const baseUrl = import.meta.env.VITE_APP_BACK_END_AWS;
  const navigate = useNavigate();
  const { id } = useParams();

  const clubIdx = id?.slice(1);

  const [clubInfo, setClubInfo] = useState({
    clubIdx: 0,
    clubName: "",
    clubInfo: "",
    clubCity: "",
    clubCounty: "",
  });

  // clubIdx로 클럽 정보 가져오기
  useEffect(() => {
    console.log("clubIdx", clubIdx);
    const getData = async () => {
      const accessToken = localStorage.getItem("accessToken");
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
          console.log("first", res);
          return res;
        })
        .catch((err) => {
          console.log("err", err);
        });
      console.log("result", result?.data);
      setClubInfo(result?.data);
    };

    getData();
  }, []);

  const goToClubBoardCreate = () => {
    // 클럽 게시판 글쓰기 페이지로 이동
    navigate("/club/clubBoardCreate", { state: { clubIdx } });
  };

  return (
    <ClubDetailContainer>
      <WriteButton onClick={goToClubBoardCreate}>글쓰기</WriteButton>
      <ClubDetailWrapper>
        <ClubDetailTitle>{clubInfo?.clubName}</ClubDetailTitle>
        <ClubDetailContent>클럽 지역 : {clubInfo?.clubCity}</ClubDetailContent>
        <ClubDetailContent>
          상세 지역 : {clubInfo?.clubCounty}
        </ClubDetailContent>
        <ClubBoardContent> {clubInfo?.clubInfo}</ClubBoardContent>
        <ClubBoardContainer>
          <ClubBoardTitle>게시판</ClubBoardTitle>
          <BoardPreview />
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
  background-color: ${({ theme }) => theme.colors.white};
  font-size: 20px;
  font-weight: 700;
  color: ${({ theme }) => theme.colors.black};
  cursor: pointer;
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

  padding: 40px 40px;

  font-family: "Noto Sans KR", sans-serif;
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
