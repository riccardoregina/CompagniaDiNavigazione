package postgresqlDAO;

import database.*;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * La classe ClienteDAO.
 */
public class ClienteDAO implements dao.ClienteDAO {
    private Logger logger = Logger.getLogger(this.getClass().getName());
    private Connection connection;

    /**
     * Istanzia un Data Access Object per richiedere accesso a dati, riguardanti informazioni necessarie al cliente,
     * presenti sul DB.
     *
     * @throws SQLException the sql exception
     */
    public ClienteDAO() throws SQLException {
        try {
            connection = ConnessioneDB.getInstance().getConnection();
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * Verifica sul DB se esiste un cliente con login e password inseriti.
     *
     * @param login la login
     * @param pw    la password
     */
    public void accede(String login, String pw) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "select *"
        + " from navigazione.cliente"
        + " where login = ? and password = ?";

        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, login);
            ps.setString(2, pw);
            rs = ps.executeQuery();
            if (!rs.next()) {
                rs.close();
                ps.close();
                logger.log(Level.FINE, "Credenziali errate / utente non esistente.");
                throw new SQLException("Credenziali errate / utente non esistente.");
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
     * Effettua il fetch delle generalita' del cliente.
     *
     * @param login   la login
     * @param nome    output - il nome
     * @param cognome output - il cognome
     */
    public void fetchCliente(String login, ArrayList<String> nome, ArrayList<String> cognome) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "select *"
                + " from navigazione.Cliente"
                + " where login = ?";

        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, login);
            rs = ps.executeQuery();

            if (rs.next()) {
                nome.add(rs.getString("nome"));
                cognome.add(rs.getString("cognome"));
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
     * Effettua il fetch dei veicoli del cliente.
     *
     * @param login        la login
     * @param veicoliTarga output - le targhe dei veicoli
     * @param veicoliTipo  output - i tipi dei veicoli
     */
    public void fetchVeicoliCliente(String login, ArrayList<String> veicoliTarga, ArrayList<String> veicoliTipo) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "select * from navigazione.Veicolo where Proprietario = ?";

        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, login);
            rs = ps.executeQuery();

            while (rs.next()) {
                veicoliTarga.add(rs.getString("targa"));
                veicoliTipo.add(rs.getString("tipo"));
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
     * Effettua il fetch delle compagnie.
     *
     * @param login         output - le login delle compagnie
     * @param nomeCompagnia output - i nomi delle compagnie
     * @param sitoWeb       output - i siti web delle compagnie
     */
    public void fetchCompagnie(ArrayList<String> login, ArrayList<String> nomeCompagnia, ArrayList<String> sitoWeb) {
        Statement s = null;
        ResultSet rs = null;
        String query = "select * from navigazione.Compagnia";

        try {
            s = connection.createStatement();
            rs = s.executeQuery(query);

            while (rs.next()) {
                login.add(rs.getString("login"));
                nomeCompagnia.add(rs.getString("nome"));
                sitoWeb.add(rs.getString("sitoWeb"));
            }
            rs.close();
            s.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Richiesta al DB fallita.");
        }  finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Impossibile chiudere la connessione.");
            }
        }
    }

    /**
     * Effettua il fetch dei contatti di tutte le compagnie del DB.
     *
     * @param compagniaSocial output - le compagnie a cui sono legati i profili social
     * @param nomeSocial output - i nomi dei social
     * @param tagSocial output - i tag nei social
     * @param compagniaEmail output - le compagnie a cui sono legati gli indirizzi email
     * @param indirizzoEmail output - gli indirizzi email
     * @param compagniaTelefono output - le compagnie a cui sono legati i recapiti telefonici
     * @param numeroTelefono output - i recapiti telefonici
     */
    public void fetchContattiCompagnie(ArrayList<String> compagniaSocial, ArrayList<String> nomeSocial, ArrayList<String> tagSocial, ArrayList<String> compagniaEmail, ArrayList<String> indirizzoEmail, ArrayList<String> compagniaTelefono, ArrayList<String> numeroTelefono) {
        Statement s = null;
        ResultSet rs = null;
        String query1 = "select * from navigazione.AccountSocial";
        String query2 = "select * from navigazione.Email";
        String query3 = "select * from navigazione.Telefono";

        try {
            s = connection.createStatement();
            rs = s.executeQuery(query1);

            while (rs.next()) {
                compagniaSocial.add(rs.getString("Compagnia"));
                nomeSocial.add(rs.getString("nomeSocial"));
                tagSocial.add(rs.getString("tag"));
            }
            rs.close();
            s.close();

            s = connection.createStatement();
            rs = s.executeQuery(query2);

            while (rs.next()) {
                compagniaEmail.add(rs.getString("Compagnia"));
                indirizzoEmail.add(rs.getString("indirizzo"));
            }
            rs.close();
            s.close();

            s = connection.createStatement();
            rs = s.executeQuery(query3);

            while (rs.next()) {
                compagniaTelefono.add(rs.getString("Compagnia"));
                numeroTelefono.add(rs.getString("numero"));
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
     * Effettua il fetch di tutti i porti del DB.
     *
     * @param idPorto        output - gli id dei porti
     * @param comuni         output - i comuni dei porti
     * @param indirizzi      output - gli indirizzi dei porti
     * @param numeriTelefono output - i recapiti telefonici dei porti
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
     * Effettua il fetch di tutti i natanti del DB.
     *
     * @param compagnia          output - le compagnie che posseggono i natanti
     * @param nome               output - i nomi dei natanti
     * @param capienzaPasseggeri output - le capienze di passeggeri dei natanti
     * @param capienzaVeicoli    output - le capienze di veicoli dei natanti
     * @param tipo               output - i tipi dei natanti
     */
    public void fetchNatanti(ArrayList<String> compagnia, ArrayList<String> nome, ArrayList<Integer> capienzaPasseggeri, ArrayList<Integer> capienzaVeicoli, ArrayList<String> tipo) {
        Statement s = null;
        ResultSet rs = null;
        String query = "select * from navigazione.Natante";

        try {
            s = connection.createStatement();
            rs = s.executeQuery(query);

            while(rs.next()) {
                compagnia.add(rs.getString("Compagnia"));
                nome.add(rs.getString("nome"));
                capienzaPasseggeri.add(rs.getInt("capienzaPasseggeri"));
                capienzaVeicoli.add(rs.getInt("capienzaVeicoli"));
                tipo.add(rs.getString("tipo"));
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
     * Effettua il fetch dei periodi di attivita delle corse
     *
     * @param idPeriodo  output - gli id dei periodi
     * @param dataInizio output - le date di inizio dei periodi
     * @param dataFine   output - le date di fine dei periodi
     * @param giorni     output - i giorni di attivita' dei periodi
     * @param corsa      output - le corse attive nei periodi
     * @param compagnia  output - le compagnie che erogano le corse
     */
    public void fetchPeriodiAttivitaCorse(ArrayList<Integer> idPeriodo ,ArrayList<LocalDate> dataInizio, ArrayList<LocalDate> dataFine, ArrayList<String> giorni, ArrayList<Integer> corsa, ArrayList<String> compagnia) {
        Statement s = null;
        ResultSet rs = null;
        String query = "select * from navigazione.Periodo natural join navigazione.AttivaIn natural join navigazione.CorsaRegolare";

        try {
            s = connection.createStatement();
            rs = s.executeQuery(query);

            while (rs.next()) {
                compagnia.add(rs.getString("Compagnia"));
                idPeriodo.add(rs.getInt("idPeriodo"));
                dataInizio.add(rs.getDate("dataInizio").toLocalDate());
                dataFine.add(rs.getDate("dataFine").toLocalDate());
                giorni.add((rs.getString("giorni")));
                corsa.add(rs.getInt("idCorsa"));
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
     * Effettua il fetch di tutte le corse regolari del DB.
     *
     * @param idCorsa         output - gli id delle corse
     * @param idPortoPartenza output - gli id dei porti di partenza
     * @param idPortoArrivo   output - gli id dei porti di arrivo
     * @param orarioPartenza  output - gli orari di partenza
     * @param orarioArrivo    output - gli orari di arrivo
     * @param costoIntero     output - i costi dei biglietti interi
     * @param scontoRidotto   output - le percentuali di sconto per i biglietti ridotti
     * @param costoBagaglio   output - i costi aggiuntivi per il bagaglio
     * @param costoPrevendita output - i costi aggiuntivi per la prevendita
     * @param costoVeicolo    output - i costi aggiuntivi per il veicolo
     * @param compagniaCorsa  output - le compagnie che erogano le corse
     * @param nomeNatante     output - i nomi dei natanti impiegati nelle corse
     * @param corsaSup        output - le corse superiori delle corse
     */
    public void fetchCorseRegolari(ArrayList<Integer> idCorsa, ArrayList<Integer> idPortoPartenza, ArrayList<Integer> idPortoArrivo, ArrayList<LocalTime> orarioPartenza, ArrayList<LocalTime> orarioArrivo, ArrayList<Float> costoIntero, ArrayList<Float> scontoRidotto, ArrayList<Float> costoBagaglio, ArrayList<Float> costoPrevendita, ArrayList<Float> costoVeicolo, ArrayList<String> compagniaCorsa, ArrayList<String> nomeNatante, ArrayList<Integer> corsaSup) {
        Statement s = null;
        ResultSet rs = null;
        String query = "select * from navigazione.CorsaRegolare order by idCorsa";
        /*
         * La order by ci consente di riempire correttamente, per ogni corsa regolare da costruire nel model,
         * il campo corsaSup: la corsaSup avrá sicuramente idCorsa < in quanto viene creata prima delle sue
         * sottocorse.
         */

        try {
            s = connection.createStatement();
            rs = s.executeQuery(query);

            while (rs.next()) {
                idCorsa.add(rs.getInt("idCorsa"));
                idPortoPartenza.add(rs.getInt("PortoPartenza"));
                idPortoArrivo.add(rs.getInt("PortoArrivo"));
                orarioPartenza.add(rs.getTime("orarioPartenza").toLocalTime());
                orarioArrivo.add(rs.getTime("orarioArrivo").toLocalTime());
                costoIntero.add(rs.getFloat("costoIntero"));
                scontoRidotto.add(rs.getFloat("scontoRidotto"));
                costoBagaglio.add(rs.getFloat("costoBagaglio"));
                costoPrevendita.add(rs.getFloat("costoPrevendita"));
                costoVeicolo.add(rs.getFloat("costoVeicolo"));
                compagniaCorsa.add(rs.getString("Compagnia"));
                nomeNatante.add(rs.getString("Natante"));
                corsaSup.add(rs.getInt("CorsaSup"));
            }
            rs.close();
            s.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Richiesta al DB fallita.");
        }  finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Impossibile chiudere la connessione.");
            }
        }
    }

    /**
     * Effettua il fetch delle corse specifiche del DB.
     *
     * @param compagniaCorsaS output - le compagnie che erogano le corse
     * @param corsaRegolare   output - le corse regolari che generano le corse specifiche
     * @param data            output - le date delle corse
     * @param postiDispPass   output - i posti disponibili per passeggeri delle corse
     * @param postiDispVei    output - i posti disponibile per veicoli delle corse
     * @param minutiRitardo   output - i minuti di ritardo delle corse
     * @param cancellata      output - valori booleani che indicano se le corse sono state cancellate o meno
     */
    public void fetchCorseSpecifiche(ArrayList<String> compagniaCorsaS, ArrayList<Integer> corsaRegolare, ArrayList<LocalDate> data, ArrayList<Integer> postiDispPass, ArrayList<Integer> postiDispVei, ArrayList<Integer> minutiRitardo, ArrayList<Boolean> cancellata) {
        Statement s = null;
        ResultSet rs = null;
        String query = "select * from navigazione.CorsaSpecifica natural join navigazione.CorsaRegolare";

        try {
            s = connection.createStatement();
            rs = s.executeQuery(query);

            while (rs.next()) {
                compagniaCorsaS.add(rs.getString("Compagnia"));
                corsaRegolare.add(rs.getInt("idCorsa"));
                data.add(rs.getDate("data").toLocalDate());
                postiDispPass.add(rs.getInt("postiDispPass"));
                postiDispVei.add(rs.getInt("postiDispVei"));
                minutiRitardo.add(rs.getInt("minutiRitardo"));
                cancellata.add(rs.getBoolean("cancellata"));
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
     * Effettua il fetch dei biglietti acquistati dal cliente dal DB.
     *
     * @param login         la login del cliente
     * @param idBiglietto   gli id dei biglietti
     * @param idCorsa       gli id delle corse
     * @param dataCorsa     le date delle corse
     * @param targaVeicolo  le targhe dei veicoli
     * @param prevendita    le prevendite dei biglietti
     * @param bagaglio      valori booleani che indicano se il cliente ha pagato un costo aggiuntivo per un bagaglio o meno
     * @param prezzo        il prezzo di acquisto
     * @param dataAcquisto  la data di acquisto
     * @param etaPasseggero l'eta del passeggero
     */
    public void fetchBigliettiCliente(String login, ArrayList<Integer> idBiglietto, ArrayList<Integer> idCorsa, ArrayList<LocalDate> dataCorsa, ArrayList<String> targaVeicolo, ArrayList<Boolean> prevendita, ArrayList<Boolean> bagaglio, ArrayList<Float> prezzo, ArrayList<LocalDate> dataAcquisto, ArrayList<Integer> etaPasseggero) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "select * from navigazione.Biglietto where Cliente = ?";

        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, login);

            rs = ps.executeQuery();

            while(rs.next()) {
                idBiglietto.add(rs.getInt("idBiglietto"));
                idCorsa.add(rs.getInt("idCorsa"));
                dataCorsa.add(rs.getDate("data").toLocalDate());
                dataAcquisto.add(rs.getDate("dataAcquisto").toLocalDate());
                targaVeicolo.add(rs.getString("Veicolo"));
                prevendita.add(rs.getBoolean("prevendita"));
                bagaglio.add(rs.getBoolean("bagaglio"));
                prezzo.add(rs.getFloat("prezzo"));
                etaPasseggero.add(rs.getInt("etaPasseggero"));
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
     * Registra l'acquisto di un biglietto da parte del cliente.
     *
     * @param idCorsa       l'id della corsa
     * @param data          la data della corsa
     * @param loginCliente  la login del cliente
     * @param targaVeicolo  la targa del veicolo
     * @param prevendita    un valore booleano che indica se il cliente sta pagando la prevendita o meno
     * @param bagaglio      un valore booleano che indica se il cliente sta pagando per un bagaglio o meno
     * @param prezzo        il prezzo di acquisto
     * @param dataAcquisto  la data di acquisto
     * @param etaPasseggero l'eta del passeggero
     * @param idBiglietto   output - l'id del biglietto assegnato dal DB
     */
    public void acquistaBiglietto(int idCorsa, LocalDate data, String loginCliente, String targaVeicolo, boolean prevendita, boolean bagaglio, float prezzo, LocalDate dataAcquisto, int etaPasseggero, AtomicInteger idBiglietto) throws SQLException{
        //l'aggiornamento dei posti disponibili sará effettuato dal DB
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "insert into navigazione.Biglietto (idCorsa, data, cliente, veicolo, prevendita, bagaglio, prezzo, dataAcquisto, etaPasseggero)" +
                " values (?,?,?,?,?,?,?,?,?)" +
                "returning idbiglietto";

        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, idCorsa);
            ps.setDate(2, java.sql.Date.valueOf(data));
            ps.setString(3, loginCliente);
            ps.setString(4, targaVeicolo);
            ps.setBoolean(5, prevendita);
            ps.setBoolean(6, bagaglio);
            ps.setFloat(7, prezzo);
            ps.setDate(8, java.sql.Date.valueOf(dataAcquisto));
            ps.setInt(9, etaPasseggero);
            rs = ps.executeQuery();
            rs.next();
            idBiglietto.set(rs.getInt("idBiglietto"));
            rs.close();
            ps.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Acquisto fallito.");
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
     * Aggiunge un veicolo del cliente al DB.
     *
     * @param targa             la targa del veicolo
     * @param tipo              il tipo di veicolo
     * @param loginProprietario la login del cliente
     */
    public void aggiungeVeicolo(String tipo, String targa, String loginProprietario) throws SQLException{
        PreparedStatement ps = null;
        String query = "insert into navigazione.Veicolo" +
                " values (?,?,?::navigazione.tipoveicolo)";

        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, targa);
            ps.setString(2, loginProprietario);
            ps.setString(3, tipo);
            
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Aggiunta del veicolo fallita.");
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
     * Registra un nuovo cliente sul DB. Se non vi riesce, lancia una eccezione.
     *
     * @param nome    il nome del nuovo cliente
     * @param cognome il cognome del nuovo cliente
     * @param login   la login del nuovo cliente
     * @param pwd     la password del nuovo cliente
     * @throws SQLException
     */
    public void siRegistra(String nome, String cognome, String login, String pwd) throws SQLException {
        PreparedStatement ps = null;
        String query = "insert into navigazione.cliente" +
                " values (?,?,?,?)";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, login);
            ps.setString(2, pwd);
            ps.setString(3, nome);
            ps.setString(4, cognome);

            ps.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Registrazione fallita.");
            throw new SQLException();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Impossibile chiudere la connessione.");
            }
        }
    }

    public void rimuoveVeicolo(String targa, String login) throws SQLException {
        PreparedStatement ps = null;
        String query = "delete from navigazione.veicolo" +
                " where targa = ? and proprietario = ?";

        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, targa);
            ps.setString(2, login);

            ps.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Rimozione del veicolo fallita.");
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
