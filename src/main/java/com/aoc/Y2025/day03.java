package com.aoc.Y2025;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class day03 {
    public static void main(String[] args) {
        File input = new File("src/main/resources/Y2025/day03.in");
        readFile(input);
        part1();
        part2();
    }

    private static final List<String> data = new ArrayList<>();

    private static void readFile(File file) {
        try {
            Scanner read = new Scanner(file);
            while (read.hasNext()) {
                data.add(read.nextLine());
            }
            read.close();
        } catch(FileNotFoundException e) {
            System.err.println("File not found");
        }
    }

    private static void part1(){
        long res = data.stream().map(day03::getBankJoltage).mapToLong(Long::longValue).sum();
        System.out.println("part 1 : " + res);
    }

    private static void part2(){
        long res = data.stream().map(day03::get12BankJoltage).mapToLong(Long::longValue).sum();
        System.out.println("part 2 : " + res);
    }

    private static long getBankJoltage(String bank){
        int idx=0;
        char fmax='0';
        char smax='0';
        long res=0;
        char[] charBank = bank.toCharArray();
        for (int i = 0; i < charBank.length-1; i++) {
            if(charBank[i] > fmax){
                fmax = charBank[i];
                idx =i;
            }
        }
        for (int i = idx+1; i < charBank.length; i++) {
            if(charBank[i] > smax){
                smax = charBank[i];
            }
        }
        return Long.parseLong(StringUtils.join(fmax,smax));
    }
    private static long get12BankJoltage(String bank) {
        List<Integer> batteries = Arrays.stream(bank.split("")).mapToInt(Integer::parseInt).boxed().toList();
        StringBuilder s = new StringBuilder();
        int idx = 0;
        for(int i = 12; i > 0; i--){
            idx = boundedMaxIndex(batteries, idx, i) + 1;
            s.append(batteries.get(idx - 1));
        }
        return Long.parseLong(s.toString());
    }
    private static int boundedMaxIndex(List<Integer> batteries, int minIdx, int maxIdx){
        int localMax = 0;
        int index = 0;
        for(int i = minIdx; i <= batteries.size() - maxIdx; i++){
            if (batteries.get(i) > localMax){
                localMax = batteries.get(i);
                index = i;
            }
        }
        return index;
    }
}
