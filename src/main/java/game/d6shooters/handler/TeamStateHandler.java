package game.d6shooters.handler;

import game.d6shooters.Main;
import game.d6shooters.bot.Bot;
import game.d6shooters.users.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public class TeamStateHandler extends AbstractHandler {
    public TeamStateHandler(Bot bot) {
        super(bot);
    }

    @Override
    public void handle(Message message) {
        User user = Main.users.getUserMap().get(message.getChatId());
        SendMessage sendMessage = template.getSquadStateMessage(user.getChatId());
        Main.bot.send(sendMessage);
    }
}

