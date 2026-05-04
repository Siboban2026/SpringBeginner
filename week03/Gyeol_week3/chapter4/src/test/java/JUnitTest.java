import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JUnitTest {

    @DisplayName("1 + 2는 3이다") // @DisplayName : 테스트 이름을 명시
    @Test                       // @Test : 테스트를 수행하는 메서드
    public void junitTest() {
        int a = 1;
        int b = 2;
        int sum = 3;

        Assertions.assertEquals(a + b, sum);
    }

//    @DisplayName("1 + 3는 4이다")
//    @Test
//    public void junitFailedTest() {
//        int a = 1;
//        int b = 3;
//        int sum = 3;
//
//        Assertions.assertEquals(a + b, sum);
//    }
}
