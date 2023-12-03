package model;

import java.time.LocalTime;

public class Scalo {
    private Porto porto;
    private LocalTime orarioAttracco;
    private LocalTime orarioRipartenza;

    public Scalo(Porto porto, LocalTime orarioAttracco, LocalTime orarioRipartenza) {
        this.porto = porto;
        this.orarioAttracco = orarioAttracco;
        this.orarioRipartenza = orarioRipartenza;
    }

    public Porto getPorto() {
        return porto;
    }

    public void setPorto(Porto porto) {
        this.porto = porto;
    }

    public LocalTime getOrarioAttracco() {
        return orarioAttracco;
    }

    public void setOrarioAttracco(LocalTime orarioAttracco) {
        this.orarioAttracco = orarioAttracco;
    }

    public LocalTime getOrarioRipartenza() {
        return orarioRipartenza;
    }

    public void setOrarioRipartenza(LocalTime orarioRipartenza) {
        this.orarioRipartenza = orarioRipartenza;
    }
}
