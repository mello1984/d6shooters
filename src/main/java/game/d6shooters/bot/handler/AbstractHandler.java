package game.d6shooters.bot.handler;

import game.d6shooters.bot.Bot;
import game.d6shooters.bot.SendMessageTemplate;
import game.d6shooters.bot.SenderMessage;

public abstract class AbstractHandler implements Handler {
    SenderMessage senderMessage = Bot.senderMessage;
    SendMessageTemplate template = new SendMessageTemplate();
}
