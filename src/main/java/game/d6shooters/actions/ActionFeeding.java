package game.d6shooters.actions;

import game.d6shooters.Main;
import game.d6shooters.game.Squad;
import game.d6shooters.game.SquadState;
import game.d6shooters.source.Text;
import game.d6shooters.users.User;
import org.springframework.stereotype.Component;

@Component
public class ActionFeeding extends AbstractAction {

    @Override
    public void action(User user) {
        Squad squad = user.getSquad();
        if (needFeeding(user)) {
            if (squad.getResource(Squad.FOOD) <= 0) {
                squad.setSquadState(SquadState.ENDGAME);
                Main.bot.send(template.getSendMessageWithButtons(user.getChatId(),
                        Text.getText(Text.FEEDING1, squad.getResource(Squad.PERIOD))));
            } else if (squad.getResource(Squad.FOOD) >= squad.getResource(Squad.SHOOTER)) {
                squad.addResource(Squad.FOOD, -squad.getResource(Squad.SHOOTER));
                Main.bot.send(template.getSendMessageWithButtons(user.getChatId(),
                        Text.getText(Text.FEEDING2, squad.getResource(Squad.PERIOD), squad.getResource(Squad.SHOOTER))));
            } else {
                squad.setResource(Squad.SHOOTER, squad.getResource(Squad.FOOD));
                squad.setResource(Squad.FOOD, 0);
                Main.bot.send(template.getSendMessageWithButtons(user.getChatId(),
                        Text.getText(Text.FEEDING3, squad.getResource(Squad.PERIOD), squad.getResource(Squad.SHOOTER))));
            }
        }
    }

    public void checkFeeding(User user) {
        if (needFeeding(user)) action(user);
    }

    private boolean needFeeding(User user) {
        Squad squad = user.getSquad();
        return squad.getResource(Squad.PERIOD) % 5 == 0 && squad.getResource(Squad.PERIOD) < 40 && squad.getResource(Squad.PERIOD) > 0;
    }
}
