package model;

/**
 * The type Veicolo.
 */
public class Veicolo {
    private String tipo;
    private String targa;

    /**
     * Instantiates a new Veicolo.
     *
     * @param tipo  the tipo
     * @param targa the targa
     */
    public Veicolo(String tipo, String targa) {
        this.targa = targa;
        this.tipo = tipo;
    }

    /**
     * Gets tipo.
     *
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Sets tipo.
     *
     * @param tipo the tipo
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Gets targa.
     *
     * @return the targa
     */
    public String getTarga() {
        return targa;
    }

    /**
     * Sets targa.
     *
     * @param targa the targa
     */
    public void setTarga(String targa) {
        this.targa = targa;
    }
}
