package game.d6shooters.actions;

import game.d6shooters.game.SquadState;
import game.d6shooters.mocks.MockActionManager;
import game.d6shooters.mocks.MockBot;
import game.d6shooters.mocks.MockMessage;
import game.d6shooters.mocks.MockTemplate;
import game.d6shooters.users.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActionEventTest {
    User user = new User(0, "name");
    MockBot mockBot = new MockBot();
    MockTemplate mockTemplate = new MockTemplate();
    MockActionManager mockActionManager = new MockActionManager(user, mockBot);
    ActionEvent action = new ActionEvent(mockBot);

    @BeforeEach
    void setUp() {
        action.template = mockTemplate;
        user.setActionManager(mockActionManager);
        user.getSquad().setAmmo(3);
        user.getSquad().setShooters(10);
    }


    @Test
    void modifyTest1() {
        action.modify(user, 1, 2, 3, -4, 5, 10);
        assertAll(
                () -> assertEquals(4, user.getSquad().getAmmo()),
                () -> assertEquals(8, user.getSquad().getFood()),
                () -> assertEquals(6, user.getSquad().getGold()),
                () -> assertEquals(6, user.getSquad().getShooters()),
                () -> assertEquals(5, user.getSquad().getPathfinding()),
                () -> assertEquals(10, user.getSquad().getPeriod())
        );
    }

    @Test
    void modifyTest2() {
        action.modify(user, -1, -2, -3, -1, 10, 100);
        assertAll(
                () -> assertEquals(2, user.getSquad().getAmmo()),
                () -> assertEquals(4, user.getSquad().getFood()),
                () -> assertEquals(0, user.getSquad().getGold()),
                () -> assertEquals(9, user.getSquad().getShooters()),
                () -> assertEquals(10, user.getSquad().getPathfinding()),
                () -> assertEquals(100, user.getSquad().getPeriod())
        );
    }

    @Test
    void modifyTest3() {
        action.modify(user, -6, -2, -3, -1, 10, 100);
        assertAll(
                () -> assertEquals(3, user.getSquad().getAmmo()),
                () -> assertEquals(4, user.getSquad().getFood()),
                () -> assertEquals(0, user.getSquad().getGold()),
                () -> assertEquals(0, user.getSquad().getShooters()),
                () -> assertEquals(10, user.getSquad().getPathfinding()),
                () -> assertEquals(100, user.getSquad().getPeriod())
        );
    }

    @Test
    void modifyTest4() {
        action.modify(user, -1, -10, -3, -1, 10, 100);
        assertAll(
                () -> assertEquals(2, user.getSquad().getAmmo()),
                () -> assertEquals(6, user.getSquad().getFood()),
                () -> assertEquals(0, user.getSquad().getGold()),
                () -> assertEquals(0, user.getSquad().getShooters()),
                () -> assertEquals(10, user.getSquad().getPathfinding()),
                () -> assertEquals(100, user.getSquad().getPeriod())
        );
    }

    @Test
    void modifyTest5() {
        action.modify(user, -1, -2, -10, -1, 10, 100);
        assertAll(
                () -> assertEquals(2, user.getSquad().getAmmo()),
                () -> assertEquals(4, user.getSquad().getFood()),
                () -> assertEquals(3, user.getSquad().getGold()),
                () -> assertEquals(0, user.getSquad().getShooters()),
                () -> assertEquals(10, user.getSquad().getPathfinding()),
                () -> assertEquals(100, user.getSquad().getPeriod())
        );
    }

    @Test
    void modifyTest6() {
        action.modify(user, 0, -1, 0, 0, 0, 1);
        assertAll(
                () -> assertEquals(3, user.getSquad().getAmmo()),
                () -> assertEquals(5, user.getSquad().getFood()),
                () -> assertEquals(3, user.getSquad().getGold()),
                () -> assertEquals(10, user.getSquad().getShooters()),
                () -> assertEquals(0, user.getSquad().getPathfinding()),
                () -> assertEquals(1, user.getSquad().getPeriod())
        );
    }

    @Test
    void modifyTest7() {
        action.modify(user, 0, 0, 0, 0, 0, 0);
        assertAll(
                () -> assertEquals(3, user.getSquad().getAmmo()),
                () -> assertEquals(6, user.getSquad().getFood()),
                () -> assertEquals(3, user.getSquad().getGold()),
                () -> assertEquals(10, user.getSquad().getShooters()),
                () -> assertEquals(0, user.getSquad().getPathfinding()),
                () -> assertEquals(0, user.getSquad().getPeriod())
        );
    }


    @Test
    void startEvent1Test() {
        action.startEvent1(user);
        assertEquals(3, user.getSquad().getPathfinding());
    }

    @Test
    void startEvent2Test1() {
        action.startEvent2(user);
        assertEquals(SquadState.EVENT2, user.getSquad().getSquadState());
    }

    @Test
    void startEvent2Test2() {
        user.getSquad().setAmmo(0);
        action.startEvent2(user);
        assertEquals(SquadState.MOVE, user.getSquad().getSquadState());
    }

    @Test
    void startEvent3Test1() {
        action.startEvent3(user);
        assertEquals(SquadState.EVENT3, user.getSquad().getSquadState());
    }

    @Test
    void startEvent3Test5() {
        System.out.println(user.getSquad());
        action.startEvent5(user);
        System.out.println(user.getSquad());
        assertAll(
                () -> assertEquals(SquadState.MOVE, user.getSquad().getSquadState()),
                () -> assertEquals(3, user.getSquad().getAmmo()),
                () -> assertEquals(5, user.getSquad().getFood()),
                () -> assertEquals(3, user.getSquad().getGold()),
                () -> assertEquals(10, user.getSquad().getShooters()),
                () -> assertEquals(0, user.getSquad().getPathfinding()),
                () -> assertEquals(1, user.getSquad().getPeriod())
        );
    }

    @Test
    void startEvent6Test1() {
        action.startEvent6(user);
        assertEquals(SquadState.EVENT6, user.getSquad().getSquadState());
    }

    @Test
    void toMoveActionTest() {
        action.toMoveAction(user);
        assertEquals(SquadState.MOVE, user.getSquad().getSquadState());
    }

    @Test
    void processMessageTest1() {
        user.getSquad().setSquadState(SquadState.EVENT2);
        MockMessage mockMessage = new MockMessage(ActionEvent.Action.EMPTY.get());
        action.processMessage(user, mockMessage);
        assertEquals(SquadState.EVENT2, user.getSquad().getSquadState());
    }

    @Test
    void processMessageTest2() {
        user.getSquad().setSquadState(SquadState.EVENT2);
        MockMessage mockMessage = new MockMessage("Something other text");
        action.processMessage(user, mockMessage);
        assertEquals(SquadState.EVENT2, user.getSquad().getSquadState());
    }

    @Test
    void processMessageTest3() {
        user.getSquad().setSquadState(SquadState.EVENT2);
        MockMessage mockMessage = new MockMessage(ActionEvent.Action.HUNT.get());
        action.processMessage(user, mockMessage);
        assertAll(
                () -> assertEquals(SquadState.MOVE, user.getSquad().getSquadState()),
                () -> assertEquals(2, user.getSquad().getAmmo()),
                () -> assertEquals(8, user.getSquad().getFood()),
                () -> assertEquals(3, user.getSquad().getGold()),
                () -> assertEquals(10, user.getSquad().getShooters()),
                () -> assertEquals(0, user.getSquad().getPathfinding()),
                () -> assertEquals(0, user.getSquad().getPeriod())
        );
    }

    @Test
    void processMessageTest4() {
        user.getSquad().setSquadState(SquadState.EVENT3);
        MockMessage mockMessage = new MockMessage(ActionEvent.Action.BUYFOOD.get());
        action.processMessage(user, mockMessage);
        assertAll(
                () -> assertEquals(SquadState.MOVE, user.getSquad().getSquadState()),
                () -> assertEquals(3, user.getSquad().getAmmo()),
                () -> assertEquals(8, user.getSquad().getFood()),
                () -> assertEquals(2, user.getSquad().getGold()),
                () -> assertEquals(10, user.getSquad().getShooters()),
                () -> assertEquals(0, user.getSquad().getPathfinding()),
                () -> assertEquals(0, user.getSquad().getPeriod())
        );
    }

    @Test
    void processMessageTest5() {
        user.getSquad().setSquadState(SquadState.EVENT3);
        MockMessage mockMessage = new MockMessage(ActionEvent.Action.BUYAMMO.get());
        action.processMessage(user, mockMessage);
        assertAll(
                () -> assertEquals(SquadState.MOVE, user.getSquad().getSquadState()),
                () -> assertEquals(5, user.getSquad().getAmmo()),
                () -> assertEquals(6, user.getSquad().getFood()),
                () -> assertEquals(2, user.getSquad().getGold()),
                () -> assertEquals(10, user.getSquad().getShooters()),
                () -> assertEquals(0, user.getSquad().getPathfinding()),
                () -> assertEquals(0, user.getSquad().getPeriod())
        );
    }

    @Test
    void processMessageTest6() {
        user.getSquad().setSquadState(SquadState.EVENT3);
        MockMessage mockMessage = new MockMessage(ActionEvent.Action.SELLFOOD.get());
        action.processMessage(user, mockMessage);
        assertAll(
                () -> assertEquals(SquadState.MOVE, user.getSquad().getSquadState()),
                () -> assertEquals(3, user.getSquad().getAmmo()),
                () -> assertEquals(4, user.getSquad().getFood()),
                () -> assertEquals(4, user.getSquad().getGold()),
                () -> assertEquals(10, user.getSquad().getShooters()),
                () -> assertEquals(0, user.getSquad().getPathfinding()),
                () -> assertEquals(0, user.getSquad().getPeriod())
        );
    }

    @Test
    void processMessageTest7() {
        user.getSquad().setSquadState(SquadState.EVENT3);
        MockMessage mockMessage = new MockMessage(ActionEvent.Action.SELLAMMO.get());
        action.processMessage(user, mockMessage);
        assertAll(
                () -> assertEquals(SquadState.MOVE, user.getSquad().getSquadState()),
                () -> assertEquals(1, user.getSquad().getAmmo()),
                () -> assertEquals(6, user.getSquad().getFood()),
                () -> assertEquals(4, user.getSquad().getGold()),
                () -> assertEquals(10, user.getSquad().getShooters()),
                () -> assertEquals(0, user.getSquad().getPathfinding()),
                () -> assertEquals(0, user.getSquad().getPeriod())
        );
    }

    @Test
    void processMessageTest8() {
        user.getSquad().setSquadState(SquadState.EVENT3);
        MockMessage mockMessage = new MockMessage(ActionEvent.Action.HUNT.get());
        action.processMessage(user, mockMessage);
        assertAll(
                () -> assertEquals(SquadState.MOVE, user.getSquad().getSquadState()),
                () -> assertEquals(3, user.getSquad().getAmmo()),
                () -> assertEquals(6, user.getSquad().getFood()),
                () -> assertEquals(3, user.getSquad().getGold()),
                () -> assertEquals(10, user.getSquad().getShooters()),
                () -> assertEquals(0, user.getSquad().getPathfinding()),
                () -> assertEquals(0, user.getSquad().getPeriod())
        );
    }

    @Test
    void processMessageTest9() {
        user.getSquad().setSquadState(SquadState.EVENT6);
        MockMessage mockMessage = new MockMessage(ActionEvent.Action.LOSE2FOOD.get());
        action.processMessage(user, mockMessage);
        assertAll(
                () -> assertEquals(SquadState.MOVE, user.getSquad().getSquadState()),
                () -> assertEquals(3, user.getSquad().getAmmo()),
                () -> assertEquals(4, user.getSquad().getFood()),
                () -> assertEquals(3, user.getSquad().getGold()),
                () -> assertEquals(10, user.getSquad().getShooters()),
                () -> assertEquals(0, user.getSquad().getPathfinding()),
                () -> assertEquals(0, user.getSquad().getPeriod())
        );
    }

    @Test
    void processMessageTest10() {
        user.getSquad().setSquadState(SquadState.EVENT6);
        MockMessage mockMessage = new MockMessage(ActionEvent.Action.LOSE2GUN.get());
        action.processMessage(user, mockMessage);
        assertAll(
                () -> assertEquals(SquadState.MOVE, user.getSquad().getSquadState()),
                () -> assertEquals(3, user.getSquad().getAmmo()),
                () -> assertEquals(6, user.getSquad().getFood()),
                () -> assertEquals(3, user.getSquad().getGold()),
                () -> assertEquals(8, user.getSquad().getShooters()),
                () -> assertEquals(0, user.getSquad().getPathfinding()),
                () -> assertEquals(0, user.getSquad().getPeriod())
        );
    }

    @Test
    void processMessageTest11() {
        user.getSquad().setSquadState(SquadState.EVENT6);
        MockMessage mockMessage = new MockMessage(ActionEvent.Action.LOSEFOODANDGOLD.get());
        action.processMessage(user, mockMessage);
        assertAll(
                () -> assertEquals(SquadState.MOVE, user.getSquad().getSquadState()),
                () -> assertEquals(3, user.getSquad().getAmmo()),
                () -> assertEquals(5, user.getSquad().getFood()),
                () -> assertEquals(2, user.getSquad().getGold()),
                () -> assertEquals(10, user.getSquad().getShooters()),
                () -> assertEquals(0, user.getSquad().getPathfinding()),
                () -> assertEquals(0, user.getSquad().getPeriod())
        );
    }

    @Test
    void processMessageTest12() {
        user.getSquad().setSquadState(SquadState.EVENT6);
        MockMessage mockMessage = new MockMessage(ActionEvent.Action.LOSE2GUN.get());
        action.processMessage(user, mockMessage);
        assertAll(
                () -> assertEquals(SquadState.MOVE, user.getSquad().getSquadState()),
                () -> assertEquals(3, user.getSquad().getAmmo()),
                () -> assertEquals(6, user.getSquad().getFood()),
                () -> assertEquals(3, user.getSquad().getGold()),
                () -> assertEquals(8, user.getSquad().getShooters()),
                () -> assertEquals(0, user.getSquad().getPathfinding()),
                () -> assertEquals(0, user.getSquad().getPeriod())
        );
    }

    @Test
    void processMessageTest13() {
        user.getSquad().setSquadState(SquadState.EVENT6);
        MockMessage mockMessage = new MockMessage(ActionEvent.Action.HUNT.get());
        action.processMessage(user, mockMessage);
        assertAll(
                () -> assertEquals(SquadState.EVENT6, user.getSquad().getSquadState()),
                () -> assertEquals(3, user.getSquad().getAmmo()),
                () -> assertEquals(6, user.getSquad().getFood()),
                () -> assertEquals(3, user.getSquad().getGold()),
                () -> assertEquals(10, user.getSquad().getShooters()),
                () -> assertEquals(0, user.getSquad().getPathfinding()),
                () -> assertEquals(0, user.getSquad().getPeriod())
        );
    }
}

