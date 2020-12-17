package game.d6shooters.bot;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataBase {
    private static final Logger logger = Logger.getLogger(DataBase.class.getName());
    private static DataBase instance = null;
    private Connection conn;

    private DataBase() {
        conn = setConnection();
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
        if (conn == null) return;
        try {
            conn.close();
            conn = null;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public boolean execute(String sql) throws SQLException {
        if (conn == null)
            throw new SQLException("Connection null!");
        Statement statement = conn.createStatement();
        boolean res = statement.execute(sql);
        statement.close();
        return res;
    }

    public int executeUpdate(String sql) throws SQLException {
        if (conn == null)
            throw new SQLException("Connection null!");
        Statement statement = conn.createStatement();
        int res = statement.executeUpdate(sql);
        statement.close();
        return res;
    }

    public ResultSet executeQuery(String sql) throws SQLException {
        if (conn == null)
            throw new SQLException("Connection null!");
        Statement statement = conn.createStatement();
        ResultSet res = statement.executeQuery(sql);
        statement.close();
        return res;
    }

    public static DataBase getInstance() {
        if (instance == null) instance = new DataBase();
        return instance;
    }
}
