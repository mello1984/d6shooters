package game.d6shooters.bot;

import game.d6shooters.Main;
import game.d6shooters.game.DicesCup;
import game.d6shooters.game.Squad;
import game.d6shooters.source.Button;
import game.d6shooters.source.Icon;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.*;

public class SendMessageTemplate {
    private static final List<String> standardButtons = Arrays.asList(Button.BAND.get(), Button.COMMAND.get());
    private static SendMessageTemplate sendMessageTemplate = null;


    public static SendMessageTemplate getInstance() {
        if (sendMessageTemplate == null) sendMessageTemplate = new SendMessageTemplate();
        return sendMessageTemplate;
    }


    public SendMessage getSendMessageWithButtons(Long chatId, String text, List<List<String>> buttons) {
        buttons.removeAll(standardButtons);
        Main.users.userMap.get(chatId).setButtons(new ArrayList<>(buttons));
        buttons.add(standardButtons);

        SendMessage sendMessage = SendMessageFormat
                .getSendMessageBaseFormat(chatId)
                .setText(text);
        SendMessageFormat.setButtons(sendMessage, buttons);
        return sendMessage;
    }

    public SendMessage getSendMessageWithButtons(Long chatId, String text, String... oneLineButtons) {
        List<List<String>> buttons = new ArrayList<>() {{
            add(Arrays.asList(oneLineButtons));
        }};
        return getSendMessageWithButtons(chatId, text, buttons);
    }

    public SendMessage getSendMessageNoButtons(Long chatId, String text) {
        return SendMessageFormat.getSendMessageBaseFormat(chatId).setText(text);
    }


    public SendMessage getDicesStringMessage(Long chatId, DicesCup dicesCup) {
        return getSendMessageNoButtons(chatId, dicesCup.toString());
    }

    public SendMessage getSquadStateMessage(Long chatId) {
        Squad squad = Main.users.userMap.get(chatId).getSquad();
        String text = Icon.GUNFIGHTER.get() + " Отряд: " + squad.getResource(Squad.SHOOTER) + "\n" +
                Icon.FOOD.get() + " Еда: " + squad.getResource(Squad.FOOD) + "\n" +
                Icon.AMMO.get() + " Боеприпасы: " + squad.getResource(Squad.AMMO) + "\n" +
                Icon.MONEYBAG.get() + " Золото: " + squad.getResource(Squad.GOLD) + "\n" +
                Icon.FOOTPRINTS.get() + " Пройдено: " + squad.getResource(Squad.PATH) + "\n" +
                Icon.CLOCK.get() + " Прошло дней: " + squad.getResource(Squad.PERIOD);
        return getSendMessageWithButtons(chatId, text, Main.users.userMap.get(chatId).getButtons());
    }
}
