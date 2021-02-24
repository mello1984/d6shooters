package game.d6shooters.bot.service;

import game.d6shooters.Main;
import game.d6shooters.bot.Bot;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Component
public class MessageSender implements Runnable {
    static int SLEEP_TIME = 30;

    @Override
    public void run() {
        log.info("START MessageSender, bot: " + Main.bot);
        try {
            while (true) {
                SendMessage sendMessage = Main.bot.sendQueue.take();
                log.debug(String.format("Message sent to: %s, : %s", sendMessage.getChatId(), sendMessage.getText()));
                send(sendMessage);
                Thread.sleep(SLEEP_TIME);

            }
        } catch (InterruptedException e) {
            log.error(e);
        }
    }

    private void send(SendMessage message) {
        try {
            Main.bot.execute(message);
            log.info(String.format("Message sent to: %s", message.getChatId()));
        } catch (TelegramApiException e) {
            log.error(String.format("Exception of sending message to: %s", message.getChatId()), e);
            try {
                Thread.sleep(100);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
            send(message);
        }
    }
}
