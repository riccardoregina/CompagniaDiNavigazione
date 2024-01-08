package postgresqlDAO;

import database.ConnessioneDB;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

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
            ps.setString(1, loginCompagnia);
            rs = ps.executeQuery();

            if (rs.next()) {
                nome = rs.getString("nome");
                sitoWeb = rs.getString("sitoWeb");
            }
            rs.close();
            ps.close();

            ps = connection.prepareStatement(query2);
            ps.setString(1, loginCompagnia);
            rs = ps.executeQuery();

            while (rs.next()) {
                nomeSocial.add(rs.getString("nomeSocial"));
                tagSocial.add(rs.getString("tag"));
            }
            rs.close();
            ps.close();

            ps = connection.prepareStatement(query3);
            ps.setString(1, loginCompagnia);
            rs = ps.executeQuery();

            while (rs.next()) {
                email.add(rs.getString("indirizzo"));
            }
            rs.close();
            ps.close();

            ps = connection.prepareStatement(query4);
            ps.setString(1, loginCompagnia);
            rs = ps.executeQuery();

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
     * @param corsaSup
     */
    public void fetchCorseRegolari(String loginCompagnia, ArrayList<Integer> idCorsa, ArrayList<Integer> idPortoPartenza, ArrayList<Integer> idPortoArrivo, ArrayList<LocalTime> orarioPartenza, ArrayList<LocalTime> orarioArrivo, ArrayList<Float> costoIntero, ArrayList<Float> scontoRidotto, ArrayList<Float> costoBagaglio, ArrayList<Float> costoPrevendita, ArrayList<Float> costoVeicolo, ArrayList<String> nomeNatante, ArrayList<Integer> corsaSup) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "select * from navigazione.CorsaRegolare where Compagnia = ? order by idCorsa";

        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, loginCompagnia);
            rs = ps.executeQuery();

            while (rs.next()) {
                idCorsa.add(rs.getInt("idCorsa"));
                idPortoPartenza.add(rs.getInt("portopartenza"));
                idPortoArrivo.add(rs.getInt("portoarrivo"));
                orarioPartenza.add(rs.getTime("orarioPartenza").toLocalTime());
                orarioArrivo.add(rs.getTime("orarioArrivo").toLocalTime());
                costoIntero.add(rs.getFloat("costoIntero"));
                scontoRidotto.add(rs.getFloat("scontoRidotto"));
                costoBagaglio.add(rs.getFloat("costoBagaglio"));
                costoPrevendita.add(rs.getFloat("costoPrevendita"));
                costoVeicolo.add(rs.getFloat("costoVeicolo"));
                nomeNatante.add(rs.getString("Natante"));
                corsaSup.add(rs.getInt("corsaSup"));
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
    public void fetchPeriodiAttivitaCorse(String loginCompagnia, ArrayList<Integer> idPeriodo, ArrayList<LocalDate> dataInizio, ArrayList<LocalDate> dataFine, ArrayList<String> giorni, ArrayList<Integer> corsa) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "select * from navigazione.Periodo natural join navigazione.AttivaIn natural join navigazione.CorsaRegolare where Compagnia = ?";

        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, loginCompagnia);
            rs = ps.executeQuery();

            while (rs.next()) {
                idPeriodo.add(rs.getInt("idPeriodo"));
                dataInizio.add(rs.getDate("dataInizio").toLocalDate());
                dataFine.add(rs.getDate("dataFine").toLocalDate());
                giorni.add((rs.getString("giorni"))); //VEDI BENE COME CONVERTIRE
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
                data.add(rs.getDate("data").toLocalDate());
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
     * Prova ad aggiungere una corsa nel DB. Se riesce, restituisce l'id
     * della tupla inserita, altrimenti viene lanciata un'eccezione.
     *
     * @param idPortoPartenza
     * @param idPortoArrivo
     * @param orarioPartenza
     * @param orarioArrivo
     * @param costoIntero
     * @param scontoRidotto
     * @param costoBagaglio
     * @param costoPrevendita
     * @param costoVeicolo
     * @param nomeNatante
     * @param idCorsa - output parameter
     */
    public void aggiungeCorsa(int idPortoPartenza, int idPortoArrivo, LocalTime orarioPartenza, LocalTime orarioArrivo, float costoIntero, float scontoRidotto, float costoBagaglio, float costoPrevendita, float costoVeicolo, String loginCompagnia, String nomeNatante, AtomicInteger idCorsa) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "insert into navigazione.CorsaRegolare (portopartenza, portoarrivo, orariopartenza, orarioarrivo, costointero, scontoridotto, costobagaglio, costoprevendita, costoveicolo, compagnia, natante) values (?,?,?,?,?,?,?,?,?,?,?) returning idcorsa";

        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, idPortoPartenza);
            ps.setInt(2, idPortoArrivo);
            ps.setTime(3, Time.valueOf(orarioPartenza));
            ps.setTime(4, Time.valueOf(orarioArrivo));
            ps.setFloat(5, costoIntero);
            ps.setFloat(6, scontoRidotto);
            ps.setFloat(7, costoBagaglio);
            ps.setFloat(8, costoPrevendita);
            ps.setFloat(9, costoVeicolo);
            ps.setString(10, loginCompagnia);
            ps.setString(11, nomeNatante);
            rs = ps.executeQuery();
            rs.next();
            idCorsa.set((int) rs.getInt("idCorsa"));
            rs.close();

            ps.close();
            connection.close();

        } catch(SQLException e) {
            System.out.println("Richiesta al DB fallita.");
            throw new SQLException();
        }
    }

    /**
     * Cancella corsa regolare.
     *
     * @param idCorsa the id corsa
     */
    public void cancellaCorsaRegolare(int idCorsa){
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
    public void cancellaCorsaSpecifica(int idCorsa, Date data) throws SQLException {
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
            throw new SQLException();
        }
    }

    /**
     * Segnala ritardo.
     *
     * @param idCorsa the id corsa
     * @param data    the data
     * @param ritardo the ritardo
     */
    public void segnalaRitardo(int idCorsa, Date data, int ritardo) throws SQLException {
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
            throw new SQLException();
        }
    }

    /**
     * Aggiunge natante.
     */
    public void aggiungeNatante(String loginCompagnia, String nomeNatante, int capienzaPasseggeri, int capienzaVeicoli, String tipo) throws Exception {
        PreparedStatement ps = null;
        String query = "insert into navigazione.natante"
        + " values (?,?,?,?,?::navigazione.tiponatante)";

        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, nomeNatante);
            ps.setString(2, loginCompagnia);
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

    public void aggiungeScalo(int idCorsa, Integer idPortoScalo, LocalTime orarioAttracco, LocalTime orarioRipartenza) throws SQLException {
        PreparedStatement ps = null;
        String query = "insert into navigazione.Scalo"
                + " values (?,?,?,?)";

        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, idCorsa);
            ps.setInt(2, idPortoScalo);
            ps.setTime(3, Time.valueOf(orarioAttracco));
            ps.setTime(4, Time.valueOf(orarioRipartenza));
            ps.executeUpdate();
            ps.close();

            connection.close();
        } catch (SQLException e) {
            System.out.println("Inserimento fallito.");
            throw new SQLException();
        }
    }

    public void aggiungePeriodo(String giorni, LocalDate inizioPeriodo, LocalDate finePeriodo, AtomicInteger idPeriodo) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "insert into navigazione.Periodo (datainizio, datafine, giorni)"
                + " values (?,?,?)" +
                " returning idPeriodo";

        try {
            ps = connection.prepareStatement(query);
            ps.setDate(1, java.sql.Date.valueOf(inizioPeriodo));
            ps.setDate(2, java.sql.Date.valueOf(finePeriodo));
            ps.setString(3, giorni);

            rs = ps.executeQuery();

            rs.next();
            idPeriodo.set(rs.getInt("idPeriodo"));
            rs.close();

            ps.close();
            connection.close();
        } catch (SQLException e) {
            throw new SQLException();
        }
    }

    public void attivaCorsaInPeriodo(int idCorsa, int idPeriodo) throws SQLException {
        PreparedStatement ps = null;
        String query = "insert into navigazione.attivain values (?,?)";

        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, idCorsa);
            ps.setInt(2, idPeriodo);
            ps.executeUpdate();

            ps.close();
            connection.close();
        } catch (SQLException e) {
            throw new SQLException();
        }
    }

    public void modificaOrarioPartenza(int idCorsa, LocalTime nuovoOrarioPartenza) throws SQLException {
        PreparedStatement ps = null;
        String query = "update navigazione.corsaregolare set orariopartenza = ? where idcorsa = ?";

        try {
            ps = connection.prepareStatement(query);
            ps.setTime(1, Time.valueOf(nuovoOrarioPartenza));
            ps.setInt(2, idCorsa);
            ps.executeUpdate();

            ps.close();
            connection.close();
        } catch (SQLException e) {
            throw new SQLException();
        }
    }

    public void modificaOrarioArrivo(int idCorsa, LocalTime nuovoOrarioArrivo) throws SQLException {
        PreparedStatement ps = null;
        String query = "update navigazione.corsaregolare set orarioarrivo = ? where idcorsa = ?";

        try {
            ps = connection.prepareStatement(query);
            ps.setTime(1, Time.valueOf(nuovoOrarioArrivo));
            ps.setInt(2, idCorsa);
            ps.executeUpdate();

            ps.close();
            connection.close();
        } catch (SQLException e) {
            throw new SQLException();
        }
    }
    
    public void modificaCostoIntero(int idCorsa, float nuovoCostoIntero) throws SQLException {
        PreparedStatement ps = null;
        String query = "update navigazione.corsaregolare set costointero = ? where idcorsa = ?";

        try {
            ps = connection.prepareStatement(query);
            ps.setFloat(1, nuovoCostoIntero);
            ps.setInt(2, idCorsa);
            ps.executeUpdate();

            ps.close();
            connection.close();
        } catch (SQLException e) {
            throw new SQLException();
        }
    }

    public void modificaScontoRidotto(int idCorsa, float nuovoScontoRidotto) throws SQLException {
        PreparedStatement ps = null;
        String query = "update navigazione.corsaregolare set scontoridotto = ? where idcorsa = ?";

        try {
            ps = connection.prepareStatement(query);
            ps.setFloat(1, nuovoScontoRidotto);
            ps.setInt(2, idCorsa);
            ps.executeUpdate();

            ps.close();
            connection.close();
        } catch (SQLException e) {
            throw new SQLException();
        }
    }

    public void modificaCostoBagaglio(int idCorsa, float nuovoCostoBagaglio) throws SQLException {
        PreparedStatement ps = null;
        String query = "update navigazione.corsaregolare set costobagaglio = ? where idcorsa = ?";

        try {
            ps = connection.prepareStatement(query);
            ps.setFloat(1, nuovoCostoBagaglio);
            ps.setInt(2, idCorsa);
            ps.executeUpdate();

            ps.close();
            connection.close();
        } catch (SQLException e) {
            throw new SQLException();
        }
    }

    public void modificaCostoPrevendita(int idCorsa, float nuovoCostoPrevendita) throws SQLException {
        PreparedStatement ps = null;
        String query = "update navigazione.corsaregolare set costoprevendita = ? where idcorsa = ?";

        try {
            ps = connection.prepareStatement(query);
            ps.setFloat(1, nuovoCostoPrevendita);
            ps.setInt(2, idCorsa);
            ps.executeUpdate();

            ps.close();
            connection.close();
        } catch (SQLException e) {
            throw new SQLException();
        }
    }


    public void modificaCostoVeicolo(int idCorsa, float nuovoCostoVeicolo) throws SQLException {
        PreparedStatement ps = null;
        String query = "update navigazione.corsaregolare set costoveicolo = ? where idcorsa = ?";

        try {
            ps = connection.prepareStatement(query);
            ps.setFloat(1, nuovoCostoVeicolo);
            ps.setInt(2, idCorsa);
            ps.executeUpdate();

            ps.close();
            connection.close();
        } catch (SQLException e) {
            throw new SQLException();
        }
    }

    /**
     * Elimina un periodo di attivita per una corsa.
     * Elimina anche tale periodo dal DB.
     *
     * @param idCorsa   the id corsa
     * @param idPeriodo the id periodo
     * @throws SQLException the sql exception
     */
    public void eliminaPeriodoAttivitaPerCorsa(int idCorsa, int idPeriodo) throws SQLException {
        PreparedStatement ps = null;
        String query1 = "delete from navigazione.attivain where idcorsa = ? and idPeriodo = ?";

        try {
            ps = connection.prepareStatement(query1);
            ps.setInt(1, idCorsa);
            ps.setInt(2, idPeriodo);
            ps.executeUpdate();
            ps.close();

            connection.close();
        } catch (SQLException e) {
            throw new SQLException();
        }
    }
}