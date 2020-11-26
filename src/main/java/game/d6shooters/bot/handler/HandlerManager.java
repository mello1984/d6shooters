package game.d6shooters.bot.handler;

import game.d6shooters.Main;
import game.d6shooters.bot.Bot;
import game.d6shooters.bot.CommandButton;
import lombok.extern.log4j.Log4j2;
import org.telegram.telegrambots.meta.api.objects.Message;

@Log4j2
public class HandlerManager {
    private final Bot bot;
    Handler startGameHandler;
    Handler teamStateHandler;
    Handler commandHandler;
    Handler eventHandler;
    Handler helpHandler;
    Handler restartHandler;
    Handler backHandler;
    Handler actionManagerHandler;


    public HandlerManager(Bot bot) {
        this.bot = bot;
        startGameHandler = new StartGameHandler(bot);
        teamStateHandler = new TeamStateHandler(bot);
        commandHandler = new CommandHandler(bot);
        eventHandler = new EventHandler(bot);
        helpHandler = new HelpHandler(bot);
        restartHandler = new RestartHandler(bot);
        backHandler = new BackHandler(bot);
        actionManagerHandler = new ActionManagerHandler(bot);

        startGameHandler.setNextHandler(teamStateHandler);
        teamStateHandler.setNextHandler(commandHandler);
        commandHandler.setNextHandler(eventHandler);
        eventHandler.setNextHandler(helpHandler);
        helpHandler.setNextHandler(restartHandler);
        restartHandler.setNextHandler(backHandler);
        backHandler.setNextHandler(actionManagerHandler);
        actionManagerHandler.setNextHandler(startGameHandler);
    }

    public Handler getStartHandler(Message message) {
        return startGameHandler;
//        if (!Main.users.userMap.containsKey(message.getChatId())) return new StartGameHandler(bot);
//        CommandButton command = CommandButton.getAction(message.getText());
//        return switch (command) {
//            case BAND -> new TeamStateHandler(bot);
//            case COMMAND -> new CommandHandler(bot);
//            case EVENT -> new EventHandler(bot);
//            case HELP -> new HelpHandler(bot);
//            case RESTART, RESTART2 -> new RestartHandler(bot);
//            case BACK -> new BackHandler(bot);
//            default -> new ActionManagerHandler(bot);
//        };
    }
}
