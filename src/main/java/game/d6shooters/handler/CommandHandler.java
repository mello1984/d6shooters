package game.d6shooters.handler;

import game.d6shooters.Main;
import game.d6shooters.bot.Bot;
import game.d6shooters.source.Button;
import game.d6shooters.bot.SendMessageFormat;
import game.d6shooters.game.Squad;
import game.d6shooters.game.SquadState;
import game.d6shooters.users.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandHandler extends AbstractHandler {
    public CommandHandler(Bot bot) {
        super(bot);
    }

    @Override
    public void handle(Message message) {
        User user = Main.users.userMap.get(message.getChatId());
        Squad squad = user.getSquad();
        SendMessage sendMessage = template.getSendMessageNoButtons(user.getChatId(), "Выберите дополнительную команду");

        List<String> buttons = new ArrayList<>(Arrays.asList(Button.HELP.get(), Button.RESTART.get(), Button.BACK.get()));
        boolean canActivateEvent = squad.isCanActivateEvent() && squad.getSquadState() == SquadState.STARTTURN1;
        if (canActivateEvent) {
            buttons.add(0, Button.EVENT.get());
            squad.setCanActivateEvent(false);
        }

        List<List<String>> list = new ArrayList<>();
        list.add(buttons);
        SendMessageFormat.setButtons(sendMessage, list);
        bot.send(sendMessage);
    }
}