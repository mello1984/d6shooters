package game.d6shooters.actions;

import game.d6shooters.bot.Bot;
import game.d6shooters.game.Dice;
import game.d6shooters.users.User;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;


public class ActionDice2 extends AbstractAction {
    private static final String TEXT1 = "На охоте добыли %d провизии";

    public ActionDice2(Bot bot) {
        super(bot);
    }

    public ActionDice2(Bot bot, Action next) {
        super(bot, next);
    }

    @Override
    public void action(User user) {
        int foundFood = getFoundFood(user);
        if (foundFood > 0) {
            user.getSquad().addFood(foundFood);
            bot.send(template.getSendMessageWithButtons(user.getChatId(), String.format(TEXT1, foundFood)));
        }
    }

    protected int getFoundFood(User user) {
        int red = (int) user.getDicesCup().getDiceList().stream().filter(d -> d.getValue() == 2 && !d.isUsed() && d.getType() == Dice.DiceType.RED).count();
        int white = (int) user.getDicesCup().getDiceList().stream().filter(d -> d.getValue() == 2 && !d.isUsed() && d.getType() == Dice.DiceType.WHITE).count();
        user.getDicesCup().setUsedDiceCurrentValue(2);
        return (white + red * (user.getSquad().isHunter() ? 2 : 1)) / 2;
    }
}
