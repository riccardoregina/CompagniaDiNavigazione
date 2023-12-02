package model;

public class Natante {
    private Compagnia compagnia;
    private String nome;
    private int capienzaPasseggeri;
    private int capienzaVeicoli = 0;
    private String tipo = "altro";
    public Natante(Compagnia compagnia, String nome, int capienzaPasseggeri, String tipo) throws RuntimeException{
        this.compagnia = compagnia;
        this.nome = nome;
        this.capienzaPasseggeri = capienzaPasseggeri;
        if (tipo.equals("traghetto")) {
            System.out.println("Usa l'altro constructor...");
            throw new RuntimeException();
        }
        this.tipo = tipo;
    }
    public Natante(Compagnia compagnia, String nome, int capienzaPasseggeri, int capienzaVeicoli, String tipo) {
        this.compagnia = compagnia;
        this.nome = nome;
        this.capienzaPasseggeri = capienzaPasseggeri;
        this.capienzaVeicoli = capienzaVeicoli;
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public Compagnia getCompagnia() {
        return compagnia;
    }

    public int getCapienzaPasseggeri() {
        return capienzaPasseggeri;
    }

    public int getCapienzaVeicoli() {
        return capienzaVeicoli;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}
