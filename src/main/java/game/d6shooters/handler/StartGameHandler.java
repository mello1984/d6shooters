package game.d6shooters.handler;

import game.d6shooters.Main;
import game.d6shooters.actions.ActionManager;
import game.d6shooters.bot.Bot;
import game.d6shooters.source.Button;
import game.d6shooters.game.SquadState;
import game.d6shooters.users.User;
import org.telegram.telegrambots.meta.api.objects.Message;

public class StartGameHandler extends AbstractHandler {
    public StartGameHandler(Bot bot) {
        super(bot);
    }

    @Override
    public void handle(Message message) {
        long chatId = message.getChatId();
        User user = new User(chatId, message.getFrom().getUserName());
        Main.users.userMap.put(chatId, user);
        user.getSquad().setSquadState(SquadState.STARTTURN1);
        user.setActionManager(new ActionManager(user, bot));
        String text = "Вы успешно начали игру\n" + template.getSquadStateMessage(chatId).getText();
        bot.send(template.getSendMessageWithButtons(chatId, text, Button.NEXT_TURN.name()));
    }
}