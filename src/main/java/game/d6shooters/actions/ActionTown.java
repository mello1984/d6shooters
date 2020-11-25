package game.d6shooters.actions;

import game.d6shooters.bot.Bot;
import game.d6shooters.game.Squad;
import game.d6shooters.game.SquadState;
import game.d6shooters.road.Place;
import game.d6shooters.road.TownShop;
import game.d6shooters.users.User;
import lombok.extern.log4j.Log4j2;
import org.telegram.telegrambots.meta.api.objects.Message;

@Log4j2
public class ActionTown extends AbstractAction {
    private static final String TEXT1 = "Вы сейчас в городе %s";
    private static final String TEXT2 = "Вы успешно приобрели %s";

    public ActionTown(Bot bot) {
        super(bot);
    }

    @Override
    public void action(User user) {
        Place place = user.getSquad().getPlace();
        log.debug(String.format("Запущен ActionTown, place: %s, townshop: %s", place, place.getTownShop()));
        bot.send(template.getSendMessageWithButtons(user.getChatId(), String.format(TEXT1, place.getTownName()), place.getTownShop().getGoods(user)));
    }

    public void processMessage(User user, Message message) {
        Squad squad = user.getSquad();
        if (message.getText().equals(TownShop.POKER)) squad.setSquadState(SquadState.POKER1);
        if (message.getText().equals(TownShop.REJECT)) squad.setSquadState(SquadState.MOVE);

        TownShop.Item item = TownShop.Item.getGood(message.getText());
        if (squad.getGold() >= item.getValue()) {
            switch (item) {
                case FOOD1, FOOD2 -> squad.addFood(item.getCount());
                case AMMO1, AMMO2 -> squad.addAmmo(item.getCount());
                case HIRE1, HIRE2, HIRE3 -> squad.addShooters(item.getCount());
                case COMPASS -> squad.setCompass(true);
                case HUNTER -> squad.setHunter(true);
                case MAP -> squad.setMap(true);
                case BINOCULAR -> squad.setBinocular(true);
                case PILL -> squad.setPill(true);
                case BOMB1, BOMB2, BOMB3 -> squad.addBomb(item.getCount());
            }
            squad.addGold(-item.getValue());
            user.getSquad().getPlace().getTownShop().getItems().removeIf(i -> i.getGroup() == item.getGroup());
            if (item != TownShop.Item.EMPTY) {
                bot.send(template.getSendMessageNoButtons(user.getChatId(), String.format(TEXT2, item.getIcon().get())));
                bot.send(template.getSquadStateMessage(user.getChatId()));
            }
        }
        user.getActionManager().doActions();
    }
}