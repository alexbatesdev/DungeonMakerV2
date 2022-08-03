package edu.neumont;

import java.util.Random;

public class Monster {
    Random random = new Random();

    final int MIN_ATTACK = 3;
    final int MAX_ATTACK = 7;

    final int MIN_HEALTH = 8;
    final int MAX_HEALTH = 17;
    private int  fullHealth;

    private int health;
    private String name;

    public Monster() {
        health = random.nextInt(MIN_HEALTH,MAX_HEALTH + 1);
        fullHealth = health;
        name = nameBuilder();
    }

    private String nameBuilder() {
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            temp.append((char)random.nextInt(97, 123));
        }
        temp.insert(0, (char)(temp.charAt(0) - 32));
        temp.deleteCharAt(1);

        return temp.toString();
    }

    public int getFullHealth() {
        return fullHealth;
    }

    public int getHealth() {
        return health;
    }

    public String getName() {
        return name;
    }

    public void damage(int damage) {
        health -= damage;
    }

    public int attack() {
        return random.nextInt(MIN_ATTACK,MAX_ATTACK + 1);
    }
}
