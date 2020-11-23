package game.d6shooters.mocks;

import game.d6shooters.actions.ActionManager;
import game.d6shooters.bot.Bot;
import game.d6shooters.users.User;
import org.telegram.telegrambots.meta.api.objects.Message;

public class MockActionManager extends ActionManager {
    public MockActionManager(User user, Bot bot) {
        super(user, bot);
    }

    @Override
    public void doActions() {
//        System.out.println("MockActionManager.doActions(). " + user.getChatId());
    }

    @Override
    public void doActions(Message message) {
//        System.out.println("MockActionManager.doActions(Message message) "+ user.getChatId());
    }
}
