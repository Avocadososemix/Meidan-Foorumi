/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.database;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Viesti;

/** Kopioin tänne vastaavanlaiset koodinpätkät kuin Irene oli tehnyt AlueDaohon ja muutin ViestiDao-kohtaisiksi. t. Lauri
 *
 * @author nikkaire
 */
public class ViestiDao implements Dao<Viesti, Integer>{
    
    private Database database;
    
    public ViestiDao(Database database) {
        this.database = database;
    }

    @Override
    public List<Viesti> etsiKaikki() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti"); //Vältä SQL-injektiot
        
        ResultSet rs = stmt.executeQuery();
        List<Viesti> viestit = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String aika = rs.getString("aika");
            String nimi = rs.getString("nimi");
            String lähettäjä = rs.getString("lähettäjä");
            Integer keskustelunavaus = rs.getInt("keskustelunavaus");

            viestit.add(new Viesti(id, aika, nimi, lähettäjä, keskustelunavaus));
        }

        rs.close();
        stmt.close();
        connection.close();

        return viestit;
    }

    public void tallenna(String lähettäjä, String viesti, Integer keskustelunavaus) throws SQLException {
        //Datetime('now'); hakee tämänhetkisen ajan 
        Connection connection = this.database.getConnection();
        Statement stmt = connection.createStatement();
        stmt.execute("INSERT INTO Viesti (aika, lähettäjä, viesti, keskustelunavaus) "
                + "VALUES (Datetime('now'), '" + lähettäjä + "', '" + viesti + "', " + keskustelunavaus + ")");
        stmt.close();
        connection.close();
    }

    public List<Viesti> etsiTietyt(Integer key) throws SQLException { //tarvitaanko?
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti WHERE id = ?");
        stmt.setObject(1, key);
        
        ResultSet rs = stmt.executeQuery();

        List<Viesti> viestit = new ArrayList<>();
        
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String aika = rs.getString("aika");
            String nimi = rs.getString("nimi");
            String lähettäjä = rs.getString("lähettäjä");

            viestit.add(new Viesti(id, aika, nimi, lähettäjä));
        }


        rs.close();
        stmt.close();
        connection.close();

        return viestit;    
    }
    
    public List<Viesti> etsiKeskustelunViestit(Integer alue, Integer keskustelu) throws SQLException { //tietty alue ja keskustelu
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement(""
                + "SELECT * FROM Viesti, Keskustelunavaus, Alue "
                + "WHERE Viesti.keskustelunavaus = Keskustelunavaus.id "
                + "AND Keskustelunavaus.alue=Alue.alue_id "
                + "AND Keskustelunavaus.id = ? "
                + "AND Alue.alue_id = ?");
        stmt.setInt(1, keskustelu);
        stmt.setInt(2, alue);
        
        ResultSet rs = stmt.executeQuery();

        List<Viesti> viestit = new ArrayList<>();
        
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String aika = rs.getString("aika");
            String viesti = rs.getString("viesti");
            String lähettäjä = rs.getString("lähettäjä");
            Integer keskustelunavaus = rs.getInt("keskustelunavaus");

            viestit.add(new Viesti(id, aika, viesti, lähettäjä, keskustelunavaus));
        }


        rs.close();
        stmt.close();
        connection.close();

        return viestit;    
    }

    public List<Integer> etsiAlueidenViestit() throws SQLException {
        Connection connection = database.getConnection();

        String kysely = "SELECT alue_id, COUNT(Viesti.id) AS viestit "
                + "FROM Viesti INNER JOIN Keskustelunavaus "
                + "ON Viesti.keskustelunavaus=Keskustelunavaus.id "
                + "INNER JOIN Alue "                                 
                + "ON Keskustelunavaus.alue=Alue.alue_id "
                + "GROUP BY alue_id";
        System.out.println("");
        System.out.println(kysely);
        System.out.println("");

        PreparedStatement stmt = connection.prepareStatement(kysely);
        
        ResultSet rs = stmt.executeQuery();

        List<Integer> viestienMaarat = new ArrayList<>();
        
        while (rs.next()) {
            viestienMaarat.add(rs.getInt("viestit"));
        }


        rs.close();
        stmt.close();
        connection.close();
        
        return viestienMaarat;    
    }
    
    
}
