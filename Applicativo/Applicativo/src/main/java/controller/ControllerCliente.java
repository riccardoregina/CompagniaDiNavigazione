package controller;

import model.*;
import postgresqlDAO.ClienteDAO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

import unnamed.Pair;

/**
 * La classe ControllerCliente si occupa di:
 *  + ricevere richieste da un interfaccia
 *  + provvedere a soddisfare tali richieste, che possono interessare: il Model, il DB, o entrambi.
 * Questa classe contiene i dati del Model presenti nei membr: cliente, porti, compagnie, corse.
 */
public class ControllerCliente {
    Logger logger = Logger.getLogger(this.getClass().getName());
    private Cliente cliente;
    private HashMap<Integer, Porto> porti;
    private HashMap<String, Compagnia> compagnie;
    private HashMap<Pair, CorsaSpecifica> corse;

    /**
     * Istanzia un nuovo ControllerCliente.
     */
    public ControllerCliente() {
        porti = new HashMap<>();
        compagnie = new HashMap<>();
        corse = new HashMap<>();
    }

    /**
     * Prende login e password dalla GUI,
     * richiede una connessione al DB,
     * se il cliente esiste nel DB,
     * crea il contesto nel model per l'esecuzione del programma.
     *
     * @param login    la login
     * @param password la password
     * @throws SQLException
     */
    public void clienteAccede(String login, String password) throws SQLException {
        try {
            ClienteDAO clienteDAO = new ClienteDAO();
            clienteDAO.accede(login, password);

            buildModel(login, password);
        } catch(SQLException e) {
            String message = e.getMessage();
            if (message.equals("Impossibile connettersi al server.")) {
                logger.log(Level.SEVERE, message);
                throw e;
            } else if (message.equals("Credenziali errate / utente non esistente.")) {
                logger.log(Level.FINE, message);
                throw e;
            }
        }
    }

    /**
     * Comanda il fetch del cliente al DB e riempie il model con i dati ottenuti.
     *
     * @param login    la login
     * @param password la password
     * @throws SQLException
     */
    public void buildCliente(String login, String password) throws SQLException {
        try {
            ClienteDAO clienteDAO = new ClienteDAO();

            ArrayList<String> nome = new ArrayList<>();
            ArrayList<String> cognome = new ArrayList<>();
            clienteDAO.fetchCliente(login, nome, cognome);

            cliente = new Cliente(login, password, nome.getFirst(), cognome.getFirst());

        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
            throw e;
        }
    }

    /**
     * Comanda il fetch dei veicoli del cliente al DB e riempie il model con i dati ottenuti.
     *
     * @param login la login
     * @throws SQLException
     */
    public void buildVeicoli(String login) throws SQLException {
        try {
            ClienteDAO clienteDAO = new ClienteDAO();
            ArrayList<String> veicoliTipo = new ArrayList<>();
            ArrayList<String> veicoliTarga = new ArrayList<>();
            clienteDAO.fetchVeicoliCliente(login, veicoliTarga, veicoliTipo);

            for (int i = 0; i < veicoliTarga.size(); i++) {
                cliente.addVeicolo(new Veicolo(veicoliTipo.get(i), veicoliTarga.get(i)));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
            throw e;
        }
    }

    /**
     * Costruisce l'ambiente dell'applicativo a partire dal fetching del DB
     */
    private void buildModel(String login, String password) throws SQLException {
        try {
            buildCliente(login, password);
            buildVeicoli(login);
            buildCompagnie();
            buildPorti();
            buildCorse();
            buildBigliettiAcquistati();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
            throw e;
        }
    }

    /**
     * Comanda il fetch dei porti al DB e riempie il model con i dati ottenuti.
     *
     * @throws SQLException
     */
    public void buildPorti() throws SQLException {
        try {
            ClienteDAO clienteDAO = new ClienteDAO();
            ArrayList<Integer> idPorto = new ArrayList<>();
            ArrayList<String> comuni = new ArrayList<>();
            ArrayList<String> indirizzi = new ArrayList<>();
            ArrayList<String> numeriTelefono = new ArrayList<>();
            clienteDAO.fetchPorti(idPorto, comuni, indirizzi, numeriTelefono);
            for (int i = 0; i < comuni.size(); i++) {
                porti.put(idPorto.get(i), new Porto(idPorto.get(i), comuni.get(i), indirizzi.get(i), numeriTelefono.get(i)));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
            throw e;
        }
    }

    /**
     * Comanda il fetch delle compagnie al DB e riempie il model con i dati ottenuti.
     *
     * @throws SQLException
     */
    public void buildCompagnie() throws SQLException {
        try {
            ClienteDAO clienteDAO = new ClienteDAO();
            //password verra' settata a null nel model per motivi di sicurezza
            ArrayList<String> login = new ArrayList<>();
            ArrayList<String> nomeCompagnia = new ArrayList<>();
            ArrayList<String> sitoWeb = new ArrayList<>();
            //Elementi dei Social
            ArrayList<String> compagniaSocial = new ArrayList<>();
            ArrayList<String> nomeSocial = new ArrayList<>();
            ArrayList<String> tagSocial = new ArrayList<>();
            //Elementi delle Email
            ArrayList<String> compagniaEmail = new ArrayList<>();
            ArrayList<String> indirizzoEmail = new ArrayList<>();
            //Elementi dei telefoni
            ArrayList<String> compagniaTelefono = new ArrayList<>();
            ArrayList<String> numeroTelefono = new ArrayList<>();
            clienteDAO.fetchCompagnie(login, nomeCompagnia, sitoWeb, compagniaSocial, nomeSocial, tagSocial, compagniaEmail, indirizzoEmail, compagniaTelefono, numeroTelefono);
            for (int i = 0; i < login.size(); i++) {
                Compagnia c = new Compagnia(login.get(i), null, nomeCompagnia.get(i));
                c.setSitoWeb(sitoWeb.get(i));
                compagnie.put(login.get(i), c);
            }

            //Aggiungo a ciascuna compagnia i suoi contatti
            clienteDAO = new ClienteDAO();
            clienteDAO.fetchContattiCompagnie(compagniaSocial, nomeSocial, tagSocial, compagniaEmail, indirizzoEmail, compagniaTelefono, numeroTelefono);
            for (int i = 0; i < tagSocial.size(); i++) {
                compagnie.get(compagniaSocial.get(i)).addAccount(new AccountSocial(compagnie.get(compagniaSocial.get(i)), nomeSocial.get(i), tagSocial.get(i)));
            }
            for (int i = 0; i < indirizzoEmail.size(); i++) {
                compagnie.get(compagniaEmail.get(i)).addEmail(indirizzoEmail.get(i));
            }
            for (int i = 0; i < numeroTelefono.size(); i++) {
                compagnie.get(compagniaTelefono.get(i)).addTelefono(numeroTelefono.get(i));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
            throw e;
        }
    }

    /**
     * Questo metodo (l'ordine é dettato dalle dipendenze):
     * 1) costruisce i natanti e li assegna alle compagnie
     * 2) costruisce le corse regolari e le assegna alle compagnie
     * 3) assegna i periodi alle corse regolari
     * 4) costruisce le corse specifiche e popola la collezione 'corse' di questa classe
     *
     * @throws SQLException
     */
    public void buildCorse() throws SQLException {
        try {
            ClienteDAO clienteDAO = new ClienteDAO();
            //Elementi dei natanti
            ArrayList<String> compagniaNatante = new ArrayList<>();
            ArrayList<String> nome = new ArrayList<>();
            ArrayList<Integer> capienzaPasseggeri = new ArrayList<>();
            ArrayList<Integer> capienzaVeicoli = new ArrayList<>();
            ArrayList<String> tipo = new ArrayList<>();
            clienteDAO.fetchNatanti(compagniaNatante, nome, capienzaPasseggeri, capienzaVeicoli, tipo);
            for (int i = 0; i < nome.size(); i++) {
                compagnie.get(compagniaNatante.get(i)).addNatante(new Natante(compagnie.get(compagniaNatante.get(i)), nome.get(i), capienzaPasseggeri.get(i), capienzaVeicoli.get(i), tipo.get(i)));
            }

            //Elementi delle corse regolari
            ArrayList<Integer> idCorsa = new ArrayList<>();
            ArrayList<Integer> idPortoPartenza = new ArrayList<>();
            ArrayList<Integer> idPortoArrivo = new ArrayList<>();
            ArrayList<LocalTime> orarioPartenza = new ArrayList<>();
            ArrayList<LocalTime> orarioArrivo = new ArrayList<>();
            ArrayList<Float> costoIntero = new ArrayList<>();
            ArrayList<Float> scontoRidotto = new ArrayList<>();
            ArrayList<Float> costoBagaglio = new ArrayList<>();
            ArrayList<Float> costoPrevendita = new ArrayList<>();
            ArrayList<Float> costoVeicolo = new ArrayList<>();
            ArrayList<String> compagniaCorsa = new ArrayList<>();
            ArrayList<String> nomeNatante = new ArrayList<>();
            ArrayList<Integer> corsaSup = new ArrayList<>();
            clienteDAO = new ClienteDAO();
            clienteDAO.fetchCorseRegolari(idCorsa, idPortoPartenza, idPortoArrivo, orarioPartenza, orarioArrivo, costoIntero, scontoRidotto, costoBagaglio, costoPrevendita, costoVeicolo, compagniaCorsa, nomeNatante, corsaSup);
            for (int i = 0; i < costoIntero.size(); i++) {
                int id = idCorsa.get(i);
                Compagnia c = compagnie.get(compagniaCorsa.get(i));
                Natante n = c.getNatantiPosseduti().get(nomeNatante.get(i));
                Porto pPartenza = porti.get(idPortoPartenza.get(i));
                Porto pArrivo = porti.get(idPortoArrivo.get(i));
                LocalTime oraPartenza = orarioPartenza.get(i);
                LocalTime oraArrivo = orarioArrivo.get(i);
                float cIntero = costoIntero.get(i);
                float sRidotto = scontoRidotto.get(i);
                float cBagaglio = costoBagaglio.get(i);
                float cPrev = costoPrevendita.get(i);
                float cVei = costoVeicolo.get(i);
                CorsaRegolare crSup = c.getCorseErogate().get(corsaSup.get(i));
                c.addCorsaRegolare(new CorsaRegolare(id, c, n, pPartenza, pArrivo, oraPartenza, oraArrivo, cIntero, sRidotto, cBagaglio, cVei, cPrev, crSup));
            }

            //Elementi dei periodi
            HashMap<Integer, Periodo> periodi = new HashMap<>();
            ArrayList<String> compagnia = new ArrayList<>();
            ArrayList<Integer> corsa = new ArrayList<>();
            ArrayList<Integer> idPeriodo = new ArrayList<>();
            ArrayList<LocalDate> dataInizio = new ArrayList<>();
            ArrayList<LocalDate> dataFine = new ArrayList<>();
            ArrayList<String> giorni = new ArrayList<>();
            clienteDAO = new ClienteDAO();
            clienteDAO.fetchPeriodiAttivitaCorse(idPeriodo, dataInizio, dataFine, giorni, corsa, compagnia);
            //Assegno un periodo alla sua corsa.
            for (int i = 0; i < dataInizio.size(); i++) {
                Compagnia c = compagnie.get(compagnia.get(i));
                CorsaRegolare cr = c.getCorseErogate().get(corsa.get(i));
                cr.addPeriodoAttivita(new Periodo(idPeriodo.get(i), dataInizio.get(i), dataFine.get(i), giorni.get(i)));
            }

            //Elementi delle corseSpecifiche
            ArrayList<String> compagniaCorsaS = new ArrayList<>();
            ArrayList<Integer> corsaRegolare = new ArrayList<>();
            ArrayList<LocalDate> data = new ArrayList<>();
            ArrayList<Integer> postiDispPass = new ArrayList<>();
            ArrayList<Integer> postiDispVei = new ArrayList<>();
            ArrayList<Integer> minutiRitardo = new ArrayList<>();
            ArrayList<Boolean> cancellata = new ArrayList<>();
            clienteDAO = new ClienteDAO();
            clienteDAO.fetchCorseSpecifiche(compagniaCorsaS, corsaRegolare, data, postiDispPass, postiDispVei, minutiRitardo, cancellata);
            for (int i = 0; i < data.size(); i++) {
                Compagnia c = compagnie.get(compagniaCorsaS.get(i));
                CorsaRegolare cr = c.getCorseErogate().get(corsaRegolare.get(i));
                LocalDate d = data.get(i);
                int pPass = postiDispPass.get(i);
                int pVei = postiDispVei.get(i);
                int mRit = minutiRitardo.get(i);
                boolean canc = cancellata.get(i);
                Pair pair = new Pair(cr.getIdCorsa(), d);
                corse.put(pair, new CorsaSpecifica(cr, d, pPass, pVei, mRit, canc));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
            throw e;
        }
    }

    /**
     * Comanda al DB il fetch dei biglietti acquistati dal cliente e riempie il model con i dati ottenuti.
     *
     * @throws SQLException
     */
    public void buildBigliettiAcquistati() throws SQLException {
        try {
            ClienteDAO clienteDAO = new ClienteDAO();
            ArrayList<Integer> idBiglietto = new ArrayList<>();
            ArrayList<Integer> idCorsa = new ArrayList<>();
            ArrayList<LocalDate> dataCorsa = new ArrayList<>();
            ArrayList<String> targaVeicolo = new ArrayList<>();
            ArrayList<Boolean> prevendita = new ArrayList<>();
            ArrayList<Boolean> bagaglio = new ArrayList<>();
            ArrayList<Float> prezzo = new ArrayList<>();
            ArrayList<LocalDate> dataAcquisto = new ArrayList<>();
            ArrayList<Integer> etaPasseggero = new ArrayList<>();
            clienteDAO.fetchBigliettiCliente(cliente.getLogin(), idBiglietto, idCorsa, dataCorsa, targaVeicolo, prevendita, bagaglio, prezzo, dataAcquisto, etaPasseggero);
            for (int i = 0; i < idBiglietto.size(); i++) {
                //trovo la corsa specifica con id e Data
                CorsaSpecifica cs = corse.get(new Pair(idCorsa.get(i), dataCorsa.get(i)));
                Veicolo v = cliente.getVeicoliPosseduti().get(targaVeicolo.get(i));
                cliente.addBiglietto(new Biglietto(idBiglietto.get(i), cliente, cs, etaPasseggero.get(i), v, bagaglio.get(i), prevendita.get(i), prezzo.get(i), dataAcquisto.get(i)));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
            throw e;
        }
    }

    /**
     * Visualizza le corse, salvate nel model, che rispettano i filtri comandati dall'interfaccia.
     *
     * @param idPortoPartenzaSelezionato il porto di partenza selezionato
     * @param idPortoArrivoSelezionato   il porto di arrivo selezionato
     * @param dataSelezionata            la data selezionata
     * @param orarioMinimoPartenza       l'orario minimo di partenza
     * @param prezzoMax                  il prezzo massimo selezionato
     * @param tipoNatanteSelezionato     il tipo di natante selezionato
     * @param etaPasseggero              l'eta del passeggero per cui verra' acquistato il biglietto
     * @param veicolo                    un valore booleano: true se il cliente intende portare con se' un veicolo, false altrimenti
     * @param bagaglio                   un valore booleano: true se il cliente intende portare con se' un bagaglio, false altrimenti
     * @param idCorsa                    output - gli id delle corse trovate
     * @param nomeCompagnia              output - i nomi delle compagnie che erogano le corse trovate
     * @param data                       output - le date delle corse trovate
     * @param orePart                    output - le ore di partenza delle corse trovate
     * @param oreDest                    output - le ore di arrivo delle corse trovate
     * @param postiDispPass              output - i posti disponibili per passeggeri delle corse trovate
     * @param postiDispVei               output - i posti disponibili per veicoli delle corse trovate
     * @param minutiRitardo              output - i minuti di ritardo delle corse trovate
     * @param natanti                    output - i natanti impiegati nelle corse trovate
     * @param cancellata                 output - i valori booleani che indicano se le corse trovate sono state cancellate o meno
     * @param prezzo                     output - i prezzi calcolati secondo i parametri di ricerca per le corse trovate
     */
    public void visualizzaCorse(int idPortoPartenzaSelezionato, int idPortoArrivoSelezionato, LocalDate dataSelezionata, LocalTime orarioMinimoPartenza, Float prezzoMax, ArrayList<String> tipoNatanteSelezionato, int etaPasseggero, boolean veicolo, boolean bagaglio, ArrayList<Integer> idCorsa, ArrayList<String> nomeCompagnia, ArrayList<LocalDate> data, ArrayList<LocalTime> orePart, ArrayList<LocalTime> oreDest, ArrayList<Integer> postiDispPass, ArrayList<Integer> postiDispVei, ArrayList<Integer> minutiRitardo, ArrayList<String> natanti, ArrayList<Boolean> cancellata, ArrayList<Float> prezzo) {
        Porto portoPartenza = porti.get(idPortoPartenzaSelezionato);
        Porto portoArrivo = porti.get(idPortoArrivoSelezionato);

        //un iteratore per scorrere la HashMap
        for (Map.Entry<Pair, CorsaSpecifica> it : corse.entrySet()) {
            CorsaSpecifica cs = it.getValue();
            if (    cs.getCorsaRegolare().getPortoPartenza().equals(portoPartenza) &&
                    cs.getCorsaRegolare().getPortoArrivo().equals(portoArrivo) &&
                    ((cs.getData().equals(dataSelezionata) && (cs.getCorsaRegolare().getOrarioPartenza().equals(orarioMinimoPartenza) || cs.getCorsaRegolare().getOrarioPartenza().isAfter(orarioMinimoPartenza))) ||
                        (cs.getData().equals(dataSelezionata.plusDays(1)) && (cs.getCorsaRegolare().getOrarioPartenza().isBefore(orarioMinimoPartenza) || cs.getCorsaRegolare().getOrarioPartenza().equals(orarioMinimoPartenza)))) &&
                    tipoNatanteSelezionato.contains(cs.getCorsaRegolare().getNatante().getTipo())
                )
            {
                //calcolo il prezzo
                float prezzoCalcolato = cs.getCorsaRegolare().getCostoIntero();
                if (etaPasseggero < 12) {
                    prezzoCalcolato *= (1 - cs.getCorsaRegolare().getScontoRidotto()/100);
                }
                if (veicolo == true) { //si suppone che la GUI abbia giá verificato che un minorenne non sia associato ad un veicolo!
                    prezzoCalcolato += cs.getCorsaRegolare().getCostoVeicolo();
                }
                if (bagaglio == true) {
                    prezzoCalcolato += cs.getCorsaRegolare().getCostoBagaglio();
                }
                if (prezzoCalcolato <= prezzoMax) {
                    //aggiungo le informazioni sulla corsa da restituire alla gui

                    //non voglio prezzi al di sotto del centesimo. Nota: non é previsto overflow per le dimensioni dei numeri in questione.
                    double prezzoArrotondato = (Math.floor(prezzoCalcolato * 100) / 100);
                    prezzo.add((float) prezzoArrotondato);

                    idCorsa.add(cs.getCorsaRegolare().getIdCorsa());
                    nomeCompagnia.add(cs.getCorsaRegolare().getCompagnia().getNome());
                    data.add(cs.getData());
                    orePart.add(cs.getCorsaRegolare().getOrarioPartenza());
                    oreDest.add(cs.getCorsaRegolare().getOrarioArrivo());
                    postiDispPass.add(cs.getPostiDispPass());
                    postiDispVei.add(cs.getPostiDispVei());
                    minutiRitardo.add(cs.getMinutiRitardo());
                    natanti.add(cs.getCorsaRegolare().getNatante().getNome());
                    cancellata.add(cs.isCancellata());
                }
            }
        }
    }

    /**
     * Comanda al DB di registrare l'acquisto di un biglietto da parte dell'utente. In caso di risposta positiva
     * del DB, il biglietto viene anche aggiunto al Model.
     *
     * @param idCorsa       l'identificativo della corsa
     * @param data          la data della corsa
     * @param targaVeicolo  indica la presenza di un veicolo.
     * @param prevendita    indica la presenza di una prevendita
     * @param bagaglio      indica la presenza di un bagaglio
     * @param etaPasseggero l'etá del passeggero per cui si vuole acquistare il biglietto
     * @param prezzo        il prezzo d'acquisto
     * @return true se l'acquisto e' andato a buon fine, false altrimenti
     */
    public boolean acquistaBiglietto(int idCorsa, LocalDate data, String targaVeicolo, boolean prevendita, boolean bagaglio, int etaPasseggero, float prezzo) {
        try {
            ClienteDAO clienteDAO = new ClienteDAO();
            CorsaSpecifica cs = corse.get(new Pair(idCorsa, data));
            AtomicInteger idBiglietto = new AtomicInteger(-1);
            LocalDate dataAcquisto = LocalDate.now();
            clienteDAO.acquistaBiglietto(cs.getCorsaRegolare().getIdCorsa(), cs.getData(), cliente.getLogin(), targaVeicolo, prevendita, bagaglio, prezzo, dataAcquisto, etaPasseggero, idBiglietto);
            Veicolo veicolo = cliente.getVeicoliPosseduti().get(targaVeicolo);
            cliente.addBiglietto(new Biglietto(idBiglietto.get(), cliente, cs, etaPasseggero, veicolo, prevendita, bagaglio, prezzo, dataAcquisto));
            corse.clear();
            buildCorse();
            return true;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
            return false;
        }
    }

    /**
     * Visualizza i porti.
     *
     * @param idPorto gli id dei porti
     * @param comune  i comuni dei porti
     */
    public void visualizzaPorti(ArrayList<Integer> idPorto, ArrayList<String> comune) {
        for (Map.Entry<Integer, Porto> it : porti.entrySet()) {
            idPorto.add(it.getKey());
            comune.add(it.getValue().getComune());
        }
    }

    /**
     * Comanda al DB di aggiungere un veicolo per il cliente. In caso di risposta positiva del DB, lo aggiunge anche al Model.
     *
     * @param tipoVeicolo il tipo del veicolo da inserire
     * @param targa       la targa del veicolo da inserire
     * @return true se il veicolo e' stato correttamente aggiunto, false altrimenti
     */
    public boolean addVeicolo(String tipoVeicolo, String targa) {
        try {
            ClienteDAO clienteDAO = new ClienteDAO();
            clienteDAO.aggiungeVeicolo(tipoVeicolo, targa, cliente.getLogin());
            cliente.addVeicolo(new Veicolo(targa, tipoVeicolo));
            return true;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
            return false;
        }
    }

    /**
     * Restituisce le compagnie presenti nel Model.
     *
     * @param login le login delle compagnie
     * @param nome  i nomi delle compagnie
     */
    public void getCompagnie(ArrayList<String> login, ArrayList<String> nome) {
        for (Map.Entry<String, Compagnia> it : compagnie.entrySet()) {
            login.add(it.getKey());
            nome.add(it.getValue().getNome());
        }
    }

    /**
     * Visualizza i contatti di una compagnia.
     *
     * @param idCompagnia l'id della compagnia
     * @param nomeSocial  i nomi dei social
     * @param tag         i tag nei social
     * @param email       gli indirizzi email
     * @param telefono    i telefoni
     * @param sitoWeb     il sito web (ArrayList solo per essere modificato)
     */
    public void visualizzaContatti(String idCompagnia, ArrayList<String> nomeSocial, ArrayList<String> tag, ArrayList<String> email, ArrayList<String> telefono, ArrayList<String> sitoWeb) {
        Compagnia c = compagnie.get(idCompagnia);
        for (AccountSocial x : c.getAccounts()) {
            nomeSocial.add(x.getNomeSocial());
            tag.add(x.getTag());
        }

        email.addAll(c.getEmails());
        telefono.addAll(c.getTelefoni());
        sitoWeb.addFirst(c.getSitoWeb());
    }

    /**
     * Visualizza i veicoli del cliente.
     *
     * @param targa le targhe dei veicoli
     * @param tipo  i tipi dei veicoli
     */
    public void visualizzaVeicoli(ArrayList<String> targa, ArrayList<String> tipo) {
        for (Map.Entry<String, Veicolo> it : cliente.getVeicoliPosseduti().entrySet()) {
            targa.add(it.getKey());
            tipo.add(it.getValue().getTipo());
        }
    }

    /**
     * Visualizza i biglietti acquistati dal cliente.
     *
     * @param idBiglietti   gli id dei biglietti
     * @param idCorse       gli id delle corse
     * @param dataCor       le date delle corse
     * @param orePart       le ore di partenza
     * @param minutiRitardo i minuti di ritardo
     * @param portoPar      i porti di partenza
     * @param portoDest     i porti di destinazione
     * @param bagagli       valori booleani che indicano se il cliente ha portato con se' un bagaglio o meno
     * @param targaVeicolo  le targe dei veicoli che il cliente ha portato con se'
     * @param etaPass       le eta dei passeggeri
     * @param dataAcquisto  le date di acquisto
     * @param prezzi        i prezzi di acquisto
     */
    public void visualizzaBigliettiAcquistati(ArrayList<Integer> idBiglietti, ArrayList<Integer> idCorse, ArrayList<LocalDate> dataCor, ArrayList<LocalTime> orePart, ArrayList<Integer> minutiRitardo, ArrayList<String> portoPar, ArrayList<String> portoDest, ArrayList<Boolean> bagagli, ArrayList<String> targaVeicolo, ArrayList<Integer> etaPass, ArrayList<LocalDate> dataAcquisto, ArrayList<Float> prezzi) {
        for (Biglietto b : cliente.getBigliettiAcquistati()) {
            idBiglietti.add(b.getIdBiglietto());
            idCorse.add(b.getCorsa().getCorsaRegolare().getIdCorsa());
            dataCor.add(b.getCorsa().getData());
            orePart.add(b.getCorsa().getCorsaRegolare().getOrarioPartenza());
            minutiRitardo.add(b.getCorsa().getMinutiRitardo());
            portoPar.add(b.getCorsa().getCorsaRegolare().getPortoPartenza().getComune());
            portoDest.add(b.getCorsa().getCorsaRegolare().getPortoArrivo().getComune());
            bagagli.add(b.isBagaglio());
            if (b.getVeicolo() != null) {
                targaVeicolo.add(b.getVeicolo().getTarga());
            } else {
                targaVeicolo.add("-");
            }
            etaPass.add(b.getEtaPasseggero());
            dataAcquisto.add(b.getDataAcquisto());
            prezzi.add(b.getPrezzo());
        }
    }

    /**
     * Comanda al DB di registrare un nuovo cliente.
     *
     * @param nome    il nome del nuovo cliente
     * @param cognome il cognome del nuovo cliente
     * @param login   la login del nuovo cliente
     * @param pwd     la password del nuovo cliente
     * @return true se la registrazione e' andata a buon fine, false altrimenti.
     */
    public boolean clienteSiRegistra(String nome, String cognome, String login, String pwd) {
        try {
            ClienteDAO clienteDAO = new ClienteDAO();
            clienteDAO.siRegistra(nome, cognome, login, pwd);
            return true;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
            return false;
        }
    }

    /**
     * Visualizza le informazioni dei porti.
     *
     * @param comune    i comuni dei porti
     * @param indirizzo gli indirizzi dei porti
     * @param telefono  i telefoni dei porti
     */
    public void visualizzaInfoPorti(ArrayList<String> comune, ArrayList<String> indirizzo, ArrayList<String> telefono) {
        for (Map.Entry<Integer, Porto> it : porti.entrySet()) {
            Porto p = it.getValue();
            comune.add(p.getComune());
            indirizzo.add(p.getIndirizzo());
            telefono.add(p.getNumeroTelefono());
        }
    }

    /**
     * Restituisce la login del cliente.
     *
     * @return la login del cliente
     */
    public String getLoginCliente() {
        return cliente.getLogin();
    }
}