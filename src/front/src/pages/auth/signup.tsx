import { useContext, useEffect, useState } from "react";

import styled from "styled-components";
import AuthButton from "../../components/Auth/AuthButton";
import AuthInput from "../../components/Auth/AuthInput";
import AuthLink from "../../components/Auth/AuthLink";

import axios from "axios";

import { useNavigate } from "react-router-dom";
import { checkLoginState } from "../../util/checkLoginState";
import { userContext } from "../../context/userContext";
import ErrorText from "../../components/Auth/ErrorText";

interface contextProps {
  setUser: React.Dispatch<React.SetStateAction<string>>;
}

const Signup = () => {
  const navigate = useNavigate();

  const baseUrl = import.meta.env.VITE_APP_BACK_END_AWS;

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  // const [passwordCheck, setPasswordCheck] = useState("");
  const [userName, setUserName] = useState("");
  const [isError, setIsError] = useState(false);

  const { setUser }: contextProps = useContext(userContext);

  // signup button click event handler function
  const signup = async () => {
    const response = await axios.post(`${baseUrl}/api/join`, {
      userId: email,
      userPassword: password,
      userName: userName,
    });

    if (response.data.status === 401) {
      setIsError(true);

      setTimeout(() => {
        setIsError(false);
      }, 3000);

      return;
    }

    if (response.data.status !== 401) {
      // login
      const loginResponse = await axios.post(`${baseUrl}/api/login`, {
        userId: email,
        userPassword: password,
      });

      // save token to local storage
      localStorage.setItem("accessToken", loginResponse.data.accessToken);
      localStorage.setItem("refreshToken", loginResponse.data.refreshToken);
      axios.defaults.headers.common[
        "Authorization"
      ] = `Bearer ${loginResponse.data.accessToken}`;
      setUser(email);
      // redirect to main page
      navigate("/");
    }
  };

  useEffect(() => {
    if (checkLoginState()) navigate("/");
  }, []);

  return (
    <SingupContainer>
      <FormContainer>
        <h1>Sign Up</h1>
        <label htmlFor="email">아이디</label>
        <AuthInput
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          placeholder="아이디를 입력해주세요."
          type="email"
        />
        <label htmlFor="password">비밀번호</label>
        <AuthInput
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          placeholder="비밀번호을 입력해주세요."
          type="password"
        />
        {/* <label htmlFor="passwordCheck">Password Check</label>
        <AuthInput
          value={passwordCheck}
          onChange={(e) => setPasswordCheck(e.target.value)}
          placeholder="Password Check을 입력해주세요."
          type="password"
        /> */}
        <label htmlFor="userName">사용자 이름</label>
        <AuthInput
          value={userName}
          onChange={(e) => setUserName(e.target.value)}
          placeholder="사용자이름을 입력해주세요."
          type="text"
        />
        {isError ? (
          <ErrorText> 작성된 내용을 확인해주세요.</ErrorText>
        ) : (
          <ErrorText></ErrorText>
        )}
        <AuthButton onClick={signup}>Sign Up</AuthButton>
        <AuthLink toURL="login">Already have an account?</AuthLink>
      </FormContainer>
    </SingupContainer>
  );
};

const SingupContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  height: 100vh;

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

    margin-bottom: 16px;
  }
`;

const FormContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  height: 100vh;

  gap: 20px;

  label {
    display: flex;
    align-items: left;
    width: 300px;

    font-style: normal;
    font-weight: 700;
    font-size: 20px;
    line-height: 24px;
  }
`;

export default Signup;
