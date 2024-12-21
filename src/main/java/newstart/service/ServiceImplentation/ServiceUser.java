package newstart.service.ServiceImplentation;

import java.util.List;

import newstart.core.Service;
import newstart.enums.Role;

public interface ServiceUser<T> extends Service<T> {
    List<T> showRole(Role role);
    T getLogin(String login);
}
