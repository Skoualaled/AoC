package com.aoc.Y2025;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class day05 {

    private static final List<Range> fresh = new ArrayList<>();
    private static final List<Long> ingredients = new ArrayList<>();

    public static void main(String[] args) {
        File input = new File("src/main/resources/Y2025/day05.in");
        readFile(input);
        part1();
        part2();
    }

    private static void readFile(File file){
        try {
            Scanner input = new Scanner(file);
            while(input.hasNext()){
                String line = input.nextLine();
                if (line.isEmpty()) continue;
                if (line.contains("-")){
                    long[] values = Arrays.stream(line.split("-")).mapToLong(Long::parseLong).toArray();
                    fresh.add(new Range(values[0], values[1]));
                }else {
                    ingredients.add(Long.parseLong(line));
                }
            }
            fresh.sort(Comparator.comparing(Range::start));
            reduceRanges();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static void part1(){
        int res = 0;

        for(Long i : ingredients){
            int add = fresh.stream().map(r -> r.inRange(i)).mapToInt(Integer::intValue).sum();
            res += add;
        }
        System.out.println("Part 1 : " + res);
    }


    private static void part2(){
        long res = 0;
        for(Range r : fresh){
            res += r.end() -  r.start()+1;
        }
        System.out.println("Part 2 : " + res);
    }

    private static void reduceRanges(){
        int idx = 0;
        while(idx < fresh.size()-1) {
            if(fresh.get(idx).end()+1 >= fresh.get(idx+1).start()){
                long s = fresh.get(idx).start();
                long e = Math.max(fresh.get(idx).end(), fresh.get(idx + 1).end());
                fresh.set(idx, new Range(s, e));
                fresh.remove(idx+1);
            }else{
                idx++;
            }
        }
    }

    public record Range(long start, long end){
        public int inRange(long val) {
            return val >= start && val <= end ? 1 :0;
        }
    }
}
