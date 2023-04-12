import React from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import useUserDataStore from "../../zustand/store";
import axios from "axios";

const ClubCreate = () => {
  const navigate = useNavigate();

  const setHasClub = useUserDataStore((state: any) => state.setHasClub);

  const clubData = {
    clubCity: "새로운시대",
    clubName: "클럽이름",
    clubIdx: 0,
    clubInfo: "string",
    clubCounty: "대한미국",
  };

  const postClub = async () => {
    const response = axios.post(
      "https://port-0-tennispartner-du3j2blg4j5r2e.sel3.cloudtype.app/login/api/clubs",
      clubData,
      {
        headers: {
          "Content-Type": "application/json",
        },
      }
    );
    response
      .then((res) => {
        console.log(res);
      })
      .catch((err) => {
        console.log(err);
      });

    console.log(response);
  };

  const onClickHandler = () => {
    postClub();
    // setHasClub(true);
    // navigate("/club/clubPage");
  };

  return (
    <ClubCreateContainer>
      <ClubCreateForm>
        <ClubCreateFormTitle>클럽 이름</ClubCreateFormTitle>
        <ClubCreateFormInput
          type="text"
          placeholder="클럽 이름을 입력해주세요."
        />
        <ClubCreateFormTitle>클럽 지역</ClubCreateFormTitle>
        <ClubCreateFormInput
          type="text"
          placeholder="클럽 지역을 입력해주세요."
        />
        <ClubCreateFormTitle>클럽 이미지</ClubCreateFormTitle>
        <ClubCreateFormInput type="file" />

        <ClubCreateFormTitle>클럽 소개</ClubCreateFormTitle>

        <ClubCreateFormInput
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
  min-height: 100vh;
  padding-top: 40px;
  background-color: ${({ theme }) => theme.colors.tennis};
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

const CreateButton = styled.button`
  width: 200px;
  height: 50px;
  margin-top: 20px;
  border-radius: 24px;
  border: 1px solid ${({ theme }) => theme.colors.black};
  background-color: ${({ theme }) => theme.colors.white};
  font-size: 18px;
  font-weight: bold;
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