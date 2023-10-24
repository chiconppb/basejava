package com.basejava.webapp;

import java.util.*;
import java.util.stream.Collectors;

public class StreamTask {

    public static void main(String[] args) {
        StreamTask streamTask = new StreamTask();

        System.out.println("Result: " + streamTask.minValue(streamTask.getRandomNumbers(random.nextInt(10))) + "\n");

        System.out.println("Result: " + streamTask.oddOrEven(streamTask.getRandomList(random.nextInt(15))));
    }

    static Random random = new Random();

    private int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce(0, (acc, val) -> (acc * 10) + val);
    }

    private List<Integer> oddOrEven(List<Integer> integers) {
        System.out.println("Sum is " + integers.stream().mapToInt(Integer::intValue).sum());

        final Map<Boolean, List<Integer>> map = integers.stream()
                .collect(Collectors.partitioningBy(number -> ((number % 2) == 0)));
        return map.get(map.get(false).size() % 2 != 0);
///////////////////////////////////////////////////////////////////////////////////
//        final Map<Boolean, List<Integer>> map = integers
//                .stream()
//                .collect(Collectors.partitioningBy(number -> (number % 2) == 0));
//
//        boolean isOdd = integers
//                .stream()
//                .mapToInt(Integer::intValue)
//                .sum() % 2 != 0;
//
//        return map.get(!isOdd);
///////////////////////////////////////////////////////////////////////////////////
//        int streamSum = integers.stream()
//                .mapToInt(Integer::intValue)
//                .sum();
//
//        List<Integer> map = integers.stream()
//                .collect(Collectors
//                        .partitioningBy(number -> (number % 2) == 0))
//                .values()
//                .stream()
//                .toList()
//                .get(((streamSum % 2) == 0) ? 0 : 1);
//
//        System.out.println("Sum is " + streamSum);
//        return map;
    }

    private int[] getRandomNumbers(int count) {
        int[] numbers = new int[count];
        for (int i = 0; i < count; i++) {
            int number = random.nextInt(9) + 1;
            numbers[i] = number;
        }
        System.out.println("Numbers are: " + Arrays.toString(numbers));
        return numbers;
    }

    private List<Integer> getRandomList(int count) {
        List<Integer> list = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            list.add(random.nextInt(count));
        }
        System.out.println("List contains: " + list);
        return list;
    }

}
