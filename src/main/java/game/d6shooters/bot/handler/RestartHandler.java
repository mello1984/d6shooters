package game.d6shooters.bot.handler;

import game.d6shooters.Main;
import game.d6shooters.bot.Bot;
import game.d6shooters.bot.CommandButton;
import game.d6shooters.bot.SendMessageFormat;
import game.d6shooters.users.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public class RestartHandler extends AbstractHandler {
    private static final String TEXT1 = "Вы уверены, что хотите сделать рестарт игры?";
    private static final String TEXT2 = "Проводится рестарт игры";

    public RestartHandler(Bot bot) {
        super(bot);
    }

    @Override
    public void handle(Message message) {
        if (CommandButton.getAction(message.getText()) == CommandButton.RESTART || CommandButton.getAction(message.getText()) == CommandButton.RESTART2)
            processMessage(message);
        else nextHandler.handle(message);
    }

    @Override
    public void processMessage(Message message) {
        User user = Main.users.userMap.get(message.getChatId());
        long chatId = user.getChatId();

        switch (CommandButton.getAction(message.getText())) {
            case RESTART -> {
                SendMessage sendMessage = template.getSendMessageNoButtons(user.getChatId(), TEXT1);
                SendMessageFormat.setButtons(sendMessage, CommandButton.RESTART2.get(), CommandButton.BACK.get());
                bot.send(sendMessage);
            }
            case RESTART2 -> restartGame(chatId);
        }
    }

    public void restartGame(long chatId) {
        Main.users.userMap.remove(chatId);
        SendMessage sendMessage = template.getSendMessageNoButtons(chatId, TEXT2);
        SendMessageFormat.setButtons(sendMessage, "Начать новую игру");
        bot.send(sendMessage);
    }
}
