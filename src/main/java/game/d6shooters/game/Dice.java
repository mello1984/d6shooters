package game.d6shooters.game;

import java.util.Random;

public class Dice {
    private static Random random = new Random();
    DiceType type;
    private int value;
    private boolean canReroll;


    public Dice(DiceType type) {
        this.type = type;
    }


    public Dice(DiceType type, int value) {
        this.type = type;
        this.value = value;
    }

    public void nextD6(boolean isFirstTry) {
        if (isFirstTry) {
            clean();
            value = random.nextInt(6) + 1;
            if (type == DiceType.RED && value >= 5) canReroll = false;
        } else if (canReroll) value = random.nextInt(6) + 1;
    }

    private void clean() {
        canReroll = true;
        value = 0;
    }

    public DiceType getType() {
        return type;
    }

    public int getValue() {
        return value;
    }

    public boolean isCanReroll() {
        return canReroll;
    }

    @Override
    public String toString() {
        return "{" + type + ": " + value + '}';
    }

    public enum DiceType {
        WHITE, RED
    }
}