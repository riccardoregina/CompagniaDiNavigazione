package dao;

public interface CompagniaDAO {
    public void aggiungiCorsa(CorsaRegolare corsaRegolare);
    public void modificaCorsaRegolare(CorsaRegolare corsaRegolareModificata);
    public void modificaCorsaSpecifica(CorsaSpecifica corsaSpecificaModificata);
    public void cancellaCorsaRegolare(CorsaRegolare corsaRegolare);
    public void cancellaCorsaSpecifica(CorsaSpecifica corsaSpecifica);
    public void segnalaRitardo(CorsaSpecifica corsaSpecifica, int ritardo);
    public void aggiungeNatante(Natante natante);
    public void rimuoviNatante(Natante natante);
}
