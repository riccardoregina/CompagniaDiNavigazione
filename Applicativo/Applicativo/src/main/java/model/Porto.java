package model;

public class Porto {
    private String comune;
    private String indirizzo;
    private String numeroTelefono;
    public Porto(String comune, String indirizzo, String numeroTelefono) {
        this.comune = comune;
        this.indirizzo = indirizzo;
        this.numeroTelefono = numeroTelefono;
    }
    public String getComune() {
        return comune;
    }
    public String getIndirizzo() {
        return indirizzo;
    }
    public String getNumeroTelefono() {
        return numeroTelefono;
    }
}
