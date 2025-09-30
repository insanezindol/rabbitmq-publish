# RabbitMQ 퍼블리셔 프로젝트

## 프로젝트 개요

이 프로젝트는 Spring Boot와 RabbitMQ를 사용하여 메시지를 큐에 발송(publish)하는 마이크로서비스입니다. REST API를 통해 메시지를 받아서 지정된 Exchange와 Routing Key를 사용하여 RabbitMQ로 전송합니다.

## 기술 스택

-   **Java 1.8**
-   **Spring Boot 2.3.4**
-   **Spring AMQP** - RabbitMQ 연동
-   **Lombok** - 보일러플레이트 코드 자동 생성
-   **Gradle** - 빌드 도구

## 주요 기능

-   REST API를 통한 메시지 발송
-   RabbitMQ Exchange와 Routing Key 지원
-   JSON 메시지 변환 지원
-   로깅 및 에러 처리

## 프로젝트 구조

```
src/
├── main/
│   ├── java/
│   │   └── amqp/rabbitmq/publish/
│   │       ├── PublishApplication.java      # 메인 애플리케이션
│   │       ├── config/
│   │       │   └── RabbitConfig.java        # RabbitMQ 설정
│   │       ├── controller/
│   │       │   └── PublishController.java   # REST API 컨트롤러
│   │       └── service/
│   │           └── PublishService.java      # 메시지 발송 서비스
│   └── resources/
│       └── application.yml                  # 애플리케이션 설정
└── test/
    └── java/
        └── amqp/rabbitmq/publish/
            └── PublishApplicationTests.java # 테스트 코드
```

## 환경 설정

### RabbitMQ 설정 (application.yml)

```yaml
server:
    port: 8081

spring:
    rabbitmq:
        host: localhost
        port: 5672
        username: rabbitmq
        password: 1234
        template:
            mandatory: true
            reply-timeout: 60000
```

### 필수 준비사항

1. **RabbitMQ 서버 실행**

    ```bash
    # Docker를 사용하는 경우
    docker run -d --name rabbitmq \
      -p 5672:5672 \
      -p 15672:15672 \
      -e RABBITMQ_DEFAULT_USER=rabbitmq \
      -e RABBITMQ_DEFAULT_PASS=1234 \
      rabbitmq:3-management
    ```

2. **Java 1.8 이상 설치**

## 빌드 및 실행

### Gradle을 사용한 빌드

```bash
# 프로젝트 빌드
./gradlew build

# 애플리케이션 실행
./gradlew bootRun
```

### JAR 파일 직접 실행

```bash
# JAR 파일 생성
./gradlew bootJar

# JAR 파일 실행
java -jar build/libs/rabbitmq-publish-1.0.jar
```

## API 사용법

### 메시지 발송 API

-   **URL**: `GET /rabbit/pub/{exchange}/{routingKey}/{message}`
-   **설명**: 지정된 Exchange와 Routing Key를 사용하여 메시지를 RabbitMQ에 발송합니다.

#### 파라미터

-   `exchange`: RabbitMQ Exchange 이름
-   `routingKey`: 라우팅 키
-   `message`: 발송할 메시지 내용

#### 예제 요청

```bash
# curl 사용 예제
curl "http://localhost:8081/rabbit/pub/test-exchange/test.routing.key/안녕하세요"

# 브라우저��서 직접 접속
http://localhost:8081/rabbit/pub/test-exchange/test.routing.key/Hello%20World
```

#### 응답

```
ok
```

## 주요 컴포넌트 설명

### 1. PublishController

-   REST API 엔드포인트를 제공합니다
-   URL 경로에서 exchange, routingKey, message를 추출합니다
-   PublishService를 호출하여 메시지를 발송합니다

### 2. PublishService

-   실제 메시지 발송 로직을 담당합니다
-   RabbitTemplate을 사용하여 메시지를 전송합니다
-   에러 처리 및 로깅을 수행합니다

### 3. RabbitConfig

-   RabbitMQ 연결 설정을 관리합니다
-   JSON 메시지 변환기를 설정합니다
-   RabbitTemplate 빈을 구성합니다

## 로그 확인

애플리케이션 실행 시 다음과 같은 로그를 확인할 수 있습니다:

```
[exchange명] : [routingKey] : [메시지내용]
```

## 테스트

```bash
# 단위 테스트 실행
./gradlew test

# 테스트 커버리지 확인
./gradlew jacocoTestReport
```

## 개발 환경

-   **포트**: 8081
-   **RabbitMQ 관리 콘솔**: http://localhost:15672 (rabbitmq/1234)
-   **애플리케이션 기본 URL**: http://localhost:8081

## 문제 해결

### RabbitMQ 연결 오류

-   RabbitMQ 서버가 실행 중인지 확인
-   `application.yml`의 연결 정보가 올바른지 확인
-   방화벽 설정 확인

### 메시지 발송 실패

-   Exchange가 RabbitMQ에 존재하는지 확인
-   네트워크 연결 상태 확인
-   로그에서 상세한 오류 메시지 확인

## 라이선스

이 프로젝트는 MIT 라이선스 하에 배포됩니다.
