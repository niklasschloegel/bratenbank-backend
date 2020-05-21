package de.hsrm.mi.web.bratenbank.test.ueb05;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import de.hsrm.mi.web.bratenbank.benutzer.Benutzer;

@SpringBootTest
public class Web_Ueb05_A3_BenutzerEntity {
    @Autowired
    Validator validator;

    @Test
    @DisplayName("Benutzer-Entity ok")
    public void benutzer_ok() {
        final String TESTLOGINNAME = "jockel";
        final String TESTPASSWORT = "supergeheimesjockelpasswort";
        final String TESTVOLLNAME = "Jockel Gockel";
        
        Benutzer ben = new Benutzer();
        ben.setLoginname(TESTLOGINNAME);
        ben.setPasswort(TESTPASSWORT);
        ben.setVollname(TESTVOLLNAME);
        ben.setNutzungsbedingungenok(true);

        Set<ConstraintViolation<Benutzer>> violations = validator.validate(ben);
        assertThat(violations.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Benutzer-Entity leere Felder")
    public void benutzer_name_leer() {
        Set<ConstraintViolation<Benutzer>> name_violations = validator.validateValue(Benutzer.class, "loginname", "");
        assertThat(name_violations.isEmpty()).isFalse();

        Set<ConstraintViolation<Benutzer>> passwort_violations = validator.validateValue(Benutzer.class, "passwort", "");
        assertThat(passwort_violations.isEmpty()).isFalse();

        Set<ConstraintViolation<Benutzer>> vollname_violations = validator.validateValue(Benutzer.class, "vollname", "");
        assertThat(vollname_violations.isEmpty()).isFalse();

    }

    @Test
    @DisplayName("Benutzer-Entity Mindestpasswortlaenge")
    public void benutzer_mindestpasswortlaenge() {
        Set<ConstraintViolation<Benutzer>> name_violations = validator.validateValue(Benutzer.class, "passwort", "ab");
        assertThat(name_violations.isEmpty()).isFalse();

        Set<ConstraintViolation<Benutzer>> passwort_violations = validator.validateValue(Benutzer.class, "passwort", "abc");
        assertThat(passwort_violations.isEmpty()).isTrue();
    }

}