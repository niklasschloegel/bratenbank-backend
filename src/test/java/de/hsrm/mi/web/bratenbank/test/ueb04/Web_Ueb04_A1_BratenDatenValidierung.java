package de.hsrm.mi.web.bratenbank.test.ueb04;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import de.hsrm.mi.web.bratenbank.bratboerse.BratenDaten;

@SpringBootTest
@AutoConfigureMockMvc
public class Web_Ueb04_A1_BratenDatenValidierung {


    private static ValidatorFactory validatorFactory;
    
    private static Validator validator;

    @BeforeAll
    public static void setupValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    public static void closeValidatorFactory() {
        validatorFactory.close();
    }

    /*
     * Name darf nicht leer sein und muss mindestens 3 Zeichen lang sein
     */

    @Test
    @DisplayName("BratenDaten-Validierung: Name nicht leer")
    public void validateNameNichtLeer() {
        Set<ConstraintViolation<BratenDaten>> violations = validator.validateValue(
            BratenDaten.class, "name", "");
        assertFalse(violations.isEmpty());
    }


    @Test
    @DisplayName("BratenDaten-Validierung: Name ok")
    public void validateNameOk() {
        Set<ConstraintViolation<BratenDaten>> violations = validator.validateValue(
            BratenDaten.class, "name", "Aha");
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("BratenDaten-Validierung: Name zu kurz")
    public void validateNameZuKurz() {
        Set<ConstraintViolation<BratenDaten>> violations = validator.validateValue(
            BratenDaten.class, "name", "XY");
        assertFalse(violations.isEmpty());
    }

    /*
     * Beschreibung darf nicht leer und nicht laenger als 80 Zeichen sein
     */

    @Test
    @DisplayName("BratenDaten-Validierung: Beschreibung nicht leer")
    public void validateBeschreibungNichtLeer() {
        Set<ConstraintViolation<BratenDaten>> violations = validator.validateValue(
            BratenDaten.class, "beschreibung", "");
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("BratenDaten-Validierung: Beschreibung ok")
    public void validateBeschreibungOk() {
        Set<ConstraintViolation<BratenDaten>> violations = validator.validateValue(
            BratenDaten.class, "beschreibung", "Dies ist ein grossartiger Braten");
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("BratenDaten-Validierung: Beschreibung zu lang")
    public void validateBeschreibungZuLang() {
        String beschr = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo";
        assertTrue(beschr.length() > 80, "Konfigurationsfehler - Beispielstring ist zu kurz");

        Set<ConstraintViolation<BratenDaten>> violations = validator.validateValue(
            BratenDaten.class, "beschreibung", beschr);
        assertFalse(violations.isEmpty());
    }


    /*
     * Haltbar-bis darf nicht in Vergangenheit liegen
     */

    @Test
    @DisplayName("BratenDaten-Validierung: Haltbar-bis abgelaufen")
    public void validateHaltbarkeitAbgelaufen() {
        Set<ConstraintViolation<BratenDaten>> violations = validator.validateValue(
            BratenDaten.class, "haltbarbis", LocalDate.now().minusDays(1));
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("BratenDaten-Validierung: Haltbar-bis 'heute' ok'")
    public void validateHaltbarkeitHeuteOk() {
        Set<ConstraintViolation<BratenDaten>> violations = validator.validateValue(
            BratenDaten.class, "haltbarbis", LocalDate.now());
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("BratenDaten-Validierung: Haltbar-bis 'Zukunft' ok'")
    public void validateHaltbarkeitZukunftOk() {
        Set<ConstraintViolation<BratenDaten>> violations = validator.validateValue(
            BratenDaten.class, "haltbarbis", LocalDate.now().plusDays(1));
        assertTrue(violations.isEmpty());
    }

}
