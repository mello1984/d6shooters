package game.d6shooters.handler;

import game.d6shooters.Main;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class ActionManagerHandler implements Handler {

    @Override
    public void handle(Message message) {
        Main.actionManager.doActions(message);
    }
}
