package de.hsrm.mi.web.bratenbank.benutzerapi;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BenutzerApiException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 4263521535058581384L;

    public BenutzerApiException() {
    }

    public BenutzerApiException(String message) {
        super(message);
    }

    public BenutzerApiException(Throwable cause) {
        super(cause);
    }
    
    

}