package ma.projet.service;

import ma.projet.beans.Femme;
import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HommeService implements IDao<Homme> {

    @Override
    public boolean create(Homme o) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(o);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Homme o) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(o);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Homme o) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(o);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Homme findById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Homme.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Homme> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Homme", Homme.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Afficher les épouses d'un homme entre deux dates
     */
    public List<Femme> findEpousesEntreDates(Homme homme, Date dateDebut, Date dateFin) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT DISTINCT m.femme FROM Mariage m " +
                    "WHERE m.homme = :homme " +
                    "AND m.id.dateDebut BETWEEN :dateDebut AND :dateFin";

            Query<Femme> query = session.createQuery(hql, Femme.class);
            query.setParameter("homme", homme);
            query.setParameter("dateDebut", dateDebut);
            query.setParameter("dateFin", dateFin);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Afficher le nombre d'hommes mariés à quatre femmes entre deux dates (API Criteria)
     */
    public int countHommesMariesCriteria(int nbrFemmes, Date dateDebut, Date dateFin) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();

            // Sous-requête pour compter les mariages
            CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
            Root<Homme> hommeRoot = criteriaQuery.from(Homme.class);
            Root<Mariage> mariageRoot = criteriaQuery.from(Mariage.class);

            Predicate[] predicates = new Predicate[] {
                    cb.equal(mariageRoot.get("homme"), hommeRoot),
                    cb.between(mariageRoot.get("id").get("dateDebut"), dateDebut, dateFin)
            };

            criteriaQuery.select(cb.count(hommeRoot))
                    .where(predicates)
                    .groupBy(hommeRoot.get("id"))
                    .having(cb.equal(cb.count(mariageRoot), nbrFemmes));

            List<Long> results = session.createQuery(criteriaQuery).getResultList();
            return results.size();

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Afficher les mariages d'un homme avec tous les détails
     */
    public List<Mariage> findMariagesHomme(Homme homme) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Mariage m WHERE m.homme = :homme ORDER BY m.id.dateDebut";

            Query<Mariage> query = session.createQuery(hql, Mariage.class);
            query.setParameter("homme", homme);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}