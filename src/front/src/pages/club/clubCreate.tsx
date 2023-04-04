import React from "react";
import styled from "styled-components";

const ClubCreate = () => {
  return (
    <ClubCreateContainer>
      <ClubCreateTitle>클럽 만들기</ClubCreateTitle>
      <ClubCreateForm>
        <ClubCreateFormTitle>클럽 이름</ClubCreateFormTitle>
        <ClubCreateFormInput
          type="text"
          placeholder="클럽 이름을 입력해주세요."
        />
        <ClubCreateFormTitle>클럽 설명</ClubCreateFormTitle>
        <ClubCreateFormInput
          type="text"
          placeholder="클럽 설명을 입력해주세요."
        />
        <ClubCreateFormTitle>클럽 이미지</ClubCreateFormTitle>
        <ClubCreateFormInput type="file" />
        <ClubCreateFormTitle>클럽 태그</ClubCreateFormTitle>
        <ClubCreateFormInput
          type="text"
          placeholder="클럽 태그를 입력해주세요."
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
