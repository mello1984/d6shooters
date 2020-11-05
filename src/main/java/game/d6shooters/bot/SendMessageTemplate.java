package game.d6shooters.bot;

import game.d6shooters.game.Squad;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class SendMessageTemplate {

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

}
