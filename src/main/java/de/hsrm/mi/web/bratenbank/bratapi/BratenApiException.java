package de.hsrm.mi.web.bratenbank.bratapi;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BratenApiException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 3423038582702874312L;

    public BratenApiException() {
    }

    public BratenApiException(String message) {
        super(message);
    }

    public BratenApiException(Throwable cause) {
        super(cause);
    }

    public BratenApiException(String message, Throwable cause) {
        super(message, cause);
    }

    
    
}