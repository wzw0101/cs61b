import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {
    @Test
    public void randomTest() {
        for (int i = 0; i < 10; i++) {
            if (StdRandom.bernoulli()) {
                System.out.print(1 + " ");
            } else {
                System.out.print(0 + " ");
            }
        }
    }

    @Test
    public void arrayMethodTest() {
        StudentArrayDeque<Integer> sad = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> ads = new ArrayDequeSolution<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            if (StdRandom.bernoulli()) {
                sb.append(String.format("addFirst(Integer.valueof(%d))%n", i));
                sad.addFirst(Integer.valueOf(i));
                ads.addFirst(Integer.valueOf(i));
            } else {
                sb.append(String.format("addLast(Integer.valueof(%d))%n", i));
                sad.addLast(Integer.valueOf(i));
                ads.addLast(Integer.valueOf(i));
            }
        }

        for (int i = 0; i < 10; i++) {
            if (StdRandom.bernoulli()) {
                sb.append("removeFirst()\n");
                Integer expected = ads.removeFirst();
                Integer result = sad.removeFirst();
//                assertEquals("difference occurs when expected: " + expected + " , but the result is: " + result, expected, result);
                assertEquals(sb.toString(), expected, result);
            } else {
                sb.append("removeLast()\n");
                Integer expected = ads.removeLast();
                Integer result = sad.removeLast();
//                assertEquals("difference occurs when expected: " + expected + " , but the result is: " + result, expected, result);
                assertEquals(sb.toString(), expected, result);
            }
        }
    }
}
