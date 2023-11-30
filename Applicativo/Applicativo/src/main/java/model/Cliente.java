package model;

import java.util.ArrayList;

public class Cliente extends Utente {
    private String nome;
    private String cognome;
    private ArrayList<Veicolo> veicoliPosseduti;
    public Cliente(String login, String password, String nome, String cognome) {
        super(login, password);
        this.nome = nome;
        this.cognome = cognome;
    }
}
