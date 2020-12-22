package game.d6shooters.users;

import game.d6shooters.bot.DataBase;
import lombok.Getter;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Getter
public class Users {
    private final Map<Long, User> userMap = loadUserMap(); //userId, User

    public ArrayListValuedHashMap<Integer, Long> getTopResults(int max) {
        ArrayListValuedHashMap<Integer, Long> map = new ArrayListValuedHashMap<>();

        String query = "SELECT * FROM winners ORDER BY score DESC LIMIT " + max;
        try {
            DataBase dataBase = DataBase.getInstance();
            ResultSet resultSet = dataBase.executeQuery(query);
            while (resultSet.next()) {
                Integer score = resultSet.getInt("score");
                Long userId = resultSet.getLong("user_id");
                map.put(score, userId);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return map;
    }

    public ArrayList<Integer> getUserResults(User user) {
        ArrayList<Integer> list = new ArrayList<>();

        String query = "SELECT * FROM winners WHERE user_id= " + user.getChatId() + " ORDER BY score DESC ";
        try {
            DataBase dataBase = DataBase.getInstance();
            ResultSet resultSet = dataBase.executeQuery(query);
            while (resultSet.next()) {
                Integer score = resultSet.getInt("score");
                list.add(score);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return list;
    }

    public void saveWinner(int score, User user) {
        String query = "INSERT INTO winners (score, user_id) VALUES (?, ?)";
        DataBase dataBase = DataBase.getInstance();
        Connection connection = dataBase.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, score);
            statement.setLong(2, user.getChatId());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void saveUsersToUserMap() {
        DataBase dataBase = DataBase.getInstance();
        userMap.values().forEach(dataBase::saveUserToUserMap);
    }

    public void saveUserToUserMap(User user) {
        String query = "INSERT INTO users (user_id, user_data) VALUES (?, ?) ON CONFLICT (user_id) DO UPDATE SET user_data=EXCLUDED.user_data";
        DataBase dataBase = DataBase.getInstance();
        Connection connection = dataBase.getConnection();

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            PreparedStatement statement = connection.prepareStatement(query);
            oos.writeObject(user);
            oos.flush();
            byte[] userBytes = baos.toByteArray();

            statement.setLong(1, user.getChatId());
            statement.setBytes(2, userBytes);
            statement.executeUpdate();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    private Map<Long, User> loadUserMap() {
        Map<Long, User> map = new HashMap<>();
        String query = "SELECT * FROM users";
        try {
            DataBase dataBase = DataBase.getInstance();
            ResultSet resultSet = dataBase.executeQuery(query);
            while (resultSet.next()) {
                Long chatId = resultSet.getLong("user_id");
                byte[] userBytes = resultSet.getBytes("user_data");

                try (ByteArrayInputStream bais = new ByteArrayInputStream(userBytes);
                     ObjectInputStream ois = new ObjectInputStream(bais)) {
                    User user = (User) ois.readObject();
                    map.put(chatId, user);
                } catch (IOException | ClassNotFoundException throwables) {
                    throwables.printStackTrace();
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return map;
    }
}

//    public ArrayList<Integer> getWins(User user) {
//        return winners.entries().stream()
//                .filter(e -> e.getValue() == user.getChatId())
//                .map(Map.Entry::getKey)
//                .sorted(Comparator.reverseOrder())
//                .collect(Collectors.toCollection(ArrayList::new));
//    }

//    private MultiValuedMap<Integer, Long> winners = loadWinnersMap();//Scores, userId


//    private ArrayListValuedHashMap<Integer, Long> loadWinnersMap() {
//        ArrayListValuedHashMap<Integer, Long> map = new ArrayListValuedHashMap<>();
//
//        String query = "SELECT * FROM winners";
//        try {
//            DataBase dataBase = DataBase.getInstance();
//            ResultSet resultSet = dataBase.executeQuery(query);
//            while (resultSet.next()) {
//                Integer score = resultSet.getInt("score");
//                Long userId = resultSet.getLong("user_id");
//                map.put(score, userId);
//            }
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//
//        return map;
//    }