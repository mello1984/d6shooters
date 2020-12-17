package game.d6shooters.actions;

import game.d6shooters.source.Button;
import game.d6shooters.game.Squad;
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
        user.getSquad().setResource(Squad.AMMO,3);
        user.getSquad().setResource(Squad.SHOOTER,10);
    }


    @Test
    void modifyTest1() {
        action.modify(user, 1, 2, 3, -4, 5, 10);
        assertAll(
                () -> assertEquals(4, user.getSquad().getResource(Squad.AMMO)),
                () -> assertEquals(8, user.getSquad().getResource(Squad.FOOD)),
                () -> assertEquals(6, user.getSquad().getResource(Squad.GOLD)),
                () -> assertEquals(6, user.getSquad().getResource(Squad.SHOOTER)),
                () -> assertEquals(5, user.getSquad().getResource(Squad.PATHFINDING)),
                () -> assertEquals(10, user.getSquad().getResource(Squad.PERIOD))
        );
    }

    @Test
    void modifyTest2() {
        action.modify(user, -1, -2, -3, -1, 10, 100);
        assertAll(
                () -> assertEquals(2, user.getSquad().getResource(Squad.AMMO)),
                () -> assertEquals(4, user.getSquad().getResource(Squad.FOOD)),
                () -> assertEquals(0, user.getSquad().getResource(Squad.GOLD)),
                () -> assertEquals(9, user.getSquad().getResource(Squad.SHOOTER)),
                () -> assertEquals(10, user.getSquad().getResource(Squad.PATHFINDING)),
                () -> assertEquals(100, user.getSquad().getResource(Squad.PERIOD))
        );
    }

    @Test
    void modifyTest3() {
        action.modify(user, -6, -2, -3, -1, 10, 100);
        assertAll(
                () -> assertEquals(3, user.getSquad().getResource(Squad.AMMO)),
                () -> assertEquals(4, user.getSquad().getResource(Squad.FOOD)),
                () -> assertEquals(0, user.getSquad().getResource(Squad.GOLD)),
                () -> assertEquals(0, user.getSquad().getResource(Squad.SHOOTER)),
                () -> assertEquals(10, user.getSquad().getResource(Squad.PATHFINDING)),
                () -> assertEquals(100, user.getSquad().getResource(Squad.PERIOD))
        );
    }

    @Test
    void modifyTest4() {
        action.modify(user, -1, -10, -3, -1, 10, 100);
        assertAll(
                () -> assertEquals(2, user.getSquad().getResource(Squad.AMMO)),
                () -> assertEquals(6, user.getSquad().getResource(Squad.FOOD)),
                () -> assertEquals(0, user.getSquad().getResource(Squad.GOLD)),
                () -> assertEquals(0, user.getSquad().getResource(Squad.SHOOTER)),
                () -> assertEquals(10, user.getSquad().getResource(Squad.PATHFINDING)),
                () -> assertEquals(100, user.getSquad().getResource(Squad.PERIOD))
        );
    }

    @Test
    void modifyTest5() {
        action.modify(user, -1, -2, -10, -1, 10, 100);
        assertAll(
                () -> assertEquals(2, user.getSquad().getResource(Squad.AMMO)),
                () -> assertEquals(4, user.getSquad().getResource(Squad.FOOD)),
                () -> assertEquals(3, user.getSquad().getResource(Squad.GOLD)),
                () -> assertEquals(0, user.getSquad().getResource(Squad.SHOOTER)),
                () -> assertEquals(10, user.getSquad().getResource(Squad.PATHFINDING)),
                () -> assertEquals(100, user.getSquad().getResource(Squad.PERIOD))
        );
    }

    @Test
    void modifyTest6() {
        action.modify(user, 0, -1, 0, 0, 0, 1);
        assertAll(
                () -> assertEquals(3, user.getSquad().getResource(Squad.AMMO)),
                () -> assertEquals(5, user.getSquad().getResource(Squad.FOOD)),
                () -> assertEquals(3, user.getSquad().getResource(Squad.GOLD)),
                () -> assertEquals(10, user.getSquad().getResource(Squad.SHOOTER)),
                () -> assertEquals(0, user.getSquad().getResource(Squad.PATHFINDING)),
                () -> assertEquals(1, user.getSquad().getResource(Squad.PERIOD))
        );
    }

    @Test
    void modifyTest7() {
        action.modify(user, 0, 0, 0, 0, 0, 0);
        assertAll(
                () -> assertEquals(3, user.getSquad().getResource(Squad.AMMO)),
                () -> assertEquals(6, user.getSquad().getResource(Squad.FOOD)),
                () -> assertEquals(3, user.getSquad().getResource(Squad.GOLD)),
                () -> assertEquals(10, user.getSquad().getResource(Squad.SHOOTER)),
                () -> assertEquals(0, user.getSquad().getResource(Squad.PATHFINDING)),
                () -> assertEquals(0, user.getSquad().getResource(Squad.PERIOD))
        );
    }


    @Test
    void startEvent1Test() {
        action.startEvent1(user);
        assertEquals(3, user.getSquad().getResource(Squad.PATHFINDING));
    }

    @Test
    void startEvent2Test1() {
        action.startEvent2(user);
        assertEquals(SquadState.EVENT2, user.getSquad().getSquadState());
    }

    @Test
    void startEvent2Test2() {
        user.getSquad().setResource(Squad.AMMO,0);
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
                () -> assertEquals(3, user.getSquad().getResource(Squad.AMMO)),
                () -> assertEquals(5, user.getSquad().getResource(Squad.FOOD)),
                () -> assertEquals(3, user.getSquad().getResource(Squad.GOLD)),
                () -> assertEquals(10, user.getSquad().getResource(Squad.SHOOTER)),
                () -> assertEquals(0, user.getSquad().getResource(Squad.PATHFINDING)),
                () -> assertEquals(1, user.getSquad().getResource(Squad.PERIOD))
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
        MockMessage mockMessage = new MockMessage(Button.EMPTY.get());
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
        MockMessage mockMessage = new MockMessage(Button.HUNT.get());
        action.processMessage(user, mockMessage);
        assertAll(
                () -> assertEquals(SquadState.MOVE, user.getSquad().getSquadState()),
                () -> assertEquals(2, user.getSquad().getResource(Squad.AMMO)),
                () -> assertEquals(8, user.getSquad().getResource(Squad.FOOD)),
                () -> assertEquals(3, user.getSquad().getResource(Squad.GOLD)),
                () -> assertEquals(10, user.getSquad().getResource(Squad.SHOOTER)),
                () -> assertEquals(0, user.getSquad().getResource(Squad.PATHFINDING)),
                () -> assertEquals(0, user.getSquad().getResource(Squad.PERIOD))
        );
    }

    @Test
    void processMessageTest4() {
        user.getSquad().setSquadState(SquadState.EVENT3);
        MockMessage mockMessage = new MockMessage(Button.BUYFOOD.get());
        action.processMessage(user, mockMessage);
        assertAll(
                () -> assertEquals(SquadState.MOVE, user.getSquad().getSquadState()),
                () -> assertEquals(3, user.getSquad().getResource(Squad.AMMO)),
                () -> assertEquals(8, user.getSquad().getResource(Squad.FOOD)),
                () -> assertEquals(2, user.getSquad().getResource(Squad.GOLD)),
                () -> assertEquals(10, user.getSquad().getResource(Squad.SHOOTER)),
                () -> assertEquals(0, user.getSquad().getResource(Squad.PATHFINDING)),
                () -> assertEquals(0, user.getSquad().getResource(Squad.PERIOD))
        );
    }

    @Test
    void processMessageTest5() {
        user.getSquad().setSquadState(SquadState.EVENT3);
        MockMessage mockMessage = new MockMessage(Button.BUYAMMO.get());
        action.processMessage(user, mockMessage);
        assertAll(
                () -> assertEquals(SquadState.MOVE, user.getSquad().getSquadState()),
                () -> assertEquals(5, user.getSquad().getResource(Squad.AMMO)),
                () -> assertEquals(6, user.getSquad().getResource(Squad.FOOD)),
                () -> assertEquals(2, user.getSquad().getResource(Squad.GOLD)),
                () -> assertEquals(10, user.getSquad().getResource(Squad.SHOOTER)),
                () -> assertEquals(0, user.getSquad().getResource(Squad.PATHFINDING)),
                () -> assertEquals(0, user.getSquad().getResource(Squad.PERIOD))
        );
    }

    @Test
    void processMessageTest6() {
        user.getSquad().setSquadState(SquadState.EVENT3);
        MockMessage mockMessage = new MockMessage(Button.SELLFOOD.get());
        action.processMessage(user, mockMessage);
        assertAll(
                () -> assertEquals(SquadState.MOVE, user.getSquad().getSquadState()),
                () -> assertEquals(3, user.getSquad().getResource(Squad.AMMO)),
                () -> assertEquals(4, user.getSquad().getResource(Squad.FOOD)),
                () -> assertEquals(4, user.getSquad().getResource(Squad.GOLD)),
                () -> assertEquals(10, user.getSquad().getResource(Squad.SHOOTER)),
                () -> assertEquals(0, user.getSquad().getResource(Squad.PATHFINDING)),
                () -> assertEquals(0, user.getSquad().getResource(Squad.PERIOD))
        );
    }

    @Test
    void processMessageTest7() {
        user.getSquad().setSquadState(SquadState.EVENT3);
        MockMessage mockMessage = new MockMessage(Button.SELLAMMO.get());
        action.processMessage(user, mockMessage);
        assertAll(
                () -> assertEquals(SquadState.MOVE, user.getSquad().getSquadState()),
                () -> assertEquals(1, user.getSquad().getResource(Squad.AMMO)),
                () -> assertEquals(6, user.getSquad().getResource(Squad.FOOD)),
                () -> assertEquals(4, user.getSquad().getResource(Squad.GOLD)),
                () -> assertEquals(10, user.getSquad().getResource(Squad.SHOOTER)),
                () -> assertEquals(0, user.getSquad().getResource(Squad.PATHFINDING)),
                () -> assertEquals(0, user.getSquad().getResource(Squad.PERIOD))
        );
    }

    @Test
    void processMessageTest8() {
        user.getSquad().setSquadState(SquadState.EVENT3);
        MockMessage mockMessage = new MockMessage(Button.HUNT.get());
        action.processMessage(user, mockMessage);
        assertAll(
                () -> assertEquals(SquadState.MOVE, user.getSquad().getSquadState()),
                () -> assertEquals(3, user.getSquad().getResource(Squad.AMMO)),
                () -> assertEquals(6, user.getSquad().getResource(Squad.FOOD)),
                () -> assertEquals(3, user.getSquad().getResource(Squad.GOLD)),
                () -> assertEquals(10, user.getSquad().getResource(Squad.SHOOTER)),
                () -> assertEquals(0, user.getSquad().getResource(Squad.PATHFINDING)),
                () -> assertEquals(0, user.getSquad().getResource(Squad.PERIOD))
        );
    }

    @Test
    void processMessageTest9() {
        user.getSquad().setSquadState(SquadState.EVENT6);
        MockMessage mockMessage = new MockMessage(Button.LOSE2FOOD.get());
        action.processMessage(user, mockMessage);
        assertAll(
                () -> assertEquals(SquadState.MOVE, user.getSquad().getSquadState()),
                () -> assertEquals(3, user.getSquad().getResource(Squad.AMMO)),
                () -> assertEquals(4, user.getSquad().getResource(Squad.FOOD)),
                () -> assertEquals(3, user.getSquad().getResource(Squad.GOLD)),
                () -> assertEquals(10, user.getSquad().getResource(Squad.SHOOTER)),
                () -> assertEquals(0, user.getSquad().getResource(Squad.PATHFINDING)),
                () -> assertEquals(0, user.getSquad().getResource(Squad.PERIOD))
        );
    }

    @Test
    void processMessageTest10() {
        user.getSquad().setSquadState(SquadState.EVENT6);
        MockMessage mockMessage = new MockMessage(Button.LOSE2GUN.get());
        action.processMessage(user, mockMessage);
        assertAll(
                () -> assertEquals(SquadState.MOVE, user.getSquad().getSquadState()),
                () -> assertEquals(3, user.getSquad().getResource(Squad.AMMO)),
                () -> assertEquals(6, user.getSquad().getResource(Squad.FOOD)),
                () -> assertEquals(3, user.getSquad().getResource(Squad.GOLD)),
                () -> assertEquals(8, user.getSquad().getResource(Squad.SHOOTER)),
                () -> assertEquals(0, user.getSquad().getResource(Squad.PATHFINDING)),
                () -> assertEquals(0, user.getSquad().getResource(Squad.PERIOD))
        );
    }

    @Test
    void processMessageTest11() {
        user.getSquad().setSquadState(SquadState.EVENT6);
        MockMessage mockMessage = new MockMessage(Button.LOSEFOODANDGOLD.get());
        action.processMessage(user, mockMessage);
        assertAll(
                () -> assertEquals(SquadState.MOVE, user.getSquad().getSquadState()),
                () -> assertEquals(3, user.getSquad().getResource(Squad.AMMO)),
                () -> assertEquals(5, user.getSquad().getResource(Squad.FOOD)),
                () -> assertEquals(2, user.getSquad().getResource(Squad.GOLD)),
                () -> assertEquals(10, user.getSquad().getResource(Squad.SHOOTER)),
                () -> assertEquals(0, user.getSquad().getResource(Squad.PATHFINDING)),
                () -> assertEquals(0, user.getSquad().getResource(Squad.PERIOD))
        );
    }

    @Test
    void processMessageTest12() {
        user.getSquad().setSquadState(SquadState.EVENT6);
        MockMessage mockMessage = new MockMessage(Button.LOSE2GUN.get());
        action.processMessage(user, mockMessage);
        assertAll(
                () -> assertEquals(SquadState.MOVE, user.getSquad().getSquadState()),
                () -> assertEquals(3, user.getSquad().getResource(Squad.AMMO)),
                () -> assertEquals(6, user.getSquad().getResource(Squad.FOOD)),
                () -> assertEquals(3, user.getSquad().getResource(Squad.GOLD)),
                () -> assertEquals(8, user.getSquad().getResource(Squad.SHOOTER)),
                () -> assertEquals(0, user.getSquad().getResource(Squad.PATHFINDING)),
                () -> assertEquals(0, user.getSquad().getResource(Squad.PERIOD))
        );
    }

    @Test
    void processMessageTest13() {
        user.getSquad().setSquadState(SquadState.EVENT6);
        MockMessage mockMessage = new MockMessage(Button.HUNT.get());
        action.processMessage(user, mockMessage);
        assertAll(
                () -> assertEquals(SquadState.EVENT6, user.getSquad().getSquadState()),
                () -> assertEquals(3, user.getSquad().getResource(Squad.AMMO)),
                () -> assertEquals(6, user.getSquad().getResource(Squad.FOOD)),
                () -> assertEquals(3, user.getSquad().getResource(Squad.GOLD)),
                () -> assertEquals(10, user.getSquad().getResource(Squad.SHOOTER)),
                () -> assertEquals(0, user.getSquad().getResource(Squad.PATHFINDING)),
                () -> assertEquals(0, user.getSquad().getResource(Squad.PERIOD))
        );
    }
}

