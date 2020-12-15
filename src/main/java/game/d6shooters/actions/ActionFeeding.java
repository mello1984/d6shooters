package game.d6shooters.actions;

import game.d6shooters.bot.Bot;
import game.d6shooters.game.Squad;
import game.d6shooters.users.User;

public class ActionFeeding extends AbstractAction {
    private static final String TEXT1 = "Сегодня %d день нашего путешествия. Устроили привал, съели %d еды.";

    public ActionFeeding(Bot bot) {
        super(bot);
    }

    @Override
    public void action(User user) {
        Squad squad = user.getSquad();
        if (squad.getResource(Squad.PERIOD) % 5 == 0 && squad.getResource(Squad.PERIOD) < 40 && squad.getResource(Squad.PERIOD) > 0) {
            if (squad.getResource(Squad.FOOD) >= squad.getResource(Squad.SHOOTER)) {
                squad.addResource(Squad.FOOD, -squad.getResource(Squad.SHOOTER));
                bot.send(template.getSendMessageWithButtons(user.getChatId(), String.format(TEXT1, squad.getResource(Squad.PERIOD), squad.getResource(Squad.SHOOTER))));
            } else {
                squad.setResource(Squad.SHOOTER,squad.getResource(Squad.FOOD));
                squad.setResource(Squad.FOOD, 0);
                //END GAME
            }
        }
    }
}
