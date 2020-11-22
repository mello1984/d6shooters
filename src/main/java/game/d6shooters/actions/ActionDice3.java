package game.d6shooters.actions;

import game.d6shooters.bot.Bot;
import game.d6shooters.game.SquadState;
import game.d6shooters.users.User;

public class ActionDice3 extends AbstractAction {
    public ActionDice3(Bot bot) {
        super(bot);
    }

    @Override
    public void action(User user) {
        int foundGold = user.getDicesCup().getCountActiveDiceCurrentValue(3) / 3;
        if (foundGold > 0) {
            user.getSquad().addGold(foundGold);
            bot.send(template.getSendMessageWithButtons(user.getChatId(),
                    "На рудниках добыли " + foundGold + " золота"));
        }
        user.getDicesCup().setUsedDiceCurrentValue(3);
        user.getSquad().setSquadState(SquadState.CHECKHEAT);
        user.getActionManager().doActions();
    }
}
