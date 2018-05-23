package info.infomila.billar.persistence;

import info.infomila.billar.ipersistence.BillarException;
import info.infomila.billar.ipersistence.IBillar;
import info.infomila.billar.models.Soci;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

public class Billar implements IBillar
{
    private EntityManager entityManager;
    private EntityManagerFactory entityManagerFactory;

    public Billar(String nomUnitatPersistencia) throws BillarException
    {
        entityManagerFactory = null;
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory(nomUnitatPersistencia);
        } catch (PersistenceException ex) {
            throw new BillarException("Problemes en crear l'EntityManagerFactory", ex);
        }
        try {
            entityManager = entityManagerFactory.createEntityManager();
        } catch (PersistenceException ex) {
            throw new BillarException("Problemes en crear l'EntityManager", ex);
        }
    }

    @Override
    public List<Soci> getSocis() throws BillarException
    {
        return entityManager.createQuery("SELECT s from Soci s").getResultList();
    }

    @Override
    public void insertSoci(Soci soci) throws BillarException
    {
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }

        entityManager.persist(soci);

        commit();
    }

    @Override
    public void updateSoci(Soci soci) throws BillarException
    {
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }

        entityManager.merge(soci);

        commit();
    }

    @Override
    public void deleteSoci(Soci soci) throws BillarException
    {
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }

        soci.setActiu(false);

        entityManager.merge(soci);

        commit();
    }

    @Override
    public void commit() throws BillarException
    {
        if (entityManager != null && entityManager.getTransaction().isActive()) {
            try {
                entityManager.getTransaction().commit();
            } catch (Exception ex) {
                throw new BillarException("Error! No s'ha pogut fer el commit");
            }
        }
    }

    @Override
    public void rollback() throws BillarException
    {
        if (entityManager != null && entityManager.getTransaction().isActive()) {
            try {
                entityManager.getTransaction().rollback();
            } catch (Exception ex) {
                throw new BillarException("Error! No s'ha pogut fer el rollback");
            }
        }
    }

    @Override
    public void close() throws BillarException
    {
        if (entityManager.isOpen()) {
            try {
                entityManager.close();
                entityManagerFactory.close();
            } catch (Exception ex) {
                throw new BillarException("Error! No s'ha pogut fer el close");
            }
        }
    }

}
