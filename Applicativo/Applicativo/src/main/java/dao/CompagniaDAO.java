package dao;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * L'interfaccia CompagniaDAO
 */
public interface CompagniaDAO extends UtenteDAO {
    /**
     * Effettua il fetch dei dati della compagnia sul DB.
     *
     * @param loginCompagnia la login
     * @param nome           output - il nome della compagnia
     * @param telefono       output - i telefoni della compagnia
     * @param email          output - gli indirizzi email della compagnia
     * @param nomeSocial     output - i nomi dei social
     * @param tagSocial      output - i tag della compagnia nei social
     * @param sitoWeb        output - il sito web della compagnia
     */
    void fetchCompagnia(String loginCompagnia, ArrayList<String> nome, ArrayList<String> telefono, ArrayList<String> email, ArrayList<String> nomeSocial, ArrayList<String> tagSocial, ArrayList<String> sitoWeb);
    /**
     * Effettua il fetch di tutti i porti presenti sul DB.
     *
     * @param idPorto        output - gli id dei porti
     * @param comuni         output - i comuni dei porti
     * @param indirizzi      output - gli indirizzi dei porti
     * @param numeriTelefono output - i numeri di telefono dei porti
     */
    void fetchPorti(ArrayList<Integer> idPorto, ArrayList<String> comuni, ArrayList<String> indirizzi, ArrayList<String> numeriTelefono);
    /**
     * Effettua il fetch dei natanti posseduti dalla compagnia.
     *
     * @param loginCompagnia     la login
     * @param nomeNatante        output - i nomi dei natanti
     * @param capienzaPasseggeri output - le capienze di passeggeri
     * @param capienzaVeicoli    output - le capienze di veicoli
     * @param tipo               output - i tipi dei natanti
     */
    void fetchNatantiCompagnia(String loginCompagnia, ArrayList<String> nomeNatante, ArrayList<Integer> capienzaPasseggeri, ArrayList<Integer> capienzaVeicoli, ArrayList<String> tipo);
    /**
     * Effettua il fetch delle corse regolari erogate dalla compagnia.
     *
     * @param loginCompagnia  the login compagnia
     * @param idCorsa         output - gli id delle corse
     * @param idPortoPartenza output - gli id dei porti di partenza
     * @param idPortoArrivo   output - gli id dei porti di arrivo
     * @param orarioPartenza  output - gli orari di partenza
     * @param orarioArrivo    output - gli orari di arrivo
     * @param costoIntero     output - i costi del biglietto intero
     * @param scontoRidotto   output - gli sconti percentuali da applicare in caso di biglietto ridotto
     * @param costoBagaglio   output - i costi aggiuntivi per il bagaglio
     * @param costoPrevendita output - i costi aggiuntivi per la prevendita
     * @param costoVeicolo    output - i costi aggiuntivi per il veicolo
     * @param nomeNatante     output - i nomi dei natanti impiegati nelle corse
     * @param corsaSup        output - le corse regolari di cui le corse sono sotto-corse
     */
    void fetchCorseRegolari(String loginCompagnia, ArrayList<Integer> idCorsa, ArrayList<Integer> idPortoPartenza, ArrayList<Integer> idPortoArrivo, ArrayList<LocalTime> orarioPartenza, ArrayList<LocalTime> orarioArrivo, ArrayList<Float> costoIntero, ArrayList<Float> scontoRidotto, ArrayList<Float> costoBagaglio, ArrayList<Float> costoPrevendita, ArrayList<Float> costoVeicolo, ArrayList<String> nomeNatante, ArrayList<Integer> corsaSup);
    /**
     * Effettua il fetch dei periodi di attivita' delle corse regolari erogate dalla compagnia.
     *
     * @param loginCompagnia la login
     * @param idPeriodo      output - gli id dei periodi
     * @param dataInizio     output - le date di inizio
     * @param dataFine       output - le date di fine
     * @param giorni         output - i giorni di attivita'
     * @param corsa          output - le corse a cui sono legati i periodi
     */
    void fetchPeriodiAttivitaCorse(String loginCompagnia, ArrayList<Integer> idPeriodo, ArrayList<LocalDate> dataInizio, ArrayList<LocalDate> dataFine, ArrayList<String> giorni, ArrayList<Integer> corsa);
    /**
     * Effettua il fetch delle corse specifiche erogate dalla compagnia.
     *
     * @param loginCompagnia the login compagnia
     * @param corsaRegolare  output - le corse regolari che hanno generato le corse specifiche
     * @param data           output - le date delle corse specifiche
     * @param postiDispPass  output - i posti disponibili per i passeggeri nelle corse specifiche
     * @param postiDispVei   output - i posti disponibili per i veicoli nelle corse specifiche
     * @param minutiRitardo  output - i minuti di ritardo
     * @param cancellata     output - valore booleano che indica se la corsa e' stata cancellata o meno
     */
    void fetchCorseSpecifiche(String loginCompagnia, ArrayList<Integer> corsaRegolare, ArrayList<LocalDate> data, ArrayList<Integer> postiDispPass, ArrayList<Integer> postiDispVei, ArrayList<Integer> minutiRitardo, ArrayList<Boolean> cancellata);
    /**
     * Prova ad aggiungere una corsa nel DB. Se riesce, restituisce l'id
     * della tupla inserita, altrimenti viene lanciata un'eccezione.
     *
     * @param idPortoPartenza l'id del porto di partenza
     * @param idPortoArrivo l'id del porto di arrivo
     * @param orarioPartenza l'orario di partenza
     * @param orarioArrivo l'orario di arrivo
     * @param costoIntero il costo del biglietto intero
     * @param scontoRidotto lo sconto percentuale per il biglietto ridotto
     * @param costoBagaglio il costo aggiuntivo per il bagaglio
     * @param costoPrevendita il costo aggiuntivo per la prevendita
     * @param costoVeicolo il costo aggiunto per il veicolo
     * @param nomeNatante il nome del natante della corsa
     * @param idCorsa - output - l'id assegnato dal DB alla corsa inserita.
     *
     * @throws SQLException
     */
    void aggiungeCorsa(int idPortoPartenza, int idPortoArrivo, LocalTime orarioPartenza, LocalTime orarioArrivo, float costoIntero, float scontoRidotto, float costoBagaglio, float costoPrevendita, float costoVeicolo, String loginCompagnia, String nomeNatante, AtomicInteger idCorsa) throws SQLException;
    /**
     * Cancella una corsa regolare dal DB.
     *
     * @param idCorsa l'id della corsa da cancellare
     */
    void cancellaCorsaRegolare(int idCorsa);
    /**
     * Cancella una corsa specifica dal DB.
     *
     * @param idCorsa l'id della corsa regolare che ha generato la corsa specifica da eliminare
     * @param data    la data della corsa specifica da eliminare
     */
    void cancellaCorsaSpecifica(int idCorsa, Date data) throws SQLException;
    /**
     * Segnala un ritardo per una corsa specifica
     *
     * @param idCorsa l'id della corsa regolare che ha generato la corsa specifica
     * @param data    la data della corsa specifica
     * @param ritardo il valore in minuti del ritardo da segnalare
     */
    void segnalaRitardo(int idCorsa, Date data, int ritardo) throws SQLException;
    /**
     * Aggiunge un natante della compagnia al DB.
     *
     * @param loginCompagnia la login
     * @param nomeNatante il nome del natante da aggiungere
     * @param capienzaPasseggeri la capienza di passeggeri del natante
     * @param capienzaVeicoli la capienza di veicoli del natante
     * @param tipo il tipo del natante
     * @throws SQLException
     */
    void aggiungeNatante(String loginCompagnia, String nomeNatante, int capienzaPasseggeri, int capienzaVeicoli, String tipo) throws Exception;
    /**
     * Rimuove un natante della compagnia dal DB.
     *
     * @param nomeNatante il nome del natante da eliminare
     */
    void rimuoveNatante(String nomeNatante);
    /**
     * Aggiunge sul DB uno scalo per la corsa identificata dall'idCorsa in ingresso.
     *
     * @param idCorsa          l'id della corsa per cui si aggiunge lo scalo
     * @param idPortoScalo     il porto di scalo
     * @param orarioAttracco   l'orario di arrivo al porto di scalo
     * @param orarioRipartenza l'orario di ripartenza dal porto di scalo
     * @throws SQLException
     */
    void aggiungeScalo(int idCorsa, Integer idPortoScalo, LocalTime orarioAttracco, LocalTime orarioRipartenza) throws SQLException;
    /**
     * Aggiunge un periodo di attivita', ma senza attaccarlo ad una corsa regolare.
     *
     * @param giorni        i giorni di attivita'
     * @param inizioPeriodo l'inizio del periodo
     * @param finePeriodo   la fine del periodo
     * @param idPeriodo     output - l'id assegnato dal DB al periodo inserito
     * @throws SQLException
     */
    void aggiungePeriodo(String giorni, LocalDate inizioPeriodo, LocalDate finePeriodo, AtomicInteger idPeriodo) throws SQLException;
    /**
     * Attiva una corsa regolare in un periodo
     *
     * @param idCorsa   l'id della corsa
     * @param idPeriodo l'id del periodo
     * @throws SQLException
     */
    void attivaCorsaInPeriodo(int idCorsa, int idPeriodo) throws SQLException;
    /**
     * Modifica l'orario di partenza di una corsa regolare.
     *
     * @param idCorsa             l'id della corsa
     * @param nuovoOrarioPartenza il nuovo orario di partenza
     * @throws SQLException
     */
    void modificaOrarioPartenza(int idCorsa, LocalTime nuovoOrarioPartenza) throws SQLException;
    /**
     * Modifica l'orario di arrivo della corsa.
     *
     * @param idCorsa           l'id della corsa
     * @param nuovoOrarioArrivo il nuovo orario di arrivo
     * @throws SQLException
     */
    void modificaOrarioArrivo(int idCorsa, LocalTime nuovoOrarioArrivo) throws SQLException;
    /**
     * Modifica il costo del biglietto intero della corsa.
     *
     * @param idCorsa          l'id della corsa
     * @param nuovoCostoIntero il nuovo costo intero
     * @throws SQLException
     */
    void modificaCostoIntero(int idCorsa, float nuovoCostoIntero) throws SQLException;
    /**
     * Modifica lo sconto percentuale per il biglietto ridotto.
     *
     * @param idCorsa            l'id della corsa
     * @param nuovoScontoRidotto il nuovo sconto ridotto
     * @throws SQLException
     */
    void modificaScontoRidotto(int idCorsa, float nuovoScontoRidotto) throws SQLException;
    /**
     * Modifica il costo aggiuntivo per il bagaglio.
     *
     * @param idCorsa            l'id della corsa
     * @param nuovoCostoBagaglio il nuovo costo per il bagaglio
     * @throws SQLException
     */
    void modificaCostoBagaglio(int idCorsa, float nuovoCostoBagaglio) throws SQLException;
    /**
     * Modifica il costo aggiuntivo per la prevendita.
     *
     * @param idCorsa              l'id della corsa
     * @param nuovoCostoPrevendita il nuovo costo prevendita
     * @throws SQLException
     */
    void modificaCostoPrevendita(int idCorsa, float nuovoCostoPrevendita) throws SQLException;
    /**
     * Modifica il costo aggiuntivo per il veicolo.
     *
     * @param idCorsa           l'id della corsa
     * @param nuovoCostoVeicolo il nuovo costo per il veicolo
     * @throws SQLException the sql exception
     */
    void modificaCostoVeicolo(int idCorsa, float nuovoCostoVeicolo) throws SQLException;
    /**
     * Elimina dal DB un periodo di attivita per una corsa.
     *
     * @param idCorsa   l'id della corsa
     * @param idPeriodo l'id del periodo
     * @throws SQLException
     */
    void eliminaPeriodoAttivitaPerCorsa(int idCorsa, int idPeriodo) throws SQLException;
    /**
     * Calcola gli incassi di una corsa in un periodo.
     *
     * @param idCorsa       l'id della corsa
     * @param inizioPeriodo l'inizio del periodo
     * @param finePeriodo   la fine del periodo
     * @return un float corrispondente all'incasso della corsa nel periodo
     * @throws SQLException
     */
    float calcolaIncassiCorsaInPeriodo(int idCorsa, LocalDate inizioPeriodo, LocalDate finePeriodo) throws SQLException;
    /**
     * Aggiorna i posti disponibili nelle sottocorse.
     *
     * @param idCorsa l'id della corsa
     * @throws SQLException
     */
    void aggiornaPostiDisponibiliSottocorse(int idCorsa) throws SQLException;
    /**
     * Aggiunge un profilo social per la compagnia.
     *
     * @param nomeSocial    il nome del social
     * @param tag           il tag
     * @param nomeCompagnia il nome della compagnia
     * @throws SQLException
     */
    void aggiungiSocial(String nomeSocial, String tag, String nomeCompagnia) throws SQLException;
    /**
     * Elimina un profilo social della compagnia.
     *
     * @param nomeSocial il nome del social
     * @param tag        il tag nel social
     * @throws SQLException
     */
    void eliminaSocial(String nomeSocial, String tag) throws SQLException;
    /**
     * Aggiunge un indirizzo email della compagnia.
     *
     * @param email         l'email
     * @param nomeCompagnia il nome della compagnia
     * @throws SQLException
     */
    void aggiungiEmail(String email, String nomeCompagnia) throws SQLException;
    /**
     * Elimina un indirizzo email della compagnia.
     *
     * @param email l'email
     * @throws SQLException
     */
    void eliminaEmail(String email) throws SQLException;
    /**
     * Aggiunge un recapito telefonico della compagnia.
     *
     * @param telefono      il telefono
     * @param nomeCompagnia il nome della compagnia
     * @throws SQLException
     */
    void aggiungiTelefono(String telefono, String nomeCompagnia) throws SQLException;
    /**
     * Elimina un recapito telefonico della compagnia
     *
     * @param telefono il telefono
     * @throws SQLException
     */
    void eliminaTelefono(String telefono) throws SQLException;
    /**
     * Modifica il sito web della compagnia.
     *
     * @param sito          il sito web
     * @param nomeCompagnia il nome della compagnia
     * @throws SQLException
     */
    void modificaSitoWeb(String sito, String nomeCompagnia) throws SQLException;
}
