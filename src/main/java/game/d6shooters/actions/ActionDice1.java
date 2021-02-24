package game.d6shooters.actions;

import game.d6shooters.Main;
import game.d6shooters.bot.DataBase;
import game.d6shooters.source.Button;
import game.d6shooters.game.Dice;
import game.d6shooters.game.Squad;
import game.d6shooters.game.SquadState;
import game.d6shooters.road.RoadNode;
import game.d6shooters.source.Text;
import game.d6shooters.users.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Log4j2
@Component
public class ActionDice1 extends AbstractAction {
    @Override
    public void action(User user) {
        log.info(String.format("Start ActionDice1.action, user = %d", user.getChatId()));

        Squad squad = user.getSquad();
        convertDice1ToPathfinding(user);

        if (squad.getResource(Squad.PATHFINDING) > 0) {
            squad.addResource(Squad.PATH, 1);
            squad.addResource(Squad.PATHFINDING, -1);

            if (squad.getPlace().getType() != RoadNode.Type.BRANCHSTART)
                squad.setPlace(squad.getRoadMap().next(squad.getPlace(), true));
            else {
                squad.setSquadState(SquadState.CROSSROAD);
                Main.bot.send(template.getSendMessageWithButtons(user.getChatId(), Text.getText(Text.CROSS_ROAD), Button.BRANCH_ROAD.get(), Button.MAIN_ROAD.get()));
                return;
            }
            executeSpecialPlaces(user);
            Main.actionManager.doActions(user);

        } else {
            squad.addResource(Squad.PERIOD, 1);
            Main.actionManager.checkFeeding(user);

            if (squad.getResource(Squad.PERIOD) < 40) {
                squad.setSquadState(SquadState.STARTTURN1);
                Main.bot.send(template.getSquadStateMessage(user.getChatId()));
                Main.bot.send(template.getSendMessageWithButtons(user.getChatId(), Text.getText(Text.END_TURN), Button.NEXT_TURN.get()));
                DataBase.getInstance().saveUserToUserMap(user);
            } else {
                squad.setSquadState(SquadState.ENDGAME);
                Main.actionManager.doActions(user);
            }
        }
    }

    void convertDice1ToPathfinding(User user) {
        int red = user.getDicesCup().getCountActiveDiceCurrentValue(1, Dice.DiceType.RED);
        int white = user.getDicesCup().getCountActiveDiceCurrentValue(1, Dice.DiceType.WHITE);
        user.getSquad().addResource(Squad.PATHFINDING, white + (user.getSquad().hasResource(Squad.COMPASS) ? 2 : 1) * red);
        user.getDicesCup().setUsedDiceCurrentValue(1);
        user.getSquad().addResource(Squad.DAY_PATH, user.getSquad().getResource(Squad.PATHFINDING));
    }


    void executeSpecialPlaces(User user) {
        Squad squad = user.getSquad();
        switch (squad.getPlace().getType()) {
            case TOWN -> {
                squad.setResource(Squad.PATHFINDING, 0);
                squad.setSquadState(SquadState.TOWN);
            }
            case EVENT -> squad.setSquadState(SquadState.EVENT);
            case RINO -> squad.setSquadState(SquadState.ENDGAME);
        }
    }


    @Override
    public void processMessage(User user, Message message) {
        Button button = Button.getButton(message.getText());
        switch (button) {
            case BRANCH_ROAD -> user.getSquad().setPlace(user.getSquad().getRoadMap().next(user.getSquad().getPlace(), false));
            case MAIN_ROAD -> user.getSquad().setPlace(user.getSquad().getRoadMap().next(user.getSquad().getPlace(), true));
            default -> {
                Main.bot.send(template.getSendMessageWithButtons(user.getChatId(), Text.getText(Text.UNKNOWN_COMMAND)));
                return;
            }
        }
        user.getSquad().setSquadState(SquadState.MOVE);
        Main.actionManager.doActions(user);
    }
}
