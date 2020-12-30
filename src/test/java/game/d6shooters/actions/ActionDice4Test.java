package game.d6shooters.actions;

import game.d6shooters.Main;
import game.d6shooters.bot.Bot;
import game.d6shooters.bot.SendMessageTemplate;
import game.d6shooters.game.Dice;
import game.d6shooters.game.DicesCup;
import game.d6shooters.game.Squad;
import game.d6shooters.source.Button;
import game.d6shooters.users.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ActionDice4Test {
    User user = new User(0, "name");
    ActionDice4 action = new ActionDice4();

    @BeforeEach
    void setUp() {
        List<Dice> diceList = new ArrayList<>() {{
            add(new Dice(Dice.DiceType.WHITE, 4));
            add(new Dice(Dice.DiceType.WHITE, 4));
            add(new Dice(Dice.DiceType.WHITE, 4));
            add(new Dice(Dice.DiceType.WHITE, 4));
            add(new Dice(Dice.DiceType.WHITE, 5));
            add(new Dice(Dice.DiceType.WHITE, 5));
            add(new Dice(Dice.DiceType.WHITE, 6));
            add(new Dice(Dice.DiceType.WHITE, 6));
            add(new Dice(Dice.DiceType.WHITE, 6));
        }};

        Main.actionManager = Mockito.mock(ActionManager.class);
        Main.bot = Mockito.mock(Bot.class);
        action.template = Mockito.mock(SendMessageTemplate.class);

        DicesCup dicesCup = new DicesCup();
        dicesCup.setDiceList(diceList);
        user.setDicesCup(dicesCup);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void PathfindingTest1() {
        action.allocateDices(user, Button.PATHFINDING.get());
        assertAll(
                () -> assertEquals(1, user.getSquad().getResource(Squad.PATHFINDING)),
                () -> assertEquals(2, user.getDicesCup().getCountActiveDiceCurrentValue(4))
        );
    }

    @Test
    void PathfindingTest2() {
        action.allocateDices(user, Button.PATHFINDING.get());
        action.allocateDices(user, Button.PATHFINDING.get());
        assertAll(
                () -> assertEquals(2, user.getSquad().getResource(Squad.PATHFINDING)),
                () -> assertEquals(0, user.getDicesCup().getCountActiveDiceCurrentValue(4))
        );
    }

    @Test
    void RejectTest() {
        action.allocateDices(user, Button.REJECT.get());
        assertAll(() -> assertEquals(0, user.getDicesCup().getCountActiveDiceCurrentValue(4)));
    }

    @Test
    void Hide1Test() {
        action.allocateDices(user, Button.HIDE.get());
        assertAll(
                () -> assertEquals(3, user.getDicesCup().getCountActiveDiceCurrentValue(4)),
                () -> assertEquals(1, user.getDicesCup().getCountActiveDiceCurrentValue(6)),
                () -> assertEquals(1, user.getSquad().getResource(Squad.PERIOD))

        );
    }

    @Test
    void Hide2Test() {
        action.allocateDices(user, Button.HIDE.get());
        action.allocateDices(user, Button.HIDE.get());
        assertAll(
                () -> assertEquals(2, user.getDicesCup().getCountActiveDiceCurrentValue(4)),
                () -> assertEquals(0, user.getDicesCup().getCountActiveDiceCurrentValue(6)),
                () -> assertEquals(2, user.getSquad().getResource(Squad.PERIOD))
        );
    }

    @Test
    void ShelterTest1() {
        action.allocateDices(user, Button.SHELTER.get());
        assertAll(
                () -> assertEquals(3, user.getDicesCup().getCountActiveDiceCurrentValue(4)),
                () -> assertEquals(1, user.getDicesCup().getCountActiveDiceCurrentValue(5))
        );
    }

    @Test
    void ShelterTest2() {
        action.allocateDices(user, Button.SHELTER.get());
        action.allocateDices(user, Button.SHELTER.get());
        assertAll(
                () -> assertEquals(2, user.getDicesCup().getCountActiveDiceCurrentValue(4)),
                () -> assertEquals(0, user.getDicesCup().getCountActiveDiceCurrentValue(5))
        );
    }

    @Test
    void GunFightTest1() {
        action.allocateDices(user, Button.GUNFIGHT.get());
        assertAll(
                () -> assertEquals(1, user.getSquad().getResource(Squad.GUNFIGHT)),
                () -> assertEquals(3, user.getDicesCup().getCountActiveDiceCurrentValue(4))
        );
    }

    @Test
    void GunFightTest2() {
        action.allocateDices(user, Button.GUNFIGHT.get());
        action.allocateDices(user, Button.GUNFIGHT.get());
        assertAll(
                () -> assertEquals(2, user.getSquad().getResource(Squad.GUNFIGHT)),
                () -> assertEquals(2, user.getDicesCup().getCountActiveDiceCurrentValue(4))
        );
    }
}


