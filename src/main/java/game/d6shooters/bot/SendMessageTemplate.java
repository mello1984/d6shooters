package game.d6shooters.bot;

import game.d6shooters.game.Dice;
import game.d6shooters.game.DicesCup;
import game.d6shooters.game.Squad;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.HashMap;
import java.util.Map;

public class SendMessageTemplate {
    Map<Integer, String> dices = new HashMap<>() {{
        put(1, Icon.DICE1.get());
        put(2, Icon.DICE2.get());
        put(3, Icon.DICE3.get());
        put(4, Icon.DICE4.get());
        put(5, Icon.DICE5.get());
        put(6, Icon.DICE6.get());
    }};

    public String squadState(Squad squad) {
        return Icon.GUNFIGHTER.get() + " Отряд: " + squad.getShooters() + "\n" +
                Icon.FOOD.get() + " Еда: " + squad.getFood() + "\n" +
                Icon.AMMO.get() + " Боеприпасы: " + squad.getAmmo() + "\n" +
                Icon.MONEYBAG.get() + " Золото: " + squad.getGold() + "\n" +
                Icon.FOOTPRINTS.get() + " Пройдено: " + squad.getPath() + "\n" +
                Icon.CLOCK.get() + " День: " + squad.getPeriod();
    }

    public SendMessage helloString(Long chatId) {
        SendMessage sendMessage = SendMessageFormat.getSendMessageBaseFormat(chatId)
                .setText("Вы пока не начали игру. Отправьте 'startD6' для начала.");
        SendMessageFormat.setCustomOneLineButtons(sendMessage, "startD6");
        return sendMessage;
    }

    public SendMessage getSendMessageOneLineButtons(Long chatId, String text, String... oneLineButtons) {
        SendMessage sendMessage = SendMessageFormat.getSendMessageBaseFormat(chatId)
                .setText(text);
        SendMessageFormat.setCustomOneLineButtons(sendMessage, oneLineButtons);
        return sendMessage;
    }


    public SendMessage dicesString(Long chatId, DicesCup dicesCup) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Icon.WHITESQUARE.get()).append(" (1-5): ");
        dicesCup.diceList.stream().filter(d -> d.getType() == Dice.DiceType.WHITE).forEach(d -> stringBuilder.append(dices.get(d.getValue())));
        stringBuilder.append("\n").append(Icon.REDSQUARE.get()).append(" (6-8): ");
        dicesCup.diceList.stream().filter(d -> d.getType() == Dice.DiceType.RED).forEach(d -> stringBuilder.append(dices.get(d.getValue())));
        SendMessage sendMessage = SendMessageFormat.getSendMessageBaseFormat(chatId)
                .setText(stringBuilder.toString());
        return sendMessage;
    }

}
