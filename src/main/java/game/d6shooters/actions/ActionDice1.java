package game.d6shooters.actions;

import game.d6shooters.bot.Bot;
import game.d6shooters.source.Button;
import game.d6shooters.game.Dice;
import game.d6shooters.game.Squad;
import game.d6shooters.game.SquadState;
import game.d6shooters.road.RoadNode;
import game.d6shooters.source.Text;
import game.d6shooters.users.User;
import lombok.extern.log4j.Log4j2;
import org.telegram.telegrambots.meta.api.objects.Message;

@Log4j2
public class ActionDice1 extends AbstractAction {

    public ActionDice1(Bot bot) {
        super(bot);
    }

    @Override
    public void action(User user) {
        Squad squad = user.getSquad();
        convertDice1ToPathfinding(user);

        if (squad.getResource(Squad.PATHFINDING) > 0) {
            squad.addResource(Squad.PATH, 1);
            squad.addResource(Squad.PATHFINDING, -1);

            if (squad.getPlace().getType() != RoadNode.Type.BRANCHSTART)
                squad.setPlace(squad.getRoadMap().next(squad.getPlace(), true));
            else {
                squad.setSquadState(SquadState.CROSSROAD);
                bot.send(template.getSendMessageWithButtons(user.getChatId(), Text.getText(Text.CROSS_ROAD), Button.BRANCH_ROAD.get(), Button.MAIN_ROAD.get()));
                return;
            }
            executeSpecialPlaces(squad);
            user.getActionManager().doActions();

        } else {
            squad.addResource(Squad.PERIOD, 1);

            if (squad.getResource(Squad.PERIOD) < 40) {
                squad.setSquadState(SquadState.STARTTURN1);
                bot.send(template.getSquadStateMessage(user.getChatId()));
                bot.send(template.getSendMessageWithButtons(user.getChatId(), Text.getText(Text.END_TURN), Button.NEXT_TURN.get()));
            } else {
                squad.setSquadState(SquadState.ENDGAME);
                user.getActionManager().doActions();
            }
        }
    }

    protected void convertDice1ToPathfinding(User user) {
        int red = (int) user.getDicesCup().getDiceList().stream().filter(d -> d.getValue() == 1 && !d.isUsed() && d.getType() == Dice.DiceType.RED).count();
        int white = (int) user.getDicesCup().getDiceList().stream().filter(d -> d.getValue() == 1 && !d.isUsed() && d.getType() == Dice.DiceType.WHITE).count();
        user.getSquad().addResource(Squad.PATHFINDING, white + (user.getSquad().hasResource(Squad.COMPASS) ? 2 : 1) * red);
        user.getDicesCup().setUsedDiceCurrentValue(1);
    }

    protected void executeSpecialPlaces(Squad squad) {
        switch (squad.getPlace().getType()) {
            case TOWN -> {
                squad.setResource(Squad.PATHFINDING, 0);
                squad.setSquadState(SquadState.TOWN);
            }
            case EVENT -> squad.setSquadState(SquadState.EVENT);
            case RINO -> squad.setSquadState(SquadState.ENDGAME);
        }
    }

    public void processMessage(User user, Message message) {
        Button button = Button.getButton(message.getText());
        switch (button) {
            case BRANCH_ROAD -> user.getSquad().setPlace(user.getSquad().getRoadMap().next(user.getSquad().getPlace(), false));
            case MAIN_ROAD -> user.getSquad().setPlace(user.getSquad().getRoadMap().next(user.getSquad().getPlace(), true));
            default -> {
                bot.send(template.getSendMessageWithButtons(user.getChatId(), Text.getText(Text.UNKNOWN_COMMAND)));
                return;
            }
        }
        user.getSquad().setSquadState(SquadState.MOVE);
        user.getActionManager().doActions();
    }
}
