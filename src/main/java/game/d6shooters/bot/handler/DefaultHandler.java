package game.d6shooters.bot.handler;

import game.d6shooters.Main;
import game.d6shooters.bot.Bot;
import game.d6shooters.users.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public class DefaultHandler extends AbstractHandler {
    public DefaultHandler(Bot bot) {
        super(bot);
    }

    @Override
    public void handle(Message message) {
        User user = Main.users.userMap.get(message.getChatId());
        SendMessage sendMessage = template.getSendMessageNoButtons(user.getChatId(), "DEFAULT HANDLER");
        bot.send(sendMessage);
    }
}
