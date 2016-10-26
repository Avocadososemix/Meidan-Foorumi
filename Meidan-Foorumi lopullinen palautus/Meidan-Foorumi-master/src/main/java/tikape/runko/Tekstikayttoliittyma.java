package tikape.runko;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Scanner;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import tikape.runko.database.AlueDao;
import tikape.runko.database.KeskustelunavausDao;
import tikape.runko.database.ViestiDao;
import tikape.runko.database.Database;
import tikape.runko.domain.Alue;
import tikape.runko.domain.AlueJaViestit;
import tikape.runko.domain.Keskustelunavaus;
import tikape.runko.domain.Viesti;

//täytyy mainiin lisätä jotta voi kokeilla:
//
//    public static void main(String[] args) throws ClassNotFoundException {
//
//        Tekstikayttoliittyma testi = new Tekstikayttoliittyma();
//    }
//
public class Tekstikayttoliittyma {

    private Scanner lukija;

    private AlueDao aluedao;
    private ViestiDao viestidao;
    private KeskustelunavausDao keskustelunavausdao;

    public Tekstikayttoliittyma() throws ClassNotFoundException, Exception {
        Database database = new Database("jdbc:sqlite:opiskelijat.db");

        database.init();
        //Insert constructor here
        //Luo Dao-oliot
        aluedao = new AlueDao(database);
        keskustelunavausdao = new KeskustelunavausDao(database);
        viestidao = new ViestiDao(database);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Terve! Mitä haluat testata? Alue (1), Keskustelunavaus (2), Viesti (3)");
        int testaus = 0;
        String kysymys = scanner.nextLine();
        switch (kysymys) {
            case "1":

                //Alueen toimintojen testaus
                //Kokeillaan etsiKaikki()
                /*
                try {
                    List<Alue> Kokeilu = aluedao.etsiKaikki();
                    System.out.println("Etsitään kaikki alueet: ");
                    for (Alue a : Kokeilu) {
                        System.out.println(a.getNimi());
                    }
                } catch (SQLException ex) {
                    System.out.println("Komento ei toiminut. Kaikkeutta ei löytynyt");
                }
                */
                //Kokeillaan haeAlueetViesteineen()
                try {
                    List<AlueJaViestit> Kokeilu = aluedao.haeAlueetViesteineen();
                    System.out.println("Etsitään kaikki alueet ja viestien lkm: ");
                    for (AlueJaViestit a : Kokeilu) {
                        System.out.println(a.getNimi() + " " + a.getViestit());
                    }
                } catch (SQLException ex) {
                    System.out.println("Komento ei toiminut. Kaikkeutta ei löytynyt");
                }

                System.out.println("Mikä alue tallennetaan? Anna nimi: ");
                String aluetallennus = scanner.nextLine();
                //Kokeillaan tallenna()
                try {
                    aluedao.tallenna(aluetallennus);
                    System.out.println("Tallennettu");
//                    System.out.println(a.getId());

                } catch (SQLException ex) {
                    System.out.println("Ei tallentunut");
                }

                System.out.println("Mikä alue haetaan? Anna ID: ");
                String aluehaku = scanner.nextLine();
                int aluehakuq = Integer.parseInt(aluehaku);
                //Kokeillaan etsi()
                try {
                    Alue Kokeilu2;
                    Kokeilu2 = aluedao.etsi(aluehakuq);
                    System.out.println("Yritetään etsiä yksi. Voit hakea vaikka äskettäin luodun alueen jos tiedät ID:n");
                    System.out.println(Kokeilu2.getNimi());

                } catch (Exception ex) {
                    System.out.println("Komento ei toiminut tai keskustelunavausta ei löytynyt");
                }

                break;
            case "2":

                //Keskustelunavauksen toimintojen testaus
                //Kokeillaan etsiKaikki()
                /*try {
                    List<Keskustelunavaus> Kokeilu2 = keskustelunavausdao.etsiKaikki();
                    System.out.println("Etsitään kaikki keskustelunavaukset: ");
                    for (Keskustelunavaus a : Kokeilu2) {
                        System.out.println(a.getOtsikko());
                    }
                } catch (SQLException ex) {
                    System.out.println("Komento ei toiminut. Kaikkeutta ei löytynyt");
                }
                */

                //Kokeillaan tallenna()
                System.out.println("Mikä keskustelunavaus tallennetaan? Anna nimi: ");
                String keskustelutallennus = scanner.nextLine();
                System.out.println("Anna tallennettavalle alueelle id: ");
                String keskustelutallennusid = scanner.nextLine();
                int aluehakuid = Integer.parseInt(keskustelutallennusid);

                try {
                    keskustelunavausdao.tallenna(keskustelutallennus, aluehakuid);
                    System.out.println("Tallennettu");
                } catch (SQLException ex) {
                    System.out.println("Ei tallentunut");
                }
                //Kokeillaan hakea keskustelunavaus
                System.out.println("Mikä keskustelu haetaan? Anna ID: ");
                String keskustelunavaushaku = scanner.nextLine();
                int keskustelunvausz = Integer.parseInt(keskustelunavaushaku);
                System.out.println("Löytyi");
                System.out.println("");
                //Kokeillaan etsi()
                try {
                    Keskustelunavaus keskusta;
                    keskusta = keskustelunavausdao.etsi(keskustelunvausz);
                    System.out.println("Yritetään etsiä yksi");
                    System.out.print("Keskustelunavauksen id: ");
                    System.out.println(keskusta.getAlue());
                    System.out.print("Keskustelun otsikko: ");
                    System.out.println(keskusta.getOtsikko());

                } catch (SQLException ex) {
                    System.out.println("Komento ei toiminut tai keskustelunavausta ei löytynyt");
                }

                //Kokeillaan haeKeskustelunavauksetViesteineen(Integer alueId)
                System.out.println("Haetaan keskustelunavaukset ja viestit. Mikä keskustelunavaus? Anna id: ");
                String keskusteluviesteineen = scanner.nextLine();
                int keskusteluviesteineenInt = Integer.parseInt(keskusteluviesteineen);

                try {
                    keskustelunavausdao.haeKeskustelunavauksetViesteineen(keskusteluviesteineenInt);
                    System.out.println("Löytyi");

                } catch (SQLException ex) {
                    System.out.println("Ei löytynyt");
                }

                break;
            case "3":

                //Viestin toimintojen testaus
                //Kokeillaan etsiKaikki()
                /*try {
                    List<Viesti> Kokeilu3 = viestidao.etsiKaikki();
                    System.out.println("Etsitään kaikki viestit: ");
                    for (Viesti a : Kokeilu3) {
                        System.out.println(a.getViesti());
                    }
                } catch (SQLException ex) {
                    System.out.println("Komento ei toiminut. Kaikkeutta ei löytynyt");
                }
                */

                //Kokeillaan tallenna()
                System.out.println("Olet viestin lähettäjä, annan nimesi: ");
                String viestitallennusnimi = scanner.nextLine();
                System.out.println("Mikä viesti tallennetaan? Anna viesti: ");
                String viestitallennus = scanner.nextLine();
                System.out.println("Mihin keskustelunavaukseen viesti lisätään? Anna id: ");
                String viestitallennusId = scanner.nextLine();
                int viestig = Integer.parseInt(viestitallennusId);

                try {
                    viestidao.tallenna(viestitallennusnimi, viestitallennus, viestig);
                    System.out.println("Tallennettu");
                } catch (SQLException ex) {
                    System.out.println("Ei tallentunut");
                }

                    //Ei käytetä sovelluksessa
//                //Kokeillaan etsiTietyt()
//                System.out.println("Mikä keskustelu haetaan? Anna ID: ");
//                String viestihaku = scanner.nextLine();
//                int viestiz = Integer.parseInt(viestihaku);
//
//                try {
//                    List<Viesti> Kokeilu2 = viestidao.etsiTietyt(viestiz);
//                    System.out.println("Yritetään etsiä yksi");
//                    for (Viesti b : Kokeilu2) {
//                        System.out.println(Kokeilu2.getViestit());
//                    }
//
//                } catch (SQLException ex) {
//                    System.out.println("Komento ei toiminut tai viestiä ei löytynyt");
//                }

                //Kokeillaan etsiKeskustelunViestit(Integer alue, Integer keskustelu , Integer sivunro)
                System.out.println("Anna alueen id jota haetaan: ");
                String viestialueid = scanner.nextLine();
                int viestialueidint = Integer.parseInt(viestitallennusId);
                System.out.println("Anna keskustelunavauksen id: ");
                String viestikeskusid = scanner.nextLine();
                int viestikeskusidint = Integer.parseInt(viestitallennusId);
                System.out.println("Anna sivunumero jolta haetaan, esim 1: ");
                String sivunro = scanner.nextLine();
                int sivuid = Integer.parseInt(viestitallennusId);

                try {
                    viestidao.etsiKeskustelunViestit(viestialueidint, viestikeskusidint, sivuid);
                    System.out.println("Yritetään etsiä sivun viestit");

                } catch (SQLException ex) {
                    System.out.println("Komento ei toiminut tai viestejä, keskustelunavausta tai aluetta ei löytynyt");
                }

                //Kokeillaan etsiAlueidenViestit()
                try {
                    viestidao.etsiAlueidenViestit();
                    System.out.println("Yritetään etsiä alueiden viestit sekä viestien lukumäärät alueissa");

                } catch (SQLException ex) {
                    System.out.println("Komento ei toiminut");
                }

                break;
            default:
                System.out.println("Anna 1,2 tai 3, muuten en toimi...");
        }

    }

}