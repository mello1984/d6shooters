package game.d6shooters.actions;

import game.d6shooters.game.Dice;
import game.d6shooters.game.DicesCup;
import game.d6shooters.game.SquadState;
import game.d6shooters.mocks.MockActionManager;
import game.d6shooters.mocks.MockBot;
import game.d6shooters.mocks.MockMessage;
import game.d6shooters.mocks.MockTemplate;
import game.d6shooters.road.Place;
import game.d6shooters.road.RoadMap;
import game.d6shooters.users.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ActionDice1Test {
    User user = new User(0, "name");
    MockBot mockBot = new MockBot();
    MockTemplate mockTemplate = new MockTemplate();
    MockActionManager mockActionManager = new MockActionManager(user, mockBot);

    ActionDice1 action = new ActionDice1(mockBot);
    DicesCup dicesCup = new DicesCup();
    List<Dice> diceList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        diceList = new ArrayList<>() {{
            add(new Dice(Dice.DiceType.WHITE, 2));
        }};
        dicesCup.setDiceList(diceList);

        action.template = mockTemplate;
        user.setDicesCup(dicesCup);
        user.setActionManager(mockActionManager);
    }

    @Test
    void actionDice1ProcessMessageTest1() {
        user.getSquad().setPlace(new Place(RoadMap.Road.MAINROAD, 43));
        action.processMessage(user, new MockMessage(ActionDice1.BRANCH));
        Place expected = new Place(RoadMap.Road.LONROK, 0);
        assertAll(
                () -> assertEquals(expected, user.getSquad().getPlace())
        );
    }

    @Test
    void actionDice1ProcessMessageTest2() {
        user.getSquad().setPlace(new Place(RoadMap.Road.MAINROAD, 43));
        action.processMessage(user, new MockMessage(ActionDice1.MAIN));
        Place expected = new Place(RoadMap.Road.MAINROAD, 44);
        assertAll(
                () -> assertEquals(expected, user.getSquad().getPlace())
        );
    }

    @Test
    void actionDice1ProcessMessageTest3() {
        user.getSquad().setPlace(new Place(RoadMap.Road.MAINROAD, 67));
        action.processMessage(user, new MockMessage(ActionDice1.BRANCH));
        Place expected = new Place(RoadMap.Road.BAKSKIN, 0);
        assertAll(
                () -> assertEquals(expected, user.getSquad().getPlace())
        );
    }

    @Test
    void actionDice1ProcessMessageTest4() {
        user.getSquad().setPlace(new Place(RoadMap.Road.MAINROAD, 67));
        action.processMessage(user, new MockMessage(ActionDice1.MAIN));
        Place expected = new Place(RoadMap.Road.MAINROAD, 68);
        assertAll(
                () -> assertEquals(expected, user.getSquad().getPlace())
        );
    }

    @Test
    void actionDice1ProcessMessageTest5() {
        user.getSquad().setPlace(new Place(RoadMap.Road.MAINROAD, 67));
        action.processMessage(user, new MockMessage("Other"));
        Place expected = new Place(RoadMap.Road.MAINROAD, 67);
        assertAll(
                () -> assertEquals(expected, user.getSquad().getPlace())
        );
    }

    @Test
    void executeSpecialPlacesTest1() {
        user.getSquad().setPlace(new Place(RoadMap.Road.MAINROAD, 79));
        action.executeSpecialPlaces(user.getSquad());
        assertAll(
                () -> assertEquals(SquadState.ENDGAME, user.getSquad().getSquadState())
        );
    }

    @Test
    void executeSpecialPlacesTest2() {
        user.getSquad().setPlace(new Place(RoadMap.Road.MAINROAD, 39));
        action.executeSpecialPlaces(user.getSquad());
        assertAll(
                () -> assertEquals(SquadState.EVENT, user.getSquad().getSquadState())
        );
    }

    @Test
    void executeSpecialPlacesTest3() {
        user.getSquad().setPlace(new Place(RoadMap.Road.MAINROAD, 14));
        action.executeSpecialPlaces(user.getSquad());
        assertAll(
                () -> assertEquals(SquadState.TOWN, user.getSquad().getSquadState()),
                () -> assertEquals(0, user.getSquad().getPathfinding())
        );
    }

}