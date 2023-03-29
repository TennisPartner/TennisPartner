import styled from "styled-components";
import AuthButton from "../../components/Auth/AuthButton";
import AuthInput from "../../components/Auth/AuthInput";

const login = () => {
  return (
    <LoginContainer>
      <h1>로그인</h1>
      <AuthInput titleMessage="Email" inputMessage="아이디를 입력해주세요." />
      <AuthInput
        titleMessage="Password"
        inputMessage="비밀번호를 입력해주세요."
      />

      <AuthButton>LOGIN</AuthButton>
    </LoginContainer>
  );
};

const LoginContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 10px;

  height: 100vh;

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

export default login;
