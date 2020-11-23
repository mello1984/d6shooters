package game.d6shooters.actions;

import game.d6shooters.game.Dice;
import game.d6shooters.game.DicesCup;
import game.d6shooters.users.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ActionDice4Test {
    User user = new User(0, "name");
    ActionDice4 action = new ActionDice4(null);

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

        DicesCup dicesCup = new DicesCup();
        dicesCup.setDiceList(diceList);
        user.setDicesCup(dicesCup);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void PathfindingTest1() {
        action.allocateDices(user, ActionDice4.PATHFINDING);
        assertAll(
                () -> assertEquals(1, user.getSquad().getPathfinding()),
                () -> assertEquals(2, user.getDicesCup().getCountActiveDiceCurrentValue(4))
        );
    }

    @Test
    void PathfindingTest2() {
        action.allocateDices(user, ActionDice4.PATHFINDING);
        action.allocateDices(user, ActionDice4.PATHFINDING);
        assertAll(
                () -> assertEquals(2, user.getSquad().getPathfinding()),
                () -> assertEquals(0, user.getDicesCup().getCountActiveDiceCurrentValue(4))
        );
    }

    @Test
    void RejectTest() {
        action.allocateDices(user, ActionDice4.REJECT);
        assertAll(() -> assertEquals(0, user.getDicesCup().getCountActiveDiceCurrentValue(4)));
    }

    @Test
    void Hide1Test() {
        action.allocateDices(user, ActionDice4.HIDE);
        assertAll(
                () -> assertEquals(3, user.getDicesCup().getCountActiveDiceCurrentValue(4)),
                () -> assertEquals(1, user.getDicesCup().getCountActiveDiceCurrentValue(6)),
                () -> assertEquals(1, user.getSquad().getPeriod())

        );
    }

    @Test
    void Hide2Test() {
        action.allocateDices(user, ActionDice4.HIDE);
        action.allocateDices(user, ActionDice4.HIDE);
        assertAll(
                () -> assertEquals(2, user.getDicesCup().getCountActiveDiceCurrentValue(4)),
                () -> assertEquals(0, user.getDicesCup().getCountActiveDiceCurrentValue(6)),
                () -> assertEquals(2, user.getSquad().getPeriod())
        );
    }

    @Test
    void ShelterTest1() {
        action.allocateDices(user, ActionDice4.SHELTER);
        assertAll(
                () -> assertEquals(3, user.getDicesCup().getCountActiveDiceCurrentValue(4)),
                () -> assertEquals(1, user.getDicesCup().getCountActiveDiceCurrentValue(5))
        );
    }

    @Test
    void ShelterTest2() {
        action.allocateDices(user, ActionDice4.SHELTER);
        action.allocateDices(user, ActionDice4.SHELTER);
        assertAll(
                () -> assertEquals(2, user.getDicesCup().getCountActiveDiceCurrentValue(4)),
                () -> assertEquals(0, user.getDicesCup().getCountActiveDiceCurrentValue(5))
        );
    }

    @Test
    void GunFightTest1() {
        action.allocateDices(user, ActionDice4.GUNFIGHT);
        assertAll(
                () -> assertEquals(1, user.getSquad().getGunfight()),
                () -> assertEquals(3, user.getDicesCup().getCountActiveDiceCurrentValue(4))
        );
    }

    @Test
    void GunFightTest2() {
        action.allocateDices(user, ActionDice4.GUNFIGHT);
        action.allocateDices(user, ActionDice4.GUNFIGHT);
        assertAll(
                () -> assertEquals(2, user.getSquad().getGunfight()),
                () -> assertEquals(2, user.getDicesCup().getCountActiveDiceCurrentValue(4))
        );
    }
}


