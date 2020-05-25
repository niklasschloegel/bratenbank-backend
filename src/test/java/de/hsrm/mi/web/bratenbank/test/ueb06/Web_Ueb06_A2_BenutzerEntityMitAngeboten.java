package de.hsrm.mi.web.bratenbank.test.ueb06;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;

import java.util.List;

import javax.validation.Validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import de.hsrm.mi.web.bratenbank.benutzer.Benutzer;
import de.hsrm.mi.web.bratenbank.bratrepo.Braten;

@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class Web_Ueb06_A2_BenutzerEntityMitAngeboten {
    @Autowired
    Validator validator;


    @Test
    @DisplayName("Benutzer hat angebote-Attribut mit Liste")
    public void benutzer_hat_angebote_liste() {
        Benutzer b = new Benutzer();
        assertThat(b.getAngebote()).isInstanceOf(List.class);
    }

    @Test
    @DisplayName("Benutzer angebote-Attribut - Liste leer initialisiert")
    public void benutzer_hat_angebote_liste_leer() {
        Benutzer b = new Benutzer();
        assertThat(b.getAngebote().isEmpty()).isTrue();
    }


    @Test
    @DisplayName("Benutzer angebote-Attribut - Liste ist Braten-hinzufügbar")
    public void benutzer_hat_angebote_liste_braten_einf() {
        Benutzer b = new Benutzer();
        assertThat(b.getAngebote().isEmpty()).isTrue();
        b.getAngebote().add(new Braten());
    }

}