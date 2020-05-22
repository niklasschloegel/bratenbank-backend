package de.hsrm.mi.web.bratenbank.login;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @GetMapping("/login")
    public String firstShow(Model m, Locale locale) {
        m.addAttribute("sprache", locale.getLanguage());
        return "/login";
    }


    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password,
                        Locale locale, Model m){
        String correctPW = username+username.length();
        m.addAttribute("sprache", locale.getLanguage());
        logger.info(locale.getCountry());
        if (username == null || username.isEmpty()) {
            return "/login";
        } else if (password.equals(correctPW)){
            return "redirect:/angebot"; 
        } else {
            logger.warn("Login failed for user " + username);
            m.addAttribute("username", username);
            m.addAttribute("correct_password", correctPW);
            return "/login";
        }
    
    }
}