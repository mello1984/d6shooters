package game.d6shooters.bot.handler;

import game.d6shooters.Main;
import game.d6shooters.bot.D6ShootersBot;
import game.d6shooters.bot.SendMessageTemplate;
import game.d6shooters.users.User;
import org.telegram.telegrambots.meta.api.objects.Message;

public class StartGameHandler implements Handler {
    @Override
    public void handle(Message message) {
        long chatId = message.getChatId();
        SendMessageTemplate sendMessageTemplate = new SendMessageTemplate();

        if (!message.getText().equals("startD6")) {
            D6ShootersBot.senderMessage.sendMessage(sendMessageTemplate.helloString(chatId));
        } else {
            Main.users.userMap.put(chatId, new User(chatId, message.getFrom().getUserName()));
            String text = "Вы успешно начали игру\n"
                    + sendMessageTemplate.squadState(Main.users.userMap.get(chatId).getSquad());
            D6ShootersBot.senderMessage.sendText(chatId, text);
        }
    }
}
