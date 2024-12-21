package newstart.repository.repositoryBD;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import newstart.entities.Article;
import newstart.repository.implementation.RepositoryArticle;

public class ArticleRepositoryBD extends RepositoryImplBD<Article> implements RepositoryArticle {

    public ArticleRepositoryBD(){
        this.table="Article";
    }

    @Override
    public void insert(Article article) {
        try {
            var sql = String.format("INSERT INTO %s (`reference`, `libelle`, `prix`, `qteStock`) VALUES ( ? , ? , ? , ? )",this.table);
            this.getConnexion();
            this.initStatement(sql);
            this.setFiels(article);
            this.ps.executeUpdate();
            ResultSet rs = this.ps.getGeneratedKeys();
            if (rs.next()) {
                article.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            System.out.println("Erreur de connexion à la base de données: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Erreur de chargement du driver: " + e.getMessage());
        } finally {
            try {
                this.closeConnexion();
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println("Erreur de la base de données lors de la fermeture de la connexion.");
            }
        }
    }

    @Override
    public List<Article> findAll() {
        List<Article> articles = new ArrayList<>();
        try {
            String sql = String.format("SELECT * FROM %s", this.table);
            this.getConnexion();
            this.initStatement(sql);
            try (ResultSet rs = this.ps.executeQuery()) {
                while (rs.next()) {
                    articles.add(this.convertToObjet(rs));
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error fetching all articles: " + e.getMessage());
        } finally {
            try {
                this.closeConnexion();
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println("Error closing database connection.");
            }
        }
        return articles;
    }

    @Override
    public Article convertToObjet(ResultSet rs) throws SQLException {
        Article article = new Article();
        article.setId(rs.getInt("id"));
        article.setReference(rs.getString("reference"));
        article.setLibelle(rs.getString("libelle"));
        article.setPrix(rs.getInt("prix"));
        article.setQteStock(rs.getInt("qteStock"));
        return article;
    }

    @Override
    public List<Article> findByDispo() {
        List<Article> dispos = new ArrayList<>();
        try {
            String sql = String.format("SELECT * FROM %s WHERE `qteStock` > 0", this.table);
            this.getConnexion();
            this.initStatement(sql);
            try (ResultSet rs = this.ps.executeQuery()) {
                while (rs.next()) {
                    dispos.add(this.convertToObjet(rs));
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error fetching all available articles: " + e.getMessage());
        } finally {
            try {
                this.closeConnexion();
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println("Error closing database connection.");
            }
        }
        return dispos;
    }

    @Override
    public Article getByLibelle(String libelle) {
        Article article = null;
        try {
            String sql = String.format("SELECT * FROM %s WHERE `libelle` = ?", this.table);
            this.getConnexion();
            this.initStatement(sql);
            this.ps.setString(1, libelle);
            try (ResultSet rs = this.ps.executeQuery()) {
                if (rs.next()) {
                    article = this.convertToObjet(rs);
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error fetching article by libelle: " + e.getMessage());
        } finally {
            try {
                this.closeConnexion();
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println("Error closing database connection.");
            }
        }
        return article;
    }
    
}
