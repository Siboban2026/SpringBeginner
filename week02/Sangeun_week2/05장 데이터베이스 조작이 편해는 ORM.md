# 05장. 데이터베이스 조작이 편해지는 ORM

---

## 1. ORM

**ORM(Object Relational Mapping)** 은 자바 객체와 데이터베이스를 연결하는 프로그래밍 기법입니다.

### 특징

- SQL을 직접 작성하지 않고 사용하는 언어로 데이터베이스 접근 가능
- 객체지향적으로 코드 작성 가능
- 비즈니스 로직에 집중하기 쉬운 구조
- 데이터베이스 시스템 추상화 → MySQL에서 PostgreSQL로 전환해도 추가 작업 부담 감소
- 매핑 정보가 명확하여 ERD 의존도 감소 및 유지보수에 유리

---

## 2. JPA와 하이버네이트

```
JPA (인터페이스)
  └── Hibernate (구현체)
        └── JDBC API (데이터베이스 통신)
```

| 기술 | 설명 |
|------|------|
| **JPA** (Java Persistence API) | 자바 객체와 데이터베이스를 연결하기 위한 표준 인터페이스 |
| **Hibernate** | JPA 인터페이스를 실제로 구현한 구현체 |
| **JDBC API** | Hibernate 내부에서 데이터베이스와 통신할 때 사용하는 기술 |

---

## 3. 엔티티 (Entity)

엔티티는 **데이터베이스 테이블과 매핑되는 자바 객체**입니다.

```
자바 객체 Member
      ↕ 매핑
데이터베이스 member 테이블
```

- 데이터베이스에 영향을 미치는 쿼리 실행 대상
- 일반 객체와 달리 영속성 컨텍스트의 관리 대상

---

## 4. 엔티티 매니저 (Entity Manager)

엔티티를 관리하는 객체로, 데이터베이스와 애플리케이션 사이에서 **CRUD 역할**을 수행합니다.

### 엔티티 매니저 팩토리

엔티티 매니저를 생성하는 객체로, 여러 사용자의 요청에 따라 각각의 엔티티 매니저를 생성합니다.

```
회원 1 가입 요청 → 엔티티 매니저 팩토리 → 엔티티 매니저 1 생성 → DB 저장
회원 2 가입 요청 → 엔티티 매니저 팩토리 → 엔티티 매니저 2 생성 → DB 저장
```

### 스프링 부트에서의 사용

스프링 부트는 내부적으로 엔티티 매니저 팩토리를 하나만 생성하여 관리합니다.  
`@PersistenceContext` 또는 `@Autowired` 애너테이션으로 주입받아 사용합니다.

```java
@PersistenceContext
EntityManager em;
```

---

## 5. 영속성 컨텍스트 (Persistence Context)

JPA가 엔티티를 관리하는 **가상의 공간**으로, 엔티티 매니저는 엔티티를 이 공간에 저장합니다.

### 4가지 주요 특징

| 특징 | 설명 |
|------|------|
| **1차 캐시** | 영속성 컨텍스트 내부의 캐시로, DB 접근 없이 빠른 조회 가능 |
| **쓰기 지연** | 트랜잭션 커밋 전까지 쿼리를 모아두었다가 한 번에 실행 |
| **변경 감지** | 트랜잭션 커밋 시 엔티티 변경 사항을 자동으로 감지해 UPDATE 실행 |
| **지연 로딩** | 연관 데이터를 즉시 가져오지 않고 실제 사용 시점에 조회 |

### 1차 캐시 조회 흐름

```
엔티티 조회 요청
→ 1차 캐시 확인
→ 캐시에 있으면 반환 (DB 접근 생략)
→ 캐시에 없으면 DB 조회 → 1차 캐시 저장 → 반환
```

### 쓰기 지연 동작 방식

```
INSERT 쿼리 발생 → 영속성 컨텍스트에 쿼리 저장
→ 트랜잭션 커밋 시점 → 모아둔 쿼리 한 번에 실행
```

### 변경 감지 동작 방식

```
엔티티 조회 → 1차 캐시에 최초 상태 저장
→ 필드 값 변경 → 트랜잭션 커밋
→ 최초 상태와 현재 상태 비교 → 변경 사항 있으면 UPDATE 자동 실행
```

### 지연 로딩 vs 즉시 로딩

| 방식 | 설명 |
|------|------|
| **지연 로딩** | 연관 데이터를 실제 사용하는 시점에 추가 쿼리로 조회 |
| **즉시 로딩** | 엔티티 조회 시 연관 데이터를 함께 조회 |

---

## 6. 엔티티 상태

엔티티는 영속성 컨텍스트와의 관계에 따라 4가지 상태를 가집니다.

| 상태 | 설명 |
|------|------|
| **비영속 (New)** | 영속성 컨텍스트와 전혀 관계없는 상태 |
| **영속 (Managed)** | 영속성 컨텍스트가 관리하는 상태 |
| **분리 (Detached)** | 한 번 영속 상태였지만 현재는 관리되지 않는 상태 |
| **삭제 (Removed)** | 삭제 대상으로 등록된 상태 |

```java
// 비영속
Member member = new Member(1L, "홍길동");

// 영속
em.persist(member);

// 분리
em.detach(member);

// 삭제
em.remove(member);
```

---

## 7. 스프링 데이터 JPA

스프링 데이터 JPA는 스프링 데이터의 공통 기능에 JPA 기술이 추가된 기술로,  
`JpaRepository` 인터페이스를 통해 기본 CRUD 메서드를 자동으로 제공합니다.

### 기존 JPA 방식 vs 스프링 데이터 JPA

```java
// 기존 JPA 방식 — EntityManager 직접 사용
@PersistenceContext
EntityManager em;

public void join() {
    Member member = new Member(1L, "홍길동");
    em.persist(member);
}
```

```java
// 스프링 데이터 JPA 방식 — Repository 인터페이스 선언만으로 CRUD 제공
public interface MemberRepository extends JpaRepository<Member, Long> {
}
```

### JpaRepository 제네릭 의미

```java
JpaRepository<Member, Long>
            //  ↑       ↑
            // 엔티티  기본키 타입
```

### 제공 기능

- 데이터 조회 / 저장 / 수정 / 삭제
- 페이징 및 정렬

---

## 8. 주요 조회 메서드

### 전체 조회 `findAll()`

```sql
SELECT * FROM member;
```

```java
List<Member> members = memberRepository.findAll();
assertThat(members.size()).isEqualTo(3);
```

### ID로 조회 `findById()`

```sql
SELECT * FROM member WHERE id = 2;
```

```java
Member member = memberRepository.findById(2L).get();
assertThat(member.getName()).isEqualTo("B");
```

### 쿼리 메서드

메서드 이름 규칙만으로 쿼리를 자동 생성하는 기능입니다.

```java
// Repository 선언
Optional<Member> findByName(String name);
```

```java
// 사용
Member member = memberRepository.findByName("C").get();
assertThat(member.getId()).isEqualTo(3);
```

### `@Query` — 직접 쿼리 작성

복잡한 쿼리나 성능 최적화가 필요한 경우 JPQL을 직접 작성합니다.

```java
@Query("select m from Member m where m.name = ?1")
Optional<Member> findByNameQuery(String name);
```

---

## 9. 추가 / 삭제 메서드

### 단건 저장 `save()`

```sql
INSERT INTO member (id, name) VALUES (1, 'A');
```

```java
Member member = new Member(1L, "A");
memberRepository.save(member);
assertThat(memberRepository.findById(1L).get().getName()).isEqualTo("A");
```

### 다건 저장 `saveAll()`

```java
List<Member> members = List.of(
        new Member(2L, "B"),
        new Member(3L, "C")
);
memberRepository.saveAll(members);
assertThat(memberRepository.findAll().size()).isEqualTo(2);
```

### ID로 삭제 `deleteById()`

```sql
DELETE FROM member WHERE id = 2;
```

```java
memberRepository.deleteById(2L);
assertThat(memberRepository.findById(2L).isEmpty()).isTrue();
```

### 전체 삭제 `deleteAll()`

```sql
DELETE FROM member;
```

```java
memberRepository.deleteAll();
assertThat(memberRepository.findAll().size()).isZero();
```

> ⚠️ `deleteAll()`은 모든 데이터를 삭제하므로 실제 서비스 코드에서는 사용 주의.  
> 주로 테스트 환경의 데이터 초기화 목적으로 사용합니다.

```java
// 테스트 간 데이터 격리를 위한 @AfterEach 활용
@AfterEach
public void cleanUp() {
    memberRepository.deleteAll();
}
```

---

## 10. 수정 메서드

JPA에서는 별도의 update 메서드 없이, **영속 상태 엔티티의 값을 변경하면 변경 감지로 자동 반영**됩니다.

### 엔티티에 수정 메서드 추가

```java
public class Member {
    public void changeName(String name) {
        this.name = name;
    }
}
```

### 수정 테스트

```java
@Sql("/insert-members.sql")
@Test
void update() {
    // given
    Member member = memberRepository.findById(2L).get();

    // when
    member.changeName("BC");

    // then
    assertThat(memberRepository.findById(2L).get().getName()).isEqualTo("BC");
}
```

### 서비스 코드에서의 주의 — `@Transactional` 필요

```java
@Transactional
public void updateMember(Long id, String name) {
    Member member = memberRepository.findById(id).get();
    member.changeName(name);
}
```

> `@DataJpaTest`는 기본적으로 트랜잭션이 포함되어 있어 테스트에서는 별도 선언 없이도 동작합니다.

---

## 11. 예제 코드

### Member 엔티티

```java
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    public void changeName(String name) {
        this.name = name;
    }
}
```

### 엔티티 주요 애너테이션 정리

| 애너테이션 | 설명 |
|------------|------|
| `@Entity` | JPA가 관리하는 엔티티 클래스임을 명시, 기본적으로 클래스 이름과 같은 테이블에 매핑 |
| `@NoArgsConstructor` | 기본 생성자 자동 생성, 엔티티는 반드시 필요. 접근 제어자는 `protected` 권장 |
| `@Id` | 해당 필드를 기본키(PK)로 지정 |
| `@GeneratedValue` | 기본키 자동 생성 방식 지정 |
| `@Column` | 데이터베이스 컬럼과 필드 매핑 |

### `@GeneratedValue` 생성 전략

| 전략 | 설명 |
|------|------|
| `AUTO` | 데이터베이스 방언에 따라 자동 선택 |
| `IDENTITY` | 기본키 생성을 데이터베이스에 위임 (MySQL `AUTO_INCREMENT`) |
| `SEQUENCE` | 데이터베이스 시퀀스를 사용하여 기본키 생성 |
| `TABLE` | 키 생성 전용 테이블 사용 |

### `@Column` 주요 속성

| 속성 | 설명 |
|------|------|
| `name` | 매핑할 컬럼 이름 |
| `nullable` | null 허용 여부 |
| `unique` | 유일값 여부 |
| `columnDefinition` | 컬럼 정보 직접 설정 |

### MemberRepository

```java
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByName(String name);
}
```

### 테스트용 SQL 파일 (`src/test/resources/insert-members.sql`)

```sql
INSERT INTO member (id, name) VALUES (1, 'A');
INSERT INTO member (id, name) VALUES (2, 'B');
INSERT INTO member (id, name) VALUES (3, 'C');
```

### `application.yml` — 자동 실행 방지 설정

```yaml
spring:
  sql:
    init:
      mode: never
```


