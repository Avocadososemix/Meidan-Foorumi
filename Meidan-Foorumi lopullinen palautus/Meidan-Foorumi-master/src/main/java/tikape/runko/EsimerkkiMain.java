package tikape.runko;

import java.util.HashMap;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.database.EsimerkkiDao;
import tikape.runko.database.EsimerkkiDatabase;
import tikape.runko.database.EsimerkkiOpiskelijaDao;

public class EsimerkkiMain {

    public static void main(String[] args) throws Exception {
        EsimerkkiDatabase database = new EsimerkkiDatabase("jdbc:sqlite:opiskelijat.db");
        database.init();

        EsimerkkiDao opiskelijaDao = new EsimerkkiOpiskelijaDao(database);

        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("viesti", "tervehdys");

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        get("/opiskelijat", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("opiskelijat", opiskelijaDao.findAll());

            return new ModelAndView(map, "opiskelijat");
        }, new ThymeleafTemplateEngine());

        get("/opiskelijat/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("opiskelija", opiskelijaDao.findOne(Integer.parseInt(req.params("id"))));

            return new ModelAndView(map, "opiskelija");
        }, new ThymeleafTemplateEngine());
    }
    
}
