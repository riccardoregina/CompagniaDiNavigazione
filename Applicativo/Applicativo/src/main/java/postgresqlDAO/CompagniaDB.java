package postgresqlDAO;

public class CompagniaDB {
    Connection c;
    java.sql.Connection conn;

    public void accedi(String login, String pw) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "select *
        from Compagnia
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
    public void aggiungiCorsa(CorsaRegolare corsaRegolare) {
        //deve chiamare una procedura
        conn.Call //qualcosa del genere TEMP
        String query = "insert into Natante" +
                " values (?,?,?,?,?)";

        try {
            conn = c.getConnection();
        } catch (SQLException e) { 
            System.out.println("Connessione fallita.");
            e.printStackTrace();
        }

        try {
            conn.Call //qualcosa del genere TEMP

            conn.close();
        } catch (SQLException e) {
            System.out.println("Cancellazione fallita.");
            e.printStackTrace();
        }
    }
    public void modificaCorsaRegolare(CorsaRegolare corsaRegolareModificata) {
        PreparedStatement ps = null;
        String query = "update CorsaRegolare
        set portoPartenza = ?, portoArrivo = ?, orarioPartenza = ?, orarioArrivo = ?, costoIntero = ?, scontoRidotto = ?, costoBagaglio = ?, costoPrevendita = ?, costoVeicolo = ?
        where idCorsa = ?";

        try {
            conn = c.getConnection();
        } catch (SQLException e) { 
            System.out.println("Connessione fallita.");
            e.printStackTrace();
        }

        try {
            conn.prepareStatement(query);
            ps.setInt(1, corsaRegolare.getPortoPartenza().getId());
            //etc.
            ps.setInt(10, corsaRegolare.getId());
            ps.executeUpdate();

            conn.close();
        } catch (SQLException e) {
            System.out.println("Modifica fallita.");
            e.printStackTrace();
        }
    }
    public void modificaCorsaSpecifica(CorsaSpecifica corsaSpecificaModificata) {
        //...
    }
    public void cancellaCorsaRegolare(CorsaRegolare corsaRegolare) {
        PreparedStatement ps = null;
        String query = "delete from CorsaRegolare where idCorsa = ?";

        try {
            conn = c.getConnection();
        } catch (SQLException e) { 
            System.out.println("Connessione fallita.");
            e.printStackTrace();
        }

        try {
            conn.prepareStatement(query);
            ps.setInt(1, corsaRegolare.getId());
            
            ps.executeUpdate();

            conn.close();
        } catch (SQLException e) {
            System.out.println("Eliminazione fallita.");
            e.printStackTrace();
        }
    }
    public void cancellaCorsaSpecifica(CorsaSpecifica corsaSpecifica) {
        PreparedStatement ps = null;
        String query = "update CorsaSpecifica
        set cancellata = true
        where idCorsa = ? and data = ?";

        try {
            conn = c.getConnection();
        } catch (SQLException e) { 
            System.out.println("Connessione fallita.");
            e.printStackTrace();
        }

        try {
            conn.prepareStatement(query);
            ps.setInt(1, corsaSpecifica.getCorsaRegolare().getId());
            ps.setDate(2, corsaSpecifica.getDate());
            
            ps.executeUpdate();

            conn.close();
        } catch (SQLException e) {
            System.out.println("Cancellazione fallita.");
            e.printStackTrace();
        }
    }
    public void segnalaRitardo(CorsaSpecifica corsaSpecifica, int ritardo) {
        PreparedStatement ps = null;
        String query = "update CorsaSpecifica
        set minutiRitardo = ?
        where idCorsa = ? and data = ?";

        try {
            conn = c.getConnection();
        } catch (SQLException e) { 
            System.out.println("Connessione fallita.");
            e.printStackTrace();
        }

        try {
            conn.prepareStatement(query);
            ps.setInt(1, ritardo);
            ps.setInt(2, corsaSpecifica.getCorsaRegolare().getId());
            ps.setDate(3, corsaSpecifica.getDate());
            
            ps.executeUpdate();

            conn.close();
        } catch (SQLException e) {
            System.out.println("Segnalazione ritardo fallita.");
            e.printStackTrace();
        }
    }
    public void aggiungeNatante(Natante natante) {
        PreparedStatement ps = null;
        String query = "insert into Natante
        values (?,?,?,?,?)";

        try {
            conn = c.getConnection();
        } catch (SQLException e) { 
            System.out.println("Connessione fallita.");
            e.printStackTrace();
        }

        try {
            conn.prepareStatement(query);
            ps.setString(1, natante.getCompagnia().getLogin());
            //etc.
            
            ps.executeUpdate();

            conn.close();
        } catch (SQLException e) {
            System.out.println("Aggiunta fallita.");
            e.printStackTrace();
        }
    }
    public void rimuoviNatante(Natante natante) {
        PreparedStatement ps = null;
        String query = "delete from Natante
        where nome = ?";

        try {
            conn = c.getConnection();
        } catch (SQLException e) { 
            System.out.println("Connessione fallita.");
            e.printStackTrace();
        }

        try {
            conn.prepareStatement(query);
            ps.setString(1, natante.getNome());
            
            ps.executeUpdate();

            conn.close();
        } catch (SQLException e) {
            System.out.println("Eliminazione fallita.");
            e.printStackTrace();
        }
    }
}
