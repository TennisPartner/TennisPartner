import React from "react";
import GlobalLayout from "./pages/_layout";

const DynamicMainPageIndex = React.lazy(() => import("./pages/mainPage/index"));
const DynamicMyPageIndex = React.lazy(() => import("./pages/myPage/index"));
const DynamicAuthCreateProfile = React.lazy(
  () => import("./pages/auth/CreateProfile")
);
const DynamicAuthLogin = React.lazy(() => import("./pages/auth/Login"));
const DynamicAuthSignup = React.lazy(() => import("./pages/auth/Signup"));
const DynamicBoardId = React.lazy(() => import("./pages/board/[id]"));
const DynamicBoardBoardCreate = React.lazy(
  () => import("./pages/board/boardCreate")
);
const DynamicBoardBoardPage = React.lazy(
  () => import("./pages/board/boardPage")
);
const DynamicClubId = React.lazy(() => import("./pages/club/[id]"));
const DynamicClubClubCreate = React.lazy(
  () => import("./pages/club/clubCreate")
);
const DynamicClubClubPage = React.lazy(() => import("./pages/club/ClubPage"));

export const routes = [
  {
    path: "/",
    element: <GlobalLayout />,
    children: [
      { path: "/MainPage", element: <DynamicMainPageIndex />, index: true },
      { path: "/MyPage", element: <DynamicMyPageIndex />, index: true },
      { path: "/auth/CreateProfile", element: <DynamicAuthCreateProfile /> },
      { path: "/auth/Login", element: <DynamicAuthLogin /> },
      { path: "/auth/Signup", element: <DynamicAuthSignup /> },
      { path: "/board/:id", element: <DynamicBoardId /> },
      { path: "/board/boardCreate", element: <DynamicBoardBoardCreate /> },
      { path: "/board/boardPage", element: <DynamicBoardBoardPage /> },
      { path: "/club/:id", element: <DynamicClubId /> },
      { path: "/club/clubCreate", element: <DynamicClubClubCreate /> },
      { path: "/club/clubPage", element: <DynamicClubClubPage /> },
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
  { route: "/board/boardCreate" },
  { route: "/board/boardPage" },
  { route: "/club/:id" },
  { route: "/club/clubCreate" },
  { route: "/club/clubPage" },
];
