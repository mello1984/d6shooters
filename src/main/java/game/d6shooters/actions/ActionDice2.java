package game.d6shooters.actions;

import game.d6shooters.bot.Bot;
import game.d6shooters.users.User;

public class ActionDice2 extends AbstractAction {

    public ActionDice2(Bot bot) {
        super(bot);
    }

    @Override
    public void action(User user) {
        int foundFood = user.getDicesCup().getCountActiveDiceCurrentValue(2) / 2;
        if (foundFood > 0) {
            user.getSquad().addFood(foundFood);
            bot.send(template.getSendMessageWithButtons(user.getChatId(),
                    "На охоте добыли " + foundFood + " провизии"));
        }
        user.getDicesCup().setUsedDiceCurrentValue(2);
    }
}
