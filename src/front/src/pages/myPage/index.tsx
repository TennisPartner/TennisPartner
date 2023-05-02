import React, { useContext, useEffect, useRef, useState } from "react";
import styled from "styled-components";
import AuthInput from "../../components/Auth/AuthInput";
import AuthButton from "../../components/Auth/AuthButton";

import { useNavigate } from "react-router-dom";
import axios from "axios";

import { compressImage } from "../../util/compressImage";
import { dataURLtoFile } from "../../util/dataURLtoFile";

import { userContext } from "../../context/userContext";

const MyPage = () => {
  const [nickName, setNickName] = useState("");
  const [gender, setGender] = useState("");
  const [ntrp, setNtrp] = useState("");

  const [userProfile, setUserProfile] = useState({
    nickName: "",
    gender: "",
    ntrp: "",
  });

  const { user, setUser }: any = useContext(userContext);
  const navigate = useNavigate();

  const defaultProfile = "profile.webp";

  const baseUrl = import.meta.env.VITE_APP_BACK_END_AWS;

  // web RTC 관련 코드
  const videoRef = useRef<HTMLVideoElement>(null);
  const canvasRef = useRef<HTMLCanvasElement>(null);
  const [picture, setPicture] = useState<string | null>(null);
  const [isVideo, setIsVideo] = useState(false);
  const accessToken = localStorage.getItem("accessToken");

  const startVideo = () => {
    setIsVideo(true);

    navigator.mediaDevices
      .getUserMedia({ video: true })
      .then((stream) => {
        if (videoRef.current) {
          videoRef.current.srcObject = stream;
        }
      })
      .catch((error) => {
        console.error(error);
      });
  };

  const takePicture = () => {
    if (videoRef.current && canvasRef.current) {
      const canvas = canvasRef.current;
      const context = canvas.getContext("2d");
      if (context) {
        canvas.width = videoRef.current.videoWidth;
        canvas.height = videoRef.current.videoHeight;
        context.drawImage(videoRef.current, 0, 0);
        const dataUrl = canvas.toDataURL("image/png");
        // dataURL을 File로 변환
        const file = dataURLtoFile(dataUrl, "profile.png");
        // 이미지 압축
        compressImage(file, (result: any) => {
          setPicture(result);
        });
        setPicture(dataUrl);
      }

      setIsVideo(false);
    }
  };

  // 로그아웃
  const logout = () => {
    localStorage.removeItem("accessToken");
    localStorage.removeItem("refreshToken");
    setUser(null);
    navigate("/");
  };

  // post user info by using axios
  const postUserInfo = async () => {
    const result = await axios
      .patch(
        `${baseUrl}/login/api/users`,
        {
          userNickname: nickName,
          userGender: gender,
          userNtrp: ntrp,
        },
        {
          headers: {
            contentType: "application/json",
            Authorization: `Bearer ${accessToken}`,
          },
        }
      )
      .then((res) => {
        return res;
      })
      .catch((err) => {
        console.log("err", err);
      });
  };

  // get user info by using axios
  useEffect(() => {
    if (!accessToken) {
      navigate("/auth/login");
    }

    //get user info
    const getUserInfo = async () => {
      const result = await axios
        .get(`${baseUrl}/login/api/users`, {
          headers: {
            contentType: "application/json",
            Authorization: `Bearer ${accessToken}`,
          },
        })
        .then((res) => {
          if (res.data.userNickname) {
            setNickName(res.data.userNickname);
          }
          if (res.data.userGender) {
            setGender(res.data.userGender);
          }
          if (res.data.userNtrp) {
            setNtrp(res.data.userNtrp);
          }

          return res;
        })
        .catch((err) => {
          console.log("err", err);
          navigate("/auth/login");
        });
    };
    getUserInfo();
  }, []);

  return (
    <CreateProfileContainer>
      {!isVideo ? (
        <LogoutButton onClick={logout}>로그아웃</LogoutButton>
      ) : (
        <LogoutButton onClick={takePicture}>사진 찍기</LogoutButton>
      )}
      <h1>내 정보 등록</h1>
      <ProfilePicture onClick={startVideo}>
        {isVideo ? (
          <>
            <video width={200} ref={videoRef} autoPlay />{" "}
            <canvas ref={canvasRef} style={{ display: "none" }} />
          </>
        ) : (
          <img src={picture ? picture : defaultProfile} alt="프로필 사진" />
        )}
      </ProfilePicture>

      <NickNameBox>
        <AuthInput
          value={nickName}
          onChange={(e) => setNickName(e.target.value)}
          type="text"
          placeholder="닉네임을 입력해주세요."
        />
      </NickNameBox>
      <GenderBox>
        <h2>성별</h2>
        <GenderCheck>
          <input
            type="radio"
            name="gender"
            id="man"
            value="m"
            checked={gender === "m"}
            onChange={() => setGender("m")}
          />
          <label htmlFor="man">남자</label>
          <input
            type="radio"
            name="gender"
            id="girl"
            value="f"
            checked={gender === "f"}
            onChange={() => setGender("f")}
          />
          <label htmlFor="girl">여자</label>
        </GenderCheck>
      </GenderBox>
      <NTRPBox>
        <label htmlFor="ntrp">NTRP</label>
        <NTRPCheck>
          <input
            type="range"
            id="ntrp"
            name="ntrp"
            min="0"
            max="5"
            step="0.5"
            value={ntrp}
            onChange={(e) => setNtrp(e.target.value)}
          />
          <span>{ntrp}</span>
        </NTRPCheck>
      </NTRPBox>
      <AuthButton style={{ marginTop: "0px" }} onClick={postUserInfo}>
        수정하기
      </AuthButton>
    </CreateProfileContainer>
  );
};

const LogoutButton = styled.button`
  position: absolute;
  top: 20px;
  right: 20px;
  width: 100px;
  height: 40px;
  border-radius: 10px;
  background-color: ${({ theme }) => theme.colors.tennis};
  border: 1px solid #000000;

  font-style: bold;
  font-weight: 500;
  font-size: 16px;
  line-height: 20px;

  z-index: 100;
`;

const CreateProfileContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 16px;

  width: 100%;
  height: 100vh;

  h1 {
    display: flex;
    width: 300px;

    font-style: normal;
    font-weight: 700;
    font-size: 32px;
    line-height: 39px;
    justify-content: center;
  }
`;

const NickNameBox = styled.div`
  display: flex;
  align-items: left;
  justify-content: center;

  width: 300px;
`;

const ProfilePicture = styled.div`
  img {
    width: 150px;
    height: 150px;
    border-radius: 50%;
  }
`;

const GenderBox = styled.div`
  display: flex;
  align-items: left;
  justify-content: center;
  gap: 10px;

  width: 300px;

  h2 {
    color: #6b6b6b;

    font-size: 32px;
    width: 100px;
  }
`;

const GenderCheck = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;

  gap: 20px;
  width: 100%;
`;

const NTRPBox = styled.div`
  display: flex;
  align-items: left;

  width: 300px;

  label {
    color: #6b6b6b;

    font-size: 32px;
    width: 100px;
  }
`;

const NTRPCheck = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;

  gap: 10px;
  input {
    width: 170px;
  }
  span {
    width: 30px;
    font-size: medium;
    font-weight: bold;
  }
`;

export default MyPage;
