package game.d6shooters.handler;

import game.d6shooters.Main;
import game.d6shooters.bot.Bot;
import game.d6shooters.bot.SendMessageFormat;
import game.d6shooters.users.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public class HelpHandler extends AbstractHandler {
    public HelpHandler(Bot bot) {
        super(bot);
    }

    @Override
    public void handle(Message message) {
        User user = Main.users.userMap.get(message.getChatId());
        SendMessage sendMessage = template.getSendMessageNoButtons(user.getChatId(), "HELP TEXT");
        SendMessageFormat.setButtons(sendMessage,user.getButtons());
        bot.send(sendMessage);
    }
}

