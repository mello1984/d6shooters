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
    public ActionTown(Bot bot) {
        super(bot);
    }

    @Override
    public void action(User user) {
        log.debug("Запущен ActionTown");
        Place place = user.getSquad().getPlace();
        log.debug(place);
        TownShop townShop = place.getTownShop();
        log.debug(townShop);

        bot.send(template.getSendMessageWithButtons(user.getChatId(),
                String.format("Вы вошли в город %s и можете поторговать или сыграть в покер.", place.getTownName()),
                townShop.getGoods(user)));
    }

    public void processMessage(User user, Message message) {
        Squad squad = user.getSquad();
        Place place = user.getSquad().getPlace();
        TownShop townShop = place.getTownShop();

        TownShop.Action action = TownShop.Action.getAction(message.getText());

        switch (action) {
            case BUY2FOOD -> {
                if (squad.getGold() >= TownShop.StandardGood.FOOD1.getValue()) {
                    squad.addFood(TownShop.StandardGood.FOOD1.getCount());
                    squad.addGold(-TownShop.StandardGood.FOOD1.getValue());
                    townShop.getStandardGoods().remove(TownShop.StandardGood.FOOD1);
                    townShop.getStandardGoods().remove(TownShop.StandardGood.FOOD2);
                }
            }
            case BUY5FOOD -> {
                if (squad.getGold() >= TownShop.StandardGood.FOOD2.getValue()) {
                    squad.addFood(TownShop.StandardGood.FOOD2.getCount());
                    squad.addGold(-TownShop.StandardGood.FOOD2.getValue());
                    townShop.getStandardGoods().remove(TownShop.StandardGood.FOOD1);
                    townShop.getStandardGoods().remove(TownShop.StandardGood.FOOD2);
                }
            }
            case BUY2AMMO -> {
                if (squad.getGold() >= TownShop.StandardGood.AMMO1.getValue()) {
                    squad.addAmmo(TownShop.StandardGood.AMMO1.getCount());
                    squad.addGold(-TownShop.StandardGood.AMMO1.getValue());
                    townShop.getStandardGoods().remove(TownShop.StandardGood.AMMO1);
                    townShop.getStandardGoods().remove(TownShop.StandardGood.AMMO2);
                }
            }
            case BUY5AMMO -> {
                if (squad.getGold() >= TownShop.StandardGood.AMMO2.getValue()) {
                    squad.addAmmo(TownShop.StandardGood.AMMO2.getCount());
                    squad.addGold(-TownShop.StandardGood.AMMO2.getValue());
                    townShop.getStandardGoods().remove(TownShop.StandardGood.AMMO1);
                    townShop.getStandardGoods().remove(TownShop.StandardGood.AMMO2);
                }
            }
            case HIRE1 -> {
                if (squad.getGold() >= TownShop.StandardGood.HIRE1.getValue()) {
                    squad.addShooters(TownShop.StandardGood.HIRE1.getCount());
                    squad.addGold(-TownShop.StandardGood.HIRE1.getValue());
                    townShop.getStandardGoods().remove(TownShop.StandardGood.HIRE1);
                    townShop.getStandardGoods().remove(TownShop.StandardGood.HIRE2);
                    townShop.getStandardGoods().remove(TownShop.StandardGood.HIRE3);
                }
            }
            case HIRE2 -> {
                if (squad.getGold() >= TownShop.StandardGood.HIRE2.getValue()) {
                    squad.addShooters(TownShop.StandardGood.HIRE2.getCount());
                    squad.addGold(-TownShop.StandardGood.HIRE2.getValue());
                    townShop.getStandardGoods().remove(TownShop.StandardGood.HIRE1);
                    townShop.getStandardGoods().remove(TownShop.StandardGood.HIRE2);
                    townShop.getStandardGoods().remove(TownShop.StandardGood.HIRE3);
                }
            }
            case HIRE3 -> {
                if (squad.getGold() >= TownShop.StandardGood.HIRE3.getValue()) {
                    squad.addShooters(TownShop.StandardGood.HIRE3.getCount());
                    squad.addGold(-TownShop.StandardGood.HIRE3.getValue());
                    townShop.getStandardGoods().remove(TownShop.StandardGood.HIRE1);
                    townShop.getStandardGoods().remove(TownShop.StandardGood.HIRE2);
                    townShop.getStandardGoods().remove(TownShop.StandardGood.HIRE3);
                }
            }
            case COMPASS -> {
                if (squad.getGold() >= TownShop.SpecialGood.COMPASS.getValue() && townShop.getSpecialGood() == TownShop.SpecialGood.COMPASS) {
                    squad.setCompass(true);
                    squad.addGold(-TownShop.SpecialGood.COMPASS.getValue());
                    townShop.setSpecialGood(TownShop.SpecialGood.NONE);
                }
            }
            case HUNTER -> {
                if (squad.getGold() >= TownShop.SpecialGood.COMPASS.getValue() && townShop.getSpecialGood() == TownShop.SpecialGood.COMPASS) {
                    squad.setHunter(true);
                    squad.addGold(-TownShop.SpecialGood.COMPASS.getValue());
                    townShop.setSpecialGood(TownShop.SpecialGood.NONE);
                }
            }
            case MAP -> {
                if (squad.getGold() >= TownShop.SpecialGood.MAP.getValue() && townShop.getSpecialGood() == TownShop.SpecialGood.MAP) {
                    squad.setMap(true);
                    squad.addGold(-TownShop.SpecialGood.MAP.getValue());
                    townShop.setSpecialGood(TownShop.SpecialGood.NONE);
                }
            }
            case BINOCULAR -> {
                if (squad.getGold() >= TownShop.SpecialGood.BINOCULAR.getValue() && townShop.getSpecialGood() == TownShop.SpecialGood.BINOCULAR) {
                    squad.setBinocular(true);
                    squad.addGold(-TownShop.SpecialGood.BINOCULAR.getValue());
                    townShop.setSpecialGood(TownShop.SpecialGood.NONE);
                }
            }
            case PILL -> {
                if (squad.getGold() >= TownShop.SpecialGood.PILL.getValue() && townShop.getSpecialGood() == TownShop.SpecialGood.PILL) {
                    squad.setPill(true);
                    squad.addGold(-TownShop.SpecialGood.PILL.getValue());
                    townShop.setSpecialGood(TownShop.SpecialGood.NONE);
                }
            }
            case BOMB -> {
                if (squad.getGold() >= TownShop.SpecialGood.BOMB.getValue() && townShop.getSpecialGood() == TownShop.SpecialGood.BOMB) {
                    squad.setBomb(1); /// Надо доделать до +3
                    squad.addGold(-TownShop.SpecialGood.BOMB.getValue());
                    townShop.setSpecialGood(TownShop.SpecialGood.NONE);
                }
            }
            case POKER -> {
                new ActionPoker(bot).action(user);
            }
            case NONE, EMPTY -> {
                squad.setSquadState(SquadState.MOVE);
            }
        }


        user.getActionManager().doActions();
    }

}
