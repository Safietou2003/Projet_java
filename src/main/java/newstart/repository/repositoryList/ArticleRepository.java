package newstart.repository.repositoryList;

import java.util.ArrayList;
import java.util.List;

import newstart.entities.Article;
import newstart.repository.implementation.RepositoryArticle;

public  class ArticleRepository extends RepositoryImplList<Article> implements RepositoryArticle{
    
    @Override
    public List<Article> findByDispo(){
        List<Article> dispos =new ArrayList<>();
        for (Article article : datas) {
            if(article.getQteStock() != 0){
                dispos.add(article);
            }
        }
        return dispos;
    }

    @Override
    public Article getByLibelle(String libelle){
        for (Article article : datas) {
            if(article.getLibelle().compareTo(libelle)==0){
                return article;
            }
        }
        return null;
    }
}