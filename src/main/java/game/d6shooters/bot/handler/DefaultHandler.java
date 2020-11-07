package game.d6shooters.bot.handler;

import org.telegram.telegrambots.meta.api.objects.Message;

public class DefaultHandler extends AbstractHandler {
    @Override
    public void handle(Message message) {
        senderMessage.sendMessage(
                template.getSendMessageOneLineButtons(message.getChatId(),
                        "Команда не распознана, просьба повторить"));
    }
}
