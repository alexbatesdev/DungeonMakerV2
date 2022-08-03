package edu.neumont;

import alex.lib.Console;

public class Room {
    public static final String[][] roomData = {
            {"Entrance Room", "Empty Room", "Monster Room", "Item Room", "Locked Room", "Puzzle Room", "Exit Room", ""},
            {"/_\\", "[_]", "ò~ó", "[?]", "|¡|", "|#|", "\\_/", " _ "},
            {Console.BLACK, Console.WHITE, Console.BLACK, Console.BLACK, Console.WHITE, Console.BLACK, Console.BLACK, Console.BLACK},
            {Console.BLUE_BACKGROUND, Console.BLACK_BACKGROUND, Console.RED_BACKGROUND, Console.CYAN_BACKGROUND, Console.BLACK_BACKGROUND, Console.PURPLE_BACKGROUND, Console.GREEN_BACKGROUND, Console.WHITE_BACKGROUND},
            {"You are standing in the entrance of an ancient sprawling dungeon that was probably made by someone rad as fuck.",
                    "You find yourself in an empty room, there's not much to do other than leave it.",
                    "You step into this room and see a monster standing there menacingly. You gotta fight it because that's what you do in dungeons!",
                    "You see an item nonchalantly sitting in the middle of the room. ",
                    "You unlock the door and enter the locked room, inside you find... an empty room!",
                    "As you enter this room your foot gets caught on a trip wire which locks all the doors.\nSitting in the middle of this empty room is a table with a piece of paper. Did someone call for sets?",
                    "As you walk into through the hallway into this room you see the gleam of sunlight shining from the end of the hall\nCongratulations! You made it out!",
                    "This isn't a room you should be able to be in?"}};
    //roomData[0][] is the printed out String name used for options
    //roomData[1][] is the printed out art for displaying the map
    //roomData[2][] is the color of the map text
    //roomData[3][] is the background color of the map icon
    /*The reason this stuff isn't in the constructor is that they are static. All rooms of the same type should have the same name, art, and colors*/

    public static final String[] items = {"Key", "Potion", "Sword", "Shield"};
    public enum Type {
        ENTRANCE,
        NORMAL,
        MONSTER,
        ITEM,
        LOCKED,
        PUZZLE,
        EXIT,
        EMPTY
    }

    private Type type;
    private String flavor;
    private String icon;
    private String color;
    private String backgroundColor;
    private String item;

    public Room(Type type) {
        this.type = type;
        item = null;
        update();
    }

    public Room(Type type, String item) {
        this.type = type;
        this.item = item;
        update();
    }

    public void update() {
        int typeIndex = getIntByType(type);
        icon = roomData[1][typeIndex];
        color = roomData[2][typeIndex];
        backgroundColor = roomData[3][typeIndex];
        flavor = roomData[4][typeIndex];
    }

    private int getIntByType(Type type) {
        for (int i = 0; i < Type.values().length; i++) {
            if (Type.values()[i] == type) return i;
        }
        return -1;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
        update();
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public void flavor() {
        System.out.println(flavor);
    }

    public void draw() {
        Console.print(icon, color, backgroundColor);
    }

    //     TODO Make a toggle switch room? switches are either on orr off, meaning you can or can't enter that room, just toggles between locked and unlocked
    // TODO Potion room to heal?
}
/*MAPLE NOTES*/
//
//class Room
//{
//    public enum Type
//    {
//        ENTRANCE,
//        EMPTY,
//        MONSTER,
//        KEY,
//        LOCKED,
//        PUZZLE,
//        EXIT,
//    }
//
//    Type type;
//    String description;
//    String design;
//    int color;
//
//    public (Type, String, design, color) {
//
//
//
//}
//
//
//        [0,0,0]
//        [0,0,0]
//        [0,0,0]