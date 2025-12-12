package com.aoc.Y2025;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.io.File;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.List;


public class day12 {

    private static final Logger logger = LogManager.getLogger(day12.class);
    private static final List<Shape> shapes = new ArrayList<>();
    private static final List<Region> regions = new ArrayList<>();

    public static void main(String[] args) {
        logger.info("Start Day 12");
        var startTimer = Instant.now();
        File input = new File("src/main/resources/Y2025/day12.in");
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
                String data = input.nextLine();
                if(data.matches("^\\d:$")){
                    String id = data.split(":")[0];
                    String inputShape = input.nextLine() +
                            input.nextLine() +
                            input.nextLine();
                    shapes.add(new Shape(id, inputShape));
                } else if (!data.isEmpty()) {
                    Integer l = Integer.valueOf(data.substring(0,data.indexOf('x')));
                    Integer w = Integer.valueOf(data.substring(data.indexOf('x')+1,data.indexOf(':')));
                    List<Integer> quantity = Arrays.stream(data.split(": ")[1].split(" ")).mapToInt(Integer::parseInt).boxed().toList();
                    regions.add(new Region(l,w,quantity));
                }
            }
        } catch (FileNotFoundException e) {
            logger.error("File not found");
            throw new RuntimeException(e);
        }
    }

    private static void part1(){
        long res = 0L;
        List<Integer> shapesArea = shapes.stream().map(s -> s.shape.replaceAll("\\.", "")).map(String::length).toList();
        for(Region region : regions){
            int regionArea = region.l*region.w;
            int requiredArea = 0;
            for (int i = 0; i < region.quantity.size(); i++) {
                requiredArea+= region.quantity.get(i) * shapesArea.get(i);
            }
            if(requiredArea< regionArea) res+=1;
        }
        logger.info("Part 1 : {}", res);
    }


    private static void part2(){
        long max = 0;
        logger.info("Part 2 : {}", max);
    }
    public record Shape(String id, String shape){}
    public record Region(Integer w, Integer l, List<Integer> quantity){}
}
