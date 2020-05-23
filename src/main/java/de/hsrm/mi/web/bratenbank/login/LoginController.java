package de.hsrm.mi.web.bratenbank.login;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import de.hsrm.mi.web.bratenbank.benutzer.BenutzerService;

@Controller
public class LoginController {

    @Autowired BenutzerService benutzerService;

    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @GetMapping("/login")
    public String firstShow(Model m, Locale locale) {
        m.addAttribute("sprache", locale.getLanguage());
        return "/login";
    }


    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password,
                        Locale locale, Model m){
        m.addAttribute("sprache", locale.getLanguage());
        logger.info(locale.getCountry());
        if (username == null || username.isEmpty()) {
            return "/login";
        } else if (benutzerService.pruefeLogin(username, password)){
            return "redirect:/angebot"; 
        } else {
            logger.warn("Login failed for user " + username);
            m.addAttribute("username", username);
            m.addAttribute("correct_password", benutzerService.ermittlePasswort(username));
            return "/login";
        }
    
    }
}