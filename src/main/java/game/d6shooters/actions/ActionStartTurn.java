package game.d6shooters.actions;

import game.d6shooters.Main;
import game.d6shooters.game.Squad;
import game.d6shooters.game.SquadState;
import game.d6shooters.source.Text;
import game.d6shooters.users.User;
import lombok.extern.log4j.Log4j2;
import org.telegram.telegrambots.meta.api.objects.Message;

@Log4j2
public class ActionStartTurn extends AbstractAction {

    public void processMessage(User user, Message message) {
        user.getSquad().setResource(Squad.GUNFIGHT, 0);
        user.getSquad().setResource(Squad.PATHFINDING, 0);
        log.debug(user.getSquad());
        switch (user.getSquad().getSquadState()) {
            case STARTTURN1 -> step1(user);
            case STARTTURN2 -> step2(user, message);
            case STARTTURN3 -> step3(user, message);
        }
    }

    private void step1(User user) {
        user.getDicesCup().getFirstTurnDices();
        Main.bot.send(template.getDicesStringMessage(user.getChatId(), user.getDicesCup()));
        Main.bot.send(template.getSendMessageNoButtons(user.getChatId(), Text.getText(Text.REROLL_DICES)));
        user.getSquad().setSquadState(SquadState.STARTTURN2);
    }

    private void step2(User user, Message message) {
        if (!checkText(user, message.getText())) return;
        if (message.getText().equals("0")) nextSquadState(user);
        else {
            user.getDicesCup().getRerolledDices(message.getText());
            Main.bot.send(template.getDicesStringMessage(user.getChatId(), user.getDicesCup()));
            Main.bot.send(template.getSendMessageNoButtons(user.getChatId(), Text.getText(Text.REROLL_DICES)));
            user.getSquad().setSquadState(SquadState.STARTTURN3);
        }
    }

    private void step3(User user, Message message) {
        if (!checkText(user, message.getText())) return;
        if (!message.getText().equals("0")) {
            user.getDicesCup().getRerolledDices(message.getText());
            Main.bot.send(template.getDicesStringMessage(user.getChatId(), user.getDicesCup()));
        }
        nextSquadState(user);
    }

    private boolean checkText(User user, String text) {
        if (!user.getDicesCup().checkString(text, false)) {
            Main.bot.send(template.getSendMessageNoButtons(user.getChatId(), Text.getText(Text.UNKNOWN_COMMAND)));
            return false;
        }
        return true;
    }

    private void nextSquadState(User user) {
        user.getSquad().setSquadState(SquadState.ALLOCATE);
        Main.actionManager.doActions(user);
    }
}