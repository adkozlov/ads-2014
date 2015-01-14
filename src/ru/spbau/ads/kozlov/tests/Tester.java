package ru.spbau.ads.kozlov.tests;

import ru.spbau.ads.kozlov.heaps.BinaryHeap;
import ru.spbau.ads.kozlov.heaps.IHeap;
import ru.spbau.ads.kozlov.heaps.WeakHeap;

import java.util.*;

public class Tester {

    public static final Random RANDOM = new Random();

    public static final int N_MAX = 1000;

    public static final String TEST_PASSED_MESSAGE = "passed";
    public static final String TEST_FAILED_MESSAGE = "failed";

    private static void printTestMessage(String testName, boolean value) {
        System.out.printf("%s test: %s\n", testName, value ? TEST_PASSED_MESSAGE : TEST_FAILED_MESSAGE);
    }

    public static void main(String[] args) {
        List<ComparisonsCounter<Double>> values = new ArrayList<>();
        for (int i = 0; i < N_MAX; i++) {
            values.add(new ComparisonsCounter<>(RANDOM.nextDouble()));
        }

        printTestMessage("Insertion", insertionTest(values));
        printTestMessage("Building", buildingTest(values));
        printTestMessage("Extraction", extractionTest(values));
    }

    private static <T extends Comparable<T>> boolean insertionTest(Collection<T> values) {
        IHeap<T> binaryHeap = new BinaryHeap<>(values.size());
        IHeap<T> weakHeap = new WeakHeap<>(values.size());
        List<T> list = new ArrayList<>();

        int i = 0;
        for (T value : values) {
            binaryHeap.insert(value);
            T binaryHeapMin = binaryHeap.findMin();

            weakHeap.insert(value);
            T weakHeapMin = weakHeap.findMin();

            list.add(value);
            T listMin = Collections.min(list);

            if (!assertAreEqual(i, binaryHeapMin, weakHeapMin, listMin)) {
                return false;
            }

            i++;
        }

        return true;
    }

    private static <T extends Comparable<T>> boolean buildingTest(Collection<T> values) {
        List<T> list = new ArrayList<>();

        int i = 0;
        for (T value : values) {
            list.add(value);

            IHeap<T> binaryHeap = new BinaryHeap<>(list);
            IHeap<T> weakHeap = new WeakHeap<>(list);

            T binaryHeapMin = binaryHeap.findMin();
            T weakHeapMin = weakHeap.findMin();
            T listMin = Collections.min(list);

            if (!assertAreEqual(i, binaryHeapMin, weakHeapMin, listMin)) {
                return false;
            }

            i++;
        }

        return true;
    }

    private static <T extends Comparable<T>> boolean extractionTest(Collection<T> values) {
        IHeap<T> binaryHeap = new BinaryHeap<>(values);
        IHeap<T> weakHeap = new WeakHeap<>(values);

        List<T> list = new LinkedList<>(values);
        Collections.sort(list);

        int i = 0;
        for (Iterator<T> iterator = list.iterator(); iterator.hasNext(); i++) {
            T binaryHeapMin = binaryHeap.extractMin();
            T weakHeapMin = weakHeap.extractMin();
            T listMin = iterator.next();

            if (!assertAreEqual(i, binaryHeapMin, weakHeapMin, listMin)) {
                return false;
            }
        }

        return true;
    }

    private static <T> boolean assertAreEqual(int testNumber, T binaryHeapMin, T weakHeapMin, T listMin) {
        if (!(binaryHeapMin.equals(weakHeapMin) && weakHeapMin.equals(listMin))) {
            System.out.printf("%d test failed, BH: %s, WH: %s, MIN: %s\n", testNumber, binaryHeapMin, weakHeapMin, listMin);

            return false;
        }

        return true;
    }
}
