package database;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Connection {
    private java.sql.Connection connection;

    public void connection() throws Exception{
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
}
