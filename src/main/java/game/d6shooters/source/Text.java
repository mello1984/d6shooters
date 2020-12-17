package game.d6shooters.source;

import game.d6shooters.bot.DataBase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public enum Text {
    UNKNOWN_COMMAND,
    CROSS_ROAD,
    DICE2HUNT,
    DICE3GOLD,
    END_TURN,
    DICE5HEAT1, DICE5HEAT2,
    DICE6TEXT1, DICE6TEXT2, DICE6TEXT3, DICE6TEXT4, DICE6TEXT5, DICE6TEXT6, DICE6TEXT7, DICE6TEXT8,
    END_GAME_WIN, END_GAME_LOSE,
    EVENT_TEXT1, EVENT_TEXT2, EVENT_TEXT3, EVENT_TEXT4, EVENT_TEXT5,
    FEEDING1,
    POKER_TEXT1, POKER_TEXT2, POKER_TEXT3, POKER_LOSE, POKER_WIN, POKER_DRAW,
    REROLL_DICES,
    ALLOCATE4,


    ;

    private static final Random random = new Random();
    private static final Map<Text, List<String>> map = new HashMap<>();

    static {
        DataBase dataBase = DataBase.getInstance();
        try {
            ResultSet resultSet = dataBase.executeQuery("SELECT * FROM strings");
            while (resultSet.next()) {
                Text key = Text.valueOf(resultSet.getString("key"));
                String text = resultSet.getString("value");
                if (map.containsKey(key)) map.get(key).add(text);
                else map.put(key, new ArrayList<>(Collections.singletonList(text)));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static String getText(Text key) {
        List<String> list = map.get(key);
        return list.get(random.nextInt(list.size()));
    }

    public static String getText(Text key, Integer... i) {
        List<String> list = map.get(key);
        String text = list.get(random.nextInt(list.size()));
        return i.length > 0 ? String.format(text, i) : text;
    }

    public static String getText(Text key, String... s) {
        List<String> list = map.get(key);
        String text = list.get(random.nextInt(list.size()));
        return s.length > 0 ? String.format(text, s) : text;
    }

    public static List<String> getList(Text key) {
        return map.get(key);
    }
}

//        map.put(Key.DICE2HUNT, Arrays.asList("На охоте добыли %d провизии", "Подстрелили бизона, приготовили %d пайков"));
//        map.put(Key.DICE3GOLD, Arrays.asList("На рудниках добыли %d золота"));