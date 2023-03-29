import React from "react";
import { BrowserRouter, useRoutes } from "react-router-dom";
import { ThemeProvider } from "styled-components";
import ReactDOM from "react-dom/client";
import App from "./App";
import theme from "./styles/theme";

const root = ReactDOM.createRoot(
  document.getElementById("root") as HTMLElement
);

root.render(
  <BrowserRouter>
    <ThemeProvider theme={theme}>
      <App />
    </ThemeProvider>
  </BrowserRouter>
);
