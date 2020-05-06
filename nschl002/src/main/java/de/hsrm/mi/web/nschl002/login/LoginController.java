package de.hsrm.mi.web.nschl002.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    private static final String AUTHORIZED_USER = "joghurta";
    private static final String PW = "joghurta8";

    @GetMapping("/login")
    public String firstShow() {
        return "login";
    }


    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password){
        
        return "login";
    }
}