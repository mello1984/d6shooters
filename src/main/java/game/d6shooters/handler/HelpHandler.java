package game.d6shooters.handler;

import game.d6shooters.Main;
import game.d6shooters.bot.Bot;
import game.d6shooters.bot.SendMessageFormat;
import game.d6shooters.bot.SendMessageTemplate;
import game.d6shooters.source.Button;
import game.d6shooters.source.Text;
import game.d6shooters.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class HelpHandler implements Handler {
    @Autowired
    SendMessageTemplate template;

    @Override
    public void handle(Message message) {
        User user = Main.users.getUserMap().get(message.getChatId());
        Button button = Button.getButton(message.getText());
        String text = switch (button) {
            case HELP_ABOUT -> Text.getText(Text.HELP_ABOUT);
            case HELP_MAIN -> Text.getText(Text.HELP_MAIN);
            case HELP_DICES -> Text.getText(Text.HELP_DICES);
            case HELP_EVENTS -> Text.getText(Text.HELP_EVENTS);
            case HELP_TOWN -> Text.getText(Text.HELP_TOWN);
            case HELP_END_GAME -> Text.getText(Text.HELP_END_GAME);
            default -> "";
        };
        SendMessage sendMessage = template.getSendMessageNoButtons(user.getChatId(), text);
        SendMessageFormat.setButtons(sendMessage, user.getButtons());
        Main.bot.send(sendMessage);
    }
}

