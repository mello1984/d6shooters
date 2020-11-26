package game.d6shooters.bot.handler;

import game.d6shooters.bot.Bot;
import game.d6shooters.bot.CommandButton;
import game.d6shooters.bot.SendMessageTemplate;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PROTECTED)
public abstract class AbstractHandler implements Handler {
    SendMessageTemplate template = SendMessageTemplate.getInstance();
    Bot bot;
    Handler nextHandler;

    public void setNextHandler(Handler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public AbstractHandler(Bot bot) {
        this.bot = bot;
    }
}
