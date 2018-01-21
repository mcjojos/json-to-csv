package com.feingold.mergesort;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Main class for challenge #2
 * Test two sets of test cases, one with integers and another one with UUIDs plus a string.
 * Can test any list of comparable elements.
 *
 * @author karanikasg@gmail.com
 */
public class Challenge2 {

    public void start() {

        List<TestCase<Comparable>> testCases = createTestCases();

        printLists(testCases, "INPUT UNSORTED LISTS");

        testCases.forEach(t -> MergeSort.sort(t.getList()));

        printLists(testCases, "RESULT SORTED LISTS");

    }

    private List<TestCase<Comparable>> createTestCases() {
        List<TestCase<Comparable>> list = new ArrayList<>();
        list.addAll(createIntegerTestCases());
        list.addAll(createUUIDTestCases());

        return list;
    }

    private <T> String listAsString(List<T> list) {
        return list.stream().map(String::valueOf).collect(Collectors.joining(", ", "{", "}"));
    }

    private static final class TestCase<T extends Comparable> {
        private final String description;
        private final List<T> list;

        private TestCase(List<T> list) {
            if (list == null) {
                throw new IllegalArgumentException("List is null");
            }
            this.list = list;
            Class<?> clazz;
            if (list.isEmpty()) {
                clazz = Comparable.class;
            } else {
                clazz = list.get(0).getClass();
            }
            this.description = "List of " + list.size() + " " + clazz.getSimpleName() + "s";
        }

        public List<T> getList() {
            return list;
        }

        public String getDescription() {
            return description;
        }

    }

    private void printLists(List<TestCase<Comparable>> testCases, String desc) {
        System.out.println("############################");
        System.out.println(desc);
        testCases.forEach(t -> {
            System.out.print(t.getDescription() + ": ");
            System.out.println(listAsString(t.getList()));
        });
        System.out.println("############################");
    }

    private List<TestCase<Comparable>> createIntegerTestCases() {
        List<Comparable> list1 = randomIntList(0);
        List<Comparable> list2 = randomIntList(1);
        List<Comparable> list3 = randomIntList(3);
        List<Comparable> list4 = new ArrayList<>(Arrays.asList(11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0));
        List<Comparable> list5 = randomIntList(10);
        List<Comparable> list6 = randomIntList(101);

        List<TestCase<Comparable>> integerTestCases = new ArrayList<>(6);
        integerTestCases.add(new TestCase<>(list1));
        integerTestCases.add(new TestCase<>(list2));
        integerTestCases.add(new TestCase<>(list3));
        integerTestCases.add(new TestCase<>(list4));
        integerTestCases.add(new TestCase<>(list5));
        integerTestCases.add(new TestCase<>(list6));

        return integerTestCases;
    }

    private List<TestCase<Comparable>> createUUIDTestCases() {
        List<Comparable> list1 = new ArrayList<>(randomUUIDList(2));
        List<Comparable> list2 = new ArrayList<>(randomUUIDList(5));
        List<Comparable> list3 = new ArrayList<>(randomUUIDList(10));
        List<Comparable> list4 = new ArrayList<>(Arrays.asList("l", "k", "j", "i", "h", "g", "f", "e", "d", "c", "b", "a"));
        List<Comparable> list5 = new ArrayList<>(randomUUIDList(51));

        List<TestCase<Comparable>> stringTestCases = new ArrayList<>(5);
        stringTestCases.add(new TestCase<>(list1));
        stringTestCases.add(new TestCase<>(list2));
        stringTestCases.add(new TestCase<>(list3));
        stringTestCases.add(new TestCase<>(list4));
        stringTestCases.add(new TestCase<>(list5));

        return stringTestCases;

    }

    /**
     * return a list of random integers in the range 0-255
     * @param n number of elements the list should contain
     * @return a list of randomly generated integers
     */
    private ArrayList<Comparable> randomIntList(int n) {
        ArrayList<Comparable> list = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < n; i++) {
            list.add(random.nextInt(256));
        }

        return list;
    }

    /**
     * return a list of random integers in the range 0-255
     * @param n number of elements the list should contain
     * @return a list of randomly generated integers
     */
    private ArrayList<Comparable> randomUUIDList(int n) {
        ArrayList<Comparable> list = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            list.add(UUID.randomUUID());
        }

        return list;
    }

}
