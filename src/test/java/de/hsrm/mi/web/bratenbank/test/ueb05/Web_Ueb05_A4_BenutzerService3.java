package de.hsrm.mi.web.bratenbank.test.ueb05;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import de.hsrm.mi.web.bratenbank.benutzer.Benutzer;
import de.hsrm.mi.web.bratenbank.benutzer.BenutzerRepository;
import de.hsrm.mi.web.bratenbank.benutzer.BenutzerService;
import de.hsrm.mi.web.bratenbank.benutzer.BenutzerServiceImpl;

@SpringBootTest
public class Web_Ueb05_A4_BenutzerService3 {
    final String TESTLOGINNAME = "ottokar";
    final String TESTPASSWORT = "123456789";
    final String TESTVOLLNAME = "Herr Karl Otto";

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
    @DisplayName("BenutzerService ermittlePasswort() nutzt DB")
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

        assertThat(benutzerservice.ermittlePasswort(TESTLOGINNAME)).isEqualTo(TESTPASSWORT);
    }

    @Test
    @DisplayName("BenutzerService ermittlePasswort() nutzt Altverfahren als Fallback")
    public void benutzerservice_ermittlepw_fehlanzeige() {
        final String FEHLUSERNAME = "gibtsnichtuser";

        final Benutzer neuuser = new Benutzer();
        neuuser.setLoginname(TESTLOGINNAME);
        neuuser.setPasswort(TESTPASSWORT);
        neuuser.setVollname(TESTVOLLNAME);
        neuuser.setNutzungsbedingungenok(true);

        benutzerrepo.deleteAll();

        final Benutzer managed = benutzerservice.registriereBenutzer(neuuser);
        assertThat(managed).isEqualTo(neuuser);
        assertThat(benutzerrepo.count()).isEqualTo(1);

        assertThat(benutzerservice.ermittlePasswort(FEHLUSERNAME)).isEqualTo(FEHLUSERNAME+FEHLUSERNAME.length());
    }


    @Test
    @DisplayName("BenutzerService pruefeLogin() nutzt DB")
    public void benutzerservice_pruefelogin_db() {
        final Benutzer neuuser = new Benutzer();
        neuuser.setLoginname(TESTLOGINNAME);
        neuuser.setPasswort(TESTPASSWORT);
        neuuser.setVollname(TESTVOLLNAME);
        neuuser.setNutzungsbedingungenok(true);

        benutzerrepo.deleteAll();

        final Benutzer managed = benutzerservice.registriereBenutzer(neuuser);
        assertThat(managed).isEqualTo(neuuser);
        assertThat(benutzerrepo.count()).isEqualTo(1);

        assertThat(benutzerservice.pruefeLogin(TESTLOGINNAME, TESTPASSWORT)).isTrue();
    }

    @Test
    @DisplayName("BenutzerService pruefeLogin() nutzt Altverfahren als Fallback")
    public void benutzerservice_pruefelogin_fehlanzeige() {
        final String FEHLUSERNAME = "gibtsnichtuser";

        final Benutzer neuuser = new Benutzer();
        neuuser.setLoginname(TESTLOGINNAME);
        neuuser.setPasswort(TESTPASSWORT);
        neuuser.setVollname(TESTVOLLNAME);
        neuuser.setNutzungsbedingungenok(true);

        benutzerrepo.deleteAll();

        final Benutzer managed = benutzerservice.registriereBenutzer(neuuser);
        assertThat(managed).isEqualTo(neuuser);
        assertThat(benutzerrepo.count()).isEqualTo(1);

        assertThat(benutzerservice.pruefeLogin(FEHLUSERNAME, FEHLUSERNAME+FEHLUSERNAME.length())).isTrue();
    }

}