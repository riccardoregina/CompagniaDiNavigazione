package model;

public class Natante {
    private Compagnia compagnia;
    private String nome;
    private Integer capienzaPasseggeri;
    private Integer capienzaVeicoli = 0;
    private String tipo = "altro";
    public Natante(Compagnia compagnia, String nome, Integer capienzaPasseggeri, String tipo) throws RuntimeException{
        this.compagnia = compagnia;
        this.nome = nome;
        this.capienzaPasseggeri = capienzaPasseggeri;
        if (tipo.equals("traghetto")) {
            System.out.println("Usa l'altro constructor...");
            throw new RuntimeException();
        }
        this.tipo = tipo;
    }
    public Natante(Compagnia compagnia, String nome, Integer capienzaPasseggeri, Integer capienzaVeicoli, String tipo) {
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

    public Integer getCapienzaPasseggeri() {
        return capienzaPasseggeri;
    }

    public Integer getCapienzaVeicoli() {
        return capienzaVeicoli;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}
