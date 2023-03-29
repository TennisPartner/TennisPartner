import styled from "styled-components";
import AuthButton from "../../components/Auth/AuthButton";
import AuthInput from "../../components/Auth/AuthInput";

const createProfile = () => {
  return (
    <CreateProfileContainer>
      <h1>내 정보 등록</h1>
      <ProfilePicture>
        <img
          src="https://dimg.donga.com/wps/NEWS/IMAGE/2022/01/28/111500268.2.jpg"
          alt="프로필 사진"
        />
      </ProfilePicture>
      <AuthInput titleMessage="닉네임" inputMessage="닉네임을 입력해주세요." />
      <GenderCheck>
        <div>성별</div>
        <input type="radio" name="gender">
          남자
        </input>
      </GenderCheck>
      <NTRPCheck></NTRPCheck>
      <AuthButton>Register</AuthButton>
    </CreateProfileContainer>
  );
};

const CreateProfileContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 10px;

  height: 640px;

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

    margin-bottom: 72px;
  }
`;

const ProfilePicture = styled.div`
  img {
    width: 100px;
    height: 100px;
    border-radius: 50%;
  }
`;
const GenderCheck = styled.div``;
const NTRPCheck = styled.div``;

export default createProfile;
