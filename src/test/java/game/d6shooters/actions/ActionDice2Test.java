package game.d6shooters.actions;

import game.d6shooters.game.Dice;
import game.d6shooters.game.DicesCup;
import game.d6shooters.game.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;


class ActionDice2Test {
    Game game;
    Action action;

    @BeforeEach
    void setUp() {
        game = new Game();
        action = new ActionDice2();
    }

    @Test
    void actionTest1() {
        DicesCup dicesCup = new DicesCup(new ArrayList<>(Arrays.asList(
                new Dice(Dice.DiceType.WHITE, 2),
                new Dice(Dice.DiceType.WHITE, 2),
                new Dice(Dice.DiceType.WHITE, 3))));

        int baseValue = game.squad.getFood();
        action.action(game.squad, dicesCup);
        int testResult = game.squad.getFood();
        assertEquals(baseValue + 1, testResult);
    }

    @Test
    void actionTest2() {
        DicesCup dicesCup = new DicesCup(new ArrayList<>(Arrays.asList(
                new Dice(Dice.DiceType.WHITE, 2),
                new Dice(Dice.DiceType.WHITE, 2),
                new Dice(Dice.DiceType.WHITE, 2),
                new Dice(Dice.DiceType.WHITE, 2))));

        int baseValue = game.squad.getFood();
        action.action(game.squad, dicesCup);
        int testResult = game.squad.getFood();
        assertEquals(baseValue + 2, testResult);
    }

    @Test
    void actionTest3() {
        DicesCup dicesCup = new DicesCup(new ArrayList<>(Arrays.asList(
                new Dice(Dice.DiceType.WHITE, 1),
                new Dice(Dice.DiceType.WHITE, 2),
                new Dice(Dice.DiceType.RED, 3),
                new Dice(Dice.DiceType.WHITE, 3))));

        int baseValue = game.squad.getFood();
        action.action(game.squad, dicesCup);
        int testResult = game.squad.getFood();
        assertEquals(baseValue, testResult);
    }

}