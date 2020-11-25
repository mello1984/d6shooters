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

import java.util.*;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TownShop {
    final Set<Item> items;
    Item specialItem;
    boolean canGamingPoker;
    public static final String POKER = String.format("%s Сыграть в покер", Icon.POKER.get());
    public static final String REJECT = "Ехать дальше";

    public TownShop() {
        items = new LinkedHashSet<>(Arrays.asList(Item.values()));
        canGamingPoker = true;
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
        if (canGamingPoker && (squad.getGold() > 0 || squad.getAmmo() > 0 || squad.getFood() > 0)) line3.add(POKER);
        line3.add(REJECT);

        List<List<String>> buttons = new ArrayList<>();
        buttons.add(line1);
        buttons.add(line2);
        buttons.add(line3);
        return buttons;
    }

    protected List<String> getStandardItemButtons(Squad squad) {
        int gold = squad.getGold();
        List<String> goods = new ArrayList<>();
        if (items.contains(Item.FOOD1) && gold >= Item.FOOD1.getValue() && squad.getFood() < 12)
            goods.add(Item.FOOD1.getText());
        if (items.contains(Item.FOOD2) && gold >= Item.FOOD2.getValue() && squad.getFood() < 10)
            goods.add(Item.FOOD2.getText());
        if (items.contains(Item.AMMO1) && gold >= Item.AMMO1.getValue() && squad.getAmmo() < 5)
            goods.add(Item.AMMO1.getText());
        if (items.contains(Item.AMMO2) && gold >= Item.AMMO2.getValue() && squad.getAmmo() < 4)
            goods.add(Item.AMMO2.getText());
        if (items.contains(Item.HIRE1) && gold >= Item.HIRE1.getValue() && squad.getShooters() < 12)
            goods.add(Item.HIRE1.getText());
        if (items.contains(Item.HIRE2) && gold >= Item.HIRE2.getValue() && squad.getShooters() < 11)
            goods.add(Item.HIRE2.getText());
        if (items.contains(Item.HIRE3) && gold >= Item.HIRE3.getValue() && squad.getShooters() < 10)
            goods.add(Item.HIRE3.getText());
        return goods;
    }

    protected List<String> getSpecialItemButtons(Squad squad) {
        return switch (DicesCup.getD6Int()) {
            case 1 -> setSpecialItem(squad.getGold(), squad.isCompass(), Item.COMPASS);
            case 2 -> setSpecialItem(squad.getGold(), squad.isHunter(), Item.HUNTER);
            case 3 -> setSpecialItem(squad.getGold(), squad.isMap(), Item.MAP);
            case 4 -> setSpecialItem(squad.getGold(), squad.isBinocular(), Item.BINOCULAR);
            case 5 -> setSpecialItem(squad.getGold(), squad.isPill(), Item.PILL);
            case 6 -> {
                specialItem = Item.BOMB1;
                List<String> list = new ArrayList<>();
                if (squad.getBomb() < 3 && squad.getGold() >= Item.BOMB1.value) list.add(Item.BOMB1.getText());
                if (squad.getBomb() < 2 && squad.getGold() >= Item.BOMB2.value) list.add(Item.BOMB2.getText());
                if (squad.getBomb() == 0 && squad.getGold() >= Item.BOMB3.value) list.add(Item.BOMB3.getText());
                yield list;
            }
            default -> new ArrayList<>();
        };
    }

    protected List<String> setSpecialItem(int gold, boolean squadItem, Item item) {
        specialItem = item;
        List<String> list = new ArrayList<>();
        if (!squadItem && gold >= item.value) list.add(item.getText());
        return list;
    }


    @AllArgsConstructor
    @Getter
    public enum Item {
        FOOD1(2, 1, 1, Icon.FOOD),
        FOOD2(5, 2, 1, Icon.FOOD),
        AMMO1(2, 1, 2, Icon.AMMO),
        AMMO2(5, 2, 2, Icon.AMMO),
        HIRE1(1, 1, 3, Icon.GUNFIGHTER),
        HIRE2(2, 2, 3, Icon.GUNFIGHTER),
        HIRE3(3, 3, 3, Icon.GUNFIGHTER),

        COMPASS(1, 2, 4, Icon.COMPASS),
        HUNTER(1, 3, 5, Icon.HUNTER),
        MAP(1, 3, 6, Icon.MAP),
        BINOCULAR(1, 2, 7, Icon.BINOCULAR),
        PILL(1, 2, 8, Icon.PILL),
        BOMB1(1, 1, 9, Icon.BOMB),
        BOMB2(2, 2, 9, Icon.BOMB),
        BOMB3(3, 3, 9, Icon.BOMB),

        EMPTY(0, 0, 10, Icon.WHITESQUARE);

        private final int count;
        private final int value;
        private final int group;
        private final Icon icon;

        public String getText() {
            return count > 1 ? String.format("%d%s за %d%s", count, icon.get(), value, Icon.MONEYBAG.get())
                    : String.format("%s за %d%s", icon.get(), value, Icon.MONEYBAG.get());
        }

        public static Item getGood(String string) {
            return Arrays.stream(Item.values()).filter(g -> g.getText().equals(string)).findFirst().orElse(EMPTY);
        }
    }
}