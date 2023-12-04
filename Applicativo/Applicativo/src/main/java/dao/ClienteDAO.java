package dao;

import database.CorseTrovate;

import java.util.Date;

public interface ClienteDAO extends UtenteDAO {
    public CorseTrovate visualizzaCorse(Date data, String portoPartenza, String portoArrivo);
    public void acquistaBiglietto();
    public void aggiungiVeicolo();
}
