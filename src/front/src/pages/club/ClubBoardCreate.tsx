import React, { useState } from "react";
import styled from "styled-components";
import axios from "axios";
import { useLocation } from "react-router";
import { useNavigate } from "react-router-dom";
const ClubBoardCreate = () => {
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const [meetDt, setMeetDt] = useState("");
  const [meetTime, setMeetTime] = useState("");

  // T(text) : 일반글, M(meet) : 모집글
  const [clubBoardType, setClubBoardType] = useState("T");

  const baseUrl = import.meta.env.VITE_APP_BACK_END_AWS;
  const accessToken = localStorage.getItem("accessToken");
  const navigate = useNavigate();

  const { state } = useLocation();
  const clubIdx = state?.clubIdx;

  const handleSubmit = async (event: any) => {
    event.preventDefault();
    setErrorMessage("");

    const payload = {
      clubBoardContents: content,
      clubBoardTitle: title,
      clubBoardType: clubBoardType,
      clubIdx: +clubIdx,
      meetDt: meetDt ? meetDt + "T" + meetTime : null,
      wantedCnt: 0,
      // useYn: "y",
    };
    try {
      const result = await axios.post(
        `${baseUrl}/login/api/clubs/${clubIdx}/boards?clubIdx=${clubIdx}`,
        payload,
        {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        }
      );
      navigate(`/club/:${clubIdx}`);
    } catch (error) {
      console.log("error", error);
    }
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
        <Select
          id="clubBoardType"
          name="clubBoardType"
          value={clubBoardType}
          onChange={(event) => setClubBoardType(event.target.value)}
          required
        >
          <option value="T">일반글</option>
          <option value="M">모집글</option>
        </Select>
        {clubBoardType === "M" && (
          <TimeBox>
            <Input
              type="date"
              id="meetDt"
              name="meetDt"
              value={meetDt}
              onChange={(event) => setMeetDt(event.target.value)}
              required
              style={{ width: "50%" } as React.CSSProperties}
            />

            <Input
              type="time"
              id="meetTime"
              name="meetTime"
              value={meetTime}
              onChange={(event) => setMeetTime(event.target.value)}
              required
              style={{ width: "50%" } as React.CSSProperties}
            />
          </TimeBox>
        )}

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

const Select = styled.select`
  width: 100%;
  padding: 10px;
  font-size: 16px;
  border-radius: 4px;
  border: 1px solid #ccc;

  text-align: center;

  margin: 12px 0;
`;

const TimeBox = styled.div`
  display: flex;
  justify-content: space-between;
  width: 100%;
`;

const Input = styled.input`
  width: 100%;
  margin: 12px 0;
  padding: 10px;
  font-size: 16px;
  border-radius: 4px;
  border: 1px solid #ccc;

  text-align: center;
  box-sizing: border-box;
`;

const TextArea = styled.textarea`
  width: 100%;
  height: 200px;
  margin: 10px 0;
  padding: 10px;
  font-size: 16px;
  border-radius: 4px;
  border: 1px solid #ccc;

  box-sizing: border-box;
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

export default ClubBoardCreate;
