package game.d6shooters.road;

import game.d6shooters.bot.Icon;
import game.d6shooters.game.DicesCup;
import game.d6shooters.game.Squad;
import game.d6shooters.users.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;

import java.util.*;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log4j2
public class TownShop {
    final Set<Item> items;
    Item specialItem;
    boolean canGamingPoker;
    public static final String POKER = String.format("%s Сыграть в покер", Icon.POKER.get());
    public static final String REJECT = "Ехать дальше";

    public TownShop() {
        items = new LinkedHashSet<>(Arrays.asList(Item.values()));
        canGamingPoker = true;
        specialItem = switch (DicesCup.getD6Int()) {
            case 1 -> Item.COMPASS;
            case 2 -> Item.HUNTER;
            case 3 -> Item.MAP;
            case 4 -> Item.BINOCULAR;
            case 5 -> Item.PILL;
            case 6 -> Item.BOMB1;
            default -> Item.EMPTY;
        };
    }

    public List<List<String>> getGoods(User user) {
        Squad squad = user.getSquad();

        List<String> goods = getStandardItemButtons(squad);
        List<String> line1 = new ArrayList<>();
        List<String> line2 = new ArrayList<>();
        while (goods.size() > 0) {
            if (line1.size() <= 4) line1.add(goods.remove(0));
            else line2.add(goods.remove(0));
        }

        List<String> line3 = getSpecialItemButtons(squad);
        if (canGamingPoker && (squad.getResource(Squad.GOLD) > 0 || squad.getResource(Squad.AMMO) > 0 || squad.getResource(Squad.FOOD) > 0)) line3.add(POKER);
        line3.add(REJECT);

        List<List<String>> buttons = new ArrayList<>();
        buttons.add(line1);
        buttons.add(line2);
        buttons.add(line3);
        return buttons;
    }

    protected List<String> getStandardItemButtons(Squad squad) {
        int gold = squad.getResource(Squad.GOLD);
        List<String> goods = new ArrayList<>();
        if (items.contains(Item.FOOD1) && gold >= Item.FOOD1.getValue() && squad.getResource(Squad.FOOD) < 12)
            goods.add(Item.FOOD1.getText());
        if (items.contains(Item.FOOD2) && gold >= Item.FOOD2.getValue() && squad.getResource(Squad.FOOD) < 10)
            goods.add(Item.FOOD2.getText());
        if (items.contains(Item.AMMO1) && gold >= Item.AMMO1.getValue() && squad.getResource(Squad.AMMO) < 5)
            goods.add(Item.AMMO1.getText());
        if (items.contains(Item.AMMO2) && gold >= Item.AMMO2.getValue() && squad.getResource(Squad.AMMO) < 4)
            goods.add(Item.AMMO2.getText());
        if (items.contains(Item.HIRE1) && gold >= Item.HIRE1.getValue() && squad.getResource(Squad.SHOOTER) < 12)
            goods.add(Item.HIRE1.getText());
        if (items.contains(Item.HIRE2) && gold >= Item.HIRE2.getValue() && squad.getResource(Squad.SHOOTER) < 11)
            goods.add(Item.HIRE2.getText());
        if (items.contains(Item.HIRE3) && gold >= Item.HIRE3.getValue() && squad.getResource(Squad.SHOOTER) < 10)
            goods.add(Item.HIRE3.getText());
        return goods;
    }

    protected List<String> getSpecialItemButtons(Squad squad) {
        log.debug("SpecialItem: " + specialItem);
        List<String> resultList = switch (specialItem) {
            case COMPASS, HUNTER, MAP, BINOCULAR, PILL -> setSpecialItemButton(squad);
            case BOMB1 -> {
                List<String> list = new ArrayList<>();
                if (squad.getResource(Squad.BOMB) < 3 && squad.getResource(Squad.GOLD) >= Item.BOMB1.value) list.add(Item.BOMB1.getText());
                if (squad.getResource(Squad.BOMB) < 2 && squad.getResource(Squad.GOLD) >= Item.BOMB2.value) list.add(Item.BOMB2.getText());
                if (squad.getResource(Squad.BOMB) == 0 && squad.getResource(Squad.GOLD) >= Item.BOMB3.value) list.add(Item.BOMB3.getText());
                yield list;
            }
            default -> new ArrayList<>();
        };
        log.debug("SpecialItem Button: " + resultList);
        return resultList;
    }

    protected List<String> setSpecialItemButton(Squad squad) {
        List<String> list = new ArrayList<>();
        if (!squad.hasResource(specialItem.getResource()) && squad.getResource(Squad.GOLD) >= specialItem.value) list.add(specialItem.getText());
        return list;
    }

    @AllArgsConstructor
    @Getter
    public enum Item {
        FOOD1(2, 1, 1, Icon.FOOD, Squad.FOOD),
        FOOD2(5, 2, 1, Icon.FOOD, Squad.FOOD),
        AMMO1(2, 1, 2, Icon.AMMO, Squad.AMMO),
        AMMO2(5, 2, 2, Icon.AMMO, Squad.AMMO),
        HIRE1(1, 1, 3, Icon.GUNFIGHTER, Squad.SHOOTER),
        HIRE2(2, 2, 3, Icon.GUNFIGHTER, Squad.SHOOTER),
        HIRE3(3, 3, 3, Icon.GUNFIGHTER, Squad.SHOOTER),

        COMPASS(1, 2, 4, Icon.COMPASS, Squad.COMPASS),
        HUNTER(1, 3, 5, Icon.HUNTER, Squad.HUNTER),
        MAP(1, 3, 6, Icon.MAP, Squad.MAP),
        BINOCULAR(1, 2, 7, Icon.BINOCULAR, Squad.BINOCULAR),
        PILL(1, 2, 8, Icon.PILL, Squad.PILL),
        BOMB1(1, 1, 9, Icon.BOMB, Squad.BOMB),
        BOMB2(2, 2, 9, Icon.BOMB, Squad.BOMB),
        BOMB3(3, 3, 9, Icon.BOMB, Squad.BOMB),

        EMPTY(0, 0, 10, Icon.WHITESQUARE, "");

        private final int count;
        private final int value;
        private final int group;
        private final Icon icon;
        private final String resource;

        public String getText() {
            return count > 1 ? String.format("%d%s за %d%s", count, icon.get(), value, Icon.MONEYBAG.get())
                    : String.format("%s за %d%s", icon.get(), value, Icon.MONEYBAG.get());
        }

        public static Item getGood(String string) {
            return Arrays.stream(Item.values()).filter(g -> g.getText().equals(string)).findFirst().orElse(EMPTY);
        }
    }
}