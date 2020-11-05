package game.d6shooters;

import game.d6shooters.bot.D6ShootersBot;
import game.d6shooters.game.Game;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;

public class Main {
    public static Users users = new Users();
    public static Game game = new Game();
    public static D6ShootersBot bot;

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        bot = new D6ShootersBot();
        try {
            telegramBotsApi.registerBot(bot);
        } catch (org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException e) {
            e.printStackTrace();
        }

    }
}
