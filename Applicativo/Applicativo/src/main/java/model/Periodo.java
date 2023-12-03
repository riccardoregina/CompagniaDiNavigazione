package model;

import java.util.BitSet;
import java.util.Date;

public class Periodo {
    private Date dataInizio;
    private Date dataFine;
    private BitSet giorni;

    public Periodo(Date dataInizio, Date dataFine) {
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        giorni = new BitSet(7);
    }

    public Date getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(Date dataInizio) {
        this.dataInizio = dataInizio;
    }

    public Date getDataFine() {
        return dataFine;
    }

    public void setDataFine(Date dataFine) {
        this.dataFine = dataFine;
    }

    public BitSet getGiorni() {
        return giorni;
    }

    public void setGiorni(BitSet giorni) {
        this.giorni = giorni;
    }

    public void addGiorno(int index) {
        if(index < 7 && index >= 0) {
            giorni.set(index);
        }
    }

    public void removeGiorno(int index) {
        if(index < 7 && index >= 0) {
            giorni.clear(index);
        }
    }
}
