package de.hsrm.mi.web.bratenbank.benutzer;

public interface BenutzerService {
    boolean pruefeLogin(String loginname, String passwort);
    String ermittlePasswort(String loginname);
    Benutzer registriereBenutzer(Benutzer neubenutzer) throws BenutzernameSchonVergeben;
    Benutzer findeBenutzer(String loginname);
}