import java.io.*;
import java.util.*;

public class Main{
    public static void main(String args[]) throws IOException{
        Scanner sc = new Scanner(System.in);

        String[] gan = {"갑", "을", "병", "정", "무", "기", "경", "신", "임", "계"};
        String[] ji = {"자", "축", "인", "묘", "진", "사", "오", "미", "신", "유", "술", "해"};

        while (true) {
            System.out.print("60갑자를 입력하세요 : ");
            String input = sc.next();

            if (input.equals("종료")) {
                break;
            }

            if (input.length() != 2) {
                System.out.println("잘못된 입력");
                continue;
            }

            String inputGan = input.substring(0, 1);
            String inputJi = input.substring(1, 2);

            int ganIdx = -1;
            int jiIdx = -1;

            for (int i = 0; i < gan.length; i++) {
                if (gan[i].equals(inputGan)) ganIdx = i;
            }

            for (int i = 0; i < ji.length; i++) {
                if (ji[i].equals(inputJi)) jiIdx = i;
            }

            if (ganIdx == -1 || jiIdx == -1) {
                System.out.println("잘못된 입력");
                continue;
            }

            // 1444년(갑자년) 기준으로 해당 갑자가 몇 번째인지 계산 (중국식/한국식 역법 동일 원리)
            // 60갑자 중 인덱스를 찾는 로직 (나머지 연산 활용)
            boolean found = false;
            for (int year = 1800; year <= 2100; year++) {
                // 1444년이 갑자년(0번)이므로, (연도 - 1444)를 60으로 나눈 나머지가 해당 연도의 갑자 순서
                int diff = year - 1444;
                if (diff >= 0 && diff % 10 == ganIdx && diff % 12 == jiIdx) {
                    System.out.print(year + " ");
                    found = true;
                }
            }

            if (!found) {
                System.out.print("해당하는 연도가 없습니다.");
            }
            System.out.println();
        }

    }
}