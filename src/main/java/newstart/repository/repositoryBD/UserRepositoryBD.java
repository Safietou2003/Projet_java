package newstart.repository.repositoryBD;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import newstart.entities.User;
import newstart.enums.Role;
import newstart.repository.implementation.RepositoryUser;

public class UserRepositoryBD extends RepositoryImplBD<User> implements RepositoryUser{

    public UserRepositoryBD(){
        this.table="User";
    }

    @Override
    public void insert(User user) {
        try {
            String sql = String.format("INSERT INTO %s (`login`, `password`, `role`, `statut`) VALUES ( ? , ? , ? , ? )",this.table);
            this.getConnexion();
            this.initStatement(sql);
            this.setFiels(user);
            this.ps.executeUpdate();
            ResultSet rs = this.ps.getGeneratedKeys();
            if (rs.next()) {
                user.setId(rs.getInt(1));
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
    public List<User> findByRole(Role role) {
        List<User> comptes=new ArrayList<>();
        try {
            String sql = String.format("SELECT * FROM `%s WHERE `role` like  ?",this.table);
            this.getConnexion();
            this.initStatement(sql);
            try (ResultSet rs = this.ps.executeQuery()) {
                while(rs.next()){
                    comptes.add(this.convertToObjet(rs));
                }
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Erreur de chargement du driver");
        } catch (SQLException e) {
            System.out.println("Erreur de connexion à la base de données");
        } finally {
            try {
                this.closeConnexion();
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println("Erreur de la base de données lors de la fermeture de la connexion.");
            }
        }
        return comptes;
    }

    @Override
    public User getByLogin(String login) {
        User user=null;
        try {
            String sql = String.format("SELECT * FROM %s WHERE `login` like ? ",this.table);
            this.getConnexion();
            this.initStatement(sql);
            this.ps.setString(1, login);
            try (ResultSet rs = this.ps.executeQuery()) {
                if(rs.next()){
                    user=this.convertToObjet(rs);
                }
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Erreur de chargement du driver");
        } catch (SQLException e) {
            System.out.println("Erreur de connexion à la base de données");
        } finally {
            try {
                this.closeConnexion();
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println("Erreur de la base de données lors de la fermeture de la connexion.");
            }
        }
        return user;
    }

    @Override
    public User getById(int id) {
        User user=null;
        try {
            String sql = String.format("SELECT * FROM %s WHERE `id` like ? ",this.table);
            this.getConnexion();
            this.initStatement(sql);
            this.ps.setInt(1, id);
            try (ResultSet rs = this.ps.executeQuery()) {
                if(rs.next()){
                    user=this.convertToObjet(rs);
                }
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Erreur de chargement du driver");
        } catch (SQLException e) {
            System.out.println("Erreur de connexion à la base de données");
        } finally {
            try {
                this.closeConnexion();
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println("Erreur de la base de données lors de la fermeture de la connexion.");
            }
        }
        return user;
    }

    @Override
    public User convertToObjet(ResultSet rs) throws SQLException {
        User user=new User();
        user.setId(rs.getInt("id"));
        user.setLogin(rs.getString("login"));
        user.setPassword(rs.getString("password"));
        String typeString = rs.getString("role");
        user.setRole(Role.valueOf(typeString));
        user.setStatut(rs.getString("statut"));
        return user;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try {
            String sql = String.format("SELECT * FROM %s", this.table);
            this.getConnexion();
            this.initStatement(sql);
            try (ResultSet rs = this.ps.executeQuery()) {
                while (rs.next()) {
                    users.add(this.convertToObjet(rs));
                }
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Erreur de chargement du driver");
        } catch (SQLException e) {
            System.out.println("Erreur de connexion à la base de données");
        } finally {
            try {
                this.closeConnexion();
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println("Erreur de la base de données lors de la fermeture de la connexion.");
            }
        }
        return users;
    }

    @Override
    public void delete(User user) {
        try {
            String sql = String.format("DELETE FROM %s WHERE `id` = ?", this.table);
            this.getConnexion();
            this.initStatement(sql);
            this.ps.setInt(1, user.getId());
            this.ps.executeUpdate();
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
    public void remove(int id) {
        try {
            String sql = String.format("DELETE FROM %s WHERE `id` = ?", this.table);
            this.getConnexion();
            this.initStatement(sql);
            this.ps.setInt(1, id);
            this.ps.executeUpdate();
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
    public void update(User user) {
        try {
            String sql = String.format("UPDATE %s SET `login` = ?, `password` = ?, `role` = ?, `statut` = ? WHERE `id` = ?", this.table);
            this.getConnexion();
            this.initStatement(sql);
            this.setFiels(user);
            this.ps.setInt(5, user.getId());
            this.ps.executeUpdate();
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
    public User getByLoginAndPassword(String login, String password) {
        User user = null;
        try {
            String sql = String.format("SELECT * FROM %s WHERE `login` = ? AND `password` = ?", this.table);
            this.getConnexion();
            this.initStatement(sql);
            this.ps.setString(1, login);
            this.ps.setString(2, password);
            try (ResultSet rs = this.ps.executeQuery()) {
                if (rs.next()) {
                    user = this.convertToObjet(rs);
                }
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Error loading driver");
        } catch (SQLException e) {
            System.out.println("Error connecting to the database");
        } finally {
            try {
                this.closeConnexion();
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println("Error closing database connection");
            }
        }
        return user;
    }
}
