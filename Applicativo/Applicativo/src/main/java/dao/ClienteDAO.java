package dao;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

/**
 * The interface Cliente dao.
 */
public interface ClienteDAO extends UtenteDAO {

    /**
     * Cerca corse.
     *
     * @param data              the data
     * @param idPortoPartenza   the id porto partenza
     * @param idPortoArrivo     the id porto arrivo
     * @param idCorsa           the id corsa
     * @param nomePortoPartenza the nome porto partenza
     * @param nomePortoArrivo   the nome porto arrivo
     * @param orarioPartenza    the orario partenza
     * @param orarioArrivo      the orario arrivo
     * @param costoIntero       the costo intero
     * @param scontoRidotto     the sconto ridotto
     * @param costoBagaglio     the costo bagaglio
     * @param costoPrevendita   the costo prevendita
     * @param costoVeicolo      the costo veicolo
     * @param nomeCompagnia     the nome compagnia
     * @param nomeNatante       the nome natante
     * @param tipoNatante       the tipo natante
     */
    public void cercaCorse(Date data, int idPortoPartenza, int idPortoArrivo, ArrayList<Integer> idCorsa, ArrayList<String> nomePortoPartenza, ArrayList<String> nomePortoArrivo, ArrayList<LocalTime> orarioPartenza, ArrayList<LocalTime> orarioArrivo, ArrayList<Float> costoIntero, ArrayList<Float> scontoRidotto, ArrayList<Float> costoBagaglio, ArrayList<Float> costoPrevendita, ArrayList<Float> costoVeicolo, ArrayList<String> nomeCompagnia, ArrayList<String> nomeNatante, ArrayList<String> tipoNatante);

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
    public void acquistaBiglietto(int idCorsa, Date data, String loginCliente, String targaVeicolo, boolean prevendita, boolean bagaglio, float prezzo, Date dataAcquisto, int etaPasseggero);

    /**
     * Aggiungi veicolo.
     *
     * @param targa             the targa
     * @param tipo              the tipo
     * @param loginProprietario the login proprietario
     */
    public void aggiungiVeicolo(String targa, String tipo, String loginProprietario);
}
