package controller;

import model.*;
import postgresqlDAO.ClienteDAO;
import postgresqlDAO.CompagniaDAO;
import unnamed.Pair;

import java.net.Inet4Address;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static unnamed.NonStandardConversions.StringToBitset;

/**
 * The type Controller compagnia.
 */
public class ControllerCompagnia {
    private Compagnia compagnia;
    private HashMap<Integer, Porto> porti;
    private HashMap<Pair, CorsaSpecifica> corseSpecifiche;
    private HashMap<Integer, Periodo> periodiNonCollegatiACorse;

    /**
     * Instantiates a new Controller compagnia.
     */
    public ControllerCompagnia() {
        porti = new HashMap<>();
        corseSpecifiche = new HashMap<>();
        periodiNonCollegatiACorse = new HashMap<>();
    }

    /**
     * Compagnia accede boolean.
     *
     * @param login    the login
     * @param password the password
     * @return the boolean
     */
    public void compagniaAccede(String login, String password) throws SQLException {
        try {
            CompagniaDAO compagniaDAO = new CompagniaDAO();
            compagniaDAO.accede(login, password);

            //prende dal DB tutte i dati della compagnia e costruisce l'ambiente per l'uso dell'applicativo
            buildModel(login, password);
        } catch(SQLException e) {
            String message = e.getMessage();
            if (message.equals("Impossibile connettersi al server.")) {
                throw e;
            } else if (message.equals("Credenziali errate / compagnia non esistente.")) {
                throw e;
            }
        }
    }

    /**
     * Build model.
     *
     * @param login    the login
     * @param password the password
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
            throw e;
        }
    }

    /**
     * Build porti.
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
            throw e;
        }
    }

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
            throw e;
        }
    }

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
            throw e;
        }
    }

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
            throw e;
        }
    }

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
            throw e;
        }

    }

    /**
     * Build corse specifiche.
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
            throw e;
        }
    }

    public boolean aggiungiNatante (String nome, String tipo, int capienzaPasseggeri, int capienzaVeicoli) {
        try {
            CompagniaDAO compagniaDAO = new CompagniaDAO();
            compagniaDAO.aggiungeNatante(compagnia.getLogin(), nome, capienzaPasseggeri, capienzaVeicoli, tipo);
            compagnia.addNatante(new Natante(compagnia, nome, capienzaPasseggeri, capienzaVeicoli, tipo));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void visualizzaNatanti(ArrayList<String> nome, ArrayList<Integer> capienzaPasseggeri, ArrayList<Integer> capienzaVeicoli, ArrayList<String> tipo) {
        for (Map.Entry<String, Natante> it : compagnia.getNatantiPosseduti().entrySet()) {
            Natante natante = it.getValue();
            nome.add(natante.getNome());
            capienzaPasseggeri.add(natante.getCapienzaPasseggeri());
            capienzaVeicoli.add(natante.getCapienzaVeicoli());
            tipo.add(natante.getTipo());
        }
    }

    public void visualizzaNatanti(ArrayList<String> nome) {
        for (Map.Entry<String, Natante> it : compagnia.getNatantiPosseduti().entrySet()) {
            Natante natante = it.getValue();
            nome.add(natante.getNome());
        }
    }

    public boolean isTraghetto(String nomeNatante) {
        boolean ret = false;

        if (compagnia.getNatantiPosseduti().get(nomeNatante).getTipo().equals("traghetto")) {
            ret = true;
        }

        return ret;
    }

    public boolean rimuoviNatante(String nomeNatante) {
        try {
            CompagniaDAO compagniaDAO = new CompagniaDAO();
            compagniaDAO.rimuoveNatante(nomeNatante);
            compagnia.getNatantiPosseduti().remove(nomeNatante);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

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
     * @return
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
            e.printStackTrace();
            return false;
        }
    }

    /**
     * aggiunge un periodo senza attaccarlo ad una corsa regolare.
     *
     * @param giorni
     * @param inizioPeriodo
     * @param finePeriodo
     * @param idPeriodo - output parameter
     * @return a boolean
     */
    public boolean aggiungiPeriodo(String giorni, LocalDate inizioPeriodo, LocalDate finePeriodo, AtomicInteger idPeriodo) {
        try {
            CompagniaDAO compagniaDAO = new CompagniaDAO();
            idPeriodo.set(-1);
            compagniaDAO.aggiungePeriodo(giorni, inizioPeriodo, finePeriodo, idPeriodo);
            periodiNonCollegatiACorse.put(idPeriodo.get(), new Periodo(idPeriodo.get(), inizioPeriodo, finePeriodo, giorni));
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

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
            return false;
        }
    }

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
            return false;
        }
    }

    public boolean aggiungiScalo(int idCorsa, Integer idPortoScalo, LocalTime orarioAttracco, LocalTime orarioRipartenza) {
        try {
            CompagniaDAO compagniaDAO = new CompagniaDAO();
            compagniaDAO.aggiungeScalo(idCorsa, idPortoScalo, orarioAttracco, orarioRipartenza);
            compagniaDAO = new CompagniaDAO();
            try {
                compagniaDAO.aggiornaPostiDisponibiliSottocorse(idCorsa);
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Aggiornamento dei posti disponibili fallito.");
            }

            compagnia.getCorseErogate().clear();
            corseSpecifiche.clear();
            buildCorseRegolari(compagnia.getLogin());
            buildPeriodi(compagnia.getLogin());
            buildCorseSpecifiche();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

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

    public boolean cancellaCorsaSpecifica(int idCorsa, LocalDate data) {
        try {
            CompagniaDAO compagniaDAO = new CompagniaDAO();
            compagniaDAO.cancellaCorsaSpecifica(idCorsa, Date.valueOf(data));
            buildCorseSpecifiche();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean segnalaRitardo(int idCorsa, LocalDate data, int minutiRitardo) {
        try {
            CompagniaDAO compagniaDAO = new CompagniaDAO();
            compagniaDAO.segnalaRitardo(idCorsa, Date.valueOf(data), minutiRitardo);
            CorsaSpecifica cs = corseSpecifiche.get(new Pair(idCorsa, data));
            cs.setMinutiRitardo(minutiRitardo);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public String getLoginCompagnia() {
        return compagnia.getLogin();
    }

    public boolean isSottoCorsa(Integer idCorsa) {
        CorsaRegolare cr = compagnia.getCorseErogate().get(idCorsa);
        return cr.getCorsaSup() != null;
    }

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

    public Integer getCorsaSup(Integer idCorsa) {
        if (isSottoCorsa(idCorsa)) {
            return getCorsaSup(compagnia.getCorseErogate().get(idCorsa).getCorsaSup().getIdCorsa());
        } else {
            return idCorsa;
        }
    }

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

    public boolean modificaOrarioPartenza(int idCorsa, LocalTime nuovoOrarioPartenza) {
        try {
            CompagniaDAO compagniaDAO = new CompagniaDAO();
            compagniaDAO.modificaOrarioPartenza(idCorsa, nuovoOrarioPartenza);
            buildCorseRegolari(compagnia.getLogin());
            buildPeriodi(compagnia.getLogin());
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean modificaOrarioArrivo(int idCorsa, LocalTime nuovoOrarioArrivo) {
        try {
            CompagniaDAO compagniaDAO = new CompagniaDAO();
            compagniaDAO.modificaOrarioArrivo(idCorsa, nuovoOrarioArrivo);
            buildCorseRegolari(compagnia.getLogin());
            buildPeriodi(compagnia.getLogin());
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean modificaCostoIntero(int idCorsa, float nuovoCostoIntero) {
        try {
            CompagniaDAO compagniaDAO = new CompagniaDAO();
            compagniaDAO.modificaCostoIntero(idCorsa, nuovoCostoIntero);
            compagnia.getCorseErogate().get(idCorsa).setCostoIntero(nuovoCostoIntero);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean modificaScontoRidotto(int idCorsa, float nuovoScontoRidotto) {
        try {
            CompagniaDAO compagniaDAO = new CompagniaDAO();
            compagniaDAO.modificaScontoRidotto(idCorsa, nuovoScontoRidotto);
            compagnia.getCorseErogate().get(idCorsa).setScontoRidotto(nuovoScontoRidotto);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean modificaCostoBagaglio(int idCorsa, float nuovoCostoBagaglio) {
        try {
            CompagniaDAO compagniaDAO = new CompagniaDAO();
            compagniaDAO.modificaCostoBagaglio(idCorsa, nuovoCostoBagaglio);
            compagnia.getCorseErogate().get(idCorsa).setCostoBagaglio(nuovoCostoBagaglio);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean modificaCostoPrevendita(int idCorsa, float nuovoCostoPrevendita) {
        try {
            CompagniaDAO compagniaDAO = new CompagniaDAO();
            compagniaDAO.modificaCostoPrevendita(idCorsa, nuovoCostoPrevendita);
            compagnia.getCorseErogate().get(idCorsa).setCostoPrevendita(nuovoCostoPrevendita);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean modificaCostoVeicolo(int idCorsa, float nuovoCostoVeicolo) {
        try {
            CompagniaDAO compagniaDAO = new CompagniaDAO();
            compagniaDAO.modificaCostoVeicolo(idCorsa, nuovoCostoVeicolo);
            compagnia.getCorseErogate().get(idCorsa).setCostoVeicolo(nuovoCostoVeicolo);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }


    public boolean eliminaPeriodoAttivitaPerCorsa(int idCorsa, int idPeriodo) {
        try {
            CompagniaDAO compagniaDAO = new CompagniaDAO();
            compagniaDAO.eliminaPeriodoAttivitaPerCorsa(idCorsa, idPeriodo);
            buildCorseRegolari(compagnia.getLogin());
            buildPeriodi(compagnia.getLogin());
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Chiede al DB di calcolare l'incasso di una corsa regolare in un periodo dato.
     * Se il DB fallisce, la funzione restituisce il valore -1.
     * Altrimenti, restituisce l'incasso della corsa nel periodo selezionato.
     *
     * @param idCorsa
     * @param inizioPeriodo
     * @param finePeriodo
     * @return
     */
    public float calcolaIncassiCorsaInPeriodo(int idCorsa, LocalDate inizioPeriodo, LocalDate finePeriodo) {
        try {
            CompagniaDAO compagniaDAO = new CompagniaDAO();
            return compagniaDAO.calcolaIncassiCorsaInPeriodo(idCorsa, inizioPeriodo, finePeriodo);
        } catch (SQLException e) {
            return -1;
        }
    }

    public void visualizzaContatti(ArrayList<String> nomeSocial, ArrayList<String> tag, ArrayList<String> email, ArrayList<String> telefono, ArrayList<String> sitoWeb) {
        for (AccountSocial x : compagnia.getAccounts()) {
            nomeSocial.add(x.getNomeSocial());
            tag.add(x.getTag());
        }

        email.addAll(compagnia.getEmails());
        telefono.addAll(compagnia.getTelefoni());
        sitoWeb.addFirst(compagnia.getSitoWeb());
    }

    public boolean aggiungiSocial(String nomeSocial, String tag) {
        try {
            CompagniaDAO compagniaDAO = new CompagniaDAO();
            compagniaDAO.aggiungiSocial(nomeSocial, tag, compagnia.getLogin());
            compagnia.getAccounts().add(new AccountSocial(compagnia, nomeSocial, tag));

            return true;
        } catch (SQLException e) {
            return false;
        }
    }

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
            return false;
        }
    }

    public boolean aggiungiTelefono(String telefono) {
        try {
            CompagniaDAO compagniaDAO = new CompagniaDAO();
            compagniaDAO.aggiungiTelefono(telefono, compagnia.getLogin());

            compagnia.getTelefoni().add(telefono);

            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean eliminaTelefono(String telefono) {
        try {
            CompagniaDAO compagniaDAO = new CompagniaDAO();
            compagniaDAO.eliminaTelefono(telefono);

            compagnia.getTelefoni().remove(telefono);

            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean aggiungiEmail(String email) {
        try {
            CompagniaDAO compagniaDAO = new CompagniaDAO();
            compagniaDAO.aggiungiEmail(email, compagnia.getLogin());

            compagnia.getEmails().add(email);

            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean eliminaEmail(String email) {
        try {
            CompagniaDAO compagniaDAO = new CompagniaDAO();
            compagniaDAO.eliminaEmail(email);

            compagnia.getEmails().remove(email);

            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean modificaSitoWeb(String sito) {
        try {
            CompagniaDAO compagniaDAO = new CompagniaDAO();
            compagniaDAO.modificaSitoWeb(sito, compagnia.getLogin());

            compagnia.setSitoWeb(sito);

            return  true;
        } catch (SQLException e) {
            return false;
        }
    }
}
