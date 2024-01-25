package dao;

import java.sql.SQLException;

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
    public void accede(String login, String password) throws SQLException;
}
