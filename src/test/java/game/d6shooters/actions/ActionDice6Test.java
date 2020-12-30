package game.d6shooters.actions;

import game.d6shooters.Main;
import game.d6shooters.bot.Bot;
import game.d6shooters.bot.SendMessageTemplate;
import game.d6shooters.game.Squad;
import game.d6shooters.game.SquadState;
import game.d6shooters.mocks.MockActionManager;
import game.d6shooters.mocks.MockBot;
import game.d6shooters.mocks.MockTemplate;
import game.d6shooters.users.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class ActionDice6Test {
    User user = new User(0, "name");
    ActionDice6 action = new ActionDice6();

    @BeforeEach
    void setUp() {
        user.getSquad().setSquadState(SquadState.TOWN);
        user.getSquad().setResource(Squad.AMMO, 3);
        user.getSquad().setResource(Squad.SHOOTER, 8);

        Main.actionManager = Mockito.mock(ActionManager.class);
        Main.bot = Mockito.mock(Bot.class);
        action.template = Mockito.mock(SendMessageTemplate.class);
    }

    @Test
    void getKilledShootersInShootoutTest1() {
        user.getSquad().setResource(Squad.AMMO, 3);
        action.getKilledShootersInShootout(user);
        assertEquals(2, user.getSquad().getResource(Squad.AMMO));
    }

    @Test
    void getKilledShootersInShootoutTest2() {
        user.getSquad().setResource(Squad.AMMO, 0);
        action.getKilledShootersInShootout(user);
        assertEquals(0, user.getSquad().getResource(Squad.AMMO));
    }

    @Test
    void getKilledShootersNoShootoutTest1() {
        user.getSquad().setResource(Squad.AMMO, 3);
        action.getKilledShootersNoShootout(user);
        assertEquals(3, user.getSquad().getResource(Squad.AMMO));
    }

    @Test
    void getKilledShootersNoShootoutTest2() {
        user.getSquad().setResource(Squad.AMMO, 0);
        action.getKilledShootersNoShootout(user);
        assertEquals(0, user.getSquad().getResource(Squad.AMMO));
    }


}