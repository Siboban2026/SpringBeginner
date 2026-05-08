# 04장 테스트 코드 이해하기

---

## 4.1 테스트 코드 개념 익히기

### 테스트 코드
* 프로그램이 의도대로 동작하는지 확인하는 코드
* 기능 변경 후 문제가 없는지 빠르게 검증 가능

### given / when / then
| 단계    | 의미         |
| ----- | ---------- |
| given | 테스트를 위한 준비 |
| when  | 실제 동작 실행   |
| then  | 결과 검증      |

```java
@Test
void sumTest() {

    // given
    int a = 1;
    int b = 2;

    // when
    int result = a + b;

    // then
    assertThat(result).isEqualTo(3);
}
```

---

## 4.2 스프링 부트 3와 테스트

### JUnit
- 자바에서 테스트 코드를 작성할 때 사용하는 대표 프레임워크
- 테스트 실행, 검증, 애너테이션 기능 제공
- **JUnit 특징**
    - `@Test`로 테스트 메서드 지정
    - 실행 전/후 처리 가능
    - 검증(assertion) 기능 제공

```java
@Test
void test() {
    int result = 1 + 2;

    assertEquals(3, result);
}
```

### AssertJ
* 테스트 결과를 더 읽기 쉽게 검증하는 라이브러리
* 메서드 체인 방식이라 가독성이 좋음

**자주 사용하는 메서드**
| 메서드                 | 설명        |
| ------------------- | --------- |
| `isEqualTo(A)`      | A와 같은지    |
| `isNotEqualTo(A)`   | A와 다른지    |
| `contains(A)`       | A 포함 여부   |
| `doesNotContain(A)` | A 미포함 여부  |
| `startsWith(A)`     | 접두사 검사    |
| `endsWith(A)`       | 접미사 검사    |
| `isEmpty()`         | 비어 있는지    |
| `isNotEmpty()`      | 비어 있지 않은지 |
| `isPositive()`      | 양수인지      |

```java
assertThat("spring")
        .startsWith("sp")
        .contains("rin")
        .endsWith("ng");
```

### @BeforeAll
* 전체 테스트 시작 전에 1번 실행
* 보통 공통 초기화에 사용

### @BeforeEach
* 각 테스트 실행 전에 매번 실행

### @AfterEach
* 각 테스트 실행 후 매번 실행

### @AfterAll
* 전체 테스트 종료 후 1번 실행

### 흐름
```text
@BeforeAll

@BeforeEach
@Test
@AfterEach

@BeforeEach
@Test
@AfterEach

@AfterAll
```

```java
@BeforeEach
void setUp() {
    System.out.println("테스트 전 실행");
}

@AfterEach
void tearDown() {
    System.out.println("테스트 후 실행");
}
```

---

## 4.3 제대로 테스트 코드 작성해보기

### @SpringBootTest
* 스프링 부트 전체 컨테이너를 실행해서 테스트
* 실제 실행 환경과 가장 비슷한 테스트 가능
```java
@SpringBootTest
class TestApplicationTests {

    @Test
    void contextLoads() {
    }
}
```

### @AutoConfigureMockMvc
* 서버를 실제로 띄우지 않고 HTTP 요청 테스트 가능
* Controller 테스트에서 자주 사용
```java
@SpringBootTest
@AutoConfigureMockMvc
class TestControllerTest {

    @Autowired
    private MockMvc mockMvc;
}
```

### ObjectMapper
* 자바 객체 ↔ JSON 변환 도구
* Jackson 라이브러리에서 제공
```java
ObjectMapper objectMapper = new ObjectMapper();

String json = objectMapper.writeValueAsString(member);
```

---

## 헷갈리는 개념 정리

### 1. JUnit vs AssertJ
| 개념      | 역할           |
| ------- | ------------ |
| JUnit   | 테스트 실행 프레임워크 |
| AssertJ | 결과 검증 라이브러리  |

* JUnit = 테스트를 실행
* AssertJ = 결과를 읽기 쉽게 검증

### 2. @BeforeAll vs @BeforeEach
| 애너테이션         | 실행 횟수        |
| ------------- | ------------ |
| `@BeforeAll`  | 전체 테스트 전에 1번 |
| `@BeforeEach` | 테스트마다 실행     |


### 3. @SpringBootTest vs @AutoConfigureMockMvc
| 개념                      | 역할             |
| ----------------------- | -------------- |
| `@SpringBootTest`       | 스프링 전체 환경 실행   |
| `@AutoConfigureMockMvc` | HTTP 요청 테스트 지원 |

- 함께 자주 사용
```java
@SpringBootTest
@AutoConfigureMockMvc
```

### 4. assertEquals vs assertThat
| 방식               | 특징                 |
| ---------------- | ------------------ |
| `assertEquals()` | 기본 JUnit 방식        |
| `assertThat()`   | AssertJ 방식, 가독성 좋음 |

- 비교 예제
```java
assertEquals(3, result);

assertThat(result).isEqualTo(3);
```

---

## 요약
* 테스트 코드 = 프로그램 검증 코드
* given → when → then 흐름으로 작성
* JUnit = 테스트 실행
* AssertJ = 검증 도구
* `@BeforeEach` = 테스트 전 실행
* `@SpringBootTest` = 스프링 전체 테스트
* `MockMvc` = HTTP 요청 테스트
* `ObjectMapper` = 객체 ↔ JSON 변환
