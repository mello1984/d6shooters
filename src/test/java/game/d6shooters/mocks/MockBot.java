package game.d6shooters.mocks;

import game.d6shooters.bot.Bot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class MockBot extends Bot {
    @Override
    public void send(SendMessage sendMessage) {
    }
}
