package game.d6shooters.actions;

import game.d6shooters.Main;
import game.d6shooters.bot.Bot;
import game.d6shooters.bot.DataBase;
import game.d6shooters.bot.SendMessageTemplate;
import game.d6shooters.game.Dice;
import game.d6shooters.game.DicesCup;
import game.d6shooters.game.Squad;
import game.d6shooters.game.SquadState;
import game.d6shooters.mocks.MockMessage;
import game.d6shooters.road.Place;
import game.d6shooters.road.RoadMap;
import game.d6shooters.source.Button;
import game.d6shooters.source.Text;
import game.d6shooters.users.User;
import game.d6shooters.users.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ActionDice1Test {
    User user = new User(0, "name");
    ActionDice1 action = new ActionDice1();
    DicesCup dicesCup = new DicesCup();
    List<Dice> diceList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        Main.actionManager = Mockito.mock(ActionManager.class);
        Main.bot = Mockito.mock(Bot.class);
        action.template = Mockito.mock(SendMessageTemplate.class);
        user.setDicesCup(dicesCup);
    }

    @Test
    void actionDice1ProcessMessageTest1() {
        user.getSquad().setPlace(new Place(user.getSquad(), RoadMap.Road.MAINROAD, 43));
        action.processMessage(user, new MockMessage(Button.BRANCH_ROAD.get()));
        Place expected = new Place(user.getSquad(), RoadMap.Road.LONROK, 0);
        assertAll(
                () -> assertEquals(expected, user.getSquad().getPlace())
        );
    }

    @Test
    void actionDice1ProcessMessageTest2() {
        user.getSquad().setPlace(new Place(user.getSquad(), RoadMap.Road.MAINROAD, 43));
        action.processMessage(user, new MockMessage(Button.MAIN_ROAD.get()));
        Place expected = new Place(user.getSquad(), RoadMap.Road.MAINROAD, 44);
        assertAll(
                () -> assertEquals(expected, user.getSquad().getPlace())
        );
    }

    @Test
    void actionDice1ProcessMessageTest3() {
        user.getSquad().setPlace(new Place(user.getSquad(), RoadMap.Road.MAINROAD, 67));
        action.processMessage(user, new MockMessage(Button.BRANCH_ROAD.get()));
        Place expected = new Place(user.getSquad(), RoadMap.Road.BAKSKIN, 0);
        assertAll(
                () -> assertEquals(expected, user.getSquad().getPlace())
        );
    }

    @Test
    void actionDice1ProcessMessageTest4() {
        user.getSquad().setPlace(new Place(user.getSquad(), RoadMap.Road.MAINROAD, 67));
        action.processMessage(user, new MockMessage(Button.MAIN_ROAD.get()));
        Place expected = new Place(user.getSquad(), RoadMap.Road.MAINROAD, 68);
        assertAll(
                () -> assertEquals(expected, user.getSquad().getPlace())
        );
    }

    @Test
    void actionDice1ProcessMessageTest5() {
        user.getSquad().setPlace(new Place(user.getSquad(), RoadMap.Road.MAINROAD, 67));
        action.processMessage(user, new MockMessage("Other"));
        Place expected = new Place(user.getSquad(), RoadMap.Road.MAINROAD, 67);
        assertAll(
                () -> assertEquals(expected, user.getSquad().getPlace())
        );
    }

    @Test
    void executeSpecialPlacesTest1() {
        user.getSquad().setPlace(new Place(user.getSquad(), RoadMap.Road.MAINROAD, 79));
        action.executeSpecialPlaces(user);
        assertAll(
                () -> assertEquals(SquadState.ENDGAME, user.getSquad().getSquadState())
        );
    }

    @Test
    void executeSpecialPlacesTest2() {
        user.getSquad().setPlace(new Place(user.getSquad(), RoadMap.Road.MAINROAD, 39));
        action.executeSpecialPlaces(user);
        assertAll(
                () -> assertEquals(SquadState.EVENT, user.getSquad().getSquadState())
        );
    }

    @Test
    void executeSpecialPlacesTest3() {
        user.getSquad().setPlace(new Place(user.getSquad(), RoadMap.Road.MAINROAD, 14));
        action.executeSpecialPlaces(user);
        assertAll(
                () -> assertEquals(SquadState.TOWN, user.getSquad().getSquadState()),
                () -> assertEquals(0, user.getSquad().getResource(Squad.PATHFINDING))
        );
    }

    @Test
    void convertDice1ToPathfindingTest1() {
        diceList = new ArrayList<>() {{
            add(new Dice(Dice.DiceType.WHITE, 1));
            add(new Dice(Dice.DiceType.WHITE, 1));
            add(new Dice(Dice.DiceType.RED, 1));

            add(new Dice(Dice.DiceType.WHITE, 2));
            add(new Dice(Dice.DiceType.RED, 3));
        }};
        dicesCup.setDiceList(diceList);
        user.getSquad().setResource(Squad.COMPASS, 0);
        user.getSquad().setResource(Squad.DAY_PATH, 0);
        action.convertDice1ToPathfinding(user);
        assertAll(
                () -> assertEquals(0, user.getDicesCup().getCountActiveDiceCurrentValue(1)),
                () -> assertEquals(3, user.getSquad().getResource(Squad.PATHFINDING))
        );

    }

    @Test
    void convertDice1ToPathfindingTest2() {
        diceList = new ArrayList<>() {{
            add(new Dice(Dice.DiceType.WHITE, 1));
            add(new Dice(Dice.DiceType.WHITE, 1));
            add(new Dice(Dice.DiceType.RED, 1));

            add(new Dice(Dice.DiceType.WHITE, 2));
            add(new Dice(Dice.DiceType.RED, 3));
        }};
        dicesCup.setDiceList(diceList);
        user.getSquad().setResource(Squad.COMPASS, 1);
        user.getSquad().setResource(Squad.DAY_PATH, 0);
        action.convertDice1ToPathfinding(user);
        assertAll(
                () -> assertEquals(0, user.getDicesCup().getCountActiveDiceCurrentValue(1)),
                () -> assertEquals(4, user.getSquad().getResource(Squad.PATHFINDING))
        );
    }



}