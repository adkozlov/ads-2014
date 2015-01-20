package ru.spbau.ads.kozlov.rangeQueries.tests;

/**
 * @author adkozlov
 */
public class Properties {

    public static final int MIN_DIMENSION = 2;
    public static final int MAX_DIMENSION = 5;

    public static final int MIN_ORIGIN = -10000;
    public static final int MAX_BOUND = 10000;

    public static class CorrectnessTestProperties {
        public static final int[] POINTS_COUNTS = new int[]{8, 16, 32, 64, 128};
    }

    public static class AsymptoticTestProperties {

        public static final int[] POINTS_COUNTS = new int[]{
                10000, 20000, 30000, 40000, 50000,
                100000, 200000, 300000, 400000, 500000,
                600000, 700000, 800000, 900000};

        public static final int RECTANGLES_COUNT = 1000;
    }
}
