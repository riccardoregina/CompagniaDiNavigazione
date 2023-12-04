package postgresqlDAO;

import dao.CompagniaDAO;
import database.ConnessioneDB;
import model.CorsaRegolare;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class CompagniaDB implements CompagniaDAO {
    ConnessioneDB c;
    java.sql.Connection conn;

    public boolean accedi(String login, String pw) {
        boolean found = false;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "select *"
        + "from Compagnia"
        + "where login = ? and pw = ?";

        try {
            conn = c.getConnection();
        } catch (SQLException e) {
            System.out.println("Connessione fallita.");
            e.printStackTrace();
        }

        try {
            conn.prepareStatement(query);
            ps.setString(1, login);
            ps.setString(2, pw);
            ps.executeQuery();

            if (rs.next()) { //se esiste un risultato
                found = true;
            }
            rs.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Cancellazione fallita.");
            e.printStackTrace();
        }
        return found;
    }
    public void aggiungiCorsa(/*Attributi di corsaRegolare*/) {
        //deve chiamare una procedura

        try {
            conn = c.getConnection();
        } catch (SQLException e) { 
            System.out.println("Connessione fallita.");
            e.printStackTrace();
        }

        try {
            conn.executeProcedure() //qualcosa del genere

            conn.close();
        } catch (SQLException e) {
            System.out.println("Cancellazione fallita.");
            e.printStackTrace();
        }
    }
    public void modificaCorsaRegolare(/*Attributi di corsaRegolare*/) {
        PreparedStatement ps = null;
        String query = "update CorsaRegolare"
        + "set portoPartenza = ?, portoArrivo = ?, orarioPartenza = ?, orarioArrivo = ?, costoIntero = ?, scontoRidotto = ?, costoBagaglio = ?, costoPrevendita = ?, costoVeicolo = ?"
        + "where idCorsa = ?";

        try {
            conn = c.getConnection();
        } catch (SQLException e) { 
            System.out.println("Connessione fallita.");
            e.printStackTrace();
        }

        try {
            conn.prepareStatement(query);
            ps.setInt(1, idPortoPartenza);
            //etc.
            ps.setInt(10, idCorsa);
            ps.executeUpdate();

            conn.close();
        } catch (SQLException e) {
            System.out.println("Modifica fallita.");
            e.printStackTrace();
        }
    }
    public void modificaCorsaSpecifica(/*Attributi di corsaRegolare*/) {
        //...
    }
    public void cancellaCorsaRegolare(int idCorsa) {
        PreparedStatement ps = null;
        String query = "delete from CorsaRegolare where idCorsa = ?";

        try {
            conn = c.getConnection();
        } catch (SQLException e) { 
            System.out.println("Connessione fallita.");
            e.printStackTrace();
        }

        try {
            conn.prepareStatement(query);
            ps.setInt(1, idCorsa);
            
            ps.executeUpdate();

            conn.close();
        } catch (SQLException e) {
            System.out.println("Eliminazione fallita.");
            e.printStackTrace();
        }
    }
    public void cancellaCorsaSpecifica(int idCorsa, Date data) {
        PreparedStatement ps = null;
        String query = "update CorsaSpecifica"
        + "set cancellata = true"
        + "where idCorsa = ? and data = ?";

        try {
            conn = c.getConnection();
        } catch (SQLException e) { 
            System.out.println("Connessione fallita.");
            e.printStackTrace();
        }

        try {
            conn.prepareStatement(query);
            ps.setInt(1, idCorsa);
            ps.setDate(2, (java.sql.Date) data);
            
            ps.executeUpdate();

            conn.close();
        } catch (SQLException e) {
            System.out.println("Cancellazione fallita.");
            e.printStackTrace();
        }
    }
    public void segnalaRitardo(int idCorsa, Date data, int ritardo) {
        PreparedStatement ps = null;
        String query = "update CorsaSpecifica"
        + "set minutiRitardo = ?"
        + "where idCorsa = ? and data = ?";

        try {
            conn = c.getConnection();
        } catch (SQLException e) { 
            System.out.println("Connessione fallita.");
            e.printStackTrace();
        }

        try {
            conn.prepareStatement(query);
            ps.setInt(1, ritardo);
            ps.setInt(2, idCorsa);
            ps.setDate(3, (java.sql.Date) data);
            
            ps.executeUpdate();

            conn.close();
        } catch (SQLException e) {
            System.out.println("Segnalazione ritardo fallita.");
            e.printStackTrace();
        }
    }
    public void aggiungeNatante(/*Attributi di corsaRegolare*/) {
        PreparedStatement ps = null;
        String query = "insert into Natante"
        + "values (?,?,?,?,?)";

        try {
            conn = c.getConnection();
        } catch (SQLException e) { 
            System.out.println("Connessione fallita.");
            e.printStackTrace();
        }

        try {
            conn.prepareStatement(query);
            ps.setString(1, idCompagnia);
            //etc.
            
            ps.executeUpdate();

            conn.close();
        } catch (SQLException e) {
            System.out.println("Aggiunta fallita.");
            e.printStackTrace();
        }
    }
    public void rimuoviNatante(String nomeNatante) {
        PreparedStatement ps = null;
        String query = "delete from Natante"
        + "where nome = ?";

        try {
            conn = c.getConnection();
        } catch (SQLException e) { 
            System.out.println("Connessione fallita.");
            e.printStackTrace();
        }

        try {
            conn.prepareStatement(query);
            ps.setString(1, nomeNatante);
            
            ps.executeUpdate();

            conn.close();
        } catch (SQLException e) {
            System.out.println("Eliminazione fallita.");
            e.printStackTrace();
        }
    }
}
