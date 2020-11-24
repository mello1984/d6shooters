package game.d6shooters.game;

import lombok.Getter;

import java.util.Random;

@Getter
public class Dice implements Comparable<Dice> {
    private static final Random random = new Random();
    DiceType type;
    private int value;
    private boolean canRerolled;
    private boolean used;


    public Dice(DiceType type) {
        this.type = type;
    }

    // For tests only
    public Dice(DiceType type, int value) {
        this.type = type;
        this.value = value;
    }

    public void nextD6(boolean isFirstTry) {
        if (isFirstTry) {
            clean();
            value = random.nextInt(6) + 1;
            if (type == DiceType.RED && value >= 5) canRerolled = false;
        } else if (canRerolled) value = random.nextInt(6) + 1;
    }

    private void clean() {
        canRerolled = true;
        value = 0;
        used = false;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

//    @Override
//    public String toString() {
//        return "{" + type + ": " + value + '}';
//    }

    @Override
    public int compareTo(Dice o) {
        return type == o.type ? Integer.compare(value, o.value) :
                type == DiceType.WHITE ? -1 : 1;
    }

    public enum DiceType {
        WHITE, RED
    }
}
