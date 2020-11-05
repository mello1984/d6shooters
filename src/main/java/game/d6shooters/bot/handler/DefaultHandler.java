package game.d6shooters.bot.handler;

import game.d6shooters.bot.D6ShootersBot;
import org.telegram.telegrambots.meta.api.objects.Message;

public class DefaultHandler implements Handler {
    @Override
    public void handle(Message message) {
        D6ShootersBot.senderMessage.sendText(message.getChatId(), "Команда не распознана, просьба повторить");
    }
}
