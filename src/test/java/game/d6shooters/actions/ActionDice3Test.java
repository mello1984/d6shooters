package game.d6shooters.actions;

import game.d6shooters.mocks.MockActionManager;
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

class ActionDice3Test {
    User user = new User(0, "name");
    MockBot mockBot = new MockBot();
    MockTemplate mockTemplate = new MockTemplate();
    MockActionManager mockActionManager = new MockActionManager(user, mockBot);

    ActionDice3 action = new ActionDice3(mockBot);
    DicesCup dicesCup = new DicesCup();
    List<Dice> diceList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        action.template = mockTemplate;
        user.setDicesCup(dicesCup);
        user.setActionManager(mockActionManager);
    }

    @Test
    void actionDice3Test1() {
        diceList = new ArrayList<>() {{
            add(new Dice(Dice.DiceType.WHITE, 2));
            add(new Dice(Dice.DiceType.WHITE, 3));
        }};
        dicesCup.setDiceList(diceList);
        action.action(user);
        assertAll(
                () -> assertEquals(3, user.getSquad().getGold()),
                () -> assertEquals(0, user.getDicesCup().getCountActiveDiceCurrentValue(3))
        );
    }

    @Test
    void actionDice3Test2() {
        diceList = new ArrayList<>() {{
            add(new Dice(Dice.DiceType.WHITE, 2));
            add(new Dice(Dice.DiceType.WHITE, 3));
            add(new Dice(Dice.DiceType.WHITE, 3));
            add(new Dice(Dice.DiceType.RED, 3));
        }};
        dicesCup.setDiceList(diceList);
        action.action(user);
        assertAll(
                () -> assertEquals(4, user.getSquad().getGold()),
                () -> assertEquals(0, user.getDicesCup().getCountActiveDiceCurrentValue(3))
        );
    }

    @Test
    void actionDice3Test3() {
        diceList = new ArrayList<>() {{
            add(new Dice(Dice.DiceType.WHITE, 2));
            add(new Dice(Dice.DiceType.WHITE, 3));
            add(new Dice(Dice.DiceType.WHITE, 3));
            add(new Dice(Dice.DiceType.RED, 3));
            add(new Dice(Dice.DiceType.RED, 5));
            add(new Dice(Dice.DiceType.RED, 3));
        }};
        dicesCup.setDiceList(diceList);
        action.action(user);
        assertAll(
                () -> assertEquals(4, user.getSquad().getGold()),
                () -> assertEquals(0, user.getDicesCup().getCountActiveDiceCurrentValue(3))
        );
    }


}