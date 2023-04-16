import React from "react";
import styled from "styled-components";
import { Link } from "react-router-dom";

interface AuthLinkProps {
  children: string;
  toURL: string;
}

const AuthLink = ({ children, toURL }: AuthLinkProps) => {
  const link = `/auth/${toURL}`;

  return <CustumLink to={link}>{children}</CustumLink>;
};

const CustumLink = styled(Link)`
  display: flex;
  align-items: center;
  justify-content: center;

  height: 32px;

  text-decoration: none;
  cursor: pointer;
`;
export default AuthLink;
