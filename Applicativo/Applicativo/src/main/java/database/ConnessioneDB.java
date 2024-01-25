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
 * La classe ConnessioneDB si occupa di creare il collegamento tra l'applicativo
 * e il database, caricandone il driver ed altre informazioni attinte dal file dbconfig.properties, e
 * poi restituirlo secondo la policy 'singleton': non e' possibile ottenere due collegamenti attivi al db
 * nello stesso istante.
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

            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Impossibile accedere a dbconfig.properties");
            throw new SQLException("Impossibile accedere a dbconfig.properties");
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Impossibile trovare il driver.");
            throw new SQLException("Impossibile trovare il driver.");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Impossibile connettersi al server.");
            throw new SQLException("Impossibile connettersi al server.");
        }
    }

    /**
     * Restituisce un'istanza del collegamento al DB.
     *
     * @return l'istanza del collegamento
     * @throws SQLException
     */
    public static ConnessioneDB getInstance() throws SQLException {
        if (instance == null) {
            instance = new ConnessioneDB();
        } else if (instance.connection.isClosed()) {
            instance = new ConnessioneDB();
        }
        return instance;
    }

    /**
     * Restituisce la connessione al DB.
     *
     * @return un oggetto Connection: una connessione
     */
    public Connection getConnection() {
        return connection;
    }
}
