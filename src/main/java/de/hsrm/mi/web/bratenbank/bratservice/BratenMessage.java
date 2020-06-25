package de.hsrm.mi.web.bratenbank.bratservice;

import de.hsrm.mi.web.bratenbank.bratrepo.Braten;

public class BratenMessage {

    private String operation;
    private Braten braten;

    public BratenMessage(String operation, Braten braten) {
        this.operation = operation;
        this.braten = braten;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Braten getBraten() {
        return braten;
    }

    public void setBraten(Braten braten) {
        this.braten = braten;
    }
}
