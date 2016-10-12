
package tikape.runko;

import java.util.HashMap;
import spark.ModelAndView;
import static spark.Spark.get;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.ViestiDao;

public class Main {

    public static void main(String[] args) {
        
        AlueDao alueDao = new AlueDao(database);


        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("Alue", AlueDao.findAll());

            return new ModelAndView(map, "Alue");
        }, new ThymeleafTemplateEngine());

        ViestiDao viestiDao = new ViestiDao(database);
        
      get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("Viesti", ViestiDao.findAll());

            return new ModelAndView(map, "Viesti");
        }, new ThymeleafTemplateEngine());
     
        
        // TODO code application logic here
        //Luo yhteys tietokantaan foorumi.db
        
        //Anna tietokanta Dao-olioiden käyttöön
    }
    
}
