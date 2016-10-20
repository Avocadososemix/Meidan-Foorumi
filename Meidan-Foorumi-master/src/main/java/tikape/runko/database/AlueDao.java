/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Alue;
import tikape.runko.domain.AlueJaViestit;
/**
 *
 * @author nikkaire
 */
public class AlueDao implements Dao<Alue, Integer>{
    
    private Database database;
    
    public AlueDao(Database database) {
        this.database=database;
    }

    @Override
    public List<Alue> etsiKaikki() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Alue"); 

        ResultSet rs = stmt.executeQuery();
        List<Alue> alueet = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("alue_id");
            String nimi = rs.getString("nimi");

            alueet.add(new Alue(id, nimi));
        }

        rs.close();
        stmt.close();
        connection.close();
        return alueet;
    }

    public void tallenna(String nimi) throws SQLException {
        Connection connection = this.database.getConnection();
        Statement stmt = connection.createStatement();
        stmt.execute("INSERT INTO Alue(nimi) "
                + "VALUES ('" + nimi + "')");
        stmt.close();
        connection.close();
    }

    
    public Alue etsi(Integer key) throws SQLException {                
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Alue WHERE alue_id = ?");
        stmt.setObject(1, key);
      

        ResultSet rs = stmt.executeQuery();

        boolean seuraava = rs.next(); 
        String viimeinenViesti = "";
        if (!seuraava) {
            stmt.close();
            connection.close();
            return null;
        } else {
            Integer id = rs.getInt("alue_id");
            String nimi = rs.getString("nimi");
        
            stmt.close();
            connection.close();
            return new Alue(id, nimi);
        }
    }
    /*
    public List<Timestamp> haeViimeisenViestinAika() throws Exception {
        Connection connection = database.getConnection();
        //PreparedStatement stmt = connection.prepareStatement("SELECT Viesti.aika FROM  WHERE id = ?");

        //ResultSet rs = stmt.executeQuery();

        List<Timestamp> alueet = new ArrayList<>();
        
        return alueet;
    }
    */
    
    public List<AlueJaViestit> haeAlueetViesteineen() throws SQLException {
        Connection connection = this.database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT Alue.alue_id, Alue.nimi, "
                + "COUNT(Viesti.id) AS viestit "
                + "FROM Alue LEFT JOIN Keskustelunavaus ON Keskustelunavaus.alue=Alue.alue_id "
                + "LEFT JOIN Viesti ON Viesti.keskustelunavaus=Keskustelunavaus.id GROUP BY Alue.alue_id "
                + "ORDER BY Alue.nimi ASC"); 

        ResultSet rs = stmt.executeQuery();
        List<AlueJaViestit> alueet = new ArrayList<>();
        
        while (rs.next()) {
            Integer alueId = rs.getInt("alue_id");
            String alueNimi = rs.getString("nimi");
            Integer viestienLkm = rs.getInt("viestit"); 
            
            PreparedStatement stmt1 = connection.prepareStatement("SELECT Viesti.aika "
                    + "FROM Viesti, Keskustelunavaus, Alue "
                    + "WHERE Viesti.keskustelunavaus=Keskustelunavaus.id "
                    + "AND Keskustelunavaus.alue = Alue.alue_id "
                    + "AND Alue.alue_id = ?"
                    + "ORDER BY Viesti.id DESC LIMIT 1");
            stmt1.setInt(1, alueId);
            
            ResultSet rs1 = stmt1.executeQuery();
            
            
            boolean seuraava = rs1.next(); 
            String viimeinenViesti = "";
            if (!seuraava) {
                viimeinenViesti = "----";
            } else {
                viimeinenViesti = rs1.getString("aika");
            }
            rs1.close();
            stmt1.close();  
            alueet.add(new AlueJaViestit(alueId, alueNimi, viestienLkm, viimeinenViesti));
        }
        rs.close();
        stmt.close();
        connection.close();
        return alueet;
    }
    
}
