import styled from "styled-components";

interface Props {
  type: string;
  children: React.ReactNode;
}

const Tag = ({ type, children }: Props) => {
  return <TagContainer>{children}</TagContainer>;
};

const TagContainer = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  min-width: 80px;
  height: 30px;
  border-radius: 10px;
  background-color: ${({ theme }) => theme.colors.tennis};
  margin: 0 5px;
  font-size: 12px;
  font-weight: 500;
  color: #000000;
`;

export default Tag;
