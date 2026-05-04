import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

public class JUnitQuiz {
    @Test
    public void junitTest(){
        String name1 = "홍길동";
        String name2 = "홍길동";
        String name3 = "홍길은";

        // 1. 모든 변수가 null이 아닌지 확인
        assertThat(name1).isNotNull();
        assertThat(name2).isNotNull();
        assertThat(name3).isNotNull();

        // 2.name1과 name2가 같은지 확인
        assertThat(name1).isEqualTo(name2);

        // 3. name1과 name3이 다른지 확인
        assertThat(name1).isNotEqualTo(name3);
    }

    @Test
    public void junitTest2(){
        int num1 = 15;
        int num2 = 0;
        int num3 = -1;

        // 1. num1은 양수인지 확인
        assertThat(num1).isPositive();

        // 2. num2는 0인지 확인
        assertThat(num2).isZero();

        // 3. num3은 음수인지 확인
        assertThat(num3).isNegative();

        // 4. num1은 num2보다 큰지 확인
        assertThat(num1).isGreaterThan(num2);

        // 5. num3은 num2보다 작은지 확인
        assertThat(num3).isLessThan(num2);
    }
}
