package game.d6shooters.bot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TurnMessageConsole implements TurnMessage {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public String get() {
        String out = "";
        try {
            out = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out;
    }

    @Override
    public void out(String message) {
        System.out.println(message);
    }
}
