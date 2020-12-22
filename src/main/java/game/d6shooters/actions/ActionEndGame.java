package game.d6shooters.actions;

import game.d6shooters.Main;
import game.d6shooters.handler.RestartHandler;
import game.d6shooters.game.Squad;
import game.d6shooters.road.RoadNode;
import game.d6shooters.source.Text;
import game.d6shooters.users.User;

public class ActionEndGame extends AbstractAction {

    @Override
    public void action(User user) {
        if (user.getSquad().getPlace().getType() == RoadNode.Type.RINO) {
            int score = getScores(user);
            Main.bot.send(template.getSendMessageNoButtons(user.getChatId(), Text.getText(Text.END_GAME_WIN, score)));
            Main.users.saveWinner(score, user);
        } else Main.bot.send(template.getSendMessageNoButtons(user.getChatId(), Text.getText(Text.END_GAME_LOSE)));

        Main.bot.send(template.getSquadStateMessage(user.getChatId()));
        new RestartHandler(Main.bot).restartGame(user.getChatId());
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
