import { useEffect, useState } from "react";

import styled from "styled-components";
import AuthButton from "../../components/Auth/AuthButton";
import AuthInput from "../../components/Auth/AuthInput";
import AuthLink from "../../components/Auth/AuthLink";

import axios from "axios";

import { useNavigate } from "react-router-dom";
import { checkLoginState } from "../../util/checkLoginState";

const Signup = () => {
  const navigate = useNavigate();

  const baseUrl = import.meta.env.VITE_APP_BACK_END_AWS;

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  // const [passwordCheck, setPasswordCheck] = useState("");
  const [userName, setUserName] = useState("");
  const [errorMessege, setErrorMessege] = useState("");

  // signup button click event handler function
  const signup = async () => {
    const response = await axios.post(`${baseUrl}/api/join`, {
      userId: email,
      userPassword: password,
      userName: userName,
    });
    console.log(response);
    if (response.status === 200) {
      // login
      const loginResponse = await axios.post(`${baseUrl}/api/login`, {
        userId: email,
        userPassword: password,
      });
      if (loginResponse.status === 200) {
        // save token to local storage
        localStorage.setItem("accessToken", loginResponse.data.accessToken);
        localStorage.setItem("refreshToken", loginResponse.data.refreshToken);
        axios.defaults.headers.common[
          "Authorization"
        ] = `Bearer ${loginResponse.data.accessToken}`;

        // redirect to main page
        navigate("/");
      }
    }
  };

  useEffect(() => {
    if (checkLoginState()) navigate("/");
  }, []);

  return (
    <SingupContainer>
      <FormContainer>
        <h1>Sign Up</h1>
        <label htmlFor="email">Email</label>
        <AuthInput
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          placeholder="Email를 입력해주세요."
          type="email"
        />
        <label htmlFor="password">Password</label>
        <AuthInput
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          placeholder="Password을 입력해주세요."
          type="password"
        />
        {/* <label htmlFor="passwordCheck">Password Check</label>
        <AuthInput
          value={passwordCheck}
          onChange={(e) => setPasswordCheck(e.target.value)}
          placeholder="Password Check을 입력해주세요."
          type="password"
        /> */}
        <label htmlFor="userName">userName</label>
        <AuthInput
          value={userName}
          onChange={(e) => setUserName(e.target.value)}
          placeholder="userName을 입력해주세요."
          type="text"
        />
        <ErrorMessege>{errorMessege}</ErrorMessege>
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

const ErrorMessege = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 300px;
  height: 24px;

  font-style: normal;
  font-weight: 700;
  font-size: 20px;
  line-height: 24px;
  color: red;
`;

export default Signup;
