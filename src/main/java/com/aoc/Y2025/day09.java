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
    private static final Map<Rect, Long> rectArea = new HashMap<>();
    private static final List<Rect> edges = new ArrayList<>();


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
        for (int i = 0; i < redTiles.size(); i++) {
            Position first = redTiles.get(i);
            for (int j = i+1; j < redTiles.size(); j++) {
                Position second = redTiles.get(j);
                long score = (long) (Math.abs(first.x() - second.x())+1) * (Math.abs(first.y() - second.y())+1);
                res = Math.max(score, res);
                if(first.x == second.x || first.y == second.y) continue;

                rectArea.put(new Rect(Math.min(first.x, second.x),
                                      Math.max(first.x, second.x),
                                      Math.min(first.y, second.y),
                                      Math.max(first.y, second.y))
                             , score);
            }
        }
        logger.info("Part 1 : {}", res);
    }

    private static void part2(){
        long res = -1;
        LinkedHashMap<Rect, Long> orderedRect = rectArea.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        // List des bord vertical et horizontal
        for (int i = 0; i < redTiles.size(); i++) {
            Rect cur = getRect(i);
            edges.add(cur);
        }

        for(Rect rect : orderedRect.sequencedKeySet()){
            boolean valid = true;
             for(Rect edge : edges){
                if (edge.minX < rect.maxX && edge.minY < rect.maxY && edge.maxX > rect.minX && edge.maxY > rect.minY) {
                    valid = false;
                    break;
                }
            }
            if(valid){
                logger.debug("RECT : {}", rect);
                res = rectArea.get(rect);
                break;
            }
        }
        logger.info("Part 2 : {} ", res);
    }

    private static Rect getRect(int i) {
        Rect cur;
        if (i == redTiles.size() - 1) {
            cur = new Rect(Math.min(redTiles.getFirst().x, redTiles.getLast().x),
                    Math.max(redTiles.getFirst().x, redTiles.getLast().x),
                    Math.min(redTiles.getFirst().y, redTiles.getLast().y),
                    Math.max(redTiles.getFirst().y, redTiles.getLast().y));
        } else {
            cur = new Rect(Math.min(redTiles.get(i).x, redTiles.get(i + 1).x),
                    Math.max(redTiles.get(i).x, redTiles.get(i + 1).x),
                    Math.min(redTiles.get(i).y, redTiles.get(i + 1).y),
                    Math.max(redTiles.get(i).y, redTiles.get(i + 1).y));
        }
        return cur;
    }

    public record Position(int x, int y) {}

    /***
     * X →
     * Y A--------B
     * ↓ |        |
     *   |        |
     *   D--------C
     *   A -> MinX, MinY
     *   B -> MaxX, MinY
     *   C -> MaxX, MaxY
     *   D -> MinX, MaxY
     */
    public record Rect(int minX, int maxX, int minY, int maxY){
        @Override
        public String toString(){
            return "Rect=[(%s, %s), (%s,%s)]".formatted(minX, minY, maxX, maxY);
        }
    }

}
