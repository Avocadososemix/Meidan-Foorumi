
package tikape.runko.domain;

import java.sql.Timestamp;

public class AlueJaViestit {
    private String nimi;
    private Integer viestit;
    private String viimeinenViesti;
    
    public AlueJaViestit (String otsikko, Integer viestit, String viimeinenViesti) {
        this.nimi = otsikko;
        this.viestit = viestit;
        this.viimeinenViesti = viimeinenViesti;
    }

    public AlueJaViestit (String otsikko, Integer viestit) {
        this.nimi = otsikko;
        this.viestit = viestit;
    }    
    
    public String getOtsikko() {
        return nimi;
    }

    public void setOtsikko(String otsikko) {
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
    
    
}
