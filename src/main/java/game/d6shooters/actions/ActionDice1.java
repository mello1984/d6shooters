package game.d6shooters.actions;

import game.d6shooters.bot.Bot;
import game.d6shooters.bot.CommandButton;
import game.d6shooters.game.Squad;
import game.d6shooters.game.SquadState;
import game.d6shooters.road.RoadMap;
import game.d6shooters.road.RoadNode;
import game.d6shooters.users.User;
import lombok.extern.log4j.Log4j2;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.stream.IntStream;

@Log4j2
public class ActionDice1 extends AbstractAction {
    protected final static String BRANCH = "Свернуть к городу";
    protected final static String MAIN = "Ехать прямо";
    private final static String TEXT1 = "Перекресток, выберите направление";
    private final static String TEXT2 = "Ход завершен";
    private final static String TEXT3 = "Некорректная команда, выберите направление";
    private final static String TEXT4 = "";
    private final static String TEXT5 = "";

    public ActionDice1(Bot bot) {
        super(bot);
    }

    @Override
    public void action(User user) {
        Squad squad = user.getSquad();
        IntStream.rangeClosed(1, user.getDicesCup().getCountActiveDiceCurrentValue(1)).forEach(i -> {
            useDice(user, 1);
            squad.addPathfinding(1);
        });

        if (squad.getPathfinding() > 0) {
            squad.addPath(1);
            squad.addPathfinding(-1);

            if (squad.getPlace().getType() != RoadNode.Type.BRANCHSTART)
                squad.setPlace(RoadMap.next(squad.getPlace(), true));
            else {
                squad.setSquadState(SquadState.CROSSROAD);
                bot.send(template.getSendMessageWithButtons(user.getChatId(), TEXT1, BRANCH, MAIN));
                return;
            }
            executeSpecialPlaces(squad);
            user.getActionManager().doActions();

        } else {
            squad.setSquadState(SquadState.STARTTURN.resetStep());
            squad.addPeriod(1);
            bot.send(template.getSquadStateMessage(user.getChatId()));
            bot.send(template.getSendMessageWithButtons(user.getChatId(), TEXT2, CommandButton.NEXT_TURN.name()));
        }
    }

    protected void executeSpecialPlaces(Squad squad) {
        switch (squad.getPlace().getType()) {
            case TOWN -> {
                squad.setPathfinding(0);
                squad.setSquadState(SquadState.TOWN);
            }
            case EVENT -> squad.setSquadState(SquadState.EVENT);
            case RINO -> squad.setSquadState(SquadState.ENDGAME);
        }
    }

    public void processMessage(User user, Message message) {
        switch (message.getText()) {
            case BRANCH -> user.getSquad().setPlace(RoadMap.next(user.getSquad().getPlace(), false));
            case MAIN -> user.getSquad().setPlace(RoadMap.next(user.getSquad().getPlace(), true));
            default -> {
                bot.send(template.getSendMessageWithButtons(user.getChatId(), TEXT3, BRANCH, MAIN));
                return;
            }
        }
        user.getSquad().setSquadState(SquadState.MOVE);
        user.getActionManager().doActions();
    }
}
