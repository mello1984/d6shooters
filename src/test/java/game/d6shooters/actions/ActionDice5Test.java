package game.d6shooters.actions;

import game.d6shooters.Main;
import game.d6shooters.bot.Bot;
import game.d6shooters.bot.SendMessageTemplate;
import game.d6shooters.source.Button;
import game.d6shooters.game.Dice;
import game.d6shooters.game.DicesCup;
import game.d6shooters.game.Squad;
import game.d6shooters.mocks.MockMessage;
import game.d6shooters.users.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ActionDice5Test {
    User user = new User(0, "name");
    ActionDice5 action = new ActionDice5();
    DicesCup dicesCup = new DicesCup();
    List<Dice> diceList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        diceList = new ArrayList<>() {{
            add(new Dice(Dice.DiceType.WHITE, 2));
            add(new Dice(Dice.DiceType.WHITE, 3));
            add(new Dice(Dice.DiceType.WHITE, 5));
            add(new Dice(Dice.DiceType.WHITE, 5));
        }};
        dicesCup.setDiceList(diceList);

        Main.actionManager = Mockito.mock(ActionManager.class);
        Main.bot = Mockito.mock(Bot.class);
        action.template = Mockito.mock(SendMessageTemplate.class);
        user.setDicesCup(dicesCup);
    }

    @Test
    void actionDice5ProcessMessageTest1() {
        action.processMessage(user, new MockMessage(Button.LOSE2FOOD.get()));
        assertAll(
                () -> assertEquals(4, user.getSquad().getResource(Squad.FOOD)),
                () -> assertEquals(12, user.getSquad().getResource(Squad.SHOOTER)),
                () -> assertEquals(1, user.getDicesCup().getCountActiveDiceCurrentValue(5))
        );
            }

    @Test
    void actionDice5ProcessMessageTest2() {
        action.processMessage(user, new MockMessage(Button.LOSE2FOOD.get()));
        action.processMessage(user, new MockMessage(Button.LOSE2FOOD.get()));
        assertAll(
                () -> assertEquals(2, user.getSquad().getResource(Squad.FOOD)),
                () -> assertEquals(12, user.getSquad().getResource(Squad.SHOOTER)),
                () -> assertEquals(0, user.getDicesCup().getCountActiveDiceCurrentValue(5))
        );
    }

    @Test
    void actionDice5ProcessMessageTest3() {
        action.processMessage(user, new MockMessage(Button.LOSE1GUNFIGHTER.get()));
        assertAll(
                () -> assertEquals(6, user.getSquad().getResource(Squad.FOOD)),
                () -> assertEquals(11, user.getSquad().getResource(Squad.SHOOTER)),
                () -> assertEquals(1, user.getDicesCup().getCountActiveDiceCurrentValue(5))
        );
    }

    @Test
    void actionDice5ProcessMessageTest4() {
        action.processMessage(user, new MockMessage(Button.LOSE1GUNFIGHTER.get()));
        action.processMessage(user, new MockMessage(Button.LOSE1GUNFIGHTER.get()));
        assertAll(
                () -> assertEquals(6, user.getSquad().getResource(Squad.FOOD)),
                () -> assertEquals(10, user.getSquad().getResource(Squad.SHOOTER)),
                () -> assertEquals(0, user.getDicesCup().getCountActiveDiceCurrentValue(5))
        );
    }

    @Test
    void actionDice5ProcessMessageTest5() {
        action.processMessage(user, new MockMessage(Button.LOSE2FOOD.get()));
        action.processMessage(user, new MockMessage(Button.LOSE1GUNFIGHTER.get()));
        assertAll(
                () -> assertEquals(4, user.getSquad().getResource(Squad.FOOD)),
                () -> assertEquals(11, user.getSquad().getResource(Squad.SHOOTER)),
                () -> assertEquals(0, user.getDicesCup().getCountActiveDiceCurrentValue(5))
        );
    }

    @Test
    void actionDice5ProcessMessageTest6() {
        action.processMessage(user, new MockMessage("hello world"));
        assertAll(
                () -> assertEquals(6, user.getSquad().getResource(Squad.FOOD)),
                () -> assertEquals(12, user.getSquad().getResource(Squad.SHOOTER)),
                () -> assertEquals(2, user.getDicesCup().getCountActiveDiceCurrentValue(5))
        );
    }


}