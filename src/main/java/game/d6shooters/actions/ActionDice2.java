package game.d6shooters.actions;

import game.d6shooters.users.User;

public class ActionDice2 extends AbstractAction {

    @Override
    public void action(User user) {
        int foundFood = user.getDicesCup().getCountActiveDiceCurrentValue(2) / 2;
        if (foundFood > 0) {
            user.getSquad().addFood(foundFood);
            senderMessage.sendMessage(
                    template.getSendMessageOneLineButtons(user.getChatId(),
                            "На охоте добыли " + foundFood + " провизии"));
        }
        user.getDicesCup().setUsedDiceCurrentValue(2);
    }
}
