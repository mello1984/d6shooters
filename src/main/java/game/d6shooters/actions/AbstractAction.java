package game.d6shooters.actions;

import game.d6shooters.bot.Bot;
import game.d6shooters.bot.SendMessageTemplate;
import game.d6shooters.users.User;
import lombok.extern.log4j.Log4j2;

@Log4j2
public abstract class AbstractAction implements Action {
    SendMessageTemplate template = new SendMessageTemplate();
    Bot bot;

    public AbstractAction(Bot bot) {
        this.bot = bot;
    }

    void useDice(User user, int value) {
        user.getDicesCup().getDiceList().stream()
                .filter(dice -> dice.getValue() == value && !dice.isUsed())
                .findFirst().orElseThrow(() -> new IllegalArgumentException("No '" + value + "' in stream allocation"))
                .setUsed(true);
    }
}
