package de.hsrm.mi.web.bratenbank.bratservice;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.hsrm.mi.web.bratenbank.benutzer.Benutzer;
import de.hsrm.mi.web.bratenbank.benutzer.BenutzerRepository;
import de.hsrm.mi.web.bratenbank.bratrepo.Braten;
import de.hsrm.mi.web.bratenbank.bratrepo.BratenRepository;

@Service
public class BratenServiceImpl implements BratenService {

    @Autowired
    private BratenRepository bratenRepo;

    @Autowired 
    private BenutzerRepository benutzerRepo;

    @Override
    public List<Braten> alleBraten() {
        return benutzerRepo.findAll().stream()
            .map(Benutzer::getAngebote)
            .flatMap(List::stream)
            .collect(Collectors.toList());
    }

    @Override
    public Optional<Braten> sucheBratenMitId(int id) {
        return bratenRepo.findById(id);
    }

    @Transactional
    @Override
    public Braten editBraten(String loginname, Braten braten) throws BratenServiceException{
        Benutzer ben = benutzerRepo.findByLoginname(loginname);
        
        braten.setAnbieter(ben);
        
        List<Braten> angebote = ben.getAngebote();
        angebote.remove(braten);
        angebote.add(braten);
        try {
            bratenRepo.save(braten);
            benutzerRepo.save(ben);
        } catch (OptimisticLockException ole) {
            throw new BratenServiceException(ole);
        }
        
        return braten;
    }

    @Override
    public void loescheBraten(int bratendatenid) {
        bratenRepo.deleteById(bratendatenid);
    }
    
}