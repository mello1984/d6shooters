package game.d6shooters.handler;

import game.d6shooters.Main;
import game.d6shooters.bot.SendMessageTemplate;
import game.d6shooters.users.User;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class BackHandler implements Handler {
    SendMessageTemplate template = new SendMessageTemplate();

    @Override
    public void handle(Message message) {
        User user = Main.users.getUserMap().get(message.getChatId());
        Main.bot.send(template.getSquadStateMessage(user.getChatId()));
    }
}
