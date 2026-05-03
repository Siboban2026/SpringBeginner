import org.junit.jupiter.api.*;

public class JUnitCycleTest {

        @BeforeAll // 전체 테스트를 시작하기 전에 1회 실행하므로 메서드는 static으로 선언
        static void beforeAll() {
            System.out.println("@Before all");
        }

        @BeforeEach
         public void beforeEach() {
            System.out.println("@BeforeEach");
        }

        @Test
        public void test1() {
            System.out.println("@Test1");
        }

        @Test
        public void test2() {
            System.out.println("@Test2");
        }

        @Test
        public void test3() {
            System.out.println("@Test3");
        }

        @AfterAll
        static void afterAll() {
            System.out.println("@AfterAll");
        }

        @AfterEach
        public void afterEach() {
            System.out.println("@AfterEach");
        }

}
