package game.d6shooters;

import game.d6shooters.actions.ActionManager;
import game.d6shooters.bot.Bot;
import game.d6shooters.bot.service.MessageReceiver;
import game.d6shooters.bot.service.MessageSender;
import game.d6shooters.users.Users;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.telegram.telegrambots.ApiContextInitializer;

public class Main {
    public static final Users users = new Users();
    public static Bot bot;
    public static ActionManager actionManager = new ActionManager();

    public static void main(String[] args) {
        ApiContextInitializer.init();
        bot = new Bot();
        bot.botConnect();

        MessageSender messageSender = new MessageSender(bot);
        Thread sender = new Thread(messageSender);
        sender.setDaemon(true);
        sender.setName("MsgSender");
        sender.start();

        MessageReceiver messageReceiver = new MessageReceiver(bot);
        Thread receiver = new Thread(messageReceiver);
        receiver.setDaemon(true);
        receiver.setName("MsgReciever");
        receiver.start();
    }
}
