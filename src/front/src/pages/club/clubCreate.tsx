import React from "react";
import styled from "styled-components";
import axios from "axios";

const ClubCreate = () => {
  const navigate = useNavigate();

  const baseUrl = import.meta.env.VITE_APP_BACK_END_AWS;

  const [clubInfo, setClubInfo] = React.useState("");
  const [clubName, setClubName] = React.useState("");
  const [clubCity, setClubCity] = React.useState("서울특별시");
  const [clubCounty, setClubCounty] = React.useState("재학이집");

  const clubData = {
    clubCity,
    clubName,
    clubInfo,
    clubCounty,
  };

  const createClub = () => {
    const accessToken = localStorage.getItem("accessToken");
    const response = axios.post(`${baseUrl}/login/api/clubs`, clubData, {
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
    });
    response
      .then((res) => {
        navigate("/club/clubPage");
      })
      .catch((err) => {
        console.log(err);
      });

    console.log(response);
  };

  const onClickHandler = () => {
    createClub();
  };


  return (
    <ClubCreateContainer>
      <ClubCreateTitle>클럽 만들기</ClubCreateTitle>
      <ClubCreateForm>
        <ClubCreateFormTitle>클럽 이름</ClubCreateFormTitle>
        <ClubCreateFormInput
          type="text"
          placeholder="클럽 이름을 입력해주세요."
        />
        <ClubCreateFormTitle>클럽 지역</ClubCreateFormTitle>
        <Selection
          name="pets"
          id="pet-select"
          value={clubCity}
          onChange={(e) => setClubCity(e.target.value)}
        >
          <option value="서울특별시">서울특별시</option>
          <option value="부산광역시">부산광역시</option>
          <option value="대구광역시">대구광역시</option>
          <option value="인천광역시">인천광역시</option>
          <option value="광주광역시">광주광역시</option>
          <option value="대전광역시">대전광역시</option>
          <option value="울산광역시">울산광역시</option>
          <option value="세종특별자치시">세종특별자치시</option>
          <option value="경기도">경기도</option>
          <option value="강원도">강원도</option>
          <option value="충청북도">충청북도</option>
          <option value="충청남도">충청남도</option>
          <option value="전라북도">전라북도</option>
          <option value="전라남도">전라남도</option>
          <option value="경상북도">경상북도</option>
          <option value="경상남도">경상남도</option>
          <option value="제주특별자치도">제주특별자치도</option>
        </Selection>
        <ClubCreateFormTitle>클럽 세부 지역</ClubCreateFormTitle>
        <ClubCreateFormInput
          value={clubCounty}
          onChange={(e) => setClubCounty(e.target.value)}
          type="text"
          placeholder="클럽 세부지역을 입력해주세요."

        />
        <ClubCreateFormTitle>클럽 소개</ClubCreateFormTitle>

        <ClubCreateFormInput
          type="text"
          placeholder="클럽 소개를 입력해주세요."
        />

        <ClubCreateFormTitle>클럽 관리자</ClubCreateFormTitle>
        <ClubCreateFormInput
          type="text"
          placeholder="클럽 관리자를 입력해주세요."
        />
        <ClubCreateFormTitle>클럽 관리자 이메일</ClubCreateFormTitle>
      </ClubCreateForm>
    </ClubCreateContainer>
  );
};

const ClubCreateContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;

  width: 100%;
  padding-top: 40px;

  background-color: ${({ theme }) => theme.colors.tennis};
`;

const ClubCreateTitle = styled.h1`
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 20px;
`;

const ClubCreateForm = styled.form`
  display: flex;
  flex-direction: column;
  width: 300px;
`;

const ClubCreateFormTitle = styled.label`
  font-size: 18px;
  font-weight: bold;
  margin-top: 20px;
`;

const ClubCreateFormInput = styled.input`
  padding: 10px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 5px;
  margin-top: 5px;
  margin-bottom: 20px;
`;

export default ClubCreate;
