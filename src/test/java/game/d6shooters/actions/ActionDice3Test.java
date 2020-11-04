package game.d6shooters.actions;

import game.d6shooters.Dice;
import game.d6shooters.DicesCup;
import game.d6shooters.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;


class ActionDice3Test {
    Game game;
    Action action;

    @BeforeEach
    void setUp() {
        game = new Game();
        action = new ActionDice3();
    }

    @Test
    void actionTest1() {
        DicesCup dicesCup = new DicesCup(new ArrayList<>(Arrays.asList(
                new Dice(Dice.DiceType.WHITE, 3),
                new Dice(Dice.DiceType.WHITE, 3),
                new Dice(Dice.DiceType.WHITE, 3))));

        int baseValue = game.squad.getGold();
        action.action(game.squad, dicesCup);
        int testResult = game.squad.getGold();
        assertEquals(baseValue + 1, testResult);
    }

    @Test
    void actionTest2() {
        DicesCup dicesCup = new DicesCup(new ArrayList<>(Arrays.asList(
                new Dice(Dice.DiceType.WHITE, 1),
                new Dice(Dice.DiceType.WHITE, 2),
                new Dice(Dice.DiceType.WHITE, 3),
                new Dice(Dice.DiceType.WHITE, 3))));

        int baseValue = game.squad.getGold();
        action.action(game.squad, dicesCup);
        int testResult = game.squad.getGold();
        assertEquals(baseValue, testResult);
    }

    @Test
    void actionTest3() {
        DicesCup dicesCup = new DicesCup(new ArrayList<>(Arrays.asList(
                new Dice(Dice.DiceType.WHITE, 3),
                new Dice(Dice.DiceType.WHITE, 3),
                new Dice(Dice.DiceType.RED, 3),
                new Dice(Dice.DiceType.WHITE, 3))));

        int baseValue = game.squad.getGold();
        action.action(game.squad, dicesCup);
        int testResult = game.squad.getGold();
        assertEquals(baseValue + 1, testResult);
    }

}