package game.d6shooters.actions;

import game.d6shooters.Main;
import game.d6shooters.game.Squad;
import game.d6shooters.game.SquadState;
import game.d6shooters.source.Text;
import game.d6shooters.users.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class ActionDice3 extends AbstractAction {

    @Override
    public void action(User user) {
        int foundGold = getFoundGold(user);
        if (foundGold > 0) {
            user.getSquad().addResource(Squad.GOLD, foundGold);
            Main.bot.send(template.getSendMessageWithButtons(user.getChatId(), String.format(Text.getText(Text.DICE3GOLD), foundGold)));
        }
        user.getDicesCup().setUsedDiceCurrentValue(3);
        user.getSquad().setSquadState(SquadState.CHECKHEAT);
        Main.actionManager.doActions(user);
    }

    protected int getFoundGold(User user) {
        int divisor = user.getSquad().hasResource(Squad.MAP) ? 2 : 3;
        return user.getDicesCup().getCountActiveDiceCurrentValue(3) / divisor;
    }
}
