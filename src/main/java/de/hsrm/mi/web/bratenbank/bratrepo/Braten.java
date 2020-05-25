package de.hsrm.mi.web.bratenbank.bratrepo;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import de.hsrm.mi.web.bratenbank.benutzer.Benutzer;
import de.hsrm.mi.web.bratenbank.bratboerse.validator.GuteAdresse;

@Entity
public class Braten {

    @Id
    @GeneratedValue
    private int id;

    @Version
    private int version;
    
    @ManyToOne
    private Benutzer anbieter;

    @GuteAdresse
    @Column(name = "ABHOLORT")
    private String abholort;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent
    @NotNull
    @Column(name = "HALTBARBIS")
    private LocalDate haltbarbis;
    
    @Size(min=1, max=80)
    @NotNull
    @Column(name = "BESCHREIBUNG")
    private String beschreibung;

    private int[] vegWerte = {0, 25, 50, 75, 100};
    
    @Column(name = "VGRAD")
    private int vgrad;
    
    @Override
    public String toString(){
        if (abholort == null || haltbarbis == null || beschreibung == null) {
            return "BratenDaten";
        }
        return String.format("Braten von %s in %s (haltbar bis %s): %s",  anbieter.toString(), abholort, haltbarbis.toString(), beschreibung);
    }

    public Benutzer getAnbieter(){
        return this.anbieter;
    }

    public void setAnbieter(Benutzer anbieter){
        this.anbieter = anbieter;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}