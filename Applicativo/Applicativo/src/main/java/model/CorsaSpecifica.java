package model;

import java.time.LocalDate;

/**
 * The type Corsa specifica.
 */
public class CorsaSpecifica {
    private CorsaRegolare corsaRegolare;
    private LocalDate data;
    private int postiDispPass;
    private int postiDispVei;
    private int minutiRitardo;
    private boolean cancellata;

    /**
     * Instantiates a new Corsa specifica.
     *
     * @param corsaRegolare the corsa regolare
     * @param data          the data
     */
    public CorsaSpecifica(CorsaRegolare corsaRegolare, LocalDate data) {
        this.corsaRegolare = corsaRegolare;
        this.data = data;
        postiDispPass = corsaRegolare.getNatante().getCapienzaPasseggeri();
        postiDispVei = corsaRegolare.getNatante().getCapienzaVeicoli();
        minutiRitardo = 0;
        cancellata = false;
    }

    /**
     * Gets corsa regolare.
     *
     * @return the corsa regolare
     */
    public CorsaRegolare getCorsaRegolare() {
        return corsaRegolare;
    }

    /**
     * Sets corsa regolare.
     *
     * @param corsaRegolare the corsa regolare
     */
    public void setCorsaRegolare(CorsaRegolare corsaRegolare) {
        this.corsaRegolare = corsaRegolare;
    }

    /**
     * Gets data.
     *
     * @return the data
     */
    public LocalDate getData() {
        return data;
    }

    /**
     * Sets data.
     *
     * @param data the data
     */
    public void setData(LocalDate data) {
        this.data = data;
    }

    /**
     * Gets posti disp pass.
     *
     * @return the posti disp pass
     */
    public int getPostiDispPass() {
        return postiDispPass;
    }

    /**
     * Sets posti disp pass.
     *
     * @param postiDispPass the posti disp pass
     */
    public void setPostiDispPass(int postiDispPass) {
        this.postiDispPass = postiDispPass;
    }

    /**
     * Gets posti disp vei.
     *
     * @return the posti disp vei
     */
    public int getPostiDispVei() {
        return postiDispVei;
    }

    /**
     * Sets posti disp vei.
     *
     * @param postiDispVei the posti disp vei
     */
    public void setPostiDispVei(int postiDispVei) {
        this.postiDispVei = postiDispVei;
    }

    /**
     * Gets minuti ritardo.
     *
     * @return the minuti ritardo
     */
    public int getMinutiRitardo() {
        return minutiRitardo;
    }

    /**
     * Sets minuti ritardo.
     *
     * @param minutiRitardo the minuti ritardo
     */
    public void setMinutiRitardo(int minutiRitardo) {
        this.minutiRitardo = minutiRitardo;
    }

    /**
     * Is cancellata boolean.
     *
     * @return the boolean
     */
    public boolean isCancellata() {
        return cancellata;
    }

    /**
     * Sets cancellata.
     *
     * @param b the b
     */
    public void setCancellata(boolean b) {
        cancellata = b;
    }
}
