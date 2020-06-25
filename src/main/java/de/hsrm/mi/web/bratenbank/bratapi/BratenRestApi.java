package de.hsrm.mi.web.bratenbank.bratapi;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.hsrm.mi.web.bratenbank.bratrepo.Braten;
import de.hsrm.mi.web.bratenbank.bratservice.BratenService;
import de.hsrm.mi.web.bratenbank.bratservice.BratenServiceException;

@RestController
@RequestMapping("/api")
public class BratenRestApi {
 
    @Autowired BratenService bratenService;

    private Logger logger = LoggerFactory.getLogger(BratenRestApi.class);

    @GetMapping(value="/braten", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<Braten> getAllBraten(){
        return bratenService.alleBraten();
    }

    @GetMapping(value="/braten/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public Braten getBraten(@PathVariable int id) {
        Optional<Braten> b = bratenService.sucheBratenMitId(id);
        if (b.isPresent()) return b.get();
        throw new BratenApiException("Keinen Braten unter der ID "+id+" gefunden");
    }

    @DeleteMapping(value="/braten/{id}")
    public void deleteBraten(@PathVariable int id) {
        Optional<Braten> b = bratenService.sucheBratenMitId(id);
        if (b.isPresent()) {
            bratenService.loescheBraten(id);
        } else {
            throw new BratenApiException("Es existiert kein Braten mit der ID "+id+".");
        }
    }

    @PostMapping(value="/braten", produces=MediaType.APPLICATION_JSON_VALUE)
    public Braten saveBraten(@RequestParam String loginname, @RequestBody @Valid Braten b, BindingResult result){
        if (result.hasErrors()) throw new BratenApiException("Braten invalide: "+result.getAllErrors());
        try {
            return bratenService.editBraten(loginname, b);
        } catch (BratenServiceException bse) {
            throw new BratenApiException(bse);
        }
    }

}