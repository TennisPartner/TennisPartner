import React from "react";
import GlobalLayout from "./pages/_layout";

const MainPageIndex = React.lazy(() => import("./pages/mainPage/index"));
const MyPageIndex = React.lazy(() => import("./pages/myPage/index"));
const AuthCreateProfile = React.lazy(
  () => import("./pages/auth/CreateProfile")
);
const AuthLogin = React.lazy(() => import("./pages/auth/login"));
const AuthSignup = React.lazy(() => import("./pages/auth/signup"));
const BoardId = React.lazy(() => import("./pages/board/[id]"));
const BoardBoardCreate = React.lazy(() => import("./pages/board/BoardCreate"));
const BoardBoardPage = React.lazy(() => import("./pages/board/BoardPage"));
const ClubDetail = React.lazy(() => import("./pages/club/[id]"));
const ClubClubCreate = React.lazy(() => import("./pages/club/ClubCreate"));
const ClubClubPage = React.lazy(() => import("./pages/club/ClubPage"));
const ClubClubBoardCreate = React.lazy(
  () => import("./pages/club/ClubBoardCreate")
);
const ClubBoardDetail = React.lazy(() => import("./pages/club/board/[id]"));

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
];
