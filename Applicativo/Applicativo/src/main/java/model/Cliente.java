package model;

import java.util.ArrayList;

/**
 * The type Cliente.
 */
public class Cliente extends Utente {
    private String nome;
    private String cognome;
    private ArrayList<Veicolo> veicoliPosseduti;
    private ArrayList<Biglietto> bigliettiAcquistati;

    /**
     * Instantiates a new Cliente.
     *
     * @param login   the login
     * @param pw      the pw
     * @param nome    the nome
     * @param cognome the cognome
     */
    public Cliente(String login, String pw, String nome, String cognome) {
        super(login, pw);
        this.nome = nome;
        this.cognome = cognome;
        veicoliPosseduti = new ArrayList<Veicolo>();
        bigliettiAcquistati = new ArrayList<Biglietto>();
    }

    /**
     * Gets nome.
     *
     * @return the nome
     */
    public String getNome() {
        return nome;
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
     * Gets cognome.
     *
     * @return the cognome
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Sets cognome.
     *
     * @param cognome the cognome
     */
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    /**
     * Gets veicoli posseduti.
     *
     * @return the veicoli posseduti
     */
    public ArrayList<Veicolo> getVeicoliPosseduti() {
        return veicoliPosseduti;
    }

    /**
     * Sets veicoli posseduti.
     *
     * @param veicoliPosseduti the veicoli posseduti
     */
    public void setVeicoliPosseduti(ArrayList<Veicolo> veicoliPosseduti) {
        this.veicoliPosseduti = veicoliPosseduti;
    }

    /**
     * Add veicolo.
     *
     * @param veicolo the veicolo
     */
    public void addVeicolo(Veicolo veicolo) {
        veicoliPosseduti.add(veicolo);
    }

    /**
     * Remove veicolo.
     *
     * @param veicolo the veicolo
     */
    public void removeVeicolo(Veicolo veicolo) {
        for(int i = 0; i < veicoliPosseduti.size(); i++) {
            if(veicolo.getTarga().equals(veicoliPosseduti.get(i).getTarga())) {
                veicoliPosseduti.remove(i);
            }
        }
    }

    /**
     * Gets biglietti acquistati.
     *
     * @return the biglietti acquistati
     */
    public ArrayList<Biglietto> getBigliettiAcquistati() {
        return bigliettiAcquistati;
    }

    /**
     * Sets biglietti acquistati.
     *
     * @param bigliettiAcquistati the biglietti acquistati
     */
    public void setBigliettiAcquistati(ArrayList<Biglietto> bigliettiAcquistati) {
        this.bigliettiAcquistati = bigliettiAcquistati;
    }

    /**
     * Add biglietto.
     *
     * @param biglietto the biglietto
     */
    public void addBiglietto(Biglietto biglietto) {
        bigliettiAcquistati.add(biglietto);
    }

    /**
     * Remove biglietto.
     *
     * @param biglietto the biglietto
     */
    public void removeBiglietto(Biglietto biglietto) {
        bigliettiAcquistati.remove(biglietto);
    }
}
