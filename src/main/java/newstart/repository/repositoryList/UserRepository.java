package newstart.repository.repositoryList;

import java.util.ArrayList;
import java.util.List;

import newstart.entities.User;
import newstart.enums.Role;
import newstart.repository.implementation.RepositoryUser;

public class UserRepository extends RepositoryImplList<User> implements RepositoryUser{
    private final List<User> comptes =new ArrayList<>();

    @Override
    public List<User> findByRole(Role role){
        List<User> actifs=new ArrayList<>();
        for (User user : comptes) {
            if(user.getStatut().compareTo("Activer")==0 || user.getRole().compareTo(role)==0){
                actifs.add(user);
            }
        }
        return actifs;
    }
    
    @Override
    public User getByLogin(String login) {
        for (User user : comptes) {
            if (user != null && user.getLogin() != null && user.getLogin().equals(login)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public User getById(int userId) {
        for (User user : comptes) {
            if (user.getId()== userId) {
                return user;
            }
        }
        return null;
    }
    
    @Override
    public User getByLoginAndPassword(String login, String password) {
        for (User user : comptes) {
            if (user != null && user.getLogin() != null && user.getLogin().equals(login) && user.getPassword() != null && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
    
}
