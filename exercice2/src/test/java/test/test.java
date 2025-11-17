package test;

import ma.projet.classes.*;
import ma.projet.service.*;
import ma.projet.util.HibernateUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class test {

    public static void main(String[] args) {
        // Initialiser les services
        EmployeService employeService = new EmployeService();
        ProjetService projetService = new ProjetService();
        TacheService tacheService = new TacheService();
        EmployeTacheService employeTacheService = new EmployeTacheService();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        try {
            System.out.println("=== CREATION DES EMPLOYES ===");
            Employe emp1 = new Employe("ALAMI", "Ahmed", "0612345678");
            Employe emp2 = new Employe("BENNANI", "Fatima", "0623456789");
            Employe emp3 = new Employe("COHEN", "David", "0634567890");

            employeService.create(emp1);
            employeService.create(emp2);
            employeService.create(emp3);
            System.out.println("Employés créés avec succès");

            System.out.println("\n=== CREATION DES PROJETS ===");
            Projet proj1 = new Projet("Gestion de stock", sdf.parse("14/01/2013"), emp1);
            Projet proj2 = new Projet("Site E-commerce", sdf.parse("01/03/2013"), emp2);

            projetService.create(proj1);
            projetService.create(proj2);
            System.out.println("Projets créés avec succès");

            System.out.println("\n=== CREATION DES TACHES ===");
            // Tâches pour Projet 1
            Tache t1 = new Tache("Analyse", sdf.parse("01/02/2013"), sdf.parse("28/02/2013"), 1500, proj1);
            Tache t2 = new Tache("Conception", sdf.parse("01/03/2013"), sdf.parse("31/03/2013"), 2000, proj1);
            Tache t3 = new Tache("Développement", sdf.parse("01/04/2013"), sdf.parse("30/04/2013"), 3000, proj1);

            // Tâches pour Projet 2
            Tache t4 = new Tache("Maquette", sdf.parse("15/03/2013"), sdf.parse("30/03/2013"), 800, proj2);
            Tache t5 = new Tache("Base de données", sdf.parse("01/04/2013"), sdf.parse("15/04/2013"), 1200, proj2);

            tacheService.create(t1);
            tacheService.create(t2);
            tacheService.create(t3);
            tacheService.create(t4);
            tacheService.create(t5);
            System.out.println("Tâches créées avec succès");

            System.out.println("\n=== AFFECTATION DES TACHES AUX EMPLOYES ===");
            // Employé 1 travaille sur les tâches du projet 1
            EmployeTache et1 = new EmployeTache(emp1, t1, sdf.parse("10/02/2013"), sdf.parse("20/02/2013"));
            EmployeTache et2 = new EmployeTache(emp2, t1, sdf.parse("10/02/2013"), sdf.parse("25/02/2013"));

            EmployeTache et3 = new EmployeTache(emp2, t2, sdf.parse("10/03/2013"), sdf.parse("15/03/2013"));
            EmployeTache et4 = new EmployeTache(emp3, t3, sdf.parse("10/04/2013"), sdf.parse("25/04/2013"));

            // Employé 2 travaille aussi sur projet 2
            EmployeTache et5 = new EmployeTache(emp2, t4, sdf.parse("20/03/2013"), sdf.parse("28/03/2013"));

            employeTacheService.create(et1);
            employeTacheService.create(et2);
            employeTacheService.create(et3);
            employeTacheService.create(et4);
            employeTacheService.create(et5);
            System.out.println("Affectations créées avec succès");

            // TEST 1: Afficher les tâches réalisées par un employé
            System.out.println("\n=== TEST 1: TACHES REALISEES PAR UN EMPLOYE ===");
            System.out.println("Tâches réalisées par " + emp2.getNom() + " " + emp2.getPrenom() + ":");
            List<Tache> tachesEmp2 = employeService.findTachesRealisees(emp2);
            for (Tache t : tachesEmp2) {
                System.out.println("  - " + t.getNom() + " (Projet: " + t.getProjet().getNom() + ")");
            }

            // TEST 2: Afficher les projets gérés par un employé
            System.out.println("\n=== TEST 2: PROJETS GERES PAR UN EMPLOYE ===");
            System.out.println("Projets gérés par " + emp1.getNom() + ":");
            List<Projet> projetsEmp1 = employeService.findProjetsGeres(emp1);
            for (Projet p : projetsEmp1) {
                System.out.println("  - " + p.getNom() + " (Début: " + sdf.format(p.getDateDebut()) + ")");
            }

            // TEST 3: Afficher les tâches planifiées pour un projet
            System.out.println("\n=== TEST 3: TACHES PLANIFIEES D'UN PROJET ===");
            afficherTachesPlanifiees(proj1, projetService, sdf);

            // TEST 4: Afficher les tâches réalisées avec dates réelles
            System.out.println("\n=== TEST 4: TACHES REALISEES AVEC DATES REELLES ===");
            afficherTachesRealisees(proj1, projetService, employeTacheService, sdf);

            // TEST 5: Tâches avec prix > 1000 DH
            System.out.println("\n=== TEST 5: TACHES AVEC PRIX > 1000 DH ===");
            List<Tache> tachesCher = tacheService.findTachesPrixSuperieur(1000);
            for (Tache t : tachesCher) {
                System.out.println("  - " + t.getNom() + " | Prix: " + t.getPrix() + " DH");
            }

            // TEST 6: Tâches réalisées entre deux dates
            System.out.println("\n=== TEST 6: TACHES REALISEES ENTRE DEUX DATES ===");
            Date dateDebut = sdf.parse("01/02/2013");
            Date dateFin = sdf.parse("31/03/2013");
            System.out.println("Tâches réalisées entre " + sdf.format(dateDebut) + " et " + sdf.format(dateFin) + ":");
            List<Tache> tachesParDate = tacheService.findTachesReellesEntreDates(dateDebut, dateFin);
            for (Tache t : tachesParDate) {
                System.out.println("  - " + t.getNom() + " (Prix: " + t.getPrix() + " DH)");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.shutdown();
        }
    }

    private static void afficherTachesPlanifiees(Projet projet, ProjetService projetService, SimpleDateFormat sdf) {
        System.out.println("Projet : " + projet.getId() + "      Nom : " + projet.getNom() +
                "     Date début : " + sdf.format(projet.getDateDebut()));
        System.out.println("Liste des tâches planifiées:");
        System.out.println(String.format("%-5s %-20s %-20s %-20s %s",
                "Num", "Nom", "Date Début Prévue", "Date Fin Prévue", "Prix"));
        System.out.println("---------------------------------------------------------------------------------");

        List<Tache> taches = projetService.findTachesPlanifiees(projet);
        for (Tache t : taches) {
            System.out.println(String.format("%-5d %-20s %-20s %-20s %.2f DH",
                    t.getId(),
                    t.getNom(),
                    sdf.format(t.getDateDebutPrevue()),
                    sdf.format(t.getDateFinPrevue()),
                    t.getPrix()));
        }
    }

    private static void afficherTachesRealisees(Projet projet, ProjetService projetService,
                                                EmployeTacheService employeTacheService, SimpleDateFormat sdf) {
        System.out.println("Projet : " + projet.getId() + "      Nom : " + projet.getNom() +
                "     Date début : " + sdf.format(projet.getDateDebut()));
        System.out.println("Liste des tâches:");
        System.out.println(String.format("%-5s %-20s %-20s %s",
                "Num", "Nom", "Date Début Réelle", "Date Fin Réelle"));
        System.out.println("---------------------------------------------------------------------");

        List<Tache> taches = projetService.findTachesRealisees(projet);
        for (Tache t : taches) {
            // Récupérer les informations de réalisation
            List<EmployeTache> ets = employeTacheService.findAll();
            for (EmployeTache et : ets) {
                if (et.getTache().getId() == t.getId()) {
                    System.out.println(String.format("%-5d %-20s %-20s %s",
                            t.getId(),
                            t.getNom(),
                            sdf.format(et.getDateDebutReelle()),
                            sdf.format(et.getDateFinReelle())));
                    break;
                }
            }
        }
    }
}