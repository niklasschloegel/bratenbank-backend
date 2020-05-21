package de.hsrm.mi.web.bratenbank.test.ueb05;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

import de.hsrm.mi.web.bratenbank.benutzer.BenutzerService;

@SpringBootTest
public class Web_Ueb05_A1_BenutzerService1 {
    @Autowired
    private BenutzerService benutzerservice;

    @Test
    public void vorabcheck() {
        assertThat(BenutzerService.class).isInterface();
        assertThat(benutzerservice).isNotNull();
    }

    @Test
    @DisplayName("Benutzerservice generatePassword()")
    public void benutzerservice_generatePassword() {
        assertThat(benutzerservice.ermittlePasswort("abc")).isEqualTo("abc3");
        assertThat(benutzerservice.ermittlePasswort("geheim")).isEqualTo("geheim6");
        assertThat(benutzerservice.ermittlePasswort("")).isEqualTo("0");
    }

    @Test
    @DisplayName("Benutzerservice checkLogin() ok")
    public void benutzerservice_checkLogin_ok() {
        assertThat(benutzerservice.pruefeLogin("willi", "willi5")).isTrue();
        assertThat(benutzerservice.pruefeLogin("schnorchelnasenbaer", "schnorchelnasenbaer19")).isTrue();
    }

    @Test
    @DisplayName("Benutzerservice checkLogin() ok")
    public void benutzerservice_checkLogin_falsch() {
        assertThat(benutzerservice.pruefeLogin("willi", "willi")).isFalse();
        assertThat(benutzerservice.pruefeLogin("willi", "willi4")).isFalse();
        assertThat(benutzerservice.pruefeLogin("willi", "willi6")).isFalse();

    }
}