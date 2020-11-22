package game.d6shooters.actions;

import game.d6shooters.bot.Bot;
import game.d6shooters.game.Squad;
import game.d6shooters.users.User;

public class ActionFeeding extends AbstractAction {
    public ActionFeeding(Bot bot) {
        super(bot);
    }

    @Override
    public void action(User user) {
        Squad squad = user.getSquad();
        if (squad.getPeriod() % 5 == 0 && squad.getPeriod() < 40 && squad.getPeriod() > 0) {
            if (squad.getFood() >= squad.getShooters()) {
                squad.addFood(-squad.getShooters());
                bot.send(template.getSendMessageWithButtons(user.getChatId(),
                        "Съели " + squad.getShooters() + " еды."));
            } else {
                squad.setShooters(squad.getShooters() - squad.getFood());
                squad.setFood(0);
                //END GAME
            }
        }
    }
}
