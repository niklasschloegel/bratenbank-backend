package de.hsrm.mi.web.bratenbank.benutzer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import de.hsrm.mi.web.bratenbank.bratrepo.Braten;

@Entity
@Table(name = "BENUTZER")
public class Benutzer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private long id;

    @Version
    private long version;

    @Column(name = "LOGINNAME", unique = true)
    @NotNull
    @Size(min = 2)
    private String loginname;

    @Column(name = "PASSWORT")
    @NotNull
    @Size(min = 3)
    private String passwort;

    @Column(name = "VOLLNAME")
    @NotNull
    @Size(min = 1)
    private String vollname;

    @Column(name = "NUTZUNGSBEDINGUNGENOK")
    private boolean nutzungsbedingungenok;

    @OneToMany
    private List<Braten> angebote = new ArrayList<>();

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((loginname == null) ? 0 : loginname.hashCode());
        result = prime * result + (nutzungsbedingungenok ? 1231 : 1237);
        result = prime * result + ((passwort == null) ? 0 : passwort.hashCode());
        result = prime * result + ((vollname == null) ? 0 : vollname.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Benutzer other = (Benutzer) obj;
        if (loginname == null) {
            if (other.loginname != null)
                return false;
        } else if (!loginname.equals(other.loginname))
            return false;
        if (nutzungsbedingungenok != other.nutzungsbedingungenok)
            return false;
        if (passwort == null) {
            if (other.passwort != null)
                return false;
        } else if (!passwort.equals(other.passwort))
            return false;
        if (vollname == null) {
            if (other.vollname != null)
                return false;
        } else if (!vollname.equals(other.vollname))
            return false;
        return true;
    }

    @Override
    public String toString(){
        return loginname;
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    public String getVollname() {
        return vollname;
    }

    public void setVollname(String vollname) {
        this.vollname = vollname;
    }

    public boolean isNutzungsbedingungenok() {
        return nutzungsbedingungenok;
    }

    public void setNutzungsbedingungenok(boolean nutzungsbedingungenok) {
        this.nutzungsbedingungenok = nutzungsbedingungenok;
    }

    public List<Braten> getAngebote() {
        return angebote;
    }

}