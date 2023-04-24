import React, { useState } from "react";
import styled from "styled-components";
import axios from "axios";
import { useLocation } from "react-router";

const clubBoardCreate = () => {
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [errorMessage, setErrorMessage] = useState("");

  const baseUrl = import.meta.env.VITE_APP_BACK_END_AWS;

  const { state } = useLocation();
  const clubIdx = state.clubIdx;

  const handleSubmit = async (event: any) => {
    event.preventDefault();
    setErrorMessage("");

    const accessToken = localStorage.getItem("accessToken");

    console.log("clubIdx", clubIdx);
    const result = await axios
      .post(
        `${baseUrl}/login/api/clubs/${clubIdx}/boards`,
        {
          clubBoardContents: content,
          clubBoardIdx: clubIdx,
          clubBoardTitle: title,
          clubBoardType: "club",
          wantedCnt: 3,
        },
        {
          headers: {
            Authorization: `Bearer ${accessToken}`,
            clubIdx,
          },
        }
      )
      .then((res) => {
        console.log("first", res);
        return res;
      })
      .catch((err) => {
        console.log("err", err);
      });
    console.log("result", result?.data);
  };

  return (
    <Container>
      <Form onSubmit={handleSubmit}>
        {errorMessage && <ErrorMessage>{errorMessage}</ErrorMessage>}
        <label htmlFor="title">Title</label>
        <Input
          type="text"
          id="title"
          name="title"
          value={title}
          onChange={(event) => setTitle(event.target.value)}
          required
        />
        <label htmlFor="content">Content</label>
        <TextArea
          id="content"
          name="content"
          value={content}
          onChange={(event) => setContent(event.target.value)}
          required
        />
        <Button type="submit">Save</Button>
      </Form>
    </Container>
  );
};

const Container = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100vh;

  background-color: ${({ theme }) => theme.colors.tennis};
`;

const Form = styled.form`
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 90%;
  max-width: 500px;
`;

const Input = styled.input`
  width: 100%;
  margin: 10px 0;
  padding: 10px;
  font-size: 16px;
  border-radius: 4px;
  border: 1px solid #ccc;
`;

const TextArea = styled.textarea`
  width: 100%;
  height: 300px;
  margin: 10px 0;
  padding: 10px;
  font-size: 16px;
  border-radius: 4px;
  border: 1px solid #ccc;
`;

const Button = styled.button`
  margin: 10px 0;
  padding: 10px 20px;
  font-size: 16px;
  border-radius: 4px;
  background-color: #008cba;
  color: #fff;
  border: none;
  cursor: pointer;

  &:hover {
    background-color: #005f78;
  }
`;

const ErrorMessage = styled.div`
  color: red;
  margin-bottom: 10px;
`;

export default clubBoardCreate;
