package newstart.repository.repositoryBD;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import newstart.entities.Client;
import newstart.entities.User;
import newstart.repository.implementation.RepositoryClient;
import newstart.repository.implementation.RepositoryUser;

public class ClientRepositoryBD extends RepositoryImplBD<Client> implements RepositoryClient {

    RepositoryUser userRepository;

    public ClientRepositoryBD(RepositoryUser userRepository){
        this.table="Client";
        this.userRepository=userRepository;
    }


    @Override
    public void insert(Client client) {
        User user = client.getCompte();
        try {
            if(user != null){
                userRepository.insert(user);
            }
            String sql = String.format("INSERT INTO %s (surnom, telephone, adresse,compteId) VALUES ( ? , ? , ? , ? )",this.table );
            this.getConnexion();
            this.initStatement(sql);
            this.setFiels(client);
            if(user != null){
                this.ps.setInt(4, user.getId());
            }else{
                this.ps.setNull(4, Types.INTEGER);
            }
            this.ps.executeUpdate();
            ResultSet rs = this.ps.getGeneratedKeys();
            if (rs.next()) {
                client.setId(rs.getInt(1));
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
    public Client getByTel(String telephone) {
        Client client = null;
        try {
            String sql = String.format("SELECT * FROM %s WHERE telephone like  ?",this.table);
            this.getConnexion();
            this.initStatement(sql);
            this.ps.setString(1, telephone);
            try (ResultSet rs = this.ps.executeQuery()) {
                if (rs.next()) {
                    client = this.convertToObjet(rs);
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Erreur lors de la recherche du client: " + e.getMessage());
        } finally {
            try {
                this.closeConnexion();
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println("Erreur de la base de données lors de la fermeture de la connexion.");
            }
        }
        return client;
    }

    @Override
    public Client getClientByUser(User user) {
        Client client = null;
        try {
            String sql = String.format("SELECT * FROM %s WHERE compteId = ?", this.table);
            this.getConnexion();
            this.initStatement(sql);
            this.ps.setInt(1, user.getId());
            try (ResultSet rs = this.ps.executeQuery()) {
                if (rs.next()) {
                    client = this.convertToObjet(rs);
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Erreur lors de la recherche du client: " + e.getMessage());
        } finally {
            try {
                this.closeConnexion();
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println("Erreur de la base de données lors de la fermeture de la connexion.");
            }
        }
        return client;
    }

    @Override
    public Client convertToObjet(ResultSet rs) throws SQLException{
        Client client = new Client();
        client.setId(rs.getInt("id"));
        client.setSurnom(rs.getString("surnom"));
        client.setTelephone(rs.getString("telephone"));
        client.setAdresse(rs.getString("adresse"));
        int userId = rs.getInt("compteId");
        User user = this.userRepository.getById(userId);
        client.setCompte(user);
        return client;
    }
    
    @Override
    public Client getById(int id) {
        Client client = null;
        try {
            String sql = String.format("SELECT * FROM %s WHERE compteId like  ?",this.table);
            this.getConnexion();
            this.initStatement(sql);
            this.ps.setInt(1, id);
            try (ResultSet rs = this.ps.executeQuery()) {
                if (rs.next()) {
                    client = this.convertToObjet(rs);
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Erreur lors de la recherche du client: " + e.getMessage());
        } finally {
            try {
                this.closeConnexion();
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println("Erreur de la base de données lors de la fermeture de la connexion.");
            }
        }
        return client;
    }


    @Override
    public List<Client> findAll() {
        List<Client> clients = new ArrayList<>();
        try {
            String sql = String.format("SELECT * FROM %s", this.table);
            this.getConnexion();
            this.initStatement(sql);
            try (ResultSet rs = this.ps.executeQuery()) {
                while (rs.next()) {
                    clients.add(this.convertToObjet(rs));
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Erreur lors de la liste des clients: " + e.getMessage());
        } finally {
            try {
                this.closeConnexion();
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println("Erreur de la base de données lors de la fermeture de la connexion.");
            }
        }
        return clients;
    }


    @Override
    public void delete(Client objet) {
        String sql = String.format("DELETE FROM %s WHERE id = ?", this.table);
        try {
            this.getConnexion();
            this.initStatement(sql);
            this.ps.setInt(1, objet.getId());
            this.ps.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Erreur lors de la suppression d'un client: " + e.getMessage());
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
            String sql = String.format("DELETE FROM %s WHERE id = ?", this.table);
            this.getConnexion();
            this.initStatement(sql);
            this.ps.setInt(1, id);
            this.ps.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Erreur lors de la suppression d'un client: " + e.getMessage());
        } finally {
            try {
                this.closeConnexion();
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println("Erreur de la base de données lors de la fermeture de la connexion.");
            }
        }
    }


    @Override
    public void update(Client objet) {
        try {
            String sql = String.format("UPDATE %s SET surnom = ?, telephone = ?, adresse = ? WHERE id = ?", this.table);
            this.getConnexion();
            this.initStatement(sql);
            this.ps.setString(1, objet.getSurnom());
            this.ps.setString(2, objet.getTelephone());
            this.ps.setString(3, objet.getAdresse());
            this.ps.setInt(4, objet.getId());
            this.ps.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Erreur lors de la mise  jour d'un client: " + e.getMessage());
        } finally {
            try {
                this.closeConnexion();
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println("Erreur de la base de données lors de la fermeture de la connexion.");
            }
        }
    }
}
