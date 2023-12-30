package dao;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

/**
 * The interface Compagnia dao.
 */
public interface CompagniaDAO extends UtenteDAO {
    /**
     * Aggiunge corsa.
     */

    public void aggiungeCorsa(int idPortoPartenza, int idPortoArrivo, String giorni, ArrayList<LocalDate> inizioPeriodo, ArrayList<LocalDate> finePeriodo, LocalTime orarioPartenza, LocalTime orarioArrivo, float costoIntero, float scontoRidotto, float costoBagaglio, float costoPrevendita, float costoVeicolo, String loginCompagnia, String nomeNatante, int idCorsa, ArrayList<Integer> idPeriodo) throws SQLException;

    /**
     * Cancella corsa regolare.
     *
     * @param idCorsa the id corsa
     */
    public void cancellaCorsaRegolare(int idCorsa);

    /**
     * Cancella corsa specifica.
     *
     * @param idCorsa the id corsa
     * @param data    the data
     */
    public void cancellaCorsaSpecifica(int idCorsa, Date data);

    /**
     * Segnala ritardo.
     *
     * @param idCorsa the id corsa
     * @param data    the data
     * @param ritardo the ritardo
     */
    public void segnalaRitardo(int idCorsa, Date data, int ritardo);

    /**
     * Aggiunge natante.
     */
    public void aggiungeNatante(String loginCompagnia, String nomeNatante, int capienzaPasseggeri, int capienzaVeicoli, String tipo) throws Exception;

    /**
     * Rimuove natante.
     *
     * @param nomeNatante the nome natante
     */
    public void rimuoveNatante(/*Attributi di Natante*/String nomeNatante);
}
