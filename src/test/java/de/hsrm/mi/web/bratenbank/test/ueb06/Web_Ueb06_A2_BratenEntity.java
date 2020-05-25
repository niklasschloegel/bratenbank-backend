package de.hsrm.mi.web.bratenbank.test.ueb06;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import de.hsrm.mi.web.bratenbank.bratrepo.Braten;

@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class Web_Ueb06_A2_BratenEntity {
    @Autowired
    Validator validator;


    @Test
    @DisplayName("Braten-Basics: Id")
    public void braten_hat_id() {
        final int TESTID = 17;
        Braten b = new Braten();
        b.setId(TESTID);
        assertThat(b.getId()).isEqualTo(TESTID);
    }



    @Test
    @DisplayName("Braten-Validierung: Beschreibung zu lang")
    public void validateBeschreibungZuLang() {
        String beschr = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo";

        Set<ConstraintViolation<Braten>> violations = validator.validateValue(
            Braten.class, "beschreibung", beschr);
        assertThat(violations.isEmpty()).isFalse();
    }


    @Test
    @DisplayName("Braten-Validierung: Haltbar-bis abgelaufen")
    public void validateHaltbarkeitAbgelaufen() {
        Set<ConstraintViolation<Braten>> violations = validator.validateValue(
            Braten.class, "haltbarbis", LocalDate.now().minusDays(1));
        assertThat(violations.isEmpty()).isFalse();
    }

    @Test
    @DisplayName("Braten-Validierung: Haltbar-bis 'heute' ok'")
    public void validateHaltbarkeitHeuteOk() {
        Set<ConstraintViolation<Braten>> violations = validator.validateValue(
            Braten.class, "haltbarbis", LocalDate.now());
        assertThat(violations.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Braten hat Anbieter-Attribut, ist mit null initialisiert")
    public void braten_anbieter() {
        Braten b = new Braten();
        assertThat(b.getAnbieter()).isNull();
    }

}