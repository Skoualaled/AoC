package com.aoc.Y2025;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.regex.Pattern;

public class day06 {

    private static final List<String> data = new ArrayList<>();
    private static final List<long[]> valuesP1 = new ArrayList<>();
    private static List<String> operationP1;
    private static final Map<Integer, String> valuesP2 = new HashMap<>();
    private static final Map<Integer, String> operationP2 = new HashMap<>();

    public static void main(String[] args) {
        var startTimer = Instant.now();
        File input = new File("src/main/resources/Y2025/day06.in");
        readFile(input);
        part1();
        part2();
        var endTimer = Instant.now();
        System.out.println("Execution time: " + Duration.between(startTimer, endTimer).toMillis() + " ms");
    }

    private static void readFile(File file) {
        try {
            Scanner input = new Scanner(file);
            while (input.hasNext()) {
                data.add(input.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static void part1Data(){
        for(String s : data)
            if (Pattern.compile("\\s*\\d+.*").matcher(s).matches()){
                valuesP1.add(Arrays.stream(s.trim().split("\\s+")).mapToLong(Long::parseLong).toArray());
            }else{
                operationP1 = Arrays.stream(s.split("\\s+")).toList();
            }
    }

    private static void part2Data(){
        for(String s : data){
            char[] line = s.toCharArray();
            for (int i = 0; i < s.length(); i++) {
                if(line[i] == '+' || line[i] == '*') operationP2.put(i, String.valueOf(line[i]));
                else if(line[i] != ' '){
                    valuesP2.merge(i, String.valueOf(line[i]), (a, b) -> a+b);
                }
            }
        }
    }

    private static void part1(){
        part1Data();
        long[] table = valuesP1.getFirst();
        for(int idx = 1; idx< valuesP1.size(); idx++){
            for (int i = 0; i < valuesP1.get(idx).length; i++) {
                table[i] = calc(table[i],  valuesP1.get(idx)[i], operationP1.get(i));
            }
        }
        System.out.println("Part 1 : " + Arrays.stream(table).sum());
    }

    private static void part2(){
        long res = 0;
        part2Data();
        List<Integer> keys = operationP2.keySet().stream().sorted().toList();
        for (int i = 0; i < keys.size(); i++) {
            int start = keys.get(i);
            long local = 0;
            int end = i == keys.size()-1 ? data.getFirst().length() : keys.get(i+1)-1;
            List<Integer> filtered = valuesP2.entrySet()
                    .stream().filter(e -> e.getKey()>= start && e.getKey()<= end)
                    .map(Map.Entry::getValue)
                    .mapToInt(Integer::parseInt).boxed().toList();

            for(int val : filtered){
                local = local == 0 ? val : calc(local, val, operationP2.get(start));
            }
            res += local;
        }
        System.out.println("Part 2 : " + res);
    }

    private static long calc(long a, long b, String op){
        return switch (op) {
            case "+" -> a + b;
            default -> a * b;
        };
    }

}
