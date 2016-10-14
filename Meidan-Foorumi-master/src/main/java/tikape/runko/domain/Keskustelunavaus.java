package tikape.runko.domain;

public class Keskustelunavaus {

    private Integer id;
    private String otsikko;
    private Integer alue;

    public Keskustelunavaus(Integer id, String otsikko, Integer alue) {
        this.id = id;
        this.alue = alue;
        this.otsikko = otsikko;
    }

    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOtsikko() {
        return otsikko;
    }

    public void setOtsikko(String otsikko) {
        this.otsikko = otsikko;
    }
    
    public Integer getAlue() {
        return alue;
        
    }
    public void setAlue(Integer alue) {
        this.alue = alue;
    }

}
