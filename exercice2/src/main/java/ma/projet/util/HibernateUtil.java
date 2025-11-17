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
            Properties properties = new Properties();
            File configFile = new File("src/main/resources/application.properties");

            if (configFile.exists()) {
                properties.load(new FileInputStream(configFile));
            } else {
                properties.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
                properties.setProperty("hibernate.connection.url",
                        "jdbc:mysql://localhost:3306/gestionprojets?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC");
                properties.setProperty("hibernate.connection.username", "root");
                properties.setProperty("hibernate.connection.password", "");
                properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
                properties.setProperty("hibernate.hbm2ddl.auto", "update");
                properties.setProperty("hibernate.show_sql", "true");
                properties.setProperty("hibernate.format_sql", "true");
            }

            Configuration configuration = new Configuration();
            configuration.setProperties(properties);

            // Ajouter les classes annotées
            configuration.addAnnotatedClass(ma.projet.classes.Projet.class);
            configuration.addAnnotatedClass(ma.projet.classes.Tache.class);
            configuration.addAnnotatedClass(ma.projet.classes.Employe.class);
            configuration.addAnnotatedClass(ma.projet.classes.EmployeTache.class);

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