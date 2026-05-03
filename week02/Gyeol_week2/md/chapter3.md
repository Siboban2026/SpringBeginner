# 3장 스프링 부트 3 구조 이해하기

---

## 3.0 그림으로 이해하는 프로젝트

### 전체 구조 한 줄 요약

* **브라우저 → 컨트롤러 → 서비스 → 리포지터리 → DB → 다시 응답**

### 핵심 흐름

* 사용자가 `/test` 요청
* Controller → Service → Repository → DB 접근
* 결과를 다시 Controller → 사용자에게 반환

---

## 3.1 스프링 부트 3 구조 살펴보기

### 계층 (Layer)
* 역할과 책임에 따라 나눈 구조
* 유지보수와 확장에 유리

### 프레젠테이션 계층 (Controller)
* 사용자 요청을 받는 입구
* 결과를 응답으로 반환
```java
@RestController
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "hello";
    }
}
```

### 서비스 계층 (Service)
* 비즈니스 로직 처리 (핵심 로직)
```java
@Service
public class TestService {
    public String logic() {
        return "business logic";
    }
}
```

### 퍼시스턴스 계층 (Repository)
* DB 접근 담당 (저장, 조회)
```java
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
}
```

---

## 3.2 스프링 부트 3 프로젝트 발전시키기

### implementation
* 실제 코드에서 사용하는 라이브러리

### testImplementation
* 테스트 코드에서만 사용

### runtimeOnly
* 실행할 때만 필요 (컴파일 시 X)

### compileOnly
* 컴파일 시에만 필요 (실행 시 X)

### annotationProcessor
* 어노테이션 처리 도구 (ex. Lombok)

### 주요 의존성

```gradle
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'

    runtimeOnly 'com.h2database:h2'

    compileOnly 'org.projectlombok:lombok'
    annotationProcessor 'org.projectlombok:lombok'
```

* 데이터 베이스의 테이블을 객체로 가져오게 하는 도구들

---

## 3.3 요청 → 응답 전체 흐름

### 전체 흐름
1. 사용자 → 요청 (HTTP) → 톰캣 → 스프링부트 실행 → 디스패처 서블릿 → 요청 분배 → 컨트롤러 실행
2. 서비스 → 비즈니스 처리 리포지터리 → DB 접근
3. 뷰 리졸버 → HTML / JSON,XML 등 데이터 생성
4. 결과 반환


### 디스패처 서블릿 (Dispatcher Servlet)
* 모든 요청을 받아서 적절한 컨트롤러로 전달

### View Resolver
* 어떤 화면(View)을 보여줄지 결정

### 템플릿 엔진
* HTML 생성 (Thymeleaf 등)

---

## 헷갈리는 개념 정리

### 1. Controller vs Service vs Repository
| 개념         | 역할       |
| ---------- | -------- |
| Controller | 요청/응답 처리 |
| Service    | 비즈니스 로직  |
| Repository | DB 접근    |

### 2. implementation vs runtimeOnly vs compileOnly
| 키워드            | 시점    |
| -------------- | ----- |
| implementation | 항상    |
| runtimeOnly    | 실행 시  |
| compileOnly    | 컴파일 시 |

### 3. JPA vs DB
| 개념  | 설명             |
| --- | -------------- |
| JPA | 자바에서 DB 다루는 기술 |
| DB  | 실제 데이터 저장소     |

---

## 그림 기반 핵심 요약
* Controller → 요청 받음
* Service → 로직 처리
* Repository → DB 연결
* JPA → DB 연결 도와줌
* h2 → 실제 데이터 저장
