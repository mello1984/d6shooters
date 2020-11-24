package game.d6shooters.bot;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum CommandButton {
    NEXT_TURN("next turn"),
    BAND(Icon.GUNFIGHTER.get() + Icon.GUNFIGHTER.get() + Icon.GUNFIGHTER.get() + " band"),
    COMMAND("=>"),
    HELP("help"),
    EVENT("activate event"),
    RESTART("restart"),
    RESTART2("restart!"),
    BACK("<="),
    EMPTY("");

    private final String value;

    CommandButton(String value) {
        this.value = value;
    }

    public String get() {
        return value;
    }

    public static CommandButton getAction(String string) {
        return Arrays.stream(CommandButton.values()).filter(a -> a.value.equals(string)).findFirst().orElse(EMPTY);
    }
}
