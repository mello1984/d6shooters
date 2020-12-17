package game.d6shooters.actions;

import game.d6shooters.bot.Bot;
import game.d6shooters.handler.RestartHandler;
import game.d6shooters.game.Squad;
import game.d6shooters.road.RoadNode;
import game.d6shooters.source.Text;
import game.d6shooters.users.User;

public class ActionEndGame extends AbstractAction {

    public ActionEndGame(Bot bot) {
        super(bot);
    }

    @Override
    public void action(User user) {
        if (user.getSquad().getPlace().getType() == RoadNode.Type.RINO)
            bot.send(template.getSendMessageNoButtons(user.getChatId(), Text.getText(Text.END_GAME_WIN, getScores(user))));
        else bot.send(template.getSendMessageNoButtons(user.getChatId(), Text.getText(Text.END_GAME_LOSE)));

        bot.send(template.getSquadStateMessage(user.getChatId()));
        new RestartHandler(bot).restartGame(user.getChatId());
    }

    private int getScores(User user) {
        Squad squad = user.getSquad();
        if (squad.getPlace().getType() != RoadNode.Type.RINO) return 0;
        int scores = 10;
        scores += 40 - squad.getResource(Squad.PERIOD);
        scores += squad.getResource(Squad.FOOD) + 3 * squad.getResource(Squad.SHOOTER) + squad.getResource(Squad.AMMO) / 2 + 2 * squad.getResource(Squad.GOLD);
        return scores;
    }
}
