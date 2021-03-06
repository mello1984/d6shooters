package game.d6shooters.actions;

import game.d6shooters.game.Squad;
import game.d6shooters.mocks.MockBot;
import game.d6shooters.mocks.MockTemplate;
import game.d6shooters.game.Dice;
import game.d6shooters.game.DicesCup;
import game.d6shooters.users.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ActionDice2Test {
    User user = new User(0, "name");
    MockBot mockBot = new MockBot();
    MockTemplate mockTemplate = new MockTemplate();
    ActionDice2 action = new ActionDice2(mockBot);
    DicesCup dicesCup = new DicesCup();
    List<Dice> diceList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        action.template = mockTemplate;
        user.setDicesCup(dicesCup);
    }

    @Test
    void actionDice2Test1() {
        diceList = new ArrayList<>() {{
            add(new Dice(Dice.DiceType.WHITE, 2));
            add(new Dice(Dice.DiceType.WHITE, 2));
        }};
        dicesCup.setDiceList(diceList);
        action.action(user);
        assertAll(
                () -> assertEquals(7, user.getSquad().getResource(Squad.FOOD)),
                () -> assertEquals(0, user.getDicesCup().getCountActiveDiceCurrentValue(2))
        );
    }

    @Test
    void actionDice2Test2() {
        diceList = new ArrayList<>() {{
            add(new Dice(Dice.DiceType.WHITE, 2));
        }};
        dicesCup.setDiceList(diceList);
        action.action(user);
        assertAll(
                () -> assertEquals(6, user.getSquad().getResource(Squad.FOOD)),
                () -> assertEquals(0, user.getDicesCup().getCountActiveDiceCurrentValue(2))
        );
    }

    @Test
    void actionDice2Test3() {
        diceList = new ArrayList<>() {{
            add(new Dice(Dice.DiceType.WHITE, 2));
            add(new Dice(Dice.DiceType.RED, 2));
            add(new Dice(Dice.DiceType.WHITE, 3));
        }};
        dicesCup.setDiceList(diceList);
        action.action(user);
        assertAll(
                () -> assertEquals(7, user.getSquad().getResource(Squad.FOOD)),
                () -> assertEquals(0, user.getDicesCup().getCountActiveDiceCurrentValue(2))
        );
    }

    @Test
    void getFoundFoodTest1() {
        diceList = new ArrayList<>() {{
            add(new Dice(Dice.DiceType.WHITE, 2));
            add(new Dice(Dice.DiceType.WHITE, 2));
            add(new Dice(Dice.DiceType.RED, 2));

            add(new Dice(Dice.DiceType.WHITE, 1));
            add(new Dice(Dice.DiceType.RED, 3));
        }};
        dicesCup.setDiceList(diceList);
        user.getSquad().setResource(Squad.HUNTER,0);

        int result = action.getFoundFood(user);
        assertAll(
                () -> assertEquals(0, user.getDicesCup().getCountActiveDiceCurrentValue(2)),
                () -> assertEquals(1, result)
        );
    }

    @Test
    void getFoundFoodTest2() {
        diceList = new ArrayList<>() {{
            add(new Dice(Dice.DiceType.WHITE, 2));
            add(new Dice(Dice.DiceType.WHITE, 2));
            add(new Dice(Dice.DiceType.RED, 2));

            add(new Dice(Dice.DiceType.WHITE, 1));
            add(new Dice(Dice.DiceType.RED, 3));
        }};
        dicesCup.setDiceList(diceList);
        user.getSquad().setResource(Squad.HUNTER,1);

        int result = action.getFoundFood(user);
        assertAll(
                () -> assertEquals(0, user.getDicesCup().getCountActiveDiceCurrentValue(2)),
                () -> assertEquals(2, result)
        );
    } @Test
    void getFoundFoodTest3() {
        diceList = new ArrayList<>() {{
            add(new Dice(Dice.DiceType.WHITE, 2));
            add(new Dice(Dice.DiceType.WHITE, 2));
            add(new Dice(Dice.DiceType.WHITE, 2));
            add(new Dice(Dice.DiceType.RED, 2));
            add(new Dice(Dice.DiceType.RED, 2));

            add(new Dice(Dice.DiceType.WHITE, 1));
            add(new Dice(Dice.DiceType.RED, 3));
        }};
        dicesCup.setDiceList(diceList);
        user.getSquad().setResource(Squad.HUNTER,0);

        int result = action.getFoundFood(user);
        assertAll(
                () -> assertEquals(0, user.getDicesCup().getCountActiveDiceCurrentValue(2)),
                () -> assertEquals(2, result)
        );
    }

    @Test
    void getFoundFoodTest4() {
        diceList = new ArrayList<>() {{
            add(new Dice(Dice.DiceType.WHITE, 2));
            add(new Dice(Dice.DiceType.WHITE, 2));
            add(new Dice(Dice.DiceType.WHITE, 2));
            add(new Dice(Dice.DiceType.RED, 2));
            add(new Dice(Dice.DiceType.RED, 2));

            add(new Dice(Dice.DiceType.WHITE, 1));
            add(new Dice(Dice.DiceType.RED, 3));
        }};
        dicesCup.setDiceList(diceList);
        user.getSquad().setResource(Squad.HUNTER,1);

        int result = action.getFoundFood(user);
        assertAll(
                () -> assertEquals(0, user.getDicesCup().getCountActiveDiceCurrentValue(2)),
                () -> assertEquals(3, result)
        );
    }
}