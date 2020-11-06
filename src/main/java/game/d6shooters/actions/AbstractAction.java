package game.d6shooters.actions;

import game.d6shooters.bot.D6ShootersBot;
import game.d6shooters.bot.SendMessageTemplate;
import game.d6shooters.bot.SenderMessage;
import game.d6shooters.users.User;

public abstract class AbstractAction implements Action {
    SenderMessage senderMessage = D6ShootersBot.senderMessage;
    SendMessageTemplate template = new SendMessageTemplate();


    void useDice(User user, int value) {
        user.getDicesCup().diceList.stream()
                .filter(dice -> dice.getValue() == value && !dice.isUsed())
                .findFirst().orElseThrow(() -> new IllegalArgumentException("No '" + value + "' in stream allocation"))
                .setUsed(true);
    }
}
