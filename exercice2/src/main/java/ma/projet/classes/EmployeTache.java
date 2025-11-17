package ma.projet.classes;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "employe_tache")
public class EmployeTache {

    @EmbeddedId
    private EmployeTachePK id;

    @ManyToOne
    @MapsId("employeId")
    @JoinColumn(name = "employe_id")
    private Employe employe;

    @ManyToOne
    @MapsId("tacheId")
    @JoinColumn(name = "tache_id")
    private Tache tache;

    @Column(name = "date_fin_reelle")
    @Temporal(TemporalType.DATE)
    private Date dateFinReelle;

    // Constructeurs
    public EmployeTache() {
    }

    public EmployeTache(Employe employe, Tache tache, Date dateDebutReelle, Date dateFinReelle) {
        this.id = new EmployeTachePK(employe.getId(), tache.getId(), dateDebutReelle);
        this.employe = employe;
        this.tache = tache;
        this.dateFinReelle = dateFinReelle;
    }

    // Getters et Setters
    public EmployeTachePK getId() {
        return id;
    }

    public void setId(EmployeTachePK id) {
        this.id = id;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public Tache getTache() {
        return tache;
    }

    public void setTache(Tache tache) {
        this.tache = tache;
    }

    public Date getDateFinReelle() {
        return dateFinReelle;
    }

    public void setDateFinReelle(Date dateFinReelle) {
        this.dateFinReelle = dateFinReelle;
    }

    public Date getDateDebutReelle() {
        return id != null ? id.getDateDebutReelle() : null;
    }

    @Override
    public String toString() {
        return "EmployeTache{" +
                "employe=" + (employe != null ? employe.getNom() : "null") +
                ", tache=" + (tache != null ? tache.getNom() : "null") +
                ", dateDebutReelle=" + (id != null ? id.getDateDebutReelle() : "null") +
                ", dateFinReelle=" + dateFinReelle +
                '}';
    }
}