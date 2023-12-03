package model;

import java.util.ArrayList;

import dao.ClienteDAO;
import database.CorseTrovate;
import postgresqlDAO.ClienteDB;

public class Cliente extends Utente {
    private String nome;
    private String cognome;
    private ArrayList<Veicolo> veicoliPosseduti;
    private ArrayList<Biglietto> bigliettiAcquistati;
    public Cliente(String login, String pw, String nome, String cognome) {
        super(login, pw);
        this.nome = nome;
        this.cognome = cognome;
        veicoliPosseduti = new ArrayList<Veicolo>();
        bigliettiAcquistati = new ArrayList<Biglietto>();
    }
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public ArrayList<Veicolo> getVeicoliPosseduti() {
        return veicoliPosseduti;
    }

    public void setVeicoliPosseduti(ArrayList<Veicolo> veicoliPosseduti) {
        this.veicoliPosseduti = veicoliPosseduti;
    }

    public void addVeicolo(Veicolo veicolo) {
        veicoliPosseduti.add(veicolo);
    }

    public void removeVeicolo(Veicolo veicolo) {
        for(int i = 0; i < veicoliPosseduti.size(); i++) {
            if(veicolo.getTarga().equals(veicoliPosseduti.get(i).getTarga())) {
                veicoliPosseduti.remove(i);
            }
        }
    }
    public ArrayList<Veicolo> getBigliettiAcquistati() {
        return bigliettiAcquistati;
    }

    public void setBigliettiAcquistati(ArrayList<Biglietto> bigliettiAcquistati) {
        this.bigliettiAcquistati = bigliettiAcquistati;
    }

    public void addBiglietto(Biglietto biglietto) {
        bigliettiAcquistati.add(biglietto);
    }

    public void removeBiglietto(Biglietto biglietto) {
        bigliettiAcquistati.remove(biglietto);
    }
}
