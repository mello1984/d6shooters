package game.d6shooters.users;

import game.d6shooters.game.DicesCup;
import game.d6shooters.game.PokerDices;
import game.d6shooters.game.Squad;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User implements Serializable {
    final long chatId;
    String userName;
    Squad squad;
    DicesCup dicesCup;
    PokerDices pokerDices;
    List<List<String>> buttons;

    public User(long chatId, String userName) {
        this.chatId = chatId;
        this.userName = userName;
        squad = new Squad();
        dicesCup = new DicesCup();
        buttons = new ArrayList<>();
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
