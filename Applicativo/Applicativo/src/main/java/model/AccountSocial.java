package model;

/**
 * The type Account social.
 */
public class AccountSocial {
    private Compagnia compagnia;
    private String nomeSocial;
    private String tag;

    /**
     * Instantiates a new Account social.
     *
     * @param compagnia  the compagnia
     * @param nomeSocial the nome social
     * @param tag        the tag
     */
    public AccountSocial(Compagnia compagnia, String nomeSocial, String tag) {
        this.compagnia = compagnia;
        this.nomeSocial = nomeSocial;
        this.tag = tag;
    }

    /**
     * Gets compagnia.
     *
     * @return the compagnia
     */
//metodi per gestire compagnia
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
     * Gets nome social.
     *
     * @return the nome social
     */
//metodi per gestire nomeSocial
    public String getNomeSocial() {
        return nomeSocial;
    }

    /**
     * Sets nome social.
     *
     * @param nomeSocial the nome social
     */
    public void setNomeSocial(String nomeSocial) {
        this.nomeSocial = nomeSocial;
    }

    /**
     * Gets tag.
     *
     * @return the tag
     */
//metodi per gestire tag
    public String getTag() {
        return tag;
    }

    /**
     * Sets tag.
     *
     * @param tag the tag
     */
    public void setTag(String tag) {
        this.tag = tag;
    }
}
