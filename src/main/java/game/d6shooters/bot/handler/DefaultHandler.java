package game.d6shooters.bot.handler;

import game.d6shooters.bot.Bot;
import org.telegram.telegrambots.meta.api.objects.Message;

public class DefaultHandler extends AbstractHandler {
    public DefaultHandler(Bot bot) {
        super(bot);
    }

    @Override
    public void handle(Message message) {
        bot.send(
                template.getSendMessageOneLineButtons(message.getChatId(),
                        "Команда не распознана, просьба повторить"));
    }
}
