package model;

import java.util.ArrayList;

public class Compagnia extends Utente{
    private String nome;
    private ArrayList<String> telefoni;
    private ArrayList<String> emails;
    private String sitoWeb;
    private ArrayList<Natante> natantiPosseduti;
    private ArrayList<AccountSocial> accounts;
    public Compagnia(String login, String password, String nome) {
        super(login, password);
        natantiPosseduti = new ArrayList<>();
        this.nome = nome;
    }
    public String getNome() {
        return nome;
    }

    public ArrayList<String> getEmails() {
        return emails;
    }

    public ArrayList<String> getTelefoni() {
        return telefoni;
    }

    public String getSitoWeb() {
        return sitoWeb;
    }

    public void setEmails(ArrayList<String> emails) {
        this.emails = emails;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSitoWeb(String sitoWeb) {
        this.sitoWeb = sitoWeb;
    }

    public void setTelefoni(ArrayList<String> telefoni) {
        this.telefoni = telefoni;
    }
}
