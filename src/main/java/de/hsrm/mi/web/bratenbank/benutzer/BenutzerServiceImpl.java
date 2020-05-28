package de.hsrm.mi.web.bratenbank.benutzer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BenutzerServiceImpl implements BenutzerService{

    private Logger logger = LoggerFactory.getLogger(BenutzerServiceImpl.class);

    @Autowired BenutzerRepository benutzerrepo;

    @Override
    public boolean pruefeLogin(String loginname, String passwort) {
        return passwort.equals(ermittlePasswort(loginname));
    }

    @Override
    public String ermittlePasswort(String loginname) {
        Benutzer ben = findeBenutzer(loginname);
        if (ben == null) {
            logger.info("Benutzer wurde nicht gefunden. Es wird ein Default-Passwort generiert");
            return loginname+loginname.length();
        } else {
            return ben.getPasswort();
        }
    }

    @Override
    public Benutzer registriereBenutzer(Benutzer neubenutzer) throws BenutzernameSchonVergeben {
        if (findeBenutzer(neubenutzer.getLoginname()) != null) {
            String warning = "Benutzer existiert bereits";
            logger.warn(warning);
            throw new BenutzernameSchonVergeben(warning);
        }
        return benutzerrepo.save(neubenutzer);
    }

    @Override
    public Benutzer findeBenutzer(String loginname) {
        return benutzerrepo.findByLoginname(loginname);
    }
    
}