package ru.spbau.ads.kozlov.tests;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author adkozlov
 */
public class Generator {

    private static final Random RANDOM = new Random();
    private static final int ALPHABET_LENGTH = 26;
    private static final byte OFFSET = 'a';

    public static final int STRING_MAX_LENGTH = 100;
    public static final int[] LIST_LENGTHS = {1000, 10000, 100000, 1000000};

    public static List<Double> generateListOfDoubles(int length) {
        List<Double> result = new ArrayList<>();

        for (int i = 0; i < length; i++) {
            result.add(RANDOM.nextDouble());
        }

        return result;
    }

    public static List<Integer> generateListOfInts(int length, int bound) {
        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < length; i++) {
            result.add(RANDOM.nextInt(bound));
        }

        return result;
    }

    public static String generateString() {
        byte[] buffer = new byte[RANDOM.nextInt(STRING_MAX_LENGTH)];

        for (int i = 0; i < buffer.length; i++) {
            buffer[i] = (byte) (OFFSET + RANDOM.nextInt(ALPHABET_LENGTH));
        }

        return new String(buffer);
    }

    public static List<String> generateListOfStrings(int length) {
        List<String> result = new ArrayList<>();

        for (int i = 0; i < length; i++) {
            result.add(generateString());
        }

        return result;
    }
}
