package controller;

import model.*;
import postgresqlDAO.ClienteDB;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

/**
 * The type Controller.
 */
public class ControllerCliente {
    private ClienteDB clienteDB;
    private Cliente cliente;
    private ArrayList<Porto> porti;

    /**
     * Instantiates a new Controller cliente.
     */
    public ControllerCliente() {
        clienteDB = new ClienteDB();
        porti = new ArrayList<>();
    }

    /**
     * Prende login e password dalla GUI,
     * richiede una connessione al DB,
     * se il cliente esiste nel DB,
     * lo costruisce nel model e gli associa i suoi veicoli
     * Restituisce un valore di controllo alla GUI,
     * che si occupera' di cambiare schermata
     *
     * @param login    the login
     * @param password the password
     * @return the boolean
     */
    public boolean clienteAccede(String login, String password) {
        boolean exists = clienteDB.accedi(login, password);
        if (exists) {
            String nome = null;
            String cognome = null;
            ArrayList<String> veicoliTipo = new ArrayList<>();
            ArrayList<String> veicoliTarga = new ArrayList<>();
            //crea oggetto cliente e popolalo con i dati del DB
            clienteDB.fetchClienteFromDB(login, nome, cognome, veicoliTarga, veicoliTipo);
            cliente = new Cliente(login, password, nome, cognome);
            for (int i = 0; i < veicoliTipo.size(); i++) {
                cliente.addVeicolo(new Veicolo(veicoliTipo.get(i), veicoliTarga.get(i)));
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Download porti.
     */
    public void downloadPorti() {
        ArrayList<String> comuni = new ArrayList<>();
        ArrayList<String> indirizzi = new ArrayList<>();
        ArrayList<String> numeriTelefono = new ArrayList<>();
        clienteDB.fetchPorti(comuni, indirizzi, numeriTelefono);
        for (int i = 0; i < comuni.size(); i++) {
            porti.add(new Porto(comuni.get(i), indirizzi.get(i), numeriTelefono.get(i)));
        }
    }

    /**
     * Visualizza corse.
     *
     * @param idPortoPartenza the id porto partenza
     * @param idPortoArrivo   the id porto arrivo
     * @param data            the data
     * @param etaPasseggero   the eta passeggero
     * @param veicolo         the veicolo
     * @param bagaglio        the bagaglio
     */
    public void visualizzaCorse(int idPortoPartenza, int idPortoArrivo, Date data, int etaPasseggero, boolean veicolo, boolean bagaglio) {
        ArrayList<Integer> idCorsa = new ArrayList<>();
        ArrayList<String> nomePortoPartenza = new ArrayList<>();
        ArrayList<String> nomePortoArrivo = new ArrayList<>();
        ArrayList<LocalTime> orarioPartenza = new ArrayList<>();
        ArrayList<LocalTime> orarioArrivo = new ArrayList<>();
        ArrayList<Float> costoIntero = new ArrayList<>();
        ArrayList<Float> scontoRidotto = new ArrayList<>();
        ArrayList<Float> costoBagaglio = new ArrayList<>();
        ArrayList<Float> costoPrevendita = new ArrayList<>();
        ArrayList<Float> costoVeicolo = new ArrayList<>();
        ArrayList<String> nomeCompagnia = new ArrayList<>();
        ArrayList<String> nomeNatante = new ArrayList<>();
        ArrayList<String> tipoNatante = new ArrayList<>();
        clienteDB.cercaCorse(data, idPortoPartenza, idPortoArrivo, idCorsa, nomePortoPartenza, nomePortoArrivo, orarioPartenza, orarioArrivo, costoIntero, scontoRidotto, costoBagaglio, costoPrevendita, costoVeicolo, nomeCompagnia, nomeNatante, tipoNatante);
        ArrayList<Float> prezzo = new ArrayList<>();
        for (int i = 0; i < idCorsa.size(); i++) {
            float prezzoCalcolato = costoIntero.get(i);
            if (etaPasseggero < 12) {
                prezzoCalcolato *= scontoRidotto.get(i);
            } else if (veicolo == true) { //si suppone che la GUI abbia giá verificato che un minorenne non sia associato ad un veicolo!
                prezzoCalcolato += costoVeicolo.get(i);
            } else if (bagaglio == true) {
                prezzoCalcolato += costoBagaglio.get(i);
            }
            prezzo.add(prezzoCalcolato);
        }
        //ADESSO STAMPA SULLA GUI GLI ARRAYLIST IN UNA OPPORTUNA FORMATTAZIONE
    }

    /**
     * Acquista biglietto.
     *
     * @param idCorsa       the id corsa
     * @param data          the data
     * @param targaVeicolo  the targa veicolo
     * @param prevendita    the prevendita
     * @param bagaglio      the bagaglio
     * @param prezzo        the prezzo
     * @param dataAcquisto  the data acquisto
     * @param etaPasseggero the eta passeggero
     */
    public void acquistaBiglietto(int idCorsa, Date data, String targaVeicolo, boolean prevendita, boolean bagaglio, float prezzo, Date dataAcquisto, int etaPasseggero) {
        clienteDB.acquistaBiglietto(idCorsa, data, cliente.getLogin(), targaVeicolo, prevendita, bagaglio, prezzo, dataAcquisto, etaPasseggero);
    }

    /**
     * Visualizza porti.
     */
    public void visualizzaPorti() {
        //QUI STAMPA I PORTI SULLA GUI IN MANIERA OPPORTUNA
    }
}