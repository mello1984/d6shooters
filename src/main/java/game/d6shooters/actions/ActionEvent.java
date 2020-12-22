package game.d6shooters.actions;

import game.d6shooters.Main;
import game.d6shooters.source.Button;
import game.d6shooters.game.DicesCup;
import game.d6shooters.game.Squad;
import game.d6shooters.game.SquadState;
import game.d6shooters.source.Text;
import game.d6shooters.users.User;
import lombok.extern.log4j.Log4j2;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.*;


@Log4j2
public class ActionEvent extends AbstractAction {

    @Override
    public void action(User user) {
        int roll = DicesCup.getD6Int();
        Main.bot.send(template.getSendMessageWithButtons(user.getChatId(), Text.getText(Text.EVENT_TEXT1, roll)));
        switch (roll) {
            case 1 -> startEvent1(user);
            case 2 -> startEvent2(user);
            case 3 -> startEvent3(user);
            case 4 -> toMoveAction(user);
            case 5 -> startEvent5(user);
            case 6 -> startEvent6(user);
        }
    }

    protected void startEvent1(User user) {
        modify(user, 0, 0, 0, 0, 3, 0);
        toMoveAction(user);
    }

    protected void startEvent2(User user) {
        if (user.getSquad().getResource(Squad.AMMO) > 0) {
            user.getSquad().setSquadState(SquadState.EVENT2);
            Main.bot.send(template.getSendMessageWithButtons(user.getChatId(), Text.getText(Text.EVENT_TEXT2), Button.HUNT.get(), Button.NONE.get()));
        } else toMoveAction(user);
    }

    protected void startEvent3(User user) {
        user.getSquad().setSquadState(SquadState.EVENT3);
        List<String> buttons = new ArrayList<>();
        if (user.getSquad().getResource(Squad.GOLD) > 0) buttons.add(Button.BUYFOOD.get());
        if (user.getSquad().getResource(Squad.GOLD) > 0) buttons.add(Button.BUYAMMO.get());
        if (user.getSquad().getResource(Squad.FOOD) >= 2) buttons.add(Button.SELLFOOD.get());
        if (user.getSquad().getResource(Squad.AMMO) >= 2) buttons.add(Button.SELLAMMO.get());
        buttons.add(Button.NONE.get());
        Main.bot.send(template.getSendMessageWithButtons(user.getChatId(), Text.getText(Text.EVENT_TEXT3), buttons.toArray(new String[0])));
    }

    protected void startEvent5(User user) {
        modify(user, 0, -1, 0, 0, 0, 1);
        Main.bot.send(template.getSendMessageWithButtons(user.getChatId(), Button.STRAY.get()));
        toMoveAction(user);
    }

    protected void startEvent6(User user) {
        List<String> buttons = new ArrayList<>();
        user.getSquad().setSquadState(SquadState.EVENT6);
        if (user.getSquad().getResource(Squad.FOOD) >= 2) buttons.add(Button.LOSE2FOOD.get());
        if (user.getSquad().getResource(Squad.GOLD) >= 2) buttons.add(Button.LOSE2GOLD.get());
        if (user.getSquad().getResource(Squad.GOLD) > 0 && user.getSquad().getResource(Squad.FOOD) > 0)
            buttons.add(Button.LOSEFOODANDGOLD.get());
        if (user.getSquad().hasResource(Squad.PILL)) buttons.add(Button.LOSE_PILL.get());
        buttons.add(Button.LOSE2GUN.get());
        Main.bot.send(template.getSendMessageWithButtons(user.getChatId(), Text.getText(Text.EVENT_TEXT4), buttons.toArray(new String[0])));
    }

    protected void toMoveAction(User user) {
        user.getSquad().setSquadState(SquadState.MOVE);
        Main.actionManager.doActions(user);
    }

    public void processMessage(User user, Message message) {
        Button action = Button.getButton(message.getText());

        if (action == Button.EMPTY) {
            Main.bot.send(template.getSendMessageNoButtons(user.getChatId(), Text.getText(Text.UNKNOWN_COMMAND)));
            return;
        }

        switch (user.getSquad().getSquadState()) {
            case EVENT2 -> {
                if (action == Button.HUNT) modify(user, -1, 2, 0, 0, 0, 0);
            }
            case EVENT3 -> {
                switch (action) {
                    case BUYFOOD -> modify(user, 0, 2, -1, 0, 0, 0);
                    case BUYAMMO -> modify(user, 2, 0, -1, 0, 0, 0);
                    case SELLFOOD -> modify(user, 0, -2, 1, 0, 0, 0);
                    case SELLAMMO -> modify(user, -2, 0, 1, 0, 0, 0);
                }
            }
            case EVENT6 -> {
                switch (action) {
                    case LOSE2FOOD -> modify(user, 0, -2, 0, 0, 0, 0);
                    case LOSE2GOLD -> modify(user, 0, 0, -2, 0, 0, 0);
                    case LOSEFOODANDGOLD -> modify(user, 0, -1, -1, 0, 0, 0);
                    case LOSE2GUN -> modify(user, 0, 0, 0, -2, 0, 0);
                    case LOSE_PILL -> user.getSquad().setResource(Squad.PILL, 0);
                    default -> {
                        Main.bot.send(template.getSendMessageNoButtons(user.getChatId(), Text.getText(Text.UNKNOWN_COMMAND)));
                        return;
                    }
                }
            }
        }
        toMoveAction(user);
    }

    protected void modify(User user, int ammo, int food, int gold, int gunfighter, int pathfinding, int period) {
        user.getSquad().addResource(Squad.PERIOD, period);
        user.getSquad().addResource(Squad.PATHFINDING, pathfinding);
        user.getSquad().addResource(Squad.SHOOTER, gunfighter);
        boolean error = false;
        if (ammo >= 0 || user.getSquad().getResource(Squad.AMMO) >= -ammo)
            user.getSquad().addResource(Squad.AMMO, ammo);
        else error = true;
        if (food >= 0 || user.getSquad().getResource(Squad.FOOD) >= -food)
            user.getSquad().addResource(Squad.FOOD, food);
        else error = true;
        if (gold >= 0 || user.getSquad().getResource(Squad.GOLD) >= -gold)
            user.getSquad().addResource(Squad.GOLD, gold);
        else error = true;

        if (error) {
            Main.bot.send(template.getSendMessageNoButtons(user.getChatId(), Text.getText(Text.EVENT_TEXT5)));
            user.getSquad().addResource(Squad.SHOOTER, -user.getSquad().getResource(Squad.SHOOTER));
        }
    }

}
