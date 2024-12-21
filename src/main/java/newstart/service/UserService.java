package newstart.service;

import java.util.List;

import newstart.core.Repository;
import newstart.entities.User;
import newstart.enums.Role;
import newstart.repository.implementation.RepositoryUser;
import newstart.service.ServiceImplentation.ServiceUser;

public class UserService implements ServiceUser<User>{
    private final Repository<User> userRepository;

    public UserService(Repository<User> userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public void create(User user) {
        userRepository.insert(user);

    }
    @Override
    public List<User> show() {
        return userRepository.findAll();
    }
    
    @Override
    public List<User> showRole(Role role) {
        return ((RepositoryUser) userRepository).findByRole(role);
    }
    @Override
    public User getLogin(String login){
        return ((RepositoryUser) userRepository).getByLogin(login);
    }

    public void update(User user) {
        userRepository.update(user);
    }
}
