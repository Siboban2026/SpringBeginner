# 💁‍♀️몰랐던 내용 추가 정리
ㅤ

## MVC / 요청 흐름

* 컨트롤러가 바로 요청을 받는 구조가 아니다. 먼저 디스패처 서블릿이 요청을 받아서 적절한 컨트롤러로 넘긴다.
* `@RequestParam`은 URL 파라미터를 가져오는 방식이고, 모델은 뷰에 데이터를 전달할 때 사용하는 객체다.

👉 정리하면, 요청 흐름은
**Dispatcher → Controller → View 순서로 이어진다.**

```java
// DispatcherServlet이 먼저 요청을 받고 컨트롤러로 넘겨줌
@GetMapping("/example")
public String example(Model model) {
    model.addAttribute("message", "Example");
    return "view";
}
```

```java
// URL 파라미터를 받아 모델에 넣어 뷰로 전달
@GetMapping("/hello")
public String hello(@RequestParam("name") String name, Model model) {
    model.addAttribute("name", name);
    return "hello-view";
}
```
ㅤ
ㅤ
## API / 데이터 반환

* `@ResponseBody`를 사용하면 뷰를 거치지 않고 데이터를 그대로 응답으로 보낸다.
  문자열은 그대로 반환되고, 객체는 JSON 형태로 변환된다.

* 이 과정은 `HttpMessageConverter`가 담당한다.
  문자열이면 `StringHttpMessageConverter`, 객체면 `MappingJackson2HttpMessageConverter`가 사용된다.

👉 결국 핵심은
**반환 타입에 따라 스프링이 자동으로 응답 형태를 바꿔준다.**

```java
// 뷰를 거치지 않고 문자열 그대로 응답
@GetMapping("/hello-string")
@ResponseBody
public String helloString(@RequestParam("name") String name) {
    return "hello " + name;
}
```

```java
// 객체를 반환하면 스프링이 JSON으로 변환
@GetMapping("/hello-api")
@ResponseBody
public Hello helloApi(@RequestParam("name") String name) {
    Hello hello = new Hello();
    hello.setName(name);
    return hello;
}

static class Hello {
    private String name;
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
```
ㅤ
ㅤ
## build 개념

* build는 단순히 실행하는 과정이 아니다.
  컴파일, 테스트, 패키징까지 포함된 전체 과정을 의미한다.

* build를 하면 JAR 파일이 생성되고, 이를 통해 실행하거나 배포할 수 있다.

👉 한 줄로 보면
**실행 가능한 상태로 만드는 과정이라고 이해하면 된다.**

```
.\gradlew build
java -jar build/libs/your-jar.jar
```
ㅤ
ㅤ
## 인터페이스 설계

* 인터페이스를 먼저 만드는 이유는 구현을 나중에 바꾸기 쉽게 만들기 위해서다.
* 서비스는 구체적인 클래스가 아니라 인터페이스에 의존하도록 설계한다.

👉 그래서 DB가 바뀌어도 서비스 코드는 그대로 유지할 수 있다.

```java
private final MemberRepository memberRepository;
```
ㅤ
ㅤ
## 구현체 교체 구조

* 같은 인터페이스를 구현한 클래스라면 언제든지 교체할 수 있다.

👉 즉,
**구조는 유지하면서 내부 구현만 바꿀 수 있는 구조다.**

```java
new MemoryMemberRepository();
new JdbcMemberRepository();
```
ㅤ
ㅤ
## 생성자 주입 (DI)

* 객체를 직접 생성하지 않고 외부에서 전달받는 방식이다.
* 의존성을 외부에서 주입한다고 해서 DI라고 부른다.

👉 결과적으로
**코드의 유연성과 테스트 용이성이 올라간다.**

```java
public MemberService(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
}
```
ㅤ
ㅤ
## 스프링 자동 주입

* `@Autowired`를 사용하면 스프링이 필요한 객체를 자동으로 주입해준다.

👉 개발자는 객체 생성에 신경 쓰지 않고 로직에 집중하면 된다.

```java
@Autowired
public MemberService(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
}
```
ㅤ
ㅤ
## 테스트 구조

* `beforeEach`, `afterEach`는 테스트마다 새로운 환경을 만들기 위해 사용한다.
* 이전 테스트의 데이터가 남아 있으면 결과가 틀어질 수 있기 때문이다.

```java
@BeforeEach
void beforeEach() {
    memberRepository = new MemoryMemberRepository();
    memberService = new MemberService(memberRepository);
}

@AfterEach
void afterEach() {
    memberRepository.clearStore();
}
```
ㅤ
ㅤ
## 테스트 검증

* 테스트는 실행만 하면 의미가 없다.
* 반드시 결과를 비교해서 검증해야 한다.

👉 핵심은
**예상값과 실제값이 같은지 확인하는 것이다.**

```java
assertThat(result).isEqualTo(member);
```
ㅤ
ㅤ
## 테스트 코드를 작성하는 이유

* 직접 실행하면 시간이 오래 걸리고 반복 테스트가 어렵다.

* 문제가 발생했을 때 원인을 찾기도 힘들다.

* 테스트 코드를 사용하면 기능 단위로 빠르게 검증할 수 있다.

```java
@Test
public void save() {
    Member member = new Member();
    member.setName("spring");

    repository.save(member);

    Member result = repository.findById(member.getId()).get();
    assertThat(result).isEqualTo(member);
}
```

* 중요한 점은 실행에서 끝나는 게 아니라
  결과를 검증하는 코드가 반드시 있어야 한다는 것이다.

👉 `assertThat`, `assertEquals`, `assertThrows` 같은 검증 코드가 핵심이다.
ㅤ
ㅤ
## JPA 개념 정리

* JPA는 SQL 중심이 아니라 객체 중심으로 데이터를 다룰 수 있게 해준다.
* 반복되는 JDBC 코드를 줄여주기 때문에 생산성이 높아진다.

```java
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
```

* `@Entity`는 해당 클래스가 테이블과 연결된다는 의미다.
* `@Id`는 기본키를 의미하고,
* `@GeneratedValue`는 ID 값을 자동으로 생성해준다.

```java
public Member save(Member member) {
    em.persist(member);
    return member;
}
```

* `EntityManager`는 DB 작업을 담당하는 핵심 객체다.

👉 중요한 포인트 하나
**데이터 변경은 반드시 트랜잭션 안에서 실행해야 한다.**

```java
@Transactional
public class MemberService {
}
```
ㅤ
ㅤ
## AOP 개념 정리

* AOP는 공통 기능을 따로 분리하기 위해 사용한다.
* 예를 들어 시간 측정이나 로그 처리 같은 기능을 각 메서드에 직접 넣으면 코드가 복잡해진다.

👉 이런 공통 로직을 한 곳에서 관리하도록 만드는 방식이다.

```java
@Aspect
@Component
public class TimeTraceAop {

    @Around("execution(* hello.hellospring..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        try {
            return joinPoint.proceed();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println(joinPoint.toString() + " " + timeMs + "ms");
        }
    }
}
```

* `@Aspect`는 AOP 클래스라는 의미다.
* `@Around`는 적용 범위를 지정한다.
* `proceed()`는 실제 메서드를 실행하는 부분이다.

👉 정리하면
**핵심 로직은 그대로 두고, 공통 기능만 따로 관리하는 구조다.**
