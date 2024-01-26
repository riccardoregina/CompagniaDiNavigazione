package dao;

import java.sql.SQLException;

/**
 * L'interfaccia UtenteDAO
 */
public interface UtenteDAO {
    /**
     * Verifica sul db se esiste un utente con i login e password passati come parametri.
     *
     * @param login       la login
     * @param password    la password
     * @throws SQLException
     */
    void accede(String login, String password) throws SQLException;
}
