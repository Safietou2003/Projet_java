package newstart.repository.repositoryBD;

import java.sql.ResultSet;
import java.sql.SQLException;


import newstart.core.Repository;
import newstart.core.base.DataSourceImpl;

public abstract class RepositoryImplBD<T> extends DataSourceImpl<T> implements Repository<T>{

    protected String table;
    public abstract T convertToObjet(ResultSet rs) throws SQLException;

    @Override
    public T getById(int id) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public void remove(int id) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void update(T objet) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void delete(T objet) {
        // TODO Auto-generated method stub
        
    }

}