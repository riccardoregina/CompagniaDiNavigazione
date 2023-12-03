package model;

public class Natante {
    private Compagnia compagnia;
    private String nome;
    private int capienzaPasseggeri;
    private int capienzaVeicoli;
    private String tipo;

    public Natante(Compagnia compagnia, String nome, int capienzaPasseggeri, int capienzaVeicoli, String tipo) {
        this.compagnia = compagnia;
        this.nome = nome;
        this.capienzaPasseggeri = capienzaPasseggeri;
        this.capienzaVeicoli = capienzaVeicoli;
        this.tipo = tipo;
    }

    public Compagnia getCompagnia() {
        return compagnia;
    }

    public void setCompagnia(Compagnia compagnia) {
        this.compagnia = compagnia;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCapienzaPasseggeri() {
        return capienzaPasseggeri;
    }

    public void setCapienzaPasseggeri(int capienzaPasseggeri) {
        this.capienzaPasseggeri = capienzaPasseggeri;
    }

    public int getCapienzaVeicoli() {
        return capienzaVeicoli;
    }

    public void setCapienzaVeicoli(int capienzaVeicoli) {
        this.capienzaVeicoli = capienzaVeicoli;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
