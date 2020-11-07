package game.d6shooters.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface SenderMessage {
    void sendMessage(SendMessage sendMessage);
}
