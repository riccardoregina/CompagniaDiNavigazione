package model;

import java.time.LocalTime;

/**
 * The type Scalo.
 */
public class Scalo {
    private Porto porto;
    private LocalTime orarioAttracco;
    private LocalTime orarioRipartenza;

    /**
     * Instantiates a new Scalo.
     *
     * @param porto            the porto
     * @param orarioAttracco   the orario attracco
     * @param orarioRipartenza the orario ripartenza
     */
    public Scalo(Porto porto, LocalTime orarioAttracco, LocalTime orarioRipartenza) {
        this.porto = porto;
        this.orarioAttracco = orarioAttracco;
        this.orarioRipartenza = orarioRipartenza;
    }

    /**
     * Gets porto.
     *
     * @return the porto
     */
    public Porto getPorto() {
        return porto;
    }

    /**
     * Sets porto.
     *
     * @param porto the porto
     */
    public void setPorto(Porto porto) {
        this.porto = porto;
    }

    /**
     * Gets orario attracco.
     *
     * @return the orario attracco
     */
    public LocalTime getOrarioAttracco() {
        return orarioAttracco;
    }

    /**
     * Sets orario attracco.
     *
     * @param orarioAttracco the orario attracco
     */
    public void setOrarioAttracco(LocalTime orarioAttracco) {
        this.orarioAttracco = orarioAttracco;
    }

    /**
     * Gets orario ripartenza.
     *
     * @return the orario ripartenza
     */
    public LocalTime getOrarioRipartenza() {
        return orarioRipartenza;
    }

    /**
     * Sets orario ripartenza.
     *
     * @param orarioRipartenza the orario ripartenza
     */
    public void setOrarioRipartenza(LocalTime orarioRipartenza) {
        this.orarioRipartenza = orarioRipartenza;
    }
}
