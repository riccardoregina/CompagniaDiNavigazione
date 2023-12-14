package model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The type Compagnia.
 */
public class Compagnia extends Utente{
    private String nome;
    private ArrayList<String> telefoni;
    private ArrayList<String> emails;
    private String sitoWeb;
    private ArrayList<AccountSocial> accounts;
    private HashMap<String, Natante> natantiPosseduti;
    private HashMap<Integer, CorsaRegolare> corseErogate;

    /**
     * Instantiates a new Compagnia.
     *
     * @param login the login
     * @param pw    the pw
     * @param nome  the nome
     */
    public Compagnia(String login, String pw, String nome) {
        super(login, pw);
        natantiPosseduti = new HashMap<>();
        this.nome = nome;
    }

    /**
     * Gets nome.
     *
     * @return the nome
     */
//metodi per gestire il nome della compagnia
    public String getNome() {
        return this.nome;
    }

    /**
     * Sets nome.
     *
     * @param nome the nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Gets natanti posseduti.
     *
     * @return the natanti posseduti
     */
//metodi per gestire i natanti posseduti
    public HashMap<String, Natante> getNatantiPosseduti() {
        return natantiPosseduti;
    }

    /**
     * Sets natanti posseduti.
     *
     * @param natanti the natanti
     */
    public void setNatantiPosseduti(HashMap<String, Natante> natanti) {
        this.natantiPosseduti = natanti;
    }

    /**
     * Add natante.
     *
     * @param natante the natante
     */
    public void addNatante(Natante natante) {
        natantiPosseduti.put(natante.getNome(), natante);
    }

    /**
     * Remove natante.
     *
     * @param natante the natante
     */
    public void removeNatante(Natante natante) {
        natantiPosseduti.remove(natante.getNome());
    }

    /**
     * Gets telefoni.
     *
     * @return the telefoni
     */
//metodi per gestire i numeri di telefono
    public ArrayList<String> getTelefoni() {
        return telefoni;
    }

    /**
     * Sets telefoni.
     *
     * @param telefoni the telefoni
     */
    public void setTelefoni(ArrayList<String> telefoni) {
        this.telefoni = telefoni;
    }

    /**
     * Add telefono.
     *
     * @param numeroTelefonico the numero telefonico
     */
    public void addTelefono(String numeroTelefonico) {
        telefoni.add(numeroTelefonico);
    }

    /**
     * Remove telefono.
     *
     * @param numeroTelefonico the numero telefonico
     */
    public void removeTelefono(String numeroTelefonico) {
        for(int i = 0; i < telefoni.size(); i++) {
            if(numeroTelefonico.equals(telefoni.get(i))) {
                telefoni.remove(i);
            }
        }
    }

    /**
     * Gets emails.
     *
     * @return the emails
     */
//metodi per gestire emails
    public ArrayList<String> getEmails() {
        return emails;
    }

    /**
     * Sets emails.
     *
     * @param emails the emails
     */
    public void setEmails(ArrayList<String> emails) {
        this.emails = emails;
    }

    /**
     * Add email.
     *
     * @param email the email
     */
    public void addEmail(String email) {
        emails.add(email);
    }

    /**
     * Remove email.
     *
     * @param email the email
     */
    public void removeEmail(String email) {
        for(int i = 0; i < emails.size(); i++) {
            if(email.equals(emails.get(i))) {
                emails.remove(i);
            }
        }
    }

    /**
     * Gets sito web.
     *
     * @return the sito web
     */
//metodi per gestire sitoWeb
    public String getSitoWeb() {
        return sitoWeb;
    }

    /**
     * Sets sito web.
     *
     * @param sitoWeb the sito web
     */
    public void setSitoWeb(String sitoWeb) {
        this.sitoWeb = sitoWeb;
    }

    /**
     * Gets accounts.
     *
     * @return the accounts
     */
//metodi per gestire accounts
    public ArrayList<AccountSocial> getAccounts() {
        return accounts;
    }

    /**
     * Sets accounts.
     *
     * @param accounts the accounts
     */
    public void setAccounts(ArrayList<AccountSocial> accounts) {
        this.accounts = accounts;
    }

    /**
     * Add account.
     *
     * @param account the account
     */
    public void addAccount(AccountSocial account) {
        this.accounts.add(account);
    }

    /**
     * Remove account.
     *
     * @param account the account
     */
    public void removeAccount(AccountSocial account) {
        for(int i = 0; i < accounts.size(); i++) {
            String nomeSocial = accounts.get(i).getNomeSocial();
            String tag = accounts.get(i).getTag();
            if (account.getNomeSocial().equals(nomeSocial) && account.getTag().equals(tag)) {
                accounts.remove(i);
            }
        }
    }

    /**
     * Gets corse erogate.
     *
     * @return the corse erogate
     */
//metodi per gestire le corse erogate
    public HashMap<Integer, CorsaRegolare> getCorseErogate() {
        return corseErogate;
    }

    /**
     * Sets corse erogate.
     *
     * @param corseErogate the corse erogate
     */
    public void setCorseErogate(HashMap<Integer, CorsaRegolare> corseErogate) {
        this.corseErogate = corseErogate;
    }

    /**
     * Add corsa regolare.
     *
     * @param corsa the corsa
     */
    public void addCorsaRegolare(CorsaRegolare corsa) {
        corseErogate.put(corsa.getIdCorsa(), corsa);
    }

    /**
     * Remove corsa regolare.
     *
     * @param corsa the corsa
     */
    public void removeCorsaRegolare(CorsaRegolare corsa) {
        corseErogate.remove(corsa);
    }
}
