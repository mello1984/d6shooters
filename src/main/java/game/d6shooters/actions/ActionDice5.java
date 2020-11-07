package game.d6shooters.actions;

import game.d6shooters.bot.Bot;
import game.d6shooters.game.DicesCup;
import game.d6shooters.game.Squad;
import game.d6shooters.game.SquadState;
import game.d6shooters.users.User;
import org.telegram.telegrambots.meta.api.objects.Message;

public class ActionDice5 extends AbstractAction {
    private static final String LOSE2FOOD = "Lose 2 food";
    private static final String LOSE1GUNFIGHTER = "lose 1 gunfighter";

    public ActionDice5(Bot bot) {
        super(bot);
    }

    @Override
    public void action(User user) {
        DicesCup dicesCup = user.getDicesCup();
        Squad squad = user.getSquad();
        int dice5count = dicesCup.getCountActiveDiceCurrentValue(5);
        if (dice5count > 0) {
            int roll = DicesCup.getD6Int();
            bot.send(template.getSendMessageOneLineButtons(user.getChatId(),
                    "Экстремальная жара, roll '" + roll + "' из 6.",
                    LOSE2FOOD, LOSE1GUNFIGHTER));
            if (roll < 3) {
                useDice(user, 5);
                user.getActionManager().doActions();
            }
        }
        if (dice5count == 0) {
            System.out.println(SquadState.CHECKHEAT + "->" + SquadState.GUNFIGHT);
            squad.squadState = SquadState.GUNFIGHT;
            user.getActionManager().doActions();
        }
    }

    public void processMessage(User user, Message message) {
        switch (message.getText()) {
            case LOSE2FOOD:
                user.getSquad().addFood(-2);
                useDice(user, 5);
                break;
            case LOSE1GUNFIGHTER:
                user.getSquad().addShooters(-1);
                useDice(user, 5);
                break;
            default:
        }
        user.getActionManager().doActions();
    }
}
