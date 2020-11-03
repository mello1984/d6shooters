package game.d6shooters.actions;

import game.d6shooters.*;

import java.util.ArrayList;
import java.util.Arrays;

public class ActionDice4 implements Action {
    TurnMessage turnMessage = Game.turnMessage;

    @Override
    public void action(Squad squad, DicesCup dicesCup) {
        squad.actionList = new ArrayList<>();
        int dice4count = dicesCup.getNumberDiceCurrentValue(4);
        if (dice4count > 0) {
            turnMessage.out("Необходимо распределить " + dice4count + " '4'");
            for (int i = 0; i < dice4count; i++) {
                turnMessage.out(Arrays.toString(Squad.SquadAction.values()));
                int num = Integer.parseInt(turnMessage.get());
                squad.actionList.add(Squad.SquadAction.values()[num]);
            }
        }
        turnMessage.out("Actions: " + squad.actionList);
    }
}
