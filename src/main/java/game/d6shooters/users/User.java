package game.d6shooters.users;

import game.d6shooters.game.DicesCup;
import game.d6shooters.game.Squad;

public class User {
    final long chatId;
    String userName;
    Squad squad;
    DicesCup dicesCup;

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
