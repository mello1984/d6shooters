package game.d6shooters.handler;

import game.d6shooters.Main;
import game.d6shooters.bot.Bot;
import game.d6shooters.bot.SendMessageTemplate;
import game.d6shooters.source.Button;
import game.d6shooters.bot.SendMessageFormat;
import game.d6shooters.users.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;


public class RestartHandler implements Handler{
        private static final String TEXT1 = "Вы уверены, что хотите сделать рестарт игры?";
    private static final String TEXT2 = "Проводится рестарт игры";
    SendMessageTemplate template = new SendMessageTemplate();


    @Override
    public void handle(Message message) {
        User user = Main.users.getUserMap().get(message.getChatId());
        long chatId = user.getChatId();

        switch (Button.getButton(message.getText())) {
            case RESTART -> {
                SendMessage sendMessage = template.getSendMessageNoButtons(user.getChatId(), TEXT1);
                SendMessageFormat.setButtons(sendMessage, Button.RESTART2.get(), Button.BACK.get());
                Main.bot.send(sendMessage);
            }
            case RESTART2 -> restartGame(chatId);
        }
    }

    public void restartGame(long chatId) {
        Main.users.getUserMap().remove(chatId);
        SendMessage sendMessage = template.getSendMessageNoButtons(chatId, TEXT2);
        SendMessageFormat.setButtons(sendMessage, "Начать новую игру");
        Main.bot.send(sendMessage);
    }
}
