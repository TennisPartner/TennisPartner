// check login state by using refresh token in local storage
export const checkLoginState = () => {
  const refreshToken = localStorage.getItem("refreshToken");
  if (refreshToken) {
    return true;
  }
  return false;
};
