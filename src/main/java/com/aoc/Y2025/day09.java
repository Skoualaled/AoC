package com.aoc.Y2025;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class day09 {

    private static final List<Position> redTiles = new ArrayList<>();
    private static final Logger logger = LogManager.getLogger(day09.class);
    private static final Map<Rect, Long> rectArea = new HashMap<>();
    private static final List<Edge> edges = new ArrayList<>();


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
                long score = (long) Math.abs(first.x() - second.x()+1) * Math.abs(first.y() - second.y()+1);
                res = Math.max(score, res);
                Position c = new Position(first.x, second.y);
                Position d = new Position(second.x, first.y);
                Edge diagonal = new Edge(first, second);
                Edge bord1 = new Edge(first, c);
                Edge bord2 = new Edge(first, d);
                rectArea.put(new Rect(diagonal, bord1, bord2), score);
            }
        }
        logger.info("Part 1 : {}", res);
    }

    private static void part2(){
        long max = 0;

        for (int i = 0; i < redTiles.size()-1; i++) {
            edges.add(new Edge(redTiles.get(i), redTiles.get(i+1)));
        }
        edges.add(new Edge(redTiles.getLast(), redTiles.getFirst()));
        
        /*logger.debug(edges.toString());
        logger.debug(rectArea.toString());*/
        for(Rect e : rectArea.keySet()){
            boolean valid = true;
            for(Edge b : edges){
                //if (e.diagonal.equals(b)) continue;
                if (b.intersect(e.diagonal) || b.intersect(e.bord1) || b.intersect(e.bord2)) {
                    valid = false;
                    break;
                }
            }
            if (valid) max = Math.max(max, rectArea.get(e));
        }
        logger.info("Part 2 : {}", max);
    }

    public record Position(int x, int y) {}
    public record Rect(Edge diagonal, Edge bord1, Edge bord2){}
    public record Edge(Position p1, Position p2) {
        public Edge swap() {
            return new Edge(p2, p1);
        }
        public boolean intersect(Edge line) {
            Position p3 = line.p1;
            Position p4 = line.p2;
            if (p1.equals(p3) || p1 .equals(p4) || p2.equals(p3) || p2.equals(p4)) return false;
            double denominator = ((p1.x - p2.x)*(p3.y - p4.y) - (p1.y - p2.y)*(p3.x - p4.x));
            double t = ((p1.x - p3.x)*(p3.y - p4.y) - (p1.y - p3.y)*(p3.x - p4.x)) / denominator;
            double u = -1*(((p1.x - p2.x)*(p1.y - p3.y) - (p1.y - p2.y)*(p1.x - p3.x)) / denominator);
            return t>=0 && t <=1 && u >=0 && u <=1 ;
        }
    }

}
