package game.d6shooters.actions;

import game.d6shooters.Main;
import game.d6shooters.bot.Bot;
import game.d6shooters.bot.SendMessageTemplate;
import game.d6shooters.users.User;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.Serializable;

@Log4j2
public abstract class AbstractAction implements Action, Serializable {
    transient SendMessageTemplate template;
    transient Bot bot;

    public AbstractAction() {
        template = new SendMessageTemplate();
        bot = Main.bot;
    }

    @Override
    public void action(User user) {
    }

    @Override
    public void processMessage(User user, Message message) {
    }

    void useDice(User user, int value) {
        user.getDicesCup().getDiceList().stream()
                .filter(dice -> dice.getValue() == value && !dice.isUsed())
                .findFirst().orElseThrow(() -> new IllegalArgumentException("No '" + value + "' in stream allocation"))
                .setUsed(true);
    }
}
