package de.hsrm.mi.web.bratenbank.bratservice;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.hsrm.mi.web.bratenbank.benutzer.Benutzer;
import de.hsrm.mi.web.bratenbank.benutzer.BenutzerRepository;
import de.hsrm.mi.web.bratenbank.bratrepo.Braten;
import de.hsrm.mi.web.bratenbank.bratrepo.BratenRepository;

@Service
public class BratenServiceImpl implements BratenService {

    private Logger logger = LoggerFactory.getLogger(BratenServiceImpl.class);

    @Autowired
    private BratenRepository bratenRepo;

    @Autowired 
    private BenutzerRepository benutzerRepo;

    @Override
    public List<Braten> alleBraten() {
        return bratenRepo.findAll();
    }

    @Override
    public Optional<Braten> sucheBratenMitId(int id) {
        return bratenRepo.findById(id);
    }

    @Transactional
    @Override
    public Braten editBraten(String loginname, Braten braten) throws BratenServiceException{
       
        try {
            
            Benutzer ben = benutzerRepo.findByLoginname(loginname);
            if (ben == null) {
                logger.error("Benutzer null");
                throw new BratenServiceException("Benutzer null");
            }


            braten.setAnbieter(ben);
            
            Braten bratenManaged = bratenRepo.save(braten);
            List<Braten> angebote = ben.getAngebote();
            angebote.add(bratenManaged);
            return bratenManaged;
        } catch (OptimisticLockException ole) {
            logger.warn("Transaktionsfehler");
            throw new BratenServiceException(ole);
        }
        
    }

    @Transactional
    @Override
    public void loescheBraten(int bratendatenid) {
        Optional<Braten> b = bratenRepo.findById(bratendatenid);
        if (b.isPresent()) {
            Braten braten = b.get();
            Benutzer benutzer = braten.getAnbieter();
            if (benutzer != null) benutzer.getAngebote().remove(braten);
        }
        bratenRepo.deleteById(bratendatenid);
    }
    
}