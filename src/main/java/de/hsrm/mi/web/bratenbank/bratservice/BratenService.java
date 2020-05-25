package de.hsrm.mi.web.bratenbank.bratservice;

import java.util.List;
import java.util.Optional;

import de.hsrm.mi.web.bratenbank.bratrepo.Braten;

public interface BratenService {
    // alle Braten-Angebote in der Datenbank
    List<Braten> alleBraten();

    // Der Braten mit der angegebenen ID
    Optional<Braten> sucheBratenMitId(int id);

    // Uebergebenen Braten fuer in der DB anlegen bzw. aktualisieren; // Bei Fehler
    // wird eigene BratenServiceException geworfen
    Braten editBraten(String loginname, Braten braten);

    // Braten mit der angegebenen ID loeschen
    void loescheBraten(int bratendatenid);
}