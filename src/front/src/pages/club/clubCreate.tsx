import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";
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
      <ClubCreateForm>
        <ClubCreateFormTitle>클럽 이름</ClubCreateFormTitle>
        <ClubCreateFormInput
          value={clubName}
          onChange={(e) => setClubName(e.target.value)}
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
          value={clubInfo}
          onChange={(e) => setClubInfo(e.target.value)}
          type="text"
          placeholder="클럽 소개를 입력해주세요."
        />
      </ClubCreateForm>
      <CreateButton onClick={onClickHandler}>클럽 만들기</CreateButton>
    </ClubCreateContainer>
  );
};

const ClubCreateContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 100%;
  min-height: calc(100vh - 5rem);
  padding-top: 2.5rem;

  background-color: ${({ theme }) => theme.colors.tennis};
`;

const ClubCreateForm = styled.form`
  display: flex;
  flex-direction: column;
  width: 18.75rem;
`;

const ClubCreateFormTitle = styled.label`
  font-size: 1.125rem;
  font-weight: bold;
  margin-top: 1.25rem;
`;

const CreateButton = styled.button`
  width: 12.5rem;
  height: 3.125rem;
  margin-top: 1.25rem;
  border-radius: 1.5rem;
  border: 0.0625rem solid ${({ theme }) => theme.colors.black};
  background-color: ${({ theme }) => theme.colors.white};
  font-size: 1.125rem;
  font-weight: bold;
`;

const ClubCreateFormInput = styled.input`
  padding: 0.625rem;
  font-size: 1rem;
  border: 0.0625rem solid #ccc;
  border-radius: 0.3125rem;
  margin-top: 0.3125rem;
  margin-bottom: 1.25rem;
`;

const Selection = styled.select`
  padding: 0.625rem;
  font-size: 1rem;
  border: 0.0625rem solid #ccc;
  border-radius: 0.3125rem;
  margin-top: 0.3125rem;
  margin-bottom: 1.25rem;
`;

export default ClubCreate;
