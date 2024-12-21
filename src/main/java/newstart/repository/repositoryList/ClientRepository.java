package newstart.repository.repositoryList;

import newstart.entities.Client;
import newstart.entities.User;
import newstart.repository.implementation.RepositoryClient;


public class ClientRepository extends RepositoryImplList<Client> implements RepositoryClient{
    @Override
    public Client getByTel(String telephone){
        for (Client client : datas) {
            if(client.getTelephone().compareTo(telephone)==0){
                return client;
            }
        }
        return null;
    }

    @Override
    public Client getClientByUser(User user) {
        for (Client client : datas) {
            if (client.getCompte().equals(user)) {
                return client;
            }
        }
        return null;
    }
}
