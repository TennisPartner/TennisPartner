import { useState } from "react";

import styled from "styled-components";
import AuthButton from "../../components/Auth/AuthButton";
import AuthInput from "../../components/Auth/AuthInput";
import AuthLink from "../../components/Auth/AuthLink";

const Signup = () => {
  const baseUrl = import.meta.env.VITE_APP_BACK_END_URL;

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [passwordCheck, setPasswordCheck] = useState("");

  // signup button click event handler function
  const signup = async () => {
    const response = await fetch(`${baseUrl}/auth/signup`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        email,
        password,
        passwordCheck,
      }),
    });
    const data = await response.json();
    console.log(data);
  };

  return (
    <SingupContainer>
      <FormContainer>
        <h1>Sign Up</h1>
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
        <label htmlFor="passwordCheck">Password Check</label>
        <AuthInput
          value={passwordCheck}
          onChange={(e) => setPasswordCheck(e.target.value)}
          placeholder="Password Check"
          type="password"
        />
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

const FormContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  width: 300px;
  height: 400px;

  padding: 0 30px 0 30px;
  gap: 30px;

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
