package game.d6shooters.mocks;

import org.telegram.telegrambots.meta.api.objects.Message;

public class MockMessage extends Message {
    String text;

    public MockMessage(String text) {
        this.text = text;
    }

    @Override
    public String getText() {
        return text;
    }
}
