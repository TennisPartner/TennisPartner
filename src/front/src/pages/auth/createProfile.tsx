import styled from "styled-components";
import AuthButton from "../../components/Auth/AuthButton";
import AuthInput from "../../components/Auth/AuthInput";
import { ChangeEvent, useState } from "react";

const CreateProfile = () => {
  const [nickName, setNickName] = useState<string>("");

  return (
    <CreateProfileContainer>
      <h1>내 정보 등록</h1>
      <ProfilePicture>
        <img
          src="https://dimg.donga.com/wps/NEWS/IMAGE/2022/01/28/111500268.2.jpg"
          alt="프로필 사진"
        />
      </ProfilePicture>
      <NickNameBox>
        <AuthInput
          type="text"
          placeholder="닉네임을 입력해주세요"
          value={nickName}
          onChange={(e) => {
            setNickName(e.target.value);
          }}
        />
      </NickNameBox>
      <GenderBox>
        <h2>성별</h2>
        <GenderCheck>
          <input type="radio" name="gender" id="huey" />
          <label htmlFor="man">남자</label>
          <input type="radio" name="gender" />
          <label htmlFor="girl">여자</label>
        </GenderCheck>
      </GenderBox>
      <NTRPBox>
        <h2>NTRP</h2>
        <NTRPCheck>
          <input
            type="range"
            id="volume"
            name="volume"
            min="0"
            max="10"
            step="0.5"
          />
        </NTRPCheck>
      </NTRPBox>
      <AuthButton>Register</AuthButton>
    </CreateProfileContainer>
  );
};

const CreateProfileContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 32px;

  margin-top: 40px;

  h1 {
    display: flex;
    align-items: left;
    width: 300px;

    font-style: normal;
    font-weight: 700;
    font-size: 32px;
    line-height: 39px;
    display: flex;
    align-items: center;
  }
`;

const ProfilePicture = styled.div`
  img {
    width: 100px;
    height: 100px;
    border-radius: 50%;
  }
`;

const NickNameBox = styled.div`
  display: flex;
  align-items: left;
  justify-content: center;

  width: 300px;
`;

const GenderBox = styled.div`
  display: flex;
  align-items: left;
  justify-content: center;
  gap: 10px;

  width: 300px;

  h2 {
    color: #6b6b6b;

    font-size: 32px;
    width: 100px;
  }
`;

const GenderCheck = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;

  gap: 20px;
  width: 100%;
`;

const NTRPBox = styled.div`
  display: flex;
  align-items: left;
  justify-content: center;
  gap: 10px;

  width: 300px;
  margin-bottom: 20px;
  h2 {
    color: #6b6b6b;

    font-size: 32px;
    width: 100px;
  }
`;

const NTRPCheck = styled.div`
  input {
    width: 200px;
  }
`;

export default CreateProfile;
