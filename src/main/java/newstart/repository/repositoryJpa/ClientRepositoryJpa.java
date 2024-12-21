package newstart.repository.repositoryJpa;

import javax.persistence.NoResultException;

import newstart.entities.Client;
import newstart.entities.User;
import newstart.repository.implementation.RepositoryClient;

public class ClientRepositoryJpa extends RepositoryJpaImpl<Client> implements RepositoryClient {

    public ClientRepositoryJpa() {
        super(Client.class);
    }

    @Override
    public Client getByTel(String telephone) {
        try {
            return em.createQuery("SELECT c FROM Client c WHERE c.telephone = :telephone", Client.class)
                    .setParameter("telephone", telephone)
                    .getSingleResult();
        } catch (NoResultException e) {
            // Log de l'absence de résultat
            System.out.println("Aucun client trouvé avec le téléphone : " + telephone);
            return null;
        } catch (Exception e) {
            // Gestion des autres exceptions
            System.err.println("Erreur lors de la recherche du client par téléphone : " + e.getMessage());
            throw e; // Relancer l'exception pour la gestion ultérieure
        }
    }

    @Override
    public Client getClientByUser(User user) {
        try {
            return em.createQuery("SELECT c FROM Client c WHERE c.compte = :user", Client.class)
                    .setParameter("user", user)
                    .getSingleResult();
        } catch (NoResultException e) {
            // Log de l'absence de résultat
            System.out.println("Aucun client trouvé avec le compte : " + user);
            return null;
        } catch (Exception e) {
            // Gestion des autres exceptions
            System.err.println("Erreur lors de la recherche du client par compte : " + e.getMessage());
            throw e; // Relancer l'exception pour la gestion ultérieure
        }
    
    }

}
