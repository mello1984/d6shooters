package game.d6shooters.game;

import game.d6shooters.bot.Icon;

import java.util.*;

public class DicesCup {
    private static final Random random = new Random();
    private List<Dice> diceList = new ArrayList<>();
    private static final Map<Integer, String> dices = new HashMap<>() {{
        put(1, Icon.DICE1.get());
        put(2, Icon.DICE2.get());
        put(3, Icon.DICE3.get());
        put(4, Icon.DICE4.get());
        put(5, Icon.DICE5.get());
        put(6, Icon.DICE6.get());
    }};

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

    public List<Dice> getDiceList() {
        return diceList;
    }

    public void setDiceList(List<Dice> diceList) {
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
        if (str.length() > 8 || str.length() == 0) return false; //Поправить константу 8
        return string.equals("0") || str.chars()
                .map(c -> Character.digit(c, 10))
                .distinct()
                .allMatch(i -> i > 0 && i <= 8 && diceList.get(i - 1).isCanRerolled());
    }

    public int getCountActiveDiceCurrentValue(int value) {
        return (int) diceList.stream()
                .filter(dice -> dice.getValue() == value && !dice.isUsed())
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
