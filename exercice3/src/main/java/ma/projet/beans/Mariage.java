package ma.projet.beans;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "mariage")
public class Mariage {

    @EmbeddedId
    private MariagePK id;

    @ManyToOne
    @MapsId("hommeId")
    @JoinColumn(name = "homme_id")
    private Homme homme;

    @ManyToOne
    @MapsId("femmeId")
    @JoinColumn(name = "femme_id")
    private Femme femme;

    @Column(name = "date_fin")
    @Temporal(TemporalType.DATE)
    private Date dateFin;

    @Column(name = "nbr_enfants")
    private int nbrEnfants;

    // Constructeurs
    public Mariage() {
    }

    public Mariage(Homme homme, Femme femme, Date dateDebut, Date dateFin, int nbrEnfants) {
        this.id = new MariagePK(homme.getId(), femme.getId(), dateDebut);
        this.homme = homme;
        this.femme = femme;
        this.dateFin = dateFin;
        this.nbrEnfants = nbrEnfants;
    }

    // Getters et Setters
    public MariagePK getId() {
        return id;
    }

    public void setId(MariagePK id) {
        this.id = id;
    }

    public Homme getHomme() {
        return homme;
    }

    public void setHomme(Homme homme) {
        this.homme = homme;
    }

    public Femme getFemme() {
        return femme;
    }

    public void setFemme(Femme femme) {
        this.femme = femme;
    }

    public Date getDateDebut() {
        return id != null ? id.getDateDebut() : null;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public int getNbrEnfants() {
        return nbrEnfants;
    }

    public void setNbrEnfants(int nbrEnfants) {
        this.nbrEnfants = nbrEnfants;
    }

    public boolean isEnCours() {
        return dateFin == null;
    }

    @Override
    public String toString() {
        return "Mariage{" +
                "homme=" + (homme != null ? homme.getNom() : "null") +
                ", femme=" + (femme != null ? femme.getNom() : "null") +
                ", dateDebut=" + (id != null ? id.getDateDebut() : "null") +
                ", dateFin=" + dateFin +
                ", nbrEnfants=" + nbrEnfants +
                '}';
    }
}