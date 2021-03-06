package de.hsrm.mi.web.bratenbank.bratrepo;

import java.time.LocalDate;
import java.util.Objects;

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
import de.hsrm.mi.web.bratenbank.validation.GuteAdresse;

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

    @SuppressWarnings("JpaAttributeTypeInspection")
    private int[] vegWerte = {0, 25, 50, 75, 100};
    
    @Column(name = "VGRAD")
    private int vgrad;
    
    @Override
    public String toString(){
        if (anbieter == null || abholort == null || haltbarbis == null || beschreibung == null) {
            return "BratenDaten";
        }
        return String.format("Braten von %s in %s (haltbar bis %s): %s",  anbieter.toString(), abholort, haltbarbis.toString(), beschreibung);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Braten)) return false;
        Braten braten = (Braten) o;
        return id == braten.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
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
        System.arraycopy(vegWerte, 0, this.vegWerte, 0, this.vegWerte.length);
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

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

}