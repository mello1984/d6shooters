package game.d6shooters.actions;

import game.d6shooters.bot.ReceiverMessage;
import game.d6shooters.game.DicesCup;
import game.d6shooters.game.Game;
import game.d6shooters.game.Squad;
import game.d6shooters.bot.SenderMessage;
import game.d6shooters.users.User;

public class ActionDice5 implements Action {
    SenderMessage senderMessage = Game.senderMessage;
    ReceiverMessage receiverMessage = Game.receiverMessage;

    @Override
    public void action(User user) {
        DicesCup dicesCup = user.getDicesCup();
        Squad squad = user.getSquad();
        int dice5 = dicesCup.getNumberDiceCurrentValue(5);
        int squadShelter = (int) squad.actionList.stream().filter(a -> a == Squad.SquadAction.SHELTER).count();
        for (int i = 0; i < dice5 - squadShelter; i++) {
            int roll = DicesCup.getD6Int();
            if (roll >= 3) {
                senderMessage.sendText(0L,"Выберите, что потерять:\n" +
                        "1: 2 еды\n" +
                        "2: 1 член отряда");
                int j = Integer.parseInt(receiverMessage.get());

                if (j == 1) squad.setFood(squad.getFood() - 2);
                else squad.setShooters(squad.getShooters() - 1);
            }
        }
    }
}
