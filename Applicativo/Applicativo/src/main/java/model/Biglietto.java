package model;

import java.time.LocalDate;

/**
 * The type Biglietto.
 */
public class Biglietto {
    private int idBiglietto;
    private Cliente acquirente;
    private CorsaSpecifica corsa;
    private Veicolo veicolo;
    private boolean bagaglio;
    private boolean prevendita;
    private float prezzo;
    private LocalDate dataAcquisto;
    private int etaPasseggero;

    /**
     * Instantiates a new Biglietto.
     *
     * @param acquirente    the acquirente
     * @param corsa         the corsa
     * @param etaPasseggero the eta passeggero
     */
    public Biglietto(Cliente acquirente, CorsaSpecifica corsa, int etaPasseggero) {
        //DA VEDERE MEGLIO
        this.acquirente = acquirente;
        this.corsa = corsa;
        this.etaPasseggero = etaPasseggero;
        dataAcquisto = LocalDate.now();
        veicolo = null;
        bagaglio = false;
        prevendita = false;
    }

    /**
     * Instantiates a new Biglietto.
     *
     * @param idBiglietto   the id biglietto
     * @param acquirente    the acquirente
     * @param corsa         the corsa
     * @param veicolo       the veicolo
     * @param bagaglio      the bagaglio
     * @param prevendita    the prevendita
     * @param prezzo        the prezzo
     * @param dataAcquisto  the data acquisto
     * @param etaPasseggero the eta passeggero
     */
    public Biglietto(int idBiglietto, Cliente acquirente, CorsaSpecifica corsa,  int etaPasseggero, Veicolo veicolo, boolean bagaglio, boolean prevendita, float prezzo, LocalDate dataAcquisto) {
        this.idBiglietto = idBiglietto;
        this.acquirente = acquirente;
        this.corsa = corsa;
        this.veicolo = veicolo;
        this.bagaglio = bagaglio;
        this.prevendita = prevendita;
        this.prezzo = prezzo;
        this.dataAcquisto = dataAcquisto;
        this.etaPasseggero = etaPasseggero;
    }

    /**
     * Gets id biglietto.
     *
     * @return the id biglietto
     */
    public int getIdBiglietto() {
        return idBiglietto;
    }

    /**
     * Gets acquirente.
     *
     * @return the acquirente
     */
    public Cliente getAcquirente() {
        return acquirente;
    }

    /**
     * Sets acquirente.
     *
     * @param acquirente the acquirente
     */
    public void setAcquirente(Cliente acquirente) {
        this.acquirente = acquirente;
    }

    /**
     * Gets corsa.
     *
     * @return the corsa
     */
    public CorsaSpecifica getCorsa() {
        return corsa;
    }

    /**
     * Sets corsa.
     *
     * @param corsa the corsa
     */
    public void setCorsa(CorsaSpecifica corsa) {
        this.corsa = corsa;
    }

    /**
     * Gets veicolo.
     *
     * @return the veicolo
     */
    public Veicolo getVeicolo() {
        return veicolo;
    }

    /**
     * Sets veicolo.
     *
     * @param veicolo the veicolo
     */
    public void setVeicolo(Veicolo veicolo) {
        this.veicolo = veicolo;
    }

    /**
     * Is bagaglio boolean.
     *
     * @return the boolean
     */
    public boolean isBagaglio() {
        return bagaglio;
    }

    /**
     * Sets bagaglio.
     *
     * @param bagaglio the bagaglio
     */
    public void setBagaglio(boolean bagaglio) {
        this.bagaglio = bagaglio;
    }

    /**
     * Is prevendita boolean.
     *
     * @return the boolean
     */
    public boolean isPrevendita() {
        return prevendita;
    }

    /**
     * Sets prevendita.
     *
     * @param prevendita the prevendita
     */
    public void setPrevendita(boolean prevendita) {
        this.prevendita = prevendita;
    }

    /**
     * Gets prezzo.
     *
     * @return the prezzo
     */
    public float getPrezzo() {
        return prezzo;
    }

    /**
     * Sets prezzo.
     *
     * @param prezzo the prezzo
     */
    public void setPrezzo(float prezzo) {
        this.prezzo = prezzo;
    }

    /**
     * Gets data acquisto.
     *
     * @return the data acquisto
     */
    public LocalDate getDataAcquisto() {
        return dataAcquisto;
    }

    /**
     * Sets data acquisto.
     *
     * @param dataAcquisto the data acquisto
     */
    public void setDataAcquisto(LocalDate dataAcquisto) {
        this.dataAcquisto = dataAcquisto;
    }

    /**
     * Gets eta passeggero.
     *
     * @return the eta passeggero
     */
    public int getEtaPasseggero() {
        return etaPasseggero;
    }

    /**
     * Sets eta passeggero.
     *
     * @param etaPasseggero the eta passeggero
     */
    public void setEtaPasseggero(int etaPasseggero) {
        this.etaPasseggero = etaPasseggero;
    }
}
