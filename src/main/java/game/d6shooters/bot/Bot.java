package game.d6shooters.bot;

import game.d6shooters.bot.handler.Handler;
import game.d6shooters.bot.handler.HandlerManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Bot extends TelegramLongPollingBot {
    public final BlockingQueue<SendMessage> sendQueue = new LinkedBlockingQueue<>();
    public final BlockingQueue<SendMessage> receiveQueue = new LinkedBlockingQueue<>();
    public static final Logger log = LogManager.getLogger(Bot.class);
    private static final int PAUSE = 1000;

    public static SenderMessage senderMessage;

    public Bot() {
        senderMessage = new SenderMessageTelegram(this);
    }


    @Override
    public void onUpdateReceived(Update update) {
        HandlerManager handlerManager = new HandlerManager();
        Handler handler = handlerManager.chooseHandler(update.getMessage());
        handler.handle(update.getMessage());

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
