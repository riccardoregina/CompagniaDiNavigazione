package database;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The type Connessione db.
 */
public class ConnessioneDB {
    private Logger logger = Logger.getLogger(this.getClass().getName());
    private static ConnessioneDB instance;
    private Connection connection = null;
    private ConnessioneDB() throws SQLException {
        Properties properties = new Properties();
        String filePath = "resources/dbconfig.properties";
        String url = null;
        String user = null;
        String password = null;
        String driver = null;
        try {
            InputStream inputStream = new FileInputStream(filePath);
            properties.load(inputStream);
            url = properties.getProperty("database.url");
            user = properties.getProperty("database.user");
            password = properties.getProperty("database.password");
            driver = properties.getProperty("database.driver");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Impossibile accedere a dbconfig.properties");
        }
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Impossibile trovare il driver.");
            throw new SQLException("Impossibile trovare il driver.");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Impossibile connettersi al server.");
            throw new SQLException("Impossibile connettersi al server.");
        }
    }

    public static ConnessioneDB getInstance() throws SQLException {
        try {
            if (instance == null) {
                instance = new ConnessioneDB();
            } else if (instance.connection.isClosed()) {
                instance = new ConnessioneDB();
            }
            return instance;
        } catch (SQLException e) {
            throw e;
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
