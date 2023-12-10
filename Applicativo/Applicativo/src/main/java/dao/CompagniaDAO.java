package dao;

import java.time.LocalTime;
import java.util.Date;

/**
 * The interface Compagnia dao.
 */
public interface CompagniaDAO extends UtenteDAO {
    /**
     * Aggiunge corsa.
     */
    public void aggiungeCorsa(/*Attributi di corsaRegolare*/);

    /**
     * Modifica corsa regolare.
     */
    public void modificaCorsaRegolare(int idPortoPartenza, int idPortoArrivo, LocalTime orarioPartenza, LocalTime orarioArrivo, float costoIntero, float scontoRidotto, float costoBagaglio, float costoPrevendita, float costoVeicolo, int idCorsa);

    /**
     * Modifica corsa specifica.
     */
    public void modificaCorsaSpecifica(/*Attributi di corsaRegolare*/);

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
    public void aggiungeNatante(String loginCompagnia, String nomeNatante, int capienzaPasseggeri, int capienzaVeicoli, String tipo);

    /**
     * Rimuove natante.
     *
     * @param nomeNatante the nome natante
     */
    public void rimuoveNatante(/*Attributi di Natante*/String nomeNatante);
}
