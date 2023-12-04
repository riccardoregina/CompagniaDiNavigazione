package database;

import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnessioneDB {
    private java.sql.Connection connection = null;

    private void connection() throws Exception{
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("DB non trovato.");
            e.printStackTrace();
        }

        connection = null;

        try {
            String url = "jdbc:postgresql://localhost:5432/postgres";
            connection = DriverManager.getConnection(url,"postgres", "progetto");
        } catch (SQLException e) {
            System.out.println("Connessione fallita.");
            e.printStackTrace();
        }

        if (connection != null) {
            System.out.println("Connessione effettuata.");
        } else {
            System.out.println("Connessione non riuscita.");
        }
    }

    public java.sql.Connection getConnection() throws SQLException{
        try {
            if (connection == null || connection.isClosed()) {
                connection();
            } else {
                System.out.println("Una connessione e' gia attiva.");
            }
        } catch (SQLException e) {
            System.out.println("Connessione fallita.");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}
