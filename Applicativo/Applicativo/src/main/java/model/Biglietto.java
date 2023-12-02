package model;

import java.util.Date;

public class Biglietto {
    private Cliente acquirente;
    private CorsaSpecifica corsaSpecifica;
    private Veicolo veicolo = null;
    private boolean bagaglio = null;
    private boolean prevendita = null;
    private float prezzo;
    private Date dataAcquisto;
    private int etaPasseggero;
    public Biglietto(Cliente acquirente, Corsa corsa, int etaPasseggero) {
        this.acquirente = acquirente;
        this.corsa = corsa;
        if (etaPasseggero < 0) {
            throw new RuntimeException();
        }
        if (etaPasseggero < 12) {
            this.prezzo = corsa.getCostoRidotto();
        } else {
            this.prezzo = corsa.getCostoIntero();
        }
    }
    
    public Cliente getAcquirente() {
        return acquirente;
    }
    public CorsaSpecifica getCorsaSpecifica() {
        return corsaSpecifica;
    }
    public Veicolo getVeicolo() {
        return veicolo;
    }
    public float getPrezzo() {
        return prezzo;
    }
    public Date getDataAcquisto() {
        return dataAcquisto;
    }
    public int getEtaPasseggero() {
        return etaPasseggero;
    }
    public void addVeicolo(Veicolo veicolo) {
        this.veicolo = veicolo;
        prezzo += corsaSpecifica.getCorsaRegolare().getCostoVeicolo();
    }
    public void addVeicolo(String targa, String tipo) {
        this.veicolo = new Veicolo(targa, tipo);
        prezzo += corsaSpecifica.getCorsaRegolare().getCostoVeicolo();
    }
    public void addBagaglio() {
        this.bagaglio = true;
        prezzo += corsaSpecifica.getCorsaRegolare().getCostoBagaglio();
    }
    public void addPrevendita() {
        this.prevendita = true;
        prezzo += corsaSpecifica.getCorsaRegolare().getCostoPrevendita();
    }
}
