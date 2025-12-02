package com.aoc.Y2025;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class day02 {
    public static void main(String[] args) {
        File input = new File("src/main/resources/Y2025/day02.in");
        readFile(input);
        part1();
        part2();
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

    private static void part2(){
        long res =0;
        for(IdRange ir : ids){
            for (long i = ir.start; i <= ir.end; i++) {
                if(isRepeating(i)) res += i;
            }
        }
        System.out.println("part 2 : " + res);
    }

    private static boolean isPalindrome(long number){
        String s = String.valueOf(number);
        if (s.length()%2 != 0) return false;
        return s.substring(0, s.length()/2).equals(s.substring(s.length()/2));
    }

    private static boolean isRepeating(long number){
        String s = String.valueOf(number);
        for(int idx = 1; idx <= s.length()/2; idx++){
            if (s.length()%(idx) != 0) continue;
            String subs = s.substring(0,idx);
            Pattern re = Pattern.compile("^("+subs+")+$");
            Matcher ma = re.matcher(s);
            if (ma.find()) return true;
        }
        return false;
    }

    private record IdRange(long start, long end){}
}
