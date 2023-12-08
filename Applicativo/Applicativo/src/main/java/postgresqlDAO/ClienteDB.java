package postgresqlDAO;

import database.*;
import dao.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.BitSet;
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
                + "from Cliente"
                + "where login = ?";

        try {
            conn = c.getConnection();
        } catch (SQLException e) {
            System.out.println("Connessione fallita.");
            e.printStackTrace();
        }

        try {
            //prima query
            conn.prepareStatement(query);
            ps.setString(1, login);
            ps.executeQuery();

            if (rs.next()) {
                nome = rs.getString("nome");
                cognome = rs.getString("cognome");
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
     * Fetch veicoli cliente.
     *
     * @param login        the login
     * @param veicoliTarga the veicoli targa
     * @param veicoliTipo  the veicoli tipo
     */
    public void fetchVeicoliCliente(String login, ArrayList<String> veicoliTarga, ArrayList<String> veicoliTipo) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query2 = "select * from Veicolo where Proprietario = ?";

        try {
            conn = c.getConnection();
        } catch (SQLException e) {
            System.out.println("Connessione fallita.");
            e.printStackTrace();
        }

        try {
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
        String query = "select * from Compagnia";

        try {
            conn = c.getConnection();
        } catch (SQLException e) {
            System.out.println("Connessione fallita.");
            e.printStackTrace();
        }

        try {
            s.executeQuery(query);

            while (rs.next()) {
                login.add(rs.getString("login"));
                nomeCompagnia.add(rs.getString("nome"));
                sitoWeb.add(rs.getString("sitoWeb"));
            }
            rs.close();
            s.close();

            conn.close();
        } catch (SQLException e) {
            System.out.println("Richiesta al DB fallita.");
            e.printStackTrace();
        }

        //Inoltre...
        fetchContattiCompagnie(compagniaSocial, nomeSocial, tagSocial, compagniaEmail, indirizzoEmail, compagniaTelefono, numeroTelefono);
    }

    private void fetchContattiCompagnie(ArrayList<String> compagniaSocial, ArrayList<String> nomeSocial, ArrayList<String> tagSocial, ArrayList<String> compagniaEmail, ArrayList<String> indirizzoEmail, ArrayList<String> compagniaTelefono, ArrayList<String> numeroTelefono) {
        Statement s = null;
        ResultSet rs = null;
        String query1 = "select * from AccountSocial";
        String query2 = "select * from Email";
        String query3 = "select * from Telefono";


        try {
            conn = c.getConnection();
        } catch (SQLException e) {
            System.out.println("Connessione fallita.");
            e.printStackTrace();
        }

        try {
            s.executeQuery(query1);

            while (rs.next()) {
                compagniaSocial.add(rs.getString("Compagnia"));
                nomeSocial.add(rs.getString("nomeSocial"));
                tagSocial.add(rs.getString("tag"));
            }
            rs.close();
            s.close();

            s.executeQuery(query2);

            while (rs.next()) {
                compagniaEmail.add(rs.getString("Compagnia"));
                indirizzoEmail.add(rs.getString("indirizzo"));
            }
            rs.close();
            s.close();

            s.executeQuery(query3);

            while (rs.next()) {
                compagniaTelefono.add(rs.getString("Compagnia"));
                numeroTelefono.add(rs.getString("numero"));
            }
            rs.close();
            s.close();

            conn.close();
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
        String query = "select * from Porto";

        try {
            conn = c.getConnection();
        } catch (SQLException e) {
            System.out.println("Connessione fallita.");
            e.printStackTrace();
        }

        try {
            s.executeQuery(query);

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
            conn.close();
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
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "select * from Natante";

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
                compagnia.add(rs.getString("Compagnia"));
                nome.add(rs.getString("nome"));
                capienzaPasseggeri.add(rs.getInt("capienzaPasseggeri"));
                capienzaVeicoli.add(rs.getInt("capienzaVeicoli"));
                tipo.add(rs.getString("tipo"));
            }
            rs.close();
            ps.close();
            conn.close();
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
     */
    public void fetchPeriodiAttivitaCorse(ArrayList<Integer> idPeriodo ,ArrayList<Date> dataInizio, ArrayList<Date> dataFine, ArrayList<BitSet> giorni, ArrayList<Integer> corsa) {
        Statement s = null;
        ResultSet rs = null;
        String query = "select * from Periodo natural join AttivaIn";

        try {
            conn = c.getConnection();
        } catch (SQLException e) {
            System.out.println("Connessione fallita.");
            e.printStackTrace();
        }

        try {
            s.executeQuery(query);

            while (rs.next()) {
                idPeriodo.add(rs.getInt("idPeriodo"));
                dataInizio.add(rs.getDate("dataInizio"));
                dataFine.add(rs.getDate("dataFine"));
                giorni.add((rs.getBytes("giorni"))); //VEDI BENE COME CONVERTIRE
                corsa.add(rs.getInt("idCorsa"));
            }
            rs.close();
            s.close();

            conn.close();
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
        String query = "select * from CorsaRegolare";

        try {
            conn = c.getConnection();
        } catch (SQLException e) {
            System.out.println("Connessione fallita.");
            e.printStackTrace();
        }

        try {
            s.executeQuery(query);

            while (rs.next()) {
                idCorsa.add(rs.getInt("idCorsa"));
                idPortoPartenza.add(rs.getInt("idPortoPartenza"));
                idPortoArrivo.add(rs.getInt("idPortoArrivo"));
                orarioPartenza.add(rs.getTime("orarioPartenza")); //VEDI CONVERSIONE
                orarioArrivo.add(rs.getTime("orarioArrivo")); //VEDI CONVERSIONE
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

            conn.close();
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
        String query = "select * from CorsaSpecifica join CorsaRegolare on Corsa = idCorsa";

        try {
            conn = c.getConnection();
        } catch (SQLException e) {
            System.out.println("Connessione fallita.");
            e.printStackTrace();
        }

        try {
            s.executeQuery(query);

            while (rs.next()) {
                compagniaCorsaS.add(rs.getString("Compagnia"));
                corsaRegolare.add(rs.getInt("idCorsa"));
                data.add(rs.getDate("data")); //VEDI CONVERSIONE
                postiDispPass.add(rs.getInt("postiDispPass"));
                postiDispVei.add(rs.getInt("postiDispVei"));
                minutiRitardo.add(rs.getInt("minutiRitardo"));
                cancellata.add(rs.getBoolean("cancellata"));
            }
            rs.close();
            s.close();

            conn.close();
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
        String query = "select * from Biglietto where Cliente = ?";

        try {
            conn = c.getConnection();
        } catch (SQLException e) {
            System.out.println("Connessione fallita.");
            e.printStackTrace();
        }

        try {
            conn.prepareStatement(query);
            ps.setString(1, login);

            ps.executeQuery();

            while(rs.next()) {
                idBiglietto.add(rs.getInt("idBiglietto"));
                idCorsa.add(rs.getInt("idCorsa"));
                dataCorsa.add(rs.getDate("data")); //VEDI CASTING
                dataAcquisto.add(rs.getDate("dataAcquisto")); //VEDI CASTING
                targaVeicolo.add(rs.getString("Veicolo"));
                prevendita.add(rs.getBoolean("prevendita"));
                bagaglio.add(rs.getBoolean("bagaglio"));
                prezzo.add(rs.getFloat("prezzo"));
                etaPasseggero.add(rs.getInt("etaPasseggero"));
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Aggiunta fallita.");
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
    public void acquistaBiglietto(int idCorsa, LocalDate data, String loginCliente, String targaVeicolo, boolean prevendita, boolean bagaglio, float prezzo, Date dataAcquisto, int etaPasseggero) {
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
}
