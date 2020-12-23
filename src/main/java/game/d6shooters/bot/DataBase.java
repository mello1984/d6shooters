package game.d6shooters.bot;

import game.d6shooters.Main;
import game.d6shooters.source.Text;
import game.d6shooters.users.User;
import lombok.Getter;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataBase {
    private static final Logger logger = Logger.getLogger(DataBase.class.getName());
    private static DataBase instance = null;
    @Getter
    private Connection connection;

    private static final String USERS_TABLE = "users";
    private static final String USER_ID = "user_id";
    private static final String USER_DATA = "user_data";
    private static final String WINNERS_TABLE = "winners";
    private static final String SCORE = "score";
    private static final String STRINGS_TABLE = "strings";
    private static final String KEY = "key";
    private static final String VALUE = "value";


    private DataBase() {
        connection = setConnection();
    }

    private Connection setConnection() {
        Optional<Connection> optional = Optional.empty();
        while (optional.isEmpty()) {
            try {
                URI dbUri = new URI(System.getenv("telegrambotJDBC_DATABASE_URL"));
                String username = dbUri.getUserInfo().split(":")[0];
                String password = dbUri.getUserInfo().split(":")[1];
                String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() + "?sslmode=require";
                optional = Optional.of(DriverManager.getConnection(dbUrl, username, password));
                logger.log(Level.INFO, "Database Connection Initialized");
            } catch (URISyntaxException | SQLException e) {
                logger.log(Level.SEVERE, "Exception with connection to database", e);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            }
        }
        return optional.get();
    }

    public void closeConnection() {
        if (connection == null) return;
        try {
            connection.close();
            connection = null;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public boolean execute(String sql) throws SQLException {
        if (connection == null)
            throw new SQLException("Connection null!");
        Statement statement = connection.createStatement();
        boolean res = statement.execute(sql);
        statement.close();
        return res;
    }

    public int executeUpdate(String sql) throws SQLException {
        if (connection == null)
            throw new SQLException("Connection null!");
        Statement statement = connection.createStatement();
        int res = statement.executeUpdate(sql);
        statement.close();
        return res;
    }

    public ResultSet executeQuery(String sql) throws SQLException {
        if (connection == null)
            throw new SQLException("Connection null!");
        Statement statement = connection.createStatement();
        ResultSet res = statement.executeQuery(sql);
        statement.close();
        return res;
    }

    public static DataBase getInstance() {
        if (instance == null) instance = new DataBase();
        return instance;
    }


    public boolean saveUserToUserMap(User user) {
        String query = String.format("INSERT INTO %1$s (%2$s, %3$s) VALUES (?, ?) ON CONFLICT (%2$s) DO UPDATE SET %3$s=EXCLUDED.%3$s", USERS_TABLE, USER_ID, USER_DATA);
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
            return false;
        }
        return true;
    }

    public void saveUsersToUserMap() {
        Main.users.getUserMap().values().forEach(this::saveUserToUserMap);
    }

    public Map<Long, User> loadUserMap() {
        Map<Long, User> map = new HashMap<>();
        String query = String.format("SELECT * FROM %s", USERS_TABLE);
        try {
            ResultSet resultSet = executeQuery(query);
            while (resultSet.next()) {
                Long chatId = resultSet.getLong(USER_ID);
                byte[] userBytes = resultSet.getBytes(USER_DATA);

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

    public void saveWinner(int score, User user) {
        String query = String.format("INSERT INTO %s (%s, %s) VALUES (?, ?)", WINNERS_TABLE, SCORE, USER_ID);
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, score);
            statement.setLong(2, user.getChatId());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public ArrayList<Integer> getUserResults(User user) {
        ArrayList<Integer> list = new ArrayList<>();
        String query = String.format("SELECT * FROM %s WHERE %s= %d ORDER BY %s DESC", WINNERS_TABLE, USER_ID, user.getChatId(), SCORE);
        try {
            ResultSet resultSet = executeQuery(query);
            while (resultSet.next()) {
                list.add(resultSet.getInt(SCORE));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    public ArrayListValuedHashMap<Integer, Long> getTopResults(int max) {
        ArrayListValuedHashMap<Integer, Long> map = new ArrayListValuedHashMap<>();
        String query = String.format("SELECT * FROM %s ORDER BY %s DESC LIMIT %d", WINNERS_TABLE, SCORE, max);
        try {
            ResultSet resultSet = executeQuery(query);
            while (resultSet.next()) {
                Integer score = resultSet.getInt(SCORE);
                Long userId = resultSet.getLong(USER_ID);
                map.put(score, userId);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return map;
    }

    public Map<Text, List<String>> loadTextMap() {
        Map<Text, List<String>> map = new HashMap<>();

        try {
            String query = String.format("SELECT * FROM %s", STRINGS_TABLE);
            ResultSet resultSet = executeQuery(query);
            while (resultSet.next()) {
                Text key = Text.valueOf(resultSet.getString(KEY));
                String text = resultSet.getString(VALUE);
                if (map.containsKey(key)) map.get(key).add(text);
                else map.put(key, new ArrayList<>(Collections.singletonList(text)));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return map;
    }
}
