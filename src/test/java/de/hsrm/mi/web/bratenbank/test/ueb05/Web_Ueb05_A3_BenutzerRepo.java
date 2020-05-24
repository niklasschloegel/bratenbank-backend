package de.hsrm.mi.web.bratenbank.test.ueb05;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import de.hsrm.mi.web.bratenbank.benutzer.Benutzer;
import de.hsrm.mi.web.bratenbank.benutzer.BenutzerRepository;

@SpringBootTest
public class Web_Ueb05_A3_BenutzerRepo {
    final String TESTLOGINNAME = "jockele";
    final String TESTPASSWORT = "supergeheimesjockelpasswort";
    final String TESTVOLLNAME = "Jockel Gockel";

    @Autowired
    private BenutzerRepository benutzerrepo;

    @Test
    public void vorabcheck() {
        assertThat(BenutzerRepository.class).isInterface();
        assertThat(benutzerrepo).isNotNull();
    }

    @Test
    @DisplayName("Benutzer-Entity persistieren (leert Tabelle)")
    public void benutzer_persist() {
        final Benutzer unmanaged = new Benutzer();
        unmanaged.setLoginname(TESTLOGINNAME);
        unmanaged.setPasswort(TESTPASSWORT);
        unmanaged.setVollname(TESTVOLLNAME);
        unmanaged.setNutzungsbedingungenok(true);

        benutzerrepo.deleteAll();
        final Benutzer managed = benutzerrepo.save(unmanaged);
        assertThat(managed).isEqualTo(unmanaged);

        assertThat(benutzerrepo.count()).isEqualTo(1);
    }

    @Test
    @DisplayName("BenutzerRepo doppelte Loginnamen verboten)")
    public void benutzer_loginname_eindeutig() {
        final Benutzer u1 = new Benutzer();
        u1.setLoginname(TESTLOGINNAME);
        u1.setPasswort(TESTPASSWORT);
        u1.setVollname(TESTVOLLNAME);
        u1.setNutzungsbedingungenok(true);

        benutzerrepo.deleteAll();

        final Benutzer managed1 = benutzerrepo.save(u1);
        assertThat(managed1).isEqualTo(u1);

        final Benutzer u2 = new Benutzer();
        u2.setLoginname(TESTLOGINNAME);
        u2.setPasswort("anderespasswort");
        u2.setVollname("Anderer Vollname");
        u2.setNutzungsbedingungenok(true);

        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            Benutzer managed2 = benutzerrepo.save(u2);
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