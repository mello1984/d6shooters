package game.d6shooters.bot;

import game.d6shooters.users.User;
import lombok.Getter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataBase {
    private static final Logger logger = Logger.getLogger(DataBase.class.getName());
    private static DataBase instance = null;
    @Getter
    private Connection connection;

    private DataBase() {
        connection = setConnection();
    }

    private Connection setConnection() {
        Optional<Connection> optional = Optional.empty();
        while (optional.isEmpty()) {
            try {
                URI dbUri = new URI(System.getenv("telegrambotJDBC_DATABASE_URL"));
//                URI dbUri = new URI(System.getenv(""));
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
        String query = "INSERT INTO users (user_id, user_data) VALUES (?, ?) ON CONFLICT (user_id) DO UPDATE SET user_data=EXCLUDED.user_data";
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


}


