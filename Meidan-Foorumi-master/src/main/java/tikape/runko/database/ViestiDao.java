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
            

            viestit.add(new Viesti(id, aika, nimi, lähettäjä));
        }

        rs.close();
        stmt.close();
        connection.close();

        return viestit;
    }

    @Override
    public void tallenna(Viesti Element) throws SQLException {

    }

    @Override
    public List<Viesti> etsiTietyt(Integer key) throws SQLException {
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
    
    public List<Integer> etsiAlueidenViestit() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT alue_id, COUNT(Viesti.id) AS viestit "
                + "FROM Viesti INNER JOIN Keskustelunavaus "
                + "ON Viesti.keskustelunavaus=Keskustelunavaus.id "
                + "INNER JOIN Alue "                                 //Valittaa syntaksivirhettä
                + "ON Keskustelunavaus.alue=Alue.alue_id "
                + "GROUP BY alue_id");
        
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
