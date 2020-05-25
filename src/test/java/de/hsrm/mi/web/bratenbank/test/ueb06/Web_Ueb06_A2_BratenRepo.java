package de.hsrm.mi.web.bratenbank.test.ueb06;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import de.hsrm.mi.web.bratenbank.benutzer.Benutzer;
import de.hsrm.mi.web.bratenbank.benutzer.BenutzerRepository;
import de.hsrm.mi.web.bratenbank.bratrepo.Braten;
import de.hsrm.mi.web.bratenbank.bratrepo.BratenRepository;

@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class Web_Ueb06_A2_BratenRepo {
    private final String TESTABHOLORT = "In der Ecke 17, 99441 Vollradisroda";
	private final String TESTBESCHREIBUNG = "Leckerer vegetarischer Gummibaerenbraten";
    private final int TESTVEGETARIZITAET = 25;
    
    private final String TESTUSERLOGIN = "ben123";
    private final String TESTUSERPASSWORT = "blawupps";
    private final String TESTUSERVOLLNAME = "Ben der Nutzer";


    @Autowired
    BratenRepository bratenrepo;

    @Autowired
    BenutzerRepository benutzerrepo;

    @BeforeEach
    public void init() {
        bratenrepo.deleteAll();
        benutzerrepo.deleteAll();
    }


    public Benutzer makeBenutzer(String username, String passwort, String vollname) {
        Benutzer unmanaged = new Benutzer();
        unmanaged.setLoginname(username);
        unmanaged.setPasswort(passwort);
        unmanaged.setVollname(vollname);
        unmanaged.setNutzungsbedingungenok(true);
        return benutzerrepo.save(unmanaged);
    }


    @Test
    @DisplayName("BratenRepo: Braten speichern und laden")
    @Transactional
    public void bratenrepo_speichern_laden() {
        Benutzer ben = makeBenutzer(TESTUSERLOGIN, TESTUSERPASSWORT, TESTUSERVOLLNAME);

        Braten b = new Braten();
        b.setAbholort(TESTABHOLORT);
        b.setBeschreibung(TESTBESCHREIBUNG);
        b.setHaltbarbis(LocalDate.now().plusDays(1));
        b.setVgrad(TESTVEGETARIZITAET);
        b.setAnbieter(ben);
        Braten managed = bratenrepo.save(b);

        ben.getAngebote().add(managed);

        int bid = managed.getId();
        Braten b2 = bratenrepo.getOne(bid);

        assertThat(b2.getAbholort()).isEqualTo(TESTABHOLORT);
        assertThat(b2.getBeschreibung()).isEqualTo(TESTBESCHREIBUNG);

        assertThat(b2.getAnbieter().getLoginname()).isEqualTo(TESTUSERLOGIN);
        assertThat(b2.getAnbieter().getPasswort()).isEqualTo(TESTUSERPASSWORT);
        assertThat(b2.getAnbieter().getVollname()).isEqualTo(TESTUSERVOLLNAME);
    }

    @Test
    @DisplayName("BratenRepo: findByAnbieter()")
    @Transactional
    public void bratenrepo_viele_nutzer_und_braten() {
        final String[] beschreibungen = {
            "Das ist ein erstklassiger Braten",
            "Zweitbester Braten im Lande",
            "Mit dem dritten Braten isst man besser"
        };
    
        final int BRANZAHL = beschreibungen.length;
        final int NUTZER = 3;
        List<Benutzer> nutzer = new ArrayList<>();

        for (int n=0; n < NUTZER; n++) {
            String login = TESTUSERLOGIN+n;
            Benutzer ben = makeBenutzer(login, TESTUSERPASSWORT+n, TESTUSERVOLLNAME+n);
            nutzer.add(ben);

            for (int i=0; i < BRANZAHL; i++) {
                Braten b = new Braten();
                b.setAbholort(TESTABHOLORT);
                b.setBeschreibung(beschreibungen[i]+" von Nutzer"+i);
                b.setHaltbarbis(LocalDate.now().plusDays(i));
                b.setVgrad(TESTVEGETARIZITAET);
                b.setAnbieter(ben);
                Braten managed = bratenrepo.save(b);

                ben.getAngebote().add(managed);
            }
        }

        final int SUCHIDX = 1;
        Benutzer x = nutzer.get(SUCHIDX);
        List<Braten> xbraten = bratenrepo.findByAnbieter(x);
        assertThat(xbraten.size()).isEqualTo(BRANZAHL);
        
        final int LASTIDX = BRANZAHL-1;
        assertThat(xbraten.get(LASTIDX).getBeschreibung()).contains("Nutzer"+LASTIDX);
    }

    @Test
    @DisplayName("BratenRepo: findByBeschreibungContainingIgnoringCase()")
    @Transactional
    public void bratenrepo_suche_nach_beschreibung() {
        final String[] beschreibungen = {
            "Das ist ein erstklassiger Braten",
            "Zweitbester Braten im Lande",
            "Mit dem dritten Braten isst man besser",
            "Je vierter, desto knuspriger",
            "Alle fuenf Zutaten verbraten"
        };
    
        final int BRANZAHL = beschreibungen.length;

        Benutzer ben = makeBenutzer(TESTUSERLOGIN, TESTUSERPASSWORT, TESTUSERVOLLNAME);

        for (int i=0; i < BRANZAHL; i++) {
            Braten b = new Braten();
            b.setAbholort(TESTABHOLORT);
            b.setBeschreibung(beschreibungen[i]);
            b.setHaltbarbis(LocalDate.now().plusDays(i));
            b.setVgrad(TESTVEGETARIZITAET);
            b.setAnbieter(ben);
            Braten managed = bratenrepo.save(b);

            ben.getAngebote().add(managed);
        }

        List<Braten> gefunden_erstkl = bratenrepo.findByBeschreibungContainsIgnoringCase("erstklassig");
        assertThat(gefunden_erstkl.size()).isEqualTo(1);

        List<Braten> gefunden_braten = bratenrepo.findByBeschreibungContainsIgnoringCase("braten");
        assertThat(gefunden_braten.size()).isEqualTo(BRANZAHL - 1);

        List<Braten> gefunden_nix = bratenrepo.findByBeschreibungContainsIgnoringCase("habenwirnicht");
        assertThat(gefunden_nix.size()).isEqualTo(0);

    }

}