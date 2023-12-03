package model;

public class Veicolo {
    private String tipo;
    private String targa;
    public Veicolo(String tipo, String targa) {
        this.targa = targa;
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTarga() {
        return targa;
    }

    public void setTarga(String targa) {
        this.targa = targa;
    }
}
