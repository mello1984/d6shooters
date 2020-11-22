package game.d6shooters.actions;

import game.d6shooters.bot.Bot;
import game.d6shooters.bot.Icon;
import game.d6shooters.game.PokerDices;
import game.d6shooters.game.SquadState;
import game.d6shooters.users.User;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ActionPoker extends AbstractAction {
    public ActionPoker(Bot bot) {
        super(bot);
    }

    @Override
    public void action(User user) {
        switch (user.getSquad().getSquadState()) {
            case POKER1: {
                List<String> food = IntStream.range(1, user.getSquad().getFood()).mapToObj(i -> i + Icon.FOOD.get()).collect(Collectors.toList());
                List<String> gold = IntStream.range(1, user.getSquad().getGold()).mapToObj(i -> i + Icon.MONEYBAG.get()).collect(Collectors.toList());
                List<String> ammo = IntStream.range(1, user.getSquad().getAmmo()).mapToObj(i -> i + Icon.AMMO.get()).collect(Collectors.toList());

                List<String> tempList = new ArrayList<>();
                tempList.addAll(gold);
                tempList.addAll(food);
                tempList.addAll(ammo);

                List<List<String>> buttons = new ArrayList<>();
                for (int i = 0; i < tempList.size(); i++) {
                    if (i % 4 == 0) buttons.add(new ArrayList<>());
                    buttons.get(buttons.size() - 1).add(tempList.get(i));
                }

                bot.send(template.getSendMessageWithButtons(user.getChatId(), "Озвучьте вашу ставку", buttons));
                user.getSquad().setSquadState(SquadState.POKER2);
            }
            case POKER2: {

            }
        }
    }

    public void processMessage(User user, Message message) {

        switch (user.getSquad().getSquadState()) {
            case POKER2: {
                String text = message.getText();
                if (text.contains(Icon.FOOD.get())) user.getSquad().setPokerBetType(Icon.FOOD);
                else if (text.contains(Icon.MONEYBAG.get())) user.getSquad().setPokerBetType(Icon.MONEYBAG);
                else user.getSquad().setPokerBetType(Icon.AMMO);

                text = text.replaceAll(Icon.FOOD.get(), "").replaceAll(Icon.MONEYBAG.get(), "").replaceAll(Icon.AMMO.get(), "");
                user.getSquad().setPokerBetValue(Integer.parseInt(text));
                user.getSquad().setPokerDices(new PokerDices());
                user.getSquad().getPokerDices().getFirstTurnDices();

                bot.send(template.getSendMessageWithButtons(user.getChatId(), "Ставка принята, ваш бросок:"));
                bot.send(template.getSendMessageWithButtons(user.getChatId(), user.getSquad().getPokerDices().toString()));
                bot.send(template.getSendMessageWithButtons(user.getChatId(), "Введите кубики для переброски или 0"));
                user.getSquad().setSquadState(SquadState.POKER3);
            }
            case POKER3: {
                if (!user.getSquad().getPokerDices().checkString(message.getText())) {
                    bot.send(template.getSendMessageWithButtons(user.getChatId(),
                            "Некорректные данные, введите номера кубиков для переброски или 0"));
                } else {
                    user.getSquad().setSquadState(SquadState.POKER4);
                    if (!message.getText().equals("0")) {
                        user.getSquad().getPokerDices().getRerolledDices(message.getText());
                        bot.send(template.getSendMessageWithButtons(user.getChatId(), user.getSquad().getPokerDices().toString()));
                        bot.send(template.getSendMessageWithButtons(user.getChatId(), "Введите кубики для переброски или 0"));
                    }
                }
            }
            case POKER4: {
                if (!user.getSquad().getPokerDices().checkString(message.getText())) {
                    bot.send(template.getSendMessageWithButtons(user.getChatId(),
                            "Некорректные данные, введите номера кубиков для переброски или 0"));
                } else {


                    user.getSquad().getPokerDices().getRerolledDices(message.getText());
                    bot.send(template.getSendMessageWithButtons(user.getChatId(), user.getSquad().getPokerDices().toString())); // отправили финальное значение игрока
                    switch (user.getSquad().getPokerDices().getResult()) {
                        case 0:
                            bot.send(template.getSendMessageWithButtons(user.getChatId(),
                                    "Вы сыграли вничью"));
                            user.getSquad().setPokerBetValue(0);
                            break;
                        case -1:
                            bot.send(template.getSendMessageWithButtons(user.getChatId(),
                                    "Вы проиграли"));
                            user.getSquad().setPokerBetValue(-user.getSquad().getPokerBetValue());
                            break;
                        case 1:
                            bot.send(template.getSendMessageWithButtons(user.getChatId(),
                                    "Вы выиграли"));
                            break;
                    }
                    switch (user.getSquad().getPokerBetType()) {
                        case AMMO -> user.getSquad().addAmmo(user.getSquad().getPokerBetValue());
                        case FOOD -> user.getSquad().addFood(user.getSquad().getPokerBetValue());
                        case MONEYBAG -> user.getSquad().addGold(user.getSquad().getPokerBetValue());
                    }

                    user.getSquad().setSquadState(SquadState.TOWN); //!!! Здесь надо поправить на корректное значение
                    // А здесь сделать отметку что в городе уже сыграли в покер
//                    bot.send(template.getSendMessageManyLineButtons(user.getChatId(), "Введите кубики для переброски или 0"));
                }
            }
        }


        user.getActionManager().doActions();
    }
}
