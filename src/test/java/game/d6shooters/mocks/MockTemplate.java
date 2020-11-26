package game.d6shooters.mocks;

import game.d6shooters.bot.SendMessageTemplate;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class MockTemplate extends SendMessageTemplate {

    public MockTemplate() {

    }

    @Override
    public SendMessage getSendMessageWithButtons(Long chatId, String text, String... oneLineButtons) {
        return new SendMessage(chatId, text);
    }
}
