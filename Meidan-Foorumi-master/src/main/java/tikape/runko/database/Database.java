package tikape.runko.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private String databaseAddress;

    public Database(String databaseAddress) throws ClassNotFoundException {
        this.databaseAddress = databaseAddress;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(databaseAddress);
    }

    public void init() {
        List<String> lauseet = sqliteLauseet();

        // "try with resources" sulkee resurssin automaattisesti lopuksi
        try (Connection conn = getConnection()) {
            Statement st = conn.createStatement();

            // suoritetaan komennot
            for (String lause : lauseet) {
                System.out.println("Running command >> " + lause);
                st.executeUpdate(lause);
            }

        } catch (Throwable t) {
            // jos tietokantataulu on jo olemassa, ei komentoja suoriteta
            System.out.println("Error >> " + t.getMessage());
        }
    }

    private List<String> sqliteLauseet() {
        ArrayList<String> lista = new ArrayList<>();

        // tietokantataulujen luomiseen tarvittavat komennot suoritusjärjestyksessä
        lista.add("CREATE TABLE Alue (alue_id integer PRIMARY KEY, nimi varchar(66) NOT NULL);");
        lista.add("CREATE TABLE Keskustelunavaus (id integer PRIMARY KEY, otsikko varchar(66) NOT NULL, alue integer, FOREIGN KEY(alue) REFERENCES Alue(id));");
        lista.add("CREATE TABLE Viesti (id integer PRIMARY KEY, aika timestamp, lähettäjä varchar(66) NOT NULL, viesti varchar(400) NOT NULL, keskustelunavaus integer, FOREIGN KEY(keskustelunavaus) REFERENCES Keskustelunavaus(id));");
        
        lista.add("INSERT INTO Alue (nimi) VALUES ('Ohjelmointi')");
        lista.add("INSERT INTO Alue (nimi) VALUES ('Taide')");
        
        lista.add("INSERT INTO Keskustelunavaus (otsikko, alue) VALUES ('Java', 1)"); //primary keyn indeksointi alkaa 1:stä
        lista.add("INSERT INTO Keskustelunavaus (otsikko, alue) VALUES ('Python', 1)");
        lista.add("INSERT INTO Keskustelunavaus (otsikko, alue) VALUES ('Mosaiikit', 2)");
        
        lista.add("INSERT INTO Viesti (lähettäjä, viesti, keskustelunavaus) VALUES ('Irene', 'En pidä mosaiikeista', 3)");
        lista.add("INSERT INTO Viesti (lähettäjä, viesti, keskustelunavaus) VALUES ('Eneri', 'Minä pidän mosaiikeista', 3)");
        
        lista.add("INSERT INTO Viesti (lähettäjä, viesti, keskustelunavaus) VALUES ('Irene', 'Moi vaan kaikki Javaihmiset', 1)");
        lista.add("INSERT INTO Viesti (lähettäjä, viesti, keskustelunavaus) VALUES ('Jonne', 'Moi vaan', 1)");
        lista.add("INSERT INTO Viesti (lähettäjä, viesti, keskustelunavaus) VALUES ('Oskari', 'Moi en oo käärme', 2)");
        
        
        return lista;
    }
}
