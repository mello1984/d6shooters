package game.d6shooters.mocks;

import game.d6shooters.actions.ActionManager;
import game.d6shooters.bot.Bot;
import game.d6shooters.users.User;

public class MockActionManager extends ActionManager {
    public MockActionManager(User user, Bot bot) {
        super(user, bot);
    }

    @Override
    public void doActions() {
    }
}
