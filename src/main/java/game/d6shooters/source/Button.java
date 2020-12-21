package game.d6shooters.source;

import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
public enum Button {
    HUNT(String.format("+2%s, -1%s)", Icon.FOOD.get(), Icon.AMMO.get())),
    BUYFOOD(String.format("+2%s, -1%s", Icon.FOOD.get(), Icon.MONEYBAG.get())),
    SELLFOOD(String.format("-2%s, +1%s", Icon.FOOD.get(), Icon.MONEYBAG.get())),
    BUYAMMO(String.format("+2%s, -1%s", Icon.AMMO.get(), Icon.MONEYBAG.get())),
    SELLAMMO(String.format("-2%s, +1%s", Icon.AMMO.get(), Icon.MONEYBAG.get())),
    STRAY(String.format("Вы заблудились (-1%s, +1%s)", Icon.FOOD.get(), Icon.CLOCK.get())),
    LOSE2GUN(String.format("-2%s", Icon.GUNFIGHTER.get())),
    LOSE1GUNFIGHTER("-" + Icon.GUNFIGHTER.get()),
    LOSE_PILL("-" + Icon.PILL.get()),
    LOSE2GOLD(String.format("-2%s", Icon.MONEYBAG.get())),
    LOSE2FOOD(String.format("-2%s", Icon.FOOD.get())),
    LOSEFOODANDGOLD(String.format("-1%s, -1%s", Icon.FOOD.get(), Icon.MONEYBAG.get())),
    NONE("Ехать дальше"),
    EMPTY(""),

    NEXT_TURN("next turn"),
    BAND(Icon.GUNFIGHTER.get() + Icon.GUNFIGHTER.get() + Icon.GUNFIGHTER.get() + " band"),
    COMMAND("=>"),
    HELP("help"),
    EVENT("activate event"),
    RESTART("restart"),
    RESTART2("restart!"),
    BACK("<="),
    SCORES_MY("my_scores"),
    SCORES_HIGH("high_scores"),

    BRANCH_ROAD("Свернуть к городу"),
    MAIN_ROAD("Ехать прямо"),

    REJECT("Ничего"),
    HIDE("Прятаться"),
    GUNFIGHT("Отстреливаться"),
    SHELTER("Укрываться от жары"),
    PATHFINDING("Искать путь"),

    TEXT9("Использовать медикаменты"),
    TEXT10("Не использовать медикаменты"),

    NO_GAME("Отказаться играть"),

//    HUNT2(setText(2, Icon.FOOD, -1, Icon.AMMO)),
//    BUYFOOD2(setText(2, Icon.FOOD, -1, Icon.MONEYBAG)),
//    SELLFOOD2(setText(-2, Icon.FOOD, 1, Icon.MONEYBAG)),
    ;

    private final String text;
    private static final Map<String, Button> map = Arrays.stream(values()).collect(Collectors.toMap(g -> g.text, g -> g));


    public String get() {
        return text;
    }

    public static Button getButton(String string) {
        return map.getOrDefault(string, EMPTY);
    }

    private static String setText(int i1, Icon icon1, int i2, Icon icon2) {
        return String.format("%d%s, %d%s)", i1, icon1.get(), i2, icon2.get());
    }
}
