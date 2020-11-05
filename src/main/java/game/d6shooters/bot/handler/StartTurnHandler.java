package game.d6shooters.bot.handler;

import game.d6shooters.Main;
import game.d6shooters.actions.ActionManager;
import game.d6shooters.bot.D6ShootersBot;
import game.d6shooters.bot.SendMessageTemplate;
import game.d6shooters.bot.SenderMessage;
import game.d6shooters.game.DicesCup;
import game.d6shooters.game.Squad;
import game.d6shooters.game.SquadState;
import org.telegram.telegrambots.meta.api.objects.Message;

public class StartTurnHandler extends AbstractHandler {
    @Override
    public void handle(Message message) {
        long chatId = message.getChatId();
        Squad squad = Main.users.userMap.get(chatId).getSquad();
        DicesCup dicesCup = Main.users.userMap.get(chatId).getDicesCup();
        SendMessageTemplate template = new SendMessageTemplate();

        if (squad.squadState == SquadState.REROLL2) {
            if (!dicesCup.checkString(message.getText())) {
                senderMessage.sendText(chatId, "Некорректные данные, введите номера кубиков для переброски");
            } else {
                squad.squadState = SquadState.ALLOCATE;
                dicesCup.getRerolledDices(message.getText());
                senderMessage.sendMessage(template.dicesString(chatId, dicesCup));

                System.out.println("REROLL2: SquadState: " + squad.squadState);
                new ActionManager(squad, dicesCup).doActions();
            }
        }

        if (squad.squadState == SquadState.REROLL1) {
            if (!dicesCup.checkString(message.getText())) {
                senderMessage.sendText(chatId, "Некорректные данные, введите номера кубиков для переброски");
            } else {
                squad.squadState = SquadState.REROLL2;
                dicesCup.getRerolledDices(message.getText());
                senderMessage.sendMessage(template.dicesString(chatId, dicesCup));
                senderMessage.sendText(chatId, "Введите номера кубиков для переброски");
            }
        }

        if (squad.squadState == SquadState.REGULAR) {
            dicesCup.getFirstTurnDices();
            squad.squadState = SquadState.REROLL1;
            senderMessage.sendMessage(template.dicesString(chatId, dicesCup));
            senderMessage.sendText(chatId, "Введите номера кубиков для переброски");
        }
    }
}