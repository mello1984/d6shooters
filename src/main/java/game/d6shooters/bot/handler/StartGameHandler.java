package game.d6shooters.bot.handler;

import game.d6shooters.Main;
import game.d6shooters.bot.Bot;
import game.d6shooters.bot.ButtonsType;
import game.d6shooters.bot.SendMessageTemplate;
import game.d6shooters.game.SquadState;
import game.d6shooters.users.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public class StartGameHandler extends AbstractHandler {
    public StartGameHandler(Bot bot) {
        super(bot);
    }

    @Override
    public void handle(Message message) {
        long chatId = message.getChatId();
        SendMessageTemplate template = new SendMessageTemplate();

        if (!message.getText().equals("startD6")) {
            bot.send(template.helloString(chatId));
        } else {
            Main.users.userMap.put(chatId, new User(chatId, message.getFrom().getUserName()));
            Main.users.userMap.get(chatId).getSquad().squadState = SquadState.REGULAR;
            String text = "Вы успешно начали игру\n"
                    + template.squadState(Main.users.userMap.get(chatId).getSquad());
            SendMessage sendMessage = template.getSendMessageOneLineButtons(chatId, text, ButtonsType.NEXT_TURN.name());
            bot.send(sendMessage);
        }
    }
}
