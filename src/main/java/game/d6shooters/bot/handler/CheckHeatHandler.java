package game.d6shooters.bot.handler;

import game.d6shooters.Main;
import game.d6shooters.users.User;
import org.telegram.telegrambots.meta.api.objects.Message;

public class CheckHeatHandler extends AbstractHandler {
    @Override
    public void handle(Message message) {
        User user = Main.users.userMap.get(message.getChatId());
        user.getActionManager().doActions(message);
    }
}
