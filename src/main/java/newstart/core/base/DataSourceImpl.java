package newstart.core.base;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import lombok.Data;

@Data
public class DataSourceImpl<T> implements DataSource<T>{

    protected Connection connect;
    protected PreparedStatement ps;
    private final String user  = "postgres";
    private final String password = "seventeen";
    private final String url = "jdbc:postgresql://localhost:5432/projet_fil_rouge";


    @Override
    public void getConnexion() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            connect = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Erreur de chargement du driver MySQL", e);
        } catch (SQLException e) {
            throw new SQLException("Erreur de connexion à la base de données", e);
        }
    }

    

    @Override
    public void closeConnexion() throws ClassNotFoundException, SQLException {
        if (connect != null) {
            try {
                if (!connect.isClosed()) {
                    connect.close();
                }
            } catch (SQLException e) {
                System.out.println("Erreur lors de la fermeture de la connexion: " + e.getMessage());
            }
        }
}


    @Override
    public ResultSet executeQuery() throws ClassNotFoundException, SQLException{
        return ps.executeQuery();
    }

    @Override
    public int executeUpdate() throws ClassNotFoundException, SQLException{
        return ps.executeUpdate();
    }

    @Override
    public void initStatement(String sql) throws ClassNotFoundException, SQLException {
        if(sql.toUpperCase().trim().startsWith("INSERT")){
            ps = connect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        }else{
            ps = connect.prepareStatement(sql);
        }
    }

    @Override
    public String generateSql(String sql,String objet) throws ClassNotFoundException, SQLException{
        return String.format(sql,objet);
    }

    @Override
    public T setFiels(T objet) throws ClassNotFoundException, SQLException{
        Field[] fields = objet.getClass().getDeclaredFields();
        int i =0;
        for (Field field : fields) {
            i=i+1;
            field.setAccessible(true);
            try {
                field.set(i,field.getName());
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
            try {
                System.out.println(field.getName() + ": " + field.get(objet));
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return objet;
    }
    
    
}
