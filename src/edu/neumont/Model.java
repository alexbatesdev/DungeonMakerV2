package edu.neumont;
// Runs on JDK 17
import alex.lib.Console;

public class Model {
    public void run() {
        //startup stuff
        int selectedWidth = Console.getInt("How many tiles wide do you want your dungeon to be? ", 1, 100);
        int selectedHeight = Console.getInt("How many tiles long do you want your dungeon to be? ", 1, 100);
        Dungeon dungeon = new Dungeon(selectedWidth, selectedHeight);
        Dungeon playDungeon = copyDungeon(dungeon);

        boolean running = true;
        boolean making = true;
        while (running) {
            while (making) {
                View.render(dungeon);
                int selection = Console.getMenuSelection(new String[]{"Place Room", "Remove Room", "Clear Dungeon", "Randomize Dungeon", "Load Preset Dungeon", "Play Dungeon"},true);
                switch (selection) {
                    case 0:
                        // Quit
                        making = false;
                        running = false;
                        break;
                    case 1:
                        dungeon.placeRoom();
                        break;
                    case 2:
                        dungeon.delRoom();
                        break;
                    case 3:
                        dungeon.reset();
                        break;
                    case 4:
                        //Dev dungeon
                        dungeon.reset();
                        dungeon.randomize();
                        break;
                    case 5:
                        if (dungeon.colCount >= 5 && dungeon.rowCount >= 5) {
                            dungeon.setRoom(2, 0, Room.Type.ITEM, "Key");
                            dungeon.setRoom(4, 0, Room.Type.ITEM, "Sword");
                            dungeon.setRoom(2, 1, Room.Type.MONSTER);
                            dungeon.setRoom(4, 1, Room.Type.NORMAL);
                            dungeon.setRoom(0, 2, Room.Type.EXIT);
                            dungeon.setRoom(1, 2, Room.Type.LOCKED);
                            dungeon.setRoom(2, 2, Room.Type.ENTRANCE);
                            dungeon.setRoom(3, 2, Room.Type.PUZZLE);
                            dungeon.setRoom(4, 2, Room.Type.MONSTER);
                            dungeon.setRoom(2, 3, Room.Type.NORMAL);
                            dungeon.setRoom(2, 4, Room.Type.ITEM, "Shield");
                            dungeon.setRoom(3, 4, Room.Type.ITEM, "Potion");
                        } else {
                            Console.println("Sorry, allocated grid size too small for sample dungeon", Console.RED);
                        }

                        break;
                    case 6:
                        //Switch to playing the dungeon
                        if (dungeon.playCheck()) {
                            System.out.println("Entering dungeon...");
                            playDungeon = copyDungeon(dungeon);
                            making = false;
                        }
                        break;
                }
            }
            while (!making && running) {
                Player player = new Player(playDungeon);
                player.crawl(playDungeon);
                System.out.println("Leaving Dungeon...");
                if (!Console.getBoolean("Return to editor? [y/n] ", "y", "n")) {
                    running = false;
                }
                making = true;
            }
        }
    }

    private Dungeon copyDungeon(Dungeon dungeon) {
        Dungeon temp = new Dungeon(dungeon.colCount, dungeon.rowCount);
        for (int row = 0; row < dungeon.rowCount; row++) {
            for (int col = 0; col < dungeon.colCount; col++) {
                temp.getRoom(col, row).setType(dungeon.getRoom(col, row).getType());
                temp.getRoom(col, row).setItem(dungeon.getRoom(col,row).getItem());
                temp.getRoom(col,row).update();
            }
        }
        return temp;
    }
}
