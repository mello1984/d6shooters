package game.d6shooters;

import game.d6shooters.actions.ActionManager;
import game.d6shooters.bot.Bot;
import game.d6shooters.bot.service.MessageReceiver;
import game.d6shooters.bot.service.MessageSender;
import game.d6shooters.handler.HandlerManager;
import game.d6shooters.users.Users;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.telegram.telegrambots.ApiContextInitializer;


public class Main {
    public static final Users users = new Users();
    public static Bot bot;
    public static ActionManager actionManager;
    public static HandlerManager handlerManager;

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        actionManager = context.getBean(ActionManager.class);
        handlerManager = context.getBean(HandlerManager.class);

        ApiContextInitializer.init();
        bot = new Bot();
        bot.botConnect();

        MessageSender messageSender = context.getBean(MessageSender.class);
        Thread sender = new Thread(messageSender);
        sender.setDaemon(true);
        sender.setName("MsgSender");
        sender.start();

        MessageReceiver messageReceiver = context.getBean(MessageReceiver.class);
        Thread receiver = new Thread(messageReceiver);
        receiver.setDaemon(true);
        receiver.setName("MsgReciever");
        receiver.start();
    }
}
