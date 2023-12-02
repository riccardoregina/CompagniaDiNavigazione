package postgresqlDAO;

public class ClienteDB implements UtenteDAO, ClienteDAO{
    Connection c;
    java.sql.Connection conn;

    public void accedi(String login, String pw) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "select *
        from Cliente
        where login = ? and pw = ?";

        try {
            conn = c.getConnection();
        } catch (SQLException e) { 
            System.out.println("Connessione fallita.");
            e.printStackTrace();
        }

        try {
            conn.prepareStatement(query);
            ps.setString(1, login);
            ps.setString(2, pw);
            ps.executeStatement();

            if (rs.next()) { //se esiste un risultato
                //apri nuovo frame (menu cliente) 
            } else {
                //stampa a schermo che l'utente non é registrato
            }
            rs.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Cancellazione fallita.");
            e.printStackTrace();
        }
    }
    public CorseTrovate visualizzaCorse(Date data, String portoPartenza, String portoArrivo) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "select portoPartenza, portoArrivo, orarioPartenza, orarioArrivo, costoIntero, scontoRidotto, costoBagaglio, costoPrevendita, costoVeicolo, nome as nomeCompagnia
        from (CorsaRegolare natural join CorsaSpecifica) join Compagnia on Compagnia = login
        where data = ? and portoPartenza = ? and portoArrivo = ?";

        try {
            conn = c.getConnection();
        } catch (SQLException e) { 
            System.out.println("Connessione fallita.");
            e.printStackTrace();
        }

        CorseTrovate ret = new CorseTrovate();
        try {
            conn.prepareStatement(query);
            ps.setDate(1, data);
            ps.setString(2, portoPartenza);
            ps.setString(3, portoArrivo);
            ps.executeStatement();

            while (rs.next()) {
                ret.getPortoP().add(rs.getInt("PortoPartenza"));
                //etc.
            }
            rs.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Richiesta al DB fallita.");
            e.printStackTrace();
        }

        return ret;
    }
    public void acquistaBiglietto(Biglietto biglietto) {
        //l'aggiornamento dei posti disponibili sará effettuato dal DB
        
        PreparedStatement ps = null;
        String query = "insert into Biglietto (idCorsa, data, Cliente, Veicolo, prevendita, bagaglio, prezzo, dataAcquisto, etaPasseggero)" +
                " values (?,?,?,?,?,?,?,?,?)";

        try {
            conn = c.getConnection();
        } catch (SQLException e) { 
            System.out.println("Connessione fallita.");
            e.printStackTrace();
        }

        try {
            conn.prepareStatement(query);
            ps.setInt(1, biglietto.getCorsa().getId())
            //etc.
            ps.executeUpdate();

            conn.close();
        } catch (SQLException e) {
            System.out.println("Acquisto fallito.");
            e.printStackTrace();
        }
    }
    public void aggiungiVeicolo(Veicolo veicolo) {
        PreparedStatement ps = null;
        String query = "insert into Veicolo" +
                " values (?,?,?)";

        try {
            conn = c.getConnection();
        } catch (SQLException e) { 
            System.out.println("Connessione fallita.");
            e.printStackTrace();
        }

        try {
            conn.prepareStatement(query);
            ps.setInt(1, veicolo.getTarga());
            ps.setString(2, veicolo.getTipo());
            ps.setString(3, veicolo.getProprietario().getLogin());
            
            ps.executeUpdate();

            conn.close();
        } catch (SQLException e) {
            System.out.println("Aggiunta fallita.");
            e.printStackTrace();
        }
    }
}
