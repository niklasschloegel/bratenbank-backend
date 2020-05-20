package de.hsrm.mi.web.nschl002.bratboerse;

import java.time.LocalDate;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import de.hsrm.mi.web.nschl002.bratboerse.validator.GuteAdresse;

public class BratenDaten {
    
    @Size(min=3, max=80)
    @NotNull
    private String name;
    
    @GuteAdresse
    private String abholort;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Future
    @NotNull
    private LocalDate haltbarbis;
    
    @Size(min=1, max=80)
    @NotNull
    private String beschreibung;

    private int[] vegWerte = {0, 25, 50, 75, 100};
    private int vgrad;

    public BratenDaten(){
        
    }

    public BratenDaten(String name, String abholort, LocalDate haltbarbis, String beschreibung, int vgrad) {
        this.name = name;
        this.abholort = abholort;
        this.haltbarbis = haltbarbis;
        this.beschreibung = beschreibung;
        this.vgrad = vgrad;
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

    public int[] getVegWerte(){
        return this.vegWerte;
    }

    public void setVegWerte(int[] vegWerte) {
        for (int i=0; i<this.vegWerte.length; i++){
            this.vegWerte[i] = vegWerte[i];
        }
    }

    public int getVgrad(){
        return this.vgrad;
    }

    public void setVgrad(int vgrad) {
        this.vgrad = vgrad;
    }

}