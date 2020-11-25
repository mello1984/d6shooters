package game.d6shooters.actions;

import game.d6shooters.bot.Bot;
import game.d6shooters.bot.handler.RestartHandler;
import game.d6shooters.game.Squad;
import game.d6shooters.road.RoadNode;
import game.d6shooters.users.User;

public class ActionEndGame extends AbstractAction {
    private static final String TEXT1 = "Игра закончена. Вы набрали %d очков";

    public ActionEndGame(Bot bot) {
        super(bot);
    }

    @Override
    public void action(User user) {
        bot.send(template.getSendMessageNoButtons(user.getChatId(), String.format(TEXT1, getScores(user))));
        bot.send(template.getSquadStateMessage(user.getChatId()));
        new RestartHandler(bot).restartGame(user.getChatId());
    }

    private int getScores(User user) {
        Squad squad = user.getSquad();
        int scores = squad.getPlace().getType() == RoadNode.Type.RINO ? 10 : 0;
        scores += 40 - squad.getPeriod();
        scores += squad.getFood() + 3 * squad.getShooters() + squad.getAmmo() / 2 + 2 * squad.getGold();
        return scores;

    }
}
