package game.d6shooters.handler;

import game.d6shooters.Main;
import game.d6shooters.actions.ActionManager;
import game.d6shooters.bot.Bot;
import game.d6shooters.bot.SendMessageTemplate;
import game.d6shooters.source.Button;
import game.d6shooters.game.SquadState;
import game.d6shooters.source.Text;
import game.d6shooters.users.User;
import org.telegram.telegrambots.meta.api.objects.Message;

public class StartGameHandler implements Handler {
    SendMessageTemplate template = new SendMessageTemplate();

    @Override
    public void handle(Message message) {
        long chatId = message.getChatId();
        User user = new User(chatId, message.getFrom().getUserName());
        Main.users.getUserMap().put(chatId, user);
        user.getSquad().setSquadState(SquadState.STARTTURN1);

        Main.bot.send(template.getSendMessageNoButtons(chatId, Text.getText(Text.HELP_ABOUT)));
        Main.bot.send(template.getSendMessageNoButtons(chatId, Text.getText(Text.START_GAME)));
        String text = "Вы успешно начали игру\n" + template.getSquadStateMessage(chatId).getText();
        Main.bot.send(template.getSendMessageWithButtons(chatId, text, Button.NEXT_TURN.name()));
    }
}