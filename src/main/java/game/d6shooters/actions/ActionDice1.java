package game.d6shooters.actions;

import game.d6shooters.game.DicesCup;
import game.d6shooters.game.Squad;

public class ActionDice1 implements Action {

    @Override
    public void action(Squad squad, DicesCup dicesCup) {
        int dice1 = dicesCup.getNumberDiceCurrentValue(1);
        int pathfinding = (int) squad.actionList.stream().filter(a -> a == Squad.SquadAction.PATHFINDING).count();
        int distance = dice1 + pathfinding;
        while (distance > 0) {
            distance--;
            squad.setPath(squad.getPath() + 1);
            squad.road.next();
        }
    }
}
