package game.d6shooters.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SenderMessageTelegram implements SenderMessage {
    private final D6ShootersBot bot;
    final Logger logger = Logger.getLogger(this.getClass().getName());


    public SenderMessageTelegram(D6ShootersBot bot) {
        this.bot = bot;
    }

    public void sendText(Long chatId, String s) {
        SendMessage sendMessage = SendMessageFormat.getSendMessageBaseFormat(chatId)
                .setText(s);
        send(sendMessage);
    }

    public void sendMessage(SendMessage sendMessage) {
        send(sendMessage);
    }

    private synchronized void send(SendMessage message) {
        try {
            bot.execute(message);
            logger.log(Level.INFO, String.format("Message %s sent to: %s", message.getChatId()));
        } catch (TelegramApiException e) {
            logger.log(Level.SEVERE, String.format("Exception of sending message %s to: %s", message.getChatId()), e);
        }
    }
}
