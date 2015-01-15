package ru.spbau.ads.kozlov.heaps.tests;

import ru.spbau.ads.kozlov.heaps.BinaryHeap;
import ru.spbau.ads.kozlov.heaps.IHeap;
import ru.spbau.ads.kozlov.heaps.WeakHeap;

import java.util.*;

public class Tester {

    public static void main(String[] args) {
        List<Double> values = Generator.generateListOfDoubles(Generator.LIST_LENGTHS[0]);

        printTestMessage("Insertion", insertionTest(values));
        printTestMessage("Building", buildingTest(values));
        printTestMessage("Extraction", extractionTest(values));

        for (int length : Generator.LIST_LENGTHS) {
            List<ComparisonsCounter<String>> strings = ComparisonsCounter.convert(Generator.generateListOfStrings(length));

            performanceTest(strings);
        }
    }

    private static void printTestMessage(String testName, boolean value) {
        System.out.printf("%s test: %s\n", testName, value ? "passed" : "failed");
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

    private static <T extends Comparable<T>> void performanceTest(Collection<ComparisonsCounter<T>> values) {
        List<ComparisonsCounter<T>> list = new LinkedList<>(values);
        Collections.sort(list);

        ComparisonsCounter.refreshCounter();
        extractAll(new BinaryHeap<>(values));
        long binaryHeapComparisonsCount = ComparisonsCounter.getCount();

        ComparisonsCounter.refreshCounter();
        extractAll(new WeakHeap<>(values));
        long weakHeapComparisonsCount = ComparisonsCounter.getCount();

        System.out.printf("length: %d, count of comparisons for binary heap: %d, for weak heap: %d\n", values.size(),
                binaryHeapComparisonsCount, weakHeapComparisonsCount);
    }

    private static <T extends Comparable<T>> void extractAll(IHeap<T> heap) {
        List<T> result = new ArrayList<>();
        while (!heap.isEmpty()) {
            result.add(heap.extractMin());
        }
    }
}
