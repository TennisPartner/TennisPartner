import axios from "axios";

const baseUrl = import.meta.env.VITE_APP_BACK_END_AWS;

const instance = axios.create({
  baseURL: baseUrl,
});

/**
 1. 요청 인터셉터
 2개의 콜백 함수를 받습니다.
 */
instance.interceptors.request.use(
  (config) => {
    // HTTP Authorization 요청 헤더에 jwt-token을 넣음
    // 서버측 미들웨어에서 이를 확인하고 검증한 후 해당 API에 요청함.
    const token = localStorage.getItem("accessToken");
    try {
      if (token) {
        config.headers.Authorization = `Bearer ${token}`;
      }
      return config;
    } catch (err) {
      console.error("[_axios.interceptors.request] config : " + err);
    }
    return config;
  },
  (error) => {
    // 요청 에러 직전 호출됩니다.
    return Promise.reject(error);
  }
);

/**
 2. 응답 인터셉터
 2개의 콜백 함수를 받습니다.
 */
instance.interceptors.response.use(
  (res) => {
    /*
        http status가 200인 경우
        응답 성공 직전 호출됩니다.
        .then() 으로 이어집니다.
    */

    if (res.data.status === 401) {
      const accessToken = localStorage.getItem("accessToken");
      const refreshToken = localStorage.getItem("refreshToken");
      const result = axios
        .post(
          `${baseUrl}/api/gen`,
          {},
          {
            headers: {
              contentType: "application/json",
              Authorization: `Bearer ${refreshToken}`,
              RefreshAuthorization: `${refreshToken}`,
            },
          }
        )
        .then((res) => {
          localStorage.setItem("accessToken", res.data.accessToken);
          localStorage.setItem(
            "refreshToken",
            res.data.refreshToken.refreshToken
          );
        })
        .catch((err) => {
          console.log("err", err);
          // navigate("/auth/login");
        });
    }

    return res;
  },

  (error) => {
    /*
        http status가 200이 아닌 경우
        응답 에러 직전 호출됩니다.
        .catch() 으로 이어집니다.
    */

    return Promise.reject(error);
  }
);

export default instance;
