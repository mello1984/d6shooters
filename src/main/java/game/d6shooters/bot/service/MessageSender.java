package game.d6shooters.bot.service;

import game.d6shooters.bot.Bot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class MessageSender implements Runnable {
    private final int SENDER_SLEEP_TIME = 30;
    private Bot bot;
    private static final Logger log = LogManager.getLogger(MessageSender.class);

    public MessageSender(Bot bot) {
        this.bot = bot;
    }

    @Override
    public void run() {
        log.info("START MessageSender, bot: " + bot);
        try {
            while (true) {
                SendMessage sendMessage = bot.sendQueue.take();
                log.debug(String.format("Message sent to: %s, : %s", sendMessage.getChatId(), sendMessage.getText()));
                send(sendMessage);
                Thread.sleep(SENDER_SLEEP_TIME);

            }
        } catch (InterruptedException e) {
            log.error(e);
        }
    }

    private void send(SendMessage message) {
        try {
            bot.execute(message);
            log.info(String.format("Message sent to: %s", message.getChatId()));
        } catch (TelegramApiException e) {
            log.error(String.format("Exception of sending message to: %s", message.getChatId()), e);
        }
    }
}
