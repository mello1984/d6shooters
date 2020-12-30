package game.d6shooters.handler;

import game.d6shooters.Main;
import game.d6shooters.bot.SendMessageTemplate;
import game.d6shooters.source.Button;
import game.d6shooters.bot.SendMessageFormat;
import game.d6shooters.game.Squad;
import game.d6shooters.game.SquadState;
import game.d6shooters.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class CommandHandler implements Handler {
    @Autowired
    SendMessageTemplate template;

    @Override
    public void handle(Message message) {
        User user = Main.users.getUserMap().get(message.getChatId());
        Squad squad = user.getSquad();
        SendMessage sendMessage = template.getSendMessageNoButtons(user.getChatId(), "Выберите дополнительную команду");

        List<String> buttons1 = new ArrayList<>(Arrays.asList(Button.HELP_ABOUT.get(), Button.RESTART.get()));
        boolean canActivateEvent = squad.isCanActivateEvent() && squad.getSquadState() == SquadState.STARTTURN1;
        if (canActivateEvent) {
            buttons1.add(0, Button.EVENT.get());
            squad.setCanActivateEvent(false);
        }
        List<String> buttons2 = new ArrayList<>(Arrays.asList(Button.SCORES_MY.get(), Button.SCORES_HIGH.get(), Button.BACK.get()));

        List<List<String>> list = new ArrayList<>();
        list.add(buttons1);
        list.add(buttons2);
        SendMessageFormat.setButtons(sendMessage, list);
        Main.bot.send(sendMessage);
    }
}