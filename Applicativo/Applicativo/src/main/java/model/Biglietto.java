package model;

import java.util.Date;

public class Biglietto {
    private Cliente acquirente;
    private Corsa corsa;
    private Veicolo veicolo;
    private Float prezzo;
    private Date dataAcquisto;
    private Integer etaPasseggero;
    public Biglietto(Cliente acquirente, Corsa corsa, Integer etaPasseggero) {
        this.acquirente = acquirente;
        this.corsa = corsa;
        if (etaPasseggero < 0) {
            throw new RuntimeException();
        }
        if (etaPasseggero < 9) {
            this.prezzo = corsa.getCostoRidotto();
        } else {
            this.prezzo = corsa.getCostoIntero();
        }

    }
}
