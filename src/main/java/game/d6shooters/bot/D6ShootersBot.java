package game.d6shooters.bot;

import game.d6shooters.Main;
import game.d6shooters.User;
import game.d6shooters.game.Squad;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class D6ShootersBot extends TelegramLongPollingBot {
    private final Sender sender;

    public D6ShootersBot() {
        sender = new Sender(this);
    }


    @Override
    public void onUpdateReceived(Update update) {
        Long chatId = update.getMessage().getChatId();
        Main.users.userMap.putIfAbsent(chatId, new User(chatId, update.getMessage().getFrom().getUserName()));
        Squad squad = Main.users.userMap.get(chatId).getSquad();

        SendMessageTemplate sendMessageTemplate = new SendMessageTemplate();
        sender.sendText(chatId, sendMessageTemplate.squadState(squad));
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
