package model;

public class Veicolo {
    private String tipo = "altro";
    private String targa;
    public Veicolo(String tipo, String targa) {
        this.targa = targa;
        if (!tipo.equals("traghetto") && !tipo.equals("aliscafo") && !tipo.equals("motonave")) {
            this.tipo = "altro";
        } else {
            this.tipo = tipo;
        }
    }
    public String getTarga() {
        return targa;
    }
}
