import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import "./styles/global.css"
import AppLayout from './layout.tsx'

import {
  createBrowserRouter,
  RouterProvider,
} from "react-router";
import HomePage from './pages/home.page.tsx';
import UserPage from './pages/user.page.tsx';
import BlogPage from './pages/blog.page.tsx';

const router = createBrowserRouter([
  {
    path: "/",
    element: <AppLayout />,
    children: [
      { index: true, element: <HomePage /> },
      {
        path: "users",
        element: <UserPage />,
      },
      {
        path: "blogs",
        element: <BlogPage />,
      },
    ]
  },

]);

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <RouterProvider router={router} />
  </StrictMode>,
)
