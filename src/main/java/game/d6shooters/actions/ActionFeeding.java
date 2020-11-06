package game.d6shooters.actions;

import game.d6shooters.game.DicesCup;
import game.d6shooters.game.Squad;
import game.d6shooters.game.SquadState;
import game.d6shooters.users.User;

public class ActionFeeding implements Action {
    @Override
    public void action(User user) {
        DicesCup dicesCup = user.getDicesCup();
        Squad squad = user.getSquad();
        if (squad.squadState != SquadState.OTHER) return;
        if (squad.getPeriod() % 5 == 0 && squad.getPeriod() < 40 && squad.getPeriod() > 0) {
            if (squad.getFood() == 0) {
                //END GAME
            } else if (squad.getFood() >= squad.getShooters()) {
                squad.setFood(squad.getFood() - squad.getShooters());
            } else {
                squad.setShooters(squad.getShooters() - squad.getFood());
                squad.setFood(0);
            }
        }

    }
}
