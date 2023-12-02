package model;

import java.util.ArrayList;

import postgresqlDAO.CompagniaDB;

public class Compagnia extends Utente{
    private final String login;
    private String pw;
    private String nome;
    private ArrayList<String> telefoni;
    private ArrayList<String> emails;
    private String sitoWeb;
    private ArrayList<AccountSocial> accounts;
    private ArrayList<Natante> natantiPosseduti;
    private ArrayList<CorsaRegolare> corseErogate;
    
    public Compagnia(String login, String pw, String nome) {
        super(login, pw);
        natantiPosseduti = new ArrayList<>();
        this.nome = nome;
    }
    public void accedi(String login, String pw) {
        CompagniaDB c = new Compagnia(login, pw, nome);
        c.accedi(login, pw);
    }
    public void aggiungiCorsa(CorsaRegolare corsaRegolare) {
        CompagniaDB c = new Compagnia(login, pw, nome);
        c.aggiungiCorsa(corsaRegolare);
        //qua se la deve vedere un po' la GUI
    }
    public void cancellaCorsaRegolare(CorsaRegolare corsaRegolare) {
        CompagniaDB c = new Compagnia(login, pw, nome);
        c.cancellaCorsaRegolare(corsaRegolare);
    }

    public void cancellaCorsaSpecifica(CorsaSpecifica corsaSpecifica) {
        CompagniaDB c = new Compagnia(login, pw, nome);
        c.cancellaCorsaSpecifica(corsaSpecifica);
    }

    public void segnalaRitardo(CorsaSpecifica corsaSpecifica, int minutiRitardo) {
        CompagniaDB c = new Compagnia(login, pw, nome);
        c.segnalaRitardo(corsaSpecifica, minutiRitardo);
    }

    public void modificaCorsa(CorsaRegolare corsaRegolareModificata) {
        CompagniaDB c = new Compagnia(login, pw, nome);
        c.modificaCorsa(corsaRegolareModificata);
        //anche qui deve un po' vedersela la GUI
    }

    public void aggiungiNatante(Natante natante) {
        natantiPosseduti.add(natante);
    }

    public void rimuoviNatante(Natante natante) {
        natantiPosseduti.remove(natante);
    }

    public void salvaNatantiPosseduti() {
        CompagniaDB c = new Compagnia(login, pw, nome);
        for (Natante n : natantiPosseduti) {
            c.aggiungiNatante(n);
        }
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
