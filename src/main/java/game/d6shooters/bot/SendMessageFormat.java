package game.d6shooters.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class SendMessageFormat {
    public static SendMessage getSendMessageBaseFormat(Long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setParseMode("HTML");
        sendMessage.disableWebPagePreview();
        sendMessage.setChatId(chatId);
        setMainButtons(sendMessage, chatId);
        return sendMessage;
    }

    public static void setMainButtons(SendMessage sendMessage, Long chatId) {
        // Создаем клавиатуру
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        // Создаем список строк клавиатуры
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(new KeyboardButton(ButtonsType.Button1.name()));
        keyboardRow.add(new KeyboardButton(ButtonsType.Button2.name()));
        keyboard.add(keyboardRow);

        replyKeyboardMarkup.setKeyboard(keyboard);
    }
}