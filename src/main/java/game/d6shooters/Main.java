package game.d6shooters;

import game.d6shooters.bot.Bot;
import game.d6shooters.bot.service.MessageSender;
import game.d6shooters.game.Game;
import game.d6shooters.users.Users;
import org.telegram.telegrambots.ApiContextInitializer;

public class Main {
    public static final Users users = new Users();
    public static final Game game = new Game();

    public static void main(String[] args) {
        ApiContextInitializer.init();
        Bot bot = new Bot();
        bot.botConnect();
        MessageSender messageSender = new MessageSender(bot);

        Thread sender = new Thread(messageSender);
        sender.setDaemon(true);
        sender.setName("MsgSender");
        sender.start();
    }
}
