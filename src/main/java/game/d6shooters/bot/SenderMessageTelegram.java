package game.d6shooters.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SenderMessageTelegram implements SenderMessage {
    private final Bot bot;
    final Logger logger = Logger.getLogger(this.getClass().getName());


    public SenderMessageTelegram(Bot bot) {
        this.bot = bot;
    }

    public void sendMessage(SendMessage sendMessage) {
        send(sendMessage);
    }

    private synchronized void send(SendMessage message) {
        try {
            bot.execute(message);
            logger.log(Level.INFO, String.format("Message sent to: %s", message.getChatId()));
        } catch (TelegramApiException e) {
            logger.log(Level.SEVERE, String.format("Exception of sending message to: %s", message.getChatId()), e);
        }
    }
}
