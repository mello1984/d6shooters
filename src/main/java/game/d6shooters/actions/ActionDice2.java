package game.d6shooters.actions;

import game.d6shooters.bot.D6ShootersBot;
import game.d6shooters.game.Dice;
import game.d6shooters.game.DicesCup;
import game.d6shooters.game.Squad;
import game.d6shooters.game.SquadState;
import game.d6shooters.users.User;

public class ActionDice2 extends AbstractAction {

    @Override
    public void action(User user) {
        DicesCup dicesCup = user.getDicesCup();
        Squad squad = user.getSquad();
        if (squad.squadState != SquadState.OTHER) return;

        int findedFood = dicesCup.getNumberDiceCurrentValue(2) / 2;
        if (findedFood > 0) {
            squad.addFood(findedFood);
            D6ShootersBot.senderMessage.sendText(user.getChatId(), "На охоте добыли " + findedFood + " провизии");
        }
        dicesCup.diceList.stream().filter(dice -> dice.getValue() == 2).forEach(dice -> dice.setUsed(true));
    }
}
