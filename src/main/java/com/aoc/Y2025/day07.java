package com.aoc.Y2025;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class day07 {

    private static final List<String> data = new ArrayList<>();
    private static final Logger logger = LogManager.getLogger(day07.class);
    private static final List<Position> splitters = new ArrayList<>();

    public static void main(String[] args) {
        logger.info("Start Day 07");
        var startTimer = Instant.now();
        File input = new File("src/main/resources/Y2025/day07.in");
        readFile(input);
        listSplitter();
        part1();
        part2();
        var endTimer = Instant.now();
        logger.info("Execution time: {} ms", Duration.between(startTimer, endTimer).toMillis());
    }

    private static void readFile(File file) {
        try {
            Scanner input = new Scanner(file);
            while (input.hasNext()) {
                data.add(input.nextLine());
            }
        } catch (FileNotFoundException e) {
            logger.error("File not found");
            throw new RuntimeException(e);
        }
    }

    private static void part1(){
        long res = 0;
        Set<Integer> beams = new HashSet<>();
        beams.add(data.getFirst().indexOf("S"));
        Set<Integer> rows = splitters.stream().map(Position::x).sorted().collect(Collectors.toCollection(LinkedHashSet::new));
        for(Integer row : rows){
            Set<Integer> newBeams = new HashSet<>();
            Set<Integer> cur = splitters.stream()
                    .filter(s -> s.x() == row)
                    .map(Position::y)
                    .collect(Collectors.toSet());
            for(Integer beam : beams){
                if(cur.contains(beam)){
                    res++;
                    newBeams.add(beam+1);
                    newBeams.add(beam-1);
                } else newBeams.add(beam);
            }
           beams = newBeams;
        }
        logger.info("Part 1 : {}", res);
    }

    private static void part2(){
        Map<Integer, Long> beams = new HashMap<>();
        beams.put(data.getFirst().indexOf("S"),1L);
        Set<Integer> rows = splitters.stream().map(Position::x).sorted().collect(Collectors.toCollection(LinkedHashSet::new));
        for(Integer row : rows){
            Map<Integer, Long> newBeams = new HashMap<>();
            Set<Integer> cur = splitters.stream()
                    .filter(s -> s.x() == row)
                    .map(Position::y)
                    .collect(Collectors.toSet());
            for(Integer beam : beams.keySet()){
                if(cur.contains(beam)){
                    newBeams.merge(beam+1, beams.get(beam), Long::sum);
                    newBeams.merge(beam-1, beams.get(beam), Long::sum);
                } else newBeams.merge(beam, beams.get(beam), Long::sum);
            }
           beams = newBeams;
        }
        long res = beams.values().stream().reduce(0L, Long::sum);
        logger.info("Part 2 : {}", res);
    }

    public static void listSplitter(){
        int idx = 0;
        for(String s : data){
            int y = s.indexOf("^");
            while (y>0) {
               splitters.add(new Position(idx, y));
                y = s.indexOf("^", y+1);
            }
            idx++;
        }
    }

    public record Position(int x, int y) {}

}
