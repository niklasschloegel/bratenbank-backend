package de.hsrm.mi.web.bratenbank.security;

import de.hsrm.mi.web.bratenbank.benutzer.Benutzer;
import de.hsrm.mi.web.bratenbank.benutzer.BenutzerRepository;
import de.hsrm.mi.web.bratenbank.benutzer.BenutzerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class BenutzerUserDetailService implements UserDetailsService {

    @Autowired
    BenutzerRepository benutzerRepo;

    @Autowired
    PasswordEncoder pwenc;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Benutzer ben = benutzerRepo.findByLoginname(username);
        if (ben == null) throw new UsernameNotFoundException(username);

        return User
                .withUsername(username)
                .password(pwenc.encode(ben.getPasswort()))
                .roles("USER")
                .build();

    }
}
