package game.d6shooters.handler;

import game.d6shooters.Main;
import game.d6shooters.bot.Bot;
import game.d6shooters.users.User;
import org.telegram.telegrambots.meta.api.objects.Message;

public class ActionManagerHandler extends AbstractHandler {
    public ActionManagerHandler(Bot bot) {
        super(bot);
    }

    @Override
    public void handle(Message message) {
        User user = Main.users.getUserMap().get(message.getChatId());
        user.getActionManager().doActions(message);
    }
}
