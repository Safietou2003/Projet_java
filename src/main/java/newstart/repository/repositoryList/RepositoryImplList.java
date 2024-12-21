package newstart.repository.repositoryList;

import java.util.ArrayList;
import java.util.List;

import newstart.core.Repository;

public class RepositoryImplList<T> implements Repository<T>{
    protected final List<T> datas =new ArrayList<>();
    @Override
    public void insert (T objet){
        datas.add(objet);
    }
    @Override
    public List<T> findAll(){
        return datas;
    }

    @Override
    public T getById(int userId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    @Override
    public void remove(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }
    @Override
    public void update(T objet) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }
    @Override
    public void delete(T objet) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }
}
