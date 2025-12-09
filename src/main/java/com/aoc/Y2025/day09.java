package com.aoc.Y2025;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

public class day09 {

    private static final List<Position> redTiles = new ArrayList<>();
    private static final Logger logger = LogManager.getLogger(day09.class);
    private static final List<Position> splitters = new ArrayList<>();

    public static void main(String[] args) {
        logger.info("Start Day 09");
        var startTimer = Instant.now();
        File input = new File("src/main/resources/Y2025/day09.in");
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
               List<Integer> tile = Arrays.stream(input.nextLine().split(",")).mapToInt(Integer::parseInt).boxed().toList();
               redTiles.add(new Position(tile.getFirst(), tile.getLast()));
            }
        } catch (FileNotFoundException e) {
            logger.error("File not found");
            throw new RuntimeException(e);
        }
    }

    private static void part1(){
        long res = 0;
        for(Position first :redTiles){
            for(Position second : redTiles){
                long score = (long) Math.abs(first.x() - second.x()+1) * Math.abs(first.y() - second.y()+1);
                res = Math.max(score, res);
            }
        }
        logger.info("Part 1 : {}", res);
    }

    private static void part2(){

        long res = 0;
        logger.info("Part 2 : {}", res);
    }

    public record Position(int x, int y) {}

}
