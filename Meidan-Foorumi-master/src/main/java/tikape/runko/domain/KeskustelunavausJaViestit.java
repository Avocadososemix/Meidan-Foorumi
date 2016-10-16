package tikape.runko.domain;

public class KeskustelunavausJaViestit {
    private Integer id;
    private String otsikko;
    private Integer viestit;
    private String viimeinenViesti;

    public KeskustelunavausJaViestit(Integer id, String otsikko, Integer viestit, String viimeinenViesti) {
        this.id = id;
        this.otsikko = otsikko;
        this.viestit = viestit;
        this.viimeinenViesti = viimeinenViesti;
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
