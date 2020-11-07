package game.d6shooters.bot.handler;

import game.d6shooters.bot.Bot;
import game.d6shooters.bot.SenderMessage;

public abstract class AbstractHandler implements Handler {
    SenderMessage senderMessage = Bot.senderMessage;
}
