package game.d6shooters.actions;

import game.d6shooters.bot.Bot;
import game.d6shooters.game.DicesCup;
import game.d6shooters.game.Squad;
import game.d6shooters.game.SquadState;
import game.d6shooters.users.User;
import lombok.extern.log4j.Log4j2;
import org.telegram.telegrambots.meta.api.objects.Message;

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
    private static final String TEXT7 = "Охотник покинул ваш отряд";
    private static final String TEXT8 = "В перестрелке потеряли %d стрелков, можно использовать медикаменты, чтобы их спасти.";
    private static final String TEXT9 = "Использовать медикаменты";
    private static final String TEXT10 = "Не использовать медикаменты";

    @Override
    public void action(User user) {
        int dice6count = user.getDicesCup().getCountActiveDiceCurrentValue(6);
        if (dice6count != 0) {
            int killedShooters = user.getSquad().getResource(Squad.GUNFIGHT) == 0 ? getKilledShootersNoShootout(user) : getKilledShootersInShootout(user);
            if (killedShooters > 0) {

                if (user.getSquad().hasResource(Squad.PILL)) {
                    user.getSquad().setResource(Squad.KILLED_SHOOTERS, killedShooters);
                    bot.send(template.getSendMessageWithButtons(user.getChatId(), String.format(TEXT8, killedShooters), TEXT9, TEXT10));
                    return;
                }

                bot.send(template.getSendMessageWithButtons(user.getChatId(), String.format(TEXT1, killedShooters)));
                user.getSquad().addResource(Squad.SHOOTER, -killedShooters);
            } else bot.send(template.getSendMessageWithButtons(user.getChatId(), TEXT2));
        }

        if (user.getSquad().getResource(Squad.SHOOTER) <= 1) {
            bot.send(template.getSendMessageNoButtons(user.getChatId(), TEXT7));
            user.getSquad().setResource(Squad.HUNTER, 0);
        }

        if (!user.getSquad().hasResource(Squad.SHOOTER)) user.getSquad().setSquadState(SquadState.ENDGAME);
        else user.getSquad().setSquadState(SquadState.MOVE);
        log.debug(String.format("SquadState %s -> MOVE", user.getSquad().getSquadState()));
        user.getActionManager().doActions();
    }

    protected int getKilledShootersNoShootout(User user) {
        List<Integer> band = new ArrayList<>();
        int killedShooters = IntStream.rangeClosed(1, user.getDicesCup().getCountActiveDiceCurrentValue(6)).map(i -> DicesCup.getD6Int())
                .peek(band::add)
                .map(d -> d >= 3 ? 1 : 0).sum();
        killedShooters = Math.min(killedShooters, user.getSquad().getResource(Squad.SHOOTER));
        bot.send(template.getSendMessageNoButtons(user.getChatId(), String.format(TEXT3,
                band.stream().map(String::valueOf).collect(Collectors.joining(", ")), killedShooters)));
        return killedShooters;
    }

    protected int getKilledShootersInShootout(User user) {
        int killedShooters = 0;
        while (user.getDicesCup().getCountActiveDiceCurrentValue(6) > 0 && user.getSquad().getResource(Squad.GUNFIGHT) > 0) {
            if (isSquadWinner(user)) {
                useDice(user, 6);
            } else {
                user.getSquad().addResource(Squad.GUNFIGHT, -1);
                killedShooters++;
            }
        }
        if (user.getSquad().getResource(Squad.AMMO) > 0) user.getSquad().addResource(Squad.AMMO, -1);
        return killedShooters;
    }

    private boolean isSquadWinner(User user) {
        boolean result;
        int bandStrength;
        int squadStrength;
        List<Integer> band;
        List<Integer> squad;
        do {
            band = new ArrayList<>();
            squad = new ArrayList<>();
            bandStrength = IntStream.rangeClosed(1, user.getDicesCup().getCountActiveDiceCurrentValue(6)).map(i -> DicesCup.getD6Int())
                    .peek(band::add).sum();
            squadStrength = IntStream.rangeClosed(1, user.getSquad().getResource(Squad.GUNFIGHT)).map(i -> DicesCup.getD6Int())
                    .peek(squad::add).sum()
                    + user.getSquad().getResource(Squad.AMMO) + user.getSquad().getResource(Squad.BOMB);
            result = squadStrength > bandStrength;
        } while (bandStrength == squadStrength);

        bot.send(template.getSendMessageNoButtons(user.getChatId(), String.format(TEXT4,
                band.stream().map(String::valueOf).collect(Collectors.joining(", ")),
                squad.stream().map(String::valueOf).collect(Collectors.joining(", ")),
                user.getSquad().getResource(Squad.AMMO),
                user.getSquad().getResource(Squad.BOMB),
                bandStrength,
                squadStrength,
                result ? TEXT5 : TEXT6)));
        return result;
    }

    public void processMessage(User user, Message message) {
        switch (message.getText()) {
            case TEXT9 -> {
                user.getSquad().setResource(Squad.PILL, 0);
                user.getSquad().setSquadState(SquadState.MOVE);
                user.getActionManager().doActions();
            }
            case TEXT10 -> {
                user.getSquad().addResource(Squad.SHOOTER, user.getSquad().getResource(Squad.KILLED_SHOOTERS));
                user.getSquad().setSquadState(SquadState.MOVE);
                user.getActionManager().doActions();
            }
            default -> bot.send(template.getSendMessageWithButtons(user.getChatId(), "Команда не распознана"));
        }

    }
}