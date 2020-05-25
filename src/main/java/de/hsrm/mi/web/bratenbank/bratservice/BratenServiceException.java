package de.hsrm.mi.web.bratenbank.bratservice;

public class BratenServiceException extends RuntimeException{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public BratenServiceException() {
    }

    public BratenServiceException(String message) {
        super(message);
    }

    public BratenServiceException(Throwable cause) {
        super(cause);
    }
    
    

}