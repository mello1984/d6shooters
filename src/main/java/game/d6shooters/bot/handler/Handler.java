package game.d6shooters.bot.handler;

import org.telegram.telegrambots.meta.api.objects.Message;

public interface Handler {
    void handle(Message message);

    void processMessage(Message message);

    void setNextHandler(Handler nextHandler);
}
