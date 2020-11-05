package game.d6shooters.actions;

import game.d6shooters.bot.D6ShootersBot;
import game.d6shooters.game.DicesCup;
import game.d6shooters.game.Squad;
import game.d6shooters.users.User;

public class ActionDice3 implements Action {
    @Override
    public void action(User user) {
        DicesCup dicesCup = user.getDicesCup();
        Squad squad = user.getSquad();
        int findedGold = dicesCup.getNumberDiceCurrentValue(3) / 3;
        if (findedGold > 0) {
            squad.addGold(findedGold);
            D6ShootersBot.senderMessage.sendText(user.getChatId(), "На рудниках добыли " + findedGold + " золота");
        }
        dicesCup.diceList.stream().filter(dice -> dice.getValue() == 3).forEach(dice -> dice.setUsed(true));
    }
}
