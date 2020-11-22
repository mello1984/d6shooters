package game.d6shooters.bot;

import game.d6shooters.Main;
import game.d6shooters.game.DicesCup;
import game.d6shooters.game.Squad;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.*;

public class SendMessageTemplate {
    private static List<String> standardButtons = Arrays.asList("band", "help");

    public SendMessage getSendMessageWithButtons(Long chatId, String text, List<List<String>> buttons) {
        buttons.removeAll(standardButtons);
        Main.users.userMap.get(chatId).setButtons(new ArrayList<>(buttons));
        buttons.add(standardButtons);

        SendMessage sendMessage = SendMessageFormat
                .getSendMessageBaseFormat(chatId)
                .setText(text);
        SendMessageFormat.setCustomManyLineButtons(sendMessage, buttons);
        return sendMessage;
    }

    public SendMessage getSendMessageWithButtons(Long chatId, String text, String... oneLineButtons) {
        List<List<String>> buttons = new ArrayList<>() {{
            add(Arrays.asList(oneLineButtons));
        }};
        return getSendMessageWithButtons(chatId, text, buttons);
    }

    public SendMessage getSendMessageNoButtons(Long chatId, String text) {

        SendMessage sendMessage = SendMessageFormat.getSendMessageBaseFormat(chatId)
                .setText(text);
        return sendMessage;
    }


    public SendMessage getDicesStringMessage(Long chatId, DicesCup dicesCup) {
        return this.getSendMessageNoButtons(chatId, dicesCup.toString());
    }

    public SendMessage getSquadStateMessage(Long chatId) {
        Squad squad = Main.users.userMap.get(chatId).getSquad();
        String text = Icon.GUNFIGHTER.get() + " Отряд: " + squad.getShooters() + "\n" +
                Icon.FOOD.get() + " Еда: " + squad.getFood() + "\n" +
                Icon.AMMO.get() + " Боеприпасы: " + squad.getAmmo() + "\n" +
                Icon.MONEYBAG.get() + " Золото: " + squad.getGold() + "\n" +
                Icon.FOOTPRINTS.get() + " Пройдено: " + squad.getPath() + "\n" +
                Icon.CLOCK.get() + " Прошло дней: " + squad.getPeriod();
        return this.getSendMessageWithButtons(chatId, text, Main.users.userMap.get(chatId).getButtons());
    }
}
