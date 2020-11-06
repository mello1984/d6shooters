package game.d6shooters.bot.handler;

import game.d6shooters.Main;
import game.d6shooters.actions.ActionManager;
import game.d6shooters.bot.SendMessageTemplate;
import game.d6shooters.game.DicesCup;
import game.d6shooters.game.Squad;
import game.d6shooters.game.SquadState;
import game.d6shooters.users.User;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.ArrayList;

public class StartTurnHandler extends AbstractHandler {
    @Override
    public void handle(Message message) {
        long chatId = message.getChatId();
        User user = Main.users.userMap.get(chatId);
        Squad squad = user.getSquad();
        DicesCup dicesCup = Main.users.userMap.get(chatId).getDicesCup();
        SendMessageTemplate template = new SendMessageTemplate();
        squad.actionList = new ArrayList<>();

        if (squad.squadState == SquadState.REGULAR) {
            dicesCup.getFirstTurnDices();
            squad.squadState = SquadState.REROLL1;
            senderMessage.sendMessage(template.dicesString(chatId, dicesCup));
            senderMessage.sendText(chatId, "Введите номера кубиков для переброски или 0");
            return;
        }

        if (squad.squadState == SquadState.REROLL1) {
            if (!dicesCup.checkString(message.getText())) {
                senderMessage.sendText(chatId, "Некорректные данные, введите номера кубиков для переброски или 0");
            } else {
                squad.squadState = SquadState.REROLL2;
                if (!message.getText().equals("0")) {
                    dicesCup.getRerolledDices(message.getText());
                    senderMessage.sendMessage(template.dicesString(chatId, dicesCup));
                    senderMessage.sendText(chatId, "Введите номера кубиков для переброски или 0");
                    return;
                }
            }
        }

        if (squad.squadState == SquadState.REROLL2) {
            if (!dicesCup.checkString(message.getText())) {
                senderMessage.sendText(chatId, "Некорректные данные, введите номера кубиков для переброски или 0");
            } else {
                if (!message.getText().equals("0")) {
                    dicesCup.getRerolledDices(message.getText());
                    senderMessage.sendMessage(template.dicesString(chatId, dicesCup));
                    System.out.println("REROLL2: SquadState: " + squad.squadState);
                }
                squad.squadState = SquadState.ALLOCATE;
                user.setActionManager(new ActionManager(user));
                user.getActionManager().doActions();
            }
        }
    }
}