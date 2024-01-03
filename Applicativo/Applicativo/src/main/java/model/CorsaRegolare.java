package model;

import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * The type Corsa regolare.
 */
public class CorsaRegolare {
    private int idCorsa;
    private Compagnia compagnia;
    private Natante natante;
    private Porto portoPartenza;
    private Porto portoArrivo;
    private ArrayList<Periodo> periodiAttivita;
    private LocalTime orarioPartenza;
    private LocalTime orarioArrivo;
    private float costoIntero;
    private float scontoRidotto;
    private float costoBagaglio;
    private float costoVeicolo;
    private float costoPrevendita;
    private ArrayList<Scalo> scali;
    private ArrayList<CorsaSpecifica> corseSpecifiche;
    private CorsaRegolare corsaSup;

    /**
     * Instantiates a new Corsa regolare.
     *
     * @param idCorsa         the id corsa
     * @param compagnia       the compagnia
     * @param natante         the natante
     * @param portoPartenza   the porto partenza
     * @param portoArrivo     the porto arrivo
     * @param orarioPartenza  the orario partenza
     * @param orarioArrivo    the orario arrivo
     * @param costoIntero     the costo intero
     * @param scontoRidotto   the sconto ridotto
     * @param costoBagaglio   the costo bagaglio
     * @param costoVeicolo    the costo veicolo
     * @param costoPrevendita the costo prevendita
     */
    public CorsaRegolare(int idCorsa, Compagnia compagnia, Natante natante, Porto portoPartenza, Porto portoArrivo,
                         LocalTime orarioPartenza, LocalTime orarioArrivo, float costoIntero, float scontoRidotto,
                         float costoBagaglio, float costoVeicolo, float costoPrevendita, CorsaRegolare corsaSup) {
        this.idCorsa = idCorsa;
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
        this.corsaSup = corsaSup;
        periodiAttivita = new ArrayList<Periodo>();
        corseSpecifiche = new ArrayList<CorsaSpecifica>();
        scali = new ArrayList<>();
    }

    /**
     * Gets id corsa.
     *
     * @return the id corsa
     */
    public int getIdCorsa() {
        return idCorsa;
    }

    /**
     * Sets scalo.
     *
     * @param scalo the scalo
     */
    public void addScalo(Scalo scalo) {
        this.scali.add(scalo);
    }

    /**
     * Gets compagnia.
     *
     * @return the compagnia
     */
    public Compagnia getCompagnia() {
        return compagnia;
    }

    /**
     * Sets compagnia.
     *
     * @param compagnia the compagnia
     */
    public void setCompagnia(Compagnia compagnia) {
        this.compagnia = compagnia;
    }

    /**
     * Gets orario partenza.
     *
     * @return the orario partenza
     */
    public LocalTime getOrarioPartenza() {
        return orarioPartenza;
    }

    /**
     * Sets orario partenza.
     *
     * @param orarioPartenza the orario partenza
     */
    public void setOrarioPartenza(LocalTime orarioPartenza) {
        this.orarioPartenza = orarioPartenza;
    }

    /**
     * Gets orario arrivo.
     *
     * @return the orario arrivo
     */
    public LocalTime getOrarioArrivo() {
        return orarioArrivo;
    }

    /**
     * Sets orario arrivo.
     *
     * @param orarioArrivo the orario arrivo
     */
    public void setOrarioArrivo(LocalTime orarioArrivo) {
        this.orarioArrivo = orarioArrivo;
    }

    /**
     * Gets costo intero.
     *
     * @return the costo intero
     */
    public float getCostoIntero() {
        return costoIntero;
    }

    /**
     * Sets costo intero.
     *
     * @param costoIntero the costo intero
     */
    public void setCostoIntero(float costoIntero) {
        this.costoIntero = costoIntero;
    }

    /**
     * Gets sconto ridotto.
     *
     * @return the sconto ridotto
     */
    public float getScontoRidotto() {
        return scontoRidotto;
    }

    /**
     * Sets sconto ridotto.
     *
     * @param scontoRidotto the sconto ridotto
     */
    public void setScontoRidotto(float scontoRidotto) {
        this.scontoRidotto = scontoRidotto;
    }

    /**
     * Gets costo bagaglio.
     *
     * @return the costo bagaglio
     */
    public float getCostoBagaglio() {
        return costoBagaglio;
    }

    /**
     * Sets costo bagaglio.
     *
     * @param costoBagaglio the costo bagaglio
     */
    public void setCostoBagaglio(float costoBagaglio) {
        this.costoBagaglio = costoBagaglio;
    }

    /**
     * Gets costo veicolo.
     *
     * @return the costo veicolo
     */
    public float getCostoVeicolo() {
        return costoVeicolo;
    }

    /**
     * Sets costo veicolo.
     *
     * @param costoVeicolo the costo veicolo
     */
    public void setCostoVeicolo(float costoVeicolo) {
        this.costoVeicolo = costoVeicolo;
    }

    /**
     * Gets costo prevendita.
     *
     * @return the costo prevendita
     */
    public float getCostoPrevendita() {
        return costoPrevendita;
    }

    /**
     * Sets costo prevendita.
     *
     * @param costoPrevendita the costo prevendita
     */
    public void setCostoPrevendita(float costoPrevendita) {
        this.costoPrevendita = costoPrevendita;
    }

    /**
     * Gets natante.
     *
     * @return the natante
     */
    public Natante getNatante() {
        return natante;
    }

    /**
     * Sets natante.
     *
     * @param natante the natante
     */
    public void setNatante(Natante natante) {
        this.natante = natante;
    }

    /**
     * Gets porto partenza.
     *
     * @return the porto partenza
     */
    public Porto getPortoPartenza() {
        return portoPartenza;
    }

    /**
     * Sets porto partenza.
     *
     * @param portoPartenza the porto partenza
     */
    public void setPortoPartenza(Porto portoPartenza) {
        this.portoPartenza = portoPartenza;
    }

    /**
     * Gets porto arrivo.
     *
     * @return the porto arrivo
     */
    public Porto getPortoArrivo() {
        return portoArrivo;
    }

    /**
     * Sets porto arrivo.
     *
     * @param portoArrivo the porto arrivo
     */
    public void setPortoArrivo(Porto portoArrivo) {
        this.portoArrivo = portoArrivo;
    }

    /**
     * Gets periodi attivita.
     *
     * @return the periodi attivita
     */
    public ArrayList<Periodo> getPeriodiAttivita() {
        return periodiAttivita;
    }

    /**
     * Sets periodi attivita.
     *
     * @param periodiAttivita the periodi attivita
     */
    public void setPeriodiAttivita(ArrayList<Periodo> periodiAttivita) {
        this.periodiAttivita = periodiAttivita;
    }

    /**
     * Add periodo attivita.
     *
     * @param periodoAttivita the periodo attivita
     */
    public void addPeriodoAttivita(Periodo periodoAttivita) {
        periodiAttivita.add(periodoAttivita);
    }

    /**
     * Remove periodo attivita.
     *
     * @param periodoAttivita the periodo attivita
     */
    public void removePeriodoAttivita(Periodo periodoAttivita) {
        periodiAttivita.remove(periodoAttivita);
    }

    /**
     * Gets corse specifiche.
     *
     * @return the corse specifiche
     */
    public ArrayList<CorsaSpecifica> getCorseSpecifiche() {
        return corseSpecifiche;
    }

    /**
     * Sets corse specifiche.
     *
     * @param corseSpecifiche the corse specifiche
     */
    public void setCorseSpecifiche(ArrayList<CorsaSpecifica> corseSpecifiche) {
        this.corseSpecifiche = corseSpecifiche;
    }

    /**
     * Add corsa specifica.
     *
     * @param corsaSpecifica the corsa specifica
     */
    public void addCorsaSpecifica(CorsaSpecifica corsaSpecifica) {
        corseSpecifiche.add(corsaSpecifica);
    }

    /**
     * Remove corsa specifica.
     *
     * @param corsaSpecifica the corsa specifica
     */
    public void removeCorsaSpecifica(CorsaSpecifica corsaSpecifica) {
        corseSpecifiche.remove(corsaSpecifica);
    }

    public ArrayList<Scalo> getScali() {
        return scali;
    }

    public CorsaRegolare getCorsaSup() {
        return corsaSup;
    }

    public void setCorsaSup(CorsaRegolare corsaSup) {
        this.corsaSup = corsaSup;
    }
}
