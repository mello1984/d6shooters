package game.d6shooters.actions;

import game.d6shooters.Main;
import game.d6shooters.game.Squad;
import game.d6shooters.game.SquadState;
import game.d6shooters.source.Button;
import game.d6shooters.source.Text;
import game.d6shooters.users.User;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.ArrayList;
import java.util.List;
public class ActionDice4 extends AbstractAction {

    @Override
    public void action(User user) {
        String[] buttons = getListButtons(user);
        if (buttons.length > 0) {
           Main.bot.send(template.getSendMessageWithButtons(user.getChatId(),
                    Text.getText(Text.ALLOCATE4, user.getDicesCup().getCountActiveDiceCurrentValue(4)),
                    getListButtons(user)));
        }
        if (buttons.length == 0) {
            user.getSquad().setSquadState(SquadState.OTHER);
            Main.actionManager.doActions(user);
        }
    }

    private String[] getListButtons(User user) {
        List<String> buttons = new ArrayList<>();
        int dice4count = user.getDicesCup().getCountActiveDiceCurrentValue(4);
        int dice5count = user.getDicesCup().getCountActiveDiceCurrentValue(5);
        int dice6count = user.getDicesCup().getCountActiveDiceCurrentValue(6);

        if (dice4count > 0) {
            if (dice6count > 0) buttons.add(Button.HIDE.get());
            if (dice6count > 0) buttons.add(Button.GUNFIGHT.get());
            if (dice5count > 0) buttons.add(Button.SHELTER.get());
            if (dice4count >= 2) buttons.add(Button.PATHFINDING.get());
            buttons.add(Button.REJECT.get());
        }
        return buttons.toArray(new String[0]);
    }

    public void processMessage(User user, Message message) {
        allocateDices(user, message.getText());
        Main.bot.send(template.getDicesStringMessage(user.getChatId(), user.getDicesCup()));
        Main.actionManager.doActions(user);
    }

    protected void allocateDices(User user, String text) {
        Button button = Button.getButton(text);
        switch (button) {
            case HIDE -> {
                useDice(user, 4);
                useDice(user, 6);
                if (user.getDicesCup().getCountActiveDiceCurrentValue(6) > 0) useDice(user, 6);
                user.getSquad().addResource(Squad.PERIOD, 1);
                Main.actionManager.checkFeeding(user);
            }
            case SHELTER -> {
                useDice(user, 4);
                useDice(user, 5);
            }
            case GUNFIGHT -> {
                useDice(user, 4);
                user.getSquad().addResource(Squad.GUNFIGHT, 1);
            }
            case PATHFINDING -> {
                useDice(user, 4);
                useDice(user, 4);
                user.getSquad().addResource(Squad.PATHFINDING, 1);
            }
            case REJECT -> user.getDicesCup().setUsedDiceCurrentValue(4);
        }
    }


}
