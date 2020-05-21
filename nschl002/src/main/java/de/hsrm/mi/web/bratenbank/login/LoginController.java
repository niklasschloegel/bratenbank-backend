package de.hsrm.mi.web.bratenbank.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    private static final String AUTHORIZED_USER = "joghurta";
    private static final String PW = "joghurta8";
    private static final String HINT = "Hinweis: Das korrekte Passwort für joghurta ist joghurta8.";

    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @GetMapping("/login")
    public String firstShow() {
        return "/login";
    }


    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model m){
        if (username == null || username.isEmpty()) {
            return "/login";
        } else if (username.equals(AUTHORIZED_USER) && password.equals(PW)) {
            return "redirect:/angebot";    
        } else {
            logger.warn("Login failed for user " + username);
            m.addAttribute("hint", HINT);
            return "/login";
        }
    }
}