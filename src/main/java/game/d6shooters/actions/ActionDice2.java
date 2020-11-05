package game.d6shooters.actions;

import game.d6shooters.game.DicesCup;
import game.d6shooters.game.Squad;

public class ActionDice2 implements Action {

    @Override
    public void action(Squad squad, DicesCup dicesCup) {
        int findedFood = dicesCup.getNumberDiceCurrentValue(2) / 2;
        squad.setFood(squad.getFood() + findedFood);
    }
}
