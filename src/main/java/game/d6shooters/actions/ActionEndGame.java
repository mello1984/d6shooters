package game.d6shooters.actions;

import game.d6shooters.Main;
import game.d6shooters.bot.DataBase;
import game.d6shooters.game.Squad;
import game.d6shooters.road.RoadNode;
import game.d6shooters.source.Text;
import game.d6shooters.users.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class ActionEndGame extends AbstractAction {

    @Override
    public void action(User user) {
        log.info(String.format( "Start ActionDice5.action, user = %d", user.getChatId()));
        boolean squadInRino = user.getSquad().getPlace().getType() == RoadNode.Type.RINO;
        String text = squadInRino ? getWinTextAndSaveToWinners(user) : Text.getText(Text.END_GAME_LOSE);
        Main.bot.send(template.getSendMessageNoButtons(user.getChatId(), text));
        Main.bot.send(template.getSquadStateMessage(user.getChatId()));
        Main.handlerManager.restartGame(user.getChatId());
    }

    private String getWinTextAndSaveToWinners(User user) {
        Squad squad = user.getSquad();

        int rino = 10;
        int days = 40 - squad.getResource(Squad.PERIOD);
        int gold = 2 * squad.getResource(Squad.GOLD);
        int shooters = 3 * squad.getResource(Squad.SHOOTER);
        int ammo = squad.getResource(Squad.AMMO) / 2;
        int food = squad.getResource(Squad.FOOD);
        int scores = rino + days + gold + shooters + ammo + food;

        DataBase.getInstance().saveWinner(scores, user);
        return String.format(Text.getText(Text.END_GAME_WIN), scores, rino, days, gold, shooters, food, ammo);
    }
}
