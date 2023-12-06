package dao;

import java.util.Date;

/**
 * The interface Compagnia dao.
 */
public interface CompagniaDAO extends UtenteDAO {
    /**
     * Aggiungi corsa.
     */
    public void aggiungiCorsa(/*Attributi di corsaRegolare*/);

    /**
     * Modifica corsa regolare.
     */
    public void modificaCorsaRegolare(/*Attributi di corsaRegolare*/);

    /**
     * Modifica corsa specifica.
     */
    public void modificaCorsaSpecifica(/*Attributi di corsaRegolare*/);

    /**
     * Cancella corsa regolare.
     *
     * @param idCorsa the id corsa
     */
    public void cancellaCorsaRegolare(/*Attributi di corsaRegolare*/int idCorsa);

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
    public void aggiungeNatante(/*Attributi di Natante*/);

    /**
     * Rimuovi natante.
     *
     * @param nomeNatante the nome natante
     */
    public void rimuoviNatante(/*Attributi di Natante*/String nomeNatante);
}
