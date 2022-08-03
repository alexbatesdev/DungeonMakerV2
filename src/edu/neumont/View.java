package edu.neumont;

import alex.lib.Console;

public class View {
//    protected String[] validRooms = new String[]{"Entrance Room", "Empty Room", "Monster Room", "Key Room", "Locked Room", "Puzzle Room", "Exit Room"};
    // TODO █ <- Save character for nifty health bar stolen from Simon
    public static void render(Dungeon dungeon) {
        for (int row = 0; row < dungeon.rowCount; row++) {
            for (int col = 0; col < dungeon.colCount; col++) {
                Room room = dungeon.getRoom(col, row);
                room.draw();
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void displayBar(int health, final int MAX_HEALTH, String unit, String color) {
        Console.print(unit + ": ", color);
        for (int i = 0; i < health; i++) {
            Console.print("█", color, Console.BLACK_BACKGROUND);
        }
        for (int i = 0; i < (MAX_HEALTH - health); i++) {
            Console.print("█", Console.BLACK, Console.BLACK_BACKGROUND);
        }
        System.out.println();
    }

    public static void displayProblem(String set, String question) {
        for (int y = 1; y <= 5; y++) {
            for (int x = 1; x <= 32; x++) {
                if (x == 3 && y == 2) {
                    Console.print(set, Console.BLACK, Console.WHITE_BACKGROUND);
                    x += set.length();
                }
                if (x == 3 && y == 4) {
                    Console.print(question, Console.BLACK, Console.WHITE_BACKGROUND);
                    x += question.length() - 1;
                } else {
                    Console.print("█", Console.WHITE, Console.WHITE_BACKGROUND);
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}
