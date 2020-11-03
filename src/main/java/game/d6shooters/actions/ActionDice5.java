package game.d6shooters.actions;

import game.d6shooters.DicesCup;
import game.d6shooters.Game;
import game.d6shooters.Squad;
import game.d6shooters.TurnMessage;

public class ActionDice5 implements Action {
    TurnMessage turnMessage = Game.turnMessage;

    @Override
    public void action(Squad squad, DicesCup dicesCup) {
        int dice5 = dicesCup.getNumberDiceCurrentValue(5);
        int squadShelter = (int) squad.actionList.stream().filter(a -> a == Squad.SquadAction.SHELTER).count();
        for (int i = 0; i < dice5 - squadShelter; i++) {
            int roll = DicesCup.getD6Int();
            if (roll >= 3) {
                turnMessage.out("Выберите, что потерять:\n" +
                        "1: 2 еды\n" +
                        "2: 1 член отряда");
                int j = Integer.parseInt(turnMessage.get());

                if (j == 1) squad.setFood(squad.getFood() - 2);
                else squad.setShooters(squad.getShooters() - 1);
            }
        }
    }
}
