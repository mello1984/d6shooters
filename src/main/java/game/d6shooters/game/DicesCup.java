package game.d6shooters.game;

import game.d6shooters.source.Icon;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.*;

@Getter
@Setter
public class DicesCup implements Serializable {
    private static final Random random = new Random();
    protected static final Map<Integer, String> dices = new HashMap<>() {{
        put(1, Icon.DICE1.get());
        put(2, Icon.DICE2.get());
        put(3, Icon.DICE3.get());
        put(4, Icon.DICE4.get());
        put(5, Icon.DICE5.get());
        put(6, Icon.DICE6.get());
    }};
    private static final int MAX_VALUE = 8;
    protected List<Dice> diceList = new ArrayList<>();


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

    public static int getD6Int() {
        return random.nextInt(6) + 1;
    }

    public List<Dice> getFirstTurnDices() {
        diceList.forEach(dice -> dice.nextD6(true));
        return diceList;
    }

    public List<Dice> getRerolledDices(String rerollString) {
        rerollString = rerollString.replaceAll("[^1-" + getMaxValue() + "]*", "");
        rerollString.chars()
                .map(i -> Character.digit(i, 10))
                .distinct()
                .forEach(i -> diceList.get(i - 1).nextD6(false));
        return diceList;
    }

    public boolean checkString(String string, boolean withBinocular) {
        if (string.equals("0")) return true;
        String str = string.replaceAll("[^1-" + getMaxValue() + "]*", "");
        if (str.length() == 0) return false;

        int closedDices = (int) str.chars()
                .map(c -> Character.digit(c, 10))
                .distinct()
                .mapToObj(i -> diceList.get(i - 1))
                .filter(d -> !d.isCanRerolled())
//                .filter(d -> d.getValue() >= 5 && d.getType()== Dice.DiceType.RED)
                .count();

        return !withBinocular ? closedDices == 0 : closedDices <= 1;
    }

    protected int getMaxValue() {
        return MAX_VALUE;
    }


    public int getCountActiveDiceCurrentValue(int value) {
        return (int) diceList.stream()
                .filter(dice -> dice.getValue() == value && !dice.isUsed())
                .count();
    }

    public int getCountActiveDiceCurrentValue(int value, Dice.DiceType diceType) {
        return (int) diceList.stream()
                .filter(dice -> dice.getValue() == value && !dice.isUsed() && dice.getType() == diceType)
                .count();
    }

    public void setUsedDiceCurrentValue(int value) {
        diceList.stream()
                .filter(dice -> dice.getValue() == value)
                .forEach(dice -> dice.setUsed(true));
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (diceList.stream().anyMatch(d -> d.getType() == Dice.DiceType.WHITE && !d.isUsed())) {
            stringBuilder.append(Icon.WHITESQUARE.get());
            diceList.stream().filter(d -> d.getType() == Dice.DiceType.WHITE && !d.isUsed()).forEach(d -> stringBuilder.append(dices.get(d.getValue())));
        }
        if (diceList.stream().anyMatch(d -> d.getType() == Dice.DiceType.RED && !d.isUsed())) {
            stringBuilder.append("\n").append(Icon.REDSQUARE.get());
            diceList.stream().filter(d -> d.getType() == Dice.DiceType.RED && !d.isUsed()).forEach(d -> stringBuilder.append(dices.get(d.getValue())));
        }
        return stringBuilder.toString();
    }
}
