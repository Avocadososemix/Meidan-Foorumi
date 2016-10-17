
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
            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        
        post("/", (req, res) -> {
            alueDao.tallenna(req.queryParams("alue"));
            res.redirect("/");
            return "ok";
        });
        
        get("/alue/:alueid", (req, res) -> {
            HashMap map = new HashMap<>();
            //palauta keskustelunavausten otsikot, viimeisten viestien lukumäärä ja viimeisen viestin aika
            map.put("keskustelunavaukset", keskustelunavausDao.haeKeskustelunavauksetViesteineen(Integer.parseInt(req.params(":alueid"))));
            map.put("alue", alueDao.etsi(Integer.parseInt(req.params(":alueid"))));
            return new ModelAndView(map, "keskustelunavaukset");
        }, new ThymeleafTemplateEngine());

        post("/alue/:alueid", (req, res) -> { //alueen id  --> näyttää keskustelut
            Integer keskustelunavausId = keskustelunavausDao.tallenna(req.queryParams("keskustelunavaus"), Integer.parseInt(req.params(":alueid")));
            //req.params palauttaa Stringin, jonka Integer.parseInt muuttaa luvuksi
            res.redirect("/alue/" + req.params(":alueid") + "/keskustelu/"+ Integer.toString(keskustelunavausId)); //ohjataan luomaan viesti --keskustelunavauksen id
            return "ok";
        });
        
        get("/alue/:alueid/keskustelu/:keskusteluid", (req, res) -> { //ei näytä vanhoja viestejä
            HashMap map = new HashMap<>();
            map.put("viestit", viestiDao.etsiKeskustelunViestit(Integer.parseInt(req.params(":alueid")), Integer.parseInt(req.params(":keskusteluid")))); //ei toimi
            map.put("keskustelu", keskustelunavausDao.etsi(Integer.parseInt(req.params(":keskusteluid"))));
            map.put("alue", alueDao.etsi(Integer.parseInt(req.params(":alueid"))));
            
            return new ModelAndView(map, "viestit");
        }, new ThymeleafTemplateEngine());
        
        
        post("/alue/:alueid/keskustelu/:keskusteluid", (req, res) -> {
            viestiDao.tallenna(req.queryParams("lähettäjä"), req.queryParams("viesti"), Integer.parseInt(req.params(":keskusteluid"))); //Viesti (aika, lähettäjä, viesti, keskustelunavaus)
            res.redirect("/alue/" + req.params(":alueid") + "/keskustelu/" + Integer.parseInt(req.params(":keskusteluid")));
            return "ok";
        });
    }
    
}
