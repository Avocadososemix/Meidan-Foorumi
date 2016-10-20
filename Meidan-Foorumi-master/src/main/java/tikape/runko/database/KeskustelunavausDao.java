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
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Keskustelunavaus;
import tikape.runko.domain.KeskustelunavausJaViestit;

/**
 *
 * @author nikkaire
 */
public class KeskustelunavausDao implements Dao<Keskustelunavaus, Integer>{
    
    private Database database;
    
    public KeskustelunavausDao(Database database) {
        this.database = database;
    }

    @Override
    public List<Keskustelunavaus> etsiKaikki() throws SQLException {
        Connection connection = this.database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Keskustelunavaus"); //Vältä SQL-injektiot
        
        ResultSet rs = stmt.executeQuery();
        List<Keskustelunavaus> keskustelunavaukset = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String otsikko = rs.getString("otsikko");
            Integer alue = rs.getInt("alue");
            //"Viimeisin_viesti" poistettu, ei enää tässä luokassa.

            keskustelunavaukset.add(new Keskustelunavaus(id, otsikko, alue));
        }

        rs.close();
        stmt.close();
        connection.close();

        return keskustelunavaukset;
    }

    public Integer tallenna(String otsikko, Integer alue) throws SQLException {
        Connection connection = this.database.getConnection();
        Statement stmt = connection.createStatement();
        stmt.execute("INSERT INTO Keskustelunavaus(otsikko, alue) "
                + "VALUES ('" + otsikko + "', " + alue + ")");
        stmt.close();
        
        //luo kysely, joka palauttaa luodun keskustelunavauksen id:n 
        PreparedStatement stmt1 = connection.prepareStatement("SELECT Keskustelunavaus.id FROM Keskustelunavaus ORDER BY Keskustelunavaus.id DESC LIMIT 1");
        ResultSet rs1 = stmt1.executeQuery();
        
        boolean seuraava = rs1.next();
        Integer id = null;
        if (seuraava) {
            id = rs1.getInt("id");
        }        
        connection.close();
        return id;
    }

    public List<Keskustelunavaus> etsiTietyt(Integer key) throws SQLException { //tarvitaanko tätä metodia?
        Connection connection = this.database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Keskustelunavaus WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();

        List<Keskustelunavaus> keskustelunavaukset = new ArrayList<>();
        
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String otsikko = rs.getString("otsikko");
            Integer alue = rs.getInt("alue");

            keskustelunavaukset.add(new Keskustelunavaus(id, otsikko, alue));
        }


        rs.close();
        stmt.close();
        connection.close();

        return keskustelunavaukset;    
    }
    public Keskustelunavaus etsi(Integer key) throws SQLException {                
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Keskustelunavaus WHERE id = ?");
        stmt.setObject(1, key);
      

        ResultSet rs = stmt.executeQuery();

        boolean seuraava = rs.next(); 
        String viimeinenViesti = "";
        if (!seuraava) {
            stmt.close();
            connection.close();
            return null;
        } else {
            Integer id = rs.getInt("id");
            String otsikko = rs.getString("otsikko");
            Integer alue = rs.getInt("alue");
        
            stmt.close();
            connection.close();
            return new Keskustelunavaus(id, otsikko, alue);
        }
    }
    
    public List<KeskustelunavausJaViestit> haeKeskustelunavauksetViesteineen(Integer alueId) throws SQLException {
            Connection connection = this.database.getConnection();
            PreparedStatement stmt = connection.prepareStatement(""
                    + "SELECT Keskustelunavaus.id, "
                    + "Keskustelunavaus.otsikko, "
                    + "COUNT(Viesti.id) AS viestit "
                    + "FROM Keskustelunavaus "
                    + "LEFT JOIN Viesti ON Viesti.keskustelunavaus=Keskustelunavaus.id "
                    + "WHERE Keskustelunavaus.alue = ? "
                    + "GROUP BY Keskustelunavaus.id "
                    + "ORDER BY Viesti.aika DESC;"); 
            stmt.setInt(1, alueId);
            
            ResultSet rs = stmt.executeQuery();
            List<KeskustelunavausJaViestit> keskustelunavaukset = new ArrayList<>();

            while (rs.next()) {
                Integer KeskustelunavausId = rs.getInt("id");
                String KeskustelunavausOtsikko = rs.getString("otsikko");
                Integer viestienLkm = rs.getInt("viestit"); 

                PreparedStatement stmt1 = connection.prepareStatement(""
                        + "SELECT Viesti.aika "
                        + "FROM Viesti, Keskustelunavaus, Alue "
                        + "WHERE Viesti.keskustelunavaus = ? "
                        + "AND Keskustelunavaus.alue = ? "
                        + "ORDER BY Viesti.id DESC LIMIT 1");
                stmt1.setInt(1, KeskustelunavausId);
                stmt1.setInt(2, alueId);

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
                keskustelunavaukset.add(new KeskustelunavausJaViestit(KeskustelunavausId, KeskustelunavausOtsikko, viestienLkm, viimeinenViesti));
            }
            rs.close();
            stmt.close();
            connection.close();
            return keskustelunavaukset;
    }
}
