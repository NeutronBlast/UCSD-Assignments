package ucsd;

import processing.core.PApplet;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Select assignment\n" +
                "1. Dual map");

        Scanner input = new Scanner(System.in);
        int op = input.nextInt();

        switch (op){
            case 1:
                String [] arg = {"WeekTwo"};
                WeekTwo win = new WeekTwo(1366, 600);
                PApplet.runSketch(arg, win);
                break;
        }
    }
}
