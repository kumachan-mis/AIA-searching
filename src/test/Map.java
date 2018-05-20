package test;
import java.util.Scanner;

public class Map {
    public static int W, H;
    private static char[][] map;

    static void read() {
        Scanner scanner = new Scanner(System.in);
        W = scanner.nextInt();
        H = scanner.nextInt();
        map = new char[H][W];

        for(int y = 0; y < H; ++y) {
            String temp;
            temp = scanner.next();
            for(int x = 0; x < W; ++x) {
                map[y][x] = temp.charAt(x);
            }
        }
        scanner.close();
    }

    static void show() {
        for(int y = 0; y < H; ++y) {
            for(int x = 0; x < W; ++x) {
                System.out.print(map[y][x] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static char get(int x, int y) {
        return map[y][x];
    }
}
