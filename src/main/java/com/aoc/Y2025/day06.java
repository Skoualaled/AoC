package com.aoc.Y2025;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Pattern;

public class day06 {

    private static final List<long[]> values = new ArrayList<>();
    private static List<String> operation;

    public static void main(String[] args) {
        File input = new File("src/main/resources/Y2025/day06.in");
        readFile(input);
        part1();
        part2();
    }

    private static void readFile(File file){
        try {
            Scanner input = new Scanner(file);
            while(input.hasNext()){
                String line = input.nextLine();
                if (Pattern.compile("\\s*\\d+.*").matcher(line).matches()){
                    values.add(Arrays.stream(line.trim().split("\\s+")).mapToLong(Long::parseLong).toArray());
                }else{
                    operation = Arrays.stream(line.split("\\s+")).toList();
                }

            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static void part1(){
        long[] table = values.getFirst();
        for(int idx = 1; idx<values.size(); idx++){
            for (int i = 0; i < values.get(idx).length; i++) {
                table[i] = calc(table[i],  values.get(idx)[i], operation.get(i));
            }
        }
        System.out.println("Part 1 : " + Arrays.stream(table).sum());
    }


    private static void part2(){
        long res = 0;

        System.out.println("Part 2 : " + res);
    }

    private static long calc(long a, long b, String op){
        return switch (op) {
            case "+" -> a + b;
            default -> a * b;
        };
    }

}
