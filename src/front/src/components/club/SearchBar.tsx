import { useState } from "react";
import styled from "styled-components";
import instance from "../../util/api";
import ErrorText from "../Auth/ErrorText";

const SearchBar = ({ setData, data, setTargetState }: any) => {
  const [searchInput, setSearchInput] = useState("");
  const [searchType, setSearchType] = useState("");
  const [isError, setIsError] = useState(false);
  const baseUrl = import.meta.env.VITE_APP_BACK_END_AWS;

  const searchClub = async () => {
    if (searchInput === "") {
      alert("검색어를 입력해주세요");
      return;
    }
    if (searchType === "") {
      try {
        const res = await instance.get(
          `${baseUrl}/login/api/clubs?page=0&condition=${searchInput}`
        );
        setData(res.data.content);
        setTargetState(false);
      } catch (err) {
        console.log(err);
        setIsError(true);
        setTimeout(() => {
          setIsError(false);
        }, 2000);
      }
      return;
    }
    try {
      const res = await instance.get(
        `${baseUrl}/login/api/clubs?page=0&type=${searchType}&condition=${searchInput}`
      );
      setData(res.data.content);
      setTargetState(false);
    } catch (err) {
      console.log(err);
      setIsError(true);
      setTimeout(() => {
        setIsError(false);
      }, 2000);
    }
  };

  // search bar
  return (
    <SearchBarContainer>
      {isError ? (
        <ErrorText>해당 클럽은 없습니다.</ErrorText>
      ) : (
        <SearchBarInput
          placeholder="검색어를 입력하세요"
          value={searchInput}
          onChange={(e) => setSearchInput(e.target.value)}
        />
      )}
      <TypeSelect onChange={(e) => setSearchType(e.target.value)}>
        <option value="">전체</option>
        <option value="name">클럽명</option>
        <option value="city">지역</option>
        <option value="county">상세지역</option>
      </TypeSelect>
      <SearchBarBtn onClick={() => searchClub()}>찾기</SearchBarBtn>
    </SearchBarContainer>
  );
};

const SearchBarContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 300px;
  height: 100%;
  background-color: ${({ theme }) => theme.colors.white};
  border-radius: 10px;

  box-sizing: border-box;
`;

const SearchBarInput = styled.input`
  height: 100%;
  width: 120px;
  border: none;
  outline: none;
  font-size: 14px;
  font-weight: 400;
  color: ${({ theme }) => theme.colors.black};
  border-radius: 10px;

  padding-left: 10px;
`;

const SearchBarBtn = styled.button`
  display: flex;
  align-items: center;
  justify-content: center;
  min-width: 48px;
  height: 32px;
  outline: none;
  background-color: ${({ theme }) => theme.colors.white};
  border-radius: 10px;

  margin-left: auto;
`;

const TypeSelect = styled.select`
  width: 80px;
  height: 100%;
  border: none;
  outline: none;
  font-size: 14px;
  font-weight: 400;
  color: ${({ theme }) => theme.colors.black};
  border-radius: 10px;
  padding-left: 10px;

  margin-left: auto;
`;

export default SearchBar;
