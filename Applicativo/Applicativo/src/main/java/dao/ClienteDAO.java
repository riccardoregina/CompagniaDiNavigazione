package dao;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * L'interfaccia ClienteDAO
 */
public interface ClienteDAO extends UtenteDAO {
    /**
     * Registra un nuovo cliente sul DB. Se non vi riesce, lancia una eccezione.
     *
     * @param nome    il nome del nuovo cliente
     * @param cognome il cognome del nuovo cliente
     * @param login   la login del nuovo cliente
     * @param pwd     la password del nuovo cliente
     * @throws SQLException
     */
    void siRegistra(String nome, String cognome, String login, String pwd) throws SQLException;
    /**
     * Effettua il fetch delle generalita' del cliente.
     *
     * @param login   la login
     * @param nome    output - il nome
     * @param cognome output - il cognome
     */
    void fetchCliente(String login, ArrayList<String> nome, ArrayList<String> cognome);
    /**
     * Effettua il fetch dei veicoli del cliente.
     *
     * @param login        la login
     * @param veicoliTarga output - le targhe dei veicoli
     * @param veicoliTipo  output - i tipi dei veicoli
     */
    void fetchVeicoliCliente(String login, ArrayList<String> veicoliTarga, ArrayList<String> veicoliTipo);
    /**
     * Effettua il fetch delle compagnie.
     *
     * @param login         output - le login delle compagnie
     * @param nomeCompagnia output - i nomi delle compagnie
     * @param sitoWeb       output - i siti web delle compagnie
     */
    void fetchCompagnie(ArrayList<String> login, ArrayList<String> nomeCompagnia, ArrayList<String> sitoWeb);
    /**
     * Effettua il fetch dei contatti di tutte le compagnie del DB.
     *
     * @param compagniaSocial output - le compagnie a cui sono legati i profili social
     * @param nomeSocial output - i nomi dei social
     * @param tagSocial output - i tag nei social
     * @param compagniaEmail output - le compagnie a cui sono legati gli indirizzi email
     * @param indirizzoEmail output - gli indirizzi email
     * @param compagniaTelefono output - le compagnie a cui sono legati i recapiti telefonici
     * @param numeroTelefono output - i recapiti telefonici
     */
    void fetchContattiCompagnie(ArrayList<String> compagniaSocial, ArrayList<String> nomeSocial, ArrayList<String> tagSocial, ArrayList<String> compagniaEmail, ArrayList<String> indirizzoEmail, ArrayList<String> compagniaTelefono, ArrayList<String> numeroTelefono);
    /**
     * Effettua il fetch di tutti i porti del DB.
     *
     * @param idPorto        output - gli id dei porti
     * @param comuni         output - i comuni dei porti
     * @param indirizzi      output - gli indirizzi dei porti
     * @param numeriTelefono output - i recapiti telefonici dei porti
     */
    void fetchPorti(ArrayList<Integer> idPorto, ArrayList<String> comuni, ArrayList<String> indirizzi, ArrayList<String> numeriTelefono);
    /**
     * Effettua il fetch di tutti i natanti del DB.
     *
     * @param compagnia          output - le compagnie che posseggono i natanti
     * @param nome               output - i nomi dei natanti
     * @param capienzaPasseggeri output - le capienze di passeggeri dei natanti
     * @param capienzaVeicoli    output - le capienze di veicoli dei natanti
     * @param tipo               output - i tipi dei natanti
     */
    void fetchNatanti(ArrayList<String> compagnia, ArrayList<String> nome, ArrayList<Integer> capienzaPasseggeri, ArrayList<Integer> capienzaVeicoli, ArrayList<String> tipo);
    /**
     * Effettua il fetch dei periodi di attivita delle corse
     *
     * @param idPeriodo  output - gli id dei periodi
     * @param dataInizio output - le date di inizio dei periodi
     * @param dataFine   output - le date di fine dei periodi
     * @param giorni     output - i giorni di attivita' dei periodi
     * @param corsa      output - le corse attive nei periodi
     * @param compagnia  output - le compagnie che erogano le corse
     */
    void fetchPeriodiAttivitaCorse(ArrayList<Integer> idPeriodo, ArrayList<LocalDate> dataInizio, ArrayList<LocalDate> dataFine, ArrayList<String> giorni, ArrayList<Integer> corsa, ArrayList<String> compagnia);
    /**
     * Effettua il fetch di tutte le corse regolari del DB.
     *
     * @param idCorsa         output - gli id delle corse
     * @param idPortoPartenza output - gli id dei porti di partenza
     * @param idPortoArrivo   output - gli id dei porti di arrivo
     * @param orarioPartenza  output - gli orari di partenza
     * @param orarioArrivo    output - gli orari di arrivo
     * @param costoIntero     output - i costi dei biglietti interi
     * @param scontoRidotto   output - le percentuali di sconto per i biglietti ridotti
     * @param costoBagaglio   output - i costi aggiuntivi per il bagaglio
     * @param costoPrevendita output - i costi aggiuntivi per la prevendita
     * @param costoVeicolo    output - i costi aggiuntivi per il veicolo
     * @param compagniaCorsa  output - le compagnie che erogano le corse
     * @param nomeNatante     output - i nomi dei natanti impiegati nelle corse
     * @param corsaSup        output - le corse superiori delle corse
     */
    void fetchCorseRegolari(ArrayList<Integer> idCorsa, ArrayList<Integer> idPortoPartenza, ArrayList<Integer> idPortoArrivo, ArrayList<LocalTime> orarioPartenza, ArrayList<LocalTime> orarioArrivo, ArrayList<Float> costoIntero, ArrayList<Float> scontoRidotto, ArrayList<Float> costoBagaglio, ArrayList<Float> costoPrevendita, ArrayList<Float> costoVeicolo, ArrayList<String> compagniaCorsa, ArrayList<String> nomeNatante, ArrayList<Integer> corsaSup);
    /**
     * Effettua il fetch delle corse specifiche del DB.
     *
     * @param compagniaCorsaS output - le compagnie che erogano le corse
     * @param corsaRegolare   output - le corse regolari che generano le corse specifiche
     * @param data            output - le date delle corse
     * @param postiDispPass   output - i posti disponibili per passeggeri delle corse
     * @param postiDispVei    output - i posti disponibile per veicoli delle corse
     * @param minutiRitardo   output - i minuti di ritardo delle corse
     * @param cancellata      output - valori booleani che indicano se le corse sono state cancellate o meno
     */
    void fetchCorseSpecifiche(ArrayList<String> compagniaCorsaS, ArrayList<Integer> corsaRegolare, ArrayList<LocalDate> data, ArrayList<Integer> postiDispPass, ArrayList<Integer> postiDispVei, ArrayList<Integer> minutiRitardo, ArrayList<Boolean> cancellata);
    /**
     * Effettua il fetch dei biglietti acquistati dal cliente dal DB.
     *
     * @param login         la login del cliente
     * @param idBiglietto   gli id dei biglietti
     * @param idCorsa       gli id delle corse
     * @param dataCorsa     le date delle corse
     * @param targaVeicolo  le targhe dei veicoli
     * @param prevendita    le prevendite dei biglietti
     * @param bagaglio      valori booleani che indicano se il cliente ha pagato un costo aggiuntivo per un bagaglio o meno
     * @param prezzo        il prezzo di acquisto
     * @param dataAcquisto  la data di acquisto
     * @param etaPasseggero l'eta del passeggero
     */
    void fetchBigliettiCliente(String login, ArrayList<Integer> idBiglietto, ArrayList<Integer> idCorsa, ArrayList<LocalDate> dataCorsa, ArrayList<String> targaVeicolo, ArrayList<Boolean> prevendita, ArrayList<Boolean> bagaglio, ArrayList<Float> prezzo, ArrayList<LocalDate> dataAcquisto, ArrayList<Integer> etaPasseggero);
    /**
     * Registra l'acquisto di un biglietto da parte del cliente.
     *
     * @param idCorsa       l'id della corsa
     * @param data          la data della corsa
     * @param loginCliente  la login del cliente
     * @param targaVeicolo  la targa del veicolo
     * @param prevendita    un valore booleano che indica se il cliente sta pagando la prevendita o meno
     * @param bagaglio      un valore booleano che indica se il cliente sta pagando per un bagaglio o meno
     * @param prezzo        il prezzo di acquisto
     * @param dataAcquisto  la data di acquisto
     * @param etaPasseggero l'eta del passeggero
     * @param idBiglietto   output - l'id del biglietto assegnato dal DB
     */
    void acquistaBiglietto(int idCorsa, LocalDate data, String loginCliente, String targaVeicolo, boolean prevendita, boolean bagaglio, float prezzo, LocalDate dataAcquisto, int etaPasseggero, AtomicInteger idBiglietto) throws SQLException;
    /**
     * Aggiunge un veicolo del cliente al DB.
     *
     * @param targa             la targa del veicolo
     * @param tipo              il tipo di veicolo
     * @param loginProprietario la login del cliente
     */
    void aggiungeVeicolo(String tipo, String targa, String loginProprietario) throws SQLException;
    /**
     * Rimuove un veicolo del cliente dal DB.
     *
     * @param targa
     * @param login
     * @throws SQLException
     */
    void rimuoveVeicolo(String targa, String login) throws SQLException;
}
