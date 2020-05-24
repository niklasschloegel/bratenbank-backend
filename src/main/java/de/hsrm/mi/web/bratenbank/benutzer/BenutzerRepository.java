package de.hsrm.mi.web.bratenbank.benutzer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BenutzerRepository extends JpaRepository<Benutzer, Long> {
    
    Benutzer findByLoginname(String loginname);

}