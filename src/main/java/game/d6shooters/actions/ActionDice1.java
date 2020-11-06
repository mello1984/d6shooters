package game.d6shooters.actions;

import game.d6shooters.game.DicesCup;
import game.d6shooters.game.Squad;
import game.d6shooters.road.Node;
import game.d6shooters.users.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class ActionDice1 extends AbstractAction {

    @Override
    public void action(User user) {
        SendMessage sendMessage = template.getSendMessageOneLineButtons(user.getChatId(), "Начинаем движение");
        senderMessage.sendMessage(sendMessage);

        Squad squad = user.getSquad();
        int dice1count = user.getDicesCup().getCountActiveDiceCurrentValue(1);
        int pathfinding = (int) squad.actionList.stream().filter(a -> a == Squad.SquadAction.PATHFINDING).count();
        int distance = dice1count + pathfinding;
        while (distance > 0) {
            squad.road.next();
            distance--;
            squad.addPath(1);
            if (squad.road.getNodeType() == Node.NodeType.EVENT) {
                System.out.println("EVENT");
            } else if (squad.road.getNodeType() == Node.NodeType.TOWN) {
                System.out.println("TOWN");
                return;
            } else System.out.println("ROAD");
        }
    }
}
