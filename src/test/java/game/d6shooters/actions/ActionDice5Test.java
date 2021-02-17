package game.d6shooters.actions;

import game.d6shooters.Main;
import game.d6shooters.bot.Bot;
import game.d6shooters.bot.SendMessageTemplate;
import game.d6shooters.game.SquadState;
import game.d6shooters.source.Button;
import game.d6shooters.game.Dice;
import game.d6shooters.game.DicesCup;
import game.d6shooters.game.Squad;
import game.d6shooters.users.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ActionDice5Test {
    User user = new User(0, "name");
    ActionDice5 action = new ActionDice5();
    DicesCup dicesCup = new DicesCup();
    List<Dice> diceList = new ArrayList<>();
    Message message1;
    Message message2;

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
        message1 = Mockito.mock(Message.class);
        message2 = Mockito.mock(Message.class);
        user.setDicesCup(dicesCup);
    }

    @Test
    void actionDice5ProcessMessageTest1() {
        Mockito.when(message1.getText()).thenReturn(Button.LOSE2FOOD.get());
        action.processMessage(user, message1);
        assertAll(
                () -> assertEquals(4, user.getSquad().getResource(Squad.FOOD)),
                () -> assertEquals(12, user.getSquad().getResource(Squad.SHOOTER)),
                () -> assertEquals(1, user.getDicesCup().getCountActiveDiceCurrentValue(5))
        );
    }

    @Test
    void actionDice5ProcessMessageTest2() {
        Mockito.when(message1.getText()).thenReturn(Button.LOSE2FOOD.get());
        action.processMessage(user, message1);
        action.processMessage(user, message1);
        assertAll(
                () -> assertEquals(2, user.getSquad().getResource(Squad.FOOD)),
                () -> assertEquals(12, user.getSquad().getResource(Squad.SHOOTER)),
                () -> assertEquals(0, user.getDicesCup().getCountActiveDiceCurrentValue(5))
        );
    }

    @Test
    void actionDice5ProcessMessageTest3() {
        Mockito.when(message1.getText()).thenReturn(Button.LOSE1GUNFIGHTER.get());
        action.processMessage(user, message1);
        assertAll(
                () -> assertEquals(6, user.getSquad().getResource(Squad.FOOD)),
                () -> assertEquals(11, user.getSquad().getResource(Squad.SHOOTER)),
                () -> assertEquals(1, user.getDicesCup().getCountActiveDiceCurrentValue(5))
        );
    }

    @Test
    void actionDice5ProcessMessageTest4() {
        Mockito.when(message1.getText()).thenReturn(Button.LOSE1GUNFIGHTER.get());
        action.processMessage(user, message1);
        action.processMessage(user, message1);
        assertAll(
                () -> assertEquals(6, user.getSquad().getResource(Squad.FOOD)),
                () -> assertEquals(10, user.getSquad().getResource(Squad.SHOOTER)),
                () -> assertEquals(0, user.getDicesCup().getCountActiveDiceCurrentValue(5))
        );
    }

    @Test
    void actionDice5ProcessMessageTest5() {
        Mockito.when(message1.getText()).thenReturn(Button.LOSE2FOOD.get());
        Mockito.when(message2.getText()).thenReturn(Button.LOSE1GUNFIGHTER.get());
        action.processMessage(user, message1);
        action.processMessage(user, message2);
        assertAll(
                () -> assertEquals(4, user.getSquad().getResource(Squad.FOOD)),
                () -> assertEquals(11, user.getSquad().getResource(Squad.SHOOTER)),
                () -> assertEquals(0, user.getDicesCup().getCountActiveDiceCurrentValue(5))
        );
    }

    @Test
    void actionDice5ProcessMessageTest6() {
        Mockito.when(message1.getText()).thenReturn("hello world");
        action.processMessage(user, message1);
        assertAll(
                () -> assertEquals(6, user.getSquad().getResource(Squad.FOOD)),
                () -> assertEquals(12, user.getSquad().getResource(Squad.SHOOTER)),
                () -> assertEquals(2, user.getDicesCup().getCountActiveDiceCurrentValue(5))
        );
    }

    @Test
    void actionDice5ProcessMessageTest7() {
        Mockito.when(message1.getText()).thenReturn(Button.LOSE_PILL.get());
        action.processMessage(user, message1);
        assertAll(
                () -> assertEquals(6, user.getSquad().getResource(Squad.FOOD)),
                () -> assertEquals(12, user.getSquad().getResource(Squad.SHOOTER)),
                () -> assertEquals(0, user.getDicesCup().getCountActiveDiceCurrentValue(5)),
                () -> assertFalse(user.getSquad().hasResource(Squad.PILL))
        );
    }

    @Test
    void actionDice5ProcessMessageTest8() {
        Mockito.when(message1.getText()).thenReturn(Button.LOSE1GUNFIGHTER.get());
        user.getSquad().setResource(Squad.SHOOTER, 1);
        action.processMessage(user, message1);
        assertAll(
                () -> assertEquals(6, user.getSquad().getResource(Squad.FOOD)),
                () -> assertEquals(0, user.getSquad().getResource(Squad.SHOOTER)),
                () -> assertEquals(SquadState.ENDGAME, user.getSquad().getSquadState())
        );
    }

    @Test
    void actionDice5ActionTest1() {
        diceList = new ArrayList<>() {{
            add(new Dice(Dice.DiceType.WHITE, 2));
            add(new Dice(Dice.DiceType.WHITE, 3));
        }};
        dicesCup.setDiceList(diceList);
        action.action(user);
        assertAll(
                () -> assertEquals(6, user.getSquad().getResource(Squad.FOOD)),
                () -> assertEquals(12, user.getSquad().getResource(Squad.SHOOTER)),
                () -> assertEquals(SquadState.GUNFIGHT, user.getSquad().getSquadState())
        );
    }

    @Test
    void actionDice5NoHeatDamageTest1() {
        action.processNoHeatDamage(user, 2);
        user.getSquad().setSquadState(SquadState.CHECKHEAT);
        assertAll(
                () -> assertEquals(6, user.getSquad().getResource(Squad.FOOD)),
                () -> assertEquals(12, user.getSquad().getResource(Squad.SHOOTER)),
                () -> assertEquals(1, user.getDicesCup().getCountActiveDiceCurrentValue(5)),
                () -> assertEquals(SquadState.CHECKHEAT, user.getSquad().getSquadState())
        );
    }

    @Test
    void actionDice5getHeatButtonsTest1() {
        user.getSquad().setResource(Squad.SHOOTER, 10);
        user.getSquad().setResource(Squad.FOOD, 6);
        user.getSquad().setResource(Squad.PILL, 1);

        List<String> expectedButtons = new ArrayList<>(Arrays.asList(Button.LOSE1GUNFIGHTER.get(), Button.LOSE2FOOD.get(), Button.LOSE_PILL.get()));
        List<List<String>> expected = new ArrayList<>();
        expected.add(expectedButtons);
        List<List<String>> actual = action.getHeatDamageButtons(user);

        assertEquals(expected, actual);
    }

    @Test
    void actionDice5getHeatButtonsTest2() {
        user.getSquad().setResource(Squad.SHOOTER, 1);
        user.getSquad().setResource(Squad.FOOD, 6);
        user.getSquad().setResource(Squad.PILL, 1);

        List<String> expectedButtons = new ArrayList<>(Arrays.asList(Button.LOSE1GUNFIGHTER.get(), Button.LOSE2FOOD.get(), Button.LOSE_PILL.get()));
        List<List<String>> expected = new ArrayList<>();
        expected.add(expectedButtons);
        List<List<String>> actual = action.getHeatDamageButtons(user);

        assertEquals(expected, actual);
    }

    @Test
    void actionDice5getHeatButtonsTest3() {
        user.getSquad().setResource(Squad.SHOOTER, 10);
        user.getSquad().setResource(Squad.FOOD, 1);
        user.getSquad().setResource(Squad.PILL, 1);

        List<String> expectedButtons = new ArrayList<>(Arrays.asList(Button.LOSE1GUNFIGHTER.get(), Button.LOSE_PILL.get()));
        List<List<String>> expected = new ArrayList<>();
        expected.add(expectedButtons);
        List<List<String>> actual = action.getHeatDamageButtons(user);

        assertEquals(expected, actual);
    }

    @Test
    void actionDice5getHeatButtonsTest4() {
        user.getSquad().setResource(Squad.SHOOTER, 10);
        user.getSquad().setResource(Squad.FOOD, 6);
        user.getSquad().setResource(Squad.PILL, 0);

        List<String> expectedButtons = new ArrayList<>(Arrays.asList(Button.LOSE1GUNFIGHTER.get(), Button.LOSE2FOOD.get()));
        List<List<String>> expected = new ArrayList<>();
        expected.add(expectedButtons);
        List<List<String>> actual = action.getHeatDamageButtons(user);

        assertEquals(expected, actual);
    }

    @Test
    void actionDice5getHeatButtonsTest5() {
        user.getSquad().setResource(Squad.SHOOTER, 10);
        user.getSquad().setResource(Squad.FOOD, 1);
        user.getSquad().setResource(Squad.PILL, 0);

        List<String> expectedButtons = new ArrayList<>(Collections.singletonList(Button.LOSE1GUNFIGHTER.get()));
        List<List<String>> expected = new ArrayList<>();
        expected.add(expectedButtons);
        List<List<String>> actual = action.getHeatDamageButtons(user);

        assertEquals(expected, actual);
    }

    @Test
    void actionDice5getHeatButtonsTest6() {
        user.getSquad().setResource(Squad.SHOOTER, 1);
        user.getSquad().setResource(Squad.FOOD, 0);
        user.getSquad().setResource(Squad.PILL, 0);

        List<String> expectedButtons = new ArrayList<>(Collections.singletonList(Button.LOSE1GUNFIGHTER.get()));
        List<List<String>> expected = new ArrayList<>();
        expected.add(expectedButtons);
        List<List<String>> actual = action.getHeatDamageButtons(user);

        assertEquals(expected, actual);
    }
}