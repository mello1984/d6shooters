package game.d6shooters.bot;

import game.d6shooters.bot.handler.Handler;
import game.d6shooters.bot.handler.HandlerManager;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class D6ShootersBot extends TelegramLongPollingBot {
    public static SenderMessage senderMessage;

    public D6ShootersBot() {
        senderMessage = new SenderMessageTelegram(this);
    }


    @Override
    public void onUpdateReceived(Update update) {
        HandlerManager handlerManager = new HandlerManager();
        Handler handler = handlerManager.chooseHandler(update.getMessage());
        handler.handle(update.getMessage());

    }

    @Override
    public String getBotUsername() {
        return System.getenv("telegrambotusername");
    }

    @Override
    public String getBotToken() {
        return System.getenv("telegrambottoken");
    }
}
