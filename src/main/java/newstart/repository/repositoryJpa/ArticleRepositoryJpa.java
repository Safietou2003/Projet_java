package newstart.repository.repositoryJpa;

import java.util.List;

import javax.persistence.*;
import newstart.entities.Article;
import newstart.repository.implementation.RepositoryArticle;

public class ArticleRepositoryJpa extends RepositoryJpaImpl<Article> implements RepositoryArticle {
    
    public ArticleRepositoryJpa() {
        super(Article.class);
    }

    @Override
    public List<Article> findByDispo() {
        return em.createQuery("SELECT a FROM Article a WHERE a.qteStock > 0", Article.class).getResultList();
    }

    @Override
    public Article getById(int id) {
        return em.find(Article.class, id);
    }

    @Override
    // ArticleRepositoryJpa.java
    public Article getByLibelle(String libelle) {
        try {
            return em.createQuery("SELECT a FROM Article a WHERE a.libelle = :libelle", Article.class)
                        .setParameter("libelle", libelle)
                        .getSingleResult();
        } catch (NoResultException e) {
            return null; // Retourner null si aucun article n'est trouvé
        }
    }

}
