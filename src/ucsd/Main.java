package ucsd;

import processing.core.PApplet;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Select assignment\n" +
                "1. Dual map\n" +
                "2. Map with earthquake data\n" +
                "10. Extra 1: Life expectancy map by country\n");

        Scanner input = new Scanner(System.in);
        int op = input.nextInt();

        switch (op){
            case 1:
                String [] arg = {"WeekTwo"};
                WeekTwo win = new WeekTwo(1366, 600);
                PApplet.runSketch(arg, win);
                break;
            case 2:
                String [] arg_2 = {"WeekThree"};
                WeekThree win_2 = new WeekThree();
                PApplet.runSketch(arg_2, win_2);
                break;
            case 10:
                String [] arg_10 = {"ExtraOne"};
                ExtraOne win_10 = new ExtraOne();
                PApplet.runSketch(arg_10, win_10);
        }
    }
}
