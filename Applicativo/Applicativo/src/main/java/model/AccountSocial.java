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
}
