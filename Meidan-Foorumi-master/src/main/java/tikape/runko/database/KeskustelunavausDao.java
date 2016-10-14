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
import tikape.runko.domain.Keskustelunavaus;

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

    public void tallenna() throws SQLException {
        
    }

    @Override
    public List<Keskustelunavaus> etsiTietyt(Integer key) throws SQLException {
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
    
}
