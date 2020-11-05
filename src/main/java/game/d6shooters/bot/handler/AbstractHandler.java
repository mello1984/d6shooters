package game.d6shooters.bot.handler;

import game.d6shooters.bot.D6ShootersBot;
import game.d6shooters.bot.SenderMessage;

public abstract class AbstractHandler implements Handler {
    SenderMessage senderMessage = D6ShootersBot.senderMessage;
}
