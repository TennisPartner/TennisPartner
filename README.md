
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

- java11
- docker
- react
- yarn

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
![docker](https://img.shields.io/badge/Docker-F05032?-docker-blue)
