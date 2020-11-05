package game.d6shooters.game;

import game.d6shooters.bot.ReceiverMessage;
import game.d6shooters.bot.ReceiverMessageConsole;
import game.d6shooters.users.User;
import game.d6shooters.bot.SenderMessage;
import game.d6shooters.bot.SenderMessageConsole;
import game.d6shooters.actions.ActionManager;

public class Game {
    public DicesCup dicesCup = new DicesCup();
    public Squad squad = new Squad();
    public ActionManager actionManager = new ActionManager(squad, dicesCup);
    public static SenderMessage senderMessage = new SenderMessageConsole();
    public static ReceiverMessage receiverMessage = new ReceiverMessageConsole();

    public void nextTurn(User user) {
        senderMessage.sendText(0L, dicesCup.getFirstTurnDices().toString());
        senderMessage.sendText(0L,"Введите номера кубиков для переброски или exit");
        String str = receiverMessage.get();
        while (!dicesCup.checkString(str)) {
            senderMessage.sendText(0L,"Некорректные данные, введите номера кубиков для переброски или exit");
            str = receiverMessage.get();
        }
        senderMessage.sendText(0L,dicesCup.getRerolledDices(str).toString());
        str = receiverMessage.get();
        while (!dicesCup.checkString(str)) {
            senderMessage.sendText(0L,"Некорректные данные, введите номера кубиков для переброски или exit");
            str = receiverMessage.get();
        }
        senderMessage.sendText(0L,dicesCup.getRerolledDices(str).toString());

        actionManager.doActions();

    }

    public void endGame() {

    }

}
