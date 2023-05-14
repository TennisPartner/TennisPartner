import GlobalLayout from "./pages/_layout";
import MainPageIndex from "./pages/mainPage/index";
import MyPageIndex from "./pages/myPage/index";
import AuthCreateProfile from "./pages/auth/createProfile";
import AuthLogin from "./pages/auth/login";
import AuthSignup from "./pages/auth/signup";
import BoardId from "./pages/board/[id]";
import BoardBoardCreate from "./pages/board/boardCreate";
import BoardBoardPage from "./pages/board/boardPage";
import ClubDetail from "./pages/club/[id]";
import ClubClubCreate from "./pages/club/clubCreate";
import ClubClubPage from "./pages/club/clubPage";
import ClubClubBoardCreate from "./pages/club/ClubBoardCreate";
import ClubBoardDetail from "./pages/club/board/[id]";
import Matching from "./pages/mainPage/Matching";

export const routes = [
  {
    path: "/",
    element: <GlobalLayout />,
    children: [
      { path: "/", element: <MainPageIndex />, index: true },
      { path: "/myPage", element: <MyPageIndex />, index: true },
      { path: "/auth/CreateProfile", element: <AuthCreateProfile /> },
      { path: "/auth/Login", element: <AuthLogin /> },
      { path: "/auth/Signup", element: <AuthSignup /> },
      { path: "/board/:id", element: <BoardId /> },
      { path: "/board/BoardCreate", element: <BoardBoardCreate /> },
      { path: "/board/BoardPage", element: <BoardBoardPage /> },
      { path: "/club/:id", element: <ClubDetail /> },
      { path: "/club/ClubCreate", element: <ClubClubCreate /> },
      { path: "/club/ClubPage", element: <ClubClubPage /> },
      { path: "/club/ClubBoardCreate", element: <ClubClubBoardCreate /> },
      { path: "/club/board/:id", element: <ClubBoardDetail /> },
      {
        path: "/matching",
        element: <Matching />,
      },
    ],
  },
];

export const pages = [
  { route: "/mainPage" },
  { route: "/myPage" },
  { route: "/auth/CreateProfile" },
  { route: "/auth/Login" },
  { route: "/auth/Signup" },
  { route: "/board/:id" },
  { route: "/board/BoardCreate" },
  { route: "/board/BoardPage" },
  { route: "/club/:id" },
  { route: "/club/ClubCreate" },
  { route: "/club/ClubPage" },
  { route: "/club/ClubBoardCreate" },
  { route: "/club/ClubBoardDetail" },
  { route: "/club/board/:id" },
  { route: "/matching" },
];
