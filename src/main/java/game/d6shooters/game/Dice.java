package game.d6shooters.game;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.Random;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Dice implements Comparable<Dice>, Serializable {
    private static final Random random = new Random();
    final DiceType type;
    int value;
    boolean canRerolled;
    @Setter
    boolean used;


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
            canRerolled = true;
            used = false;
            value = random.nextInt(6) + 1;
            canRerolled = !(type == DiceType.RED && value >= 5);
        } else if (canRerolled) value = random.nextInt(6) + 1;
    }

    @Override
    public String toString() {
        return "{" + type + ": " + value + ":" + used + '}';
    }

    @Override
    public int compareTo(Dice o) {
        return type == o.type ? Integer.compare(value, o.value) :
                type == DiceType.WHITE ? -1 : 1;
    }

    public enum DiceType {
        WHITE, RED
    }
}
