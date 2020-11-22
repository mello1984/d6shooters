package game.d6shooters.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SendMessageFormat {
    public static SendMessage getSendMessageBaseFormat(Long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setParseMode("HTML");
        sendMessage.disableWebPagePreview();
        sendMessage.setChatId(chatId);
        return sendMessage;
    }

    public static void setCustomManyLineButtons(SendMessage sendMessage, List<List<String>> buttons) {
        // Создаем клавиатуру
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        // Создаем список строк клавиатуры
        List<KeyboardRow> keyboard = new ArrayList<>();
        buttons.forEach(l -> {
            KeyboardRow keyboardRow = new KeyboardRow();
            l.forEach(button -> keyboardRow.add(new KeyboardButton(button)));
            keyboard.add(keyboardRow);
        });

        replyKeyboardMarkup.setKeyboard(keyboard);
    }
}
