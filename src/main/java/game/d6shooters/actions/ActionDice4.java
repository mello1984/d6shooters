package game.d6shooters.actions;

import game.d6shooters.bot.*;
import game.d6shooters.game.DicesCup;
import game.d6shooters.game.Game;
import game.d6shooters.game.Squad;
import game.d6shooters.game.SquadState;
import game.d6shooters.users.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.ArrayList;
import java.util.List;

public class ActionDice4 implements Action {

    @Override
    public void action(User user) {
        DicesCup dicesCup = user.getDicesCup();
        Squad squad = user.getSquad();
        squad.actionList = new ArrayList<>();
        int dice4count = (int) dicesCup.diceList.stream().filter(dice -> dice.getValue() == 4 && !dice.isUsed()).count();
        int dice5count = (int) dicesCup.diceList.stream().filter(dice -> dice.getValue() == 5 && !dice.isUsed()).count();
        int dice6count = (int) dicesCup.diceList.stream().filter(dice -> dice.getValue() == 6 && !dice.isUsed()).count();

        SendMessageTemplate template = new SendMessageTemplate();
        List<String> buttons = new ArrayList<>();
        if (dice4count > 0) {

            if (dice6count > 0) {
                buttons.add(Squad.SquadAction.HIDE.name());
                buttons.add(Squad.SquadAction.GUNFIGHT.name());
            }
            if (dice5count > 0) {
                buttons.add(Squad.SquadAction.SHELTER.name());
            }
            if (dice4count >= 2) {
                buttons.add(Squad.SquadAction.PATHFINDING.name());
            }
            buttons.add("REJECT");

            if (buttons.size() > 0) {
                SendMessage sendMessage = template.getSendMessageOneLineButtons(user.getChatId(),
                        "Необходимо распределить " + dice4count + " '4', будьте внимательны",
                        buttons.toArray(new String[0]));
                D6ShootersBot.senderMessage.sendMessage(sendMessage);
            }
        }

        if (buttons.size() <= 1) {
            user.getSquad().squadState = SquadState.CHECKHEAT;
            System.out.println(SquadState.CHECKHEAT);
        }
    }

    public void processMessage(User user, Message message) {
        if (message.getText().equals(Squad.SquadAction.HIDE.name())) {
            useDice(user, 6);
            if ((int) user.getDicesCup().diceList.stream().filter(dice -> dice.getValue() == 6 && !dice.isUsed()).count() > 0)
                useDice(user, 6);
            useDice(user, 4);
            user.getSquad().addPeriod(1);
        }
        if (message.getText().equals(Squad.SquadAction.SHELTER.name())) {
            useDice(user, 5);
            useDice(user, 4);
        }
        if (message.getText().equals(Squad.SquadAction.GUNFIGHT.name())) {
            useDice(user, 4);
            user.getSquad().actionList.add(Squad.SquadAction.GUNFIGHT);
        }
        if (message.getText().equals(Squad.SquadAction.PATHFINDING.name())) {
            useDice(user, 4);
            useDice(user, 4);
            user.getSquad().actionList.add(Squad.SquadAction.PATHFINDING);
        }
        if (message.getText().equals("REJECT")) {
            user.getDicesCup().diceList.stream()
                    .filter(dice -> dice.getValue() == 4)
                    .forEach(dice -> dice.setUsed(true));
        }
        SendMessageTemplate template = new SendMessageTemplate();
        D6ShootersBot.senderMessage.sendMessage(template.dicesString(user.getChatId(), user.getDicesCup()));
        user.getActionManager().doActions();
    }

    private void useDice(User user, int value) {
        user.getDicesCup().diceList.stream()
                .filter(dice -> dice.getValue() == value && !dice.isUsed())
                .findFirst().orElseThrow(() -> new IllegalArgumentException("No '" + value + "' in stream allocation"))
                .setUsed(true);
    }
}
