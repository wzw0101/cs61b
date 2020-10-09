package bearmaps;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class KDTreeTest {

    List<Point> points = null;
    NaivePointSet nps = null;
    KDTree kdt = null;
    Random random = null;

    private final int BOUND = 100;
    private final int TURNS = 100;
    private final int PNUMBRE = 200;
    private final int TESTS = 20;

    @Test
    public void test() {
        random = new Random(1L);
        for (int i = 0 ; i < TURNS; i++) {
            points = new ArrayList<>(PNUMBRE);
            for (int j = 0; j < PNUMBRE; j++) {
                points.add(pointGenerator(random));
            }
            nps = new NaivePointSet(points);
            kdt = new KDTree(points);
            for (int k = 0; k < TESTS; k++) {
                Point point = pointGenerator(random);
                assertEquals(nps.nearest(point.getX(), point.getY()), kdt.nearest(point.getX(),point.getY()));
            }
        }
    }

    @Test
    public void pointGeneratorTest() {
        random = new Random(1L);
        for (int i = 0; i < 100; i++) {
            System.out.println(pointGenerator(random));
        }
    }

    private Point pointGenerator(Random random) {
        return new Point(range(random, BOUND), range(random, BOUND));
    }

    private double range(Random random, int i) {
        return (random.nextDouble() * 2 - 1) * i;
    }
}
