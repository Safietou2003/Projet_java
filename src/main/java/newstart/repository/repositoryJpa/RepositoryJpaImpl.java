package newstart.repository.repositoryJpa;

import java.util.List;
import java.util.Map;

import javax.persistence.*;
import newstart.core.Repository;
import newstart.core.services.YamlService;
import newstart.core.services.impl.YamlServiceImpl;

public class RepositoryJpaImpl<T> implements Repository<T> {

    protected Class<T> entityClass;
    protected EntityManagerFactory emf;
    protected EntityManager em;
    YamlService yamlService;

    public RepositoryJpaImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
        yamlService = new YamlServiceImpl();
        Map<String, Object> mapYaml = yamlService.loadYaml();
        String persistenceUnitName = mapYaml.get("persistence").toString();
        this.emf = Persistence.createEntityManagerFactory(persistenceUnitName);
        if (this.emf != null) {
            this.em = emf.createEntityManager();
        } else {
            throw new IllegalArgumentException("No persistence unit found: " + persistenceUnitName);
        }
    }
    

    @Override
    public void insert(T objet) {
        try {
            em.getTransaction().begin();
            em.persist(objet);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Error inserting entity", e);
        }
    }

    @Override
    public List<T> findAll() {
        try {
            return em.createQuery("SELECT t FROM " + entityClass.getCanonicalName() + " t", entityClass).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Error finding all entities", e);
        }
    }

    @Override
    public T getById(int id) {
        return em.find(entityClass, id);
    }

    public void remove(T entity) {
        try {
            em.getTransaction().begin();
            if (em.contains(entity)) {
                em.remove(entity);
            } else {
                em.remove(em.merge(entity));
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Error removing entity", e);
        }
    }

    @Override
    public void remove(int id) {
        try {
            em.getTransaction().begin();
            T objet = getById(id);
            if (objet != null) {
                em.remove(objet);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Error removing entity by ID", e);
        }
    }

    @Override
    public void update(T objet) {
        try {
            em.getTransaction().begin();
            em.merge(objet);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Error updating entity", e);
        }
    }

    @Override
    public void delete(T objet) {
        remove(objet); // Réutilisation de la méthode remove(T)
    }

    public T selectBy(String fieldName, Object value) {
        try {
            return em.createQuery(
                "SELECT t FROM " + entityClass.getSimpleName() + " t WHERE t." + fieldName + " = :value", entityClass)
                .setParameter("value", value)
                .getSingleResult();
        } catch (NoResultException e) {
            return null; // Aucun résultat trouvé
        } catch (Exception e) {
            throw new RuntimeException("Error selecting entity by " + fieldName, e);
        }
    }

    public void close() {
        if (em != null) {
            em.close();
        }
        if (emf != null) {
            emf.close();
        }
    }
}


