package newstart.repository.repositoryJpa;

import java.util.List;

import javax.persistence.*;
import newstart.entities.User;
import newstart.enums.Role;
import newstart.repository.implementation.RepositoryUser;

public class UserRepositoryJpa extends RepositoryJpaImpl<User> implements RepositoryUser {

    public UserRepositoryJpa() {
        super(User.class);
    }

    @Override
    public List<User> findByRole(Role role) {
        // Requête JPQL pour rechercher les utilisateurs par rôle
        return em.createQuery("SELECT u FROM User u WHERE u.role = :role", User.class)
                .setParameter("role", role)
                .getResultList();
    }

    @Override
    public User getByLogin(String login) {
        // Requête JPQL pour récupérer un utilisateur par login
        try {
            return em.createQuery("SELECT u FROM User u WHERE u.login = :login", User.class)
                    .setParameter("login", login)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;  // Aucun utilisateur trouvé pour ce login
        }
    }

    @Override
    public User getByLoginAndPassword(String login, String password) {
        // Requête JPQL pour récupérer un utilisateur par login et mot de passe
        try {
            return em.createQuery("SELECT u FROM User u WHERE u.login = :login AND u.password = :password", User.class)
                    .setParameter("login", login)
                    .setParameter("password", password)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;  // Aucun utilisateur trouvé pour ce login et mot de passe
        }
    }
}
