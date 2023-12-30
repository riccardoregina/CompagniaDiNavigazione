package controller;

import model.*;
import postgresqlDAO.CompagniaDAO;
import unnamed.Pair;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static unnamed.NonStandardConversions.StringToBitset;

/**
 * The type Controller compagnia.
 */
public class ControllerCompagnia {
    private Compagnia compagnia;
    private HashMap<Integer, Porto> porti;
    private HashMap<Pair, CorsaSpecifica> corseSpecifiche;

    /**
     * Instantiates a new Controller compagnia.
     */
//Metodi per Compagnia
    public ControllerCompagnia() {
        porti = new HashMap<>();
        corseSpecifiche = new HashMap<>();
    }

    /**
     * Compagnia accede boolean.
     *
     * @param login    the login
     * @param password the password
     * @return the boolean
     */
    public boolean compagniaAccede(String login, String password) {
        CompagniaDAO compagniaDAO = new CompagniaDAO();
        boolean exists = compagniaDAO.accede(login, password);
        if (exists) {
            //prende dal DB tutte i dati della compagnia e costruisce l'ambiente per l'uso dell'applicativo
            buildModel(login, password);
            return true;
        } else {
            //restituisci errore sulla GUI
            return false;
        }
    }

    /**
     * Build model.
     *
     * @param login    the login
     * @param password the password
     */
    public void buildModel(String login, String password) {
        buildPorti();
        buildCompagnia(login, password);
        buildCorseSpecifiche();
    }

    /**
     * Build porti.
     */
    public void buildPorti() {
        CompagniaDAO compagniaDAO = new CompagniaDAO();
        ArrayList<Integer> idPorto = new ArrayList<>();
        ArrayList<String> comuni = new ArrayList<>();
        ArrayList<String> indirizzi = new ArrayList<>();
        ArrayList<String> numeriTelefono = new ArrayList<>();
        compagniaDAO.fetchPorti(idPorto, comuni, indirizzi, numeriTelefono);
        for (int i = 0; i < comuni.size(); i++) {
            porti.put(idPorto.get(i), new Porto(idPorto.get(i), comuni.get(i), indirizzi.get(i), numeriTelefono.get(i)));
        }
    }

    /**
     * Build compagnia.
     *
     * @param loginCompagnia the login compagnia
     * @param password       the password
     */
    public void buildCompagnia(String loginCompagnia, String password) {
        CompagniaDAO compagniaDAO = new CompagniaDAO();
        String nomeCompagnia = null;
        ArrayList<String> telefono = new ArrayList<>();
        ArrayList<String> email = new ArrayList<>();
        ArrayList<String> nomeSocial = new ArrayList<>();
        ArrayList<String> tagSocial = new ArrayList<>();
        String sitoWeb = null;
        compagniaDAO.fetchCompagnia(loginCompagnia, nomeCompagnia, telefono, email, nomeSocial, tagSocial, sitoWeb);

        compagnia = new Compagnia(loginCompagnia, password, nomeCompagnia);
        compagnia.setTelefoni(telefono);
        compagnia.setEmails(email);
        compagnia.setSitoWeb(sitoWeb);

        ArrayList<AccountSocial> accountSocial = new ArrayList<>();
        for (int i = 0; i < tagSocial.size(); i++) {
            accountSocial.add(new AccountSocial(compagnia, nomeSocial.get(i), tagSocial.get(i)));
        }
        compagnia.setAccounts(accountSocial);

        //costruisco i natanti
        compagniaDAO = new CompagniaDAO();
        ArrayList<String> nomeNatante = new ArrayList<>();
        ArrayList<Integer> capienzaPasseggeri = new ArrayList<>();
        ArrayList<Integer> capienzaVeicoli = new ArrayList<>();
        ArrayList<String> tipo = new ArrayList<>();
        compagniaDAO.fetchNatantiCompagnia(loginCompagnia, nomeNatante, capienzaPasseggeri, capienzaVeicoli, tipo);
        for (int i = 0; i < nomeNatante.size(); i++) {
            compagnia.addNatante(new Natante(compagnia, nomeNatante.get(i), capienzaPasseggeri.get(i), capienzaVeicoli.get(i), tipo.get(i)));
        }

        //costruisco le corse regolari
        compagniaDAO = new CompagniaDAO();
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
        compagniaDAO.fetchCorseRegolari(loginCompagnia, idCorsa, idPortoPartenza, idPortoArrivo, orarioPartenza, orarioArrivo, costoIntero, scontoRidotto, costoBagaglio, costoPrevendita, costoVeicolo, nomeNatanteCorsa);
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
            compagnia.addCorsaRegolare(new CorsaRegolare(id, compagnia, n, pPartenza, pArrivo, oraPartenza, oraArrivo, cIntero, sRidotto, cBagaglio, cPrev, cVei));
        }

        //costruisco i periodi e li assegno alle corse
        compagniaDAO = new CompagniaDAO();
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
            cr.addPeriodoAttivita(new Periodo(idPeriodo.get(i), dataInizio.get(i), dataFine.get(i), StringToBitset(giorni.get(i))));
        }
    }

    /**
     * Build corse specifiche.
     */
    public void buildCorseSpecifiche() {
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
    }

    public boolean aggiungiNatante (String nome, String tipo, int capienzaPasseggeri, int capienzaVeicoli) {
        CompagniaDAO compagniaDAO = new CompagniaDAO();
        try {
            compagniaDAO.aggiungeNatante(compagnia.getLogin(), nome, capienzaPasseggeri, capienzaVeicoli, tipo);
        } catch (Exception e) {
            return false;
        }
        compagnia.addNatante(new Natante(compagnia, nome, capienzaPasseggeri, capienzaVeicoli, tipo));
        return true;
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

    public boolean rimuoviNatante(String nomeNatante) {
        CompagniaDAO compagniaDAO = new CompagniaDAO();
        try {
            compagniaDAO.rimuoveNatante(nomeNatante);
        } catch (Exception e) {
            return false;
        }
        compagnia.getNatantiPosseduti().remove(nomeNatante);
        return true;
    }

    public void visualizzaPorti(ArrayList<Pair> porti) {
        for (Map.Entry<Integer, Porto> it : this.porti.entrySet()) {
            porti.add(new Pair(it.getKey(), it.getValue().getComune()));
        }
    }

    /**
     * tenta di creare una corsa sul DB mediante un metodo di CompagniaDAO.
     * Se riesce, setta il suo idCorsa (parametro di output), la aggiunge nel Model, restituisce true.
     * Se non riesce, idCorsa resta pari a -1, viene restituito false.
     *
     * @param idPortoPartenza
     * @param idPortoArrivo
     * @param giorni
     * @param inizioPeriodo
     * @param finePeriodo
     * @param orarioPartenza
     * @param orarioArrivo
     * @param costoIntero
     * @param scontoRidotto
     * @param costoBagaglio
     * @param costoPrevendita
     * @param costoVeicolo
     * @param nomeNatante
     * @param idCorsa output parameter
     * @return a boolean
     */
    public boolean creaCorsa(int idPortoPartenza, int idPortoArrivo, ArrayList<String> giorni, ArrayList<LocalDate> inizioPeriodo, ArrayList<LocalDate> finePeriodo, LocalTime orarioPartenza, LocalTime orarioArrivo, float costoIntero, float scontoRidotto, float costoBagaglio, float costoPrevendita, float costoVeicolo, String nomeNatante, int idCorsa) {
        CompagniaDAO compagniaDAO = new CompagniaDAO();
        //Mi faccio restituire dal DAO gli id delle tuple inserite.
        idCorsa = -1; //solo una inizializzazione...
        ArrayList<Integer> idPeriodo = new ArrayList<>();
        try {
            compagniaDAO.aggiungeCorsa(idPortoPartenza, idPortoArrivo, giorni, inizioPeriodo, finePeriodo, orarioPartenza, orarioArrivo, costoIntero, scontoRidotto, costoBagaglio, costoPrevendita, costoVeicolo, compagnia.getLogin(), nomeNatante, idCorsa, idPeriodo);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        CorsaRegolare cr = new CorsaRegolare(idCorsa, compagnia, compagnia.getNatantiPosseduti().get(nomeNatante), porti.get(idPortoPartenza), porti.get(idPortoArrivo), orarioPartenza, orarioArrivo, costoIntero, scontoRidotto, costoBagaglio, costoVeicolo, costoPrevendita);
        for (int i = 0; i < giorni.size(); i++) {
            cr.addPeriodoAttivita(new Periodo(idPeriodo.get(i), inizioPeriodo.get(i), finePeriodo.get(i), StringToBitset(giorni.get(i))));
            compagnia.addCorsaRegolare(cr);
        }

        return true;
    }

    public boolean aggiungiScali(int idCorsa, ArrayList<Integer> idPortoScalo, ArrayList<LocalTime> orarioAttracco, ArrayList<LocalTime> orarioRipartenza) {
        CompagniaDAO compagniaDAO = new CompagniaDAO();
        try {
            compagniaDAO.aggiungeScali(idCorsa, idPortoScalo, orarioAttracco, orarioRipartenza);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        CorsaRegolare cr = compagnia.getCorseErogate().get(idCorsa);
        try {
            for (int i = 0; i < idPortoScalo.size(); i++) {
                cr.addScalo(new Scalo(porti.get(idPortoScalo.get(i)), orarioAttracco.get(i), orarioRipartenza.get(i)));
            }
        } catch (NoSuchElementException e) {
            System.out.println("Elemento non trovato.");
            e.printStackTrace();
        }

        return true;
    }

    public void visualizzaCorseSpecifichePerData(LocalDate data, int idPortoPartenza, int idPortoArrivo, ArrayList<Integer> idCorsa, ArrayList<LocalTime> orarioPartenza, ArrayList<LocalTime> orarioArrivo, ArrayList<String> nomeNatante) {
        for (Map.Entry<Pair, CorsaSpecifica> it : corseSpecifiche.entrySet()) {
            if (it.getValue().getData().equals(data) && it.getValue().getCorsaRegolare().getPortoPartenza().equals(porti.get(idPortoPartenza)) && it.getValue().getCorsaRegolare().getPortoArrivo().equals(porti.get(idPortoArrivo))) {
                idCorsa.add(it.getValue().getCorsaRegolare().getIdCorsa());
                orarioPartenza.add(it.getValue().getCorsaRegolare().getOrarioPartenza());
                orarioArrivo.add(it.getValue().getCorsaRegolare().getOrarioArrivo());
                nomeNatante.add(it.getValue().getCorsaRegolare().getNatante().getNome());
            }
        }
    }
}
