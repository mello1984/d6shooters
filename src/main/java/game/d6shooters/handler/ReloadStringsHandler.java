package game.d6shooters.handler;

import game.d6shooters.Main;
import game.d6shooters.bot.Bot;
import game.d6shooters.bot.SendMessageFormat;
import game.d6shooters.bot.SendMessageTemplate;
import game.d6shooters.source.Text;
import game.d6shooters.users.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public class ReloadStringsHandler implements Handler {
    SendMessageTemplate template = new SendMessageTemplate();


    @Override
    public void handle(Message message) {
        Text.reloadTextConstants();
        User user = Main.users.getUserMap().get(message.getChatId());
        SendMessage sendMessage = template.getSendMessageNoButtons(user.getChatId(), "ReloadStringsHandler: successful");
        Main.bot.send(sendMessage);
    }
}

