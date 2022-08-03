package edu.neumont;

import alex.lib.Console;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Random;

public class Player {
    //location row
    //location column
    //amount of keys
    //health
    private int row;
    private int col;
    protected int keys;
    private int health;
    protected ArrayList<String> items = new ArrayList<>();
    public final int MAX_HEALTH = 20;
    private Random random = new Random();

    public Player(Dungeon dungeon) {
        health = MAX_HEALTH;
        spawn(dungeon);
    }

    public void spawn(Dungeon dungeon) {
        for (int row = 0; row < dungeon.rowCount; row++) {
            for (int col = 0; col < dungeon.colCount; col++) {
                if (dungeon.getRoom(col, row).getType() == Room.Type.ENTRANCE) {
                    this.row = row;
                    this.col = col;
                    break;
                }
            }
        }
    }

    public int getHealth() {
        return health;
    }

    public void crawl(Dungeon dungeon) {
        while (health > 0 && dungeon.getRoom(col, row).getType() != Room.Type.EXIT) {
            /*Gameplay loop*/
            //print health
            //DISPLAY LOCATION
            //ROOM EVENTS
            //DISPLAY POSSIBLE DIRECTIONS
            //TAKE INPUT
            View.render(dungeon);
            View.displayBar(health, MAX_HEALTH, "HP", Console.GREEN);
            move(dungeon);
            playRoom(dungeon.getRoom(col, row));

        }
    }

    private void move(Dungeon dungeon) {
        ArrayList<String> directions = new ArrayList<>();
        //check up
        if(dungeon.checkDirection(col, row, 0, -1, keys)) directions.add("North");
        //check right
        if(dungeon.checkDirection(col, row, 1, 0, keys)) directions.add("East");
        //check down
        if(dungeon.checkDirection(col, row, 0, 1, keys)) directions.add("South");
        //check left
        if(dungeon.checkDirection(col, row, -1, 0, keys)) directions.add("West");

        System.out.println("Travel in which direction:");
        String[] options = directions.toArray(new String[0]);
        int travelDir = Console.getMenuSelection(options, false);
        switch (options[travelDir - 1]) {
            case "North" -> row -= 1;
            case "East" -> col += 1;
            case "South" -> row += 1;
            case "West" -> col -= 1;
        }
    }

    private void battle(Monster monster) {
        View.displayBar(monster.getHealth(), monster.getFullHealth(), "Monster (" + monster.getName() + ") HP", Console.RED);
        View.displayBar(health, MAX_HEALTH, "HP", Console.GREEN);

        int defence = 1;
        for (String item : items) {
            if (item.equals("Shield")) {
                defence += 1;
                break;
            }
        }

        int selection = Console.getMenuSelection(new String[]{"Attack", "Defend", "Item"},false);
        switch (selection) {
            case 1:
                int modifier = 1;
                for (String item : items) {
                    if (item.equals("Sword")) {
                        modifier = 2;
                        break;
                    }
                }

                int swing = random.nextInt(0, 21);
                if (swing >= 4) {
                    int damage = ((random.nextInt(1,5) + modifier) * modifier);
                    monster.damage(damage);
                    System.out.println("You attack " + monster.getName() + " for " + damage + " damage!");
                } else {
                    System.out.println("*Woosh* \n You hear the disappointing lack of contact with " + monster.getName() + " (You missed)");
                }
                break;
            case 2:
                for (String item : items) {
                    if (item.equals("Shield")) {
                        defence += 1;
                        break;
                    }
                }
                defence += 1;
                break;
            case 3:
                if (!items.isEmpty()) {
                    useItem(monster);
                } else {
                    System.out.println("You feel around your bag, there's nothing there...");
                }
                break;
        }
        int swing = random.nextInt(0, 21);
        if (swing >= 3) {
            int mDamage = (monster.attack() / defence);
            health -= mDamage;
            String prompt = monster.getName() + " attacks you for " + mDamage + " damage!";
            Console.println(prompt, Console.RED);
        } else {
            System.out.println("You swiftly dodge " + monster.getName() + "'s attack!");
        }
    }

    private void useItem(Monster monster) {
        System.out.println("Use which item?");
        String[] options = items.toArray(new String[0]);
        int selectedItem = Console.getMenuSelection(options, false);
        switch (options[selectedItem - 1]) {
            case "Potion":
                items.remove(selectedItem - 1);
                int temp = random.nextInt(5, 11);
                health += temp;
                Console.println("You drink down the frothy white potion you found earlier in the dungeon. You heal " + temp + "HP! It is a lot thicker than you were expecting.", Console.GREEN);
                break;
            case "Sword":
                System.out.println("You probably should be attacking with this instead of looking at it...");
                break;
            case "Shield":
                System.out.println("This could be really helpful when defending, but you are not doing that right now...");
                break;
            case "Key":
                System.out.println("Keys are really useful to open locked things! If only this was some sort of locked door demon instead of " + monster.getName() + "...");
        }
    }

    private void playRoom(Room room) {
        room.flavor();
        switch (room.getType()) {
            case ITEM -> room_Item(room);
            case LOCKED -> room_locked(room);
            case MONSTER -> room_monster(room);
            case PUZZLE -> room_puzzle(room);
        }
    }

    private void room_Item(Room room) {
        if (Console.getBoolean("Pick up the " + room.getItem() + "? [y/n] ", "y", "n")){
            System.out.println("You grab the " + room.getItem() + " and stick it in your pocket");
            if (room.getItem().equals("Key")) {
                keys++;
            } else {
                items.add(room.getItem());
            }
            room.setType(Room.Type.NORMAL);
        } else {
            System.out.println("\"I'm sure the " + room.getItem() + " will still be there later\" you say to yourself.");
        }
    }

    private void room_locked(Room room) {
        room.setType(Room.Type.NORMAL);
        keys--;
    }

    private void room_monster(Room room) {
        boolean inProgress = true;
        Monster monster = new Monster();
        while (inProgress) {
            battle(monster);
            if (health <= 0 || monster.getHealth() <= 0) {
                inProgress = false;
                if (health < 0) Console.println("You take your last blow and your last breath. You experience feeling for the last time. \nYou are dead.", Console.RED);
            }
        }
        room.setType(Room.Type.NORMAL);
    }

    private void room_puzzle(Room room) {

        StringBuilder set = new StringBuilder("{");
        int[] set_int = new int[5];
        for (int i = 0; i < 5; i++) {
            String comma = (i != 4) ? "," : "";
            int num = random.nextInt(10);
            set.append(num).append(comma);
            set_int[i] = num;
        }
        set.append("}");

        int index = random.nextInt(set_int.length);
        int answer = set_int[index];
        View.displayProblem(set.toString(), "What is the " + (index + 1) + "th element?");
        boolean valid = false;
        while (!valid) {
            if (Console.getInt("Enter selection: ") == answer) valid = true;
        }
        room.setType(Room.Type.NORMAL);
    }
}
