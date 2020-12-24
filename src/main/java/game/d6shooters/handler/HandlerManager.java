package game.d6shooters.handler;

import game.d6shooters.Main;
import game.d6shooters.bot.Bot;
import game.d6shooters.source.Button;
import game.d6shooters.users.User;
import lombok.extern.log4j.Log4j2;
import org.telegram.telegrambots.meta.api.objects.Message;

@Log4j2
public class HandlerManager {
    Handler actionManagerHandler = new ActionManagerHandler();
    Handler backHandler = new BackHandler();
    Handler commandHandler = new CommandHandler();
    Handler eventHandler = new EventHandler();
    Handler helpHandler = new HelpHandler();
    Handler reloadStringsHandler = new ReloadStringsHandler();
    Handler restartHandler = new RestartHandler();
    Handler scoresHandler = new ScoresHandler();
    Handler startGameHandler = new StartGameHandler();
    Handler teamStateHandler = new TeamStateHandler();

    public Handler chooseHandler(Message message) {
        boolean newUser = !Main.users.getUserMap().containsKey(message.getChatId());
        if (newUser) return startGameHandler;

        Button command = Button.getButton(message.getText());
        return switch (command) {
            case BACK -> backHandler;
            case BAND -> teamStateHandler;
            case COMMAND -> commandHandler;
            case EVENT -> eventHandler;
            case HELP_ABOUT, HELP_DICES, HELP_END_GAME, HELP_EVENTS, HELP_MAIN, HELP_TOWN -> helpHandler;
            case RESTART, RESTART2 -> restartHandler;
            case RELOAD_STRINGS -> reloadStringsHandler;
            case SCORES_MY, SCORES_HIGH -> scoresHandler;

            default -> actionManagerHandler;
        };
    }

    public void restartGame(long chatId){
        ((RestartHandler) restartHandler).restartGame(chatId);
    }
}

