package game.d6shooters.actions;

import game.d6shooters.game.SquadState;
import game.d6shooters.users.User;
import org.telegram.telegrambots.meta.api.objects.Message;

public class ActionManager {
    private final Action actionDice1 = new ActionDice1();
    private final Action actionDice2 = new ActionDice2();
    private final Action actionDice3 = new ActionDice3();
    private final ActionDice4 actionDice4 = new ActionDice4();
    private final ActionDice5 actionDice5 = new ActionDice5();
    private final ActionDice6 actionDice6 = new ActionDice6();
    private final Action actionFeeding = new ActionFeeding();
    private final User user;

    public ActionManager(User user) {
        this.user = user;
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
        user.getSquad().addPeriod(1);
    }

    public void doActions(Message message) {
        if (user.getSquad().squadState == SquadState.ALLOCATE) actionDice4.processMessage(user, message);
        else if (user.getSquad().squadState == SquadState.CHECKHEAT) actionDice5.processMessage(user, message);
    }
}