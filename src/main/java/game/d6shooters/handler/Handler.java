package game.d6shooters.handler;

import org.telegram.telegrambots.meta.api.objects.Message;

public interface Handler {
    void handle(Message message);
}