package game.d6shooters.actions;

import game.d6shooters.bot.Bot;
import game.d6shooters.bot.Icon;
import game.d6shooters.game.DicesCup;
import game.d6shooters.game.Squad;
import game.d6shooters.road.Place;
import game.d6shooters.road.RoadMap;
import game.d6shooters.road.TownShop;
import game.d6shooters.users.User;

import java.util.Arrays;

public class ActionTown extends AbstractAction {
    public ActionTown(Bot bot) {
        super(bot);
    }

    @Override
    public void action(User user) {
        Place place = user.getSquad().getPlace();
        TownShop townShop = place.getTownShop();


        bot.send(template.getSendMessageManyLineButtons(user.getChatId(),
                String.format("Вы вошли в город %s и можете поторговать или сыграть в покер.", place.getTownName()),
                townShop.getGoods(user)));
        

    }
//
//    private enum Action {
//        BUY2FOOD(String.format("Купить 2%s за 1%s", Icon.FOOD.get(), Icon.MONEYBAG.get())),
//        BUY5FOOD(String.format("Купить 5%s за 2%s", Icon.FOOD.get(), Icon.MONEYBAG.get())),
//        BUY2AMMO(String.format("Купить 2%s за 1%s", Icon.AMMO.get(), Icon.MONEYBAG.get())),
//        BUY5AMMO(String.format("Купить 5%s за 2%s", Icon.AMMO.get(), Icon.MONEYBAG.get())),
//        HIRE(String.format("Нанять 1%s за 1%s", Icon.GUNFIGHTER.get(), Icon.MONEYBAG.get())),
//        COMPASS(String.format("Купить компас %s за 2%s", Icon.COMPASS.get(), Icon.MONEYBAG.get())),
//        HUNTER(String.format("Нанять охотника %s за 3%s", Icon.HUNTER.get(), Icon.MONEYBAG.get())),
//        MAP(String.format("Купить карту золотых приисков %s за 2%s", Icon.MAP.get(), Icon.MONEYBAG.get())),
//        BINOCULAR(String.format("Купить бинокль %s за 2%s", Icon.BINOCULAR.get(), Icon.MONEYBAG.get())),
//        PILL(String.format("Купить медикаменты %s за %s", Icon.PILL.get(), Icon.MONEYBAG.get())),
//        BOMB(String.format("Улучшить вооружение %s за 1%s", Icon.BOMB.get(), Icon.MONEYBAG.get())),
//        POKER(String.format("Сыграть в покер %s", Icon.POKER.get())),
//
//        NONE("Отказаться"),
//        EMPTY("");
//
//        private String value;
//
//        Action(String value) {
//            this.value = value;
//        }
//
//        private String get() {
//            return value;
//        }
//
//        public static Action getAction(String string) {
//            return Arrays.stream(Action.values()).filter(a -> a.value.equals(string)).findFirst().orElse(EMPTY);
//        }
//    }
}
