package game.d6shooters.bot.handler;

import game.d6shooters.Main;
import game.d6shooters.bot.Bot;
import game.d6shooters.bot.CommandButton;
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

        List<String> buttons = new ArrayList<>(Arrays.asList(CommandButton.HELP.get(), CommandButton.RESTART.get(), CommandButton.BACK.get()));
        boolean canActivateEvent = squad.isCanActivateEvent() && squad.getSquadState() == SquadState.STARTTURN && squad.getSquadState().getStep() == 1;
        if (canActivateEvent) {
            buttons.add(0, CommandButton.EVENT.get());
            squad.setCanActivateEvent(false);
        }

        List<List<String>> list = new ArrayList<>();
        list.add(buttons);
        SendMessageFormat.setButtons(sendMessage, list);
        bot.send(sendMessage);
    }
}
