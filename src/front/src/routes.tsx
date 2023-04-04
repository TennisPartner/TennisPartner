
import React from 'react';
import GlobalLayout from './pages\_layout'

const DynamicCreateProfile = React.lazy(() => import('./pages/auth/createProfile'));
const DynamicLogin = React.lazy(() => import('./pages/auth/login'));
const DynamicSignup = React.lazy(() => import('./pages/auth/signup'));
const DynamicId = React.lazy(() => import('./pages/board/[id]'));
const DynamicBoardCreate = React.lazy(() => import('./pages/board/boardCreate'));
const DynamicBoardPage = React.lazy(() => import('./pages/board/boardPage'));
const DynamicId = React.lazy(() => import('./pages/club/[id]'));
const DynamicClubCreate = React.lazy(() => import('./pages/club/clubCreate'));
const DynamicClubPage = React.lazy(() => import('./pages/club/clubPage'));
const DynamicIndex = React.lazy(() => import('./pages/mainPage/index'));
const DynamicIndex = React.lazy(() => import('./pages/myPage/index'));


export const routes = [
  {
    path: '/',
    element: <GlobalLayout />,
    children: [
      { path: 'C:\auth\createProfile', element: <DynamicCreateProfile />, },
      { path: 'C:\auth\login', element: <DynamicLogin />, },
      { path: 'C:\auth\signup', element: <DynamicSignup />, },
      { path: 'C:\board\:id', element: <DynamicId />, },
      { path: 'C:\board\boardCreate', element: <DynamicBoardCreate />, },
      { path: 'C:\board\boardPage', element: <DynamicBoardPage />, },
      { path: 'C:\club\:id', element: <DynamicId />, },
      { path: 'C:\club\clubCreate', element: <DynamicClubCreate />, },
      { path: 'C:\club\clubPage', element: <DynamicClubPage />, },
      { path: 'C:\mainPage', element: <DynamicIndex />, index: true},
      { path: 'C:\myPage', element: <DynamicIndex />, index: true},
    ]
  }
]

export const pages = [
  { route: 'C:\auth\createProfile' },
  { route: 'C:\auth\login' },
  { route: 'C:\auth\signup' },
  { route: 'C:\board\:id' },
  { route: 'C:\board\boardCreate' },
  { route: 'C:\board\boardPage' },
  { route: 'C:\club\:id' },
  { route: 'C:\club\clubCreate' },
  { route: 'C:\club\clubPage' },
  { route: 'C:\mainPage' },
  { route: 'C:\myPage' },
]
