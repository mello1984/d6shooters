package game.d6shooters.actions;

import game.d6shooters.bot.SendMessageTemplate;
import game.d6shooters.game.DicesCup;
import game.d6shooters.game.Game;
import game.d6shooters.game.Squad;
import game.d6shooters.bot.SenderMessage;
import game.d6shooters.game.SquadState;
import game.d6shooters.users.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.stream.IntStream;

public class ActionDice6 extends AbstractAction {

    @Override
    public void action(User user) {
        DicesCup dicesCup = user.getDicesCup();
        Squad squad = user.getSquad();
        int dice6count = (int) dicesCup.diceList.stream().filter(dice -> dice.getValue() == 6 && !dice.isUsed()).count();
        if (dice6count <= 0) {
            squad.squadState = SquadState.MOVE;
            System.out.println(SquadState.GUNFIGHT + "->" + SquadState.MOVE);
            return;
        }

        int mod = 0;
        int squadGunfight = (int) squad.actionList.stream().filter(a -> a == Squad.SquadAction.GUNFIGHT).count();
        if (squadGunfight == 0) {
            mod = (int) IntStream.range(1, dice6count).map(i -> DicesCup.getD6Int()).filter(d -> d >= 3).count();
        } else {
            while (dice6count > 0 && squadGunfight > 0) {
                if (isSquadWinner(squad, dice6count, squadGunfight)) {
                    dice6count--;
                    useDice(user, 6);
                } else {
                    squadGunfight--;
                    mod++;
                }
            }
            squad.addAmmo(-1);
        }
        mod = Math.min(mod, squad.getShooters());
        if (mod > 0) {
            SendMessage sendMessage = template.getSendMessageOneLineButtons(user.getChatId(),
                    "В перестрелке потеряли " + mod + " стрелков.");
            senderMessage.sendMessage(sendMessage);
            squad.addShooters(-mod);
        } else {
            SendMessage sendMessage = template.getSendMessageOneLineButtons(user.getChatId(),
                    "В перестрелке никого не потеряли .");
            senderMessage.sendMessage(sendMessage);
        }
        squad.squadState = SquadState.MOVE;
        System.out.println(SquadState.GUNFIGHT + "->" + SquadState.MOVE);
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