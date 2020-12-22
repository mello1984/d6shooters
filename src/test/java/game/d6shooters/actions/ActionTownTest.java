package game.d6shooters.actions;

import game.d6shooters.Main;
import game.d6shooters.game.Squad;
import game.d6shooters.game.SquadState;
import game.d6shooters.mocks.MockActionManager;
import game.d6shooters.mocks.MockBot;
import game.d6shooters.mocks.MockMessage;
import game.d6shooters.mocks.MockTemplate;
import game.d6shooters.road.TownShop;
import game.d6shooters.users.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActionTownTest {
    User user = new User(0, "name");
    MockBot mockBot = new MockBot();
    MockTemplate mockTemplate = new MockTemplate();
    MockActionManager mockActionManager = new MockActionManager();
    ActionTown action = new ActionTown();

    @BeforeEach
    void setUp() {
        Main.users.getUserMap().put(user.getChatId(), user);
        action.template = mockTemplate;
        //        user.setActionManager(mockActionManager);
        user.getSquad().setSquadState(SquadState.TOWN);
        user.getSquad().setResource(Squad.AMMO,0);
        user.getSquad().setResource(Squad.SHOOTER,8);
    }

    @Test
    void processMessageTest1() {
        action.processMessage(user, new MockMessage(TownShop.POKER));
        assertEquals(SquadState.POKER1, user.getSquad().getSquadState());
    }

    @Test
    void processMessageTest2() {
        action.processMessage(user, new MockMessage(TownShop.REJECT));
        assertEquals(SquadState.MOVE, user.getSquad().getSquadState());
    }

    @Test
    void processMessageTest3() {
        action.processMessage(user, new MockMessage(TownShop.Item.FOOD1.getText()));
        assertAll(
                () -> assertEquals(SquadState.TOWN, user.getSquad().getSquadState()),
                () -> assertEquals(8, user.getSquad().getResource(Squad.FOOD)),
                () -> assertEquals(2, user.getSquad().getResource(Squad.GOLD)),
                () -> assertFalse(user.getSquad().getPlace().getTownShop().getItems().contains(TownShop.Item.FOOD1)),
                () -> assertFalse(user.getSquad().getPlace().getTownShop().getItems().contains(TownShop.Item.FOOD2))
        );
    }

    @Test
    void processMessageTest4() {
        action.processMessage(user, new MockMessage(TownShop.Item.FOOD2.getText()));
        assertAll(
                () -> assertEquals(SquadState.TOWN, user.getSquad().getSquadState()),
                () -> assertEquals(11, user.getSquad().getResource(Squad.FOOD)),
                () -> assertEquals(1, user.getSquad().getResource(Squad.GOLD)),
                () -> assertFalse(user.getSquad().getPlace().getTownShop().getItems().contains(TownShop.Item.FOOD1)),
                () -> assertFalse(user.getSquad().getPlace().getTownShop().getItems().contains(TownShop.Item.FOOD2))
        );
    }

    @Test
    void processMessageTest5() {
        user.getSquad().setResource(Squad.FOOD, 8);
        action.processMessage(user, new MockMessage(TownShop.Item.FOOD2.getText()));
        assertAll(
                () -> assertEquals(SquadState.TOWN, user.getSquad().getSquadState()),
                () -> assertEquals(12, user.getSquad().getResource(Squad.FOOD)),
                () -> assertEquals(1, user.getSquad().getResource(Squad.GOLD)),
                () -> assertFalse(user.getSquad().getPlace().getTownShop().getItems().contains(TownShop.Item.FOOD1)),
                () -> assertFalse(user.getSquad().getPlace().getTownShop().getItems().contains(TownShop.Item.FOOD2))
        );
    }

    @Test
    void processMessageTest6() {
        action.processMessage(user, new MockMessage(TownShop.Item.AMMO1.getText()));
        assertAll(
                () -> assertEquals(SquadState.TOWN, user.getSquad().getSquadState()),
                () -> assertEquals(2, user.getSquad().getResource(Squad.AMMO)),
                () -> assertEquals(2, user.getSquad().getResource(Squad.GOLD)),
                () -> assertFalse(user.getSquad().getPlace().getTownShop().getItems().contains(TownShop.Item.AMMO1)),
                () -> assertFalse(user.getSquad().getPlace().getTownShop().getItems().contains(TownShop.Item.AMMO2))
        );
    }

    @Test
    void processMessageTest7() {
        action.processMessage(user, new MockMessage(TownShop.Item.AMMO2.getText()));
        assertAll(
                () -> assertEquals(SquadState.TOWN, user.getSquad().getSquadState()),
                () -> assertEquals(5, user.getSquad().getResource(Squad.AMMO)),
                () -> assertEquals(1, user.getSquad().getResource(Squad.GOLD)),
                () -> assertFalse(user.getSquad().getPlace().getTownShop().getItems().contains(TownShop.Item.AMMO1)),
                () -> assertFalse(user.getSquad().getPlace().getTownShop().getItems().contains(TownShop.Item.AMMO2))
        );
    }

    @Test
    void processMessageTest8() {
        user.getSquad().setResource(Squad.AMMO,2);
        action.processMessage(user, new MockMessage(TownShop.Item.AMMO2.getText()));
        assertAll(
                () -> assertEquals(SquadState.TOWN, user.getSquad().getSquadState()),
                () -> assertEquals(5, user.getSquad().getResource(Squad.AMMO)),
                () -> assertEquals(1, user.getSquad().getResource(Squad.GOLD)),
                () -> assertFalse(user.getSquad().getPlace().getTownShop().getItems().contains(TownShop.Item.AMMO1)),
                () -> assertFalse(user.getSquad().getPlace().getTownShop().getItems().contains(TownShop.Item.AMMO2))
        );
    }

    @Test
    void processMessageTest9() {
        action.processMessage(user, new MockMessage(TownShop.Item.HIRE1.getText()));
        assertAll(
                () -> assertEquals(SquadState.TOWN, user.getSquad().getSquadState()),
                () -> assertEquals(9, user.getSquad().getResource(Squad.SHOOTER)),
                () -> assertEquals(2, user.getSquad().getResource(Squad.GOLD)),
                () -> assertFalse(user.getSquad().getPlace().getTownShop().getItems().contains(TownShop.Item.HIRE1)),
                () -> assertFalse(user.getSquad().getPlace().getTownShop().getItems().contains(TownShop.Item.HIRE2)),
                () -> assertFalse(user.getSquad().getPlace().getTownShop().getItems().contains(TownShop.Item.HIRE3))
        );
    }

    @Test
    void processMessageTest10() {
        action.processMessage(user, new MockMessage(TownShop.Item.HIRE2.getText()));
        assertAll(
                () -> assertEquals(SquadState.TOWN, user.getSquad().getSquadState()),
                () -> assertEquals(10, user.getSquad().getResource(Squad.SHOOTER)),
                () -> assertEquals(1, user.getSquad().getResource(Squad.GOLD)),
                () -> assertFalse(user.getSquad().getPlace().getTownShop().getItems().contains(TownShop.Item.HIRE1)),
                () -> assertFalse(user.getSquad().getPlace().getTownShop().getItems().contains(TownShop.Item.HIRE2)),
                () -> assertFalse(user.getSquad().getPlace().getTownShop().getItems().contains(TownShop.Item.HIRE3))
        );
    }

    @Test
    void processMessageTest11() {
        action.processMessage(user, new MockMessage(TownShop.Item.HIRE3.getText()));
        assertAll(
                () -> assertEquals(SquadState.TOWN, user.getSquad().getSquadState()),
                () -> assertEquals(11, user.getSquad().getResource(Squad.SHOOTER)),
                () -> assertEquals(0, user.getSquad().getResource(Squad.GOLD)),
                () -> assertFalse(user.getSquad().getPlace().getTownShop().getItems().contains(TownShop.Item.HIRE1)),
                () -> assertFalse(user.getSquad().getPlace().getTownShop().getItems().contains(TownShop.Item.HIRE2)),
                () -> assertFalse(user.getSquad().getPlace().getTownShop().getItems().contains(TownShop.Item.HIRE3))
        );
    }

    @Test
    void processMessageTest12() {
        user.getSquad().getPlace().getTownShop().setSpecialItem(TownShop.Item.COMPASS);
        action.processMessage(user, new MockMessage(TownShop.Item.COMPASS.getText()));
        assertAll(
                () -> assertEquals(SquadState.TOWN, user.getSquad().getSquadState()),
                () -> assertTrue(user.getSquad().hasResource(Squad.COMPASS)),
                () -> assertEquals(1, user.getSquad().getResource(Squad.GOLD)),
                () -> assertFalse(user.getSquad().getPlace().getTownShop().getItems().contains(TownShop.Item.COMPASS))
        );
    }

    @Test
    void processMessageTest13() {
        user.getSquad().getPlace().getTownShop().setSpecialItem(TownShop.Item.HUNTER);
        action.processMessage(user, new MockMessage(TownShop.Item.HUNTER.getText()));
        assertAll(
                () -> assertEquals(SquadState.TOWN, user.getSquad().getSquadState()),
                () -> assertTrue(user.getSquad().hasResource(Squad.HUNTER)),
                () -> assertEquals(0, user.getSquad().getResource(Squad.GOLD)),
                () -> assertFalse(user.getSquad().getPlace().getTownShop().getItems().contains(TownShop.Item.HUNTER))
        );
    }

    @Test
    void processMessageTest14() {
        user.getSquad().getPlace().getTownShop().setSpecialItem(TownShop.Item.MAP);
        action.processMessage(user, new MockMessage(TownShop.Item.MAP.getText()));
        assertAll(
                () -> assertEquals(SquadState.TOWN, user.getSquad().getSquadState()),
                () -> assertTrue(user.getSquad().hasResource(Squad.MAP)),
                () -> assertEquals(0, user.getSquad().getResource(Squad.GOLD)),
                () -> assertFalse(user.getSquad().getPlace().getTownShop().getItems().contains(TownShop.Item.MAP))
        );
    }

    @Test
    void processMessageTest15() {
        user.getSquad().getPlace().getTownShop().setSpecialItem(TownShop.Item.BINOCULAR);
        action.processMessage(user, new MockMessage(TownShop.Item.BINOCULAR.getText()));
        assertAll(
                () -> assertEquals(SquadState.TOWN, user.getSquad().getSquadState()),
                () -> assertTrue(user.getSquad().hasResource(Squad.BINOCULAR)),
                () -> assertEquals(1, user.getSquad().getResource(Squad.GOLD)),
                () -> assertFalse(user.getSquad().getPlace().getTownShop().getItems().contains(TownShop.Item.BINOCULAR))
        );
    }

    @Test
    void processMessageTest16() {
        user.getSquad().getPlace().getTownShop().setSpecialItem(TownShop.Item.PILL);
        action.processMessage(user, new MockMessage(TownShop.Item.PILL.getText()));
        assertAll(
                () -> assertEquals(SquadState.TOWN, user.getSquad().getSquadState()),
                () -> assertTrue(user.getSquad().hasResource(Squad.PILL)),
                () -> assertEquals(1, user.getSquad().getResource(Squad.GOLD)),
                () -> assertFalse(user.getSquad().getPlace().getTownShop().getItems().contains(TownShop.Item.PILL))
        );
    }

    @Test
    void processMessageTest17() {
        user.getSquad().getPlace().getTownShop().setSpecialItem(TownShop.Item.BOMB1);
        action.processMessage(user, new MockMessage(TownShop.Item.BOMB1.getText()));
        assertAll(
                () -> assertEquals(SquadState.TOWN, user.getSquad().getSquadState()),
                () -> assertEquals(1, user.getSquad().getResource(Squad.BOMB)),
                () -> assertEquals(2, user.getSquad().getResource(Squad.GOLD)),
                () -> assertFalse(user.getSquad().getPlace().getTownShop().getItems().contains(TownShop.Item.BOMB1)),
                () -> assertFalse(user.getSquad().getPlace().getTownShop().getItems().contains(TownShop.Item.BOMB2)),
                () -> assertFalse(user.getSquad().getPlace().getTownShop().getItems().contains(TownShop.Item.BOMB3))
        );
    }

    @Test
    void processMessageTest18() {
        user.getSquad().getPlace().getTownShop().setSpecialItem(TownShop.Item.BOMB1);
        action.processMessage(user, new MockMessage(TownShop.Item.BOMB2.getText()));
        assertAll(
                () -> assertEquals(SquadState.TOWN, user.getSquad().getSquadState()),
                () -> assertEquals(2, user.getSquad().getResource(Squad.BOMB)),
                () -> assertEquals(1, user.getSquad().getResource(Squad.GOLD)),
                () -> assertFalse(user.getSquad().getPlace().getTownShop().getItems().contains(TownShop.Item.BOMB1)),
                () -> assertFalse(user.getSquad().getPlace().getTownShop().getItems().contains(TownShop.Item.BOMB2)),
                () -> assertFalse(user.getSquad().getPlace().getTownShop().getItems().contains(TownShop.Item.BOMB3))
        );
    }

    @Test
    void processMessageTest19() {
        user.getSquad().getPlace().getTownShop().setSpecialItem(TownShop.Item.BOMB1);
        action.processMessage(user, new MockMessage(TownShop.Item.BOMB3.getText()));
        assertAll(
                () -> assertEquals(SquadState.TOWN, user.getSquad().getSquadState()),
                () -> assertEquals(3, user.getSquad().getResource(Squad.BOMB)),
                () -> assertEquals(0, user.getSquad().getResource(Squad.GOLD)),
                () -> assertFalse(user.getSquad().getPlace().getTownShop().getItems().contains(TownShop.Item.BOMB1)),
                () -> assertFalse(user.getSquad().getPlace().getTownShop().getItems().contains(TownShop.Item.BOMB2)),
                () -> assertFalse(user.getSquad().getPlace().getTownShop().getItems().contains(TownShop.Item.BOMB3))
        );
    }  @Test
    void processMessageTest20() {
        user.getSquad().getPlace().getTownShop().setSpecialItem(TownShop.Item.BOMB1);
        action.processMessage(user, new MockMessage("Hello world"));
        assertAll(
                () -> assertEquals(SquadState.TOWN, user.getSquad().getSquadState()),
                () -> assertEquals(0, user.getSquad().getResource(Squad.BOMB)),
                () -> assertEquals(3, user.getSquad().getResource(Squad.GOLD)),
                () -> assertEquals( user.getSquad().getPlace().getTownShop().getSpecialItem(), TownShop.Item.BOMB1)
        );
    }
}