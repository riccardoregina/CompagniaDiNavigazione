package model;

import java.sql.Timestamp;

public class Corsa {
    private Compagnia compagnia;
    private Natante natante;
    private Porto portoPartenza;
    private Porto portoScalo = null;
    private Porto portoArrivo;
    private Periodo periodoAttivita;
    private Timestamp dataOraPartenza;
    private Timestamp dataOraScaloAttracco;
    private Timestamp dataOraScaloRipartenza;
    private Timestamp dataOraArrivo;
    private Float costoIntero;
    private Float costoRidotto;
    private Integer minutiRitardo = 0;
    private Integer postiDispPass = natante.getCapienzaPasseggeri();
    private Integer postiDispVei = natante.getCapienzaVeicoli();
    public Corsa(Compagnia compagnia, Natante natante, Porto portoPartenza, Porto portoScalo, Porto portoArrivo, Periodo periodoAttivita, Timestamp dataOraPartenza, Timestamp dataOraScaloAttracco, Timestamp dataOraScaloRipartenza, Timestamp dataOraArrivo, Float costoIntero, Float costoRidotto) {
        this.compagnia = compagnia;
        this.natante = natante;
        this.portoPartenza = portoPartenza;
        this.portoScalo = portoScalo;
        this.portoArrivo = portoArrivo;
        this.dataOraArrivo = dataOraArrivo;
        this.dataOraScaloAttracco = dataOraScaloAttracco;
        this.dataOraScaloRipartenza = dataOraScaloRipartenza;
        this.dataOraPartenza = dataOraPartenza;
        this.costoIntero = costoIntero;
        this.costoRidotto = costoRidotto;
    }
    public Corsa(Compagnia compagnia, Natante natante, Porto portoPartenza, Porto portoArrivo, Periodo periodoAttivita, Timestamp dataOraPartenza, Timestamp dataOraArrivo, Float costoIntero, Float costoRidotto) {
        this.compagnia = compagnia;
        this.natante = natante;
        this.portoPartenza = portoPartenza;
        this.portoArrivo = portoArrivo;
        this.dataOraArrivo = dataOraArrivo;
        this.dataOraPartenza = dataOraPartenza;
        this.costoIntero = costoIntero;
        this.costoRidotto = costoRidotto;
    }

    public Compagnia getCompagnia() {
        return compagnia;
    }

    public Float getCostoIntero() {
        return costoIntero;
    }

    public Float getCostoRidotto() {
        return costoRidotto;
    }

    public Integer getMinutiRitardo() {
        return minutiRitardo;
    }

    public Integer getPostiDispPass() {
        return postiDispPass;
    }

    public Integer getPostiDispVei() {
        return postiDispVei;
    }

    public Natante getNatante() {
        return natante;
    }

    public Periodo getPeriodoAttivita() {
        return periodoAttivita;
    }

    public Timestamp getDataOraArrivo() {
        return dataOraArrivo;
    }

    public Timestamp getDataOraPartenza() {
        return dataOraPartenza;
    }

    public Porto getPortoArrivo() {
        return portoArrivo;
    }

    public Porto getPortoPartenza() {
        return portoPartenza;
    }

    public Porto getPortoScalo() {
        return portoScalo;
    }

    public Timestamp getDataOraScaloAttracco() {
        return dataOraScaloAttracco;
    }

    public Timestamp getDataOraScaloRipartenza() {
        return dataOraScaloRipartenza;
    }
}
