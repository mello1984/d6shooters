package game.d6shooters.bot.handler;

import game.d6shooters.Main;
import game.d6shooters.bot.Bot;
import game.d6shooters.bot.SendMessageTemplate;
import game.d6shooters.users.User;
import org.telegram.telegrambots.meta.api.objects.Message;

public class TeamStateHandler implements Handler {
    @Override
    public void handle(Message message) {
        SendMessageTemplate template = new SendMessageTemplate();
        User user = Main.users.userMap.get(message.getChatId());
        String text = template.squadState(user.getSquad());
        Bot.senderMessage.sendText(user.getChatId(), text);
    }
}
