package model;

import java.time.LocalDate;

/**
 * The type Periodo.
 */
public class Periodo {
    private int idPeriodo;
    private LocalDate dataInizio;
    private LocalDate dataFine;
    private String giorni;

    /**
     * Instantiates a new Periodo.
     *
     * @param idPeriodo  the id periodo
     * @param dataInizio the data inizio
     * @param dataFine   the data fine
     * @param giorni     the giorni
     */
    public Periodo(int idPeriodo, LocalDate dataInizio, LocalDate dataFine, String giorni) {
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
    public LocalDate getDataInizio() {
        return dataInizio;
    }

    /**
     * Sets data inizio.
     *
     * @param dataInizio the data inizio
     */
    public void setDataInizio(LocalDate dataInizio) {
        this.dataInizio = dataInizio;
    }

    /**
     * Gets data fine.
     *
     * @return the data fine
     */
    public LocalDate getDataFine() {
        return dataFine;
    }

    /**
     * Sets data fine.
     *
     * @param dataFine the data fine
     */
    public void setDataFine(LocalDate dataFine) {
        this.dataFine = dataFine;
    }

    /**
     * Gets giorni.
     *
     * @return the giorni
     */
    public String getGiorni() {
        return giorni;
    }

    /**
     * Sets giorni.
     *
     * @param giorni the giorni
     */
    public void setGiorni(String giorni) {
        this.giorni = giorni;
    }

    /**
     * Gets id periodo.
     *
     * @return the id periodo
     */
    public int getIdPeriodo() {
        return idPeriodo;
    }
}
