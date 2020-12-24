package game.d6shooters.bot;

import lombok.extern.log4j.Log4j2;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Log4j2
public class Bot extends TelegramLongPollingBot  {
    public final BlockingQueue<SendMessage> sendQueue = new LinkedBlockingQueue<>();
    public final BlockingQueue<Message> receiveQueue = new LinkedBlockingQueue<>();
    private static final int PAUSE = 1000;

    @Override
    public void onUpdateReceived(Update update) {
        try {
            if (update.hasMessage()) receiveQueue.put(update.getMessage());
        } catch (InterruptedException e) {
            log.error(e);
        }
    }

    public void send(SendMessage sendMessage) {
        try {
            sendQueue.put(sendMessage);
        } catch (InterruptedException e) {
            log.error(e);
        }
    }

    public void botConnect() {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(this);
            log.info("BOT connected: " + this);
        } catch (org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException e) {
            log.error("BOT connect error. ", e);
            try {
                Thread.sleep(PAUSE);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
            botConnect();
        }
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
