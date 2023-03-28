import React from "react";
import ReactDOM from "react-dom";
import { BrowserRouter, useRoutes } from "react-router-dom";
import App from "./App";
import { ThemeProvider } from "styled-components";
import theme from "./styles/theme";

ReactDOM.render(
  <BrowserRouter>
    <ThemeProvider theme={theme}>
      <App />
    </ThemeProvider>
  </BrowserRouter>,
  document.getElementById("root")
);
