package game.d6shooters.actions;

import game.d6shooters.Main;
import game.d6shooters.game.Squad;
import game.d6shooters.game.SquadState;
import game.d6shooters.source.Text;
import game.d6shooters.users.User;

//TODO: питание, если день зачеркнули с помощью действия

public class ActionFeeding extends AbstractAction {

    @Override
    public void action(User user) {
        Squad squad = user.getSquad();
        if (squad.getResource(Squad.PERIOD) % 5 == 0 && squad.getResource(Squad.PERIOD) < 40 && squad.getResource(Squad.PERIOD) > 0) {
            if (!squad.hasResource(Squad.FOOD)) squad.setSquadState(SquadState.ENDGAME);
            else if (squad.getResource(Squad.FOOD) >= squad.getResource(Squad.SHOOTER)) {
                squad.addResource(Squad.FOOD, -squad.getResource(Squad.SHOOTER));
                Main.bot.send(template.getSendMessageWithButtons(user.getChatId(), Text.getText(Text.FEEDING1, squad.getResource(Squad.PERIOD), squad.getResource(Squad.SHOOTER))));
            } else {
                squad.setResource(Squad.SHOOTER, squad.getResource(Squad.FOOD));
                squad.setResource(Squad.FOOD, 0);
            }
        }
    }
}
