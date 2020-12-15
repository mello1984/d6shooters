package game.d6shooters.actions;

import game.d6shooters.bot.Bot;
import game.d6shooters.bot.Icon;
import game.d6shooters.game.DicesCup;
import game.d6shooters.game.Squad;
import game.d6shooters.game.SquadState;
import game.d6shooters.users.User;
import lombok.extern.log4j.Log4j2;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.*;


@Log4j2
public class ActionEvent extends AbstractAction {
    public static final String TEXT1 = "EVENT, roll: %d";
    public static final String TEXT2 = "Вы натыкаетесь на стадо животных и можете поохотиться.";
    public static final String TEXT3 = "Вы встретили торговый обоз и можете поторговать";
    public static final String TEXT4 = "Вы несете непредвиденные потери.";
    public static final String TEXT5 = "Некорректная команда, просьба уточнить";
    public static final String TEXT6 = "Похоже, произошел какой-то сбой. Он может быть вызван некорректной логикой (тогда просьба понять и простить)," +
            " или попыткой читерства. К сожалению, продолжить игру невозможно, корманда погибла... :(";

    public ActionEvent(Bot bot) {
        super(bot);
    }

    @Override
    public void action(User user) {
        int roll = DicesCup.getD6Int();
        bot.send(template.getSendMessageWithButtons(user.getChatId(), String.format(TEXT1, roll)));
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
            bot.send(template.getSendMessageWithButtons(user.getChatId(), TEXT2, Action.HUNT.get(), Action.NONE.get()));
        } else toMoveAction(user);
    }

    protected void startEvent3(User user) {
        user.getSquad().setSquadState(SquadState.EVENT3);
        List<String> buttons = new ArrayList<>();
        if (user.getSquad().getResource(Squad.GOLD) > 0) buttons.add(Action.BUYFOOD.get());
        if (user.getSquad().getResource(Squad.GOLD) > 0) buttons.add(Action.BUYAMMO.get());
        if (user.getSquad().getResource(Squad.FOOD) >= 2) buttons.add(Action.SELLFOOD.get());
        if (user.getSquad().getResource(Squad.AMMO) >= 2) buttons.add(Action.SELLAMMO.get());
        buttons.add(Action.NONE.get());
        bot.send(template.getSendMessageWithButtons(user.getChatId(), TEXT3, buttons.toArray(new String[0])));
    }

    protected void startEvent5(User user) {
        modify(user, 0, -1, 0, 0, 0, 1);
        bot.send(template.getSendMessageWithButtons(user.getChatId(), Action.STRAY.get()));
        toMoveAction(user);
    }

    protected void startEvent6(User user) {
        List<String> buttons = new ArrayList<>();
        user.getSquad().setSquadState(SquadState.EVENT6);
        if (user.getSquad().getResource(Squad.FOOD) >= 2) buttons.add(Action.LOSE2FOOD.get());
        if (user.getSquad().getResource(Squad.GOLD) >= 2) buttons.add(Action.LOSE2GOLD.get());
        if (user.getSquad().getResource(Squad.GOLD) > 0 && user.getSquad().getResource(Squad.FOOD) > 0)
            buttons.add(Action.LOSEFOODANDGOLD.get());
        if (user.getSquad().hasResource(Squad.PILL)) buttons.add(Action.LOSE_PILL.get());
        buttons.add(Action.LOSE2GUN.get());
        bot.send(template.getSendMessageWithButtons(user.getChatId(), TEXT4, buttons.toArray(new String[0])));
    }

    protected void toMoveAction(User user) {
        user.getSquad().setSquadState(SquadState.MOVE);
        user.getActionManager().doActions();
    }

    public void processMessage(User user, Message message) {
        Action action = Action.getAction(message.getText());

        if (action == Action.EMPTY) {
            bot.send(template.getSendMessageNoButtons(user.getChatId(), TEXT5));
            return;
        }

        switch (user.getSquad().getSquadState()) {
            case EVENT2 -> {
                if (action == Action.HUNT) modify(user, -1, 2, 0, 0, 0, 0);
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
                        bot.send(template.getSendMessageNoButtons(user.getChatId(), TEXT5));
                        return;
                    }
                }
            }
        }
        toMoveAction(user);
    }

    protected void modify(User user, int ammo, int food, int gold, int gunfighter, int pathfinding, int period) {
        user.getSquad().addResource(Squad.PERIOD,period);
        user.getSquad().addResource(Squad.PATHFINDING,pathfinding);
        user.getSquad().addResource(Squad.SHOOTER,gunfighter);
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
            bot.send(template.getSendMessageNoButtons(user.getChatId(), TEXT6));
            user.getSquad().addResource(Squad.SHOOTER,-user.getSquad().getResource(Squad.SHOOTER));
        }
    }

    protected enum Action {
        HUNT(String.format("+2%s, -1%s)", Icon.FOOD.get(), Icon.AMMO.get())),
        BUYFOOD(String.format("+2%s, -1%s", Icon.FOOD.get(), Icon.MONEYBAG.get())),
        SELLFOOD(String.format("-2%s, +1%s", Icon.FOOD.get(), Icon.MONEYBAG.get())),
        BUYAMMO(String.format("+2%s, -1%s", Icon.AMMO.get(), Icon.MONEYBAG.get())),
        SELLAMMO(String.format("-2%s, +1%s", Icon.AMMO.get(), Icon.MONEYBAG.get())),
        STRAY(String.format("Вы заблудились (-1%s, +1%s)", Icon.FOOD.get(), Icon.CLOCK.get())),
        LOSE2GUN(String.format("-2%s", Icon.GUNFIGHTER.get())),
        LOSE2GOLD(String.format("-2%s", Icon.MONEYBAG.get())),
        LOSE2FOOD(String.format("-2%s", Icon.FOOD.get())),
        LOSEFOODANDGOLD(String.format("-1%s, -1%s", Icon.FOOD.get(), Icon.MONEYBAG.get())),
        LOSE_PILL(String.format("-%s", Icon.PILL.get())),
        NONE("Ехать дальше"),
        EMPTY("");

        private final String value;

        Action(String value) {
            this.value = value;
        }

        protected String get() {
            return value;
        }

        public static Action getAction(String string) {
            return Arrays.stream(Action.values()).filter(a -> a.value.equals(string)).findFirst().orElse(EMPTY);
        }
    }

}
