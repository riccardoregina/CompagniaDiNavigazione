package postgresqlDAO;

import dao.DAO;

public class PostgreSQLDAO implements DAO {
    //Responsabilita di Utente
    public void accedi();
    //Responsabilita di Compagnia
    public void aggiungiCorsa();
    public void cancellaCorsa();
    public void segnalaRitardo();
    public void modificaCorsa();
    public void aggiungiNatante();
    public void rimuoviNatante();
    //Responsabilita di Cliente
    public void visualizzaCorse();
    public void acquistaBiglietto();
    public void addVeicolo();
    //Responsabilita di Biglietto
    public void aggiornaPostiDisp();
}
