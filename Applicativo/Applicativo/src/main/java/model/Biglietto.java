package model;

import java.time.LocalDate;

public class Biglietto {
    private Cliente acquirente;
    private CorsaSpecifica corsa;
    private Veicolo veicolo;
    private boolean bagaglio;
    private boolean prevendita;
    private float prezzo;
    private LocalDate dataAcquisto;
    private int etaPasseggero;
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

    public Cliente getAcquirente() {
        return acquirente;
    }

    public void setAcquirente(Cliente acquirente) {
        this.acquirente = acquirente;
    }

    public CorsaSpecifica getCorsa() {
        return corsa;
    }

    public void setCorsa(CorsaSpecifica corsa) {
        this.corsa = corsa;
    }

    public Veicolo getVeicolo() {
        return veicolo;
    }

    public void setVeicolo(Veicolo veicolo) {
        this.veicolo = veicolo;
    }

    public boolean isBagaglio() {
        return bagaglio;
    }

    public void setBagaglio(boolean bagaglio) {
        this.bagaglio = bagaglio;
    }

    public boolean isPrevendita() {
        return prevendita;
    }

    public void setPrevendita(boolean prevendita) {
        this.prevendita = prevendita;
    }

    public float getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(float prezzo) {
        this.prezzo = prezzo;
    }

    public LocalDate getDataAcquisto() {
        return dataAcquisto;
    }

    public void setDataAcquisto(LocalDate dataAcquisto) {
        this.dataAcquisto = dataAcquisto;
    }

    public int getEtaPasseggero() {
        return etaPasseggero;
    }

    public void setEtaPasseggero(int etaPasseggero) {
        this.etaPasseggero = etaPasseggero;
    }
}
