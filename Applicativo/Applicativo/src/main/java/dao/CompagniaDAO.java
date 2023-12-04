package dao;

import java.util.Date;

public interface CompagniaDAO extends UtenteDAO {
    public void aggiungiCorsa(/*Attributi di corsaRegolare*/);
    public void modificaCorsaRegolare(/*Attributi di corsaRegolare*/);
    public void modificaCorsaSpecifica(/*Attributi di corsaRegolare*/);
    public void cancellaCorsaRegolare(/*Attributi di corsaRegolare*/);
    public void cancellaCorsaSpecifica(int idCorsa, Date data);
    public void segnalaRitardo(int idCorsa, Date data, int ritardo);
    public void aggiungeNatante(/*Attributi di Natante*/);
    public void rimuoviNatante(/*Attributi di Natante*/);
}
