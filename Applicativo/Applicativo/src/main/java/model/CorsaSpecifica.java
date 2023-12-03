package model;

import java.time.LocalDate;

public class CorsaSpecifica {
    private CorsaRegolare corsaRegolare;
    private LocalDate data;
    private int postiDispPass;
    private int postiDispVei;
    private int minutiRitardo;
    private boolean cancellata;

    public CorsaSpecifica(CorsaRegolare corsaRegolare, LocalDate data) {
        this.corsaRegolare = corsaRegolare;
        this.data = data;
        postiDispPass = corsaRegolare.getNatante().getCapienzaPasseggeri();
        postiDispVei = corsaRegolare.getNatante().getCapienzaVeicoli();
        minutiRitardo = 0;
        cancellata = false;
    }
    public CorsaRegolare getCorsaRegolare() {
        return corsaRegolare;
    }

    public void setCorsaRegolare(CorsaRegolare corsaRegolare) {
        this.corsaRegolare = corsaRegolare;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public int getPostiDispPass() {
        return postiDispPass;
    }

    public void setPostiDispPass(int postiDispPass) {
        this.postiDispPass = postiDispPass;
    }

    public int getPostiDispVei() {
        return postiDispVei;
    }

    public void setPostiDispVei(int postiDispVei) {
        this.postiDispVei = postiDispVei;
    }

    public int getMinutiRitardo() {
        return minutiRitardo;
    }

    public void setMinutiRitardo(int minutiRitardo) {
        this.minutiRitardo = minutiRitardo;
    }

    public boolean isCancellata() {
        return cancellata;
    }

    public void setCancellata(boolean b) {
        cancellata = b;
    }
}
