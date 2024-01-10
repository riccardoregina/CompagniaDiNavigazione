package database;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The type Connessione db.
 */
public class ConnessioneDB {
    private static ConnessioneDB instance;
    private Connection connection = null;
    private String url = "jdbc:postgresql://ep-wispy-king-20409617.eu-central-1.aws.neon.tech/navigazione" + "?stringtype=unspecified";
    private String user = "riccardoregina04";
    private String password = METTIPW;
    private String driver = "org.postgresql.Driver";
    private ConnessioneDB() throws SQLException {
        try {
            //password = Files.readString(Path.of("password.txt"), Charset.defaultCharset());
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Impossibile trovare il driver.");
        } catch (SQLException e) {
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
