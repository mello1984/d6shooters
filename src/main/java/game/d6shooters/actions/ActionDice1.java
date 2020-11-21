package game.d6shooters.actions;

import game.d6shooters.bot.Bot;
import game.d6shooters.bot.ButtonsType;
import game.d6shooters.game.Squad;
import game.d6shooters.game.SquadState;
import game.d6shooters.road.RoadMap;
import game.d6shooters.road.RoadNode;
import game.d6shooters.users.User;
import lombok.extern.log4j.Log4j2;
import org.telegram.telegrambots.meta.api.objects.Message;

@Log4j2
public class ActionDice1 extends AbstractAction {
    private final static String BRANCH = "Свернуть к городу";
    private final static String MAIN = "Ехать прямо";

    public ActionDice1(Bot bot) {
        super(bot);
    }

    @Override
    public void action(User user) {

        Squad squad = user.getSquad();
        int dice1count = user.getDicesCup().getCountActiveDiceCurrentValue(1);
        int pathfinding = squad.getPathfinding();
        int distance = dice1count + pathfinding;

        if (distance > 0) {
            log.debug(squad.getPlace());

            squad.addPath(1);
            if (dice1count > 0) useDice(user, 1);
            else squad.addPathfinding(-1);

            if (squad.getPlace().getType() != RoadNode.Type.BRANCHSTART)
                squad.setPlace(RoadMap.next(squad.getPlace(), true));
            else {
                squad.setSquadState(SquadState.CROSSROAD);
                bot.send(template.getSendMessageOneLineButtons(user.getChatId(), "Перекресток, выберите направление",
                        BRANCH, MAIN));
                return;
            }


            if (squad.getPlace().getType() == RoadNode.Type.TOWN) {
                user.getDicesCup().setUsedDiceCurrentValue(1);
                squad.setPathfinding(0);
                squad.setSquadState(SquadState.TOWN);

//                user.getActionManager().doActions();
            }
            if (squad.getPlace().getType() == RoadNode.Type.EVENT) {
                squad.setSquadState(SquadState.EVENT);
//                user.getActionManager().doActions();
            }
            if (squad.getPlace().getType() == RoadNode.Type.RINO) {
                squad.setSquadState(SquadState.ENDGAME);
//                user.getActionManager().doActions();
            }
            user.getActionManager().doActions();

        } else {
            squad.setSquadState(SquadState.REGULAR);
            squad.addPeriod(1);
            bot.send(template.squadState(user.getChatId(), user.getSquad()));
            bot.send(template.getSendMessageOneLineButtons(user.getChatId(), "Ход завершен}", ButtonsType.NEXT_TURN.name()));
        }
    }

    public void processMessage(User user, Message message) {
        if (!message.getText().equals(BRANCH) && !message.getText().equals(MAIN)) {
            bot.send(template.getSendMessageOneLineButtons(user.getChatId(), "Некорректная команда, выберите направление",
                    BRANCH, MAIN));
            return;
        }

        switch (message.getText()) {
            case BRANCH -> user.getSquad().setPlace(RoadMap.next(user.getSquad().getPlace(), true));
            case MAIN -> user.getSquad().setPlace(RoadMap.next(user.getSquad().getPlace(), false));
        }
        bot.send(template.getSendMessageOneLineButtons(user.getChatId(), user.getSquad().getPlace().toString()));
        user.getSquad().setSquadState(SquadState.MOVE);
        user.getActionManager().doActions();
    }
}
