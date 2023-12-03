package model;

public class AccountSocial {
    private Compagnia compagnia;
    private String nomeSocial;
    private String tag;
    public AccountSocial(Compagnia compagnia, String nomeSocial, String tag) {
        this.compagnia = compagnia;
        this.nomeSocial = nomeSocial;
        this.tag = tag;
    }

    //metodi per gestire compagnia
    public Compagnia getCompagnia() {
        return compagnia;
    }

    public void setCompagnia(Compagnia compagnia) {
        this.compagnia = compagnia;
    }

    //metodi per gestire nomeSocial
    public String getNomeSocial() {
        return nomeSocial;
    }

    public void setNomeSocial(String nomeSocial) {
        this.nomeSocial = nomeSocial;
    }

    //metodi per gestire tag
    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
