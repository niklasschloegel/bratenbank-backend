package de.hsrm.mi.web.bratenbank.test.ueb05;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import de.hsrm.mi.web.bratenbank.benutzer.Benutzer;
import de.hsrm.mi.web.bratenbank.benutzer.BenutzerRepository;
import de.hsrm.mi.web.bratenbank.benutzer.BenutzerService;
import de.hsrm.mi.web.bratenbank.benutzer.BenutzerServiceImpl;
import de.hsrm.mi.web.bratenbank.benutzer.BenutzernameSchonVergeben;

@SpringBootTest
public class Web_Ueb05_A4_BenutzerService2 {
    final String TESTLOGINNAME = "trudeliese";
    final String TESTPASSWORT = "krasswortmitsonderzeichen!";
    final String TESTVOLLNAME = "Trudel Iese";

    @Autowired
    private BenutzerRepository benutzerrepo;

    @Autowired
    private BenutzerService benutzerservice;

    @Test
    public void vorabcheck() {
        assertThat(BenutzerRepository.class).isInterface();
        assertThat(benutzerrepo).isNotNull();

        assertThat(BenutzerService.class).isInterface();
        assertThat(benutzerservice).isNotNull();
        assertThat(benutzerservice).isInstanceOf(BenutzerServiceImpl.class);
    }

    @Test
    @DisplayName("BenutzerService registrieren Neuuser")
    public void benutzer_persist() {
        final Benutzer neuuser = new Benutzer();
        neuuser.setLoginname(TESTLOGINNAME);
        neuuser.setPasswort(TESTPASSWORT);
        neuuser.setVollname(TESTVOLLNAME);
        neuuser.setNutzungsbedingungenok(true);

        benutzerrepo.deleteAll();

        final Benutzer managed = benutzerservice.registriereBenutzer(neuuser);
        assertThat(managed).isEqualTo(neuuser);

        assertThat(benutzerrepo.count()).isEqualTo(1);
    }

    @Test
    @DisplayName("BenutzerService doppelte Loginnamen -> BenutzernameSchonVergeben Exception)")
    public void benutzer_loginname_eindeutig() {
        final Benutzer u1 = new Benutzer();
        u1.setLoginname(TESTLOGINNAME);
        u1.setPasswort(TESTPASSWORT);
        u1.setVollname(TESTVOLLNAME);
        u1.setNutzungsbedingungenok(true);

        benutzerrepo.deleteAll();

        final Benutzer managed1 = benutzerservice.registriereBenutzer(u1);
        assertThat(managed1).isEqualTo(u1);

        final Benutzer u2 = new Benutzer();
        u2.setLoginname(TESTLOGINNAME);
        u2.setPasswort("anderespasswort");
        u2.setVollname("Anderer Vollname");
        u2.setNutzungsbedingungenok(true);

        Assertions.assertThrows(BenutzernameSchonVergeben.class, () -> {
            Benutzer managed2 = benutzerservice.registriereBenutzer(u2);
            assertThat(managed2).isEqualTo(u2);
        });

        assertThat(benutzerrepo.count()).isEqualTo(1);
    }


    @Test
    @DisplayName("BenutzerRepo findByLoginname)")
    public void benutzer_loginname_findByLoginname() {
        final int ANZAHL = 5;
        
        benutzerrepo.deleteAll();

        for (int i=0; i < ANZAHL; i++) {
            final Benutzer u1 = new Benutzer();
            u1.setLoginname(TESTLOGINNAME+i);
            u1.setPasswort(TESTPASSWORT+i);
            u1.setVollname(TESTVOLLNAME+i);
            u1.setNutzungsbedingungenok(true);
            benutzerrepo.save(u1);
        }
        assertThat(benutzerrepo.count()).isEqualTo(ANZAHL);

        for (int i=0; i < ANZAHL; i++) {
            Benutzer fund = benutzerrepo.findByLoginname(TESTLOGINNAME+i);
            assertThat(fund.getPasswort()).isEqualTo(TESTPASSWORT+i);
            assertThat(fund.getVollname()).isEqualTo(TESTVOLLNAME+i);
        }
    }

    
    @Test
    @DisplayName("BenutzerRepo findByLoginname() Fehlanzeige")
    public void benutzer_loginname_findByLoginname_fehlanzeige() {
        benutzerrepo.deleteAll();

        final Benutzer u1 = new Benutzer();
        u1.setLoginname(TESTLOGINNAME);
        u1.setPasswort(TESTPASSWORT);
        u1.setVollname(TESTVOLLNAME);
        u1.setNutzungsbedingungenok(true);

        benutzerrepo.save(u1);
        
        assertThat(benutzerrepo.count()).isEqualTo(1);

        Benutzer b = benutzerrepo.findByLoginname("gibtsnicht");
        assertThat(b).isNull();
    }
}