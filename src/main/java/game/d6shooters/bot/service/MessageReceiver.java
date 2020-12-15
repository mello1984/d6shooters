package game.d6shooters.bot.service;

import game.d6shooters.bot.Bot;
import game.d6shooters.bot.handler.HandlerManager;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.telegram.telegrambots.meta.api.objects.Message;

@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MessageReceiver implements Runnable {
    static final int SLEEP_TIME = 30;
    Bot bot;
    HandlerManager handlerManager;

    public MessageReceiver(Bot bot) {
        this.bot = bot;
        handlerManager = new HandlerManager(bot);
    }

    @Override
    public void run() {
        log.info("START MessageReceiver, bot: " + bot);
        try {
            while (true) {
                Message message = bot.receiveQueue.take();
                handlerManager.chooseHandler(message)
                        .handle(message);
                log.debug(String.format("Message received from: %d, : %s", message.getChatId(), message.getText()));
                Thread.sleep(SLEEP_TIME);

            }
        } catch (InterruptedException e) {
            log.error(e);
        }
    }
}
