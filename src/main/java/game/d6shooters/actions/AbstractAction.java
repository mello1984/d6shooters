package game.d6shooters.actions;

import game.d6shooters.bot.D6ShootersBot;
import game.d6shooters.bot.SenderMessage;

public abstract class AbstractAction implements Action {
    SenderMessage senderMessage = D6ShootersBot.senderMessage;
}
