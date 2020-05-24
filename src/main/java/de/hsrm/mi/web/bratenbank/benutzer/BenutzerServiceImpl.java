package de.hsrm.mi.web.bratenbank.benutzer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BenutzerServiceImpl implements BenutzerService{

    @Autowired BenutzerRepository benutzerrepo;

    @Override
    public boolean pruefeLogin(String loginname, String passwort) {
        return passwort.equals(ermittlePasswort(loginname));
    }

    @Override
    public String ermittlePasswort(String loginname) {
        Benutzer ben = findeBenutzer(loginname);
        if (ben == null) {
            return loginname+loginname.length();
        } else {
            return ben.getPasswort();
        }
    }

    @Override
    public Benutzer registriereBenutzer(Benutzer neubenutzer) throws BenutzernameSchonVergeben {
        if (findeBenutzer(neubenutzer.getLoginname()) != null) {
            throw new BenutzernameSchonVergeben();
        }
        return benutzerrepo.save(neubenutzer);
    }

    @Override
    public Benutzer findeBenutzer(String loginname) {
        return benutzerrepo.findByLoginname(loginname);
    }
    
}