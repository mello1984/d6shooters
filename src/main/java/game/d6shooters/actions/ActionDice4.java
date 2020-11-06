package game.d6shooters.actions;

import game.d6shooters.game.Squad;
import game.d6shooters.game.SquadState;
import game.d6shooters.users.User;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.ArrayList;
import java.util.List;

public class ActionDice4 extends AbstractAction {
    private static final String REJECT = "Ничего";
    private static final String HIDE = "Прятаться";
    private static final String GUNFIGHT = "Отстреливаться";
    private static final String SHELTER = "Укрываться от жары";
    private static final String PATHFINDING = "Искать путь";

    @Override
    public void action(User user) {
        List<String> buttons = getListButtons(user);
        if (buttons.size() > 0) {
            senderMessage.sendMessage(
                    template.getSendMessageOneLineButtons(user.getChatId(),
                            "Необходимо распределить " + user.getDicesCup().getCountActiveDiceCurrentValue(4) + " '4', будьте внимательны",
                            buttons.toArray(new String[0])));
        }
        if (buttons.size() == 0) {
            user.getSquad().squadState = SquadState.OTHER;
            System.out.println(SquadState.ALLOCATE + "->" + SquadState.OTHER);
        }
    }

    private List<String> getListButtons(User user) {
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
        return buttons;
    }

    public void processMessage(User user, Message message) {
        switch (message.getText()) {
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
                user.getSquad().actionList.add(Squad.SquadAction.GUNFIGHT);
                break;
            case PATHFINDING:
                useDice(user, 4);
                useDice(user, 4);
                user.getSquad().actionList.add(Squad.SquadAction.PATHFINDING);
                break;
            case REJECT:
                user.getDicesCup().setUsedDiceCurrentValue(4);
                break;
            default:
                break;
        }

        senderMessage.sendMessage(template.dicesString(user.getChatId(), user.getDicesCup()));
        user.getActionManager().doActions();
    }


}
