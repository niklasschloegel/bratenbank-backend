package de.hsrm.mi.web.bratenbank.bratboerse;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("angebote")
public class BratenAngebotController {
    
    private Logger logger = LoggerFactory.getLogger(BratenAngebotController.class);

    @ModelAttribute("angebote")
    public void initList(Model m) {
        m.addAttribute("angebote", new ArrayList<BratenDaten>());
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