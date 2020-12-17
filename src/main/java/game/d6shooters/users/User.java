package game.d6shooters.users;

import game.d6shooters.actions.ActionManager;
import game.d6shooters.game.DicesCup;
import game.d6shooters.game.PokerDices;
import game.d6shooters.game.Squad;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    final long chatId;
    String userName;
    Squad squad;
    transient DicesCup dicesCup;
    transient PokerDices pokerDices;
    transient ActionManager actionManager;
    transient List<List<String>> buttons;
    transient List<Integer> games;

    public User(long chatId, String userName) {
        this.chatId = chatId;
        this.userName = userName;
        squad = new Squad();
        dicesCup = new DicesCup();
        buttons = new ArrayList<>();
        games = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return chatId == user.chatId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatId);
    }
}
