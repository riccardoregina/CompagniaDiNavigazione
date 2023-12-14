package postgresqlDAO;

import database.ConnessioneDB;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Date;

/**
 * The type Compagnia db.
 */
public class CompagniaDAO implements dao.CompagniaDAO {
    private Connection connection;
    public CompagniaDAO() {
        try {
            connection = ConnessioneDB.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Accedi boolean.
     *
     * @param login the login
     * @param pw    the pw
     * @return the boolean
     */
    public boolean accede(String login, String pw) {
        boolean found = false;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "select *"
        + " from navigazione.Compagnia"
        + " where login = ? and password = ?";

        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, login);
            ps.setString(2, pw);
            rs = ps.executeQuery();

            if (rs.next()) { //se esiste un risultato
                found = true;
            }
            rs.close();
            connection.close();
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
     * @param nome           the nome
     * @param telefono       the telefono
     * @param email          the email
     * @param nomeSocial     the nome social
     * @param tagSocial      the tag social
     * @param sitoWeb        the sito web
     */
    public void fetchCompagnia(String loginCompagnia, String nome, ArrayList<String> telefono, ArrayList<String> email, ArrayList<String> nomeSocial, ArrayList<String> tagSocial, String sitoWeb) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query1 = "select * from navigazione.Compagnia where login = ?";
        String query2 = "select * from navigazione.AccountSocial where Compagnia = ?";
        String query3 = "select * from navigazione.Email where Compagnia = ?";
        String query4 = "select * from navigazione.Telefono where Compagnia = ?";

        try {
            ps = connection.prepareStatement(query1);
            rs = ps.executeQuery(query1);

            if (rs.next()) {
                nome = rs.getString("nome");
                sitoWeb = rs.getString("sitoWeb");
            }
            rs.close();
            ps.close();

            ps = connection.prepareStatement(query2);
            rs = ps.executeQuery(query2);

            while (rs.next()) {
                nomeSocial.add(rs.getString("nomeSocial"));
                tagSocial.add(rs.getString("tag"));
            }
            rs.close();
            ps.close();

            ps = connection.prepareStatement(query3);
            rs = ps.executeQuery(query3);

            while (rs.next()) {
                email.add(rs.getString("indirizzo"));
            }
            rs.close();
            ps.close();

            ps = connection.prepareStatement(query4);
            rs = ps.executeQuery(query4);

            while (rs.next()) {
                telefono.add(rs.getString("numero"));
            }
            rs.close();
            ps.close();

            connection.close();
        } catch (SQLException e) {
            System.out.println("Richiesta al DB fallita.");
            e.printStackTrace();
        }
    }

    /**
     * Fetch porti.
     *
     * @param idPorto        the id porto
     * @param comuni         the comuni
     * @param indirizzi      the indirizzi
     * @param numeriTelefono the numeri telefono
     */
    public void fetchPorti(ArrayList<Integer> idPorto, ArrayList<String> comuni, ArrayList<String> indirizzi, ArrayList<String> numeriTelefono) {
        Statement s = null;
        ResultSet rs = null;
        String query = "select * from navigazione.Porto";

        try {
            s = connection.createStatement();
            rs = s.executeQuery(query);

            while(rs.next()) {
                //Riempio delle variabili locali con le colonne della tupla trovata
                // e faccio costruire a controller un porto da inserire nel model
                idPorto.add(rs.getInt("idPorto"));
                comuni.add(rs.getString("comune"));
                indirizzi.add(rs.getString("indirizzo"));
                numeriTelefono.add(rs.getString("numeroTelefono"));
            }
            rs.close();
            s.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Aggiunta fallita.");
            e.printStackTrace();
        }
    }

    /**
     * Fetch natanti compagnia.
     *
     * @param loginCompagnia     the login compagnia
     * @param nomeNatante        the nome natante
     * @param capienzaPasseggeri the capienza passeggeri
     * @param capienzaVeicoli    the capienza veicoli
     * @param tipo               the tipo
     */
    public void fetchNatantiCompagnia(String loginCompagnia, ArrayList<String> nomeNatante, ArrayList<Integer> capienzaPasseggeri, ArrayList<Integer> capienzaVeicoli, ArrayList<String> tipo) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "select *"
                + " from navigazione.Natante"
                + " where Compagnia = ?";

        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, loginCompagnia);
            rs = ps.executeQuery();

            while (rs.next()) {
                nomeNatante.add(rs.getString("nome"));
                capienzaPasseggeri.add(rs.getInt("capienzaPasseggeri"));
                capienzaVeicoli.add(rs.getInt("capienzaVeicoli"));
                tipo.add(rs.getString("tipo"));
            }
            rs.close();
            ps.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Cancellazione fallita.");
            e.printStackTrace();
        }
    }

    /**
     * Fetch corse regolari.
     *
     * @param loginCompagnia  the login compagnia
     * @param idCorsa         the id corsa
     * @param idPortoPartenza the id porto partenza
     * @param idPortoArrivo   the id porto arrivo
     * @param orarioPartenza  the orario partenza
     * @param orarioArrivo    the orario arrivo
     * @param costoIntero     the costo intero
     * @param scontoRidotto   the sconto ridotto
     * @param costoBagaglio   the costo bagaglio
     * @param costoPrevendita the costo prevendita
     * @param costoVeicolo    the costo veicolo
     * @param nomeNatante     the nome natante
     */
    public void fetchCorseRegolari(String loginCompagnia, ArrayList<Integer> idCorsa, ArrayList<Integer> idPortoPartenza, ArrayList<Integer> idPortoArrivo, ArrayList<LocalTime> orarioPartenza, ArrayList<LocalTime> orarioArrivo, ArrayList<Float> costoIntero, ArrayList<Float> scontoRidotto, ArrayList<Float> costoBagaglio, ArrayList<Float> costoPrevendita, ArrayList<Float> costoVeicolo, ArrayList<String> nomeNatante) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "select * from navigazione.CorsaRegolare where Compagnia = ?";

        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, loginCompagnia);
            rs = ps.executeQuery();

            while (rs.next()) {
                idCorsa.add(rs.getInt("idCorsa"));
                idPortoPartenza.add(rs.getInt("idPortoPartenza"));
                idPortoArrivo.add(rs.getInt("idPortoArrivo"));
                //orarioPartenza.add(rs.getTime("orarioPartenza")); //VEDI CONVERSIONE
                //orarioArrivo.add(rs.getTime("orarioArrivo")); //VEDI CONVERSIONE
                costoIntero.add(rs.getFloat("costoIntero"));
                scontoRidotto.add(rs.getFloat("scontoRidotto"));
                costoBagaglio.add(rs.getFloat("costoBagaglio"));
                costoPrevendita.add(rs.getFloat("costoPrevendita"));
                costoVeicolo.add(rs.getFloat("costoVeicolo"));
                nomeNatante.add(rs.getString("Natante"));
            }
            rs.close();
            ps.close();

            connection.close();
        } catch (SQLException e) {
            System.out.println("Richiesta al DB fallita.");
            e.printStackTrace();
        }
    }

    /**
     * Fetch periodi attivita corse.
     *
     * @param loginCompagnia the login compagnia
     * @param idPeriodo      the id periodo
     * @param dataInizio     the data inizio
     * @param dataFine       the data fine
     * @param giorni         the giorni
     * @param corsa          the corsa
     */
    public void fetchPeriodiAttivitaCorse(String loginCompagnia, ArrayList<Integer> idPeriodo, ArrayList<Date> dataInizio, ArrayList<Date> dataFine, ArrayList<BitSet> giorni, ArrayList<Integer> corsa) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "select * from navigazione.Periodo natural join navigazione.AttivaIn natural join navigazione.CorsaRegolare where Compagnia = ?";

        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, loginCompagnia);
            rs = ps.executeQuery();

            while (rs.next()) {
                idPeriodo.add(rs.getInt("idPeriodo"));
                dataInizio.add(rs.getDate("dataInizio"));
                dataFine.add(rs.getDate("dataFine"));
                //giorni.add((rs.getBytes("giorni"))); //VEDI BENE COME CONVERTIRE
                corsa.add(rs.getInt("idCorsa"));
            }
            rs.close();
            ps.close();

            connection.close();
        } catch (SQLException e) {
            System.out.println("Richiesta al DB fallita.");
            e.printStackTrace();
        }
    }

    /**
     * Fetch corse specifiche.
     *
     * @param loginCompagnia the login compagnia
     * @param corsaRegolare  the corsa regolare
     * @param data           the data
     * @param postiDispPass  the posti disp pass
     * @param postiDispVei   the posti disp vei
     * @param minutiRitardo  the minuti ritardo
     * @param cancellata     the cancellata
     */
    public void fetchCorseSpecifiche(String loginCompagnia, ArrayList<Integer> corsaRegolare, ArrayList<LocalDate> data, ArrayList<Integer> postiDispPass, ArrayList<Integer> postiDispVei, ArrayList<Integer> minutiRitardo, ArrayList<Boolean> cancellata) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "select * from navigazione.CorsaSpecifica natural join navigazione.CorsaRegolare where Compagnia = ?";

        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, loginCompagnia);
            rs = ps.executeQuery();

            while (rs.next()) {
                corsaRegolare.add(rs.getInt("idCorsa"));
                //data.add(rs.getDate("data")); //VEDI CONVERSIONE
                postiDispPass.add(rs.getInt("postiDispPass"));
                postiDispVei.add(rs.getInt("postiDispVei"));
                minutiRitardo.add(rs.getInt("minutiRitardo"));
                cancellata.add(rs.getBoolean("cancellata"));
            }
            rs.close();
            ps.close();

            connection.close();
        } catch (SQLException e) {
            System.out.println("Richiesta al DB fallita.");
            e.printStackTrace();
        }
    }

    /**
     * Aggiungi corsa.
     */
    public void aggiungeCorsa(/*Attributi di corsaRegolare*/) {
        //deve chiamare una procedura

        try {
            //conn.executeProcedure(); //qualcosa del genere

            connection.close();
        } catch (SQLException e) {
            System.out.println("Cancellazione fallita.");
            e.printStackTrace();
        }
    }

    /**
     * Modifica corsa regolare.
     */
    public void modificaCorsaRegolare(int idPortoPartenza, int idPortoArrivo, LocalTime orarioPartenza, LocalTime orarioArrivo, float costoIntero, float scontoRidotto, float costoBagaglio, float costoPrevendita, float costoVeicolo, int idCorsa) {
        PreparedStatement ps = null;
        String query = "update navigazione.CorsaRegolare"
        + " set portoPartenza = ?, portoArrivo = ?, orarioPartenza = ?, orarioArrivo = ?, costoIntero = ?, scontoRidotto = ?, costoBagaglio = ?, costoPrevendita = ?, costoVeicolo = ?"
        + " where idCorsa = ?";

        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, idPortoPartenza);
            ps.setInt(2, idPortoArrivo);
            //ps.setTime(3, orarioPartenza); //DA RISOLVERE LA CONVERSIONE
            //ps.setTime(4, orarioArrivo);   //DA RISOLVERE LA CONVERSIONE
            ps.setFloat(5, costoIntero);
            ps.setFloat(6, scontoRidotto);
            ps.setFloat(7, costoBagaglio);
            ps.setFloat(8, costoPrevendita);
            ps.setFloat(9, costoVeicolo);
            ps.setInt(10, idCorsa);
            ps.executeUpdate();

            ps.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Modifica fallita.");
            e.printStackTrace();
        }
    }

    /**
     * Modifica corsa specifica.
     */
    public void modificaCorsaSpecifica() {
        //DI DUBBIA UTILITA': PROPONGO DI FAR INSERIRE UNA CORSA REGOLARE DI DURATA UN GIORNO E SEGNARE COME CANCELLATA LA CORSA SPECIFICA DA MODIFICARE
    }

    /**
     * Cancella corsa regolare.
     *
     * @param idCorsa the id corsa
     */
    public void cancellaCorsaRegolare(int idCorsa) {
        PreparedStatement ps = null;
        String query = "delete from navigazione.CorsaRegolare where idCorsa = ?";

        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, idCorsa);
            
            ps.executeUpdate();

            connection.close();
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
        String query = "update navigazione.CorsaSpecifica"
        + " set cancellata = true"
        + " where idCorsa = ? and data = ?";
        
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, idCorsa);
            ps.setDate(2, (java.sql.Date) data);
            
            ps.executeUpdate();

            connection.close();
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
        String query = "update navigazione.CorsaSpecifica"
        + " set minutiRitardo = ?"
        + " where idCorsa = ? and data = ?";
        
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, ritardo);
            ps.setInt(2, idCorsa);
            ps.setDate(3, (java.sql.Date) data);
            
            ps.executeUpdate();

            connection.close();
        } catch (SQLException e) {
            System.out.println("Segnalazione ritardo fallita.");
            e.printStackTrace();
        }
    }

    /**
     * Aggiunge natante.
     */
    public void aggiungeNatante(String loginCompagnia, String nomeNatante, int capienzaPasseggeri, int capienzaVeicoli, String tipo) throws Exception {
        PreparedStatement ps = null;
        String query = "insert into navigazione.natante"
        + " values (?,?,?,?,?::tiponatante)";

        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, loginCompagnia);
            ps.setString(2, nomeNatante);
            ps.setInt(3, capienzaPasseggeri);
            ps.setInt(4, capienzaVeicoli);
            ps.setString(5, tipo);
            
            ps.executeUpdate();

            connection.close();
        } catch (SQLException e) {
            System.out.println("Aggiunta fallita.");
            throw new Exception();
        }
    }

    /**
     * Rimuovi natante.
     *
     * @param nomeNatante the nome natante
     */
    public void rimuoveNatante(String nomeNatante) {
        PreparedStatement ps = null;
        String query = "delete from navigazione.natante"
        + " where nome = ?";

        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, nomeNatante);
            
            ps.executeUpdate();

            connection.close();
        } catch (SQLException e) {
            System.out.println("Eliminazione fallita.");
            e.printStackTrace();
        }
    }
}
