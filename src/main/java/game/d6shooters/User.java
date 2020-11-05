package game.d6shooters;

import game.d6shooters.game.Squad;

public class User {
    final long chatId;
    String userName;
    Squad squad;

    public User(long chatId, String userName) {
        this.chatId = chatId;
        this.userName = userName;
        squad = new Squad();
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
}
