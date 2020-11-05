package game.d6shooters.bot.handler;

import game.d6shooters.Main;
import game.d6shooters.actions.ActionManager;
import game.d6shooters.bot.D6ShootersBot;
import game.d6shooters.bot.SenderMessage;
import game.d6shooters.game.DicesCup;
import game.d6shooters.game.Squad;
import game.d6shooters.game.SquadState;
import org.telegram.telegrambots.meta.api.objects.Message;

public class StartTurnHandler implements Handler {
    @Override
    public void handle(Message message) {
        SenderMessage senderMessage = D6ShootersBot.senderMessage;
        long chatId = message.getChatId();
        Squad squad = Main.users.userMap.get(chatId).getSquad();
        DicesCup dicesCup = Main.users.userMap.get(chatId).getDicesCup();

        if (squad.squadState == SquadState.REGULAR) {
            senderMessage.sendText(chatId, dicesCup.getFirstTurnDices().toString());
            senderMessage.sendText(chatId, "Введите номера кубиков для переброски");
            squad.squadState = SquadState.REROLL1;
        }

        if (squad.squadState == SquadState.REROLL1) {
            if (!dicesCup.checkString(message.getText())) {
                senderMessage.sendText(chatId, "Некорректные данные, введите номера кубиков для переброски");
            } else {
                senderMessage.sendText(chatId, dicesCup.getRerolledDices(message.getText()).toString());
                squad.squadState = SquadState.REROLL2;
            }
        }

        if (squad.squadState == SquadState.REROLL2) {
            if (!dicesCup.checkString(message.getText())) {
                senderMessage.sendText(chatId, "Некорректные данные, введите номера кубиков для переброски");
            } else {
                senderMessage.sendText(chatId, dicesCup.getRerolledDices(message.getText()).toString());
                squad.squadState = SquadState.ALLOCATE;
                new ActionManager(squad, dicesCup).doActions();
            }
        }
    }
}
