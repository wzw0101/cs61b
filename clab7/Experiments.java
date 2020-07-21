import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleToLongFunction;

/**
 * Created by hug.
 */
public class Experiments {

    public static final int N = 5000;
    public static final int RANDOMUPPERBOUND = 2 * N;
    public static final int M = 1000000;

    public static void experiment1() {
        List<Integer> numOfNodes = new ArrayList<>();
        List<Double> bstAverageDepth = new ArrayList<>();
        List<Double> optimalAverageDepth = new ArrayList<>();

        BST<Integer> bst = new BST<>();
        int lastSize = bst.size();
        for (int i = 0; i < N; i++) {
            Integer integer = RandomGenerator.getRandomInt(RANDOMUPPERBOUND);
            while (bst.contains(integer)) integer = RandomGenerator.getRandomInt(RANDOMUPPERBOUND);
            bst.add(integer);
            numOfNodes.add(bst.size());
            bstAverageDepth.add(bst.averageDepth());
            optimalAverageDepth.add(ExperimentHelper.optimalAverageDepth(bst.size()));
        }
        XYChart chart = new XYChartBuilder().width(800)
                                            .height(600)
                                            .xAxisTitle("x nodes number")
                                            .yAxisTitle("y average depth").build();
        chart.addSeries("BST Average Depth", numOfNodes, bstAverageDepth);
        chart.addSeries("Optimal Average Depth", numOfNodes, optimalAverageDepth);

        new SwingWrapper(chart).displayChart();
    }

    public static void experiment2() {
        // init bst
        BST<Integer> bst = new BST<>();
        for (int i = 0; i < N; i++) {
            Integer integer = RandomGenerator.getRandomInt(RANDOMUPPERBOUND);
            while (bst.contains(integer)) integer = RandomGenerator.getRandomInt(RANDOMUPPERBOUND);
            bst.add(integer);
        }
        // record start point
        List<Integer> operationTimes = new ArrayList<>();
        List<Double> adList = new ArrayList<>();
        operationTimes.add(0);
        adList.add(bst.averageDepth());
        for (int i = 1; i <= M ; i++) {
            oneTurnKnottOperation(bst);
            operationTimes.add(i);
            adList.add(bst.averageDepth());
        }

        XYChart chart = new XYChartBuilder().width(800)
                .height(600)
                .xAxisTitle("x Knott Operation Times")
                .yAxisTitle("y Average Depth").build();
        chart.addSeries("Knott Experiment", operationTimes, adList);
        new SwingWrapper(chart).displayChart();
    }

    private static void oneTurnKnottOperation(BST<Integer> bst) {
        Integer integer = RandomGenerator.getRandomInt(RANDOMUPPERBOUND);
        while (!bst.contains(integer)) integer = RandomGenerator.getRandomInt(RANDOMUPPERBOUND);
        bst.deleteTakingSuccessor(integer);

        integer = RandomGenerator.getRandomInt(RANDOMUPPERBOUND);
        while (bst.contains(integer)) integer = RandomGenerator.getRandomInt(RANDOMUPPERBOUND);
        bst.add(integer);
    }

    public static void experiment3() {
        // init bst
        BST<Integer> bst = new BST<>();
        for (int i = 0; i < N; i++) {
            Integer integer = RandomGenerator.getRandomInt(RANDOMUPPERBOUND);
            while (bst.contains(integer)) integer = RandomGenerator.getRandomInt(RANDOMUPPERBOUND);
            bst.add(integer);
        }
        // record start point
        List<Integer> operationTimes = new ArrayList<>();
        List<Double> adList = new ArrayList<>();
        operationTimes.add(0);
        adList.add(bst.averageDepth());
        for (int i = 1; i <= M ; i++) {
            oneTurnEpplingerOperation(bst);
            operationTimes.add(i);
            adList.add(bst.averageDepth());
        }

        XYChart chart = new XYChartBuilder().width(800)
                .height(600)
                .xAxisTitle("x Epplinger Operation Times")
                .yAxisTitle("y Average Depth").build();
        chart.addSeries("Epplinger Experiment", operationTimes, adList);
        new SwingWrapper(chart).displayChart();
    }

    private static void oneTurnEpplingerOperation(BST<Integer> bst) {
        Integer integer = RandomGenerator.getRandomInt(RANDOMUPPERBOUND);
        while (!bst.contains(integer)) integer = RandomGenerator.getRandomInt(RANDOMUPPERBOUND);
        bst.deleteTakingRandom(integer);

        integer = RandomGenerator.getRandomInt(RANDOMUPPERBOUND);
        while (bst.contains(integer)) integer = RandomGenerator.getRandomInt(RANDOMUPPERBOUND);
        bst.add(integer);
    }

    public static void main(String[] args) {
        experiment2();
    }
}
