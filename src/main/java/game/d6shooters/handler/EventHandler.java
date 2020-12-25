package game.d6shooters.handler;

import game.d6shooters.Main;
import game.d6shooters.bot.Bot;
import game.d6shooters.bot.SendMessageTemplate;
import game.d6shooters.game.SquadState;
import game.d6shooters.users.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public class EventHandler implements Handler {
    SendMessageTemplate template = new SendMessageTemplate();
    private static final String TEXT1 = "Вы активировали случайное событие";

    @Override
    public void handle(Message message) {
        User user = Main.users.getUserMap().get(message.getChatId());
        SendMessage sendMessage = template.getSendMessageNoButtons(user.getChatId(), TEXT1);
        Main.bot.send(sendMessage);
        user.getSquad().setSquadState(SquadState.EVENT);
        Main.actionManager.doActions(user);
    }
}