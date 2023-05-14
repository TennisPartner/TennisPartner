import { useState } from "react";
import styled from "styled-components";
import instance from "../../util/api";

const SearchBar = ({ setData, data }: any) => {
  const [searchInput, setSearchInput] = useState("");
  const [searchType, setSearchType] = useState("");

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
        console.log(res);
        setData(res.data.content);
      } catch (err) {
        console.log(err);
      }
      return;
    } else
      try {
        const res = await instance.get(
          `${baseUrl}/login/api/clubs?page=0&type=${searchType}&condition=${searchInput}`
        );
        console.log(res);
        setData(res.data.content);
      } catch (err) {
        console.log(err);
      }
  };

  // search bar
  return (
    <SearchBarContainer>
      <SearchBarInput
        placeholder="검색어를 입력하세요"
        value={searchInput}
        onChange={(e) => setSearchInput(e.target.value)}
      />
      <TypeSelect>
        <select onChange={(e) => setSearchType(e.target.value)}>
          <option value="">전체</option>
          <option value="name">제목</option>
          <option value="city">지역</option>
          <option value="county">상세 지역</option>
        </select>
      </TypeSelect>
      <SearchBarBtn onClick={() => searchClub()}>찾기</SearchBarBtn>
    </SearchBarContainer>
  );
};

const SearchBarContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
  width: 300px;
  height: 100%;
  background-color: ${({ theme }) => theme.colors.white};
  border-radius: 10px;

  box-sizing: border-box;
`;

const SearchBarInput = styled.input`
  height: 100%;
  width: 140px;
  border: none;
  outline: none;
  font-size: 16px;
  font-weight: 400;
  color: ${({ theme }) => theme.colors.black};
  border-radius: 10px;

  padding-left: 10px;
`;

const SearchBarBtn = styled.button`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 48px;
  height: 32px;
  outline: none;
  background-color: ${({ theme }) => theme.colors.white};
  border-radius: 10px;
`;

const TypeSelect = styled.div`
  width: 80px;
  height: 40px;
  background-color: ${({ theme }) => theme.colors.white};
  border-radius: 10px;

  select {
    width: 100%;
    height: 100%;
    border: none;
    outline: none;
    font-size: 12px;
    font-weight: 400;
    color: ${({ theme }) => theme.colors.black};
    background-color: ${({ theme }) => theme.colors.white};
    border-radius: 10px;
  }
`;

export default SearchBar;
