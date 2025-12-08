package com.aoc.Y2025;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

import java.util.stream.Collectors;

public class day08 {

    private static final List<String> data = new ArrayList<>();
    private static final Logger logger = LogManager.getLogger(day08.class);

    public static void main(String[] args) {
        logger.info("Start Day 07");
        var startTimer = Instant.now();
        File input = new File("src/main/resources/Y2025/day08.in");
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
                data.add(input.nextLine());
            }
        } catch (FileNotFoundException e) {
            logger.error("File not found");
            throw new RuntimeException(e);
        }
    }

    private static List<Jbox> scanPart1(){
        List<Jbox> localData = new ArrayList<>();
        for(String s : data){
            List<Integer> values = Arrays.stream(s.split(",")).map(Integer::parseInt).toList();
            Jbox box = new Jbox(values.get(0), values.get(1), values.get(2));
            localData.add(box);
        }
        return localData;
    }

    private static void part1(){
        Map<List<Jbox>, Double> distMap = new HashMap<>();
        List<Jbox> jboxes =  scanPart1();
        Map<Integer, Set<Jbox>> circuits = new HashMap<>();
        for(Jbox curBox : jboxes){
            List<Jbox> toPair = jboxes.stream().filter(b -> !b.equals(curBox)).toList();
            for(Jbox secondBox : toPair){
                List<Jbox> boxList = List.of(curBox, secondBox);
                if(!distMap.containsKey(boxList)){
                    distMap.put(boxList, distBoxes(curBox,secondBox));
                }
            }
        }
        // map sort by values
        distMap = distMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        int limit = 0;
        int MaxId = 0;
        for(List<Jbox> key : distMap.keySet()){
            if (limit == 10) break;
            int curCircuit=0;
            for(Integer c : circuits.keySet()){
                if (circuits.get(c).contains(key.getFirst()) || circuits.get(c).contains(key.getLast())) curCircuit = c; break;
            }
            if (curCircuit == 0){
                MaxId++;
                circuits.put(MaxId, new HashSet<>(key)) ;
            }else{
                circuits.get(curCircuit).addAll(key);
            }
            limit++;
        }
        List<Integer> r = circuits.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().size()))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new))
                .values().stream().toList();

        long res = r.getFirst()* r.get(1) * r.get(2);
        logger.info("Part 1 : {}", res);
    }

    private static void part2(){
        long res =0;
        logger.info("Part 2 : {}", res);
    }

    private record Jbox(int x, int y, int z){}
    public static double distBoxes(Jbox f, Jbox s){
        return Math.sqrt(Math.pow(f.x()-s.x(),2) + Math.pow(f.y()-s.y(),2)+ Math.pow(f.z()-s.z(),2));
    }

}
