package game.d6shooters.actions;

import game.d6shooters.game.DicesCup;
import game.d6shooters.game.Squad;
import game.d6shooters.users.User;

public class ActionDice1 implements Action {

    @Override
    public void action(User user) {
        DicesCup dicesCup = user.getDicesCup();
        Squad squad = user.getSquad();
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
