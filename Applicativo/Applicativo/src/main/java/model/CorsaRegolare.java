package model;

import java.sql.Timestamp;
import java.util.ArrayList;

public class CorsaRegolare {
    private Compagnia compagnia;
    private Natante natante;
    private Porto portoPartenza;
    private Porto portoArrivo;
    private ArrayList<Periodo> periodiAttivita;
    private Time orarioPartenza;
    private Time orarioArrivo;
    private float costoIntero;
    private float scontoRidotto;
    private float costoVeicolo;
    private float costoPrevendita;

    public CorsaRegolare(Compagnia compagnia, Natante natante, Porto portoPartenza, Porto portoArrivo, Periodo periodoAttivita, Time orarioPartenza, Time orarioArrivo, float costoIntero, float scontoRidotto, float costoVeicolo, float costoPrevendita) {
        this.compagnia = compagnia;
        this.natante = natante;
        this.portoPartenza = portoPartenza;
        this.portoArrivo = portoArrivo;
        periodiAttivita = new ArrayList<>();
        periodiAttivita.add(periodoAttivita);
        this.orarioPartenza = orarioPartenza;
        this.orarioArrivo = orarioArrivo;
        this.costoIntero = costoIntero;
        this.scontoRidotto = scontoRidotto;
        this.costoPrevendita = costoPrevendita;
        this.costoVeicolo = costoVeicolo;
    }

    public Compagnia getCompagnia() {
        return compagnia;
    }

    public Date getOrarioPartenza() {
        return orarioPartenza;
    }

    public Date getOrarioArrivo() {
        return orarioArrivo;
    }

    public float getCostoIntero() {
        return costoIntero;
    }

    public float getScontoRidotto() {
        return scontoRidotto;
    }

    public float getCostoVeicolo() {
        return costoVeicolo;
    }

    public float getCostoPrevendita() {
        return costoPrevendita;
    }

    public Natante getNatante() {
        return natante;
    }

    public Porto getPortoArrivo() {
        return portoArrivo;
    }

    public Porto getPortoPartenza() {
        return portoPartenza;
    }

    public void addPeriodoAttivita(Periodo periodoAttivita) {
        periodiAttivita.add(periodoAttivita);
    }
}
