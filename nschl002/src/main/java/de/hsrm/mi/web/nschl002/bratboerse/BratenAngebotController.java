package de.hsrm.mi.web.nschl002.bratboerse;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("angebote")
public class BratenAngebotController {
    
    private Logger logger = LoggerFactory.getLogger(BratenAngebotController.class);

    private static final String WARN_ANBIETER = "Muss zwischen 3 und 80 liegen";
    private static final String WARN_ABHOLORT = "Bitte einen Ort eingeben";
    private static final String WARN_BESCHREIBUNG = "Bitte aussagekräftige Beschreibung eingeben";
    private static final String WARN_HALTBARBIS = "Der ist ja schon abgelaufen...";

    @ModelAttribute("angebote")
    public void initList(Model m) {
        List<BratenDaten> bratenListe = new ArrayList<>();
        bratenListe.add(new BratenDaten("Jönhard", "Vollradisroda", LocalDate.of(2020, 5, 6), "leckerer Grünkohlbraten"));
        bratenListe.add(new BratenDaten("Friedfert", "Arensch", LocalDate.of(2020, 5, 31), "Palatschinkenbraten"));
        bratenListe.add(new BratenDaten("Tupel", "Wiesbaden", LocalDate.of(2020, 6, 1), "Tupel's Spezialbraten"));
        m.addAttribute("angebote", bratenListe);
    }

    @GetMapping("/angebot")
    public String showListe(Model m) {
        return "/angebote/liste";
    }

    @PostMapping("/angebot")
    public String getNewEntry(Model m, @ModelAttribute("angebote") List<BratenDaten> bratenListe,
                                @Valid @ModelAttribute("angebotform") BratenDaten bratenDaten,
                                BindingResult result) {

        if (result.hasErrors()) {


            logger.warn("Fehler beim editieren eines Bratens");
            return "/angebote/bearbeiten";
        }

        bratenListe.add(bratenDaten);
        m.addAttribute("angebote", bratenListe);
        return "/angebote/liste";
    }

    @GetMapping("/angebot/neu")
    public String addNewEntry(Model m) {
        m.addAttribute("angebotform", new BratenDaten());
        return "/angebote/bearbeiten";
    }

    @GetMapping("/angebot/{index}/del")
    public String deleteEntry(Model m, @ModelAttribute("angebote") List<BratenDaten> bratenListe,
                                @PathVariable("index") int index) {
        bratenListe.remove(index);
        m.addAttribute("angebote", bratenListe);
        return "redirect:/angebot";
    }

    @GetMapping("/angebot/{index}")
    public String editEntry(Model m, @PathVariable("index") int index, 
                            @ModelAttribute("angebote") List<BratenDaten> bratenListe) {
        m.addAttribute("angebotform", bratenListe.get(index));
        bratenListe.remove(index);
        m.addAttribute("angebote", bratenListe);
        return "/angebote/bearbeiten";
    }
}