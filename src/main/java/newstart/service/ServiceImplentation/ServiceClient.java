package newstart.service.ServiceImplentation;

import newstart.core.Service;

public interface ServiceClient<T> extends Service<T>{
    T getTel(String telephone);
}
