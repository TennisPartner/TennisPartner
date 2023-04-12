import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import useUserDataStore from "../../zustand/store";
import axios from "axios";

const ClubCreate = () => {
  const navigate = useNavigate();

  const setHasClub = useUserDataStore((state: any) => state.setHasClub);

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
        navigate("/club/clubPage");
      })
      .catch((err) => {
        console.log(err);
      });

    console.log(response);
  };

  const onClickHandler = () => {
    postClub();
  };

  // useEffect(() => {
  //   const fetchCity = async () => {
  //     const response = await axios.get(
  //       "https://api.vworld.kr/req/data?key=CEB52025-E065-364C-9DBA-44880E3B02B8&domain=http://localhost:8080&service=data&version=2.0&request=getfeature&format=json&size=1000&page=1&geometry=false&attribute=true&crs=EPSG:3857&geomfilter=BOX(13663271.680031825,3894007.9689600193,14817776.555251127,4688953.0631258525)&data=LT_C_ADSIGG_INFO&callback=jQuery1111021026584809524618_1681218717270&attrfilter=sig_cd%3Alike%3A11&_=16812"
  //     );
  //     console.log(response);
  //   };
  //   fetchCity();
  // }, []);

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
        <Selection
          name="pets"
          id="pet-select"
          value={clubCounty}
          onChange={(e) => setClubCounty(e.target.value)}
        >
          <option value="재학이집">재학이집</option>
          <option value="준호네집">준호네집</option>
          <option value="동철이집">동철이집</option>
          <option value="길거리팁">길거리팁</option>
        </Selection>

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