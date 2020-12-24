package game.d6shooters.actions;

import game.d6shooters.Main;
import game.d6shooters.game.Dice;
import game.d6shooters.game.Squad;
import game.d6shooters.source.Text;
import game.d6shooters.users.User;

public class ActionDice2 extends AbstractAction {

    @Override
    public void action(User user) {
        int foundFood = getFoundFood(user);
        if (foundFood > 0) {
            user.getSquad().addResource(Squad.FOOD, foundFood);
            Main.bot.send(template.getSendMessageWithButtons(user.getChatId(), String.format(Text.getText(Text.DICE2HUNT), foundFood)));
        }
    }

    protected int getFoundFood(User user) {
        int red = (int) user.getDicesCup().getDiceList().stream().filter(d -> d.getValue() == 2 && !d.isUsed() && d.getType() == Dice.DiceType.RED).count();
        int white = (int) user.getDicesCup().getDiceList().stream().filter(d -> d.getValue() == 2 && !d.isUsed() && d.getType() == Dice.DiceType.WHITE).count();
        user.getDicesCup().setUsedDiceCurrentValue(2);
        return (white + red * (user.getSquad().hasResource(Squad.HUNTER) ? 2 : 1)) / 2;
    }
}
