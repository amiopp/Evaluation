package ma.projet.classes;

import javax.persistence.*;

@Entity
@Table(name = "ligne_commande")
public class LigneCommande {

    @EmbeddedId
    private LigneCommandePK id;

    @ManyToOne
    @MapsId("commandeId")
    @JoinColumn(name = "commande_id")
    private Commande commande;

    @ManyToOne
    @MapsId("produitId")
    @JoinColumn(name = "produit_id")
    private Produit produit;

    @Column(nullable = false)
    private int quantite;

    // Constructeurs
    public LigneCommande() {
    }

    public LigneCommande(Commande commande, Produit produit, int quantite) {
        this.id = new LigneCommandePK(commande.getId(), produit.getId());
        this.commande = commande;
        this.produit = produit;
        this.quantite = quantite;
    }

    // Getters et Setters
    public LigneCommandePK getId() {
        return id;
    }

    public void setId(LigneCommandePK id) {
        this.id = id;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    @Override
    public String toString() {
        return "LigneCommande{" +
                "produit=" + (produit != null ? produit.getReference() : "null") +
                ", quantite=" + quantite +
                '}';
    }
}