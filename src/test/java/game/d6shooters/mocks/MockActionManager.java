package game.d6shooters.mocks;

import game.d6shooters.actions.Action;
import game.d6shooters.actions.ActionManager;
import game.d6shooters.bot.Bot;
import game.d6shooters.users.User;
import org.telegram.telegrambots.meta.api.objects.Message;

public class MockActionManager extends ActionManager {
    public MockActionManager(Action actionDice1, Action actionDice2, Action actionDice3, Action actionDice4, Action actionDice5, Action actionDice6, Action actionFeeding, Action actionEvent, Action actionEndGame, Action actionTown, Action actionStartTurn, Action actionPoker) {
        super(actionDice1, actionDice2, actionDice3, actionDice4, actionDice5, actionDice6, actionFeeding, actionEvent, actionEndGame, actionTown, actionStartTurn, actionPoker);
    }

    public void doActions() {
//        System.out.println("MockActionManager.doActions(). " + user.getChatId());
    }



    @Override
    public void doActions(Message message) {
//        System.out.println("MockActionManager.doActions(Message message) "+ user.getChatId());
    }
}
