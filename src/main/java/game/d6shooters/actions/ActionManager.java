package game.d6shooters.actions;

import game.d6shooters.bot.Bot;
import game.d6shooters.users.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.Serializable;

@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ActionManager implements Serializable {
    Action actionDice1;
    Action actionDice2;
    Action actionDice3;
    Action actionDice4;
    Action actionDice5;
    Action actionDice6;
    Action actionFeeding;
    Action actionEvent;
    Action actionEndGame;
    Action actionTown;
    Action actionStartTurn;
    Action actionPoker;
    User user;

    public ActionManager(User user) {
        this.user = user;
        actionDice1 = new ActionDice1();
        actionDice2 = new ActionDice2();
        actionDice3 = new ActionDice3();
        actionDice4 = new ActionDice4();
        actionDice5 = new ActionDice5();
        actionDice6 = new ActionDice6();
        actionFeeding = new ActionFeeding();
        actionEvent = new ActionEvent();
        actionEndGame = new ActionEndGame();
        actionTown = new ActionTown();
        actionStartTurn = new ActionStartTurn();
        actionPoker = new ActionPoker();
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
            case STARTTURN1, STARTTURN2, STARTTURN3 -> actionStartTurn.action(user);
            case POKER1 -> actionPoker.action(user);

        }

    }

    public void doActions(Message message) {
        switch (user.getSquad().getSquadState()) {
            case STARTTURN1, STARTTURN2, STARTTURN3 -> actionStartTurn.processMessage(user, message);
            case ALLOCATE -> actionDice4.processMessage(user, message);
            case CHECKHEAT -> actionDice5.processMessage(user, message);
            case CROSSROAD -> actionDice1.processMessage(user, message);
            case EVENT2, EVENT3, EVENT6 -> actionEvent.processMessage(user, message);
            case TOWN -> actionTown.processMessage(user, message);
            case GUNFIGHT -> actionDice6.processMessage(user, message);
            case POKER2, POKER3, POKER4 -> actionPoker.processMessage(user, message);
        }

    }
}