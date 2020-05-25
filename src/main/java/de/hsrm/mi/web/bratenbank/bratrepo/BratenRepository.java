package de.hsrm.mi.web.bratenbank.bratrepo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import de.hsrm.mi.web.bratenbank.benutzer.Benutzer;

public interface BratenRepository extends JpaRepository<Braten, Integer> {
    
    List<Braten> findByBeschreibungContainsIgnoringCase(String beschreibung); 
    List<Braten> findByAnbieter(Benutzer anbieter);

}