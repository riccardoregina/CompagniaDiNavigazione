package model;

import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.ArrayList;

public class CorsaRegolare {
    private Compagnia compagnia;
    private Natante natante;
    private Porto portoPartenza;
    private Porto portoArrivo;
    private Scalo scalo = null;
    private ArrayList<Periodo> periodiAttivita;
    private LocalTime orarioPartenza;
    private LocalTime orarioArrivo;
    private float costoIntero;
    private float scontoRidotto;
    private float costoBagaglio;
    private float costoVeicolo;
    private float costoPrevendita;
    private ArrayList<CorsaSpecifica> corseSpecifiche;

    public CorsaRegolare(Compagnia compagnia, Natante natante, Porto portoPartenza, Porto portoArrivo,
                         LocalTime orarioPartenza, LocalTime orarioArrivo, float costoIntero, float scontoRidotto,
                         float costoBagaglio, float costoVeicolo, float costoPrevendita) {
        this.compagnia = compagnia;
        this.natante = natante;
        this.portoPartenza = portoPartenza;
        this.portoArrivo = portoArrivo;
        this.orarioPartenza = orarioPartenza;
        this.orarioArrivo = orarioArrivo;
        this.costoIntero = costoIntero;
        this.scontoRidotto = scontoRidotto;
        this.costoBagaglio = costoBagaglio;
        this.costoPrevendita = costoPrevendita;
        this.costoVeicolo = costoVeicolo;
        periodiAttivita = new ArrayList<Periodo>();
        corseSpecifiche = new ArrayList<CorsaSpecifica>();
    }

    public Scalo getScalo() {
        return scalo;
    }

    public void setScalo(Scalo scalo) {
        this.scalo = scalo;
    }

    public Compagnia getCompagnia() {
        return compagnia;
    }

    public void setCompagnia(Compagnia compagnia) {
        this.compagnia = compagnia;
    }

    public LocalTime getOrarioPartenza() {
        return orarioPartenza;
    }

    public void setOrarioPartenza(LocalTime orarioPartenza) {
        this.orarioPartenza = orarioPartenza;
    }
    public LocalTime getOrarioArrivo() {
        return orarioArrivo;
    }

    public void setOrarioArrivo(LocalTime orarioArrivo) {
        this.orarioArrivo = orarioArrivo;
    }

    public float getCostoIntero() {
        return costoIntero;
    }
    public void setCostoIntero(float costoIntero) {
        this.costoIntero = costoIntero;
    }

    public float getScontoRidotto() {
        return scontoRidotto;
    }

    public void setScontoRidotto(float scontoRidotto) {
        this.scontoRidotto = scontoRidotto;
    }

    public float getCostoBagaglio() {
        return costoBagaglio;
    }

    public void setCostoBagaglio(float costoBagaglio) {
        this.costoBagaglio = costoBagaglio;
    }

    public float getCostoVeicolo() {
        return costoVeicolo;
    }

    public void setCostoVeicolo(float costoVeicolo) {
        this.costoVeicolo = costoVeicolo;
    }

    public float getCostoPrevendita() {
        return costoPrevendita;
    }

    public void setCostoPrevendita(float costoPrevendita) {
        this.costoPrevendita = costoPrevendita;
    }

    public Natante getNatante() {
        return natante;
    }

    public void setNatante(Natante natante) {
        this.natante = natante;
    }

    public Porto getPortoPartenza() {
        return portoPartenza;
    }

    public void setPortoPartenza(Porto portoPartenza) {
        this.portoPartenza = portoPartenza;
    }

    public Porto getPortoArrivo() {
        return portoArrivo;
    }

    public void setPortoArrivo(Porto portoArrivo) {
        this.portoArrivo = portoArrivo;
    }

    public ArrayList<Periodo> getPeriodiAttivita() {
        return periodiAttivita;
    }

    public void setPeriodiAttivita(ArrayList<Periodo> periodiAttivita) {
        this.periodiAttivita = periodiAttivita;
    }
    public void addPeriodoAttivita(Periodo periodoAttivita) {
        periodiAttivita.add(periodoAttivita);
    }

    public void removePeriodoAttivita(Periodo periodoAttivita) {
        periodiAttivita.remove(periodoAttivita);
    }

    public ArrayList<CorsaSpecifica> getCorseSpecifiche() {
        return corseSpecifiche;
    }

    public void setCorseSpecifiche(ArrayList<CorsaSpecifica> corseSpecifiche) {
        this.corseSpecifiche = corseSpecifiche;
    }

    public void addCorsaSpecifica(CorsaSpecifica corsaSpecifica) {
        corseSpecifiche.add(corsaSpecifica);
    }

    public void removeCorsaSpecifica(CorsaSpecifica corsaSpecifica) {
        corseSpecifiche.remove(corsaSpecifica);
    }
}
