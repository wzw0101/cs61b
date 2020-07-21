import java.util.Map;
import static java.lang.Math.log1p;
import static java.lang.Math.log;
import static java.lang.Math.floor;
import static java.lang.Math.pow;

/**
 * Created by hug.
 */
public class ExperimentHelper {

    /** Returns the internal path length for an optimum binary search tree of
     *  size N. Examples:
     *  N = 1, OIPL: 0
     *  N = 2, OIPL: 1
     *  N = 3, OIPL: 2
     *  N = 4, OIPL: 4
     *  N = 5, OIPL: 6
     *  N = 6, OIPL: 8
     *  N = 7, OIPL: 10
     *  N = 8, OIPL: 13
     */
    public static int optimalIPL(int N) {
        // h is the hight of the tree basing 0, where the depth from 0 to h -1 of the tree should be bushy.
        // Therefore, we have an invariant, 2 ^ h - 1 <= N <= 2 ^ (h + 1) - 1.
        int h = (int) floor(log1pBase2(N));
        int ipl = 0;
        for (int i = 0; i < h; i++) {
            ipl += i * pow(2, i);
        }
        ipl += (N - pow(2, h) + 1) * h;
        return ipl;
    }

    /** Returns the average depth for nodes in an optimal BST of
     *  size N.
     *  Examples:
     *  N = 1, OAD: 0
     *  N = 5, OAD: 1.2
     *  N = 8, OAD: 1.625
     * @return
     */
    public static double optimalAverageDepth(int N) {
        return optimalIPL(N) / (double) N;
    }

    private static double log1pBase2(double d) {
        return log1p(d) / log(2);
    }



}
