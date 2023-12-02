package model;

import java.util.ArrayList;

import dao.ClienteDAO;
import database.CorseTrovate;
import postgresqlDAO.ClienteDB;

public class Cliente extends Utente {
    private final String login;
    private String nome;
    private String cognome;
    private String pw;
    private ArrayList<Veicolo> veicoliPosseduti;
    private ArrayList<Biglietto> bigliettiAcquistati;
    public Cliente(String login, String pw, String nome, String cognome) {
        super(login, pw);
        this.nome = nome;
        this.cognome = cognome;
    }
    public accedi(String login, String pw) {
        ClienteDB c = new ClienteDB();
        c.accedi(login, pw);
    }
    public getNome() {
        return nome;
    }
    public getCognome() {
        return cognome;
    }
    public ArrayList<Veicolo> getVeicoliPosseduti() {
        return veicoliPosseduti;
    }
    public addVeicolo(Veicolo veicolo) {
        veicoliPosseduti.add(veicolo);
    }
    public addVeicolo(String targa, String tipo) {
        veicoliPosseduti.add(new Veicolo(targa, tipo));
    }
    public ArrayList<Veicolo> getBigliettiAcquistati() {
        return bigliettiAcquistati;
    }
    public String getLogin() {
        return login;
    }
    public void visualizzaCorse(Date data, String portoPartenza, String portoArrivo) {
        ClienteDB c = new ClienteDB();
        CorseTrovate ct = c.visualizzaCorse(/*input da GUI */);
        //Qui stampa sulla GUI in un certo formato
    }
    public void acquistaBiglietto() {
        ClienteDB c = new ClienteDB();
        Biglietto b = new Biglietto(this, /*da GUI*/, /*da GUI */);
        c.acquistaBiglietto(b);
    }
    public void salvaVeicoliPosseduti() {
        ClienteDB c = new ClienteDB();
        for (Veicolo v : veicoliPosseduti) {
            c.addVeicolo(v);
        }
    }
}
