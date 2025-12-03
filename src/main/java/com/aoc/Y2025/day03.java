package com.aoc.Y2025;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        long res;
        res = data.stream().map(day03::getBankJoltage).mapToLong(Long::longValue).sum();
        System.out.println("part 1 : " + res);
    }

    private static void part2(){
        long res =0;

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

}
