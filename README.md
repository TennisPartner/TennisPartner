
# TennisPartner
![누끼2](https://user-images.githubusercontent.com/91179733/235034355-b2a21f32-8fb1-467f-ba20-935db8c14c77.png)
<br>
테니스 모임에서 모든 참여자가 동등한 횟수로 게임을 할 수 있도록 도와주는 어플리케이션

## 배포 주소
프론트 서버 : https://front-deploy-three.vercel.app/ <br>
백엔드 서버 : https://www.tennispartner.online/<br>
스웨거 : https://www.tennispartner.online/swagger-ui/index.html#/

## DEMO

|                   로그인             |                   회원가입                    |                         메인페이지                          |
| :--------------------------------: | :-----------------------------------------: | :-------------------------------------------------------: |
| <img src="https://user-images.githubusercontent.com/50473516/235682689-06510253-2e78-4aa2-a909-55dc9a9b77a3.png" alt="로그인_페이지" width=80%> | <img src="https://user-images.githubusercontent.com/50473516/235682687-1f5b074a-c7ec-41d5-bb7f-02d6f407a064.png" alt="회원가입_페이지" width=80%> | <img src="https://user-images.githubusercontent.com/50473516/235682685-1047381b-f00b-4f46-9133-ae7cdf57b32f.png" alt="메인페이지" width=80%> |

|                     매칭페이지        |                   마이페이지                   |                         클럽페이지                         |
| :--------------------------------: | :-----------------------------------------: | :-------------------------------------------------------: |
| <img src="https://user-images.githubusercontent.com/50473516/235682680-34809b52-65e4-4c0b-a739-df6b01d86192.png" alt="매칭_페이지" width=80%> | <img src="https://user-images.githubusercontent.com/50473516/235682667-e211b318-91ad-4df6-aab9-c35ef22e7348.png" alt="마이_페이지" width=80%> | <img src="https://user-images.githubusercontent.com/50473516/235682650-d4550547-6b1b-4e46-bf3f-9ecdd07be9a7.png" alt="클럽_메인페이지" width=80%> |


|                     클럽 개설        |                   클럽 상세                   | 
| :--------------------------------: | :-----------------------------------------: |
| <img src="https://user-images.githubusercontent.com/50473516/235682655-431aa09b-2e86-47e9-9720-e1b5b7bf4344.png" alt="클럽_개설페이지" width=80%> | <img src="https://user-images.githubusercontent.com/50473516/235682641-22c79dc5-f82f-4e2a-9fba-9d072ee3eb1a.png" alt="클럽_상세페이지" width=80%> |



</br>
</br>

## Swagger 사용 방법

1. userController에서 아래 body정보로 api/login 요청을 통해 로그인을 합니다.
{
 "userId": "test@example.com"
 "userPassword" : "admin1234!"
}

![image](https://user-images.githubusercontent.com/91179733/237030895-a1f5c9e1-f607-4da4-9b48-bf1b2392448d.png)<br>

2.결과값인accessToken을 복사합니다.

![image](https://user-images.githubusercontent.com/91179733/237031472-beb82680-166f-46f1-8a97-88fa344552f3.png)

3.Swagger에 Authorize 클릭후 복사한 accessToken을 Value에 붙여넣습니다.

![image](https://user-images.githubusercontent.com/91179733/237034510-8d286ad5-2fb8-400f-8268-161af504648b.png)

## 팀 소개
|      임재학       |      옥동철        |        윤준호         |                                                                                                               
| :------------------------------------------------------------------------------: | :---------------------------------------------------------------------------------------------------------------------------------------------------: | :---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------: | 
|   [@cosiw](https://github.com/cosiw)   |    [@Ockddak](https://github.com/Ockddak)  | [@yunjunhojj](https://github.com/yunjunhojj)  |
| 1년차 백엔드 개발자 | 4년차 백엔드 개발자 | 0년차 프론트엔드 개발자 |

## 프로젝트 소개
테니스 클럽에서 매주 대진표를 직접 작성하는 것을 해결하기 위해 프로젝트를 시작했으며, 이와 더불어 클럽 운영에 도움이 될 수 있는 어플리케이션을 개발하고자 개발하였습니다.

## 시작 가이드
### Requirements

- Java SE 11
- docker
- react

### Installation
``` bash
$ git clone https://github.com/TennisPartner/TennisPartner.git
$ cd TennisPartner
```

### BackEnd
``` bash
$ ./gradlew build
$ cd build/libs
$ java -jar tennisP-0.0.1-SNAPSHOT.jar
```

### docker(redis)
``` bash
$ cd TennisPartner
$ docker build -t tennis-redis-img .
$ docker run -d --name tennis-redis -p 6379:6379 redis
$ docker exec -it tennis-redis redis-cli
```

### FrontEnd
``` bash
$ cd TennisPartner/src/front
$ yarn
$ yarn dev
```

## Stacks

### Environment
![Git](https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=Git&logoColor=white)
![GitHub](https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=GitHub&logoColor=white)
![Visual studio Code](https://img.shields.io/badge/VisualStudioCode-007ACC?style=for-the-badge&logo=VisualStudioCode&logoColor=white)
![Intellij](https://img.shields.io/badge/IntelliJ-000000?style=for-the-badge&logo=IntelliJIDEA&logoColor=white)

### Config
![Yarn](https://img.shields.io/badge/Yarn-2C8EBB?style=for-the-badge&logo=Yarn&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=Gradle&logoColor=white)

### Operation
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=Docker&logoColor=white)
![Amazon EC2](https://img.shields.io/badge/AmazonEC2-FF9900?style=for-the-badge&logo=AmazonEC2&logoColor=white)
![Amazon RDS](https://img.shields.io/badge/AmazonRDS-527FFF?style=for-the-badge&logo=AmazonRDS&logoColor=white)

### Development
![TypeScript](https://img.shields.io/badge/TypeScript-F7DF1E?style=for-the-badge&logo=TypeScript&logoColor=black)
![React](https://img.shields.io/badge/React-61DAFB?style=for-the-badge&logo=ReacT&logoColor=white)
![Java](https://img.shields.io/badge/JAVA-007396?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=SpringBoot&logoColor=white)
![Spring Secuirty](https://img.shields.io/badge/SpringSecurity-6DB33F?style=for-the-badge&logo=SpringSecurity&logoColor=white)
![JPA](https://img.shields.io/badge/JPA-6DB33F?style=for-the-badge&logo=JPA&logoColor=white)

### DataBase
![MariaDB](https://img.shields.io/badge/MariaDB-003545?style=for-the-badge&logo=MariaDB&logoColor=white)
![Redis](https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=Redis&logoColor=white)



## 주요 기능

### 회원가입 및 로그인
 - 해당 어플리케이션에서 회원가입 및 로그인이 가능하며, 로그인을 통해 클럽 서비스를 이용할 수 있습니다.

### 대진표 생성
 - 비회원으로 모임에 참여하는 인원수, 진행하는 게임수, 코트수를 입력하면 상황에 맞게 대진표를 생성합니다.

### 클럽 운영
 - 직접 클럽을 생성할 수 있으며, 각 회원들이 클럽에 가입이 가능합니다.
 - 클럽 게시판을 통해 각 클럽에서의 커뮤니티가 가능합니다.
 - 모임을 생성할 수 있으며, 클럽에 가입된 회원은 모임에 참가를 체크할 수 있습니다.

## 추후 추가 기능
 - 클럽에서 모임을 통해 모인 회원들로 대진표를 작성하는 기능을 추가할 예정입니다.
 - 클럽 게시판에서 댓글 기능을 추가할 예정입니다.
 - 클럽 게시판, 일반 게시판에서 사진이나 파일을 업로드할 수 있도록 기능을 추가할 예정입니다.
 - 클럽외 다른 회원들과 커뮤니티가 가능하도록 일반 게시판 기능을 추가할 예정입니다.

## 아키텍처
  <img src="https://user-images.githubusercontent.com/50473516/235689445-15160436-4d29-4993-ae63-cde8b28dab70.png" alt="아키텍쳐" width=95%>

## skills

### Front-end

<p>
  <img src="https://user-images.githubusercontent.com/52682603/138834243-fb74d81e-e90d-4c6a-8793-05df588f59ab.png" alt="react" width=20%>
  <img src="https://user-images.githubusercontent.com/52682603/138834262-a7af2293-e398-416d-8dd3-ff5fab8cb80d.png" alt="type_script" width=20%>    
  <img src="https://user-images.githubusercontent.com/50473516/235687897-51a4b631-bd4f-4b36-8a27-84add5155852.jpeg" alt="styled_component" width=20%>
  <img src="https://user-images.githubusercontent.com/50473516/235687219-501335c1-780d-4189-a78d-3f2df2cd822e.png" alt="cypress" width=20%>
</p>

- **React.js + vite** 로 빠른 빌드와 TS 기본 지원으로 좋은 DX(개발 경험)을 느꼈습니다.
- **Typescript** 를 사용해 빌드 시에 오류를 미리 찾아, 사전에 에러를 방지 했습니다. 개발 진행 동안 any type을 사용했던 것을 추후 리팩토링할 예정입니다.
- **Styled Component** 으로 자바스크립트로 스타일을 관리했습니다. 반복되는 스타일 컴포넌트를 재활용하고, 상태에 따른 스타일 변경에 용이했습니다.  
- **Cypress** 로 E2E 테스팅을 진행했습니다. 이를 통해 개발 진행 중 테스팅 시간을 단축시키고 더욱 신뢰도 있게 진행할 수 있었습니다.

### Back-end

- TestCode를 작성함으로써 개발한 기능에 대한 불확실성을 감소시킬 수 있었습니다.
- JPA를 사용함으로써 직접 쿼리를 작성하는 것 보다 가독성이 좋아지고 코드량이 줄어든 것을 느낄 수 있었습니다.
- EC2, 로드밸런서, ACM, Route53 등을 이용하여 배포함으로써 다양한 AWS 인프라를 경험할 수 있었습니다.

---


## folder
``` bash
└─TennisPartner
    ├─gradle
    │  └─wrapper
    └─src
        ├─front
        │  ├─cypress
        │  │  ├─e2e
        │  │  ├─fixtures
        │  │  └─support
        │  ├─public
        │  └─src
        │      ├─components
        │      │  ├─Auth
        │      │  ├─board
        │      │  ├─club
        │      │  └─Matching
        │      ├─hooks
        │      ├─pages
        │      │  ├─auth
        │      │  ├─board
        │      │  ├─club
        │      │  ├─mainPage
        │      │  └─myPage
        │      ├─styles
        │      └─util
        ├─main
        │  ├─java
        │  │  └─com
        │  │      └─tennisPartner
        │  │          └─tennisP
        │  │              ├─club
        │  │              │  ├─controller
        │  │              │  ├─domain
        │  │              │  ├─repository
        │  │              │  │  └─dto
        │  │              │  └─service
        │  │              ├─clubBoard
        │  │              │  ├─controller
        │  │              │  ├─domain
        │  │              │  ├─repository
        │  │              │  │  └─dto
        │  │              │  └─service
        │  │              ├─clubBoardReply
        │  │              │  ├─controller
        │  │              │  ├─domain
        │  │              │  ├─repository
        │  │              │  │  └─dto
        │  │              │  └─service
        │  │              ├─common
        │  │              │  ├─config
        │  │              │  ├─domain
        │  │              │  ├─Exception
        │  │              │  └─filter
        │  │              ├─unAuthMatch
        │  │              │  ├─controller
        │  │              │  ├─dto
        │  │              │  └─service
        │  │              └─user
        │  │                  ├─controller
        │  │                  ├─domain
        │  │                  ├─jwt
        │  │                  ├─repository
        │  │                  │  └─dto
        │  │                  ├─resolver
        │  │                  └─service
        │  └─resources
        └─test
            └─java
                └─com
                    └─tennisPartner
                        └─tennisP
                            ├─club
                            │  └─service
                            ├─clubBoard
                            │  └─service
                            ├─clubBoardReply
                            │  └─service
                            └─user
                                ├─repository
                                └─service

```

## DB
![image](https://user-images.githubusercontent.com/91179733/235814658-bdcb2274-83d2-4b96-93c2-b3ec897fcfb3.png)
