package newstart.repository.repositoryJpa;

import java.util.List;

import javax.persistence.NoResultException;

import newstart.entities.Client;
import newstart.entities.Dette;
import newstart.repository.implementation.RepositoryDette;

public class DetteRepositoryJpa extends RepositoryJpaImpl<Dette> implements RepositoryDette{

    public DetteRepositoryJpa() {
        super(Dette.class);
    }

    @Override
    public Client getByClient(int id) {
        try {
            return em.createQuery("SELECT d.client FROM Dette d WHERE d.client.id = :id", Client.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            // Log de l'absence de résultat
            System.out.println("Aucune dette trouvée avec l'id : " + id);
            return null;
        } catch (Exception e) {
            // Gestion des autres exceptions
            System.err.println("Erreur lors de la recherche de la dette par id : " + e.getMessage());
            throw e; // Relancer l'exception pour la gestion ultérieure
        }
    }

    @Override
    public List<Dette> findFiltre() {
        return em.createQuery("SELECT d FROM Dette d WHERE d.soldation = false", Dette.class)
                .getResultList();
    }

    @Override
    public List<Dette> getDettesByClientId(int clientId) {
        return em.createQuery("SELECT d FROM Dette d WHERE d.client.id = :id", Dette.class)
                .setParameter("id", clientId)
                .getResultList();
    }

    @Override
    public List<Dette> getDettesNonSoldeesByClientId(int clientId) {
        return em.createQuery("SELECT d FROM Dette d WHERE d.client.id = :id AND d.soldation = false", Dette.class)
                .setParameter("id", clientId)
                .getResultList();
    }

    @Override
    public List<Dette> getDettesEnCoursByClientId(int clientId) {
        // Vérifie si un clientId est fourni (différent de null ou 0)
        if (clientId != 0) {
            // Requête pour les dettes en cours d'un client spécifique
            return em.createQuery("SELECT d FROM Dette d WHERE d.client.id = :id AND d.validation = 'En cours'", Dette.class)
                    .setParameter("id", clientId)
                    .getResultList();
        } else {
            // Requête pour toutes les dettes en cours (sans filtrer par clientId)
            return em.createQuery("SELECT d FROM Dette d WHERE d.validation = 'En cours'", Dette.class)
                    .getResultList();
        }
    }


    @Override
    public void updateValidation(int id, String etat) {
        em.createQuery("UPDATE Dette d SET d.validation = :etat WHERE d.id = :id")
                .setParameter("id", id)
                .setParameter("etat", etat)
                .executeUpdate();
    }

    
}
