package postgresqlDAO;

import database.*;
import dao.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

/**
 * The type Cliente db.
 */
public class ClienteDB implements ClienteDAO {
    /**
     * The C.
     */
    private ConnessioneDB c;
    /**
     * The Conn.
     */
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

    /**
     * Fetch cliente from db.
     *
     * @param login        the login
     * @param nome         the nome
     * @param cognome      the cognome
     * @param veicoliTarga the veicoli targa
     * @param veicoliTipo  the veicoli tipo
     */
    public void fetchClienteFromDB(String login, String nome, String cognome, ArrayList<String> veicoliTarga, ArrayList<String> veicoliTipo) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query1 = "select *"
                + "from Cliente"
                + "where login = ?";
        String query2 = "select * from Veicolo where Proprietario = ?";

        try {
            conn = c.getConnection();
        } catch (SQLException e) {
            System.out.println("Connessione fallita.");
            e.printStackTrace();
        }

        try {
            //prima query
            conn.prepareStatement(query1);
            ps.setString(1, login);
            ps.executeQuery();

            if (rs.next()) {
                nome = rs.getString("nome");
                cognome = rs.getString("cognome");
            }
            rs.close();
            ps.close();

            //seconda query
            conn.prepareStatement(query2);
            ps.setString(1, login);
            ps.executeQuery();

            while (rs.next()) {
                veicoliTarga.add(rs.getString("targa"));
                veicoliTipo.add(rs.getString("tipo"));
            }
            rs.close();
            ps.close();

            conn.close();
        } catch (SQLException e) {
            System.out.println("Richiesta al DB fallita.");
            e.printStackTrace();
        }
    }

    /**
     * Cerca corse.
     *
     * @param data              the data
     * @param idPortoPartenza   the id porto partenza
     * @param idPortoArrivo     the id porto arrivo
     * @param idCorsa           the id corsa
     * @param nomePortoPartenza the nome porto partenza
     * @param nomePortoArrivo   the nome porto arrivo
     * @param orarioPartenza    the orario partenza
     * @param orarioArrivo      the orario arrivo
     * @param costoIntero       the costo intero
     * @param scontoRidotto     the sconto ridotto
     * @param costoBagaglio     the costo bagaglio
     * @param costoPrevendita   the costo prevendita
     * @param costoVeicolo      the costo veicolo
     * @param nomeCompagnia     the nome compagnia
     * @param nomeNatante       the nome natante
     * @param tipoNatante       the tipo natante
     */
    public void cercaCorse(Date data, int idPortoPartenza, int idPortoArrivo, ArrayList<Integer> idCorsa, ArrayList<String> nomePortoPartenza, ArrayList<String> nomePortoArrivo, ArrayList<LocalTime> orarioPartenza, ArrayList<LocalTime> orarioArrivo, ArrayList<Float> costoIntero, ArrayList<Float> scontoRidotto, ArrayList<Float> costoBagaglio, ArrayList<Float> costoPrevendita, ArrayList<Float> costoVeicolo, ArrayList<String> nomeCompagnia, ArrayList<String> nomeNatante, ArrayList<String> tipoNatante) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        //DA RIVEDERE LA QUERY, SOPRATTUTTO PER QUANTO RIGUARDA I RENAME
        String query = "select *"
        +"from ((CorsaRegolare join Porto as PP on PortoPartenza = idPorto) join Porto as PA on PortoArrivo = idPorto) join Natante on Natante = nome) natural join CorsaSpecifica) join Compagnia on Compagnia = login"
        +"where data = ? and portoPartenza = ? and portoArrivo = ?";

        try {
            conn = c.getConnection();
        } catch (SQLException e) { 
            System.out.println("Connessione fallita.");
            e.printStackTrace();
        }

        try {
            conn.prepareStatement(query);
            ps.setDate(1, (java.sql.Date) data);
            ps.setInt(2, idPortoPartenza);
            ps.setInt(3, idPortoArrivo);
            ps.executeQuery();

            while (rs.next()) {
                idCorsa.add(rs.getInt("idCorsa"));
                nomePortoPartenza.add(rs.getString("nomePortoPartenza"));
                nomePortoArrivo.add(rs.getString("nomePortoArrivo"));
                nomeNatante.add(rs.getString("nomeNatante"));
                tipoNatante.add(rs.getString("tipo"));
                orarioPartenza.add((LocalTime) rs.getTime("orarioPartenza")); //risolvere il casting
                orarioArrivo.add((LocalTime) rs.getTime("orarioArrivo")); //risolvere il casting
                costoIntero.add(rs.getFloat("costoIntero"));
                scontoRidotto.add(rs.getFloat("scontoRidotto"));
                costoBagaglio.add(rs.getFloat("costoBagaglio"));
                costoPrevendita.add(rs.getFloat("costoPrevendita"));
                costoVeicolo.add(rs.getFloat("costoVeicolo"));
                nomeCompagnia.add(rs.getString("nomeCompagnia"));
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Richiesta al DB fallita.");
            e.printStackTrace();
        }
    }

    /**
     * Acquista biglietto.
     *
     * @param idCorsa       the id corsa
     * @param data          the data
     * @param loginCliente  the login cliente
     * @param targaVeicolo  the targa veicolo
     * @param prevendita    the prevendita
     * @param bagaglio      the bagaglio
     * @param prezzo        the prezzo
     * @param dataAcquisto  the data acquisto
     * @param etaPasseggero the eta passeggero
     */
    public void acquistaBiglietto(int idCorsa, Date data, String loginCliente, String targaVeicolo, boolean prevendita, boolean bagaglio, float prezzo, Date dataAcquisto, int etaPasseggero) {
        //l'aggiornamento dei posti disponibili sará effettuato dal DB
        
        PreparedStatement ps = null;
        String query = "insert into Biglietto (idCorsa, data, loginCliente, targaVeicolo, prevendita, bagaglio, prezzo, dataAcquisto, etaPasseggero)" +
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
            ps.setDate(2, (java.sql.Date) data);
            ps.setString(3, loginCliente);
            ps.setString(4, targaVeicolo);
            ps.setBoolean(5, prevendita);
            ps.setBoolean(6, bagaglio);
            ps.setFloat(7, prezzo);
            ps.setDate(8, (java.sql.Date) dataAcquisto);
            ps.setInt(9, etaPasseggero);
            ps.executeUpdate();

            conn.close();
        } catch (SQLException e) {
            System.out.println("Acquisto fallito.");
            e.printStackTrace();
        }
    }

    /**
     * Aggiungi veicolo.
     *
     * @param targa             the targa
     * @param tipo              the tipo
     * @param loginProprietario the login proprietario
     */
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

    /**
     * Fetch porti.
     *
     * @param comuni         the comuni
     * @param indirizzi      the indirizzi
     * @param numeriTelefono the numeri telefono
     */
    public void fetchPorti(ArrayList<String> comuni, ArrayList<String> indirizzi, ArrayList<String> numeriTelefono) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "select * from Porto";

        try {
            conn = c.getConnection();
        } catch (SQLException e) {
            System.out.println("Connessione fallita.");
            e.printStackTrace();
        }

        try {
            conn.prepareStatement(query);

            ps.executeQuery();

            while(rs.next()) {
                //Riempio delle variabili locali con le colonne della tupla trovata
                // e faccio costruire a controller un porto da inserire nel model
                comuni.add(rs.getString("comune"));
                indirizzi.add(rs.getString("indirizzo"));
                numeriTelefono.add(rs.getString("numeroTelefono"));
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Aggiunta fallita.");
            e.printStackTrace();
        }
    }
}
