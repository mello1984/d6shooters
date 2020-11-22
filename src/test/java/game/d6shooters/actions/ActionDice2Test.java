package game.d6shooters.actions;

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
    ActionDice2 actionDice2 = new ActionDice2(mockBot);
    DicesCup dicesCup = new DicesCup();
    List<Dice> diceList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        actionDice2.template = mockTemplate;
        user.setDicesCup(dicesCup);
    }

    @Test
    void actionDice2Test1() {
        diceList = new ArrayList<>() {{
            add(new Dice(Dice.DiceType.WHITE, 2));
            add(new Dice(Dice.DiceType.WHITE, 2));
        }};
        dicesCup.setDiceList(diceList);
        actionDice2.action(user);
        assertAll(
                () -> assertEquals(7, user.getSquad().getFood()),
                () -> assertEquals(0, user.getDicesCup().getCountActiveDiceCurrentValue(2))
        );
    }

    @Test
    void actionDice2Test2() {
        diceList = new ArrayList<>() {{
            add(new Dice(Dice.DiceType.WHITE, 2));
        }};
        dicesCup.setDiceList(diceList);
        actionDice2.action(user);
        assertAll(
                () -> assertEquals(6, user.getSquad().getFood()),
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
        actionDice2.action(user);
        assertAll(
                () -> assertEquals(7, user.getSquad().getFood()),
                () -> assertEquals(0, user.getDicesCup().getCountActiveDiceCurrentValue(2))
        );
    }
}