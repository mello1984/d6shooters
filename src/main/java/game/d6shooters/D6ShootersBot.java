package game.d6shooters;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class D6ShootersBot extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {

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
