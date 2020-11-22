package game.d6shooters.bot.handler;

import game.d6shooters.Main;
import game.d6shooters.actions.ActionManager;
import game.d6shooters.bot.Bot;
import game.d6shooters.game.DicesCup;
import game.d6shooters.game.Squad;
import game.d6shooters.game.SquadState;
import game.d6shooters.users.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.meta.api.objects.Message;

public class StartTurnHandler extends AbstractHandler {
    private static final Logger log = LogManager.getLogger(StartTurnHandler.class);

    public StartTurnHandler(Bot bot) {
        super(bot);
    }

    @Override
    public void handle(Message message) {
        long chatId = message.getChatId();
        User user = Main.users.userMap.get(chatId);
        Squad squad = user.getSquad();
        DicesCup dicesCup = user.getDicesCup();
        squad.setGunfight(0);
        squad.setPathfinding(0);

        log.debug(squad);
        log.debug(squad.getSquadState());

        int step = squad.getSquadState().getStep();
        switch (step) {
            case 1:
                dicesCup.getFirstTurnDices();
                bot.send(template.getDicesStringMessage(chatId, dicesCup));
                bot.send(template.getSendMessageNoButtons(user.getChatId(),
                        "Введите номера кубиков для переброски или 0"));
                squad.getSquadState().nextStep();
                break;
            case 2:

                if (!dicesCup.checkString(message.getText())) {
                    bot.send(template.getSendMessageNoButtons(user.getChatId(),
                            "Некорректные данные, введите номера кубиков для переброски или 0"));
                } else {
                    squad.getSquadState().nextStep();
                    if (!message.getText().equals("0")) {
                        dicesCup.getRerolledDices(message.getText());
                        bot.send(template.getDicesStringMessage(chatId, dicesCup));
                        bot.send(template.getSendMessageWithButtons(user.getChatId(),
                                "Введите номера кубиков для переброски или 0"));
                    }
                }

                break;
            case 3:
                if (!dicesCup.checkString(message.getText())) {
                    bot.send(template.getSendMessageNoButtons(user.getChatId(),
                            "Некорректные данные, введите номера кубиков для переброски или 0"));
                } else {
                    if (!message.getText().equals("0")) {
                        dicesCup.getRerolledDices(message.getText());
                        bot.send(template.getDicesStringMessage(chatId, dicesCup));
                    }
                    squad.setSquadState(SquadState.ALLOCATE);
                    user.setActionManager(new ActionManager(user, bot));
                    user.getActionManager().doActions();
                }

                break;
        }
    }
}

/*
public class StartTurnHandler extends AbstractHandler {
    private static final Logger log = LogManager.getLogger(StartTurnHandler.class);

    public StartTurnHandler(Bot bot) {
        super(bot);
    }

    @Override
    public void handle(Message message) {
        long chatId = message.getChatId();
        User user = Main.users.userMap.get(chatId);
        Squad squad = user.getSquad();
        DicesCup dicesCup = Main.users.userMap.get(chatId).getDicesCup();
        squad.setGunfight(0);
        squad.setPathfinding(0);

        log.debug(squad);
        log.debug(squad.getSquadState());
        log.info(squad);
        log.info(squad.getSquadState());


        if (squad.getSquadState() == SquadState.REGULAR) {
            dicesCup.getFirstTurnDices();
            squad.setSquadState(SquadState.REROLL1);
            bot.send(template.getDicesStringMessage(chatId, dicesCup));
            bot.send(template.getSendMessageWithButtons(user.getChatId(),
                    "Введите номера кубиков для переброски или 0"));
            return;
        }

        if (squad.getSquadState() == SquadState.REROLL1) {
            if (!dicesCup.checkString(message.getText())) {
                bot.send(template.getSendMessageWithButtons(user.getChatId(),
                        "Некорректные данные, введите номера кубиков для переброски или 0"));
            } else {
                squad.setSquadState(SquadState.REROLL2);
                if (!message.getText().equals("0")) {
                    dicesCup.getRerolledDices(message.getText());
                    bot.send(template.getDicesStringMessage(chatId, dicesCup));
                    bot.send(template.getSendMessageWithButtons(user.getChatId(),
                            "Введите номера кубиков для переброски или 0"));
                    return;
                }
            }
        }

        if (squad.getSquadState() == SquadState.REROLL2) {
            if (!dicesCup.checkString(message.getText())) {
                bot.send(template.getSendMessageWithButtons(user.getChatId(),
                        "Некорректные данные, введите номера кубиков для переброски или 0"));
            } else {
                if (!message.getText().equals("0")) {
                    dicesCup.getRerolledDices(message.getText());
                    bot.send(template.getDicesStringMessage(chatId, dicesCup));
                }
                squad.setSquadState(SquadState.ALLOCATE);
                user.setActionManager(new ActionManager(user, bot));
                user.getActionManager().doActions();
            }
        }
    }
}
 */