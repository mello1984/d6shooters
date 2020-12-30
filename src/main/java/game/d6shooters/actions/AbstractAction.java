package game.d6shooters.actions;

import game.d6shooters.bot.SendMessageTemplate;
import game.d6shooters.users.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.Message;

@Log4j2
public abstract class AbstractAction implements Action {
    @Autowired
    protected SendMessageTemplate template;

    @Override
    public void action(User user) {
    }

    @Override
    public void processMessage(User user, Message message) {
    }

    public SendMessageTemplate getTemplate() {
        return template;
    }

    void useDice(User user, int value) {
        user.getDicesCup().getDiceList().stream()
                .filter(dice -> dice.getValue() == value && !dice.isUsed())
                .findFirst().orElseThrow(() -> new IllegalArgumentException("No '" + value + "' in stream allocation"))
                .setUsed(true);
    }
}
