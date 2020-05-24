package de.hsrm.mi.web.bratenbank.benutzerui;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import de.hsrm.mi.web.bratenbank.benutzer.Benutzer;
import de.hsrm.mi.web.bratenbank.benutzer.BenutzerService;
import de.hsrm.mi.web.bratenbank.benutzer.BenutzernameSchonVergeben;

@Controller
public class BenutzerController{

    private Logger logger = LoggerFactory.getLogger(BenutzerController.class);

    @Autowired BenutzerService benutzerService;

    @GetMapping("/benutzer")
    public String firstVisit(Model m){
        m.addAttribute("benutzer", new Benutzer());
        return "benutzerui/benutzerregistrierung";
    }

    @PostMapping("/benutzer")
    public String signup(Model m, @Valid @ModelAttribute("benutzer") Benutzer benutzer,
                            BindingResult result){
        
        m.addAttribute("benutzer", benutzer);

        if (!benutzer.isNutzungsbedingungenok() || result.hasErrors()){
            logger.warn("Fehlerhafte Registrierung für Benutzer " + benutzer.toString());
            return "benutzerui/benutzerregistrierung";
        }
        try {
            benutzerService.registriereBenutzer(benutzer);
        } catch (BenutzernameSchonVergeben bsv) {
            benutzer.setLoginname("");
            m.addAttribute("benutzer", benutzer);
            return "benutzerui/benutzerregistrierung";
        }
        return "/login";
    }
}