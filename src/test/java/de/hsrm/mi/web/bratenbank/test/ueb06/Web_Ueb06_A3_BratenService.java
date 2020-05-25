package de.hsrm.mi.web.bratenbank.test.ueb06;

import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDate;
import java.util.Optional;

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
import de.hsrm.mi.web.bratenbank.bratservice.BratenService;

@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class Web_Ueb06_A3_BratenService {
    private final String TESTABHOLORT = "In der Ecke 17, 99441 Vollradisroda";
	private final String TESTBESCHREIBUNG = "Leckerer vegetarischer Gummibaerenbraten";
    private final int TESTVEGETARIZITAET = 25;
    
    private final String TESTABHOLORT2 = "Bei der Sache 12, 12345 Dahanne";
	private final String TESTBESCHREIBUNG2 = "Entsprechend essbare Sache";
    private final int TESTVEGETARIZITAET2 = 50;

    private final String TESTUSER1 = "ben123";
    private final String TESTPASS1 = "blawupps";
    private final String TESTVOLLNAME1 = "Ben der Nutzer";

    private final String TESTUSER2 = "doing";
    private final String TESTPASS2 = "doing5";
    private final String TESTVOLLNAME2 = "Ding der Dudel";


    @Autowired
    BenutzerRepository benutzerrepo;

    @Autowired
    BratenRepository bratenrepo;

    @Autowired
    BratenService bratenservice;

    @BeforeEach
    public void init() {
        bratenrepo.deleteAll();
        makeBenutzer(TESTUSER1, TESTPASS1, TESTVOLLNAME1);
        makeBenutzer(TESTUSER2, TESTPASS2, TESTVOLLNAME2);
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
    @DisplayName("BratenService: editBraten() - neu anlegen")
    @Transactional
    public void bratenservice_edit_braten_anlegen() {
        Braten b1 = new Braten();
        b1.setAbholort(TESTABHOLORT);
        b1.setBeschreibung(TESTBESCHREIBUNG);
        b1.setHaltbarbis(LocalDate.now().plusDays(1));
        b1.setVgrad(TESTVEGETARIZITAET);

        Braten b = bratenservice.editBraten(TESTUSER1, b1);

        Optional<Braten> erg = bratenrepo.findById(b.getId());
        assertThat(erg.isPresent()).isTrue();
        assertThat(erg.get().getBeschreibung()).isEqualTo(TESTBESCHREIBUNG);
        // Braten -> Anbieter
        assertThat(erg.get().getAnbieter().getVollname()).isEqualTo(TESTVOLLNAME1);
        // Rück-Richtung Anbieter -> Braten
        assertThat(erg.get().getAnbieter().getAngebote()).contains(b);
    }


    @Test
    @DisplayName("BratenService: editBraten() - aendern")
    @Transactional
    public void bratenservice_edit_braten_aendern() {
        Braten b1 = new Braten();
        b1.setAbholort(TESTABHOLORT);
        b1.setBeschreibung(TESTBESCHREIBUNG);
        b1.setHaltbarbis(LocalDate.now().plusDays(1));
        b1.setVgrad(TESTVEGETARIZITAET);

        Braten be = bratenservice.editBraten(TESTUSER2, b1);
        int be_id = be.getId();
        int be_vers = be.getVersion();

        Braten b2 = new Braten();
        b2.setId(be_id);
        b2.setVersion(be_vers);
        b2.setAbholort(TESTABHOLORT2);
        b2.setBeschreibung(TESTBESCHREIBUNG2);
        b2.setHaltbarbis(LocalDate.now().plusDays(1));
        b2.setVgrad(TESTVEGETARIZITAET2);


        Braten be2 = bratenservice.editBraten(TESTUSER2, b2);
        int be2_id = be2.getId();
        
        assertThat(be_id).isEqualTo(be2_id);

        Optional<Braten> erg = bratenrepo.findById(be2.getId());
        assertThat(erg.isPresent()).isTrue();
        assertThat(erg.get().getBeschreibung()).isEqualTo(TESTBESCHREIBUNG2);

    }


    @Test
    @DisplayName("BratenService: alleBraten()")
    @Transactional
    public void bratenservice_alle_braten() {
        Braten b1 = new Braten();
        b1.setAbholort(TESTABHOLORT);
        b1.setBeschreibung(TESTBESCHREIBUNG);
        b1.setHaltbarbis(LocalDate.now().plusDays(1));
        b1.setVgrad(TESTVEGETARIZITAET);

        Braten be = bratenservice.editBraten(TESTUSER1, b1);

        Braten b2 = new Braten();
        b2.setAbholort(TESTABHOLORT2);
        b2.setBeschreibung(TESTBESCHREIBUNG2);
        b2.setHaltbarbis(LocalDate.now().plusDays(1));
        b2.setVgrad(TESTVEGETARIZITAET2);

        Braten be2 = bratenservice.editBraten(TESTUSER2, b2);
        
        assertThat(bratenservice.alleBraten()).contains(be, be2);

    }


    @Test
    @DisplayName("BratenService: sucheBratenMitId()")
    @Transactional
    public void bratenservice_suche_braten_mit_id() {
        Braten b1 = new Braten();
        b1.setAbholort(TESTABHOLORT);
        b1.setBeschreibung(TESTBESCHREIBUNG);
        b1.setHaltbarbis(LocalDate.now().plusDays(1));
        b1.setVgrad(TESTVEGETARIZITAET);

        Braten b1s = bratenrepo.save(b1);

        Braten b2 = new Braten();
        b2.setAbholort(TESTABHOLORT2);
        b2.setBeschreibung(TESTBESCHREIBUNG2);
        b2.setHaltbarbis(LocalDate.now().plusDays(1));
        b2.setVgrad(TESTVEGETARIZITAET2);

        Braten b2s = bratenrepo.save(b2);
        
        Optional<Braten> b1such = bratenservice.sucheBratenMitId(b1s.getId());
        assertThat(b1such.isPresent()).isTrue();
        assertThat(b1such.get().getBeschreibung()).isEqualTo(TESTBESCHREIBUNG);

        Optional<Braten> b2such = bratenservice.sucheBratenMitId(b2s.getId());
        assertThat(b2such.isPresent()).isTrue();
        assertThat(b2such.get().getBeschreibung()).isEqualTo(TESTBESCHREIBUNG2);
    }


    @Test
    @DisplayName("BratenService: loescheBraten()")
    @Transactional
    public void bratenservice_loesche_braten() {
        Braten b1 = new Braten();
        b1.setAbholort(TESTABHOLORT);
        b1.setBeschreibung(TESTBESCHREIBUNG);
        b1.setHaltbarbis(LocalDate.now().plusDays(1));
        b1.setVgrad(TESTVEGETARIZITAET);

        Braten b1s = bratenrepo.save(b1);

        Braten b2 = new Braten();
        b2.setAbholort(TESTABHOLORT2);
        b2.setBeschreibung(TESTBESCHREIBUNG2);
        b2.setHaltbarbis(LocalDate.now().plusDays(2));
        b2.setVgrad(TESTVEGETARIZITAET2);

        Braten b2s = bratenrepo.save(b2);


        assertThat(bratenrepo.findById(b1s.getId()).isPresent()).isTrue();
        bratenservice.loescheBraten(b1s.getId());
        assertThat(bratenrepo.findById(b1s.getId()).isPresent()).isFalse();

        assertThat(bratenrepo.findById(b2s.getId()).isPresent()).isTrue();
        bratenservice.loescheBraten(b2s.getId());
        assertThat(bratenrepo.findById(b2s.getId()).isPresent()).isFalse();

    }



}