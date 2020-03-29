
import org.jfree.ui.RefineryUtilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

class Lab3 {

    private static int n = 12;
    private static int N = 1024;
    private static int Wgr = 2400;
    private static double rangeMin = 0;
    private static double rangeMax = 100;
    public static List<Double> signal = new ArrayList<>();
    private static Random random = new Random();
    private static List<List<Double>> valuesArray = new ArrayList<>(new ArrayList<>());
    private static List<Double> fiH = new ArrayList<>();
    private static List<Double> F = new ArrayList<>();

    public static void main(String[] args) {
        createPlot("signal", createSignal());
        createFI();
        createTable();
        createPlot("F", createFp());
    }

    private static List<Double> createSignal() {

        IntStream.range(0, N)
                .forEach(i -> signal.add(i, 0.0));

        for (int i = 1; i < n + 1; i++) {
            double fi = rangeMin + (rangeMax - rangeMin) * random.nextDouble();
            double ai = rangeMin + (rangeMax - rangeMin) * random.nextDouble();
            int finalI = i;
            IntStream.range(0, N)
                    .forEach(j -> signal.set(j, signal.get(j) + ai * Math.sin((((double) Wgr / finalI * (j + 1))) + fi)));
        }
        return signal;
    }

    private static List<Double> createFp() {
        for (int i = 0; i < valuesArray.size(); i++) {
            for (int j = 0; j < valuesArray.get(i).size(); j++) {
                F.set(i, F.get(i) + signal.get(j) * valuesArray.get(i).get(j));
            }
        }

        return F;
    }

    private static void createTable() {
        IntStream.range(0, N)
                .forEach(i -> F.add(i, 0.0));
        for (int i = 0; i < N; i++) {
            valuesArray.add(new ArrayList<>(Collections.nCopies(N, 0.0)));
        }

        for (int i = 0; i < valuesArray.size(); i++) {
            for (int j = 0; j < valuesArray.get(i).size(); j++) {
                int temp = i * j;
                if (temp >= N) {
                    temp %= N;
                }
                valuesArray.get(i).set(j, Math.cos(fiH.get(temp)) + Math.sin(fiH.get(temp)));
            }
        }
    }

    public static void createPlot(String title, List<Double> list) {
        double[] arr = list.stream().mapToDouble(d -> d).toArray();
        final XYSeriesDemo demo = new XYSeriesDemo(title, arr);
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
    }


    private static void createFI() {
        IntStream.range(0, N)
                .forEach(i -> fiH.add(i, -(2 * Math.PI) * ((double) i / 10)));
    }
}