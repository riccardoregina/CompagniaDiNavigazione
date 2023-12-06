package model;

/**
 * The type Porto.
 */
public class Porto {
    private String comune;
    private String indirizzo;
    private String numeroTelefono;

    /**
     * Instantiates a new Porto.
     *
     * @param comune         the comune
     * @param indirizzo      the indirizzo
     * @param numeroTelefono the numero telefono
     */
    public Porto(String comune, String indirizzo, String numeroTelefono) {
        this.comune = comune;
        this.indirizzo = indirizzo;
        this.numeroTelefono = numeroTelefono;
    }

    /**
     * Gets comune.
     *
     * @return the comune
     */
    public String getComune() {
        return comune;
    }

    /**
     * Sets comune.
     *
     * @param comune the comune
     */
    public void setComune(String comune) {
        this.comune = comune;
    }

    /**
     * Gets indirizzo.
     *
     * @return the indirizzo
     */
    public String getIndirizzo() {
        return indirizzo;
    }

    /**
     * Sets indirizzo.
     *
     * @param indirizzo the indirizzo
     */
    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    /**
     * Gets numero telefono.
     *
     * @return the numero telefono
     */
    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    /**
     * Sets numero telefono.
     *
     * @param numeroTelefono the numero telefono
     */
    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }
}
