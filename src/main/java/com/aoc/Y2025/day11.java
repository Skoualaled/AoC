package com.aoc.Y2025;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class day11 {

    private static final Logger logger = LogManager.getLogger(day11.class);
    private static final Map<String, List<String>> nodes = new HashMap<>();

    public static void main(String[] args) {
        logger.info("Start Day 11");
        var startTimer = Instant.now();
        File input = new File("src/main/resources/Y2025/day11.in");
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
                String[] data = input.nextLine().split(": ");
                List<String> connexion = Arrays.stream(data[1].split(" ")).toList();
                nodes.put(data[0], connexion);
            }
        } catch (FileNotFoundException e) {
            logger.error("File not found");
            throw new RuntimeException(e);
        }
    }

    private static void part1(){
        Set<LinkedList<String>> answer = solve("you");

        logger.info("Part 1 : {}", answer.size());
    }

    private static Set<LinkedList<String>> solve(String start) {
        LinkedList<String> lStart = new LinkedList<>(List.of(start));
        Stack<LinkedList<String>> states = new Stack<>();
        states.add(lStart);

        Set<LinkedList<String>> answer = new HashSet<>();

        while (!states.isEmpty()) {
            LinkedList<String> curState = states.pop();
            for (String nextNode : nodes.get(curState.getLast())) {
                LinkedList<String> nexState = new LinkedList<>(curState);
                nexState.add(nextNode);
                if (nextNode.equals("out")) answer.add(nexState);
                else if (!curState.contains(nextNode)) states.add(nexState);
            }
        }
        return answer;
    }


    private static void part2(){
        long max = 0;
        logger.info("Part 2 : {}", max);
    }

}
