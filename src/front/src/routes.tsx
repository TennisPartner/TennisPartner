
import React from 'react';
import GlobalLayout from './pages/_layout'

const DynamicMainPage = React.lazy(() => import('./pages/mainPage'));
const DynamicAuthLogin = React.lazy(() => import('./pages/auth/login'));
const DynamicAuthSignup = React.lazy(() => import('./pages/auth/signup'));
const DynamicBoardId = React.lazy(() => import('./pages/board/[id]'));
const DynamicBoardBoardCreate = React.lazy(() => import('./pages/board/boardCreate'));
const DynamicBoardBoardPage = React.lazy(() => import('./pages/board/boardPage'));
const DynamicClubId = React.lazy(() => import('./pages/club/[id]'));
const DynamicClubClubCreate = React.lazy(() => import('./pages/club/clubCreate'));
const DynamicClubClubPage = React.lazy(() => import('./pages/club/clubPage'));


export const routes = [
  {
    path: '/',
    element: <GlobalLayout />,
    children: [
      { path: '/mainPage', element: <DynamicMainPage />, },
      { path: '/auth/login', element: <DynamicAuthLogin />, },
      { path: '/auth/signup', element: <DynamicAuthSignup />, },
      { path: '/board/:id', element: <DynamicBoardId />, },
      { path: '/board/boardCreate', element: <DynamicBoardBoardCreate />, },
      { path: '/board/boardPage', element: <DynamicBoardBoardPage />, },
      { path: '/club/:id', element: <DynamicClubId />, },
      { path: '/club/clubCreate', element: <DynamicClubClubCreate />, },
      { path: '/club/clubPage', element: <DynamicClubClubPage />, },
    ]
  }
]

export const pages = [
  { route: '/mainPage' },
  { route: '/auth/login' },
  { route: '/auth/signup' },
  { route: '/board/:id' },
  { route: '/board/boardCreate' },
  { route: '/board/boardPage' },
  { route: '/club/:id' },
  { route: '/club/clubCreate' },
  { route: '/club/clubPage' },
]
