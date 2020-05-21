package de.hsrm.mi.web.bratenbank.test.ueb03;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import de.hsrm.mi.web.bratenbank.bratboerse.BratenDaten;

public class Web_Ueb03_A3_BratenDaten {
    private final String TESTNAME = "Der coole Braten";
    private final String TESTABHOLORT = "In der Ecke 17, 99441 Vollradisroda";
    private final LocalDate TESTHALTBARBIS = LocalDate.now();
    private final String TESTBESCHREIBUNG = "Leckerer vegetarischer Gummibärenbraten";
    
    @Test
    @DisplayName("BratenDaten-Instanz anlegen und setter/toString() testen")
    public void bratendaten_vorhanden() {
        BratenDaten brada = new BratenDaten();
        brada.setName(TESTNAME);
        brada.setAbholort(TESTABHOLORT);
        brada.setHaltbarbis(TESTHALTBARBIS);
        brada.setBeschreibung(TESTBESCHREIBUNG);

        String tostr = brada.toString();
        assertTrue(tostr.contains(TESTNAME));
        assertTrue(tostr.contains(TESTABHOLORT));
        assertTrue(tostr.contains(TESTBESCHREIBUNG));
    }
    
}