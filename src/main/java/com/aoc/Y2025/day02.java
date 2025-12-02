package com.aoc.Y2025;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class day02 {
    public static void main(String[] args) {
        File input = new File("src/main/resources/Y2025/day02.in");
        readFile(input);
        part1();
        //part2();
    }

    private static final List<IdRange> ids = new ArrayList<>();

    private static void readFile(File file) {
        try {
            Scanner read = new Scanner(file);
            while (read.hasNext()) {
                String[] line = read.nextLine().split(",");
                for(String s : line){

                    long[] idTuple = Arrays.stream(s.split("-")).mapToLong(Long::parseLong).toArray();
                    ids.add(new IdRange(idTuple[0], idTuple[1]));
                }
            }
            read.close();
        } catch(FileNotFoundException e) {
            System.err.println("File not found");
        }
    }

    private static void part1(){
        long res =0;
        for(IdRange ir : ids){
            for (long i = ir.start; i <= ir.end; i++) {
                if(isPalindrome(i)) res += i;
            }
        }
        System.out.println("part 1 : " + res);
    }

    private static void part2(){}

    private static boolean isPalindrome(long number){
        String s = String.valueOf(number);
        if (s.length()%2 != 0) return false;
        return s.substring(0, s.length()/2).equals(s.substring(s.length()/2));
    }

    private record IdRange(long start, long end){}
}
