package game.d6shooters.actions;

import game.d6shooters.bot.Bot;
import game.d6shooters.game.SquadState;
import game.d6shooters.users.User;
import org.telegram.telegrambots.meta.api.objects.Message;

public class ActionManager {
    private final Action actionDice1;
    private final Action actionDice2;
    private final Action actionDice3;
    private final ActionDice4 actionDice4;
    private final ActionDice5 actionDice5;
    private final ActionDice6 actionDice6;
    private final Action actionFeeding;
    private final User user;

    public ActionManager(User user, Bot bot) {
        this.user = user;
        actionDice1 = new ActionDice1(bot);
        actionDice2 = new ActionDice2(bot);
        actionDice3 = new ActionDice3(bot);
        actionDice4 = new ActionDice4(bot);
        actionDice5 = new ActionDice5(bot);
        actionDice6 = new ActionDice6(bot);
        actionFeeding = new ActionFeeding(bot);
    }

    public void doActions() {
        switch (user.getSquad().squadState) {
            case ALLOCATE -> actionDice4.action(user);
            case CHECKHEAT -> actionDice5.action(user);
            case GUNFIGHT -> actionDice6.action(user);
            case MOVE -> actionDice1.action(user);
            case OTHER -> {
                actionFeeding.action(user);
                actionDice2.action(user);
                actionDice3.action(user);
            }
        }

    }

    public void doActions(Message message) {
        if (user.getSquad().squadState == SquadState.ALLOCATE) actionDice4.processMessage(user, message);
        else if (user.getSquad().squadState == SquadState.CHECKHEAT) actionDice5.processMessage(user, message);
    }
}