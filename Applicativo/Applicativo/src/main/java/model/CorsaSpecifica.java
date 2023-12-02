package model;

public class CorsaSpecifica {
    private CorsaRegolare corsaRegolare;
    private int postiDispPass = natante.corsaRegolare.getNatante().getCapienzaPasseggeri();
    private int postiDispVei = natante.corsaRegolare.getNatante().getCapienzaVeicoli();
    private int minutiRitardo = 0;
    private boolean cancellata = false;
    private Date data;

    public CorsaRegolare getCorsaRegolare() {
        return corsaRegolare;
    }
    
    public int getPostiDispPass() {
        return postiDispPass;
    }

    public int getPostiDispVei() {
        return postiDispVei;
    }

    public int getMinutiRitardo() {
        return minutiRitardo;
    }

    public boolean isCancellata() {
        return cancellata;
    }

    public Date getData() {
        return data;
    }
}
