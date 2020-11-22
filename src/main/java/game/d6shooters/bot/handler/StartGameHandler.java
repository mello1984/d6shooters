package game.d6shooters.bot.handler;

import game.d6shooters.Main;
import game.d6shooters.bot.Bot;
import game.d6shooters.bot.ButtonsType;
import game.d6shooters.bot.SendMessageTemplate;
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
        SendMessageTemplate template = new SendMessageTemplate();
            Main.users.userMap.put(chatId, new User(chatId, message.getFrom().getUserName()));
            Main.users.userMap.get(chatId).getSquad().setSquadState(SquadState.STARTTURN);
            Main.users.userMap.get(chatId).getSquad().getSquadState().resetStep();

            String text = "Вы успешно начали игру\n" + template.getSquadStateMessage(chatId).getText();
            bot.send(template.getSendMessageWithButtons(chatId, text, ButtonsType.NEXT_TURN.name()));
//        }
    }
}
