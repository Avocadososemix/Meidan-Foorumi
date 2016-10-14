
package tikape.runko.domain;

import java.sql.Timestamp;

public class AlueJaViestit {
    private String otsikko;
    private Integer viestit;
    private Timestamp viimeinenViesti;
    
    public AlueJaViestit (String otsikko, Integer viestit, Timestamp viimeinenViesti) {
        this.otsikko = otsikko;
        this.viestit = viestit;
        this.viimeinenViesti = viimeinenViesti;
    }
    
    
}
