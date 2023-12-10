package controller;

import model.*;
import postgresqlDAO.CompagniaDB;
import unnamed.Pair;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Date;
import java.util.HashMap;

/**
 * The type Controller compagnia.
 */
public class ControllerCompagnia {
    private CompagniaDB compagniaDB;
    private Compagnia compagnia;
    private HashMap<Integer, Porto> porti;
    private HashMap<Pair, CorsaSpecifica> corseSpecifiche;

    /**
     * Instantiates a new Controller compagnia.
     */
//Metodi per Compagnia
    public ControllerCompagnia() {
        compagniaDB = new CompagniaDB();
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
        CompagniaDB compagniaDB = new CompagniaDB();
        boolean exists = compagniaDB.accede(login, password);
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
        ArrayList<Integer> idPorto = new ArrayList<>();
        ArrayList<String> comuni = new ArrayList<>();
        ArrayList<String> indirizzi = new ArrayList<>();
        ArrayList<String> numeriTelefono = new ArrayList<>();
        compagniaDB.fetchPorti(idPorto, comuni, indirizzi, numeriTelefono);
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
        String nomeCompagnia = null;
        ArrayList<String> telefono = new ArrayList<>();
        ArrayList<String> email = new ArrayList<>();
        ArrayList<String> nomeSocial = new ArrayList<>();
        ArrayList<String> tagSocial = new ArrayList<>();
        String sitoWeb = null;
        compagniaDB.fetchCompagnia(loginCompagnia, nomeCompagnia, telefono, email, nomeSocial, tagSocial, sitoWeb);

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
        ArrayList<String> nomeNatante = new ArrayList<>();
        ArrayList<Integer> capienzaPasseggeri = new ArrayList<>();
        ArrayList<Integer> capienzaVeicoli = new ArrayList<>();
        ArrayList<String> tipo = new ArrayList<>();
        compagniaDB.fetchNatantiCompagnia(loginCompagnia, nomeNatante, capienzaPasseggeri, capienzaVeicoli, tipo);
        for (int i = 0; i < nomeNatante.size(); i++) {
            compagnia.addNatante(new Natante(compagnia, nomeNatante.get(i), capienzaPasseggeri.get(i), capienzaVeicoli.get(i), tipo.get(i)));
        }

        //costruisco le corse regolari
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
        compagniaDB.fetchCorseRegolari(loginCompagnia, idCorsa, idPortoPartenza, idPortoArrivo, orarioPartenza, orarioArrivo, costoIntero, scontoRidotto, costoBagaglio, costoPrevendita, costoVeicolo, nomeNatante);
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
        HashMap<Integer, Periodo> periodi = new HashMap<>();
        ArrayList<Integer> corsa = new ArrayList<>();
        ArrayList<Integer> idPeriodo = new ArrayList<>();
        ArrayList<Date> dataInizio = new ArrayList<>();
        ArrayList<Date> dataFine = new ArrayList<>();
        ArrayList<BitSet> giorni = new ArrayList<>();
        compagniaDB.fetchPeriodiAttivitaCorse(loginCompagnia, idPeriodo, dataInizio, dataFine, giorni, corsa);
        //Assegno un periodo alla sua corsa.
        for (int i = 0; i < dataInizio.size(); i++) {
            CorsaRegolare cr = compagnia.getCorseErogate().get(corsa.get(i));
            cr.addPeriodoAttivita(new Periodo(idPeriodo.get(i), dataInizio.get(i), dataFine.get(i), giorni.get(i)));
        }
    }

    /**
     * Build corse specifiche.
     */
    public void buildCorseSpecifiche() {
        ArrayList<Integer> corsaRegolare = new ArrayList<>();
        ArrayList<LocalDate> data = new ArrayList<>();
        ArrayList<Integer> postiDispPass = new ArrayList<>();
        ArrayList<Integer> postiDispVei = new ArrayList<>();
        ArrayList<Integer> minutiRitardo = new ArrayList<>();
        ArrayList<Boolean> cancellata = new ArrayList<>();
        compagniaDB.fetchCorseSpecifiche(compagnia.getLogin(),corsaRegolare, data, postiDispPass, postiDispVei, minutiRitardo, cancellata);
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
}
