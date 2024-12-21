package newstart.service.ServiceImplentation;

import java.util.List;

import newstart.core.Service;
import newstart.entities.Article;

public interface ServiceArticle<T> extends Service<T>{
    T getLibelle(String libelle);
    List<T> findByDispo();
    void updateArticle(Article article);
}
