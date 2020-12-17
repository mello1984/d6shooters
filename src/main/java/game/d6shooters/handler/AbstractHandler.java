package game.d6shooters.handler;

import game.d6shooters.bot.Bot;
import game.d6shooters.bot.SendMessageTemplate;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PROTECTED)
public abstract class AbstractHandler implements Handler {
    SendMessageTemplate template = new SendMessageTemplate();
    Bot bot;

    public AbstractHandler(Bot bot) {
        this.bot = bot;
    }
}
