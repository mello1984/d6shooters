package game.d6shooters.bot.handler;

import game.d6shooters.Main;
import game.d6shooters.bot.ButtonsType;
import game.d6shooters.game.Squad;
import game.d6shooters.game.SquadState;
import org.telegram.telegrambots.meta.api.objects.Message;

public class HandlerManager {
    public Handler chooseHandler(Message message) {
        long chatId = message.getChatId();
        if (!Main.users.userMap.containsKey(chatId)) {
            System.out.println("Choose StartTurnHandler");
            return new StartGameHandler();
        }

        Squad squad = Main.users.userMap.get(chatId).getSquad();
        if (message.getText().equals(ButtonsType.NEXT_TURN.name())
                || squad.squadState == SquadState.REGULAR
                || squad.squadState == SquadState.REROLL1
                || squad.squadState == SquadState.REROLL2) {
            System.out.println("Choose StartTurnHandler " + squad.squadState);
            return new StartTurnHandler();
        }

        System.out.println("Choose DefaultHandler");
        return new DefaultHandler();
    }
}
