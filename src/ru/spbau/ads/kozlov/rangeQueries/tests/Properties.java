package ru.spbau.ads.kozlov.rangeQueries.tests;

/**
 * @author adkozlov
 */
public class Properties {

    public static final int MIN_ORIGIN = -10000;
    public static final int MAX_BOUND = 10000;

    public static class CorrectnessTestProperties {
        public static final int[] POINTS_COUNTS = new int[]{8, 16, 32, 64, 128};
    }

    public static class AsymptoticTestProperties {
        public static final int RECTANGLES_COUNT = 1000;
    }
}
