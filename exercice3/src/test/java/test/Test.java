package ma.projet.test;

import ma.projet.beans.Femme;
import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.service.FemmeService;
import ma.projet.service.HommeService;
import ma.projet.service.MariageService;
import ma.projet.util.HibernateUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Test {

    public static void main(String[] args) {
        HommeService hommeService = new HommeService();
        FemmeService femmeService = new FemmeService();
        MariageService mariageService = new MariageService();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        try {
            System.out.println("=== CREATION DE 10 FEMMES ===");
            Femme f1 = new Femme("RAMI", "SALIMA", "0612345601", "Casablanca", sdf.parse("15/03/1970"));
            Femme f2 = new Femme("ALI", "AMAL", "0612345602", "Rabat", sdf.parse("20/05/1975"));
            Femme f3 = new Femme("ALAOUI", "WAFA", "0612345603", "Fès", sdf.parse("10/08/1978"));
            Femme f4 = new Femme("ALAMI", "KARIMA", "0612345604", "Marrakech", sdf.parse("25/12/1968"));
            Femme f5 = new Femme("BENNANI", "FATIMA", "0612345605", "Tanger", sdf.parse("05/01/1980"));
            Femme f6 = new Femme("COHEN", "SARAH", "0612345606", "Casablanca", sdf.parse("18/07/1982"));
            Femme f7 = new Femme("TAZI", "NADIA", "0612345607", "Rabat", sdf.parse("22/09/1976"));
            Femme f8 = new Femme("IDRISSI", "LEILA", "0612345608", "Agadir", sdf.parse("30/11/1984"));
            Femme f9 = new Femme("FASSI", "MALIKA", "0612345609", "Meknès", sdf.parse("14/04/1972"));
            Femme f10 = new Femme("SLAOUI", "AMINA", "0612345610", "Oujda", sdf.parse("08/06/1979"));

            femmeService.create(f1);
            femmeService.create(f2);
            femmeService.create(f3);
            femmeService.create(f4);
            femmeService.create(f5);
            femmeService.create(f6);
            femmeService.create(f7);
            femmeService.create(f8);
            femmeService.create(f9);
            femmeService.create(f10);
            System.out.println("10 femmes créées avec succès");

            System.out.println("\n=== CREATION DE 5 HOMMES ===");
            Homme h1 = new Homme("SAFI", "SAID", "0623456701", "Casablanca", sdf.parse("12/02/1965"));
            Homme h2 = new Homme("MANSOURI", "AHMED", "0623456702", "Rabat", sdf.parse("28/06/1970"));
            Homme h3 = new Homme("BERRADA", "KARIM", "0623456703", "Fès", sdf.parse("17/10/1968"));
            Homme h4 = new Homme("LAHLOU", "MOHAMED", "0623456704", "Tanger", sdf.parse("03/12/1972"));
            Homme h5 = new Homme("ZAKI", "YOUSSEF", "0623456705", "Marrakech", sdf.parse("21/08/1975"));

            hommeService.create(h1);
            hommeService.create(h2);
            hommeService.create(h3);
            hommeService.create(h4);
            hommeService.create(h5);
            System.out.println("5 hommes créés avec succès");

            System.out.println("\n=== CREATION DES MARIAGES ===");

            // Mariages pour h1 (SAFI SAID) - 4 femmes comme dans l'exemple
            Mariage m1 = new Mariage(h1, f4, sdf.parse("03/09/1989"), sdf.parse("03/09/1990"), 0);
            Mariage m2 = new Mariage(h1, f1, sdf.parse("03/09/1990"), null, 4);
            Mariage m3 = new Mariage(h1, f2, sdf.parse("03/09/1995"), null, 2);
            Mariage m4 = new Mariage(h1, f3, sdf.parse("04/11/2000"), null, 3);

            // Mariages pour h2
            Mariage m5 = new Mariage(h2, f5, sdf.parse("15/06/2000"), null, 3);
            Mariage m6 = new Mariage(h2, f6, sdf.parse("20/05/2005"), null, 2);

            // Mariages pour h3
            Mariage m7 = new Mariage(h3, f7, sdf.parse("10/03/1998"), sdf.parse("10/03/2008"), 2);
            Mariage m8 = new Mariage(h3, f8, sdf.parse("15/04/2010"), null, 1);

            // Mariages pour h4
            Mariage m9 = new Mariage(h4, f9, sdf.parse("22/07/2001"), null, 4);

            // Mariages pour h5 - femme mariée plusieurs fois
            Mariage m10 = new Mariage(h5, f10, sdf.parse("01/01/2003"), sdf.parse("01/01/2008"), 1);
            Mariage m11 = new Mariage(h2, f10, sdf.parse("15/06/2010"), null, 2);

            mariageService.create(m1);
            mariageService.create(m2);
            mariageService.create(m3);
            mariageService.create(m4);
            mariageService.create(m5);
            mariageService.create(m6);
            mariageService.create(m7);
            mariageService.create(m8);
            mariageService.create(m9);
            mariageService.create(m10);
            mariageService.create(m11);
            System.out.println("Mariages créés avec succès");

            // TEST 1: Afficher la liste des femmes
            System.out.println("\n=== TEST 1: LISTE DES FEMMES ===");
            List<Femme> femmes = femmeService.findAll();
            for (Femme f : femmes) {
                System.out.println(f.getNom() + " " + f.getPrenom() + " - Né(e) le: " +
                        sdf.format(f.getDateNaissance()));
            }

            // TEST 2: Afficher la femme la plus âgée
            System.out.println("\n=== TEST 2: FEMME LA PLUS ÂGEE ===");
            Femme femmePlusAgee = femmeService.findFemmePlusAgee();
            if (femmePlusAgee != null) {
                System.out.println("Femme la plus âgée: " + femmePlusAgee.getNom() + " " +
                        femmePlusAgee.getPrenom() + " - Née le: " +
                        sdf.format(femmePlusAgee.getDateNaissance()));
            }

            // TEST 3: Afficher les épouses d'un homme donné
            System.out.println("\n=== TEST 3: EPOUSES D'UN HOMME ===");
            Date dateDebut = sdf.parse("01/01/1990");
            Date dateFin = sdf.parse("31/12/2000");
            System.out.println("Épouses de " + h1.getNom() + " " + h1.getPrenom() +
                    " entre " + sdf.format(dateDebut) + " et " + sdf.format(dateFin) + ":");
            List<Femme> epouses = hommeService.findEpousesEntreDates(h1, dateDebut, dateFin);
            for (Femme f : epouses) {
                System.out.println("  - " + f.getNom() + " " + f.getPrenom());
            }

            // TEST 4: Nombre d'enfants d'une femme entre deux dates
            System.out.println("\n=== TEST 4: NOMBRE D'ENFANTS D'UNE FEMME ===");
            int nbrEnfants = femmeService.countEnfantsEntreDates(f1,
                    sdf.parse("01/01/1990"), sdf.parse("31/12/2000"));
            System.out.println("Nombre d'enfants de " + f1.getNom() + " " + f1.getPrenom() +
                    " entre 1990 et 2000: " + nbrEnfants);

            // TEST 5: Femmes mariées deux fois ou plus
            System.out.println("\n=== TEST 5: FEMMES MARIEES PLUSIEURS FOIS ===");
            List<Femme> femmesMariees = femmeService.findFemmesMarieesPlusieurssFois();
            System.out.println("Femmes mariées au moins 2 fois:");
            for (Femme f : femmesMariees) {
                System.out.println("  - " + f.getNom() + " " + f.getPrenom());
            }

            // TEST 6: Hommes mariés à quatre femmes entre deux dates
            System.out.println("\n=== TEST 6: HOMMES MARIES A 4 FEMMES ===");
            int count = hommeService.countHommesMariesCriteria(4,
                    sdf.parse("01/01/1989"), sdf.parse("31/12/2010"));
            System.out.println("Nombre d'hommes mariés à 4 femmes entre 1989 et 2010: " + count);

            // TEST 7: Afficher les mariages d'un homme avec tous les détails
            System.out.println("\n=== TEST 7: MARIAGES D'UN HOMME ===");
            afficherMariagesHomme(h1, hommeService, sdf);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.shutdown();
        }
    }

    private static void afficherMariagesHomme(Homme homme, HommeService hommeService, SimpleDateFormat sdf) {
        System.out.println("Nom : " + homme.getNom() + " " + homme.getPrenom());

        List<Mariage> mariages = hommeService.findMariagesHomme(homme);

        // Mariages en cours
        System.out.println("\nMariages En Cours :");
        int index = 1;
        for (Mariage m : mariages) {
            if (m.isEnCours()) {
                System.out.println(index + ". Femme : " + m.getFemme().getPrenom() + " " +
                        m.getFemme().getNom() + "   Date Début : " +
                        sdf.format(m.getDateDebut()) + "    Nbr Enfants : " +
                        m.getNbrEnfants());
                index++;
            }
        }

        // Mariages échoués
        System.out.println("\nMariages échoués :");
        index = 1;
        for (Mariage m : mariages) {
            if (!m.isEnCours()) {
                System.out.println(index + ". Femme : " + m.getFemme().getPrenom() + " " +
                        m.getFemme().getNom() + "  Date Début : " +
                        sdf.format(m.getDateDebut()));
                System.out.println("   Date Fin : " + sdf.format(m.getDateFin()) +
                        "    Nbr Enfants : " + m.getNbrEnfants());
                index++;
            }
        }
    }
}