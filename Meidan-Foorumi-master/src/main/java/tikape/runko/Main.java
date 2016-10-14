
package tikape.runko;

import java.util.*;
import spark.ModelAndView;
import static spark.Spark.get;
import static spark.Spark.post;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.AlueDao;
import tikape.runko.database.Database;
import tikape.runko.database.KeskustelunavausDao;
import tikape.runko.database.ViestiDao;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException {
        Database database = new Database("jdbc:sqlite:foorumi.db");
        database.init();
        
        AlueDao alueDao = new AlueDao(database);
        KeskustelunavausDao keskustelunavausDao = new KeskustelunavausDao(database);
        ViestiDao viestiDao = new ViestiDao(database);


        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("alueet", alueDao.haeAlueetViesteineen()); 
            System.out.println("Alueet: " + alueDao.haeAlueetViesteineen());
            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        
        post("/", (req, res) -> {
            alueDao.tallenna(req.queryParams("alue"));
            res.redirect("/");
            return "ok";
        });

    }
    
}
