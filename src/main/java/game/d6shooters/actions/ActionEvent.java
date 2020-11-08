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

///////////////////////////////////// Надо править enum!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

@Log4j2
public class ActionEvent extends AbstractAction {
    public ActionEvent(Bot bot) {
        super(bot);
    }

    @Override
    public void action(User user) {
        int rnd = DicesCup.getD6Int();
        List<String> buttons = new ArrayList<>();
        log.debug("EVENT, roll: " + rnd);

        switch (rnd) {
            case 1:
                user.getSquad().setSquadState(SquadState.MOVE);
                user.getSquad().addPathfinding(3);
                user.getActionManager().doActions();
                break;
            case 2:
                if (user.getSquad().getAmmo() > 0) {
                    user.getSquad().setSquadState(SquadState.EVENT2);
                    bot.send(template.getSendMessageOneLineButtons(user.getChatId(),
                            "Вы натыкаетесь на стадо животных и можете поохотиться.",
                            Action.HUNT.get(), Action.NONE.get()));
                } else {
                    user.getSquad().setSquadState(SquadState.MOVE);
                    user.getActionManager().doActions();
                }
                break;
            case 3:
                user.getSquad().setSquadState(SquadState.EVENT3);
                if (user.getSquad().getAmmo() >= 2) buttons.add(Action.SELLAMMO.get());
                if (user.getSquad().getFood() >= 2) buttons.add(Action.SELLFOOD.get());
                if (user.getSquad().getGold() > 0) buttons.add(Action.BUYAMMO.get());
                if (user.getSquad().getGold() > 0) buttons.add(Action.BUYFOOD.get());
                buttons.add(Action.NONE.get());
                bot.send(template.getSendMessageOneLineButtons(user.getChatId(),
                        "Вы встретили торговый обоз и можете поторговать",
                        buttons.toArray(new String[0])));
                break;
            case 4:
                user.getSquad().setSquadState(SquadState.MOVE);
                user.getActionManager().doActions();
                break;
            case 5:
                user.getSquad().setSquadState(SquadState.MOVE);
                bot.send(template.getSendMessageOneLineButtons(user.getChatId(), Action.STRAY.get()));
                user.getSquad().addPeriod(1);
                user.getSquad().addFood(-1);
                user.getActionManager().doActions();
                break;
            case 6:
                user.getSquad().setSquadState(SquadState.EVENT6);
                if (user.getSquad().getFood() >= 2) buttons.add(Action.LOSE2FOOD.get());
                if (user.getSquad().getGold() >= 2) buttons.add(Action.LOSE2GOLD.get());
                if (user.getSquad().getGold() > 0 && user.getSquad().getFood() > 0)
                    buttons.add(Action.LOSEFOODANDGOLD.get());
                buttons.add(Action.LOSE2GUN.get());

                bot.send(template.getSendMessageOneLineButtons(user.getChatId(),
                        "Вы несете непредвиденные потери.",
                        Action.LOSE2FOOD.get(), Action.LOSE2GOLD.get(), Action.LOSEFOODANDGOLD.get(), Action.LOSE2GUN.get()));
                break;
        }


    }

    public void processMessage(User user, Message message) {

        Action action = Action.getAction(message.getText());

        if (action == Action.EMPTY) {
            bot.send(template.getSendMessageWithoutButtons(user.getChatId(), "Некорректная команда, просьба уточнить"));
            return;
        }

        switch (user.getSquad().getSquadState()) {
            case EVENT2 -> {
                if (action == Action.HUNT) {
                    user.getSquad().addAmmo(-1);
                    user.getSquad().addFood(2);
                }
            }
            case EVENT3 -> {
                switch (action) {
                    case BUYFOOD: {
                        if (user.getSquad().getGold() > 0) {
                            user.getSquad().addFood(2);
                            user.getSquad().addGold(-1);
                        }
                    }
                    case BUYAMMO: {
                        if (user.getSquad().getGold() > 0) {
                            user.getSquad().addAmmo(2);
                            user.getSquad().addGold(-1);
                        }
                    }
                    case SELLFOOD: {
                        if (user.getSquad().getFood() > 2) {
                            user.getSquad().addFood(-2);
                            user.getSquad().addGold(1);
                        }
                    }
                    case SELLAMMO: {
                        if (user.getSquad().getAmmo() > 2) {
                            user.getSquad().addAmmo(-2);
                            user.getSquad().addGold(1);
                        }
                    }
                }

            }
            case EVENT6 -> {
                switch (action) {
                    case LOSE2FOOD: {
                        if (user.getSquad().getFood() >= 2) {
                            user.getSquad().addFood(-2);
                        } else {
                            bot.send(template.getSendMessageOneLineButtons(user.getChatId(), "Да вы читер..."));
                            user.getSquad().addShooters(-user.getSquad().getShooters());
                        }
                    }
                    case LOSE2GOLD: {
                        if (user.getSquad().getGold() >= 2) {
                            user.getSquad().addGold(-2);
                        } else {
                            bot.send(template.getSendMessageOneLineButtons(user.getChatId(), "Да вы читер..."));
                            user.getSquad().addShooters(-user.getSquad().getShooters());
                        }
                    }
                    case LOSEFOODANDGOLD: {
                        if (user.getSquad().getFood() > 0 && user.getSquad().getGold() > 0) {
                            user.getSquad().addFood(-1);
                            user.getSquad().addGold(-1);
                        } else {
                            bot.send(template.getSendMessageOneLineButtons(user.getChatId(), "Да вы читер..."));
                            user.getSquad().addShooters(-user.getSquad().getShooters());
                        }
                    }
                    case LOSE2GUN: {
                        user.getSquad().addShooters(-2);
                    }

                }

            }
        }
        bot.send(template.squadState(user.getChatId(), user.getSquad()));
        user.getSquad().setSquadState(SquadState.MOVE);
        user.getActionManager().doActions();
    }

    private enum Action {
        HUNT(String.format("Охотиться (2%s за 1%s)", Icon.FOOD.get(), Icon.AMMO.get())),
        BUYFOOD(String.format("Купить 2%s за 1%s", Icon.FOOD.get(), Icon.MONEYBAG.get())),
        SELLFOOD(String.format("Продать 2%s за 1%s", Icon.FOOD.get(), Icon.MONEYBAG.get())),
        BUYAMMO(String.format("Купить 2%s за 1%s", Icon.AMMO.get(), Icon.MONEYBAG.get())),
        SELLAMMO(String.format("Продать 2%s за 1%s", Icon.AMMO.get(), Icon.MONEYBAG.get())),
        STRAY(String.format("Вы заблудились (-1%s, +1%s)", Icon.FOOD.get(), Icon.CLOCK.get())),
        LOSE2GUN(String.format("-2%s", Icon.GUNFIGHTER.get())),
        LOSE2GOLD(String.format("-2%s", Icon.MONEYBAG.get())),
        LOSE2FOOD(String.format("-2%s", Icon.FOOD.get())),
        LOSEFOODANDGOLD(String.format("-1%s, -1%s", Icon.FOOD.get(), Icon.MONEYBAG.get())),
        NONE("Отказаться"),
        EMPTY("");

        private String value;

        Action(String value) {
            this.value = value;
        }

        private String get() {
            return value;
        }

        public static Action getAction(String string) {
            return Arrays.stream(Action.values()).filter(a -> a.value.equals(string)).findFirst().orElse(EMPTY);
        }
    }

}
