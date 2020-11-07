package game.d6shooters.bot.handler;

import game.d6shooters.Main;
import game.d6shooters.bot.Bot;
import game.d6shooters.game.SquadState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.meta.api.objects.Message;

public class HandlerManager {
    private static final Logger log = LogManager.getLogger(HandlerManager.class);
    private Bot bot;

    public HandlerManager(Bot bot) {
        this.bot = bot;
    }

    public Handler chooseHandler(Message message) {
        long chatId = message.getChatId();

        if (!Main.users.userMap.containsKey(chatId)) return new StartGameHandler(bot);
        if (message.getText().equals("band")) return new TeamStateHandler(bot);

        SquadState squadState = Main.users.userMap.get(chatId).getSquad().squadState;
        Handler handler = switch (squadState) {
            case REGULAR, REROLL1, REROLL2 -> new StartTurnHandler(bot);
            case ALLOCATE -> new AllocationCubesHandler(bot);
            case CHECKHEAT -> new CheckHeatHandler(bot);
            default -> new DefaultHandler(bot);
        };
        log.info("Choose handler: " + handler.getClass().getSimpleName());
        return handler;
    }
}
