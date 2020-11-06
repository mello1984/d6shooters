package game.d6shooters.actions;

import game.d6shooters.game.DicesCup;
import game.d6shooters.game.Squad;
import game.d6shooters.game.SquadState;
import game.d6shooters.users.User;

import java.util.stream.IntStream;

public class ActionDice6 extends AbstractAction {

    @Override
    public void action(User user) {
        Squad squad = user.getSquad();
        int dice6count = user.getDicesCup().getCountActiveDiceCurrentValue(6);

        int killedShooters;
        if (squad.actionList.stream().noneMatch(a -> a == Squad.SquadAction.GUNFIGHT)) {
            killedShooters = (int) IntStream.range(1, dice6count).map(i -> DicesCup.getD6Int()).filter(d -> d >= 3).count();
        } else {
            killedShooters = getKilledShooters(user);
        }
        killedShooters = Math.min(killedShooters, squad.getShooters());

        if (killedShooters > 0) {
            senderMessage.sendMessage(template.getSendMessageOneLineButtons(user.getChatId(),
                    "В перестрелке потеряли " + killedShooters + " стрелков."));
            squad.addShooters(-killedShooters);
        } else  if (dice6count > 0) {
            senderMessage.sendMessage(template.getSendMessageOneLineButtons(user.getChatId(),
                    "В перестрелке никого не потеряли ."));
        }

        squad.squadState = SquadState.MOVE;
        System.out.println(SquadState.GUNFIGHT + "->" + SquadState.MOVE);
        user.getActionManager().doActions();
    }

    private int getKilledShooters(User user) {
        int dice6count = user.getDicesCup().getCountActiveDiceCurrentValue(6);
        int squadGunfight = (int) user.getSquad().actionList.stream().filter(a -> a == Squad.SquadAction.GUNFIGHT).count();
        int killedShooters = 0;
        while (dice6count > 0 && squadGunfight > 0) {
            if (isSquadWinner(user)) {
                dice6count--;
                useDice(user, 6);
            } else {
                squadGunfight--;
                user.getSquad().actionList.remove(Squad.SquadAction.GUNFIGHT);
                killedShooters++;
            }
        }
        if (user.getSquad().getAmmo() > 0) user.getSquad().addAmmo(-1);
        return killedShooters;
    }

    private boolean isSquadWinner(User user) {
        boolean out;
        int griggStrength;
        int squadStrength;
        int squadGunfight = (int) user.getSquad().actionList.stream().filter(a -> a == Squad.SquadAction.GUNFIGHT).count();
        do {
            griggStrength = IntStream.rangeClosed(1, user.getDicesCup().getCountActiveDiceCurrentValue(6)).map(i -> DicesCup.getD6Int()).sum();
            squadStrength = IntStream.rangeClosed(1, squadGunfight).map(i -> DicesCup.getD6Int()).sum() + user.getSquad().getAmmo();
            out = squadStrength > griggStrength;
        } while (griggStrength == squadStrength);
        return out;
    }


}