import { useEffect, useState } from "react";
import axios from "axios";
import styled from "styled-components";

import { useNavigate, useParams } from "react-router-dom";
import BoardPreview from "../../components/board/BoardPreview";
import uuid from "react-uuid";

import useIntersect from "../../hooks/useIntersect";

const ClubDetail = () => {
  const baseUrl = import.meta.env.VITE_APP_BACK_END_AWS;
  const navigate = useNavigate();
  const { id } = useParams();
  const clubIdx = id?.slice(1);
  const accessToken = localStorage.getItem("accessToken");
  const [page, setPage] = useState(0);
  const [clubBoardList, setClubBoardList] = useState<any>([]);
  const [targetState, setTargetState] = useState(false);
  const [totalPage, setTotalPage] = useState(100);
  const [isOwner, setIsOwner] = useState(false);
  const [isEdit, setIsEdit] = useState(false);

  const [clubInfo, setClubInfo] = useState({
    clubIdx: 0,
    clubName: "",
    clubInfo: "",
    clubCity: "",
    clubCounty: "",
  });

  const deleteClub = async () => {
    const result = await axios
      .patch(
        `${baseUrl}/login/api/clubs/${clubIdx}`,
        {
          ...clubInfo,
          useYn: "N",
        },
        {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        }
      )
      .then((res) => {
        navigate("/club/clubPage");
      })
      .catch((err) => {
        console.log("club-err", err);
      });
  };
  const getData = async (userId: string) => {
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
        if (res.data.joinList[0].userDTO.userId === userId) {
          setIsOwner(true);
        }

        return res;
      })
      .catch((err) => {
        console.log("club-err", err);
      });
    setClubInfo(result?.data);
  };
  useEffect(() => {
    const getUserInfo = async () => {
      const result = await axios
        .get(`${baseUrl}/login/api/users`, {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        })
        .then((res) => {
          getData(res.data.userId);
          return res;
        })
        .catch((err) => {
          console.log("err", err);
        });
    };
    getUserInfo();
  }, []);

  const goToClubBoardCreate = () => {
    // 클럽 게시판 글쓰기 페이지로 이동
    navigate("/club/clubBoardCreate", { state: { clubIdx } });
  };

  const updateClub = () => {
    // axios patch 요청
    const response = axios
      .patch(
        `${baseUrl}/login/api/clubs/${clubIdx}`,
        {
          clubInfo,
        },
        {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        }
      )
      .then((res) => {
        return res;
      })
      .catch((err) => {
        console.log("clubBoard-err", err);
      });

    setIsEdit(false);
  };

  const fetchData = () => {
    if (totalPage === page) return setTargetState(false);
    const result = axios
      .get(`${baseUrl}/login/api/clubs/${clubIdx}/boards?page=${page}`, {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      })
      .then((res) => {
        setTotalPage(res.data.totalPages);
        setPage(page + 1);
        setTargetState(true);
        setClubBoardList([...clubBoardList, ...res?.data.content]);
        return res;
      })
      .catch((err) => {
        setTargetState(false);
        console.log("clubBoard-err", err);
      });
  };

  // useIntersect hook 사용
  const ref = useIntersect(async (entry, observer) => {
    observer.unobserve(entry.target);
    fetchData();
  });

  useEffect(() => {
    fetchData();
  }, []);

  return (
    <ClubDetailContainer>
      {!isEdit ? (
        <>
          <ButtonContainer>
            <WriteButton onClick={goToClubBoardCreate}>글쓰기</WriteButton>
          </ButtonContainer>
          <ClubDetailWrapper>
            <ClubProfileContainer>
              <ClubDetailTitle>{clubInfo?.clubName}</ClubDetailTitle>
              <ClubDetailContent>
                클럽 지역 : {clubInfo?.clubCity}
              </ClubDetailContent>
              <ClubDetailContent>
                상세 지역 : {clubInfo?.clubCounty}
              </ClubDetailContent>
              <ClubBoardContent>
                소개 글 : {clubInfo?.clubInfo}
              </ClubBoardContent>
              {isOwner && (
                <ButtonContainer style={{ position: "relative" }}>
                  <WriteButton
                    style={{ marginLeft: "auto" }}
                    onClick={() => setIsEdit(true)}
                  >
                    수정하기
                  </WriteButton>
                  <WriteButton
                    style={{ marginLeft: "auto" }}
                    onClick={deleteClub}
                  >
                    클럽 삭제
                  </WriteButton>
                </ButtonContainer>
              )}
            </ClubProfileContainer>
            <ClubBoardContainer>
              <ClubBoardTitle>게시판</ClubBoardTitle>
              {/* 게시글 data map  */}
              {
                <BoardContainer>
                  {clubBoardList &&
                    clubBoardList?.map((board: any) => (
                      <BoardPreview key={uuid()} board={board} />
                    ))}
                  {targetState && <div ref={ref}></div>}
                </BoardContainer>
              }
            </ClubBoardContainer>
          </ClubDetailWrapper>
        </>
      ) : (
        <ClubDetailWrapper>
          <ClubProfileContainer>
            <ButtonContainer>
              <WriteButton onClick={updateClub}>수정 완료</WriteButton>
              <WriteButton onClick={() => setIsEdit(false)}>
                수정 취소
              </WriteButton>
            </ButtonContainer>
            <ClubDetailTitle>{clubInfo?.clubName}</ClubDetailTitle>
            <EditBox>
              <label>클럽 이름 : </label>
              <input
                type="text"
                value={clubInfo?.clubName}
                onChange={(e) => {
                  setClubInfo({ ...clubInfo, clubName: e.target.value });
                }}
              />
            </EditBox>
            <EditBox>
              <label>상세 지역 : </label>
              <input
                type="text"
                value={clubInfo?.clubCounty}
                onChange={(e) => {
                  setClubInfo({ ...clubInfo, clubCounty: e.target.value });
                }}
              />
            </EditBox>
            <EditBox style={{ flexDirection: "column" }}>
              <label style={{ alignSelf: "start" }}>소개 글 : </label>
              <input
                type="text"
                value={clubInfo?.clubInfo}
                onChange={(e) => {
                  setClubInfo({ ...clubInfo, clubInfo: e.target.value });
                }}
              />
            </EditBox>
          </ClubProfileContainer>
        </ClubDetailWrapper>
      )}
    </ClubDetailContainer>
  );
};

const EditBox = styled.div`
  display: flex;
  align-items: center;
  margin-bottom: 10px;
  gap: 10px;

  label {
    width: 120px;
    font-size: 16px;
    font-weight: 700;
  }

  input {
    width: 100%;
    height: 40px;
    border-radius: 10px;
    border: none;
    background-color: ${({ theme }) => theme.colors.white};
    font-size: 20px;
    font-weight: 700;
    color: ${({ theme }) => theme.colors.black};
  }
`;

const Input = styled.input`
  width: 100%;
  height: 40px;
  border-radius: 10px;
  border: none;
  background-color: ${({ theme }) => theme.colors.white};
  font-size: 14px;
  font-weight: 700;
  color: ${({ theme }) => theme.colors.black};
  cursor: pointer;
`;

const ButtonContainer = styled.div`
  display: flex;
  justify-content: flex-end;
  margin-bottom: 20px;

  position: absolute;
  top: 40px;
  right: 5%;

  gap: 10px;
`;

const WriteButton = styled.button`
  width: 80px;
  height: 40px;
  border-radius: 10px;
  border: none;
  background-color: ${({ theme }) => theme.colors.primary};
  font-size: 14px;
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

  width: 100%;
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
