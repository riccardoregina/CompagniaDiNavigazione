package model;

import java.util.BitSet;
import java.util.Date;

/**
 * The type Periodo.
 */
public class Periodo {
    private int idPeriodo;
    private Date dataInizio;
    private Date dataFine;
    private BitSet giorni;

    /**
     * Instantiates a new Periodo.
     *
     * @param dataInizio the data inizio
     * @param dataFine   the data fine
     */
    public Periodo(Date dataInizio, Date dataFine) {
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        giorni = new BitSet(7);
    }

    /**
     * Instantiates a new Periodo.
     *
     * @param idPeriodo  the id periodo
     * @param dataInizio the data inizio
     * @param dataFine   the data fine
     * @param giorni     the giorni
     */
    public Periodo(int idPeriodo, Date dataInizio, Date dataFine, BitSet giorni) {
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.giorni = giorni;
        this.idPeriodo = idPeriodo;
    }

    /**
     * Gets data inizio.
     *
     * @return the data inizio
     */
    public Date getDataInizio() {
        return dataInizio;
    }

    /**
     * Sets data inizio.
     *
     * @param dataInizio the data inizio
     */
    public void setDataInizio(Date dataInizio) {
        this.dataInizio = dataInizio;
    }

    /**
     * Gets data fine.
     *
     * @return the data fine
     */
    public Date getDataFine() {
        return dataFine;
    }

    /**
     * Sets data fine.
     *
     * @param dataFine the data fine
     */
    public void setDataFine(Date dataFine) {
        this.dataFine = dataFine;
    }

    /**
     * Gets giorni.
     *
     * @return the giorni
     */
    public BitSet getGiorni() {
        return giorni;
    }

    /**
     * Sets giorni.
     *
     * @param giorni the giorni
     */
    public void setGiorni(BitSet giorni) {
        this.giorni = giorni;
    }

    /**
     * Add giorno.
     *
     * @param index the index
     */
    public void addGiorno(int index) {
        if(index < 7 && index >= 0) {
            giorni.set(index);
        }
    }

    /**
     * Remove giorno.
     *
     * @param index the index
     */
    public void removeGiorno(int index) {
        if(index < 7 && index >= 0) {
            giorni.clear(index);
        }
    }
}
