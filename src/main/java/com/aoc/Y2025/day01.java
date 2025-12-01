package com.aoc.Y2025;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class day01 {

    public static void main(String[] args) {
        File input = new File("src/main/resources/Y2025/day01.in");
        readFile(input);
        part1(); // 1172
        part2();
    }

    private static final List<Rotation> rotations = new ArrayList<>();
    private static final int start  = 50;

    private static void readFile(File file) {
        try {
            Scanner read = new Scanner(file);
            while (read.hasNext()) {
                String line = read.nextLine();
                Rotation curRot = new Rotation(line.substring(0,1), Integer.parseInt(line.substring(1)));
                rotations.add(curRot);
            }
            read.close();
        } catch(FileNotFoundException e) {
            System.err.println("File not found");
        }
    }

    private static void part1() {
       int password=0;
       int dial = start;

       for(Rotation r : rotations ){
           dial += r.getValue();
           dial = Math.floorMod(dial,100);

           if(dial == 0){
               password++;
           }

       }
       System.out.println("part1 : "+ password);
    }

    private static void part2(){
        int password=0;
        int dial = start;

       for(Rotation r : rotations ) {

           int d = dial+r.getValue();
           if(dial*d < 0 || d == 0) password++;
           d = Math.abs(d);
           while ( d >= 100) {
               d-=100;
               password++;
           }
           dial = Math.floorMod(dial+r.getValue(), 100);
       }

       System.out.println("part2 : "+ password);

    }

    public record Rotation(String dir, Integer value){
        public Integer getValue() {
            if (dir.equals("L")){
                return value*-1;
            }else{
                return value;
            }
        }
    }
}