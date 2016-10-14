
package tikape.runko.domain;

import java.sql.Timestamp;

public class AlueJaViestit {
    private Integer id;
    private String nimi;
    private Integer viestit;
    private String viimeinenViesti;
    
    public AlueJaViestit (Integer id, String otsikko, Integer viestit, String viimeinenViesti) {
        this.id = id;
        this.nimi = otsikko;
        this.viestit = viestit;
        this.viimeinenViesti = viimeinenViesti;
    }

    public AlueJaViestit (String otsikko, Integer viestit) {
        this.nimi = otsikko;
        this.viestit = viestit;
    }    
    
    public String getNimi() {
        return nimi;
    }

    public void setNimi(String otsikko) {
        this.nimi = otsikko;
    }

    public Integer getViestit() {
        return viestit;
    }

    public void setViestit(Integer viestit) {
        this.viestit = viestit;
    }

    public String getViimeinenViesti() {
        return viimeinenViesti;
    }

    public void setViimeinenViesti(String viimeinenViesti) {
        this.viimeinenViesti = viimeinenViesti;
    }
    
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
}
