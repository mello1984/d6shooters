package game.d6shooters.actions;

import game.d6shooters.bot.Bot;
import game.d6shooters.bot.ButtonsType;
import game.d6shooters.game.Squad;
import game.d6shooters.game.SquadState;
import game.d6shooters.road.RoadMap;
import game.d6shooters.road.RoadNode;
import game.d6shooters.users.User;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ActionDice1 extends AbstractAction {

    public ActionDice1(Bot bot) {
        super(bot);
    }

    @Override
    public void action(User user) {
        bot.send(template.getSendMessageOneLineButtons(user.getChatId(), "Начинаем движение"));

        Squad squad = user.getSquad();
        int dice1count = user.getDicesCup().getCountActiveDiceCurrentValue(1);
        int pathfinding = (int) squad.actionList.stream().filter(a -> a == Squad.SquadAction.PATHFINDING).count();
        int distance = dice1count + pathfinding;
        log.debug("dice1count: " + dice1count + ", pathfinding: " + pathfinding + ", dice1: " + user.getDicesCup().getCountActiveDiceCurrentValue(1) +
                ", actions: " + (int) squad.actionList.stream().filter(a -> a == Squad.SquadAction.PATHFINDING).count());

        if (distance > 0) {
            log.debug("distance: " + distance);
            squad.setPlace(RoadMap.next(squad.getPlace()));
            squad.addPath(1);
            log.debug("path: " + squad.getPath());
            if (dice1count > 0) useDice(user, 1);
            else squad.actionList.remove(Squad.SquadAction.PATHFINDING);
            log.debug("dice1: " + user.getDicesCup().getCountActiveDiceCurrentValue(1) +
                    ", actions: " + (int) squad.actionList.stream().filter(a -> a == Squad.SquadAction.PATHFINDING).count());

            if (squad.getPlace().getType() == RoadNode.Type.TOWN) {
//                new ActionTown().action(user);
            }
            if (squad.getPlace().getType() == RoadNode.Type.EVENT) {
//                new ActionEvent().action(user);
            }
            if (squad.getPlace().getType() == RoadNode.Type.RINO) {
//                new ActionEvent().action(user);
            }
            bot.send(template.getSendMessageOneLineButtons(user.getChatId(), "Прошли 1 клетку"));
            user.getActionManager().doActions();

        } else {
            squad.setSquadState(SquadState.REGULAR);
            bot.send(template.getSendMessageOneLineButtons(user.getChatId(), "Движение завершено"));
            squad.addPeriod(1);
            bot.send(template.squadState(user.getChatId(), user.getSquad()));
            bot.send(template.getSendMessageOneLineButtons(user.getChatId(), "Ход завершен}", ButtonsType.NEXT_TURN.name()));
        }
    }
}
