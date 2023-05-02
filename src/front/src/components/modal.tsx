import React from "react";
import styled from "styled-components";

interface LoginModalProps {
  isOpen: boolean;
  onClose: () => void;
}

const ModalOverlay = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.5);
  z-index: 1000;
`;

const ModalContent = styled.div`
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 300px;
  max-width: 90%;
  padding: 24px;
  background-color: #fff;
  border-radius: 8px;
`;

const Title = styled.h2`
  margin-top: 0;
  margin-bottom: 24px;

  font-size: 24px;
`;

const Text = styled.p``;

const Actions = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
`;

const Link = styled.a`
  color: #fff;
  background-color: #0077ff;
  padding: 12px 24px;
  border-radius: 8px;
  text-decoration: none;

  margin: 24px auto 0 auto;
`;

const Button = styled.button`
  background-color: transparent;
  color: #0077ff;
  border: none;
  padding: 0;
  font-size: 16px;
  cursor: pointer;
`;

const GoLoginModal: React.FC<LoginModalProps> = ({ isOpen, onClose }) => {
  return (
    <>
      {isOpen && (
        <ModalOverlay>
          <ModalContent>
            <Title>로그인 하러 가기</Title>
            <Text>로그인이 필요한 기능입니다.</Text>
            <Text>로그인 페이지로 이동하시겠습니까?</Text>
            <Actions>
              <Link href="/auth/login">로그인 페이지로 이동</Link>
              {/* <Button onClick={onClose}>닫기</Button> */}
            </Actions>
          </ModalContent>
        </ModalOverlay>
      )}
    </>
  );
};

export default GoLoginModal;
