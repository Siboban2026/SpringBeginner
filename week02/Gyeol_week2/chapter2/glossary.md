# 02장 스프링 부트 시작하기

---

## 2.1 스프링과 스프링 부트

### Spring
- 자바 애플리케이션 개발 프레임워크
- 다양한 설정을 직접 구성해야 함

### Spring Boot
- Spring을 더 쉽게 사용하도록 도와주는 도구
- 설정 자동화
- 내장 WAS 제공
- 빠른 개발 가능

### Spring vs Spring Boot

| 항목 | Spring | Spring Boot |
|------|--------|-------------|
| 설정 | 수동 설정 많음 | 자동 설정 |
| WAS | 별도 필요 | 내장 WAS 제공 |
| 실행 | 배포 과정 필요 | 단독 실행 가능 |
| 목적 | 프레임워크 | Spring 편의성 향상 |

### 내장 WAS
- 웹 서버가 애플리케이션 안에 포함됨
- 예: Tomcat

---

## 2.2 스프링 콘셉트

### IoC (Inversion of Control)
- 제어의 역전
- 객체 생성/관리를 개발자가 아닌 스프링이 담당

#### 직접 객체 생성 (IoC 적용 전)
```java
public class A {
    B b = new B();
}
```
- A가 직접 B 객체 생성
- 객체 제어권이 개발자에게 있음

#### IoC 적용 후
```java
public class A {
    private B b;
}
```

- 직접 생성하지 않음
- 필요한 객체를 외부(스프링)에서 관리


### DI (Dependency Injection)
- 의존성 주입
- 필요한 객체를 직접 만들지 않고 주입받음
- 
#### 쉽게 보면
개발자:
```java
B b = new B();
```

스프링:
```java
// A에서 B를 주입받음
@Autowired
B b;
```

### IoC vs DI
- IoC → 큰 개념
- DI → IoC 구현 방식 중 하나

### Spring Container
- 객체(Bean) 생성 및 관리

### Bean
- 스프링 컨테이너가 관리하는 객체

### AOP (Aspect Oriented Programming)
- 관점 지향 프로그래밍
- 공통 기능 분리
- 예: 로깅, 트랜잭션

### PSA (Portable Service Abstraction)
- 이식 가능한 서비스 추상화
- 기술 구현체가 달라도 같은 방식으로 사용 가능

예)
- MySQL → PostgreSQL 변경
- 코드 수정 최소화

---

## 2.3 스프링 부트3 둘러보기

### @SpringBootConfiguration
- 스프링 부트의 설정 클래스임을 의미 
- 내부적으로 @Configuration과 동일
- “이 클래스는 설정 클래스다” 라고 스프링에게 알려줌

### @ComponentScan
- 지정된 패키지에서 Bean을 자동으로 탐색 
- @Component, @Service, @Repository, @Controller 등을 찾아 등록 
- “이 패키지 아래 다 뒤져서 Bean으로 등록해”

### @EnableAutoConfiguration
- 스프링 부트의 자동 설정 기능 활성화 
- 라이브러리 기반으로 필요한 설정을 자동 적용
- “의존성 보고 알아서 설정해줘”

---

## 2.4 스프링 부트3 코드 이해하기

### @Component
- 스프링이 관리하는 일반적인 Bean 
- 직접 역할이 정해지지 않은 기본 객체
- "그냥 Bean 등록용 기본 애노테이션"

### @RestController
- REST API를 만드는 컨트롤러
- @Component 포함
- @Controller + @ResponseBody 합쳐진 형태
- “HTTP 요청 받고 JSON으로 응답하는 클래스”


---

## 헷갈리는 개념 메모

| 개념 | 정리 |
|------|------|
| IoC | 제어권을 스프링이 가짐 |
| DI | 필요한 객체를 주입 |
| Container | Bean 관리 |
| Bean | 관리되는 객체 |
| AOP | 공통 기능 분리 |
| @ComponentScan | Bean 자동 탐색 |
| @EnableAutoConfiguration | 자동 설정 |
| @SpringBootConfiguration | 설정 클래스 |
| @SpringBootApplication | 위 3개 합친 것 |
| @Component | 기본 Bean |
| @RestController | API 컨트롤러 |
