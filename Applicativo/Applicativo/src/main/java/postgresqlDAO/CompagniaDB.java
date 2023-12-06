package postgresqlDAO;

import dao.CompagniaDAO;
import database.ConnessioneDB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * The type Compagnia db.
 */
public class CompagniaDB implements CompagniaDAO {
    private ConnessioneDB c;
    private java.sql.Connection conn;

    /**
     * Accedi boolean.
     *
     * @param login the login
     * @param pw    the pw
     * @return the boolean
     */
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

    /**
     * Fetch compagnia from db.
     *
     * @param loginCompagnia the login compagnia
     */
    public void fetchCompagniaFromDB(String loginCompagnia, /*ATTRIBUTI DA RIEMPIRE DI COMPAGNIA*/) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "select *"
                + "from Compagnia"
                + "where login = ?";

        try {
            conn = c.getConnection();
        } catch (SQLException e) {
            System.out.println("Connessione fallita.");
            e.printStackTrace();
        }

        try {
            conn.prepareStatement(query);
            ps.setString(1, loginCompagnia);
            ps.executeQuery();

            if (rs.next()) { //se esiste un risultato
                //RIEMPI GLI ATTRIBUTI PASSATI COME PARAMETRI
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Cancellazione fallita.");
            e.printStackTrace();
        }
    }

    /**
     * Aggiungi corsa.
     */
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

    /**
     * Modifica corsa regolare.
     */
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

    /**
     * Modifica corsa specifica.
     */
    public void modificaCorsaSpecifica(/*Attributi di corsaRegolare*/) {
        //...
    }

    /**
     * Cancella corsa regolare.
     *
     * @param idCorsa the id corsa
     */
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

    /**
     * Cancella corsa specifica.
     *
     * @param idCorsa the id corsa
     * @param data    the data
     */
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

    /**
     * Segnala ritardo.
     *
     * @param idCorsa the id corsa
     * @param data    the data
     * @param ritardo the ritardo
     */
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

    /**
     * Aggiunge natante.
     */
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
            //etc. (il resto degli attributi da settare)
            
            ps.executeUpdate();

            conn.close();
        } catch (SQLException e) {
            System.out.println("Aggiunta fallita.");
            e.printStackTrace();
        }
    }

    /**
     * Rimuovi natante.
     *
     * @param nomeNatante the nome natante
     */
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
