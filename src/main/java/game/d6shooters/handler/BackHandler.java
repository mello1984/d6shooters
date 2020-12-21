package game.d6shooters.handler;

import game.d6shooters.Main;
import game.d6shooters.bot.Bot;
import game.d6shooters.users.User;
import org.telegram.telegrambots.meta.api.objects.Message;

public class BackHandler extends AbstractHandler {
    public BackHandler(Bot bot) {
        super(bot);
    }

    @Override
    public void handle(Message message) {
        User user = Main.users.getUserMap().get(message.getChatId());
        bot.send(template.getSquadStateMessage(user.getChatId()));
    }
}
