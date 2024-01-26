package postgresqldao;

import database.ConnessioneDB;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * La classe CompagniaPostgresqlDAO.
 */
public class CompagniaPostgresqlDAO implements dao.CompagniaDAO {
    private Logger logger = Logger.getLogger(this.getClass().getName());
    private Connection connection;

    /**
     *  Istanzia un Data Access Object per richiedere accesso a dati, riguardanti la compagnia,
     *  presenti sul DB.
     *
     * @throws SQLException
     */
    public CompagniaPostgresqlDAO() throws SQLException {
        connection = ConnessioneDB.getInstance().getConnection();
    }

    /**
     * Verifica sul db se esiste una compagnia con i login e password passati come parametri.
     *
     * @param login la login
     * @param pw    la password
     * @throws SQLException
     */
    public void accede(String login, String pw) throws SQLException {
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

            if (!rs.next()) {
                rs.close();
                ps.close();
                logger.log(Level.FINE, "Credenziali errate / compagnia non esistente.");
                throw new SQLException("Credenziali errate / compagnia non esistente.");
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            String message = e.getMessage();
            logger.log(Level.SEVERE, message);
            throw e;
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Impossibile chiudere la connessione.");
            }
        }
    }

    /**
     * Effettua il fetch dei dati della compagnia sul DB.
     *
     * @param loginCompagnia la login
     * @param nome           output - il nome della compagnia
     * @param telefono       output - i telefoni della compagnia
     * @param email          output - gli indirizzi email della compagnia
     * @param nomeSocial     output - i nomi dei social
     * @param tagSocial      output - i tag della compagnia nei social
     * @param sitoWeb        output - il sito web della compagnia
     */
    public void fetchCompagnia(String loginCompagnia, ArrayList<String> nome, ArrayList<String> telefono, ArrayList<String> email, ArrayList<String> nomeSocial, ArrayList<String> tagSocial, ArrayList<String> sitoWeb) {
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
                nome.addFirst(rs.getString("nome"));
                sitoWeb.addFirst(rs.getString("sitoWeb"));
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
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Richiesta al DB fallita.");
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Impossibile chiudere la connessione.");
            }
        }
    }

    /**
     * Effettua il fetch di tutti i porti presenti sul DB.
     *
     * @param idPorto        output - gli id dei porti
     * @param comuni         output - i comuni dei porti
     * @param indirizzi      output - gli indirizzi dei porti
     * @param numeriTelefono output - i numeri di telefono dei porti
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
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Richiesta al DB fallita.");
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Impossibile chiudere la connessione.");
            }
        }
    }

    /**
     * Effettua il fetch dei natanti posseduti dalla compagnia.
     *
     * @param loginCompagnia     la login
     * @param nomeNatante        output - i nomi dei natanti
     * @param capienzaPasseggeri output - le capienze di passeggeri
     * @param capienzaVeicoli    output - le capienze di veicoli
     * @param tipo               output - i tipi dei natanti
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
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Richiesta al DB fallita.");
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Impossibile chiudere la connessione.");
            }
        }
    }

    /**
     * Effettua il fetch delle corse regolari erogate dalla compagnia.
     *
     * @param loginCompagnia  the login compagnia
     * @param idCorsa         output - gli id delle corse
     * @param idPortoPartenza output - gli id dei porti di partenza
     * @param idPortoArrivo   output - gli id dei porti di arrivo
     * @param orarioPartenza  output - gli orari di partenza
     * @param orarioArrivo    output - gli orari di arrivo
     * @param costoIntero     output - i costi del biglietto intero
     * @param scontoRidotto   output - gli sconti percentuali da applicare in caso di biglietto ridotto
     * @param costoBagaglio   output - i costi aggiuntivi per il bagaglio
     * @param costoPrevendita output - i costi aggiuntivi per la prevendita
     * @param costoVeicolo    output - i costi aggiuntivi per il veicolo
     * @param nomeNatante     output - i nomi dei natanti impiegati nelle corse
     * @param corsaSup        output - le corse regolari di cui le corse sono sotto-corse
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
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Richiesta al DB fallita.");
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Impossibile chiudere la connessione.");
            }
        }
    }

    /**
     * Effettua il fetch dei periodi di attivita' delle corse regolari erogate dalla compagnia.
     *
     * @param loginCompagnia la login
     * @param idPeriodo      output - gli id dei periodi
     * @param dataInizio     output - le date di inizio
     * @param dataFine       output - le date di fine
     * @param giorni         output - i giorni di attivita'
     * @param corsa          output - le corse a cui sono legati i periodi
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
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Richiesta al DB fallita.");
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Impossibile chiudere la connessione.");
            }
        }
    }

    /**
     * Effettua il fetch delle corse specifiche erogate dalla compagnia.
     *
     * @param loginCompagnia the login compagnia
     * @param corsaRegolare  output - le corse regolari che hanno generato le corse specifiche
     * @param data           output - le date delle corse specifiche
     * @param postiDispPass  output - i posti disponibili per i passeggeri nelle corse specifiche
     * @param postiDispVei   output - i posti disponibili per i veicoli nelle corse specifiche
     * @param minutiRitardo  output - i minuti di ritardo
     * @param cancellata     output - valore booleano che indica se la corsa e' stata cancellata o meno
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
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Richiesta al DB fallita.");
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Impossibile chiudere la connessione.");
            }
        }
    }


    /**
     * Prova ad aggiungere una corsa nel DB. Se riesce, restituisce l'id
     * della tupla inserita, altrimenti viene lanciata un'eccezione.
     *
     * @param idPortoPartenza l'id del porto di partenza
     * @param idPortoArrivo l'id del porto di arrivo
     * @param orarioPartenza l'orario di partenza
     * @param orarioArrivo l'orario di arrivo
     * @param costoIntero il costo del biglietto intero
     * @param scontoRidotto lo sconto percentuale per il biglietto ridotto
     * @param costoBagaglio il costo aggiuntivo per il bagaglio
     * @param costoPrevendita il costo aggiuntivo per la prevendita
     * @param costoVeicolo il costo aggiunto per il veicolo
     * @param nomeNatante il nome del natante della corsa
     * @param idCorsa - output - l'id assegnato dal DB alla corsa inserita.
     *
     * @throws SQLException
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
            idCorsa.set(rs.getInt("idCorsa"));
            rs.close();

            ps.close();
        } catch(SQLException e) {
            logger.log(Level.SEVERE, "Richiesta al DB fallita.");
            throw new SQLException();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Impossibile chiudere la connessione.");
            }
        }
    }

    /**
     * Cancella una corsa regolare dal DB.
     *
     * @param idCorsa l'id della corsa da cancellare
     */
    public void cancellaCorsaRegolare(int idCorsa){
        PreparedStatement ps = null;
        String query = "delete from navigazione.CorsaRegolare where idCorsa = ?";

        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, idCorsa);
            
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Richiesta al DB fallita.");
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Impossibile chiudere la connessione.");
            }
        }
    }

    /**
     * Cancella una corsa specifica dal DB.
     *
     * @param idCorsa l'id della corsa regolare che ha generato la corsa specifica da eliminare
     * @param data    la data della corsa specifica da eliminare
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
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Richiesta al DB fallita.");
            throw new SQLException();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Impossibile chiudere la connessione.");
            }
        }
    }

    /**
     * Segnala un ritardo per una corsa specifica
     *
     * @param idCorsa l'id della corsa regolare che ha generato la corsa specifica
     * @param data    la data della corsa specifica
     * @param ritardo il valore in minuti del ritardo da segnalare
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
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Richiesta al DB fallita.");
            throw new SQLException();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Impossibile chiudere la connessione.");
            }
        }
    }

    /**
     * Aggiunge un natante della compagnia al DB.
     *
     * @param loginCompagnia la login
     * @param nomeNatante il nome del natante da aggiungere
     * @param capienzaPasseggeri la capienza di passeggeri del natante
     * @param capienzaVeicoli la capienza di veicoli del natante
     * @param tipo il tipo del natante
     * @throws SQLException
     */
    public void aggiungeNatante(String loginCompagnia, String nomeNatante, int capienzaPasseggeri, int capienzaVeicoli, String tipo) throws SQLException {
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
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Aggiunta del natante fallita.");
            throw new SQLException();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Impossibile chiudere la connessione.");
            }
        }
    }

    /**
     * Rimuove un natante della compagnia dal DB.
     *
     * @param nomeNatante il nome del natante da eliminare
     */
    public void rimuoveNatante(String nomeNatante) {
        PreparedStatement ps = null;
        String query = "delete from navigazione.natante"
        + " where nome = ?";

        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, nomeNatante);
            
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Rimozione fallita.");
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Impossibile chiudere la connessione.");
            }
        }
    }

    /**
     * Aggiunge sul DB uno scalo per la corsa identificata dall'idCorsa in ingresso.
     *
     * @param idCorsa          l'id della corsa per cui si aggiunge lo scalo
     * @param idPortoScalo     il porto di scalo
     * @param orarioAttracco   l'orario di arrivo al porto di scalo
     * @param orarioRipartenza l'orario di ripartenza dal porto di scalo
     * @throws SQLException
     */
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
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Inserimento fallito.");
            throw new SQLException();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Impossibile chiudere la connessione.");
            }
        }
    }

    /**
     * Aggiunge un periodo di attivita', ma senza attaccarlo ad una corsa regolare.
     *
     * @param giorni        i giorni di attivita'
     * @param inizioPeriodo l'inizio del periodo
     * @param finePeriodo   la fine del periodo
     * @param idPeriodo     output - l'id assegnato dal DB al periodo inserito
     * @throws SQLException
     */
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
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Aggiunta del periodo fallita.");
            throw new SQLException();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Impossibile chiudere la connessione.");
            }
        }
    }

    /**
     * Attiva una corsa regolare in un periodo
     *
     * @param idCorsa   l'id della corsa
     * @param idPeriodo l'id del periodo
     * @throws SQLException
     */
    public void attivaCorsaInPeriodo(int idCorsa, int idPeriodo) throws SQLException {
        PreparedStatement ps = null;
        String query = "insert into navigazione.attivain values (?,?)";

        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, idCorsa);
            ps.setInt(2, idPeriodo);
            ps.executeUpdate();

            ps.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Attivazione della corsa fallita.");
            throw new SQLException();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Impossibile chiudere la connessione.");
            }
        }
    }

    /**
     * Modifica l'orario di partenza di una corsa regolare.
     *
     * @param idCorsa             l'id della corsa
     * @param nuovoOrarioPartenza il nuovo orario di partenza
     * @throws SQLException
     */
    public void modificaOrarioPartenza(int idCorsa, LocalTime nuovoOrarioPartenza) throws SQLException {
        PreparedStatement ps = null;
        String query = "update navigazione.corsaregolare set orariopartenza = ? where idcorsa = ?";

        try {
            ps = connection.prepareStatement(query);
            ps.setTime(1, Time.valueOf(nuovoOrarioPartenza));
            ps.setInt(2, idCorsa);
            ps.executeUpdate();

            ps.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Modifica orario fallita.");
            throw new SQLException();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Impossibile chiudere la connessione.");
            }
        }
    }

    /**
     * Modifica l'orario di arrivo della corsa.
     *
     * @param idCorsa           l'id della corsa
     * @param nuovoOrarioArrivo il nuovo orario di arrivo
     * @throws SQLException
     */
    public void modificaOrarioArrivo(int idCorsa, LocalTime nuovoOrarioArrivo) throws SQLException {
        PreparedStatement ps = null;
        String query = "update navigazione.corsaregolare set orarioarrivo = ? where idcorsa = ?";

        try {
            ps = connection.prepareStatement(query);
            ps.setTime(1, Time.valueOf(nuovoOrarioArrivo));
            ps.setInt(2, idCorsa);
            ps.executeUpdate();

            ps.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Modifica orario fallita.");
            throw new SQLException();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Impossibile chiudere la connessione.");
            }
        }
    }

    /**
     * Modifica il costo del biglietto intero della corsa.
     *
     * @param idCorsa          l'id della corsa
     * @param nuovoCostoIntero il nuovo costo intero
     * @throws SQLException
     */
    public void modificaCostoIntero(int idCorsa, float nuovoCostoIntero) throws SQLException {
        PreparedStatement ps = null;
        String query = "update navigazione.corsaregolare set costointero = ? where idcorsa = ?";

        try {
            ps = connection.prepareStatement(query);
            ps.setFloat(1, nuovoCostoIntero);
            ps.setInt(2, idCorsa);
            ps.executeUpdate();

            ps.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Modifica costo fallita.");
            throw new SQLException();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Impossibile chiudere la connessione.");
            }
        }
    }

    /**
     * Modifica lo sconto percentuale per il biglietto ridotto.
     *
     * @param idCorsa            l'id della corsa
     * @param nuovoScontoRidotto il nuovo sconto ridotto
     * @throws SQLException
     */
    public void modificaScontoRidotto(int idCorsa, float nuovoScontoRidotto) throws SQLException {
        PreparedStatement ps = null;
        String query = "update navigazione.corsaregolare set scontoridotto = ? where idcorsa = ?";

        try {
            ps = connection.prepareStatement(query);
            ps.setFloat(1, nuovoScontoRidotto);
            ps.setInt(2, idCorsa);
            ps.executeUpdate();

            ps.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Modifica costo fallita.");
            throw new SQLException();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Impossibile chiudere la connessione.");
            }
        }
    }

    /**
     * Modifica il costo aggiuntivo per il bagaglio.
     *
     * @param idCorsa            l'id della corsa
     * @param nuovoCostoBagaglio il nuovo costo per il bagaglio
     * @throws SQLException
     */
    public void modificaCostoBagaglio(int idCorsa, float nuovoCostoBagaglio) throws SQLException {
        PreparedStatement ps = null;
        String query = "update navigazione.corsaregolare set costobagaglio = ? where idcorsa = ?";

        try {
            ps = connection.prepareStatement(query);
            ps.setFloat(1, nuovoCostoBagaglio);
            ps.setInt(2, idCorsa);
            ps.executeUpdate();

            ps.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Modifica costo fallita.");
            throw new SQLException();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Impossibile chiudere la connessione.");
            }
        }
    }

    /**
     * Modifica il costo aggiuntivo per la prevendita.
     *
     * @param idCorsa              l'id della corsa
     * @param nuovoCostoPrevendita il nuovo costo prevendita
     * @throws SQLException
     */
    public void modificaCostoPrevendita(int idCorsa, float nuovoCostoPrevendita) throws SQLException {
        PreparedStatement ps = null;
        String query = "update navigazione.corsaregolare set costoprevendita = ? where idcorsa = ?";

        try {
            ps = connection.prepareStatement(query);
            ps.setFloat(1, nuovoCostoPrevendita);
            ps.setInt(2, idCorsa);
            ps.executeUpdate();

            ps.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Modifica costo fallita.");
            throw new SQLException();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Impossibile chiudere la connessione.");
            }
        }
    }


    /**
     * Modifica il costo aggiuntivo per il veicolo.
     *
     * @param idCorsa           l'id della corsa
     * @param nuovoCostoVeicolo il nuovo costo per il veicolo
     * @throws SQLException the sql exception
     */
    public void modificaCostoVeicolo(int idCorsa, float nuovoCostoVeicolo) throws SQLException {
        PreparedStatement ps = null;
        String query = "update navigazione.corsaregolare set costoveicolo = ? where idcorsa = ?";

        try {
            ps = connection.prepareStatement(query);
            ps.setFloat(1, nuovoCostoVeicolo);
            ps.setInt(2, idCorsa);
            ps.executeUpdate();

            ps.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Modifica costo fallita.");
            throw new SQLException();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Impossibile chiudere la connessione.");
            }
        }
    }

    /**
     * Elimina dal DB un periodo di attivita per una corsa.
     *
     * @param idCorsa   l'id della corsa
     * @param idPeriodo l'id del periodo
     * @throws SQLException
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
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Rimozione di un periodo di attivita fallito.");
            throw new SQLException();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Impossibile chiudere la connessione.");
            }
        }
    }

    /**
     * Calcola gli incassi di una corsa in un periodo.
     *
     * @param idCorsa       l'id della corsa
     * @param inizioPeriodo l'inizio del periodo
     * @param finePeriodo   la fine del periodo
     * @return un float corrispondente all'incasso della corsa nel periodo
     * @throws SQLException
     */
    public float calcolaIncassiCorsaInPeriodo(int idCorsa, LocalDate inizioPeriodo, LocalDate finePeriodo) throws SQLException {
        CallableStatement cs = null;
        try {
            cs = connection.prepareCall("call navigazione.calcolaincassicorsainperiodo(?,?,?,?)");
            cs.setInt(1, idCorsa);
            cs.setDate(2, java.sql.Date.valueOf(inizioPeriodo));
            cs.setDate(3, java.sql.Date.valueOf(finePeriodo));
            cs.setFloat(4, 0);
            cs.registerOutParameter(4, Types.DOUBLE);

            cs.execute();
            float ret = (float) cs.getDouble(4);
            cs.close();

            return ret;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Richiesta al DB fallita.");
            throw new SQLException();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Impossibile chiudere la connessione.");
            }
        }
    }

    /**
     * Aggiorna i posti disponibili nelle sottocorse.
     *
     * @param idCorsa l'id della corsa
     * @throws SQLException
     */
    public void aggiornaPostiDisponibiliSottocorse(int idCorsa) throws SQLException {
        CallableStatement cs = null;
        try {
            cs = connection.prepareCall("call navigazione.aggiornapostisottocorse(?)");
            cs.setInt(1, idCorsa);

            cs.executeUpdate();
            cs.close();

            logger.log(Level.FINEST, "I posti sono stati aggiornati");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Richiesta al DB fallita");
            throw new SQLException();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Impossibile chiudere la connessione.");
            }
        }
    }

    /**
     * Aggiunge un profilo social per la compagnia.
     *
     * @param nomeSocial    il nome del social
     * @param tag           il tag
     * @param nomeCompagnia il nome della compagnia
     * @throws SQLException
     */
    public void aggiungiSocial(String nomeSocial, String tag, String nomeCompagnia) throws SQLException {
        PreparedStatement ps = null;
        String query = "insert into navigazione.AccountSocial"
                + " values (?,?,?)";

        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, nomeSocial);
            ps.setString(2, tag);
            ps.setString(3, nomeCompagnia);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Inserimento fallito.");
            throw new SQLException();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Impossibile chiudere la connessione.");
            }
        }
    }

    /**
     * Elimina un profilo social della compagnia.
     *
     * @param nomeSocial il nome del social
     * @param tag        il tag nel social
     * @throws SQLException
     */
    public void eliminaSocial(String nomeSocial, String tag) throws SQLException {
        PreparedStatement ps = null;
        String query = "delete from navigazione.AccountSocial "
                + "where nomesocial = ? AND tag = ?";

        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, nomeSocial);
            ps.setString(2, tag);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Eliminazione fallita.");
            throw new SQLException();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Impossibile chiudere la connessione.");
            }
        }
    }

    /**
     * Aggiunge un indirizzo email della compagnia.
     *
     * @param email         l'email
     * @param nomeCompagnia il nome della compagnia
     * @throws SQLException
     */
    public void aggiungiEmail(String email, String nomeCompagnia) throws SQLException {
        PreparedStatement ps = null;
        String query = "insert into navigazione.Email"
                + " values (?,?)";

        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, email);
            ps.setString(2, nomeCompagnia);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Aggiunta fallita.");
            throw new SQLException();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Impossibile chiudere la connessione.");
            }
        }
    }

    /**
     * Elimina un indirizzo email della compagnia.
     *
     * @param email l'email
     * @throws SQLException
     */
    public void eliminaEmail(String email) throws SQLException {
        PreparedStatement ps = null;
        String query = "delete from navigazione.Email " +
                "where indirizzo = ?";

        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, email);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Eliminazione fallita.");
            throw new SQLException();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Impossibile chiudere la connessione.");
            }
        }
    }

    /**
     * Aggiunge un recapito telefonico della compagnia.
     *
     * @param telefono      il telefono
     * @param nomeCompagnia il nome della compagnia
     * @throws SQLException
     */
    public void aggiungiTelefono(String telefono, String nomeCompagnia) throws SQLException {
        PreparedStatement ps = null;
        String query = "insert into navigazione.Telefono"
                + " values (?,?)";

        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, telefono);
            ps.setString(2, nomeCompagnia);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Aggiunta fallita.");
            throw new SQLException();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Impossibile chiudere la connessione.");
            }
        }
    }

    /**
     * Elimina un recapito telefonico della compagnia
     *
     * @param telefono il telefono
     * @throws SQLException
     */
    public void eliminaTelefono(String telefono) throws SQLException {
        PreparedStatement ps = null;
        String query = "delete from navigazione.Telefono " +
                "where numero = ?";

        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, telefono);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Eliminazione fallita.");
            throw new SQLException();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Impossibile chiudere la connessione.");
            }
        }
    }

    /**
     * Modifica il sito web della compagnia.
     *
     * @param sito          il sito web
     * @param nomeCompagnia il nome della compagnia
     * @throws SQLException
     */
    public void modificaSitoWeb(String sito, String nomeCompagnia) throws SQLException {
        PreparedStatement ps = null;
        String query = "update navigazione.Compagnia " +
                        "set sitoweb = ? " +
                        "where login = ?";

        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, sito);
            ps.setString(2, nomeCompagnia);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Modifica fallita.");
            throw new SQLException();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Impossibile chiudere la connessione.");
            }
        }
    }
}