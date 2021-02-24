package game.d6shooters.actions;

import game.d6shooters.Main;
import game.d6shooters.game.Dice;
import game.d6shooters.game.Squad;
import game.d6shooters.source.Text;
import game.d6shooters.users.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class ActionDice2 extends AbstractAction {

    @Override
    public void action(User user) {
        log.info(String.format("Start ActionDice2.action, user = %d", user.getChatId()));
        int foundFood = getFoundFood(user);
        if (foundFood > 0) {
            user.getSquad().addResource(Squad.FOOD, foundFood);
            Main.bot.send(template.getSendMessageWithButtons(user.getChatId(), String.format(Text.getText(Text.DICE2HUNT), foundFood, user.getSquad().getResource(Squad.FOOD))));
        }
    }

    protected int getFoundFood(User user) {
        int red = user.getDicesCup().getCountActiveDiceCurrentValue(2, Dice.DiceType.RED);
        int white = user.getDicesCup().getCountActiveDiceCurrentValue(2, Dice.DiceType.WHITE);
        user.getDicesCup().setUsedDiceCurrentValue(2);
        return (white + red * (user.getSquad().hasResource(Squad.HUNTER) ? 2 : 1)) / 2;
    }
}
