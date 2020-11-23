package game.d6shooters.bot.handler;

import game.d6shooters.Main;
import game.d6shooters.bot.Bot;
import lombok.extern.log4j.Log4j2;
import org.telegram.telegrambots.meta.api.objects.Message;

@Log4j2
public class HandlerManager {
    private final Bot bot;

    public HandlerManager(Bot bot) {
        this.bot = bot;
    }

    public Handler chooseHandler(Message message) {
        if (!Main.users.userMap.containsKey(message.getChatId())) return new StartGameHandler(bot);
//        if (message.getText().equals("startD6")) return new StartGameHandler(bot);
        if (message.getText().equals("band")) return new TeamStateHandler(bot);
        if (message.getText().equals("help")) return new TeamStateHandler(bot);
        return new ActionManagerHandler(bot);
    }
}
