import React, { useContext, useEffect, useState } from "react";

import styled from "styled-components";
import AuthButton from "../../components/Auth/AuthButton";
import AuthInput from "../../components/Auth/AuthInput";
import AuthLink from "../../components/Auth/AuthLink";

import axios from "axios";

import { useNavigate } from "react-router-dom";

import { checkLoginState } from "../../util/checkLoginState";

import { userContext } from "../../context/userContext";

interface contextProps {
  setUser: React.Dispatch<React.SetStateAction<string>>;
}

const Login = () => {
  const baseUrl = import.meta.env.VITE_APP_BACK_END_AWS;

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const navigate = useNavigate();

  const { setUser }: contextProps = useContext(userContext);

  // login button click event handler function
  const login = async (e: React.MouseEvent<HTMLButtonElement>) => {
    e.preventDefault();

    const response = await axios.post(`${baseUrl}/api/login`, {
      userId: email,
      userPassword: password,
    });

    if (response.status === 200) {
      // save token to local storage
      localStorage.setItem("accessToken", response.data.accessToken);
      localStorage.setItem("refreshToken", response.data.refreshToken);

      document.cookie = `user=${email}`;

      axios.defaults.headers.common[
        "Authorization"
      ] = `Bearer ${response.data.accessToken}`;
      setUser(email);

      // redirect to main page
      navigate("/");
    }
  };

  useEffect(() => {
    if (checkLoginState()) {
      navigate("/");
    }
  }, []);

  return (
    <LoginContainer>
      <FormContainer>
        <h1>Log In</h1>
        <label htmlFor="email">아이디</label>
        <AuthInput
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          placeholder="아이디를 입력해주세요."
          type="text"
        />
        <label htmlFor="password">비밀번호</label>
        <AuthInput
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          placeholder="비밀번호를 입력해주세요."
          type="password"
        />
        <AuthButton onClick={login}>Log In</AuthButton>
        <AuthLink toURL="signup">Don't have an account?</AuthLink>
      </FormContainer>
    </LoginContainer>
  );
};

const LoginContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 40px;

  padding: 0px 30px 0 30px;

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

const FormContainer = styled.form`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 20px;

  width: 300px;

  label {
    font-style: normal;
    font-weight: 700;
    font-size: 16px;
    line-height: 19px;
    display: flex;
    align-items: left;
    letter-spacing: 0.01em;

    width: 100%;
  }
`;

export default Login;
