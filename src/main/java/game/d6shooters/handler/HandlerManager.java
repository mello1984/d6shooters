package game.d6shooters.handler;

import game.d6shooters.Main;
import game.d6shooters.bot.Bot;
import game.d6shooters.source.Button;
import lombok.extern.log4j.Log4j2;
import org.telegram.telegrambots.meta.api.objects.Message;

@Log4j2
public class HandlerManager {
    private final Bot bot;

    public HandlerManager(Bot bot) {
        this.bot = bot;
    }

    public Handler chooseHandler(Message message) {
        if (!Main.users.getUserMap().containsKey(message.getChatId())) return new StartGameHandler(bot);

        Button command = Button.getButton(message.getText());
        return switch (command) {
            case BAND -> new TeamStateHandler(bot);
            case COMMAND -> new CommandHandler(bot);
            case EVENT -> new EventHandler(bot);
            case HELP_ABOUT, HELP_DICES, HELP_END_GAME, HELP_EVENTS, HELP_MAIN, HELP_TOWN -> new HelpHandler(bot);
            case RESTART, RESTART2 -> new RestartHandler(bot);
            case BACK -> new BackHandler(bot);
            case SCORES_MY, SCORES_HIGH -> new ScoresHandler(bot);
            case RELOAD_STRINGS -> new ReloadStringsHandler(bot);

            default -> new ActionManagerHandler(bot);
        };
    }
}

