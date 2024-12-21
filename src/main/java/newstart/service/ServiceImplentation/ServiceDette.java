package newstart.service.ServiceImplentation;

import java.util.List;

import newstart.core.Service;
import newstart.entities.Client;
import newstart.entities.Dette;

public interface ServiceDette<T> extends Service<T> {
    List<Dette> getDettesNonSoldeesByClientId(int clientId);
    Dette getById(int id);
    void updateValidation(Dette dette,String etat);
    List<Dette> getDettesEnCoursByClientId(int clientId);
    List<Dette> getDettesByClientId(int clientId);
    List<Dette> showFiltre();
    void update(Dette dette);
    Client getClient(int id);
    double getMontantTotalClient(int clientId);
}
