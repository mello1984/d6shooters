package game.d6shooters.actions;

import game.d6shooters.bot.Bot;
import game.d6shooters.game.SquadState;
import game.d6shooters.users.User;
import lombok.extern.log4j.Log4j2;
import org.telegram.telegrambots.meta.api.objects.Message;

@Log4j2
public class ActionStartTurn extends AbstractAction {
    public ActionStartTurn(Bot bot) {
        super(bot);
    }

    @Override
    public void action(User user) {
    } // Always used processMessage method

    public void processMessage(User user, Message message) {
        user.getSquad().setGunfight(0);
        user.getSquad().setPathfinding(0);
        log.debug(user.getSquad());

        switch (user.getSquad().getSquadState().getStep()) {
            case 1 -> step1(user);
            case 2 -> step2(user, message);
            case 3 -> step3(user, message);
        }
    }

    private void step1(User user) {
        user.getDicesCup().getFirstTurnDices();
        bot.send(template.getDicesStringMessage(user.getChatId(), user.getDicesCup()));
        bot.send(template.getSendMessageNoButtons(user.getChatId(), "Введите номера кубиков для переброски или 0"));
        user.getSquad().getSquadState().nextStep();
    }

    private void step2(User user, Message message) {
        if (!checkText(user, message.getText())) return;
        if (message.getText().equals("0")) nextSquadState(user);
        else {
            user.getDicesCup().getRerolledDices(message.getText());
            bot.send(template.getDicesStringMessage(user.getChatId(), user.getDicesCup()));
            bot.send(template.getSendMessageNoButtons(user.getChatId(), "Введите номера кубиков для переброски или 0"));
            user.getSquad().getSquadState().nextStep();
        }
    }

    private void step3(User user, Message message) {
        if (!checkText(user, message.getText())) return;
        if (!message.getText().equals("0")) {
            user.getDicesCup().getRerolledDices(message.getText());
            bot.send(template.getDicesStringMessage(user.getChatId(), user.getDicesCup()));
        }
        nextSquadState(user);
    }

    private boolean checkText(User user, String text) {
        if (!user.getDicesCup().checkString(text)) {
            bot.send(template.getSendMessageNoButtons(user.getChatId(), "Некорректные данные, введите номера кубиков для переброски или 0"));
            return false;
        }
        return true;
    }

    private void nextSquadState(User user) {
        user.getSquad().getSquadState().resetStep();
        user.getSquad().setSquadState(SquadState.ALLOCATE);
        user.getActionManager().doActions();
    }
}