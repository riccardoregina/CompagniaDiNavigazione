package model;

/**
 * The type Natante.
 */
public class Natante {
    private Compagnia compagnia;
    private String nome;
    private int capienzaPasseggeri;
    private int capienzaVeicoli;
    private String tipo;

    /**
     * Instantiates a new Natante.
     *
     * @param compagnia          the compagnia
     * @param nome               the nome
     * @param capienzaPasseggeri the capienza passeggeri
     * @param capienzaVeicoli    the capienza veicoli
     * @param tipo               the tipo
     */
    public Natante(Compagnia compagnia, String nome, int capienzaPasseggeri, int capienzaVeicoli, String tipo) {
        this.compagnia = compagnia;
        this.nome = nome;
        this.capienzaPasseggeri = capienzaPasseggeri;
        this.capienzaVeicoli = capienzaVeicoli;
        this.tipo = tipo;
    }

    /**
     * Gets compagnia.
     *
     * @return the compagnia
     */
    public Compagnia getCompagnia() {
        return compagnia;
    }

    /**
     * Sets compagnia.
     *
     * @param compagnia the compagnia
     */
    public void setCompagnia(Compagnia compagnia) {
        this.compagnia = compagnia;
    }

    /**
     * Gets nome.
     *
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Sets nome.
     *
     * @param nome the nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Gets capienza passeggeri.
     *
     * @return the capienza passeggeri
     */
    public int getCapienzaPasseggeri() {
        return capienzaPasseggeri;
    }

    /**
     * Sets capienza passeggeri.
     *
     * @param capienzaPasseggeri the capienza passeggeri
     */
    public void setCapienzaPasseggeri(int capienzaPasseggeri) {
        this.capienzaPasseggeri = capienzaPasseggeri;
    }

    /**
     * Gets capienza veicoli.
     *
     * @return the capienza veicoli
     */
    public int getCapienzaVeicoli() {
        return capienzaVeicoli;
    }

    /**
     * Sets capienza veicoli.
     *
     * @param capienzaVeicoli the capienza veicoli
     */
    public void setCapienzaVeicoli(int capienzaVeicoli) {
        this.capienzaVeicoli = capienzaVeicoli;
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
}
