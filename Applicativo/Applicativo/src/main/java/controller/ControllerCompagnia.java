package controller;

import model.*;
import postgresqlDAO.CompagniaDAO;
import unnamed.Pair;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * La classe ControllerCompagnia si occupa di:
 * + ricevere richieste da un interfaccia
 * + provvedere a soddisfare tali richieste, che possono interessare: il Model, il DB, o entrambi.
 * Questa classe contiene i dati del Model presenti nei membri: compagnia, porti, corseSpecifiche, periodiNonCollegatiACorse.
 */
public class ControllerCompagnia {
    private Logger logger = Logger.getLogger(this.getClass().getName());
    private Compagnia compagnia;
    private HashMap<Integer, Porto> porti;
    private HashMap<Pair, CorsaSpecifica> corseSpecifiche;
    private HashMap<Integer, Periodo> periodiNonCollegatiACorse;

    /**
     * Istanzia un nuovo ControllerCompagnia
     */
    public ControllerCompagnia() {
        porti = new HashMap<>();
        corseSpecifiche = new HashMap<>();
        periodiNonCollegatiACorse = new HashMap<>();
    }

    /**
     * Prende login e password dalla GUI,
     * richiede una connessione al DB,
     * se compagnia esiste nel DB,
     * crea il contesto nel model per l'esecuzione del programma.
     *
     * @param login    la login
     * @param password la password
     * @throws SQLException the sql exception
     */
    public void compagniaAccede(String login, String password) throws SQLException {
        try {
            CompagniaDAO compagniaDAO = new CompagniaDAO();
            compagniaDAO.accede(login, password);

            //prende dal DB tutte i dati della compagnia e costruisce l'ambiente per l'uso dell'applicativo
            buildModel(login, password);
        } catch(SQLException e) {
            String message = e.getMessage();
            logger.log(Level.FINE, message);
            throw e;
        }
    }

    /**
     * Comanda il fetch del contesto al DB.
     *
     * @param login    il login
     * @param password la password
     * @throws SQLException the sql exception
     */
    public void buildModel(String login, String password) throws SQLException {
        try {
            buildPorti();
            buildCompagnia(login, password);
            buildNatantiCompagnia(login);
            buildCorseRegolari(login);
            buildPeriodi(login);
            buildCorseSpecifiche();
        } catch (SQLException e) {
            logger.log(Level.FINE, e.getMessage());
            throw e;
        }
    }

    /**
     * Comanda al DB il fetch dei porti e riempie il model con i dati ottenuti.
     *
     * @throws SQLException the sql exception
     */
    public void buildPorti() throws SQLException {
        try {
            CompagniaDAO compagniaDAO = new CompagniaDAO();
            ArrayList<Integer> idPorto = new ArrayList<>();
            ArrayList<String> comuni = new ArrayList<>();
            ArrayList<String> indirizzi = new ArrayList<>();
            ArrayList<String> numeriTelefono = new ArrayList<>();
            compagniaDAO.fetchPorti(idPorto, comuni, indirizzi, numeriTelefono);
            for (int i = 0; i < comuni.size(); i++) {
                porti.put(idPorto.get(i), new Porto(idPorto.get(i), comuni.get(i), indirizzi.get(i), numeriTelefono.get(i)));
            }
        } catch (SQLException e) {
            logger.log(Level.FINE, e.getMessage());
            throw e;
        }
    }

    /**
     * Comanda al DB il fetch della compagnia e riempie il model con i dati ottenuti.
     *
     * @param loginCompagnia la login della compagnia
     * @param password       la password
     * @throws SQLException
     */
    public void buildCompagnia(String loginCompagnia, String password) throws SQLException {
        try {
            CompagniaDAO compagniaDAO = new CompagniaDAO();
            ArrayList<String> nomeCompagnia = new ArrayList<String>();
            ArrayList<String> telefono = new ArrayList<>();
            ArrayList<String> email = new ArrayList<>();
            ArrayList<String> nomeSocial = new ArrayList<>();
            ArrayList<String> tagSocial = new ArrayList<>();
            ArrayList<String> sitoWeb = new ArrayList<String>();
            compagniaDAO.fetchCompagnia(loginCompagnia, nomeCompagnia, telefono, email, nomeSocial, tagSocial, sitoWeb);

            compagnia = new Compagnia(loginCompagnia, password, nomeCompagnia.getFirst());
            compagnia.setTelefoni(telefono);
            compagnia.setEmails(email);
            compagnia.setSitoWeb(sitoWeb.getFirst());

            ArrayList<AccountSocial> accountSocial = new ArrayList<>();
            for (int i = 0; i < tagSocial.size(); i++) {
                accountSocial.add(new AccountSocial(compagnia, nomeSocial.get(i), tagSocial.get(i)));
            }
            compagnia.setAccounts(accountSocial);
        } catch (SQLException e) {
            logger.log(Level.FINE, e.getMessage());
            throw e;
        }
    }

    /**
     * Comanda al DB il fetch dei natanti della compagnia e riempie il model con i dati ottenuti.
     *
     * @param loginCompagnia la login della compagnia
     * @throws SQLException
     */
    public void buildNatantiCompagnia(String loginCompagnia) throws SQLException {
        try {
            CompagniaDAO compagniaDAO = new CompagniaDAO();
            ArrayList<String> nomeNatante = new ArrayList<>();
            ArrayList<Integer> capienzaPasseggeri = new ArrayList<>();
            ArrayList<Integer> capienzaVeicoli = new ArrayList<>();
            ArrayList<String> tipo = new ArrayList<>();
            compagniaDAO.fetchNatantiCompagnia(loginCompagnia, nomeNatante, capienzaPasseggeri, capienzaVeicoli, tipo);
            for (int i = 0; i < nomeNatante.size(); i++) {
                compagnia.addNatante(new Natante(compagnia, nomeNatante.get(i), capienzaPasseggeri.get(i), capienzaVeicoli.get(i), tipo.get(i)));
            }
        } catch (SQLException e) {
            logger.log(Level.FINE, e.getMessage());
            throw e;
        }
    }

    /**
     * Comanda al DB il fetch delle corse regolari erogate dalla compagnia e riempie il model con i dati ottenuti.
     *
     * @param loginCompagnia la login della compagnia
     * @throws SQLException
     */
    public void buildCorseRegolari(String loginCompagnia) throws SQLException {
        try {
            CompagniaDAO compagniaDAO = new CompagniaDAO();
            ArrayList<Integer> idCorsa = new ArrayList<>();
            ArrayList<Integer> idPortoPartenza = new ArrayList<>();
            ArrayList<Integer> idPortoArrivo = new ArrayList<>();
            ArrayList<LocalTime> orarioPartenza = new ArrayList<>();
            ArrayList<LocalTime> orarioArrivo = new ArrayList<>();
            ArrayList<Float> costoIntero = new ArrayList<>();
            ArrayList<Float> scontoRidotto = new ArrayList<>();
            ArrayList<Float> costoBagaglio = new ArrayList<>();
            ArrayList<Float> costoPrevendita = new ArrayList<>();
            ArrayList<Float> costoVeicolo = new ArrayList<>();
            ArrayList<String> nomeNatanteCorsa = new ArrayList<>();
            ArrayList<Integer> corsaSup = new ArrayList<>();
            compagniaDAO.fetchCorseRegolari(loginCompagnia, idCorsa, idPortoPartenza, idPortoArrivo, orarioPartenza, orarioArrivo, costoIntero, scontoRidotto, costoBagaglio, costoPrevendita, costoVeicolo, nomeNatanteCorsa, corsaSup);
            for (int i = 0; i < costoIntero.size(); i++) {
                int id = idCorsa.get(i);
                Natante n = compagnia.getNatantiPosseduti().get(nomeNatanteCorsa.get(i));
                Porto pPartenza = porti.get(idPortoPartenza.get(i));
                Porto pArrivo = porti.get(idPortoArrivo.get(i));
                LocalTime oraPartenza = orarioPartenza.get(i);
                LocalTime oraArrivo = orarioArrivo.get(i);
                float cIntero = costoIntero.get(i);
                float sRidotto = scontoRidotto.get(i);
                float cBagaglio = costoBagaglio.get(i);
                float cPrev = costoPrevendita.get(i);
                float cVei = costoVeicolo.get(i);
                CorsaRegolare crSup = compagnia.getCorseErogate().get(corsaSup.get(i));
                compagnia.addCorsaRegolare(new CorsaRegolare(id, compagnia, n, pPartenza, pArrivo, oraPartenza, oraArrivo, cIntero, sRidotto, cBagaglio, cVei, cPrev, crSup));
            }
        } catch (SQLException e) {
            logger.log(Level.FINE, e.getMessage());
            throw e;
        }
    }

    /**
     * Comanda al DB il fetch dei periodi di attivita' delle corse e riempie il model con i dati ottenuti.
     *
     * @param loginCompagnia la login della compagnia
     * @throws SQLException
     */
    public void buildPeriodi(String loginCompagnia) throws SQLException {
        try {
            CompagniaDAO compagniaDAO = new CompagniaDAO();
            HashMap<Integer, Periodo> periodi = new HashMap<>();
            ArrayList<Integer> corsa = new ArrayList<>();
            ArrayList<Integer> idPeriodo = new ArrayList<>();
            ArrayList<LocalDate> dataInizio = new ArrayList<>();
            ArrayList<LocalDate> dataFine = new ArrayList<>();
            ArrayList<String> giorni = new ArrayList<>();
            compagniaDAO.fetchPeriodiAttivitaCorse(loginCompagnia, idPeriodo, dataInizio, dataFine, giorni, corsa);
            //Assegno un periodo alla sua corsa.
            for (int i = 0; i < dataInizio.size(); i++) {
                CorsaRegolare cr = compagnia.getCorseErogate().get(corsa.get(i));
                cr.addPeriodoAttivita(new Periodo(idPeriodo.get(i), dataInizio.get(i), dataFine.get(i), giorni.get(i)));
            }
        } catch (SQLException e) {
            logger.log(Level.FINE, e.getMessage());
            throw e;
        }

    }

    /**
     * Comanda al DB il fetch delle corse specifiche erogate dalla compagnia e riempie il model con i dati ottenuti.
     *
     * @throws SQLException
     */
    public void buildCorseSpecifiche() throws SQLException {
        try {
            CompagniaDAO compagniaDAO = new CompagniaDAO();
            ArrayList<Integer> corsaRegolare = new ArrayList<>();
            ArrayList<LocalDate> data = new ArrayList<>();
            ArrayList<Integer> postiDispPass = new ArrayList<>();
            ArrayList<Integer> postiDispVei = new ArrayList<>();
            ArrayList<Integer> minutiRitardo = new ArrayList<>();
            ArrayList<Boolean> cancellata = new ArrayList<>();
            compagniaDAO.fetchCorseSpecifiche(compagnia.getLogin(),corsaRegolare, data, postiDispPass, postiDispVei, minutiRitardo, cancellata);
            for (int i = 0; i < data.size(); i++) {
                CorsaRegolare cr = compagnia.getCorseErogate().get(corsaRegolare.get(i));
                LocalDate d = data.get(i);
                int pPass = postiDispPass.get(i);
                int pVei = postiDispVei.get(i);
                int mRit = minutiRitardo.get(i);
                boolean canc = cancellata.get(i);
                Pair pair = new Pair(cr.getIdCorsa(), d);
                corseSpecifiche.put(pair, new CorsaSpecifica(cr, d, pPass, pVei, mRit, canc));
            }
        } catch (SQLException e) {
            logger.log(Level.FINE, e.getMessage());
            throw e;
        }
    }

    /**
     * Comanda al DB di aggiungere un natante della compagnia.
     *
     * @param nome               il nome del natante da inserire
     * @param tipo               il tipo del natante da inserire
     * @param capienzaPasseggeri la capienza di passeggeri del natante da inserire
     * @param capienzaVeicoli    la capienza di veicoli del natante da inserire
     * @return true se il natante e' stato aggiunto correttamente, false altrimenti
     */
    public boolean aggiungiNatante (String nome, String tipo, int capienzaPasseggeri, int capienzaVeicoli) {
        try {
            CompagniaDAO compagniaDAO = new CompagniaDAO();
            compagniaDAO.aggiungeNatante(compagnia.getLogin(), nome, capienzaPasseggeri, capienzaVeicoli, tipo);
            compagnia.addNatante(new Natante(compagnia, nome, capienzaPasseggeri, capienzaVeicoli, tipo));
            return true;
        } catch (Exception e) {
            logger.log(Level.FINE, e.getMessage());
            return false;
        }
    }

    /**
     * Visualizza i natanti della compagnia.
     *
     * @param nome               i nomi
     * @param capienzaPasseggeri le capienze di passeggeri
     * @param capienzaVeicoli    le capienze di veicoli
     * @param tipo               i tipi dei natanti
     */
    public void visualizzaNatanti(ArrayList<String> nome, ArrayList<Integer> capienzaPasseggeri, ArrayList<Integer> capienzaVeicoli, ArrayList<String> tipo) {
        for (Map.Entry<String, Natante> it : compagnia.getNatantiPosseduti().entrySet()) {
            Natante natante = it.getValue();
            nome.add(natante.getNome());
            capienzaPasseggeri.add(natante.getCapienzaPasseggeri());
            capienzaVeicoli.add(natante.getCapienzaVeicoli());
            tipo.add(natante.getTipo());
        }
    }

    /**
     * Visualizza i nomi dei natanti della compagnia.
     *
     * @param nome output - i nomi dei natanti della compagnia
     */
    public void visualizzaNomiNatanti(ArrayList<String> nome) {
        for (Map.Entry<String, Natante> it : compagnia.getNatantiPosseduti().entrySet()) {
            Natante natante = it.getValue();
            nome.add(natante.getNome());
        }
    }

    /**
     * Verifica se un natante e' un traghetto.
     *
     * @param nomeNatante il nome del natante
     * @return true se e' un traghetto, false altrimenti
     */
    public boolean isTraghetto(String nomeNatante) {
        boolean ret = false;

        if (compagnia.getNatantiPosseduti().get(nomeNatante).getTipo().equals("traghetto")) {
            ret = true;
        }

        return ret;
    }

    /**
     * Comanda al DB di rimuovere un natante della compagnia.
     *
     * @param nomeNatante il nome del natante da eliminare
     * @return true se la rimozione e' andata a buon fine, false altrimenti
     */
    public boolean rimuoviNatante(String nomeNatante) {
        try {
            CompagniaDAO compagniaDAO = new CompagniaDAO();
            compagniaDAO.rimuoveNatante(nomeNatante);
            compagnia.getNatantiPosseduti().remove(nomeNatante);
            return true;
        } catch (Exception e) {
            logger.log(Level.FINE, e.getMessage());
            return false;
        }
    }

    /**
     * Visualizza i porti.
     *
     * @param porti output - i porti
     */
    public void visualizzaPorti(ArrayList<Pair> porti) {
        for (Map.Entry<Integer, Porto> it : this.porti.entrySet()) {
            porti.add(new Pair(it.getKey(), it.getValue().getComune()));
        }
    }

    /**
     * Tenta di inserire una corsaRegolare nel DB.
     * Se vi riesce, aggiorna il parametro idCorsa, inserisce nel model
     * una nuova corsaRegolare, restituisce true.
     * Altrimenti, il parametro idCorsa resta pari a -1, viene restituito false
     * senza creare la corsaRegolare nel model.
     *
     * @param idPortoPartenza l'id del porto di partenza
     * @param idPortoArrivo   l'id del porto di arrivo
     * @param orarioPartenza  l'orario di partenza
     * @param orarioArrivo    l'orario di arrivo
     * @param costoIntero     il costo del biglietto intero
     * @param scontoRidotto   la percentuale di sconto per il biglietto ridotto
     * @param costoBagaglio   il costo aggiuntivo per il bagaglio
     * @param costoPrevendita il costo aggiuntivo per la prevendita
     * @param costoVeicolo    il costo aggiuntivo per il veicolo
     * @param nomeNatante     il nome del natante
     * @param idCorsa         output - l'id attribuito dal DB alla corsa
     * @return true se la corsa e' stata creata correttamente, false altrimenti
     */
    public boolean creaCorsa(int idPortoPartenza, int idPortoArrivo, LocalTime orarioPartenza, LocalTime orarioArrivo, float costoIntero, float scontoRidotto, float costoBagaglio, float costoPrevendita, float costoVeicolo, String nomeNatante, AtomicInteger idCorsa) {
        try {
            CompagniaDAO compagniaDAO = new CompagniaDAO();
            //Mi faccio restituire dal DAO l'id della tupla inserita.
            idCorsa.set(-1); //solo una inizializzazione...
            compagniaDAO.aggiungeCorsa(idPortoPartenza, idPortoArrivo, orarioPartenza, orarioArrivo, costoIntero, scontoRidotto, costoBagaglio, costoPrevendita, costoVeicolo, compagnia.getLogin(), nomeNatante, idCorsa);

            compagnia.getCorseErogate().clear();
            corseSpecifiche.clear();
            buildCorseRegolari(compagnia.getLogin());
            buildPeriodi(compagnia.getLogin());
            buildCorseSpecifiche();
            return true;
        } catch (SQLException e) {
            logger.log(Level.FINE, e.getMessage());
            return false;
        }
    }

    /**
     * aggiunge un periodo senza attaccarlo ad una corsa regolare.
     *
     * @param giorni        the giorni
     * @param inizioPeriodo the inizio periodo
     * @param finePeriodo   the fine periodo
     * @param idPeriodo     output - l'id attribuito dal DB al periodo inserito
     * @return true se il periodo e' stato inserito correttamente, false altrimenti
     */
    public boolean aggiungiPeriodo(String giorni, LocalDate inizioPeriodo, LocalDate finePeriodo, AtomicInteger idPeriodo) {
        try {
            CompagniaDAO compagniaDAO = new CompagniaDAO();
            idPeriodo.set(-1);
            compagniaDAO.aggiungePeriodo(giorni, inizioPeriodo, finePeriodo, idPeriodo);
            periodiNonCollegatiACorse.put(idPeriodo.get(), new Periodo(idPeriodo.get(), inizioPeriodo, finePeriodo, giorni));
            return true;
        } catch (SQLException e) {
            logger.log(Level.FINE, e.getMessage());
            return false;
        }
    }

    /**
     * Comanda al DB di attivare una corsa in un periodo. In caso di risposta positiva del DB,
     * la attiva anche nel Model.
     *
     * @param idCorsa   l'id della corsa da attivare
     * @param idPeriodo l'id del periodo
     * @return true se l'operazione e' avvenuta con successo, false altrimenti
     */
    public boolean attivaCorsaInPeriodo(int idCorsa, int idPeriodo) {
        try {
            CompagniaDAO compagniaDAO = new CompagniaDAO();
            compagniaDAO.attivaCorsaInPeriodo(idCorsa, idPeriodo);
            CorsaRegolare cr = compagnia.getCorseErogate().get(idCorsa);
            Periodo p = periodiNonCollegatiACorse.get(idPeriodo);
            cr.addPeriodoAttivita(p);
            //se il periodo non era collegato ad una corsa, adesso lo é.
            periodiNonCollegatiACorse.remove(idPeriodo);

            compagnia.getCorseErogate().clear();
            corseSpecifiche.clear();
            buildCorseRegolari(compagnia.getLogin());
            buildPeriodi(compagnia.getLogin());
            buildCorseSpecifiche();

            return true;
        } catch (SQLException e) {
            logger.log(Level.FINE, e.getMessage());
            return false;
        }
    }

    /**
     * Comanda al DB di eliminare una corsa regolare. Se la risposta del DB e' positiva,
     * procede ad eliminarla anche dal Model.
     *
     * @param idCorsa l'id della corsa da eliminare
     * @return true se la corsa e' stata eliminata, false altrimenti
     */
    public boolean eliminaCorsaRegolare(int idCorsa) {
        try {
            CompagniaDAO compagniaDAO = new CompagniaDAO();
            compagniaDAO.cancellaCorsaRegolare(idCorsa);
            //aggiorno le corse regolari e le corse specifiche
            compagnia.getCorseErogate().clear();
            corseSpecifiche.clear();
            buildCorseRegolari(compagnia.getLogin());
            buildPeriodi(compagnia.getLogin());
            buildCorseSpecifiche();
            return true;
        } catch (SQLException e) {
            logger.log(Level.FINE, e.getMessage());
            return false;
        }
    }

    /**
     * Comanda al DB di aggiungere uno scalo per una corsa. In caso di risposta positiva dal DB,
     * lo aggiunge anche nel Model.
     *
     * @param idCorsa          l'id della corsa
     * @param idPortoScalo     l'id del porto di scalo
     * @param orarioAttracco   l'orario di arrivo al porto di scalo
     * @param orarioRipartenza l'orario di ripartenza dal porto di scalo
     * @return true se lo scalo e' stato aggiunto correttamente, false altrimenti
     */
    public boolean aggiungiScalo(int idCorsa, Integer idPortoScalo, LocalTime orarioAttracco, LocalTime orarioRipartenza) {
        try {
            CompagniaDAO compagniaDAO = new CompagniaDAO();
            compagniaDAO.aggiungeScalo(idCorsa, idPortoScalo, orarioAttracco, orarioRipartenza);
            compagniaDAO = new CompagniaDAO();
            try {
                compagniaDAO.aggiornaPostiDisponibiliSottocorse(idCorsa);
            } catch (SQLException e) {
                logger.log(Level.FINE, e.getMessage());
                System.out.println("Aggiornamento dei posti disponibili fallito.");
            }

            compagnia.getCorseErogate().clear();
            corseSpecifiche.clear();
            buildCorseRegolari(compagnia.getLogin());
            buildPeriodi(compagnia.getLogin());
            buildCorseSpecifiche();

            return true;
        } catch (SQLException e) {
            logger.log(Level.FINE, e.getMessage());
            return false;
        }
    }

    /**
     * Visualizza le corse specifiche in una data fissata.
     *
     * @param data           la data
     * @param idCorsa        output - gli id delle corse
     * @param portoPartenza  output - i porti di partenza
     * @param orarioPartenza output - gli orari di partenza
     * @param portoArrivo    output - i porti di arrivo
     * @param orarioArrivo   output - gli orari di arrivo
     * @param minutiRitardo  output - i minuti di ritardo
     * @param postiDispPass  output - i posti disponibili per passeggeri
     * @param postiDispVei   output - i posti disponibili per veicoli
     * @param cancellata     output - valori booleani che indicano se le corse sono state eliminate o meno
     */
    public void visualizzaCorseSpecifichePerData(LocalDate data, ArrayList<Integer> idCorsa, ArrayList<String> portoPartenza, ArrayList<LocalTime> orarioPartenza, ArrayList<String> portoArrivo, ArrayList<LocalTime> orarioArrivo, ArrayList<Integer> minutiRitardo, ArrayList<Integer> postiDispPass, ArrayList<Integer> postiDispVei, ArrayList<Boolean> cancellata) {
        for (Map.Entry<Pair, CorsaSpecifica> it : corseSpecifiche.entrySet()) {
            if (it.getValue().getData().equals(data)) {
                CorsaSpecifica cs = it.getValue();
                idCorsa.add(cs.getCorsaRegolare().getIdCorsa());
                portoPartenza.add(cs.getCorsaRegolare().getPortoPartenza().getComune());
                orarioPartenza.add(cs.getCorsaRegolare().getOrarioPartenza());
                portoArrivo.add(cs.getCorsaRegolare().getPortoArrivo().getComune());
                orarioArrivo.add(cs.getCorsaRegolare().getOrarioArrivo());
                minutiRitardo.add(cs.getMinutiRitardo());
                postiDispPass.add(cs.getPostiDispPass());
                postiDispVei.add(cs.getPostiDispVei());
                cancellata.add(cs.isCancellata());
            }
        }
    }

    /**
     * Visualizza le corse regolari.
     *
     * @param idCorsa        output - gli id delle corse
     * @param portoPartenza  output - i porti di partenza delle corse
     * @param portoArrivo    output - i porti di arrivo delle corse
     * @param natante        output - i natanti delle corse
     * @param orarioPartenza output - gli orari di partenza delle corse
     * @param orarioArrivo   output - gli orari di arrivo delle corse
     */
    public void visualizzaCorseRegolari(ArrayList<Integer> idCorsa, ArrayList<String> portoPartenza, ArrayList<String> portoArrivo, ArrayList<String> natante, ArrayList<LocalTime> orarioPartenza, ArrayList<LocalTime> orarioArrivo) {
        HashMap<Integer, CorsaRegolare> cr = compagnia.getCorseErogate();
        for(Map.Entry<Integer, CorsaRegolare> it : cr.entrySet()) {
            idCorsa.add(it.getValue().getIdCorsa());
            portoPartenza.add(it.getValue().getPortoPartenza().getComune());
            portoArrivo.add(it.getValue().getPortoArrivo().getComune());
            natante.add(it.getValue().getNatante().getNome());
            orarioPartenza.add(it.getValue().getOrarioPartenza());
            orarioArrivo.add(it.getValue().getOrarioArrivo());
        }
    }

    /**
     * Comanda al DB di cancellare una corsa specifica. In caso di risposta positiva del DB,
     * procede a cancellarla anche nel Model.
     *
     * @param idCorsa l'id della corsa da cancellare
     * @param data    la data della corsa da cancellare
     * @return true se la corsa viene cancellata, false altrimenti
     */
    public boolean cancellaCorsaSpecifica(int idCorsa, LocalDate data) {
        try {
            CompagniaDAO compagniaDAO = new CompagniaDAO();
            compagniaDAO.cancellaCorsaSpecifica(idCorsa, Date.valueOf(data));
            buildCorseSpecifiche();
            return true;
        } catch (SQLException e) {
            logger.log(Level.FINE, e.getMessage());
            return false;
        }
    }

    /**
     * Comanda al DB di segnalare un ritardo per la corsa. In caso di riposta positiva del DB,
     * segnala il ritardo anche nel Model.
     *
     * @param idCorsa       l'id della corsa per cui segnalare il ritardo
     * @param data          la data della corsa
     * @param minutiRitardo i minuti di ritardo da segnalare
     * @return true se la segnalazione e' avvenuta con successo, false altrimenti
     */
    public boolean segnalaRitardo(int idCorsa, LocalDate data, int minutiRitardo) {
        try {
            CompagniaDAO compagniaDAO = new CompagniaDAO();
            compagniaDAO.segnalaRitardo(idCorsa, Date.valueOf(data), minutiRitardo);
            CorsaSpecifica cs = corseSpecifiche.get(new Pair(idCorsa, data));
            cs.setMinutiRitardo(minutiRitardo);
            return true;
        } catch (SQLException e) {
            logger.log(Level.FINE, e.getMessage());
            return false;
        }
    }

    /**
     * Restituisce il login della compagnia
     *
     * @return il login della compagnia
     */
    public String getLoginCompagnia() {
        return compagnia.getLogin();
    }

    /**
     * Verifica se una corsa regolare e' sottocorsa di un'altra corsa regolare.
     *
     * @param idCorsa l'id della corsa
     * @return true se la corsa e' una sottocorsa, false altrimenti
     */
    public boolean isSottoCorsa(Integer idCorsa) {
        CorsaRegolare cr = compagnia.getCorseErogate().get(idCorsa);
        return cr.getCorsaSup() != null;
    }

    /**
     * Verifica se una corsa regolare ha delle sottocorse.
     *
     * @param idCorsa l'id della corsa
     * @return true se la corsa ha delle sottocorse, false altrimenti
     */
    public boolean haveSottoCorse(Integer idCorsa) {
        for(Map.Entry<Integer, CorsaRegolare> it : compagnia.getCorseErogate().entrySet()) {
            if (it.getValue().getCorsaSup() != null) {
                if (it.getValue().getCorsaSup().getIdCorsa() == idCorsa.intValue()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Restituisce la corsa regolare di cui la corsa in input e' sottocorsa
     *
     * @param idCorsa l'id della corsa
     * @return l'id della corsa superiore
     */
    public Integer getCorsaSup(Integer idCorsa) {
        if (isSottoCorsa(idCorsa)) {
            return getCorsaSup(compagnia.getCorseErogate().get(idCorsa).getCorsaSup().getIdCorsa());
        } else {
            return idCorsa;
        }
    }

    /**
     * Visualizza info corsa.
     *
     * @param idCorsa         l'id della corsa
     * @param portoPartenza   il porto di partenza
     * @param portoArrivo     il porto di arrivo
     * @param natante         il natante
     * @param orarioPartenza  l'orario di partenza
     * @param orarioArrivo    l'orario di arrivo
     * @param costoIntero     il costo del biglietto intero
     * @param scontoRidotto   lo sconto percentuale del biglietto ridotto
     * @param costoBagaglio   il costo aggiuntivo per il bagaglio
     * @param costoPrevendita il costo aggiuntivo per la prevendita
     * @param costoVeicolo    il costo aggiuntivo per il veicolo
     */
    public void visualizzaInfoCorsa(int idCorsa, ArrayList<String> portoPartenza, ArrayList<String> portoArrivo, ArrayList<String> natante, ArrayList<LocalTime> orarioPartenza, ArrayList<LocalTime> orarioArrivo, ArrayList<Float> costoIntero, ArrayList<Float> scontoRidotto, ArrayList<Float> costoBagaglio, ArrayList<Float> costoPrevendita, ArrayList<Float> costoVeicolo) {
        CorsaRegolare cr = compagnia.getCorseErogate().get(idCorsa);

        portoPartenza.addFirst(cr.getPortoPartenza().getComune());
        portoArrivo.addFirst(cr.getPortoArrivo().getComune());
        natante.addFirst(cr.getNatante().getNome());
        orarioPartenza.addFirst(cr.getOrarioPartenza());
        orarioArrivo.addFirst(cr.getOrarioArrivo());
        costoIntero.addFirst(cr.getCostoIntero());
        scontoRidotto.addFirst(cr.getScontoRidotto());
        costoBagaglio.addFirst(cr.getCostoBagaglio());
        costoPrevendita.addFirst(cr.getCostoPrevendita());
        costoVeicolo.addFirst(cr.getCostoVeicolo());
    }

    /**
     * Visualizza info corsa.
     *
     * @param idCorsa         l'id della corsa
     * @param portoPartenza   il porto di partenza
     * @param portoArrivo     il porto di arrivo
     * @param natante         il natante
     * @param orarioPartenza  l'orario di partenza
     * @param orarioArrivo    l'orario di arrivo
     * @param costoIntero     il costo del biglietto intero
     * @param scontoRidotto   lo sconto percentuale del biglietto ridotto
     * @param costoBagaglio   il costo aggiuntivo per il bagaglio
     * @param costoPrevendita il costo aggiuntivo per la prevendita
     * @param costoVeicolo    il costo aggiuntivo per il veicolo
     * @param listaIdPeriodo  la lista degli id dei periodi
     * @param inizioPer       la lista delle date di inizio dei periodi
     * @param finePer         la lista delle date di fine dei periodi
     * @param giorniAttivi    la lista dei giorni di attivita' dei periodi
     * @param idSottoCorse    la lista degli id delle sotto corse
     */
    public void visualizzaInfoCorsa(int idCorsa, ArrayList<String> portoPartenza, ArrayList<String> portoArrivo, ArrayList<String> natante, ArrayList<LocalTime> orarioPartenza, ArrayList<LocalTime> orarioArrivo, ArrayList<Float> costoIntero, ArrayList<Float> scontoRidotto, ArrayList<Float> costoBagaglio, ArrayList<Float> costoPrevendita, ArrayList<Float> costoVeicolo, ArrayList<Integer> listaIdPeriodo, ArrayList<LocalDate> inizioPer, ArrayList<LocalDate> finePer, ArrayList<String> giorniAttivi, ArrayList<Integer> idSottoCorse) {
        CorsaRegolare cr = compagnia.getCorseErogate().get(idCorsa);
        for (Map.Entry<Integer, Periodo> p : compagnia.getCorseErogate().get(idCorsa).getPeriodiAttivita().entrySet()) {
            listaIdPeriodo.add(p.getKey());
            inizioPer.add(p.getValue().getDataInizio());
            finePer.add(p.getValue().getDataFine());
            giorniAttivi.add(p.getValue().getGiorni());
        }
        visualizzaInfoCorsa(idCorsa, portoPartenza, portoArrivo, natante, orarioPartenza, orarioArrivo, costoIntero, scontoRidotto, costoBagaglio, costoPrevendita, costoVeicolo);

        for (Map.Entry<Integer, CorsaRegolare> it : compagnia.getCorseErogate().entrySet()) {
            if (it.getValue().getCorsaSup() != null) {
                if (it.getValue().getCorsaSup().getIdCorsa() == cr.getIdCorsa()) {
                    idSottoCorse.add(it.getValue().getIdCorsa());
                }
            }
        }
    }

    /**
     * Visualizza corse per natante.
     *
     * @param nomeNatante   il nome del natante
     * @param idCorsa       gli id delle corse trovate
     * @param portoPartenza i porti di partenza delle corse trovate
     * @param oraPartenza   gli orari di partenza delle corse trovate
     * @param portoArrivo   i porti di arrivo delle corse trovate
     * @param oraArrivo     gli orari di arrivo delle corse trovate
     */
    public void visualizzaCorsePerNatante(String nomeNatante, ArrayList<Integer> idCorsa, ArrayList<String> portoPartenza, ArrayList<LocalTime> oraPartenza, ArrayList<String> portoArrivo, ArrayList<LocalTime> oraArrivo) {
        Natante nt = compagnia.getNatantiPosseduti().get(nomeNatante);

        for (Map.Entry<Integer, CorsaRegolare> it : compagnia.getCorseErogate().entrySet()) {
            CorsaRegolare cr = it.getValue();
            if (cr.getNatante().getNome().equals(nt.getNome()) && cr.getCorsaSup() == null) {
                idCorsa.add(cr.getIdCorsa());
                portoPartenza.add(cr.getPortoPartenza().getComune());
                oraPartenza.add(cr.getOrarioPartenza());
                portoArrivo.add(cr.getPortoArrivo().getComune());
                oraArrivo.add(cr.getOrarioArrivo());
            }
        }
    }

    /**
     * Comanda al DB di modificare l'orario di partenza di una corsa. In caso di risposta
     * positiva del DB, procede a modificarlo anche nel Model.
     *
     * @param idCorsa             l'id della corsa da modificare
     * @param nuovoOrarioPartenza il nuovo orario di partenza
     * @return true se la modifica e' avvenuta con successo, false altrimenti
     */
    public boolean modificaOrarioPartenza(int idCorsa, LocalTime nuovoOrarioPartenza) {
        try {
            CompagniaDAO compagniaDAO = new CompagniaDAO();
            compagniaDAO.modificaOrarioPartenza(idCorsa, nuovoOrarioPartenza);
            buildCorseRegolari(compagnia.getLogin());
            buildPeriodi(compagnia.getLogin());
            return true;
        } catch (SQLException e) {
            logger.log(Level.FINE, e.getMessage());
            return false;
        }
    }

    /**
     * Comanda al DB di modificare l'orario di arrivo di una corsa. In caso di risposta
     * positiva del DB, procede a modificarlo anche nel Model.
     *
     * @param idCorsa             l'id della corsa da modificare
     * @param nuovoOrarioArrivo il nuovo orario di arrivo
     * @return true se la modifica e' avvenuta con successo, false altrimenti
     */
    public boolean modificaOrarioArrivo(int idCorsa, LocalTime nuovoOrarioArrivo) {
        try {
            CompagniaDAO compagniaDAO = new CompagniaDAO();
            compagniaDAO.modificaOrarioArrivo(idCorsa, nuovoOrarioArrivo);
            buildCorseRegolari(compagnia.getLogin());
            buildPeriodi(compagnia.getLogin());
            return true;
        } catch (SQLException e) {
            logger.log(Level.FINE, e.getMessage());
            return false;
        }
    }

    /**
     * Comanda al DB di modificare il costo del biglietto intero di una corsa. In caso di risposta
     * positiva del DB, procede a modificarlo anche nel Model.
     *
     * @param idCorsa             l'id della corsa da modificare
     * @param nuovoCostoIntero il nuovo costo del biglietto intero
     * @return true se la modifica e' avvenuta con successo, false altrimenti
     */
    public boolean modificaCostoIntero(int idCorsa, float nuovoCostoIntero) {
        try {
            CompagniaDAO compagniaDAO = new CompagniaDAO();
            compagniaDAO.modificaCostoIntero(idCorsa, nuovoCostoIntero);
            compagnia.getCorseErogate().get(idCorsa).setCostoIntero(nuovoCostoIntero);
            return true;
        } catch (SQLException e) {
            logger.log(Level.FINE, e.getMessage());
            return false;
        }
    }

    /**
     * Comanda al DB di modificare l'orario di partenza di una corsa. In caso di risposta
     * positiva del DB, procede a modificarlo anche nel Model.
     *
     * @param idCorsa             l'id della corsa da modificare
     * @param nuovoScontoRidotto la nuova percentuale di sconto per il biglietto ridotto
     * @return true se la modifica e' avvenuta con successo, false altrimenti
     */
    public boolean modificaScontoRidotto(int idCorsa, float nuovoScontoRidotto) {
        try {
            CompagniaDAO compagniaDAO = new CompagniaDAO();
            compagniaDAO.modificaScontoRidotto(idCorsa, nuovoScontoRidotto);
            compagnia.getCorseErogate().get(idCorsa).setScontoRidotto(nuovoScontoRidotto);
            return true;
        } catch (SQLException e) {
            logger.log(Level.FINE, e.getMessage());
            return false;
        }
    }

    /**
     * Comanda al DB di modificare il costo aggiuntivo per il bagaglio di una corsa. In caso di risposta
     * positiva del DB, procede a modificarlo anche nel Model.
     *
     * @param idCorsa             l'id della corsa da modificare
     * @param nuovoCostoBagaglio il nuovo costo aggiuntivo per il bagaglio
     * @return true se la modifica e' avvenuta con successo, false altrimenti
     */
    public boolean modificaCostoBagaglio(int idCorsa, float nuovoCostoBagaglio) {
        try {
            CompagniaDAO compagniaDAO = new CompagniaDAO();
            compagniaDAO.modificaCostoBagaglio(idCorsa, nuovoCostoBagaglio);
            compagnia.getCorseErogate().get(idCorsa).setCostoBagaglio(nuovoCostoBagaglio);
            return true;
        } catch (SQLException e) {
            logger.log(Level.FINE, e.getMessage());
            return false;
        }
    }

    /**
     * Comanda al DB di modificare il costo aggiuntivo per la prevendita di una corsa. In caso di risposta
     * positiva del DB, procede a modificarlo anche nel Model.
     *
     * @param idCorsa             l'id della corsa da modificare
     * @param nuovoCostoPrevendita il nuovo costo aggiuntivo per la prevendita
     * @return true se la modifica e' avvenuta con successo, false altrimenti
     */
    public boolean modificaCostoPrevendita(int idCorsa, float nuovoCostoPrevendita) {
        try {
            CompagniaDAO compagniaDAO = new CompagniaDAO();
            compagniaDAO.modificaCostoPrevendita(idCorsa, nuovoCostoPrevendita);
            compagnia.getCorseErogate().get(idCorsa).setCostoPrevendita(nuovoCostoPrevendita);
            return true;
        } catch (SQLException e) {
            logger.log(Level.FINE, e.getMessage());
            return false;
        }
    }

    /**
     * Comanda al DB di modificare il costo aggiuntivo per il veicolo di una corsa. In caso di risposta
     * positiva del DB, procede a modificarlo anche nel Model.
     *
     * @param idCorsa             l'id della corsa da modificare
     * @param nuovoCostoVeicolo il nuovo costo aggiuntivo per un veicolo
     * @return true se la modifica e' avvenuta con successo, false altrimenti
     */
    public boolean modificaCostoVeicolo(int idCorsa, float nuovoCostoVeicolo) {
        try {
            CompagniaDAO compagniaDAO = new CompagniaDAO();
            compagniaDAO.modificaCostoVeicolo(idCorsa, nuovoCostoVeicolo);
            compagnia.getCorseErogate().get(idCorsa).setCostoVeicolo(nuovoCostoVeicolo);
            return true;
        } catch (SQLException e) {
            logger.log(Level.FINE, e.getMessage());
            return false;
        }
    }


    /**
     * Comanda al DB di eliminare un periodo di attivita per una corsa. In caso
     * di riposta positiva, procede ad eliminarlo anche dal Model.
     *
     * @param idCorsa   l'id della corsa
     * @param idPeriodo l'id del periodo
     * @return true se l'eliminazione e' avvenuta con successo, false altrimenti
     */
    public boolean eliminaPeriodoAttivitaPerCorsa(int idCorsa, int idPeriodo) {
        try {
            CompagniaDAO compagniaDAO = new CompagniaDAO();
            compagniaDAO.eliminaPeriodoAttivitaPerCorsa(idCorsa, idPeriodo);
            buildCorseRegolari(compagnia.getLogin());
            buildPeriodi(compagnia.getLogin());
            return true;
        } catch (SQLException e) {
            logger.log(Level.FINE, e.getMessage());
            return false;
        }
    }

    /**
     * Chiede al DB di calcolare l'incasso di una corsa regolare in un periodo dato.
     * Se il DB fallisce, la funzione restituisce il valore -1.
     * Altrimenti, restituisce l'incasso della corsa nel periodo selezionato.
     *
     * @param idCorsa       l'id della corsa
     * @param inizioPeriodo l'inizio del periodo
     * @param finePeriodo   la fine del periodo
     * @return l'incasso
     */
    public float calcolaIncassiCorsaInPeriodo(int idCorsa, LocalDate inizioPeriodo, LocalDate finePeriodo) {
        try {
            CompagniaDAO compagniaDAO = new CompagniaDAO();
            return compagniaDAO.calcolaIncassiCorsaInPeriodo(idCorsa, inizioPeriodo, finePeriodo);
        } catch (SQLException e) {
            logger.log(Level.FINE, e.getMessage());
            return -1;
        }
    }

    /**
     * Visualizza i contatti della compagnia.
     *
     * @param nomeSocial i nomi dei social
     * @param tag        i tag nei social
     * @param email      gli indirizzi email
     * @param telefono   i recapiti telefonici
     * @param sitoWeb    il sito web
     */
    public void visualizzaContatti(ArrayList<String> nomeSocial, ArrayList<String> tag, ArrayList<String> email, ArrayList<String> telefono, ArrayList<String> sitoWeb) {
        for (AccountSocial x : compagnia.getAccounts()) {
            nomeSocial.add(x.getNomeSocial());
            tag.add(x.getTag());
        }

        email.addAll(compagnia.getEmails());
        telefono.addAll(compagnia.getTelefoni());
        sitoWeb.addFirst(compagnia.getSitoWeb());
    }

    /**
     * Comanda al DB di aggiungere un profilo social per la compagnia.
     * In caso di risposta positiva, procede ad aggiungerlo anche nel Model.
     *
     * @param nomeSocial il nome del social
     * @param tag        il tag nel social
     * @return true se l'aggiunta e' stata eseguita con successo, false altrimenti
     */
    public boolean aggiungiSocial(String nomeSocial, String tag) {
        try {
            CompagniaDAO compagniaDAO = new CompagniaDAO();
            compagniaDAO.aggiungiSocial(nomeSocial, tag, compagnia.getLogin());
            compagnia.getAccounts().add(new AccountSocial(compagnia, nomeSocial, tag));

            return true;
        } catch (SQLException e) {
            logger.log(Level.FINE, e.getMessage());
            return false;
        }
    }

    /**
     * Comanda al DB di rimuovere un profilo social della compagnia.
     * In caso di risposta positiva, procede a rimuoverlo anche dal Model.
     *
     * @param nomeSocial il nome del social
     * @param tag        il tag nel social
     * @return true se la rimozione e' stata eseguita con successo, false altrimenti
     */
    public boolean eliminaSocial(String nomeSocial, String tag) {
        try {
            CompagniaDAO compagniaDAO = new CompagniaDAO();
            compagniaDAO.eliminaSocial(nomeSocial, tag);

            for (int i = 0; i < compagnia.getAccounts().size(); i++) {
                if (compagnia.getAccounts().get(i).getNomeSocial().equals(nomeSocial) && compagnia.getAccounts().get(i).getTag().equals(tag)) {
                    compagnia.getAccounts().remove(i);
                }
            }

            return true;
        } catch (SQLException e) {
            logger.log(Level.FINE, e.getMessage());
            return false;
        }
    }

    /**
     * Comanda al DB di aggiungere un recapito telefonico per la compagnia.
     * In caso di risposta positiva, procede ad aggiungerlo anche nel Model.
     *
     * @param telefono il recapito telefonico da aggiungere
     * @return true se l'aggiunta e' stata eseguita con successo, false altrimenti
     */
    public boolean aggiungiTelefono(String telefono) {
        try {
            CompagniaDAO compagniaDAO = new CompagniaDAO();
            compagniaDAO.aggiungiTelefono(telefono, compagnia.getLogin());

            compagnia.getTelefoni().add(telefono);

            return true;
        } catch (SQLException e) {
            logger.log(Level.FINE, e.getMessage());
            return false;
        }
    }

    /**
     * Comanda al DB di rimuovere un recapito telefonico per la compagnia.
     * In caso di risposta positiva, procede a rimuoverlo anche dal Model.
     *
     * @param telefono il recapito telefonico da rimuovere
     * @return true se la rimozione e' stata eseguita con successo, false altrimenti
     */
    public boolean eliminaTelefono(String telefono) {
        try {
            CompagniaDAO compagniaDAO = new CompagniaDAO();
            compagniaDAO.eliminaTelefono(telefono);

            compagnia.getTelefoni().remove(telefono);

            return true;
        } catch (SQLException e) {
            logger.log(Level.FINE, e.getMessage());
            return false;
        }
    }

    /**
     * Comanda al DB di aggiungere un indirizzo email per la compagnia.
     * In caso di risposta positiva, procede ad aggiungerlo anche nel Model.
     *
     * @param email l'indirizzo email da aggiungere
     * @return true se l'aggiunta e' stata eseguita con successo, false altrimenti
     */
    public boolean aggiungiEmail(String email) {
        try {
            CompagniaDAO compagniaDAO = new CompagniaDAO();
            compagniaDAO.aggiungiEmail(email, compagnia.getLogin());

            compagnia.getEmails().add(email);

            return true;
        } catch (SQLException e) {
            logger.log(Level.FINE, e.getMessage());
            return false;
        }
    }

    /**
     * Comanda al DB di rimuovere un indirizzo email della compagnia.
     * In caso di risposta positiva, procede a rimuoverlo anche dal Model.
     *
     * @param email l'indirizzo email da rimuovere
     * @return true se la rimozione e' stata eseguita con successo, false altrimenti
     */
    public boolean eliminaEmail(String email) {
        try {
            CompagniaDAO compagniaDAO = new CompagniaDAO();
            compagniaDAO.eliminaEmail(email);

            compagnia.getEmails().remove(email);

            return true;
        } catch (SQLException e) {
            logger.log(Level.FINE, e.getMessage());
            return false;
        }
    }

    /**
     * Comanda al DB di modificare il sito web della compagnia.
     * In caso di risposta positiva, procede a modificarlo anche nel Model.
     *
     * @param sito il nuovo sito web
     * @return true se la modifica e' stata eseguita con successo, false altrimenti
     */
    public boolean modificaSitoWeb(String sito) {
        try {
            CompagniaDAO compagniaDAO = new CompagniaDAO();
            compagniaDAO.modificaSitoWeb(sito, compagnia.getLogin());

            compagnia.setSitoWeb(sito);

            return  true;
        } catch (SQLException e) {
            logger.log(Level.FINE, e.getMessage());
            return false;
        }
    }
}
