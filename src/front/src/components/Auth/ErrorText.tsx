import { Children } from "react";
import styled from "styled-components";

const ErrorText = (props: any) => {
  return <ErrorContainer>{props.children}</ErrorContainer>;
};

const ErrorContainer = styled.div`
  color: red;
  font-size: 12px;

  width: 100%;
  height: 20px;

  display: flex;
  align-items: center;
  justify-content: center;
`;
export default ErrorText;
