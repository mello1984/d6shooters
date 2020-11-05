package game.d6shooters.actions;

import game.d6shooters.bot.ReceiverMessage;
import game.d6shooters.bot.SenderMessage;
import game.d6shooters.game.DicesCup;
import game.d6shooters.game.Game;
import game.d6shooters.game.Squad;

import java.util.ArrayList;
import java.util.Arrays;

public class ActionDice4 implements Action {
    SenderMessage senderMessage = Game.senderMessage;
    ReceiverMessage receiverMessage = Game.receiverMessage;

    @Override
    public void action(Squad squad, DicesCup dicesCup) {
        squad.actionList = new ArrayList<>();
        int dice4count = dicesCup.getNumberDiceCurrentValue(4);
        if (dice4count > 0) {
            senderMessage.sendText(0L,"Необходимо распределить " + dice4count + " '4'"); //need 2 '4' for PATHFINDING
            for (int i = 0; i < dice4count; i++) {
                senderMessage.sendText(0l, Arrays.toString(Squad.SquadAction.values()));
                int num = Integer.parseInt(receiverMessage.get());
                Squad.SquadAction action = Squad.SquadAction.values()[num - 1];
                squad.actionList.add(action);
            }
        }
        senderMessage.sendText(0L,"Actions: " + squad.actionList);
    }
}
