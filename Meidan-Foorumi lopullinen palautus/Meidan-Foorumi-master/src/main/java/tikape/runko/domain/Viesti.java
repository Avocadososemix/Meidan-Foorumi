/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.domain;

/**
 *
 * @author nikkaire
 */
public class Viesti {

    private Integer id;
    private String aika;
    private String viesti;
    private String lähettäjä;
    private Integer keskustelunavaus;

    public Viesti(Integer id, String aika, String viesti, String lähettäjä) {
        this.id = id;
        this.aika = aika;
        this.viesti = viesti;
        this.lähettäjä = lähettäjä;
    }
    
    public Viesti(Integer id, String aika, String viesti, String lähettäjä, Integer keskustelunavaus) {
        this.id = id;
        this.aika = aika;
        this.viesti = viesti;
        this.lähettäjä = lähettäjä;
        this.keskustelunavaus = keskustelunavaus;
    }
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAika() {
        return aika;
    }

    public void setAika(String aika) {
        this.aika = aika;
    }
    
    public String getViesti() {
        return viesti;
        
    }
    public void setViesti(String viesti) {
        this.viesti = viesti;
    }
    
    public String getLähettäjä() {
        return lähettäjä;
    }
    
    public void setLähettäjä(String lähettäjä) {
        this.lähettäjä = lähettäjä;
    }
}
