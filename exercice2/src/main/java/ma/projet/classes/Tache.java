package ma.projet.classes;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tache")
@NamedQueries({
        @NamedQuery(
                name = "findTachesPrixSuperieur",
                query = "SELECT t FROM Tache t WHERE t.prix > :prix"
        )
})
public class Tache {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 100)
    private String nom;

    @Column(name = "date_debut_prevue")
    @Temporal(TemporalType.DATE)
    private Date dateDebutPrevue;

    @Column(name = "date_fin_prevue")
    @Temporal(TemporalType.DATE)
    private Date dateFinPrevue;

    @Column(name = "date_debut_reelle")
    @Temporal(TemporalType.DATE)
    private Date dateDebutReelle;

    @Column(name = "date_fin_reelle")
    @Temporal(TemporalType.DATE)
    private Date dateFinReelle;

    @Column(nullable = false)
    private double prix;

    @ManyToOne
    @JoinColumn(name = "projet_id")
    private Projet projet;

    @OneToMany(mappedBy = "tache", fetch = FetchType.LAZY)
    private List<EmployeTache> employeTaches;

    // Constructeurs
    public Tache() {
    }

    public Tache(String nom, Date dateDebutPrevue, Date dateFinPrevue, double prix, Projet projet) {
        this.nom = nom;
        this.dateDebutPrevue = dateDebutPrevue;
        this.dateFinPrevue = dateFinPrevue;
        this.prix = prix;
        this.projet = projet;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Date getDateDebutPrevue() {
        return dateDebutPrevue;
    }

    public void setDateDebutPrevue(Date dateDebutPrevue) {
        this.dateDebutPrevue = dateDebutPrevue;
    }

    public Date getDateFinPrevue() {
        return dateFinPrevue;
    }

    public void setDateFinPrevue(Date dateFinPrevue) {
        this.dateFinPrevue = dateFinPrevue;
    }

    public Date getDateDebutReelle() {
        return dateDebutReelle;
    }

    public void setDateDebutReelle(Date dateDebutReelle) {
        this.dateDebutReelle = dateDebutReelle;
    }

    public Date getDateFinReelle() {
        return dateFinReelle;
    }

    public void setDateFinReelle(Date dateFinReelle) {
        this.dateFinReelle = dateFinReelle;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public Projet getProjet() {
        return projet;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
    }

    public List<EmployeTache> getEmployeTaches() {
        return employeTaches;
    }

    public void setEmployeTaches(List<EmployeTache> employeTaches) {
        this.employeTaches = employeTaches;
    }

    @Override
    public String toString() {
        return "Tache{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prix=" + prix +
                ", projet=" + (projet != null ? projet.getNom() : "null") +
                '}';
    }
}