package de.hsrm.mi.web.nschl002.bratboerse;

import java.time.LocalDate;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

public class BratenDaten {
    
    @NotNull
    @Size(min=3)
    private String name;
    
    @NotNull
    private String abholort;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Future
    private LocalDate haltbarbis;
    
    @NotNull
    @Size(max=80)
    private String beschreibung;

    public BratenDaten(){
        
    }

    public BratenDaten(String name, String abholort, LocalDate haltbarbis, String beschreibung) {
        this.name = name;
        this.abholort = abholort;
        this.haltbarbis = haltbarbis;
        this.beschreibung = beschreibung;
    } 
    
    @Override
    public String toString(){
        return String.format("Braten von %s in %s (haltbar bis %s): %s", name, abholort, haltbarbis.toString(), beschreibung);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbholort() {
        return this.abholort;
    }

    public void setAbholort(String abholort) {
        this.abholort = abholort;
    }

    public LocalDate getHaltbarbis() {
        return this.haltbarbis;
    }

    public void setHaltbarbis(LocalDate haltbarbis) {
        this.haltbarbis = haltbarbis;
    }

    public String getBeschreibung() {
        return this.beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

}