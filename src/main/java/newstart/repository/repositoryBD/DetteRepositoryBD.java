package newstart.repository.repositoryBD;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import newstart.entities.Client;
import newstart.entities.Dette;
import newstart.repository.implementation.RepositoryClient;
import newstart.repository.implementation.RepositoryDette;

public class DetteRepositoryBD extends RepositoryImplBD<Dette> implements RepositoryDette{

    RepositoryClient clientRepository;

    public DetteRepositoryBD(RepositoryClient clientRepository) {
        this.table="Dette";
        this.clientRepository=clientRepository;
    }

    @Override
    public void insert(Dette objet) {
        try {
            var sql = String.format("INSERT INTO %s (`montant_total`, `montant_verse`, `soldation`,`client_id`) VALUES (? , ? , ? , ? )",this.table);
            this.getConnexion();
            this.initStatement(sql);
            this.ps.setDouble(1, objet.getMontantTotal());
            this.ps.setDouble(2, objet.getMontantVerse());
            this.ps.setBoolean(3, objet.getSoldation());
            this.ps.setInt(4, objet.getClient().getId());
            this.ps.executeUpdate();
            ResultSet rs = this.ps.getGeneratedKeys();
            if (rs.next()) {
                objet.setId(rs.getInt(1));
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
    public List<Dette> findAll() {
        List<Dette> dettes = new ArrayList<>();
        try {
            String sql = String.format("SELECT * FROM %s", this.table);
            this.getConnexion();
            this.initStatement(sql);
            try (ResultSet rs = this.ps.executeQuery()) {
                while (rs.next()) {
                    dettes.add(this.convertToObjet(rs));
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Erreur lors de la liste des dettes: " + e.getMessage());
        } finally {
            try {
                this.closeConnexion();
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println("Erreur de la base de données lors de la fermeture de la connexion.");
            }
        }
        return dettes;
    }

    @Override
    public Dette convertToObjet(ResultSet rs) throws SQLException {
        Dette dette = new Dette();
        dette.setId(rs.getInt("id"));
        dette.setMontantTotal(rs.getDouble("montant_total"));
        dette.setMontantVerse(rs.getDouble("montant_verse"));
        dette.setSoldation(rs.getBoolean("soldation"));
        int clientId = rs.getInt("client_id");
        Client client = this.clientRepository.getById(clientId);
        dette.setClient(client);
        return dette;
    }

    @Override
    public List<Dette> findFiltre() {
        List<Dette> dettes = new ArrayList<>();
        try {
            String sql = String.format("SELECT * FROM %s WHERE `montant_verse` != `montant_total`", this.table);
            this.getConnexion();
            this.initStatement(sql);
            try (ResultSet rs = this.ps.executeQuery()) {
                while (rs.next()) {
                    dettes.add(this.convertToObjet(rs));
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Erreur lors de la liste des dettes filtrees: " + e.getMessage());
        } finally {
            try {
                this.closeConnexion();
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println("Erreur de la base de données lors de la fermeture de la connexion.");
            }
        }
        return dettes;
    }

    @Override
    public Client getByClient(int id) {
        Client client = null;
        try {
            String sql = String.format("SELECT client_id FROM %s WHERE id = ?", this.table);
            this.getConnexion();
            this.initStatement(sql);
            this.ps.setInt(1, id);
            try (ResultSet rs = this.ps.executeQuery()) {
                if (rs.next()) {
                    int clientId = rs.getInt("client_id");
                    client = this.clientRepository.getById(clientId);
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Erreur lors de la recherche du client par id: " + e.getMessage());
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
    public List<Dette> getDettesByClientId(int clientId) {
        List<Dette> dettesByClient = new ArrayList<>();
        try {
            String sql = String.format("SELECT * FROM %s WHERE client_id = ?", this.table);
            this.getConnexion();
            this.initStatement(sql);
            this.ps.setInt(1, clientId);
            try (ResultSet rs = this.ps.executeQuery()) {
                while (rs.next()) {
                    dettesByClient.add(this.convertToObjet(rs));
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Erreur lors de la récupération des dettes par clientId: " + e.getMessage());
        } finally {
            try {
                this.closeConnexion();
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println("Erreur de la base de données lors de la fermeture de la connexion.");
            }
        }
        return dettesByClient;
    }

    @Override
    public List<Dette> getDettesEnCoursByClientId(int clientId) {
        List<Dette> dettesEnCoursByClient = new ArrayList<>();
        try {
            String sql;
            if (clientId != 0) {
                sql = String.format("SELECT * FROM %s WHERE client_id = ? AND validation = 'En cours'", this.table);
                this.getConnexion();
                this.initStatement(sql);
                this.ps.setInt(1, clientId);
            } else {
                sql = String.format("SELECT * FROM %s WHERE validation = 'En cours'", this.table);
                this.getConnexion();
                this.initStatement(sql);
            }
            try (ResultSet rs = this.ps.executeQuery()) {
                while (rs.next()) {
                    dettesEnCoursByClient.add(this.convertToObjet(rs));
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Erreur lors de la récupération des dettes en cours: " + e.getMessage());
        } finally {
            try {
                this.closeConnexion();
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println("Erreur de la base de données lors de la fermeture de la connexion.");
            }
        }
        return dettesEnCoursByClient;
    }

    @Override
    public List<Dette> getDettesNonSoldeesByClientId(int clientId) {
        List<Dette> dettesNonSoldeesByClient = new ArrayList<>();
        try {
            String sql;
            if (clientId != 0) {
                sql = String.format("SELECT * FROM %s WHERE client_id = ? AND soldation = false", this.table);
                this.getConnexion();
                this.initStatement(sql);
                this.ps.setInt(1, clientId);
            } else {
                sql = String.format("SELECT * FROM %s WHERE soldation = false", this.table);
                this.getConnexion();
                this.initStatement(sql);
            }
            try (ResultSet rs = this.ps.executeQuery()) {
                while (rs.next()) {
                    dettesNonSoldeesByClient.add(this.convertToObjet(rs));
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Erreur lors de la récupération des dettes non soldées: " + e.getMessage());
        } finally {
            try {
                this.closeConnexion();
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println("Erreur de la base de données lors de la fermeture de la connexion.");
            }
        }
        return dettesNonSoldeesByClient;
    }


    @Override
    public void updateValidation(int id, String etat) {
        try {
            String sql = String.format("UPDATE %s SET validation = ? WHERE id = ?", this.table);
            this.getConnexion();
            this.initStatement(sql);
            this.ps.setString(1, etat);
            this.ps.setInt(2, id);
            this.ps.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Erreur lors de l'update de la validation d'une dette: " + e.getMessage());
        } finally {
            try {
                this.closeConnexion();
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println("Erreur de la base de données lors de la fermeture de la connexion.");
            }
        }
    }
}
