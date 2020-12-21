package game.d6shooters.actions;

import game.d6shooters.users.User;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface Action  {

    void action(User user);

    void processMessage(User user, Message message);
}
