package de.hsrm.mi.web.bratenbank.benutzerapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.hsrm.mi.web.bratenbank.benutzer.Benutzer;
import de.hsrm.mi.web.bratenbank.benutzer.BenutzerService;
import de.hsrm.mi.web.bratenbank.benutzer.BenutzernameSchonVergeben;

@RestController
@RequestMapping("/api")
public class BenutzerRestApi {
    
    @Autowired BenutzerService benutzerService;

    private Logger logger = LoggerFactory.getLogger(BenutzerRestApi.class);

    @PostMapping(value="/benutzer", produces=MediaType.APPLICATION_JSON_VALUE) 
    public Benutzer postBenutzer(@RequestBody Benutzer b){
        try {
            return benutzerService.registriereBenutzer(b);
        } catch (BenutzernameSchonVergeben bsv) {
            throw new BenutzerApiException(bsv);
        }    
    }

    @GetMapping("/benutzer/{loginname}")
    public Benutzer getBenutzer(@PathVariable("loginname") String loginname) {
        logger.info("REST-Get-Request for "+loginname);
        Benutzer b = benutzerService.findeBenutzer(loginname);
        if (b != null) return b;
        throw new BenutzerApiException("Benutzer nicht gefunden");
    }
}