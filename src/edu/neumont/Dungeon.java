package edu.neumont;

import alex.lib.Console;

import java.util.Random;

public class Dungeon {
    private Room[][] grid;
    protected int colCount;
    protected int rowCount;

    public Dungeon(int colCount, int rowCount) {
        this.colCount = colCount;
        this.rowCount = rowCount;

        grid = new Room[rowCount][colCount];
        reset();
    }

    //Generic Methods

    public Room getRoom(int col, int row) {
        return grid[row][col];
    }

    public void setRoom(int col, int row, Room.Type type, String item) {
        grid[row][col] = new Room(type, item);
    }

    public void setRoom(int col, int row, Room.Type type) {
        grid[row][col] = new Room(type);
    }

    //Make Mode Methods

    public void reset() {
        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < colCount; col++) {
                grid[row][col] = new Room(Room.Type.EMPTY);
            }
        }
    }

    public void placeRoom() {
        System.out.println("What type of room would you like to place?");
        String[] validOptions = new String[Room.roomData[0].length-1];
        for (int i = 0; i < Room.roomData[0].length-1; i++) {
            validOptions[i] = Room.roomData[0][i];
        }
        Room.Type selectedRoom = Room.Type.values()[Console.getMenuSelection(validOptions, false) - 1];
        String item = null;
        if (selectedRoom == Room.Type.ITEM) {
            item = Room.items[Console.getMenuSelection(Room.items, false) - 1];
        }

        View.render(this);
        boolean valid = false;
        int selectedCol = 0;
        int selectedRow = 0;
        while (!valid) {
            selectedCol = Console.getInt("Which column do you want to place it on? (Horizontal) ", 1, colCount) - 1;
            selectedRow = Console.getInt("Which row do you want to place it on? (Vertical) ", 1, rowCount) - 1;
            if (grid[selectedRow][selectedCol].getType() == Room.Type.EMPTY) {
                valid = true;
                System.out.println("Placed " + selectedRoom);
            } else {
                String prompt = "Override room at targeted location? (" + selectedRoom + " -> " + grid[selectedRow][selectedCol].getType() + ") [y/n] ";
                valid = Console.getBoolean(prompt, "y", "n");
            }
        }
        if (item == null) {
            grid[selectedRow][selectedCol] = new Room(selectedRoom);
        } else {
            grid[selectedRow][selectedCol] = new Room(selectedRoom, item);
        }
    }

    public void delRoom() {
        View.render(this);
        int selectedCol = 0;
        int selectedRow = 0;
        selectedCol = Console.getInt("Which column do you want to place it on? (Horizontal) ", 1, colCount) - 1;
        selectedRow = Console.getInt("Which row do you want to place it on? (Vertical) ", 1, rowCount) - 1;
        System.out.println("Removed " + grid[selectedRow][selectedCol]);
        grid[selectedRow][selectedCol].setType(Room.Type.EMPTY);
    }

    public void randomize() {
        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < colCount; col++) {
                int randRoom = new Random().nextInt(Room.Type.values().length);
                grid[row][col] = new Room(Room.Type.values()[randRoom]);
            }
        }
    }

    //Play Mode Methods

    public boolean playCheck() {
        int entrance = 0;
        int exit = 0;
        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < colCount; col++) {
                if (grid[row][col].getType() == Room.Type.ENTRANCE) entrance++;
                if (grid[row][col].getType() == Room.Type.EXIT) exit++;
            }
        }
        if (entrance < 1) {
            Console.println("Unable to start, missing dungeon entrance.", Console.RED);
            return false;
        } else if (entrance > 1) {
            Console.println("Unable to start, unknown starting room. Too many entrances.", Console.RED);
            return false;
        }
        if (exit < 1) {
            Console.println("Warning, this dungeon is unbeatable. There is no exit.", Console.YELLOW);
        }
        return true;
    }

    public boolean checkDirection(int col, int row, int colDir, int rowDir, int keys) {
        if ((row + rowDir >= 0 && row + rowDir < colCount) && (col + colDir >= 0 && col + colDir < rowCount)) {
            if (grid[row + rowDir][col + colDir].getType() == Room.Type.LOCKED && keys > 0) {
                return true;
            } else if (grid[row + rowDir][col + colDir].getType() != Room.Type.EMPTY && grid[row + rowDir][col + colDir].getType() != Room.Type.LOCKED) {
                return true;
            };
        }
        return false;
    }

    public Room[][] getGrid() {
        return grid;
    }
}
