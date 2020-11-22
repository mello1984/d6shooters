package game.d6shooters.actions;

import game.d6shooters.bot.Bot;
import game.d6shooters.game.SquadState;
import game.d6shooters.users.User;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.ArrayList;
import java.util.List;

public class ActionDice4 extends AbstractAction {
    protected static final String REJECT = "Ничего";
    protected static final String HIDE = "Прятаться";
    protected static final String GUNFIGHT = "Отстреливаться";
    protected static final String SHELTER = "Укрываться от жары";
    protected static final String PATHFINDING = "Искать путь";

    public ActionDice4(Bot bot) {
        super(bot);
    }

    @Override
    public void action(User user) {
        String[] buttons = getListButtons(user);
        if (buttons.length > 0) {
            bot.send(template.getSendMessageWithButtons(user.getChatId(),
                    "Необходимо распределить " + user.getDicesCup().getCountActiveDiceCurrentValue(4) + " '4', будьте внимательны",
                    getListButtons(user)));
        }
        if (buttons.length == 0) {
            user.getSquad().setSquadState(SquadState.OTHER);
            user.getActionManager().doActions();
        }
    }

    private String[] getListButtons(User user) {
        List<String> buttons = new ArrayList<>();
        int dice4count = user.getDicesCup().getCountActiveDiceCurrentValue(4);
        int dice5count = user.getDicesCup().getCountActiveDiceCurrentValue(5);
        int dice6count = user.getDicesCup().getCountActiveDiceCurrentValue(6);

        if (dice4count > 0) {
            if (dice6count > 0) buttons.add(HIDE);
            if (dice6count > 0) buttons.add(GUNFIGHT);
            if (dice5count > 0) buttons.add(SHELTER);
            if (dice4count >= 2) buttons.add(PATHFINDING);
            buttons.add(REJECT);
        }
        return buttons.toArray(new String[0]);
    }

    public void processMessage(User user, Message message) {
        allocateDices(user, message.getText());
        bot.send(template.getDicesStringMessage(user.getChatId(), user.getDicesCup()));
        user.getActionManager().doActions();
    }

    protected void allocateDices(User user, String text) {
        switch (text) {
            case HIDE:
                useDice(user, 4);
                useDice(user, 6);
                if (user.getDicesCup().getCountActiveDiceCurrentValue(6) > 0) useDice(user, 6);
                user.getSquad().addPeriod(1);
                break;
            case SHELTER:
                useDice(user, 4);
                useDice(user, 5);
                break;
            case GUNFIGHT:
                useDice(user, 4);
                user.getSquad().addGunfight(1);
                break;
            case PATHFINDING:
                useDice(user, 4);
                useDice(user, 4);
                user.getSquad().addPathfinding(1);
                break;
            case REJECT:
                user.getDicesCup().setUsedDiceCurrentValue(4);
                break;
            default:
                break;
        }
    }


}
