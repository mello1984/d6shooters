package game.d6shooters.actions;

import game.d6shooters.Main;
import game.d6shooters.users.User;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.telegram.telegrambots.meta.api.objects.Message;


@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ActionManager {
    Action actionDice1 = new ActionDice1();
    Action actionDice2 = new ActionDice2();
    Action actionDice3 = new ActionDice3();
    Action actionDice4 = new ActionDice4();
    Action actionDice5 = new ActionDice5();
    Action actionDice6 = new ActionDice6();
    Action actionFeeding = new ActionFeeding();
    Action actionEvent = new ActionEvent();
    Action actionEndGame = new ActionEndGame();
    Action actionTown = new ActionTown();
    Action actionStartTurn = new ActionStartTurn();
    Action actionPoker = new ActionPoker();

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
}