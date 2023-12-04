package postgresqlDAO;
import database.*;
import dao.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class ClienteDB implements UtenteDAO {
    ConnessioneDB c;
    java.sql.Connection conn;

    public boolean accedi(String login, String pw) {
        boolean found = false;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "select *"
        + "from Cliente"
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

    public ClienteTrovato retrieveCliente(String login) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "select *"
                + "from Cliente"
                + "where login = ?";

        try {
            conn = c.getConnection();
        } catch (SQLException e) {
            System.out.println("Connessione fallita.");
            e.printStackTrace();
        }

        try {
            conn.prepareStatement(query);
        } catch (SQLException e) {
            System.out.println("Connessione fallita.");
            e.printStackTrace();
        }

        ClienteTrovato ret = new ClienteTrovato();
        try {
            conn.prepareStatement(query);
            ps.setString(1, login);
            ps.executeQuery();

            if (rs.next()) {
                ret.setLogin(rs.getString("login"));
                ret.setPw(rs.getString("password"));
                ret.setNome(rs.getString("nome"));
                ret.setCognome(rs.getString("cognome"));
            }
            rs.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Richiesta al DB fallita.");
            e.printStackTrace();
        }
        return ret;
    }
    public CorseTrovate visualizzaCorse(Date data, String portoPartenza, String portoArrivo) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "select portoPartenza, portoArrivo, orarioPartenza, orarioArrivo, costoIntero, scontoRidotto, costoBagaglio, costoPrevendita, costoVeicolo, nome as nomeCompagnia"
        +"from (CorsaRegolare natural join CorsaSpecifica) join Compagnia on Compagnia = login"
        +"where data = ? and portoPartenza = ? and portoArrivo = ?";

        try {
            conn = c.getConnection();
        } catch (SQLException e) { 
            System.out.println("Connessione fallita.");
            e.printStackTrace();
        }

        CorseTrovate ret = new CorseTrovate();
        try {
            conn.prepareStatement(query);
            ps.setDate(1, (java.sql.Date) data);
            ps.setString(2, portoPartenza);
            ps.setString(3, portoArrivo);
            ps.executeQuery();

            while (rs.next()) {
                ret.getPortoP().add(rs.getInt("PortoPartenza"));
                //etc.
            }
            rs.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Richiesta al DB fallita.");
            e.printStackTrace();
        }

        return ret;
    }
    public void acquistaBiglietto(int idCorsa, Date data, String loginCliente, String targaVeicolo, boolean prevendita, boolean bagaglio, float prezzo, Date dataAcquisto, int etaPasseggero) {
        //l'aggiornamento dei posti disponibili sará effettuato dal DB
        
        PreparedStatement ps = null;
        String query = "insert into Biglietto (idCorsa, data, Cliente, Veicolo, prevendita, bagaglio, prezzo, dataAcquisto, etaPasseggero)" +
                " values (?,?,?,?,?,?,?,?,?)";

        try {
            conn = c.getConnection();
        } catch (SQLException e) { 
            System.out.println("Connessione fallita.");
            e.printStackTrace();
        }

        try {
            conn.prepareStatement(query);
            ps.setInt(1, idCorsa);
            //etc.
            ps.executeUpdate();

            conn.close();
        } catch (SQLException e) {
            System.out.println("Acquisto fallito.");
            e.printStackTrace();
        }
    }
    public void aggiungiVeicolo(String targa, String tipo, String loginProprietario) {
        PreparedStatement ps = null;
        String query = "insert into Veicolo" +
                " values (?,?,?)";

        try {
            conn = c.getConnection();
        } catch (SQLException e) { 
            System.out.println("Connessione fallita.");
            e.printStackTrace();
        }

        try {
            conn.prepareStatement(query);
            ps.setString(1, targa);
            ps.setString(2, tipo);
            ps.setString(3, loginProprietario);
            
            ps.executeUpdate();

            conn.close();
        } catch (SQLException e) {
            System.out.println("Aggiunta fallita.");
            e.printStackTrace();
        }
    }
}
