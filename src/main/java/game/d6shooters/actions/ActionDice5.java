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
import java.util.Collections;
import java.util.List;

@Log4j2
@Component
public class ActionDice5 extends AbstractAction {

    @Override
    public void action(User user) {
        log.info(String.format("Start ActionDice5.action, user = %d", user.getChatId()));
        int dice5count = user.getDicesCup().getCountActiveDiceCurrentValue(5);
        if (dice5count > 0) {
            int roll = DicesCup.getD6Int();
            if (roll >= 3) {
                List<List<String>> buttonList = getHeatDamageButtons(user);
                Main.bot.send(template.getSendMessageWithButtons(user.getChatId(), Text.getText(Text.DICE5HEAT1, roll), buttonList));
            } else {
                processNoHeatDamage(user, roll);
            }
        } else {
            user.getSquad().setSquadState(SquadState.GUNFIGHT);
            Main.actionManager.doActions(user);
        }

    }

    List<List<String>> getHeatDamageButtons(User user) {
        List<String> buttons = new ArrayList<>(Collections.singletonList(Button.LOSE1GUNFIGHTER.get()));
        if (user.getSquad().getResource(Squad.FOOD) >= 2) buttons.add(Button.LOSE2FOOD.get());
        if (user.getSquad().hasResource(Squad.PILL)) buttons.add(Button.LOSE_PILL.get());
        List<List<String>> result = new ArrayList<>();
        result.add(buttons);
        return result;
    }

    void processNoHeatDamage(User user, int roll) {
        useDice(user, 5);
        Main.bot.send(template.getSendMessageWithButtons(user.getChatId(), Text.getText(Text.DICE5HEAT2, roll)));
        Main.actionManager.doActions(user);
    }

    @Override
    public void processMessage(User user, Message message) {
        Button button = Button.getButton(message.getText());
        if (button != Button.EMPTY) useDice(user, 5);
        switch (button) {
            case LOSE2FOOD -> user.getSquad().addResource(Squad.FOOD, -2);
            case LOSE1GUNFIGHTER -> {
                user.getSquad().addResource(Squad.SHOOTER, -1);
                if (!user.getSquad().hasResource(Squad.SHOOTER)) user.getSquad().setSquadState(SquadState.ENDGAME);
            }
            case LOSE_PILL -> {
                user.getSquad().setResource(Squad.PILL, 0);
                user.getDicesCup().setUsedDiceCurrentValue(5);
            }
        }

        Main.actionManager.doActions(user);
    }
}