import org.junit.jupiter.api.*;

public class JUnitCycleQuiz {

    @Test
    public void junitQuiz3() {
        System.out.println("This is First Test");
    }
    @Test
    public void junitQuiz4() {
        System.out.println("This is Second Test");
    }

    @BeforeEach
    public void beforeEach() {
        System.out.println("Hello!");
    }
    @AfterAll
    public static void afterAll() {
        System.out.println("Bye!");
    }
}
