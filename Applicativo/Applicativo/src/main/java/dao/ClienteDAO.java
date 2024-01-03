package dao;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * The interface Cliente dao.
 */
public interface ClienteDAO extends UtenteDAO {
    public void fetchCliente(String login, String nome, String cognome);
    public void fetchVeicoliCliente(String login, ArrayList<String> veicoliTarga, ArrayList<String> veicoliTipo);
    public void fetchCompagnie(ArrayList<String> login, ArrayList<String> nomeCompagnia, ArrayList<String> sitoWeb, ArrayList<String> compagniaSocial, ArrayList<String> nomeSocial, ArrayList<String> tagSocial, ArrayList<String> compagniaEmail, ArrayList<String> indirizzoEmail, ArrayList<String> compagniaTelefono, ArrayList<String> numeroTelefono);
    public void fetchContattiCompagnie(ArrayList<String> compagniaSocial, ArrayList<String> nomeSocial, ArrayList<String> tagSocial, ArrayList<String> compagniaEmail, ArrayList<String> indirizzoEmail, ArrayList<String> compagniaTelefono, ArrayList<String> numeroTelefono);
    public void fetchPorti(ArrayList<Integer> idPorto, ArrayList<String> comuni, ArrayList<String> indirizzi, ArrayList<String> numeriTelefono);
    public void fetchNatanti(ArrayList<String> compagnia, ArrayList<String> nome, ArrayList<Integer> capienzaPasseggeri, ArrayList<Integer> capienzaVeicoli, ArrayList<String> tipo);
    public void fetchPeriodiAttivitaCorse(ArrayList<Integer> idPeriodo ,ArrayList<LocalDate> dataInizio, ArrayList<LocalDate> dataFine, ArrayList<String> giorni, ArrayList<Integer> corsa, ArrayList<String> compagnia);
    public void fetchCorseRegolari(ArrayList<Integer> idCorsa, ArrayList<Integer> idPortoPartenza, ArrayList<Integer> idPortoArrivo, ArrayList<LocalTime> orarioPartenza, ArrayList<LocalTime> orarioArrivo, ArrayList<Float> costoIntero, ArrayList<Float> scontoRidotto, ArrayList<Float> costoBagaglio, ArrayList<Float> costoPrevendita, ArrayList<Float> costoVeicolo, ArrayList<String> compagniaCorsa, ArrayList<String> nomeNatante, ArrayList<Integer> corsaSup);
    public void fetchCorseSpecifiche(ArrayList<String> compagniaCorsaS, ArrayList<Integer> corsaRegolare, ArrayList<LocalDate> data, ArrayList<Integer> postiDispPass, ArrayList<Integer> postiDispVei, ArrayList<Integer> minutiRitardo, ArrayList<Boolean> cancellata);
    public void fetchBigliettiCliente(String login, ArrayList<Integer> idBiglietto, ArrayList<Integer> idCorsa, ArrayList<LocalDate> dataCorsa, ArrayList<String> targaVeicolo, ArrayList<Boolean> prevendita, ArrayList<Boolean> bagaglio, ArrayList<Float> prezzo, ArrayList<LocalDate> dataAcquisto, ArrayList<Integer> etaPasseggero);
    public void acquistaBiglietto(int idCorsa, LocalDate data, String loginCliente, String targaVeicolo, boolean prevendita, boolean bagaglio, float prezzo, LocalDate dataAcquisto, int etaPasseggero, Integer idBiglietto) throws SQLException;
    public void aggiungeVeicolo(String tipo, String targa, String loginProprietario) throws SQLException;
}
