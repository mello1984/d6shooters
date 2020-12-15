package game.d6shooters.actions;

import game.d6shooters.bot.Bot;
import game.d6shooters.bot.Icon;
import game.d6shooters.game.PokerDices;
import game.d6shooters.game.Squad;
import game.d6shooters.game.SquadState;
import game.d6shooters.users.User;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ActionPoker extends AbstractAction {
    public static final String REJECT = "Отказаться играть";
    public static final String TEXT1 = "Озвучьте вашу ставку";
    public static final String TEXT2 = "Ставка принята, ваш бросок:";
    public static final String TEXT3 = "Введите кубики для переброски или 0";
    public static final String TEXT4 = "Некорректная команда, введите номера кубиков для переброски или 0";

    public ActionPoker(Bot bot) {
        super(bot);
    }

    @Override //action - only SquadState.POKER1
    public void action(User user) {
        List<String> food = IntStream.range(1, user.getSquad().getResource(Squad.FOOD)).mapToObj(i -> i + Icon.FOOD.get()).collect(Collectors.toList());
        List<String> gold = IntStream.range(1, user.getSquad().getResource(Squad.GOLD)).mapToObj(i -> i + Icon.MONEYBAG.get()).collect(Collectors.toList());
        List<String> ammo = IntStream.range(1, user.getSquad().getResource(Squad.AMMO)).mapToObj(i -> i + Icon.AMMO.get()).collect(Collectors.toList());

        List<String> tempList = new ArrayList<>();
        tempList.addAll(gold);
        tempList.addAll(food);
        tempList.addAll(ammo);

        List<List<String>> buttons = new ArrayList<>();
        for (int i = 0; i < tempList.size(); i++) {
            if (i % 4 == 0) buttons.add(new ArrayList<>());
            buttons.get(buttons.size() - 1).add(tempList.get(i));
        }
        buttons.add(Collections.singletonList(REJECT));

        bot.send(template.getSendMessageWithButtons(user.getChatId(), TEXT1, buttons));
        user.getSquad().setSquadState(SquadState.POKER2);
    }

    public void processMessage(User user, Message message) {

        switch (user.getSquad().getSquadState()) {
            case POKER2 -> {
                String text = message.getText();
                if (text.equals(REJECT)) {
                    user.getSquad().setSquadState(SquadState.TOWN);
                    user.getActionManager().doActions();
                    return;
                }

                if (text.contains(Icon.FOOD.get())) user.getSquad().setPokerBetType(Icon.FOOD);
                else if (text.contains(Icon.MONEYBAG.get())) user.getSquad().setPokerBetType(Icon.MONEYBAG);
                else user.getSquad().setPokerBetType(Icon.AMMO);

                text = text.replaceAll(Icon.FOOD.get(), "").replaceAll(Icon.MONEYBAG.get(), "").replaceAll(Icon.AMMO.get(), "");
                user.getSquad().setPokerBetValue(Integer.parseInt(text));

                user.setPokerDices(new PokerDices());
                user.getPokerDices().getFirstTurnDices();
                bot.send(template.getSendMessageNoButtons(user.getChatId(), TEXT2));
                bot.send(template.getSendMessageNoButtons(user.getChatId(), user.getPokerDices().toString()));
                bot.send(template.getSendMessageNoButtons(user.getChatId(), TEXT3));
                user.getSquad().setSquadState(SquadState.POKER3);
            }
            case POKER3 -> {
                if (!user.getPokerDices().checkString(message.getText())) {
                    bot.send(template.getSendMessageNoButtons(user.getChatId(), TEXT4));
                    return;
                }

                user.getSquad().setSquadState(SquadState.POKER4);
                if (!message.getText().equals("0")) {
                    user.getPokerDices().getRerolledDices(message.getText());
                    bot.send(template.getSendMessageNoButtons(user.getChatId(), user.getPokerDices().toString()));
                    bot.send(template.getSendMessageNoButtons(user.getChatId(), TEXT3));
                } else processMessage(user, message);
            }
            case POKER4 -> {
                if (!user.getPokerDices().checkString(message.getText())) {
                    bot.send(template.getSendMessageWithButtons(user.getChatId(), TEXT4));
                    return;
                }

                if (!message.getText().equals("0")) user.getPokerDices().getRerolledDices(message.getText());
                bot.send(template.getSendMessageNoButtons(user.getChatId(), user.getPokerDices().toString()));
                executeResultGame(user);
            }
        }
    }

    protected void executeResultGame(User user) {
        switch (user.getPokerDices().getResult()) {
            case 0 -> setResultGame(user, "Ничья", 0);
            case -1 -> setResultGame(user, "Вы проиграли", -1);
            case 1 -> setResultGame(user, "Вы выиграли", 1);
        }

        switch (user.getSquad().getPokerBetType()) {
            case AMMO -> user.getSquad().addResource(Squad.AMMO, user.getSquad().getPokerBetValue());
            case FOOD -> user.getSquad().addResource(Squad.FOOD, user.getSquad().getPokerBetValue());
            case MONEYBAG -> user.getSquad().addResource(Squad.GOLD, user.getSquad().getPokerBetValue());
        }

        user.getSquad().setSquadState(SquadState.TOWN);
        user.getSquad().getPlace().getTownShop().setCanGamingPoker(false);
        user.getActionManager().doActions();
    }

    protected void setResultGame(User user, String text, int resultMultiplier) {
        bot.send(template.getSendMessageNoButtons(user.getChatId(), text));
        user.getSquad().setPokerBetValue(resultMultiplier * user.getSquad().getPokerBetValue());
    }
}
