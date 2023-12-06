package controller;

import model.Compagnia;
import postgresqlDAO.CompagniaDB;

/**
 * The type Controller compagnia.
 */
public class ControllerCompagnia {
    /**
     * Prende login e password dalla GUI,
     * richiede una connessione al DB,
     * se la compagnia esiste nel DB,
     * lo costruisce nel Model
     * Restituisce un valore di controllo alla GUI,
     * che si occupera' di cambiare schermata
     *
     * @param login    the login
     * @param password the password
     * @return the boolean
     */
//Metodi per Compagnia
    public boolean compagniaAccede(String login, String password) {
        CompagniaDB compagniaDB = new CompagniaDB();
        boolean exists = compagniaDB.accedi(login, password);
        if (exists) {
            //crea oggetto cliente e popolalo con i dati del DB
            compagniaDB.retrieveCompagnia(login);
            return true;
        } else {
            //restituisci errore sulla GUI
            return false;
        }
    }
}
