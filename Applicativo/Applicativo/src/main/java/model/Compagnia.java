package model;

import java.util.ArrayList;

import postgresqlDAO.CompagniaDB;

public class Compagnia extends Utente{
    private String nome;
    private ArrayList<String> telefoni;
    private ArrayList<String> emails;
    private String sitoWeb;
    private ArrayList<AccountSocial> accounts;
    private ArrayList<Natante> natantiPosseduti;
    private ArrayList<CorsaRegolare> corseErogate;
    
    public Compagnia(String login, String pw, String nome) {
        super(login, pw);
        natantiPosseduti = new ArrayList<Natante>();
        this.nome = nome;
    }

    //metodi per gestire il nome della compagnia
    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    //metodi per gestire i natanti posseduti
    public ArrayList<Natante> getNatantiPosseduti() {
        return natantiPosseduti;
    }

    public void setNatantiPosseduti(ArrayList<Natante> natanti) {
        this.natantiPosseduti = natanti;
    }
    public void addNatante(Natante natante) {
        natantiPosseduti.add(natante);
    }

    public void removeNatante(Natante natante) {
        for(int i = 0; i < natantiPosseduti.size(); i++) {
            if (natante.getNome().equals(natantiPosseduti.get(i).getNome())) {
                natantiPosseduti.remove(i);
            }
        }
    }

    //metodi per gestire i numeri di telefono
    public ArrayList<String> getTelefoni() {
        return telefoni;
    }

    public void setTelefoni(ArrayList<String> telefoni) {
        this.telefoni = telefoni;
    }

    public void addTelefono(String numeroTelefonico) {
        telefoni.add(numeroTelefonico);
    }

    public void removeTelefono(String numeroTelefonico) {
        for(int i = 0; i < telefoni.size(); i++) {
            if(numeroTelefonico.equals(telefoni.get(i))) {
                telefoni.remove(i);
            }
        }
    }

    //metodi per gestire emails
    public ArrayList<String> getEmails() {
        return emails;
    }

    public void setEmails(ArrayList<String> emails) {
        this.emails = emails;
    }

    public void addEmail(String email) {
        emails.add(email);
    }

    public void removeEmail(String email) {
        for(int i = 0; i < emails.size(); i++) {
            if(email.equals(emails.get(i))) {
                emails.remove(i);
            }
        }
    }

    //metodi per gestire sitoWeb
    public String getSitoWeb() {
        return sitoWeb;
    }

    public void setSitoWeb(String sitoWeb) {
        this.sitoWeb = sitoWeb;
    }

    //metodi per gestire accounts
    public ArrayList<AccountSocial> getAccounts() {
        return accounts;
    }

    public void setAccounts(ArrayList<AccountSocial> accounts) {
        this.accounts = accounts;
    }

    public void addAccount(AccountSocial account) {
        this.accounts.add(account);
    }

    public void removeAccount(AccountSocial account) {
        for(int i = 0; i < accounts.size(); i++) {
            String nomeSocial = accounts.get(i).getNomeSocial();
            String tag = accounts.get(i).getTag();
            if (account.getNomeSocial().equals(nomeSocial) && account.getTag().equals(tag)) {
                accounts.remove(i);
            }
        }
    }

    //metodi per gestire le corse erogate
    public ArrayList<CorsaRegolare> getCorseErogate() {
        return corseErogate;
    }

    public void setCorseErogate(ArrayList<CorsaRegolare> corseErogate) {
        this.corseErogate = corseErogate;
    }

    public void addCorsaRegolare(CorsaRegolare corsa) {
        corseErogate.add(corsa);
    }

    public void removeCorsaRegolare(CorsaRegolare corsa) {
        corseErogate.remove(corsa);
    }
}
