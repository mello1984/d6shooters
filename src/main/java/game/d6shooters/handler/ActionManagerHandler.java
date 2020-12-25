package game.d6shooters.handler;

import game.d6shooters.Main;
import game.d6shooters.actions.ActionManager;
import game.d6shooters.bot.Bot;
import game.d6shooters.users.User;
import org.telegram.telegrambots.meta.api.objects.Message;

public class ActionManagerHandler implements Handler {

    @Override
    public void handle(Message message) {
        Main.actionManager.doActions(message);
    }
}