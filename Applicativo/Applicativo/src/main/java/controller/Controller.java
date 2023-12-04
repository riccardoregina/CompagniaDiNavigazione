package controller;

import database.ClienteTrovato;
import database.CompagniaTrovata;
import database.CorseTrovate;
import model.Cliente;
import model.Compagnia;
import postgresqlDAO.ClienteDB;
import postgresqlDAO.CompagniaDB;

import java.util.Date;

public class Controller {
    //Metodi per Cliente
    public void clienteAccede(/*prendi login e password dalla GUI*/String login, String password) {
        ClienteDB clienteDB = new ClienteDB();
        boolean exists = clienteDB.accedi(login, password);
        if (exists) {
            //crea oggetto cliente e popolalo con i dati del DB
            ClienteTrovato clienteTrovato = clienteDB.retrieveCliente(login);
            Cliente cliente = new Cliente(clienteTrovato.getLogin(), clienteTrovato.getPw(), clienteTrovato.getNome(), clienteTrovato.getCognome());
        } else {
            //restituisci errore sulla GUI
        }
    }

    public void visualizzaCorse(/*prendi parametri dalla GUI*/int idPortoPartenza, int idPortoArrivo, Date data) {
        ClienteDB clienteDB = new ClienteDB();
        CorseTrovate corseTrovate = clienteDB.visualizzaCorse(data, idPortoPartenza, idPortoArrivo);
        //stampa sulla GUI di CorseTrovate
        /*
        * La domanda é: nella GUI, al momento della scelta dei porti, come facciamo visualizzare
        * tutti i porti disponibili?
        * Li carichiamo dal DB non appena l'Utente riesce a fare l'accesso?
        * Risolto cio, dobbiamo fare in modo che ció che viene scelto dalla GUI sia un oggetto Porto
        * e non solo una stringa: ci serve idPorto per la query, in quanto chiave primaria.
        * */
    }




    //Metodi per Compagnia
    public void compagniaAccede(/*prendi login, password e nome dalla GUI*/) {
        CompagniaDB compagniaDB = new CompagniaDB();
        boolean exists = compagniaDB.accedi(login, password, nome);
        if (exists) {
            //crea oggetto cliente e popolalo con i dati del DB
            CompagniaTrovata compagniaTrovata = compagniaDB.retrieveCompagnia(login);
            Compagnia compagnia = new Compagnia(compagniaTrovata.getLogin(), compagniaTrovata.getPw(), compagniaTrovata.getNome());
        } else {
            //restituisci errore sulla GUI
        }
    }

    //Metodi per ...
}