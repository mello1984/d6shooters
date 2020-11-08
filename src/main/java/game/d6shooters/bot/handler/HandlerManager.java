package game.d6shooters.bot.handler;

import game.d6shooters.Main;
import game.d6shooters.bot.Bot;
import game.d6shooters.game.SquadState;
import lombok.extern.log4j.Log4j2;
import org.telegram.telegrambots.meta.api.objects.Message;

@Log4j2
public class HandlerManager {
    private Bot bot;

    public HandlerManager(Bot bot) {
        this.bot = bot;
    }

    public Handler chooseHandler(Message message) {
        if (!Main.users.userMap.containsKey(message.getChatId())) return new StartGameHandler(bot);
        if (message.getText().equals("band")) return new TeamStateHandler(bot);

        SquadState squadState = Main.users.userMap.get(message.getChatId()).getSquad().getSquadState();
        Handler handler = switch (squadState) {
            case REGULAR, REROLL1, REROLL2 -> new StartTurnHandler(bot);
            case ALLOCATE, CHECKHEAT, CROSSROAD, EVENT, EVENT2, EVENT3, EVENT6 -> new ActionManagerHandler(bot);
            default -> new DefaultHandler(bot);
        };
        log.debug("Choose handler: " + handler.getClass().getSimpleName());
        return handler;
    }
}
