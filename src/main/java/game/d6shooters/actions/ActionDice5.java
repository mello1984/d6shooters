package game.d6shooters.actions;

import game.d6shooters.bot.Bot;
import game.d6shooters.bot.Icon;
import game.d6shooters.game.DicesCup;
import game.d6shooters.game.Squad;
import game.d6shooters.game.SquadState;
import game.d6shooters.users.User;
import lombok.extern.log4j.Log4j2;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Log4j2
public class ActionDice5 extends AbstractAction {
    private static final String TEXT1 = "Экстремальная жара, roll '%d' из 6.";
    private static final String TEXT2 = "Экстремальная жара, roll '%d' из 6, что же, сегодня вам повезло...";


    public ActionDice5(Bot bot) {
        super(bot);
    }

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
                bot.send(template.getSendMessageWithButtons(user.getChatId(), String.format(TEXT1, roll), lists));
            }
            else {
                bot.send(template.getSendMessageWithButtons(user.getChatId(), String.format(TEXT2, roll)));
                useDice(user, 5);
                user.getActionManager().doActions();
            }
        } else {
            user.getSquad().setSquadState(SquadState.GUNFIGHT);
            log.debug(String.format("SquadState %s -> GUNFIGHT", user.getSquad().getSquadState()));
            user.getActionManager().doActions();
        }
    }

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
        user.getActionManager().doActions();
    }

    protected enum Button {
        LOSE2FOOD("-2" + Icon.FOOD.get()),
        LOSE1GUNFIGHTER("-1" + Icon.GUNFIGHTER.get()),
        LOSE_PILL("-" + Icon.PILL.get()),
        EMPTY("");

        private String value;

        Button(String value) {
            this.value = value;
        }

        protected String get() {
            return value;
        }

        private static Button getButton(String string) {
            return Arrays.stream(Button.values()).filter(a -> a.value.equals(string)).findFirst().orElse(EMPTY);
        }
    }
}