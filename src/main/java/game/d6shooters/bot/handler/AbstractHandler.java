package game.d6shooters.bot.handler;

import game.d6shooters.bot.Bot;
import game.d6shooters.bot.SendMessageTemplate;

public abstract class AbstractHandler implements Handler {
    SendMessageTemplate template = new SendMessageTemplate();
    Bot bot;

    public AbstractHandler(Bot bot) {
        this.bot = bot;
    }
}
