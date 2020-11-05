package game.d6shooters.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DicesCup {
    private static Random random = new Random();
    public List<Dice> diceList = new ArrayList<>();

    public DicesCup() {
        diceList.add(new Dice(Dice.DiceType.WHITE));
        diceList.add(new Dice(Dice.DiceType.WHITE));
        diceList.add(new Dice(Dice.DiceType.WHITE));
        diceList.add(new Dice(Dice.DiceType.WHITE));
        diceList.add(new Dice(Dice.DiceType.WHITE));
        diceList.add(new Dice(Dice.DiceType.RED));
        diceList.add(new Dice(Dice.DiceType.RED));
        diceList.add(new Dice(Dice.DiceType.RED));
    }

    public DicesCup(List<Dice> diceList) {
        this.diceList = diceList;
    }

    public static int getD6Int() {
        return random.nextInt(6) + 1;
    }


    public List<Dice> getFirstTurnDices() {
        diceList.forEach(dice -> dice.nextD6(true));
        return diceList;
    }

    public List<Dice> getRerolledDices(String rerollString) {
        rerollString = rerollString.replaceAll("[/D]*", "");
        rerollString.chars().map(i -> Character.digit(i, 10)).distinct().forEach(i -> diceList.get(i - 1).nextD6(false));
        return diceList;
    }

    public boolean checkString(String string) {
        String str = string.replaceAll("[/D]*", "");
        if (str.length() > 8 || str.length() == 0) return false;
        return string.equals("0") || str.chars()
                .map(c -> Character.digit(c, 10))
                .distinct()
                .allMatch(i -> i > 0 && i <= 8 && diceList.get(i - 1).isCanReroll());
    }

    public int getNumberDiceCurrentValue(int value) {
        return (int) diceList.stream().filter(d -> d.getValue() == value).count();
    }

    @Override
    public String toString() {
        return "DicesCup{" + diceList + '}';
    }


}
