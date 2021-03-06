package de.hsrm.mi.web.bratenbank.bratui;

import java.security.Principal;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import de.hsrm.mi.web.bratenbank.benutzer.Benutzer;
import de.hsrm.mi.web.bratenbank.benutzer.BenutzerService;
import de.hsrm.mi.web.bratenbank.bratrepo.Braten;
import de.hsrm.mi.web.bratenbank.bratservice.BratenService;
import de.hsrm.mi.web.bratenbank.bratservice.BratenServiceException;

@Controller
@RequestMapping("/braten")
public class BratenWebController {

    private Logger logger = LoggerFactory.getLogger(BratenWebController.class);

    @Autowired
    private BratenService bratenService;

    @Autowired
    private BenutzerService benutzerService;

    @GetMapping("/angebot")
    public String showListe(Model m) {
        m.addAttribute("angebote", bratenService.alleBraten());
        return "braten/liste";
    }

    @PostMapping("/angebot/neu")
    public String getNewEntry(Model m, Principal login,
                                @Valid @ModelAttribute("angebotform") Braten braten,
                                BindingResult result) {

        if (result.hasErrors()) {
            logger.warn("Fehler beim editieren eines Bratens");
            return "braten/bearbeiten";
        }

        try {
            bratenService.editBraten(login.getName(), braten);
        } catch (BratenServiceException bse) {
            logger.warn("Beim editieren des Bratens ist ein Fehler mit der Datenbank aufgetreten.");
            return "braten/bearbeiten";
        }

        return "redirect:/braten/angebot";
    }

    @GetMapping("/angebot/neu")
    public String addNewEntry(Model m) {
        m.addAttribute("angebotform", new Braten());
        return "braten/bearbeiten";
    }

    @GetMapping("/angebot/{id}/del")
    public String deleteEntry(Model m, @PathVariable("id") int id) {
        bratenService.loescheBraten(id);
        return "redirect:/braten/angebot";
    }

    @GetMapping("/angebot/{id}")
    public String editEntry(Model m, @PathVariable("id") int id) {
        Optional<Braten> braten = bratenService.sucheBratenMitId(id);
        if (braten.isPresent()) {
            m.addAttribute("angebotform", braten.get());
            return "braten/bearbeiten";
        }
        logger.warn("Braten wurde nicht gefunden.");
        return "redirect:/braten/angebot";
    }
}