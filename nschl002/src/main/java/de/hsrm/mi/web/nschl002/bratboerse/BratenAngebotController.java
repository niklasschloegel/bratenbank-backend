package de.hsrm.mi.web.nschl002.bratboerse;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("angebote")
public class BratenAngebotController {
    
    @ModelAttribute("angebote")
    public void initList(Model m) {
        List<BratenDaten> bratenListe = new ArrayList<>();
        bratenListe.add(new BratenDaten("Jönhard", "Vollradisroda", LocalDate.of(2020, 5, 6), "leckerer Grünkohlbraten"));
        bratenListe.add(new BratenDaten("Friedfert", "Arensch", LocalDate.of(2020, 5, 31), "Palatschinkenbraten"));
        bratenListe.add(new BratenDaten("Tupel", "Wiesbaden", LocalDate.of(2020, 6, 1), "Tupel's Spezialbraten"));
        m.addAttribute("angebote", bratenListe);
    }

    @GetMapping("angebot")
    public String showListe(Model m) {
        return "/angebote/liste";
    }

}