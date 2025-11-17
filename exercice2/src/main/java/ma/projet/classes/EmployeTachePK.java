package ma.projet.classes;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Embeddable
public class EmployeTachePK implements Serializable {

    private int employeId;
    private int tacheId;
    private Date dateDebutReelle;

    // Constructeurs
    public EmployeTachePK() {
    }

    public EmployeTachePK(int employeId, int tacheId, Date dateDebutReelle) {
        this.employeId = employeId;
        this.tacheId = tacheId;
        this.dateDebutReelle = dateDebutReelle;
    }

    // Getters et Setters
    public int getEmployeId() {
        return employeId;
    }

    public void setEmployeId(int employeId) {
        this.employeId = employeId;
    }

    public int getTacheId() {
        return tacheId;
    }

    public void setTacheId(int tacheId) {
        this.tacheId = tacheId;
    }

    public Date getDateDebutReelle() {
        return dateDebutReelle;
    }

    public void setDateDebutReelle(Date dateDebutReelle) {
        this.dateDebutReelle = dateDebutReelle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeTachePK that = (EmployeTachePK) o;
        return employeId == that.employeId &&
                tacheId == that.tacheId &&
                Objects.equals(dateDebutReelle, that.dateDebutReelle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeId, tacheId, dateDebutReelle);
    }
}