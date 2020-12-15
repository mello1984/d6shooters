package game.d6shooters.bot.handler;

import game.d6shooters.Main;
import game.d6shooters.bot.Bot;
import game.d6shooters.bot.CommandButton;
import game.d6shooters.game.SquadState;
import game.d6shooters.users.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public class EventHandler extends AbstractHandler {
    private static final String TEXT1 = "Вы активировали случайное событие";

    public EventHandler(Bot bot) {
        super(bot);
    }

    @Override
    public void handle(Message message) {
        User user = Main.users.userMap.get(message.getChatId());
        SendMessage sendMessage = template.getSendMessageNoButtons(user.getChatId(), TEXT1);
        bot.send(sendMessage);
        user.getSquad().setSquadState(SquadState.EVENT);
        user.getActionManager().doActions();
    }
}
