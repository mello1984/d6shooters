package game.d6shooters.bot;

import game.d6shooters.Main;
import game.d6shooters.game.DicesCup;
import game.d6shooters.game.Squad;
import game.d6shooters.source.Button;
import game.d6shooters.source.Icon;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.*;

@Component
public class SendMessageTemplate {
    private static final List<String> standardButtons = Arrays.asList(Button.BAND.get(), Button.COMMAND.get());
//    private static SendMessageTemplate sendMessageTemplate = null;
//
//
//    public static SendMessageTemplate getInstance() {
//        if (sendMessageTemplate == null) sendMessageTemplate = new SendMessageTemplate();
//        return sendMessageTemplate;
//    }


    public SendMessage getSendMessageWithButtons(Long chatId, String text, List<List<String>> buttons) {
        buttons.removeAll(standardButtons);
        Main.users.getUserMap().get(chatId).setButtons(new ArrayList<>(buttons));
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
        Squad squad = Main.users.getUserMap().get(chatId).getSquad();

        String mainResources = getMainResourceString(Icon.GUNFIGHTER, "Отряд", Squad.SHOOTER, squad) +
                getMainResourceString(Icon.FOOD, "Еда", Squad.FOOD, squad) +
                getMainResourceString(Icon.AMMO, "Боеприпасы", Squad.AMMO, squad) +
                getMainResourceString(Icon.MONEYBAG, "Золото", Squad.GOLD, squad) +
                getMainResourceString(Icon.FOOTPRINTS, "Пройдено", Squad.PATH, squad) +
                getMainResourceString(Icon.CLOCK, "Прошло дней", Squad.PERIOD, squad);

        String specialItem = (squad.hasResource(Squad.COMPASS) ? Icon.COMPASS.get() : "") +
                (squad.hasResource(Squad.HUNTER) ? Icon.HUNTER.get() : "") +
                (squad.hasResource(Squad.MAP) ? Icon.MAP.get() : "") +
                (squad.hasResource(Squad.BINOCULAR) ? Icon.BINOCULAR.get() : "") +
                (squad.hasResource(Squad.PILL) ? Icon.PILL.get() : "") +
                (squad.hasResource(Squad.BOMB) ? squad.getResource(Squad.BOMB) + " " + Icon.BOMB.get() : "");
        specialItem = specialItem.length() > 0 ? "Особенные вещи: " + specialItem : "Особенных вещей нет";

        String text = mainResources + specialItem;


        return getSendMessageWithButtons(chatId, text, Main.users.getUserMap().get(chatId).getButtons());
    }

    private String getMainResourceString(Icon icon, String text, String resource, Squad squad) {
        return String.format("%s %s: %s \n", icon.get(), text, squad.getResource(resource));
    }
}
