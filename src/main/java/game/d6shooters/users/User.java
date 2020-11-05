package game.d6shooters.users;

import game.d6shooters.actions.ActionManager;
import game.d6shooters.game.DicesCup;
import game.d6shooters.game.Squad;

public class User {
    final long chatId;
    String userName;
    Squad squad;
    DicesCup dicesCup;
    ActionManager actionManager;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setSquad(Squad squad) {
        this.squad = squad;
    }

    public void setDicesCup(DicesCup dicesCup) {
        this.dicesCup = dicesCup;
    }

    public ActionManager getActionManager() {
        return actionManager;
    }

    public void setActionManager(ActionManager actionManager) {
        this.actionManager = actionManager;
    }

    public User(long chatId, String userName) {
        this.chatId = chatId;
        this.userName = userName;
        squad = new Squad();
        dicesCup = new DicesCup();
    }

    public long getChatId() {
        return chatId;
    }

    public String getUserName() {
        return userName;
    }

    public Squad getSquad() {
        return squad;
    }

    public DicesCup getDicesCup() {
        return dicesCup;
    }
}
