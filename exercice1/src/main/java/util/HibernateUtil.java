package ma.projet.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class HibernateUtil {

    private static SessionFactory sessionFactory;

    static {
        try {
            // Charger les propriétés depuis application.properties
            Properties properties = new Properties();
            File configFile = new File("src/main/resources/application.properties");

            if (configFile.exists()) {
                properties.load(new FileInputStream(configFile));
            } else {
                // Si le fichier n'existe pas, utiliser les valeurs par défaut
                properties.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
                properties.setProperty("hibernate.connection.url",
                        "jdbc:mysql://localhost:3306/gestionstock?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC");
                properties.setProperty("hibernate.connection.username", "root");
                properties.setProperty("hibernate.connection.password", "");
                properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
                properties.setProperty("hibernate.hbm2ddl.auto", "update");
                properties.setProperty("hibernate.show_sql", "true");
                properties.setProperty("hibernate.format_sql", "true");
            }

            // Créer la configuration Hibernate
            Configuration configuration = new Configuration();
            configuration.setProperties(properties);

            // Ajouter les classes annotées
            configuration.addAnnotatedClass(ma.projet.classes.Categorie.class);
            configuration.addAnnotatedClass(ma.projet.classes.Produit.class);
            configuration.addAnnotatedClass(ma.projet.classes.Commande.class);
            configuration.addAnnotatedClass(ma.projet.classes.LigneCommande.class);

            // Construire la SessionFactory
            sessionFactory = configuration.buildSessionFactory();

        } catch (IOException e) {
            System.err.println("Erreur lors du chargement du fichier de configuration: " + e.getMessage());
            e.printStackTrace();
        } catch (Throwable ex) {
            System.err.println("Échec de la création de SessionFactory: " + ex.getMessage());
            ex.printStackTrace();
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}