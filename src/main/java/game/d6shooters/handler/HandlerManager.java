package game.d6shooters.handler;

import game.d6shooters.Main;
import game.d6shooters.source.Button;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Log4j2
@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HandlerManager {
    Handler actionManagerHandler;
    Handler backHandler;
    Handler commandHandler;
    Handler eventHandler;
    Handler helpHandler;
    Handler reloadStringsHandler;
    Handler restartHandler;
    Handler scoresHandler;
    Handler startGameHandler;
    Handler teamStateHandler;

    public HandlerManager(@Autowired @Qualifier("actionManagerHandler") Handler actionManagerHandler,
                          @Autowired @Qualifier("backHandler") Handler backHandler,
                          @Autowired @Qualifier("commandHandler") Handler commandHandler,
                          @Autowired @Qualifier("eventHandler") Handler eventHandler,
                          @Autowired @Qualifier("helpHandler") Handler helpHandler,
                          @Autowired @Qualifier("reloadStringsHandler") Handler reloadStringsHandler,
                          @Autowired @Qualifier("restartHandler") Handler restartHandler,
                          @Autowired @Qualifier("scoresHandler") Handler scoresHandler,
                          @Autowired @Qualifier("startGameHandler") Handler startGameHandler,
                          @Autowired @Qualifier("teamStateHandler") Handler teamStateHandler) {
        this.actionManagerHandler = actionManagerHandler;
        this.backHandler = backHandler;
        this.commandHandler = commandHandler;
        this.eventHandler = eventHandler;
        this.helpHandler = helpHandler;
        this.reloadStringsHandler = reloadStringsHandler;
        this.restartHandler = restartHandler;
        this.scoresHandler = scoresHandler;
        this.startGameHandler = startGameHandler;
        this.teamStateHandler = teamStateHandler;
    }

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

    public void restartGame(long chatId) {
        ((RestartHandler) restartHandler).restartGame(chatId);
    }
}

