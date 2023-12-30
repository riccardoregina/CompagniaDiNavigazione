package postgresqlDAO;

import database.*;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Date;

/**
 * The type Cliente db.
 */
public class ClienteDAO implements dao.ClienteDAO {
    private Connection connection;
    public ClienteDAO() {
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
        + " from navigazione.cliente"
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
            return found;
        } catch (SQLException e) {
            System.out.println("Accesso negato.");
            e.printStackTrace();
        }
        return found;
    }

    /**
     * Fetch cliente from db.
     *
     * @param login   the login
     * @param nome    the nome
     * @param cognome the cognome
     */
    public void fetchCliente(String login, String nome, String cognome) {
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
                nome = rs.getString("nome");
                cognome = rs.getString("cognome");
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
     * Fetch veicoli cliente.
     *
     * @param login        the login
     * @param veicoliTarga the veicoli targa
     * @param veicoliTipo  the veicoli tipo
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
            connection.close();
        } catch (SQLException e) {
            System.out.println("Richiesta al DB fallita.");
            e.printStackTrace();
        }
    }

    /**
     * Fetch compagnie.
     *
     * @param login             the login
     * @param nomeCompagnia     the nome compagnia
     * @param sitoWeb           the sito web
     * @param compagniaSocial   the compagnia social
     * @param nomeSocial        the nome social
     * @param tagSocial         the tag social
     * @param compagniaEmail    the compagnia email
     * @param indirizzoEmail    the indirizzo email
     * @param compagniaTelefono the compagnia telefono
     * @param numeroTelefono    the numero telefono
     */
    public void fetchCompagnie(ArrayList<String> login, ArrayList<String> nomeCompagnia, ArrayList<String> sitoWeb, ArrayList<String> compagniaSocial, ArrayList<String> nomeSocial, ArrayList<String> tagSocial, ArrayList<String> compagniaEmail, ArrayList<String> indirizzoEmail, ArrayList<String> compagniaTelefono, ArrayList<String> numeroTelefono) {
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

            connection.close();
        } catch (SQLException e) {
            System.out.println("Richiesta al DB fallita.");
            e.printStackTrace();
        }
    }

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
     * Fetch natanti.
     *
     * @param compagnia          the compagnia
     * @param nome               the nome
     * @param capienzaPasseggeri the capienza passeggeri
     * @param capienzaVeicoli    the capienza veicoli
     * @param tipo               the tipo
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
            connection.close();
        } catch (SQLException e) {
            System.out.println("Aggiunta fallita.");
            e.printStackTrace();
        }
    }

    /**
     * Fetch periodi attivita corse.
     *
     * @param idPeriodo  the id periodo
     * @param dataInizio the data inizio
     * @param dataFine   the data fine
     * @param giorni     the giorni
     * @param corsa      the corsa
     * @param compagnia  the compagnia
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

            connection.close();
        } catch (SQLException e) {
            System.out.println("Richiesta al DB fallita.");
            e.printStackTrace();
        }
    }

    /**
     * Fetch corse regolari.
     *
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
     * @param compagniaCorsa  the compagnia corsa
     * @param nomeNatante     the nome natante
     */
    public void fetchCorseRegolari(ArrayList<Integer> idCorsa, ArrayList<Integer> idPortoPartenza, ArrayList<Integer> idPortoArrivo, ArrayList<LocalTime> orarioPartenza, ArrayList<LocalTime> orarioArrivo, ArrayList<Float> costoIntero, ArrayList<Float> scontoRidotto, ArrayList<Float> costoBagaglio, ArrayList<Float> costoPrevendita, ArrayList<Float> costoVeicolo, ArrayList<String> compagniaCorsa, ArrayList<String> nomeNatante) {
        Statement s = null;
        ResultSet rs = null;
        String query = "select * from navigazione.CorsaRegolare";

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
            }
            rs.close();
            s.close();

            connection.close();
        } catch (SQLException e) {
            System.out.println("Richiesta al DB fallita.");
            e.printStackTrace();
        }
    }

    /**
     * Fetch corse specifiche.
     *
     * @param compagniaCorsaS the compagnia corsa s
     * @param corsaRegolare   the corsa regolare
     * @param data            the data
     * @param postiDispPass   the posti disp pass
     * @param postiDispVei    the posti disp vei
     * @param minutiRitardo   the minuti ritardo
     * @param cancellata      the cancellata
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

            connection.close();
        } catch (SQLException e) {
            System.out.println("Richiesta al DB fallita.");
            e.printStackTrace();
        }
    }

    /**
     * Fetch biglietti cliente.
     *
     * @param login         the login
     * @param idBiglietto   the id biglietto
     * @param idCorsa       the id corsa
     * @param dataCorsa     the data corsa
     * @param targaVeicolo  the targa veicolo
     * @param prevendita    the prevendita
     * @param bagaglio      the bagaglio
     * @param prezzo        the prezzo
     * @param dataAcquisto  the data acquisto
     * @param etaPasseggero the eta passeggero
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
            connection.close();
        } catch (SQLException e) {
            System.out.println("Aggiunta fallita.");
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
     * @param idBiglietto   output parameter
     */
    public void acquistaBiglietto(int idCorsa, LocalDate data, String loginCliente, String targaVeicolo, boolean prevendita, boolean bagaglio, float prezzo, LocalDate dataAcquisto, int etaPasseggero, Integer idBiglietto) throws SQLException{
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
            idBiglietto = rs.getInt("idBiglietto");
            rs.close();
            ps.close();

            connection.close();
        } catch (SQLException e) {
            System.out.println("Acquisto fallito.");
            throw new SQLException();
        }
    }

    /**
     * Aggiungi veicolo.
     *
     * @param targa             the targa
     * @param tipo              the tipo
     * @param loginProprietario the login proprietario
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

            connection.close();
        } catch (SQLException e) {
            System.out.println("Aggiunta fallita.");
            throw new SQLException();
        }
    }
}
