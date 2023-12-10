package dao;

import java.time.LocalDate;

/**
 * The interface Cliente dao.
 */
public interface ClienteDAO extends UtenteDAO {
    /**
     * Acquista biglietto.
     *
     * @param idCorsa       the id corsa
     * @param data          the data
     * @param loginCliente  the login cliente
     * @param targaVeicolo  the targa veicolo
     * @param prevendita    the prevendita
     * @param bagaglio      the bagaglio
     * @param prezzo        the prezzo
     * @param dataAcquisto  the data acquisto
     * @param etaPasseggero the eta passeggero
     */
    public void acquistaBiglietto(int idCorsa, LocalDate data, String loginCliente, String targaVeicolo, boolean prevendita, boolean bagaglio, float prezzo, LocalDate dataAcquisto, int etaPasseggero);

    /**
     * Aggiungi veicolo.
     *
     * @param targa             the targa
     * @param tipo              the tipo
     * @param loginProprietario the login proprietario
     */
    public void aggiungeVeicolo(String targa, String tipo, String loginProprietario);
}
