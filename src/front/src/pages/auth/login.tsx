import { useState } from "react";

import styled from "styled-components";
import AuthButton from "../../components/Auth/AuthButton";
import AuthInput from "../../components/Auth/AuthInput";
import AuthLink from "../../components/Auth/AuthLink";

const Login = () => {
  const baseUrl = import.meta.env.VITE_APP_BACK_END_URL;

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  // login button click event handler function
  const login = async () => {
    const response = await fetch(`${baseUrl}/auth/login`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        email,
        password,
      }),
    });
    const data = await response.json();
    console.log(data);
  };

  return (
    <LoginContainer>
      <FormContainer>
        <h1>Log In</h1>
        <label htmlFor="email">Email</label>
        <AuthInput
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          placeholder="Email"
          type="email"
        />
        <label htmlFor="password">Password</label>
        <AuthInput
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          placeholder="Password"
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

  height: 640px;

  padding: 0 30px 0 30px;

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
