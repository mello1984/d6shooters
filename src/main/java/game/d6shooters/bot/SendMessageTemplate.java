package game.d6shooters.bot;

import game.d6shooters.game.Squad;

public class SendMessageTemplate {

    String squadState(Squad squad) {
        return Icon.GUNFIGHTER.get() + " Отряд: " + squad.getShooters() + "\n" +
                Icon.FOOD.get() + " Еда: " + squad.getFood() + "\n" +
                Icon.AMMO.get() + " Боеприпасы: " + squad.getAmmo() + "\n" +
                Icon.MONEYBAG.get() + " Золото: " + squad.getGold() + "\n" +
                Icon.FOOTPRINTS.get() + " Пройдено: " + squad.getPath() + "\n" +
                Icon.CLOCK.get() + " День: " + squad.getPeriod();
    }

}
