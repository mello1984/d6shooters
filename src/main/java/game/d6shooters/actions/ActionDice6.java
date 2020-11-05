package game.d6shooters.actions;

import game.d6shooters.game.DicesCup;
import game.d6shooters.game.Game;
import game.d6shooters.game.Squad;
import game.d6shooters.bot.SenderMessage;

import java.util.stream.IntStream;

public class ActionDice6 implements Action {
    SenderMessage senderMessage = Game.senderMessage;

    @Override
    public void action(Squad squad, DicesCup dicesCup) {
        int dice6 = dicesCup.getNumberDiceCurrentValue(6);
        int squadHide = (int) squad.actionList.stream().filter(a -> a == Squad.SquadAction.HIDE).count();
        if (squadHide > 0) {
            dice6 -= squadHide * 2;
            squad.setPeriod(squad.getPeriod() + squadHide);
        }

        if (dice6 <= 0) return;

        int squadGunfight = (int) squad.actionList.stream().filter(a -> a == Squad.SquadAction.GUNFIGHT).count();
        if (squadGunfight == 0) {
            for (int i = 0; i < dice6; i++) {
                if (DicesCup.getD6Int() >= 3) {
                    squad.setShooters(squad.getShooters() - 1);
                }
            }
        } else {
            while (dice6 > 0 && squadGunfight > 0) {
                if (isSquadWinner(squad, dice6, squadGunfight)) dice6--;
                else squadGunfight--;
            }
            squad.setShooters(squad.getShooters() - dice6);
            squad.setAmmo(squad.getAmmo() - 1);
        }
    }

    private boolean isSquadWinner(Squad squad, int dice6, int squadGunfight) {
        boolean out;
        int griggStrength;
        int squadStrength;
        do {
            griggStrength = IntStream.rangeClosed(1, dice6).map(i -> DicesCup.getD6Int()).sum();
            squadStrength = IntStream.rangeClosed(1, squadGunfight).map(i -> DicesCup.getD6Int()).sum() + squad.getAmmo();
            out = squadStrength > griggStrength;
        } while (griggStrength == squadStrength);
        return out;
    }
}