package test;




import ma.projet.classes.*;
import service.*;
import ma.projet.util.HibernateUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class test {

    public static void main(String[] args) {
        // Initialiser les services
        CategorieService categorieService = new CategorieService();
        ProduitService produitService = new ProduitService();
        CommandeService commandeService = new CommandeService();
        LigneCommandeService ligneCommandeService = new LigneCommandeService();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        try {
            System.out.println("=== CREATION DES CATEGORIES ===");
            Categorie cat1 = new Categorie("ORD", "Ordinateurs");
            Categorie cat2 = new Categorie("IMP", "Imprimantes");
            Categorie cat3 = new Categorie("ACC", "Accessoires");

            categorieService.create(cat1);
            categorieService.create(cat2);
            categorieService.create(cat3);
            System.out.println("Catégories créées avec succès");

            System.out.println("\n=== CREATION DES PRODUITS ===");
            Produit p1 = new Produit("ES12", 120, cat1);
            Produit p2 = new Produit("ZR85", 100, cat1);
            Produit p3 = new Produit("EE85", 200, cat2);
            Produit p4 = new Produit("AA12", 50, cat3);
            Produit p5 = new Produit("BB15", 150, cat2);

            produitService.create(p1);
            produitService.create(p2);
            produitService.create(p3);
            produitService.create(p4);
            produitService.create(p5);
            System.out.println("Produits créés avec succès");

            System.out.println("\n=== CREATION DES COMMANDES ===");
            Commande cmd1 = new Commande(sdf.parse("14/03/2013"));
            Commande cmd2 = new Commande(sdf.parse("20/05/2013"));
            Commande cmd3 = new Commande(sdf.parse("10/06/2013"));

            commandeService.create(cmd1);
            commandeService.create(cmd2);
            commandeService.create(cmd3);
            System.out.println("Commandes créées avec succès");

            System.out.println("\n=== CREATION DES LIGNES DE COMMANDE ===");
            // Commande 1
            LigneCommande lc1 = new LigneCommande(cmd1, p1, 7);
            LigneCommande lc2 = new LigneCommande(cmd1, p2, 14);
            LigneCommande lc3 = new LigneCommande(cmd1, p3, 5);

            // Commande 2
            LigneCommande lc4 = new LigneCommande(cmd2, p1, 3);
            LigneCommande lc5 = new LigneCommande(cmd2, p5, 2);

            // Commande 3
            LigneCommande lc6 = new LigneCommande(cmd3, p3, 10);

            ligneCommandeService.create(lc1);
            ligneCommandeService.create(lc2);
            ligneCommandeService.create(lc3);
            ligneCommandeService.create(lc4);
            ligneCommandeService.create(lc5);
            ligneCommandeService.create(lc6);
            System.out.println("Lignes de commande créées avec succès");

            // TEST 1: Afficher la liste des produits par catégorie
            System.out.println("\n=== TEST 1: PRODUITS PAR CATEGORIE ===");
            System.out.println("Produits de la catégorie 'Ordinateurs':");
            List<Produit> produitsOrdinateurs = produitService.findByCategorie(cat1);
            for (Produit p : produitsOrdinateurs) {
                System.out.println("  - " + p.getReference() + " | Prix: " + p.getPrix() + " DH");
            }

            // TEST 2: Afficher les produits commandés entre deux dates
            System.out.println("\n=== TEST 2: PRODUITS COMMANDES ENTRE DEUX DATES ===");
            Date dateDebut = sdf.parse("01/03/2013");
            Date dateFin = sdf.parse("31/05/2013");
            System.out.println("Produits commandés entre " + sdf.format(dateDebut) + " et " + sdf.format(dateFin) + ":");
            List<Produit> produitsParDate = produitService.findProduitsCommandesEntreDates(dateDebut, dateFin);
            for (Produit p : produitsParDate) {
                System.out.println("  - " + p.getReference() + " | Prix: " + p.getPrix() + " DH");
            }

            // TEST 3: Afficher les produits commandés dans une commande donnée
            System.out.println("\n=== TEST 3: PRODUITS D'UNE COMMANDE ===");
            afficherProduitsCommande(cmd1, ligneCommandeService);

            // TEST 4: Afficher les produits dont le prix est supérieur à 100 DH
            System.out.println("\n=== TEST 4: PRODUITS AVEC PRIX > 100 DH (Requête nommée) ===");
            List<Produit> produitsCher = produitService.findProduitsPrixSuperieur(100);
            for (Produit p : produitsCher) {
                System.out.println("  - " + p.getReference() + " | Prix: " + p.getPrix() + " DH");
            }

            // TEST 5: Afficher toutes les commandes
            System.out.println("\n=== TEST 5: TOUTES LES COMMANDES ===");
            List<Commande> commandes = commandeService.findAll();
            for (Commande cmd : commandes) {
                System.out.println("\nCommande ID: " + cmd.getId() + " | Date: " + sdf.format(cmd.getDate()));
                afficherProduitsCommande(cmd, ligneCommandeService);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Fermer la SessionFactory
            HibernateUtil.shutdown();
        }
    }

    /**
     * Méthode utilitaire pour afficher les produits d'une commande
     */
    private static void afficherProduitsCommande(Commande commande, LigneCommandeService ligneCommandeService) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");

        System.out.println("Commande : " + commande.getId() + "     Date : " + sdf.format(commande.getDate()));
        System.out.println("Liste des produits :");
        System.out.println(String.format("%-12s %-12s %s", "Référence", "Prix", "Quantité"));
        System.out.println("----------------------------------------");

        List<LigneCommande> lignes = ligneCommandeService.findByCommande(commande);
        for (LigneCommande lc : lignes) {
            Produit p = lc.getProduit();
            System.out.println(String.format("%-12s %-12s %d",
                    p.getReference(),
                    p.getPrix() + " DH",
                    lc.getQuantite()));
        }
    }
}