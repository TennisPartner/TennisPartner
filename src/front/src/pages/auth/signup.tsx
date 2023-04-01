import styled from "styled-components";
import AuthButton from "../../components/Auth/AuthButton";
import AuthInput from "../../components/Auth/AuthInput";
import AuthLink from "../../components/Auth/AuthLink";

const Signup = () => {
  return (
    <SingupContainer>
      <h1>회원가입</h1>
      <AuthInput titleMessage="Email" inputMessage="아이디를 입력해주세요." />
      <AuthInput titleMessage="Username" inputMessage="이름을 입력해주세요." />
      <AuthInput
        titleMessage="Password"
        inputMessage="비밀번호를 입력해주세요."
      />
      <div>
        <AuthButton>SIGNUP</AuthButton>
        <AuthLink toURL="login">로그인하러가기</AuthLink>
      </div>
    </SingupContainer>
  );
};

const SingupContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  height: 640px;

  padding: 0 30px 0 30px;
  gap: 30px;

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

    margin-bottom: 32px;
  }
`;

export default Signup;