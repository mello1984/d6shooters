package game.d6shooters.actions;

import game.d6shooters.bot.Bot;
import game.d6shooters.users.User;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.telegram.telegrambots.meta.api.objects.Message;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ActionManager {
    ActionDice1 actionDice1;
    ActionDice2 actionDice2;
    ActionDice3 actionDice3;
    ActionDice4 actionDice4;
    ActionDice5 actionDice5;
    ActionDice6 actionDice6;
    ActionFeeding actionFeeding;
    ActionEvent actionEvent;
    ActionEndGame actionEndGame;
    ActionTown actionTown;
    ActionStartTurn actionStartTurn;
    ActionPoker actionPoker;
    protected final User user;

    public ActionManager(User user, Bot bot) {
        this.user = user;
        actionDice1 = new ActionDice1(bot);
        actionDice2 = new ActionDice2(bot);
        actionDice3 = new ActionDice3(bot);
        actionDice4 = new ActionDice4(bot);
        actionDice5 = new ActionDice5(bot);
        actionDice6 = new ActionDice6(bot);
        actionFeeding = new ActionFeeding(bot);
        actionEvent = new ActionEvent(bot);
        actionEndGame = new ActionEndGame(bot);
        actionTown = new ActionTown(bot);
        actionStartTurn = new ActionStartTurn(bot);
        actionPoker = new ActionPoker(bot);
    }

    public void doActions() {
        switch (user.getSquad().getSquadState()) {
            case ALLOCATE -> actionDice4.action(user);
            case CHECKHEAT -> actionDice5.action(user);
            case GUNFIGHT -> actionDice6.action(user);
            case MOVE -> actionDice1.action(user);
            case TOWN -> actionTown.action(user);
            case OTHER -> {
                actionFeeding.action(user);
                actionDice2.action(user);
                actionDice3.action(user);
            }
            case EVENT -> actionEvent.action(user);
            case ENDGAME -> actionEndGame.action(user);
//            case STARTTURN -> actionStartTurn.action(user);
            case STARTTURN1, STARTTURN2, STARTTURN3 -> actionStartTurn.action(user);
            case POKER1 -> actionPoker.action(user);

        }

    }

    public void doActions(Message message) {
        switch (user.getSquad().getSquadState()) {
//            case STARTTURN -> actionStartTurn.processMessage(user, message);
            case STARTTURN1, STARTTURN2, STARTTURN3 -> actionStartTurn.processMessage(user, message);
            case ALLOCATE -> actionDice4.processMessage(user, message);
            case CHECKHEAT -> actionDice5.processMessage(user, message);
            case CROSSROAD -> actionDice1.processMessage(user, message);
            case EVENT2, EVENT3, EVENT6 -> actionEvent.processMessage(user, message);
            case TOWN -> actionTown.processMessage(user, message);
            case POKER2, POKER3, POKER4 -> actionPoker.processMessage(user, message);
        }

    }
}