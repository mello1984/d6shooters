package game.d6shooters.actions;

import game.d6shooters.Main;
import game.d6shooters.game.DicesCup;
import game.d6shooters.game.Squad;
import game.d6shooters.game.SquadState;
import game.d6shooters.source.Button;
import game.d6shooters.source.Text;
import game.d6shooters.users.User;
import lombok.extern.log4j.Log4j2;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Log4j2
public class ActionDice6 extends AbstractAction {

    @Override
    public void action(User user) {
        int dice6count = user.getDicesCup().getCountActiveDiceCurrentValue(6);
        if (dice6count > 0) {
            int killedShooters = user.getSquad().hasResource(Squad.GUNFIGHT) ?
                    getKilledShootersInShootout(user) :
                    getKilledShootersNoShootout(user);

            if (killedShooters > 0) {
                if (user.getSquad().hasResource(Squad.PILL)) {
                    user.getSquad().setResource(Squad.KILLED_SHOOTERS, killedShooters);
                    Main.bot.send(template.getSendMessageWithButtons(user.getChatId(), Text.getText(Text.DICE6TEXT8, killedShooters), Button.TEXT9.get(), Button.TEXT10.get()));
                    return;
                } else {
                    user.getSquad().addResource(Squad.SHOOTER, -killedShooters);
                    Main.bot.send(template.getSendMessageWithButtons(user.getChatId(), Text.getText(Text.DICE6TEXT1, killedShooters)));
                }
            } else Main.bot.send(template.getSendMessageWithButtons(user.getChatId(), Text.getText(Text.DICE6TEXT2)));
        }

        removeHunterIfTooShootersLeft(user);
        SquadState nextState = user.getSquad().hasResource(Squad.SHOOTER) ? SquadState.MOVE : SquadState.ENDGAME;
        user.getSquad().setSquadState(nextState);
        Main.actionManager.doActions(user);
    }

    protected void removeHunterIfTooShootersLeft(User user) {
        if (user.getSquad().getResource(Squad.SHOOTER) <= 1) {
            user.getSquad().setResource(Squad.HUNTER, 0);
            Main.bot.send(template.getSendMessageNoButtons(user.getChatId(), Text.getText(Text.DICE6TEXT7)));
        }
    }

    protected int getKilledShootersNoShootout(User user) {
        List<Integer> band = new ArrayList<>();
        int killedShooters = IntStream.rangeClosed(1, user.getDicesCup().getCountActiveDiceCurrentValue(6)).map(i -> DicesCup.getD6Int())
                .peek(band::add)
                .map(d -> d >= 3 ? 1 : 0).sum();
        killedShooters = Math.min(killedShooters, user.getSquad().getResource(Squad.SHOOTER));
        Main.bot.send(template.getSendMessageNoButtons(user.getChatId(), String.format(Text.getText(Text.DICE6TEXT3),
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

        Main.bot.send(template.getSendMessageNoButtons(user.getChatId(), String.format(Text.getText(Text.DICE6TEXT4),
                band.stream().map(String::valueOf).collect(Collectors.joining(", ")),
                squad.stream().map(String::valueOf).collect(Collectors.joining(", ")),
                user.getSquad().getResource(Squad.AMMO),
                user.getSquad().getResource(Squad.BOMB),
                bandStrength,
                squadStrength,
                result ? Text.getText(Text.DICE6TEXT5) : Text.getText(Text.DICE6TEXT6))));
        return result;
    }

    @Override
    public void processMessage(User user, Message message) {
        Button button = Button.getButton(message.getText());
        switch (button) {
            case TEXT9 -> {
                user.getSquad().setResource(Squad.PILL, 0);
                user.getSquad().setSquadState(SquadState.MOVE);
                Main.actionManager.doActions(user);
            }
            case TEXT10 -> {
                user.getSquad().addResource(Squad.SHOOTER, -user.getSquad().getResource(Squad.KILLED_SHOOTERS));
                user.getSquad().setSquadState(SquadState.MOVE);
                Main.actionManager.doActions(user);
            }
            default -> Main.bot.send(template.getSendMessageWithButtons(user.getChatId(), Text.getText(Text.UNKNOWN_COMMAND)));
        }
    }
}