package game.d6shooters.actions;

import game.d6shooters.bot.D6ShootersBot;
import game.d6shooters.bot.ReceiverMessage;
import game.d6shooters.bot.SendMessageTemplate;
import game.d6shooters.game.DicesCup;
import game.d6shooters.game.Game;
import game.d6shooters.game.Squad;
import game.d6shooters.bot.SenderMessage;
import game.d6shooters.game.SquadState;
import game.d6shooters.users.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public class ActionDice5 extends AbstractAction {

    @Override
    public void action(User user) {
        DicesCup dicesCup = user.getDicesCup();
        Squad squad = user.getSquad();
        int dice5count = (int) dicesCup.diceList.stream().filter(dice -> dice.getValue() == 5 && !dice.isUsed()).count();
        template = new SendMessageTemplate();
        if (dice5count > 0) {
            int roll = DicesCup.getD6Int();
            if (roll >= 3) {
                SendMessage sendMessage = template.getSendMessageOneLineButtons(user.getChatId(),
                        "Экстремальная жара, roll '" + roll + "' из 6.",
                        "Lose 2 food", "lose 1 gunfighter");
                D6ShootersBot.senderMessage.sendMessage(sendMessage);
            } else {
                useDice(user, 5);
                SendMessage sendMessage = template.getSendMessageOneLineButtons(user.getChatId(),
                        "Экстремальная жара, roll '" + roll + "' из 6.");
                D6ShootersBot.senderMessage.sendMessage(sendMessage);
                user.getActionManager().doActions();
            }
        }
        if (dice5count == 0) {
            System.out.println(SquadState.CHECKHEAT + "->" + SquadState.GUNFIGHT);
            squad.squadState = SquadState.GUNFIGHT;
        }
    }

    public void processMessage(User user, Message message) {
        if (message.getText().equals("Lose 2 food")) {
            user.getSquad().addFood(-2);
            useDice(user, 5);
            user.getActionManager().doActions();
        } else if (message.getText().equals("lose 1 gunfighter")) {
            user.getSquad().addShooters(-1);
            useDice(user, 5);
            user.getActionManager().doActions();
        }

    }


}
