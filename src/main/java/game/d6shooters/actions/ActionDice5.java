package game.d6shooters.actions;

import game.d6shooters.Main;
import game.d6shooters.source.Button;
import game.d6shooters.game.DicesCup;
import game.d6shooters.game.Squad;
import game.d6shooters.game.SquadState;
import game.d6shooters.source.Text;
import game.d6shooters.users.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Log4j2
@Component
public class ActionDice5 extends AbstractAction {

    @Override
    public void action(User user) {
        int dice5count = user.getDicesCup().getCountActiveDiceCurrentValue(5);
        if (dice5count > 0) {
            int roll = DicesCup.getD6Int();
            if (roll >= 3) {
                List<String> buttons = new ArrayList<>(Arrays.asList(Button.LOSE2FOOD.get(), Button.LOSE1GUNFIGHTER.get()));
                if (user.getSquad().getResource(Squad.PILL) > 0) buttons.add(Button.LOSE_PILL.get());
                List<List<String>> lists = new ArrayList<>();
                lists.add(buttons);
                Main.bot.send(template.getSendMessageWithButtons(user.getChatId(), Text.getText(Text.DICE5HEAT1, roll), lists));
            } else {
                Main.bot.send(template.getSendMessageWithButtons(user.getChatId(), Text.getText(Text.DICE5HEAT2, roll)));
                useDice(user, 5);
                Main.actionManager.doActions(user);
            }
        } else {
            user.getSquad().setSquadState(SquadState.GUNFIGHT);
            Main.actionManager.doActions(user);
        }
    }

    @Override
    public void processMessage(User user, Message message) {
        Button button = Button.getButton(message.getText());
        if (button != Button.EMPTY) useDice(user, 5);
        switch (button) {
            case LOSE2FOOD -> user.getSquad().addResource(Squad.FOOD, -2);
            case LOSE1GUNFIGHTER -> user.getSquad().addResource(Squad.SHOOTER, -1);
            case LOSE_PILL -> {
                user.getSquad().setResource(Squad.PILL, 0);
                user.getDicesCup().setUsedDiceCurrentValue(5);
            }
        }
        if (!user.getSquad().hasResource(Squad.SHOOTER)) user.getSquad().setSquadState(SquadState.ENDGAME);
        Main.actionManager.doActions(user);
    }
}