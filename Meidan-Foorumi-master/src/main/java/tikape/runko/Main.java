
package tikape.runko;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import static spark.Spark.get;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.AlueDao;
import tikape.runko.database.Database;
import tikape.runko.database.KeskustelunavausDao;
import tikape.runko.database.ViestiDao;
import tikape.runko.domain.AlueJaViestit;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException {
        Database database = new Database("jdbc:sqlite:foorumi.db");
        database.init();
        
        AlueDao alueDao = new AlueDao(database);
        KeskustelunavausDao keskustelunavausDao = new KeskustelunavausDao(database);
        ViestiDao viestiDao = new ViestiDao(database);


        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            //map.put("alueet", alueDao.haeAlueetViesteineen());
            //map.put("alueet", alueDao.etsiKaikki());
            map.put("viestienMaarat", viestiDao.etsiAlueidenViestit()); //hakee jokaisen alueen kaikkien viestien lukumäärät listana
            //map.put("viimeisin", viestiDao.)
            
            //ongelma: miten saada thymeleafille tieto alueen viesteistä ilman, että niitä laittaa erillisessä listassa?

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        /*
        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("Viesti", ViestiDao.findAll());

            return new ModelAndView(map, "Viesti");
        }, new ThymeleafTemplateEngine());
        */
     
        
        // TODO code application logic here
        //Luo yhteys tietokantaan foorumi.db
        
        //Anna tietokanta Dao-olioiden käyttöön
    }
    
}
