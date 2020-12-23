package game.d6shooters.users;

import game.d6shooters.bot.DataBase;
import lombok.Getter;

import java.util.*;

@Getter
public class Users {
    private final Map<Long, User> userMap = DataBase.getInstance().loadUserMap(); //userId, User
}