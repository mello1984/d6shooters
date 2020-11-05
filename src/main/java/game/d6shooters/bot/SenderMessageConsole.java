package game.d6shooters.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SenderMessageConsole implements SenderMessage {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

//    public String get() {
//        String out = "";
//        try {
//            out = reader.readLine();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return out;
//    }


    @Override
    public void sendText(Long chatId, String message) {
        System.out.println(message);
    }

    @Override
    public void sendMessage(SendMessage sendMessage) {
        System.out.println(sendMessage.getText());
    }


}
