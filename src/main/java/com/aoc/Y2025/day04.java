package com.aoc.Y2025;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class day04 {
    public static void main(String[] args) {
        File input = new File("src/main/resources/Y2025/day04.in");
        readFile(input);
        part1();
        part2();
    }

    private static final int bound = 135;
    private static final char[][] rollArray = new char[bound][bound];

    private static void readFile(File file) {
        try {
            Scanner read = new Scanner(file);
            int x =0;
            while (read.hasNext()) {
                char[] line = read.nextLine().toCharArray();
                rollArray[x] = line;
                x++;
            }
            read.close();
        } catch(FileNotFoundException e) {
            System.err.println("File not found");
        }
    }

    private static void part1(){
        long res=0;
        for (int x = 0; x < rollArray.length; x++) {
            for (int y = 0; y < rollArray[x].length; y++) {
                if(rollArray[x][y] == '@'){
                    res += checkCross(x,y) < 4 ? 1 :0;
                }
            }
        }
        System.out.println("part 1 : " + res);
    }

    private static void part2(){
        long res = 0;
        ArrayList<Roll> toRemove = new ArrayList<>();
        do {
            while (!toRemove.isEmpty()) {
                Roll r = toRemove.getFirst();
                rollArray[r.x][r.y] = '.';
                toRemove.remove(r);
            }
            for (int x = 0; x < rollArray.length; x++) {
                for (int y = 0; y < rollArray[x].length; y++) {
                    if(rollArray[x][y] == '@'){
                        if(checkCross(x,y) < 4){
                            toRemove.add(new Roll(x,y));
                        }
                    }
                }
            }
            res += toRemove.size();
        } while (!toRemove.isEmpty());

        System.out.println("part 2 : " + res);
    }

    private static boolean inBound(int x, int y) {
        return x >= 0 && x < rollArray.length && y >= 0 && y < rollArray[x].length;
    }

    private static int checkCross(int x, int y) {
        int sum=0;
        int[] d = {1,0,-1};
        for (int i : d) {
            for (int j : d) {
                if (i==0 && j ==0) continue;
                if (inBound(x+i,y+j) && rollArray[x+i][y+j] == '@') sum += 1;
            }
        }
        return sum;
    }

    public record Roll(int x, int y){}
}
