package game.d6shooters.actions;

import game.d6shooters.game.Squad;
import game.d6shooters.game.SquadState;
import game.d6shooters.users.User;
import org.telegram.telegrambots.meta.api.objects.Message;

public class ActionManager {
    Action actionDice1 = new ActionDice1();
    Action actionDice2 = new ActionDice2();
    Action actionDice3 = new ActionDice3();
    ActionDice4 actionDice4 = new ActionDice4();
    ActionDice5 actionDice5 = new ActionDice5();
    ActionDice6 actionDice6 = new ActionDice6();
    Action actionFeeding = new ActionFeeding();
    User user;

    public ActionManager(User user) {

        this.user = user;
    }

    public void doActions() {
        Squad squad = user.getSquad();
        if (squad.squadState == SquadState.ALLOCATE) actionDice4.action(user);
        if (squad.squadState == SquadState.OTHER) {
            actionFeeding.action(user);
            actionDice2.action(user);
            actionDice3.action(user);
        }
        if (squad.squadState == SquadState.CHECKHEAT) {
            actionDice5.action(user);
        }
        if (squad.squadState == SquadState.GUNFIGHT) {
            actionDice6.action(user);
        }
        if (squad.squadState == SquadState.MOVE) {
            actionDice1.action(user);
        }
        squad.setPeriod(squad.getPeriod() + 1);
    }

    public void doActions(Message message) {
        Squad squad = user.getSquad();
        if (squad.squadState == SquadState.ALLOCATE) actionDice4.processMessage(user, message);
        else if (squad.squadState == SquadState.CHECKHEAT) actionDice5.processMessage(user, message);
    }
}