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

    @Override
    public void tallenna(Alue Element) throws SQLException {
        
    }


    @Override
    public List<Alue> etsiTietyt(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Alue WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();

        List<Alue> alueet = new ArrayList<>();
        
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String nimi = rs.getString("nimi");

            alueet.add(new Alue(id, nimi));
        }


        rs.close();
        stmt.close();
        connection.close();

        return alueet;    
    }
    
    public List<Timestamp> haeViimeisenViestinAika() throws Exception {
        Connection connection = database.getConnection();
        //PreparedStatement stmt = connection.prepareStatement("SELECT Viesti.aika FROM  WHERE id = ?");

        //ResultSet rs = stmt.executeQuery();

        List<Timestamp> alueet = new ArrayList<>();
        
        return alueet;
    }
    
    public List<AlueJaViestit> haeAlueetViesteineen() throws SQLException {
        Connection connection = this.database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT Alue.alue_id, Alue.nimi, "
                + "COUNT(Viesti.id) AS viestit "
                + "FROM Viesti INNER JOIN Keskustelunavaus ON Viesti.keskustelunavaus=Keskustelunavaus.id "
                + "INNER JOIN Alue ON Keskustelunavaus.alue=Alue.alue_id GROUP BY Alue.nimi;"); 

        ResultSet rs = stmt.executeQuery();
        List<AlueJaViestit> alueet = new ArrayList<>();
        
        while (rs.next()) {
            String alueId = rs.getString("alue_id");
            String alueNimi = rs.getString("nimi");
            Integer viestienLkm = rs.getInt("viestit"); //Miten viestien lukumäärä on nimetty?
            
            PreparedStatement stmt1 = connection.prepareStatement("SELECT Viesti.aika "
                    + "FROM Viesti, Keskustelunavaus, Alue "
                    + "WHERE VIesti.keskustelunavaus=Keskustelunavaus.id "
                    + "AND Keskustelunavaus.alue=?"
                    + "ORDER BY Viesti.id DESC LIMIT 1");
            stmt1.setObject(1, alueId);
            ResultSet rs1 = stmt1.executeQuery();
            
            String viimeinenViesti = rs1.getString("aika"); //Miten viimeisen viestin aika on nimetty?
            alueet.add(new AlueJaViestit(alueNimi, viestienLkm, viimeinenViesti));
        }
        
        return alueet;
    }
    
}
