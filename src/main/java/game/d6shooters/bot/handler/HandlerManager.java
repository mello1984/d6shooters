package game.d6shooters.bot.handler;

import game.d6shooters.Main;
import game.d6shooters.bot.Bot;
import game.d6shooters.bot.CommandButton;
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

        CommandButton command = CommandButton.getAction(message.getText());
        return switch (command) {
            case BAND -> new TeamStateHandler(bot);
            case COMMAND -> new CommandHandler(bot);
            case EVENT -> new EventHandler(bot);
            case HELP -> new HelpHandler(bot);
            case RESTART, RESTART2 -> new RestartHandler(bot);
            case BACK -> new BackHandler(bot);
            default -> new ActionManagerHandler(bot);
//            default -> new DefaultHandler(bot);
        };

//        if (command == CommandButtons.BAND) return new TeamStateHandler(bot);
//        if (command == CommandButtons.COMMAND) return new CommandHandler(bot);
//        if (command == CommandButtons.HELP) return new TeamStateHandler(bot);
//        return new ActionManagerHandler(bot);
    }
}
