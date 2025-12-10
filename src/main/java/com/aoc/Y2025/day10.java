package com.aoc.Y2025;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class day10 {

    private static final Logger logger = LogManager.getLogger(day10.class);
    private static final List<Indicator> indicators = new ArrayList<>();

    public static void main(String[] args) {
        logger.info("Start Day 10");
        var startTimer = Instant.now();
        File input = new File("src/main/resources/Y2025/day10.in");
        readFile(input);
        part1();
        part2();
        var endTimer = Instant.now();
        logger.info("Execution time: {} ms", Duration.between(startTimer, endTimer).toMillis());
    }

    private static void readFile(File file) {
        try {
            Scanner input = new Scanner(file);
            while (input.hasNext()) {
                List<String> obj = Arrays.stream(input.nextLine().split(" ")).toList();
                List<List<Integer>> buttons = new ArrayList<>();
                String indicator = "";
                List<Integer> joltage = new ArrayList<>();
                for (String o : obj) {
                    if (o.matches("\\[.*]")) {
                        indicator = o.replaceAll("[\\[\\]]", "");
                    } else if (o.matches("\\((\\d+,*)+\\)")) {
                        List<Integer> val = Arrays.stream(o.replaceAll("[()]", "").split(",")).mapToInt(Integer::parseInt).boxed().toList();
                        buttons.add(val);
                    } else {
                        joltage = Arrays.stream(o.replaceAll("[{}]", "").split(",")).mapToInt(Integer::parseInt).boxed().toList();
                    }
                }
                indicators.add(new Indicator(indicator, buttons, joltage));
            }
        } catch (FileNotFoundException e) {
            logger.error("File not found");
            throw new RuntimeException(e);
        }
    }

    private static void part1(){
        long res = 0;

        for(Indicator key : indicators){
            res+= solve(key);
        }
        logger.info("Part 1 : {}", res);
    }

    private static void part2(){
        long max = 0;

        logger.info("Part 2 : {}", max);
    }

    public static String operate(List<Integer> operation, String key){
        char[] keyChar = key.toCharArray();
        for(Integer op : operation){
            keyChar[op] = (keyChar[op] == '.') ? '#' : '.';
        }
        return new String(keyChar);
    }

    public static int solve(Indicator data){

        String start = ".".repeat(data.indicator.length());

        PriorityQueue<Pair<String, Integer>> states = new PriorityQueue<>(200, Comparator.comparingInt(Pair::getRight));
        states.add(Pair.of(start,0));

        Set<String> visited = new HashSet<>();
        visited.add(start);

        while(!states.isEmpty()){
            Pair<String, Integer> curState = states.poll();
            visited.add(curState.getLeft());
            if(curState.getLeft().equals(data.indicator)) return curState.getRight();
            for(List<Integer> op : data.buttons){
                String newState = operate(op, curState.getLeft());
                int nextDist = curState.getRight()+1;
                if(!visited.contains(newState)) states.add(Pair.of(newState, nextDist));
            }
        }
        return -1;
    }

    public record Indicator(String indicator, List<List<Integer>> buttons, List<Integer> joltage){}
}
