package game.d6shooters.bot;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
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

    private static final Map<String, CommandButton> map = Arrays.stream(values()).collect(Collectors.toMap(g -> g.value, g -> g));
    private final String value;

    public String get() {
        return value;
    }

    public static CommandButton getAction(String string) {
        return map.get(string);
//        return Arrays.stream(CommandButton.values()).filter(a -> a.value.equals(string)).findFirst().orElse(EMPTY);
    }
}
