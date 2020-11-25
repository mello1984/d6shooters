package game.d6shooters.actions;

import game.d6shooters.bot.Bot;
import game.d6shooters.game.DicesCup;
import game.d6shooters.game.Squad;
import game.d6shooters.game.SquadState;
import game.d6shooters.users.User;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Log4j2
public class ActionDice6 extends AbstractAction {

    public ActionDice6(Bot bot) {
        super(bot);
    }

    private static final String TEXT1 = "В перестрелке потеряли %d стрелков.";
    private static final String TEXT2 = "Удача на нашей стороне, в перестрелке никого не потеряли.";
    private static final String TEXT3 = "Пытаемся оторваться от погони, бросок бандитов: [%s], убито: %d";
    private static final String TEXT4 = "Бросок бандитов: [%s], бросок стрелков: [%s], боеприпасы: %d, вооружение: %d, итог: [%d:%d] %s";
    private static final String TEXT5 = "бандит убит";
    private static final String TEXT6 = "погиб стрелок";

    @Override
    public void action(User user) {
        Squad squad = user.getSquad();
        int dice6count = user.getDicesCup().getCountActiveDiceCurrentValue(6);

        List<Integer> band = new ArrayList<>();
        int killedShooters;
        if (squad.getGunfight() == 0 && dice6count > 0) {
            killedShooters = IntStream.rangeClosed(1, dice6count).map(i -> DicesCup.getD6Int())
                    .peek(band::add)
                    .map(d -> d >= 3 ? 1 : 0).sum();
            killedShooters = Math.min(killedShooters, squad.getShooters());
            bot.send(template.getSendMessageNoButtons(user.getChatId(), String.format(TEXT3,
                    band.stream().map(String::valueOf).collect(Collectors.joining(", ")), killedShooters))); // Поставить условие на отправку сообщения
        } else {
            killedShooters = getKilledShooters(user);
        }

        if (killedShooters > 0) {
            bot.send(template.getSendMessageWithButtons(user.getChatId(), String.format(TEXT1, killedShooters)));
            squad.addShooters(-killedShooters);
        } else if (dice6count > 0) {
            bot.send(template.getSendMessageWithButtons(user.getChatId(), TEXT2));
        }

        squad.setSquadState(SquadState.MOVE);
        user.getActionManager().doActions();
    }

    private int getKilledShooters(User user) {
        int killedShooters = 0;
        while (user.getDicesCup().getCountActiveDiceCurrentValue(6) > 0 && user.getSquad().getGunfight() > 0) {
            if (isSquadWinner(user)) {
                useDice(user, 6);
            } else {
                user.getSquad().addGunfight(-1);
                killedShooters++;
            }
        }
        if (user.getSquad().getAmmo() > 0) user.getSquad().addAmmo(-1);
        return killedShooters;
    }

    private boolean isSquadWinner(User user) {
        boolean out;
        int bandStrength;
        int squadStrength;
        List<Integer> band;
        List<Integer> squad;
        do {
            band = new ArrayList<>();
            squad = new ArrayList<>();
            bandStrength = IntStream.rangeClosed(1, user.getDicesCup().getCountActiveDiceCurrentValue(6)).map(i -> DicesCup.getD6Int())
                    .peek(band::add).sum();
            squadStrength = IntStream.rangeClosed(1, user.getSquad().getGunfight()).map(i -> DicesCup.getD6Int())
                    .peek(squad::add)
                    .sum() + user.getSquad().getAmmo() + user.getSquad().getBomb();
            out = squadStrength > bandStrength;
        } while (bandStrength == squadStrength);

        bot.send(template.getSendMessageNoButtons(user.getChatId(), String.format(TEXT4,
                band.stream().map(String::valueOf).collect(Collectors.joining(", ")),
                squad.stream().map(String::valueOf).collect(Collectors.joining(", ")),
                user.getSquad().getAmmo(),
                user.getSquad().getBomb(),
                bandStrength,
                squadStrength,
                out ? TEXT5 : TEXT6)));

        return out;
    }
}