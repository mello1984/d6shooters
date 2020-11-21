package game.d6shooters.game;

import lombok.Value;

import java.util.*;

public class PokerDices extends DicesCup {
    public List<Dice> diceList = new ArrayList<>();
    private Combination combination;
    Random random = new Random();


    public PokerDices() {
        diceList.add(new Dice(Dice.DiceType.WHITE));
        diceList.add(new Dice(Dice.DiceType.WHITE));
        diceList.add(new Dice(Dice.DiceType.WHITE));
        diceList.add(new Dice(Dice.DiceType.WHITE));
        diceList.add(new Dice(Dice.DiceType.WHITE));
    }

    public int getResult() {
        diceList.sort(Comparator.comparingInt(Dice::getValue));
        int d1 = diceList.get(0).getValue();
        int d2 = diceList.get(1).getValue();
        int d3 = diceList.get(2).getValue();
        int d4 = diceList.get(3).getValue();
        int d5 = diceList.get(4).getValue();
        if (d1 == d5) combination = new Combination(6, d3, 0);
        else if (d1 == d4 || d2 == d5) combination = new Combination(5, d3, 0);
        else if (d1 == 1 && d2 == 2 && d3 == 3 && d4 == 4 && d5 == 5) combination = new Combination(4, d3, 0);
        else if (d1 == 2 && d2 == 3 && d3 == 4 && d4 == 5 && d5 == 6) combination = new Combination(4, d3, 0);
        else if (d1 == d3 && d4 == d5) combination = new Combination(3, d3, d4);
        else if (d1 == d2 && d3 == d5) combination = new Combination(3, d2, d3);
        else if (d1 == d2 || d2 == d3) combination = new Combination(2, d2, 0);
        else if (d3 == d4 || d4 == d5) combination = new Combination(2, d4, 0);
        else combination = new Combination(1, d5, 0);
        return Combination.compareToBase(combination);
    }


    @Value
    private static class Combination implements Comparable<Combination> {
        int l1;
        int l2;
        int l3;
        static List<Combination> baseCombinations = Arrays.asList(
                new Combination(1, 6, 0), new Combination(2, 4, 0), new Combination(3, 4, 2),
                new Combination(4, 3, 0), new Combination(5, 3, 0), new Combination(6, 4, 0));

        static int compareToBase(Combination combination) {
            int roll = new Random().nextInt(6);
            return combination.compareTo(baseCombinations.get(roll));
        }

        @Override
        public int compareTo(Combination o) {
            return (l1 != o.l1) ? Integer.compare(l1, o.l1) :
                    (l2 != o.l2) ? Integer.compare(l2, o.l2) : Integer.compare(l3, o.l3);
        }
    }
}