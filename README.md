
# TennisPartner
![누끼2](https://user-images.githubusercontent.com/91179733/235034355-b2a21f32-8fb1-467f-ba20-935db8c14c77.png)
<br>
테니스 모임에서 모든 참여자가 동등한 횟수로 게임을 할 수 있도록 도와주는 어플리케이션

## 배포 주소
프론트 서버 : https://front-deploy-three.vercel.app/ <br>
백엔드 서버 : https://www.tennispartner.online/

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
![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=JavaScript&logoColor=white)
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
