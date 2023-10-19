package com.basejava.webapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class StreamTask {

    public static void main(String[] args) {
        StreamTask streamTask = new StreamTask();

        System.out.println("Result: " + streamTask.minValue(streamTask.getRandomNumbers(random.nextInt(10))) + "\n");

        System.out.println("Result: " + streamTask.oddOrEven(streamTask.getRandomList(random.nextInt(15))));
    }

    static Random random = new Random();

    int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce(0, (acc, val) -> (acc * 10) + val);
    }

    List<Integer> oddOrEven(List<Integer> integers) {
        List<Integer> listEven = new ArrayList<>();
        List<Integer> listOdd = new ArrayList<>();

        int streamSum = integers.stream()
                .mapToInt(integer -> {
                    if (integer % 2 == 0) {
                        listOdd.add(integer);
                    } else {
                        listEven.add(integer);
                    }
                    return integer;
                })
                .sum();

        System.out.println("Sum of elements: " + streamSum);
        if (streamSum % 2 == 0) {
            return listEven;
        } else return listOdd;
    }

    int[] getRandomNumbers(int count) {
        int[] numbers = new int[count];
        for (int i = 0; i < count; i++) {
            int number = random.nextInt(9) + 1;
            numbers[i] = number;
        }
        System.out.println("Numbers are: " + Arrays.toString(numbers));
        return numbers;
    }

    List<Integer> getRandomList(int count) {
        List<Integer> list = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            list.add(random.nextInt(count));
        }
        System.out.println("List contains: " + list);
        return list;
    }

}
