package game.d6shooters.handler;

import game.d6shooters.Main;
import game.d6shooters.bot.Bot;
import game.d6shooters.bot.SendMessageFormat;
import game.d6shooters.bot.SendMessageTemplate;
import game.d6shooters.source.Text;
import game.d6shooters.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class ReloadStringsHandler implements Handler {
    @Autowired
    SendMessageTemplate template;

    @Override
    public void handle(Message message) {
        Text.reloadTextConstants();
        User user = Main.users.getUserMap().get(message.getChatId());
        SendMessage sendMessage = template.getSendMessageNoButtons(user.getChatId(), "ReloadStringsHandler: successful");
        Main.bot.send(sendMessage);
    }
}

