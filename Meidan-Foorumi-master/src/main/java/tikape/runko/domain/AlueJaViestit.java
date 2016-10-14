
package tikape.runko.domain;

import java.sql.Timestamp;

public class AlueJaViestit {
    private String otsikko;
    private Integer viestit;
    private String viimeinenViesti;
    
    public AlueJaViestit (String otsikko, Integer viestit, String viimeinenViesti) {
        this.otsikko = otsikko;
        this.viestit = viestit;
        this.viimeinenViesti = viimeinenViesti;
    }

    public AlueJaViestit (String otsikko, Integer viestit) {
        this.otsikko = otsikko;
        this.viestit = viestit;
    }    
    
    public String getOtsikko() {
        return otsikko;
    }

    public void setOtsikko(String otsikko) {
        this.otsikko = otsikko;
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

    public void setViimeinenViesti(Timestamp viimeinenViesti) {
        this.viimeinenViesti = viimeinenViesti;
    }
    
    
}
