package dao;

/**
 * The interface Utente dao.
 */
public interface UtenteDAO {
    /**
     * Accedi boolean.
     *
     * @param login    the login
     * @param password the password
     * @return the boolean
     */
    public boolean accede(String login, String password);
}
