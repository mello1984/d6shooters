package game.d6shooters.road;

import game.d6shooters.bot.Icon;
import game.d6shooters.users.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.*;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TownShop {
    static final List<SpecialGood> SPECIAL_GOOD_LIST = new ArrayList<>(Arrays.asList(SpecialGood.values()));
    final Set<StandardGood> standardGoods;
    SpecialGood specialGood;
    boolean canGamingPoker;

    public TownShop() {
        standardGoods = new HashSet<>(Arrays.asList(StandardGood.values()));
        specialGood = SPECIAL_GOOD_LIST.get(new Random().nextInt(SPECIAL_GOOD_LIST.size() + 1));
        canGamingPoker = true;
    }

    public List<List<String>> getGoods(User user) {
        int gold = user.getSquad().getGold();
        List<String> line1 = new ArrayList<>();
        List<String> line2 = new ArrayList<>();
        List<String> line3 = new ArrayList<>();

        if (gold > 0) line1.add(Action.BUY2FOOD.get());
        if (gold > 1) line1.add(Action.BUY5FOOD.get());
        if (gold > 0) line1.add(Action.BUY2AMMO.get());
        if (gold > 1) line1.add(Action.BUY5AMMO.get());

        if (gold > 0) line2.add(Action.HIRE1.get());
        if (gold > 1) line2.add(Action.HIRE2.get());
        if (gold > 2) line2.add(Action.HIRE3.get());

        if (gold >= specialGood.value) line3.add(Action.specialGoodActionMap.get(specialGood).get());
        if (gold > 0) line3.add(Action.POKER.get());
        line3.add(Action.NONE.get());

        List<List<String>> buttons = new ArrayList<>();
        buttons.add(line1);
        buttons.add(line2);
        buttons.add(line3);
        return buttons;
    }


    @AllArgsConstructor
    private enum StandardGood {
        FOOD1(2, 1), FOOD2(5, 2), AMMO1(2, 1), AMMO2(5, 2),
        HIRE1(1, 1), HIRE2(2, 2), HIRE3(3, 3);
        private int count;
        private int value;
    }

    @AllArgsConstructor
    private enum SpecialGood {
        COMPASS(2), HUNTER(3), MAP(3), BINOCULAR(2), PILL(2), BOMB(1);
        private int value;
    }

    @FieldDefaults(level = AccessLevel.PRIVATE)
    private enum Action {
        BUY2FOOD(String.format("%d%s за %d%s", StandardGood.FOOD1.count, Icon.FOOD.get(), StandardGood.FOOD1.value, Icon.MONEYBAG.get())),
        BUY5FOOD(String.format("5%s за 2%s", Icon.FOOD.get(), Icon.MONEYBAG.get())),
        BUY2AMMO(String.format("2%s за 1%s", Icon.AMMO.get(), Icon.MONEYBAG.get())),
        BUY5AMMO(String.format("5%s за 2%s", Icon.AMMO.get(), Icon.MONEYBAG.get())),
        HIRE1(String.format("1%s за 1%s", Icon.GUNFIGHTER.get(), Icon.MONEYBAG.get())),
        HIRE2(String.format("2%s за 2%s", Icon.GUNFIGHTER.get(), Icon.MONEYBAG.get())),
        HIRE3(String.format("3%s за 3%s", Icon.GUNFIGHTER.get(), Icon.MONEYBAG.get())),
        COMPASS(String.format("%s за 2%s", Icon.COMPASS.get(), Icon.MONEYBAG.get())),
        HUNTER(String.format("%s за 3%s", Icon.HUNTER.get(), Icon.MONEYBAG.get())),
        MAP(String.format("%s за 2%s", Icon.MAP.get(), Icon.MONEYBAG.get())),
        BINOCULAR(String.format("%s за 2%s", Icon.BINOCULAR.get(), Icon.MONEYBAG.get())),
        PILL(String.format("%s за %s", Icon.PILL.get(), Icon.MONEYBAG.get())),
        BOMB(String.format("%s за 1%s", Icon.BOMB.get(), Icon.MONEYBAG.get())),

        POKER(String.format("%s Сыграть в покер", Icon.POKER.get())),
        NONE("Отказаться"),
        EMPTY("");
        private String value;

        Action(String value) {
            this.value = value;
        }

        static Map<SpecialGood, Action> specialGoodActionMap = new HashMap<>() {{
            put(SpecialGood.COMPASS, COMPASS);
            put(SpecialGood.HUNTER, HUNTER);
            put(SpecialGood.MAP, MAP);
            put(SpecialGood.BINOCULAR, BINOCULAR);
            put(SpecialGood.PILL, PILL);
            put(SpecialGood.BOMB, BOMB);

        }};

        private String get() {
            return value;
        }

        public static Action getAction(String string) {
            return Arrays.stream(Action.values()).filter(a -> a.value.equals(string)).findFirst().orElse(EMPTY);
        }
    }
}
