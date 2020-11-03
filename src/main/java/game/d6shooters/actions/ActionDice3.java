package game.d6shooters.actions;

import game.d6shooters.DicesCup;
import game.d6shooters.Squad;

public class ActionDice3 implements Action {
    @Override
    public void action(Squad squad, DicesCup dicesCup) {
        int findedGold = dicesCup.getNumberDiceCurrentValue(3) / 3;
        squad.setFood(squad.getGold() + findedGold);
    }
}
