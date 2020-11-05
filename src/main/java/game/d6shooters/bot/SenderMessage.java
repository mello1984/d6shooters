package game.d6shooters.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface SenderMessage {
//    String get();
    void sendText(Long chatId, String message);
    void sendMessage(SendMessage sendMessage);
}
