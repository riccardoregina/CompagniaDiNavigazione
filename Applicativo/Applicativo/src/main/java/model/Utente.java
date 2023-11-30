package model;

public class Utente {
    private final String login;
    private String password;

    public Utente(String login, String password) {
        this.login = login;
        this.password = password;
    }
    public String getLogin() {
        return login;
    }
}
