package game.d6shooters.bot.handler;

import game.d6shooters.Main;
import game.d6shooters.bot.Bot;
import game.d6shooters.bot.CommandButton;
import game.d6shooters.users.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public class TeamStateHandler extends AbstractHandler {
    public TeamStateHandler(Bot bot) {
        super(bot);
    }

    @Override
    public void handle(Message message) {
        if (CommandButton.getAction(message.getText()) == CommandButton.BAND) processMessage(message);
        else nextHandler.handle(message);
    }

    @Override
    public void processMessage(Message message) {
        User user = Main.users.userMap.get(message.getChatId());
        bot.send(template.getSquadStateMessage(user.getChatId()));
    }
}
