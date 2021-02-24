package game.d6shooters.actions;

import game.d6shooters.Main;
import game.d6shooters.users.User;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ActionManager {
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

    public ActionManager(@Autowired @Qualifier("actionDice1") Action actionDice1,
                         @Autowired @Qualifier("actionDice2") Action actionDice2,
                         @Autowired @Qualifier("actionDice3") Action actionDice3,
                         @Autowired @Qualifier("actionDice4") Action actionDice4,
                         @Autowired @Qualifier("actionDice5") Action actionDice5,
                         @Autowired @Qualifier("actionDice6") Action actionDice6,
                         @Autowired @Qualifier("actionFeeding") Action actionFeeding,
                         @Autowired @Qualifier("actionEvent") Action actionEvent,
                         @Autowired @Qualifier("actionEndGame") Action actionEndGame,
                         @Autowired @Qualifier("actionTown") Action actionTown,
                         @Autowired @Qualifier("actionStartTurn") Action actionStartTurn,
                         @Autowired @Qualifier("actionPoker") Action actionPoker) {
        this.actionDice1 = actionDice1;
        this.actionDice2 = actionDice2;
        this.actionDice3 = actionDice3;
        this.actionDice4 = actionDice4;
        this.actionDice5 = actionDice5;
        this.actionDice6 = actionDice6;
        this.actionFeeding = actionFeeding;
        this.actionEvent = actionEvent;
        this.actionEndGame = actionEndGame;
        this.actionTown = actionTown;
        this.actionStartTurn = actionStartTurn;
        this.actionPoker = actionPoker;
    }

    public void doActions(User user) {
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
        User user = Main.users.getUserMap().get(message.getChatId());
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

    public void checkFeeding(User user) {
        ((ActionFeeding) actionFeeding).checkFeeding(user);
    }
}